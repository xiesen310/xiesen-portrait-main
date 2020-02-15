package top.xiesen.analy.task;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.utils.ParameterTool;
import org.bson.Document;
import top.xiesen.analy.entity.BaiJiaInfo;
import top.xiesen.analy.entity.CarrierInfo;
import top.xiesen.analy.map.BaiJiaMap;
import top.xiesen.analy.map.CarrierMap;
import top.xiesen.analy.reduce.BaiJiaReduce;
import top.xiesen.analy.reduce.CarrierReduce;
import top.xiesen.analy.util.DateUtils;
import top.xiesen.analy.util.HbaseUtils;
import top.xiesen.analy.util.MongoUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description 败家指数 = 支付金额平均值*0.3、最大支付金额*0.3、下单频率*0.4
 * @className top.xiesen.analy.task.YearBaseTask
 * @Author 谢森
 * @Email xiesen310@163.com
 * @Date 2020/2/4 21:11
 */
public class BaiJiaTask {
    public static void main(String[] args) throws Exception {
        final ParameterTool params = ParameterTool.fromArgs(args);

        // set up the execution environment
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        // make parameters available in the web interface
        env.getConfig().setGlobalJobParameters(params);
        DataSet<String> text = null;
        if (null == params.get("input")) {
            text = env.readTextFile("");
        } else {
            text = env.readTextFile(params.get("input"));
        }

        DataSet<BaiJiaInfo> mapResult = text.map(new BaiJiaMap());

        DataSet<BaiJiaInfo> reduceResult = mapResult.groupBy("groupField").reduce(new BaiJiaReduce());

        try {
            mapResult.print();
            List<BaiJiaInfo> resultList = reduceResult.collect();
            for (BaiJiaInfo baiJiaInfo : resultList) {
                String userId = baiJiaInfo.getUserId();
                List<BaiJiaInfo> list = baiJiaInfo.getList();
                Collections.sort(list, new Comparator<BaiJiaInfo>() {
                    @Override
                    public int compare(BaiJiaInfo o1, BaiJiaInfo o2) {
                        String time1 = o1.getCreateTime();
                        String time2 = o2.getCreateTime();
                        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd hhmmss");
                        Date dateNow = new Date();
                        Date date1 = dateNow;
                        Date date2 = dateNow;

                        try {
                            date1 = dateFormat.parse(time1);
                            date2 = dateFormat.parse(time2);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        return time1.compareTo(time2);
                    }
                });
                Map<Integer, Integer> frequencyMap = new HashMap<>();
                double maxAmount = 0d;
                double sum = 0d;
                BaiJiaInfo before = null;
                for (BaiJiaInfo baiJiaInfoInner : list) {
                    if (before == null) {
                        before = baiJiaInfoInner;
                        continue;
                    }


                    // 计算购买的频次
                    String beforeTime = before.getCreateTime();
                    String endTime = baiJiaInfoInner.getCreateTime();
                    int days = DateUtils.getDaysBetweenByStartAndEnd(beforeTime, endTime, "yyyyMMdd hhmmss");

                    int beforeDays = frequencyMap.get(days) == null ? 0 : frequencyMap.get(days);
                    frequencyMap.put(days, beforeDays + 1);

                    // 计算最大金额
                    String totalAmountString = baiJiaInfoInner.getTotalAmount();
                    Double totalAmount = Double.parseDouble(totalAmountString);
                    if (totalAmount > maxAmount) {
                        maxAmount = totalAmount;
                    }

                    // 计算平均值
                    sum += totalAmount;
                    before = baiJiaInfoInner;

                }

                double avgAmount = sum / list.size();
                int totalDays = 0;

                Set<Map.Entry<Integer, Integer>> set = frequencyMap.entrySet();
                for (Map.Entry<Integer, Integer> entry : set) {
                    Integer frequencyCount = entry.getValue();
                    Integer frequencyDays = entry.getKey();
                    totalDays += frequencyDays * frequencyCount;
                }

                // 平均天数
                int avgDays = totalDays / list.size();
                /**
                 * 败家指数 = 支付金额平均值*0.3、最大支付金额*0.3、下单频率*0.4
                 * 支付金额平均值30分 (0-20: 5; 20-60: 10; 60-100:20; 100-150:30; 150-200:40; 200-250:60; 250-350:70;
                 * 350-450:80;450-600:90; 600以上:100 )
                 * 最大支付金额30分 (0-20:5; 20-60:10; 60-200:30; 200-500:60; 500-700:80; 700以上:100)
                 * 下单频率40分 (0-5:100; 5-10:90; 10-30:70; 30-60: 60; 60-80:40; 80-100:20; 100以上: 10)
                 *
                 */
                int avgAmountScore = 0;
                if (avgAmount >= 0 && avgAmount < 20) {
                    avgAmountScore = 5;
                } else if (avgAmount >= 20 && avgAmount < 60) {
                    avgAmountScore = 10;
                } else if (avgAmount >= 60 && avgAmount < 100) {
                    avgAmountScore = 20;
                } else if (avgAmount >= 100 && avgAmount < 150) {
                    avgAmountScore = 30;
                } else if (avgAmount >= 150 && avgAmount < 200) {
                    avgAmountScore = 40;
                } else if (avgAmount >= 200 && avgAmount < 250) {
                    avgAmountScore = 60;
                } else if (avgAmount >= 250 && avgAmount < 350) {
                    avgAmountScore = 70;
                } else if (avgAmount >= 350 && avgAmount < 450) {
                    avgAmountScore = 80;
                } else if (avgAmount >= 450 && avgAmount < 600) {
                    avgAmountScore = 90;
                } else if (avgAmount >= 600) {
                    avgAmountScore = 100;
                }

                int maxAmountScore = 0;
                // (0-20:5; 20-60:10; 60-200:30; 200-500:60; 500-700:80; 700以上:100)
                if (maxAmount >= 0 && maxAmount < 20) {
                    maxAmountScore = 5;
                } else if (maxAmount >= 20 && maxAmount < 60) {
                    maxAmountScore = 10;
                } else if (maxAmount >= 60 && maxAmount < 200) {
                    maxAmountScore = 30;
                } else if (maxAmount >= 200 && maxAmount < 500) {
                    maxAmountScore = 60;
                } else if (maxAmount >= 500 && maxAmount < 700) {
                    maxAmountScore = 80;
                } else if (maxAmount >= 700) {
                    maxAmountScore = 100;
                }

                int avgDaysScore = 0;
                // (0-5:100; 5-10:90; 10-30:70; 30-60: 60; 60-80:40; 80-100:20; 100以上: 10)
                if (avgDays >= 0 && avgDays < 5) {
                    avgDaysScore = 100;
                } else if (avgDays >= 5 && avgDays < 10) {
                    avgDaysScore = 90;
                } else if (avgDays >= 10 && avgDays < 30) {
                    avgDaysScore = 70;
                } else if (avgDays >= 30 && avgDays < 60) {
                    avgDaysScore = 60;
                } else if (avgDays >= 60 && avgDays < 80) {
                    avgDaysScore = 40;
                } else if (avgDays >= 80 && avgDays < 100) {
                    avgDaysScore = 20;
                } else if (avgDays >= 100) {
                    avgDaysScore = 10;
                }

                double totalScore = (avgAmountScore / 100) * 30 + (maxAmountScore / 100) * 30 + (avgDaysScore / 100) * 40;

                String tableName = "userFlagInfo";
                String rowKey = userId;
                String familyName = "baseInfo";
                String column = "baiJiaInfo";
                HbaseUtils.putData(tableName, rowKey, familyName, column, totalScore + "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
