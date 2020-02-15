package top.xiesen.analy.mock;

import top.xiesen.analy.util.DateUtils;
import top.xiesen.analy.util.FileUtils;

import java.io.*;
import java.sql.Timestamp;
import java.util.Random;

/**
 * @Description 模拟数据库同步数据
 * @className top.xiesen.analy.mock.UserInfoMock
 * @Author 谢森
 * @Email xiesen310@163.com
 * @Date 2020/2/5 11:50
 */
public class UserInfoMock {
    /**
     * 中国电信号码格式验证 CHINA_TELECOM_PATTERN  手机段： 133,153,180,181,189,177,173,199
     * 中国联通号码格式验证 CHINA_UNICOM_PATTERN  手机段：130,131,132,155,156,185,186,145,176
     * 中国移动号码格式验证 CHINA_MOBILE_PATTERN 手机段：134,135,136,137,138,139,150,151,152,157,158,159,182,183,184,187,188,147,178
     *
     * @return
     */
    private static String getRandomTelPhone() {
        String[] prefixChinaTel = {"133", "153", "180", "181", "189", "177", "173", "199"};
        String[] prefixChinaUnion = {"130", "131", "132", "155", "156", "185", "186", "145", "176"};
        String[] prefixChinaMobile = {"134", "135", "136", "137", "138", "139", "150", "151", "152",
                "157", "158", "159", "182", "183", "184", "187", "188", "147", "178"};
        String prefix = "110";
        Random random = new Random();
        int i = random.nextInt(3);
        switch (i) {
            case 0:
                prefix = prefixChinaTel[random.nextInt(prefixChinaTel.length - 1)];
                break;
            case 1:
                prefix = prefixChinaUnion[random.nextInt(prefixChinaUnion.length - 1)];
                break;
            case 2:
                prefix = prefixChinaMobile[random.nextInt(prefixChinaMobile.length - 1)];
                break;
            default:
                prefix = "110";
                break;
        }
        String tel = prefix + getNum() + getNum();
        return tel;

    }

    /**
     * 获取4位随机数字
     *
     * @return String
     */
    private static String getNum() {
        String nums = "1234567890";
        String result = "";
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            int index = random.nextInt(nums.length());
            char c = nums.charAt(index);
            result += c;
        }
        return result;
    }

    /**
     * * 网易邮箱 @163.com @126.com
     * * 移动邮箱 @139.com
     * * 搜狐邮箱 @sohu.com
     * * qq邮箱  @qq.com
     * * 189邮箱 @189.cn
     * * tom邮箱 @tom.com
     * * 阿里邮箱 @aliyun.com
     * * 新浪邮箱 @sina.com
     * * 等等
     *
     * @return
     */
    private static String getRandomEmail() {
        Random random = new Random();
        String[] emailSuffix = {"@163.com", "@126.com", "@139.com", "@sohu.com", "@qq.com", "@189.cn",
                "@tom.com", "@aliyun.com", "@sina.com"};
        String suffix = emailSuffix[random.nextInt(emailSuffix.length - 1)];
        String email = getNum() + getNum() + suffix;
        return email;
    }

    private static String mockUserInfo() {
        Random random = new Random();
        String[] names = {"Ada", "Addie", "Abbey", "Adela", "Adeline", "Adele"};
        String[] phones = {"131123456789", "151123456789", "134123456789", "181123456789", "152123456789", "181123456789"};
        /**
         * 终端类型:0、pc端；1、移动端；2、小程序
         */
        int[] userTypes = {0, 1, 2};

        int userId = random.nextInt(100);
        String username = names[random.nextInt(names.length - 1)];
        int sex = 0;
        String telPhone = getRandomTelPhone();
        String email = getRandomEmail();
        int age = (int) (Math.random() * 90 + 10);
        Timestamp registerTime = DateUtils.getCurrentTimestamp();
        int userType = userTypes[random.nextInt(userTypes.length - 1)];

        return String.format("%d,%s,%d,%s,%s,%d,%s,%d",
                userId, username, sex, telPhone, email, age, registerTime, userType);
    }

    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 100; i++) {
            String output = mockUserInfo();
            String file = "D:\\Develop\\workspace\\xiesen\\xiesen-portrait-main\\xiesen-analy-service\\src\\main\\resources\\userinfo.txt";
            FileUtils.writerFile(file, output + "\n");
        }
//        System.out.println(getRandomTelPhone());
//        System.out.println(getRandomEmail());
    }
}
