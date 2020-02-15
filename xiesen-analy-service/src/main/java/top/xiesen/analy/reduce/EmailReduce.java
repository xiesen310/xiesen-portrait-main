package top.xiesen.analy.reduce;

import org.apache.flink.api.common.functions.ReduceFunction;
import top.xiesen.analy.entity.CarrierInfo;
import top.xiesen.analy.entity.EmailInfo;

/**
 * @Description email reduce
 * @className top.xiesen.analy.reduce.YearBaseReduce
 * @Author 谢森
 * @Email xiesen310@163.com
 * @Date 2020/2/5 11:19
 */
public class EmailReduce implements ReduceFunction<EmailInfo> {


    @Override
    public EmailInfo reduce(EmailInfo emailInfo, EmailInfo t1) throws Exception {
        String emailType = emailInfo.getEmailType();
        String groupField = emailInfo.getGroupField();
        Long count1 = emailInfo.getCount();
        Long count2 = t1.getCount();

        EmailInfo finalEmailInfo = new EmailInfo();
        finalEmailInfo.setEmailType(emailType);
        finalEmailInfo.setCount(count1 + count2);
        finalEmailInfo.setGroupField(groupField);

        return finalEmailInfo;
    }
}
