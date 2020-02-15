package top.xiesen.analy.util;

import java.io.*;

/**
 * @Description 文件工具类
 * @className top.xiesen.analy.util.FileUtils
 * @Author 谢森
 * @Email xiesen310@163.com
 * @Date 2020/2/5 12:18
 */
public class FileUtils {

    /**
     * 以追加的方式写入,注意换行
     * @param filePath 文件路径
     * @param content 写入的数据
     */
    public static void writerFile(String filePath, String content) {
        File file = new File(filePath);
        OutputStreamWriter out = null;
        try {
            out = new OutputStreamWriter(
                    new FileOutputStream(file, true), // true to append
                    "UTF-8"
            );
            out.write(content);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
