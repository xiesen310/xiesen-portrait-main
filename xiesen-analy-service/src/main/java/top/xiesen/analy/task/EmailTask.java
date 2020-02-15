package top.xiesen.analy.task;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.utils.ParameterTool;
import org.bson.Document;
import top.xiesen.analy.entity.CarrierInfo;
import top.xiesen.analy.entity.EmailInfo;
import top.xiesen.analy.map.CarrierMap;
import top.xiesen.analy.map.EmailMap;
import top.xiesen.analy.reduce.CarrierReduce;
import top.xiesen.analy.reduce.EmailReduce;
import top.xiesen.analy.util.MongoUtils;

import java.util.List;

/**
 * @Description 统计每个 email 群里的数量，做到近实时统计，每半个小时进行一次统计
 * @className top.xiesen.analy.task.YearBaseTask
 * @Author 谢森
 * @Email xiesen310@163.com
 * @Date 2020/2/4 21:11
 */
public class EmailTask {
    public static void main(String[] args) throws Exception {
        final ParameterTool params = ParameterTool.fromArgs(args);

        // set up the execution environment
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        // make parameters available in the web interface
        env.getConfig().setGlobalJobParameters(params);
        DataSet<String> text = null;
        if (null == params.get("input")) {
            text = env.readTextFile("D:\\Develop\\workspace\\xiesen\\xiesen-portrait-main\\xiesen-analy-service\\src\\main\\resources\\userinfo.txt");
        } else {
            text = env.readTextFile(params.get("input"));
        }

        DataSet<EmailInfo> mapResult = text.map(new EmailMap());

        DataSet<EmailInfo> reduceResult = mapResult.groupBy("groupField").reduce(new EmailReduce());

        try {
            mapResult.print();
            List<EmailInfo> resultList = reduceResult.collect();
            for (EmailInfo emailInfo : resultList) {
                String emailType = emailInfo.getEmailType();
                Long count = emailInfo.getCount();
                Document doc = MongoUtils.findOneBy("emailStatics", "xiesenPortrait", emailType);

                if (doc == null) {
                    doc = new Document();
                    doc.put("info", emailType);
                    doc.put("count", count);
                } else {
                    Long countPre = doc.getLong("count");
                    Long total = countPre + count;
                    doc.put("count", total);
                }
                MongoUtils.saveOrUpdateMongo("emailStatics", "xiesenPortrait", doc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
