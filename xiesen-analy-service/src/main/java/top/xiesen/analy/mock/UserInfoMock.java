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
        String telphone = phones[random.nextInt(phones.length - 1)];
        String email = username + "@163.com";
        int age = (int) (Math.random() * 90 + 10);
        Timestamp registerTime = DateUtils.getCurrentTimestamp();
        int userType = userTypes[random.nextInt(userTypes.length - 1)];

        return String.format("%d,%s,%d,%s,%s,%d,%s,%d",
                userId, username, sex, telphone, email, age, registerTime, userType);
    }

    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 10; i++) {
            String output = mockUserInfo();
            String file = "D:\\Develop\\workspace\\xiesen\\xiesen-portrait-main\\xiesen-analy-service\\src\\main\\resources\\userinfo.txt";
            FileUtils.writerFile(file, output + "\n");
        }
    }
}
