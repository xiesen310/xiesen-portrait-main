package top.xiesen.analy.map;

import org.apache.commons.lang.StringUtils;
import org.apache.flink.api.common.functions.MapFunction;
import top.xiesen.analy.entity.CarrierInfo;
import top.xiesen.analy.entity.EmailInfo;
import top.xiesen.analy.util.CarrierUtils;
import top.xiesen.analy.util.EmailUtils;
import top.xiesen.analy.util.HbaseUtils;

/**
 * @Description email map
 * @className top.xiesen.analy.map.YearBaseMap
 * @Author 谢森
 * @Email xiesen310@163.com
 * @Date 2020/2/4 21:15
 */
public class EmailMap implements MapFunction<String, EmailInfo> {
    @Override
    public EmailInfo map(String s) throws Exception {
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

        String emailType = EmailUtils.getEmailTypeBy(email);

        // create 'userFlagInfo',  {NAME=>'baseInfo'}
        String tableName = "userFlagInfo";
        String rowKey = userId;
        String familyName = "baseInfo";
        String column = "emailInfo";
        HbaseUtils.putData(tableName, rowKey, familyName, column, emailType);

        EmailInfo emailInfo = new EmailInfo();
        String groupField = "emailInfo==" + emailType;
        emailInfo.setCount(1L);
        emailInfo.setEmailType(emailType);
        emailInfo.setGroupField(groupField);

        return emailInfo;
    }
}
