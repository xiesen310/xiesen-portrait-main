package top.xiesen.common.log;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description 浏览商品日志
 * @className top.xiesen.analy.log.ScanProductLog
 * @Author 谢森
 * @Email xiesen310@163.com
 * @Date 2020/2/15 13:43
 */
@Data
public class ScanProductLog implements Serializable {
    /**
     * 商品 id
     */
    private int productId;

    /**
     * 商品类型 id
     */
    private int productTypeId;

    /**
     * 浏览时间
     */
    private String scanTime;

    /**
     * 停留时间
     */
    private String stayTime;

    /**
     * 用户 id
     */
    private int userId;

    /**
     * 终端类型:0、pc端；1、移动端；2、小程序
     */
    private int useType;

    /**
     * 用户ip
     */
    private String ip;

    /**
     * 品牌
     */
    private String brand;
}
