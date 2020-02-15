package top.xiesen.info;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Description
 * @className top.xiesen.info.StartUpMain
 * @Author 谢森
 * @Email xiesen310@163.com
 * @Date 2020/2/15 14:56
 */
@SpringBootApplication
@EnableEurekaClient
public class StartUpMain {
    public static void main(String[] args) {
        SpringApplication.run(StartUpMain.class, args);
    }
}
