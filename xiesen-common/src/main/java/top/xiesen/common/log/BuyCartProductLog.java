package top.xiesen.common.log;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description 购物车行为日志
 * @className top.xiesen.analy.log.CollectProductLog
 * @Author 谢森
 * @Email xiesen310@163.com
 * @Date 2020/2/15 13:49
 */
@Data
public class BuyCartProductLog implements Serializable {
    /**
     * 商品 id
     */
    private int productId;

    /**
     * 商品类型 id
     */
    private int productTypeId;

    /**
     * 操作时间
     */
    private String operationTime;

    /**
     * 操作类型
     * 0 加入
     * 1 删除
     */
    private int operationType;

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
