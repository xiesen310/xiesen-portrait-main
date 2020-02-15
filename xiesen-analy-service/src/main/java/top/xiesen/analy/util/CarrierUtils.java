package top.xiesen.analy.util;

import java.util.regex.Pattern;

/**
 * @Description 手机运行商工具类
 * @className top.xiesen.analy.util.CarrierUtils
 * @Author 谢森
 * @Email xiesen310@163.com
 * @Date 2020/2/15 9:28
 */
public class CarrierUtils {
    /**
     * 中国电信号码格式验证 手机段： 133,153,180,181,189,177,1700,173,199
     **/
    private static final String CHINA_TELECOM_PATTERN = "(^1(33|53|77|73|99|8[019])\\d{8}$)|(^1700\\d{7}$)";

    /**
     * 中国联通号码格式验证 手机段：130,131,132,155,156,185,186,145,176,1709
     **/
    private static final String CHINA_UNICOM_PATTERN = "(^1(3[0-2]|4[5]|5[56]|7[6]|8[56])\\d{8}$)|(^1709\\d{7}$)";

    /**
     * 中国移动号码格式验证
     * 手机段：134,135,136,137,138,139,150,151,152,157,158,159,182,183,184,187,188,147,178,1705
     **/
    private static final String CHINA_MOBILE_PATTERN = "(^1(3[4-9]|4[7]|5[0-27-9]|7[8]|8[2-478])\\d{8}$)|(^1705\\d{7}$)";

    /**
     * 0、未知 1、移动 2、联通 3、电信
     * 根据手机号获取运行商
     *
     * @param telPhone 电话号码
     * @return int
     */
    public static int getCarrierByTel(String telPhone) {
        boolean b1 = telPhone == null || telPhone.trim().equals("") ? false : match(CHINA_MOBILE_PATTERN, telPhone);
        if (b1) {
            return 1;
        }
        b1 = telPhone == null || telPhone.trim().equals("") ? false : match(CHINA_UNICOM_PATTERN, telPhone);
        if (b1) {
            return 2;
        }
        b1 = telPhone == null || telPhone.trim().equals("") ? false : match(CHINA_TELECOM_PATTERN, telPhone);
        if (b1) {
            return 3;
        }
        return 0;
    }

    /**
     * 正则匹配
     *
     * @param regex    匹配规则
     * @param telPhone 电话号码
     * @return boolean
     */
    private static boolean match(String regex, String telPhone) {
        return Pattern.matches(regex, telPhone);
    }

}
