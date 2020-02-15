package top.xiesen.analy.map;

import org.apache.commons.lang.StringUtils;
import org.apache.flink.api.common.functions.MapFunction;
import top.xiesen.analy.entity.CarrierInfo;
import top.xiesen.analy.entity.YearBase;
import top.xiesen.analy.util.CarrierUtils;
import top.xiesen.analy.util.DateUtils;
import top.xiesen.analy.util.HbaseUtils;

/**
 * @Description 年代标签 map
 * @className top.xiesen.analy.map.YearBaseMap
 * @Author 谢森
 * @Email xiesen310@163.com
 * @Date 2020/2/4 21:15
 */
public class CarrierMap implements MapFunction<String, CarrierInfo> {
    @Override
    public CarrierInfo map(String s) throws Exception {
        if (StringUtils.isBlank(s)) {
            return null;
        }

        String[] userInfos = s.split(",");
        String userId = userInfos[0];
        String username = userInfos[1];
        String sex = userInfos[2];
        String telPhone = userInfos[3];
        String email = userInfos[4];
        String age = userInfos[5];
        String registerTime = userInfos[6];
        String userType = userInfos[7]; // 终端类型:0、pc端；1、移动端；2、小程序
        int carrierType = CarrierUtils.getCarrierByTel(telPhone);
        String carrierTypeStr = carrierType == 0 ? "未知运营商" : carrierType == 1 ? "移动用户"
                : carrierType == 2 ? "联通用户" : "电信用户";


        // create 'userFlagInfo',  {NAME=>'baseInfo'}
        String tableName = "userFlagInfo";
        String rowKey = userId;
        String familyName = "baseInfo";
        String column = "carrierInfo";
        HbaseUtils.putData(tableName, rowKey, familyName, column, carrierTypeStr);

        CarrierInfo carrierInfo = new CarrierInfo();

        String groupField = "carrierInfo==" + carrierType;
        carrierInfo.setCarrier(carrierTypeStr);
        carrierInfo.setCount(1L);
        carrierInfo.setGroupField(groupField);

        return carrierInfo;
    }
}
