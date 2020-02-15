package top.xiesen.analy.reduce;

import org.apache.flink.api.common.functions.ReduceFunction;
import top.xiesen.analy.entity.YearBase;

/**
 * @Description
 * @className top.xiesen.analy.reduce.YearBaseReduce
 * @Author 谢森
 * @Email xiesen310@163.com
 * @Date 2020/2/5 11:19
 */
public class YearBaseReduce implements ReduceFunction<YearBase> {

    @Override
    public YearBase reduce(YearBase yearBase, YearBase t1) throws Exception {
        String yearType = yearBase.getYearType();
        String groupField = yearBase.getGroupField();
        Long count1 = yearBase.getCount();
        Long count2 = t1.getCount();

        YearBase finalYearBase = new YearBase();
        finalYearBase.setYearType(yearType);
        finalYearBase.setCount(count1 + count2);
        finalYearBase.setGroupField(groupField);
        return finalYearBase;
    }
}
