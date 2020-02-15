package top.xiesen.analy.map;

import org.apache.commons.lang.StringUtils;
import org.apache.flink.api.common.functions.MapFunction;
import top.xiesen.analy.entity.YearBase;
import top.xiesen.analy.util.DateUtils;
import top.xiesen.analy.util.HbaseUtils;

/**
 * @Description 年代标签 map
 * @className top.xiesen.analy.map.YearBaseMap
 * @Author 谢森
 * @Email xiesen310@163.com
 * @Date 2020/2/4 21:15
 */
public class YearBaseMap implements MapFunction<String, YearBase> {
    @Override
    public YearBase map(String s) throws Exception {
        if (StringUtils.isBlank(s)) {
            return null;
        }

        String[] userInfos = s.split(",");
        String userId = userInfos[0];
        String username = userInfos[1];
        String sex = userInfos[2];
        String telphone = userInfos[3];
        String email = userInfos[4];
        String age = userInfos[5];
        String registerTime = userInfos[6];
        String userType = userInfos[7]; // 终端类型:0、pc端；1、移动端；2、小程序

        String yearBaseType = DateUtils.getYearBaseByAge(age);

        // create 'userFlagInfo',  {NAME=>'baseInfo'}
        String tableName = "userFlagInfo";
        String rowKey = userId;
        String familyName = "baseInfo";
        String column = "yearBase";
        HbaseUtils.putData(tableName, rowKey, familyName, column, yearBaseType);

        YearBase yearBase = new YearBase();
        String groupField = "yearBase==" + yearBaseType;
        yearBase.setYearType(yearBaseType);
        yearBase.setCount(1L);
        yearBase.setGroupField(groupField);

        return yearBase;
    }
}
