package top.xiesen.common.utils;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
/**
 * @Description
 * @className top.xiesen.common.utils.ReadProperties
 * @Author 谢森
 * @Email xiesen310@163.com
 * @Date 2020/2/15 16:06
 */
public class ReadProperties {
    public final static Config config = ConfigFactory.load("xiesen.properties");
    public static String getKey(String key){
        return config.getString(key).trim();
    }
    public static String getKey(String key,String filename){
        Config config =  ConfigFactory.load(filename);
        return config.getString(key).trim();
    }
}
