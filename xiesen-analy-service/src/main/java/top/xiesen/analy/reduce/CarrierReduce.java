package top.xiesen.analy.reduce;

import org.apache.flink.api.common.functions.ReduceFunction;
import top.xiesen.analy.entity.CarrierInfo;
import top.xiesen.analy.entity.YearBase;

/**
 * @Description 手机运行商 reduce
 * @className top.xiesen.analy.reduce.YearBaseReduce
 * @Author 谢森
 * @Email xiesen310@163.com
 * @Date 2020/2/5 11:19
 */
public class CarrierReduce implements ReduceFunction<CarrierInfo> {

    @Override
    public CarrierInfo reduce(CarrierInfo carrierInfo, CarrierInfo carrierInfo1) throws Exception {
        String carrierType = carrierInfo.getCarrier();
        String groupField = carrierInfo.getGroupField();
        Long count1 = carrierInfo.getCount();
        Long count2 = carrierInfo1.getCount();

        CarrierInfo finalCarrierInfo = new CarrierInfo();
        finalCarrierInfo.setGroupField(groupField);
        finalCarrierInfo.setCount(count1 + count2);
        finalCarrierInfo.setCarrier(carrierType);

        return finalCarrierInfo;
    }
}
