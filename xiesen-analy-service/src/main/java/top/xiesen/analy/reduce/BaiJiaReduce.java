package top.xiesen.analy.reduce;

import org.apache.flink.api.common.functions.ReduceFunction;
import top.xiesen.analy.entity.BaiJiaInfo;
import top.xiesen.analy.entity.CarrierInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 败家 reduce
 * @className top.xiesen.analy.reduce.YearBaseReduce
 * @Author 谢森
 * @Email xiesen310@163.com
 * @Date 2020/2/5 11:19
 */
public class BaiJiaReduce implements ReduceFunction<BaiJiaInfo> {

    @Override
    public BaiJiaInfo reduce(BaiJiaInfo baiJiaInfo, BaiJiaInfo t1) throws Exception {
        String userId = baiJiaInfo.getUserId();
        List<BaiJiaInfo> list1 = baiJiaInfo.getList();
        List<BaiJiaInfo> list2 = t1.getList();
        List<BaiJiaInfo> finalList = new ArrayList<>();
        finalList.addAll(list1);
        finalList.addAll(list2);

        BaiJiaInfo finaleBaiJiaInfo = new BaiJiaInfo();
        finaleBaiJiaInfo.setUserId(userId);
        finaleBaiJiaInfo.setList(finalList);
        return finaleBaiJiaInfo;
    }
}
