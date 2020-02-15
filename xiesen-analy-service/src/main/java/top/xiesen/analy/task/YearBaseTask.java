package top.xiesen.analy.task;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.utils.ParameterTool;
import org.bson.Document;
import top.xiesen.analy.entity.YearBase;
import top.xiesen.analy.map.YearBaseMap;
import top.xiesen.analy.reduce.YearBaseReduce;
import top.xiesen.analy.util.MongoUtils;

import java.util.List;

/**
 * @Description 统计每个年代群里的数量，做到近实时统计，每半个小时进行一次统计
 * @className top.xiesen.analy.task.YearBaseTask
 * @Author 谢森
 * @Email xiesen310@163.com
 * @Date 2020/2/4 21:11
 */
public class YearBaseTask {
    public static void main(String[] args) throws Exception {
        final ParameterTool params = ParameterTool.fromArgs(args);

        // set up the execution environment
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        // make parameters available in the web interface
        env.getConfig().setGlobalJobParameters(params);

        DataSet<String> text = env.readTextFile(params.get("input"));

        DataSet<YearBase> mapResult = text.map(new YearBaseMap());

        DataSet<YearBase> reduceResult = mapResult.groupBy("groupField").reduce(new YearBaseReduce());

        try {
            mapResult.print();
            List<YearBase> resultList = reduceResult.collect();
            for (YearBase yearBase : resultList) {
                String yearType = yearBase.getYearType();
                Long count = yearBase.getCount();
                Document doc = MongoUtils.findOneBy("yearBaseStatics", "xiesenPortrait", yearType);
                if (doc == null) {
                    doc = new Document();
                    doc.put("yearBaseType", yearType);
                    doc.put("count", count);
                } else {
                    Long countPre = doc.getLong("count");
                    Long total = countPre + count;
                    doc.put("count", total);
                }
                MongoUtils.saveOrUpdateMongo("yearBaseStatics", "xiesenPortrait", doc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
