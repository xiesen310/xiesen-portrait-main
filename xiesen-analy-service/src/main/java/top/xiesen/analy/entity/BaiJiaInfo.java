package top.xiesen.analy.entity;

import lombok.Data;

import java.util.List;

/**
 * @Description 败家实体
 * @className top.xiesen.analy.entity.BaiJiaInfo
 * @Author 谢森
 * @Email xiesen310@163.com
 * @Date 2020/2/15 11:34
 */
@Data
public class BaiJiaInfo {
    /**
     * 败家指数区段:
     * 0 - 20
     * 20 - 50
     * 50 - 70
     * 70 - 80
     * 80 - 90
     * 90 - 100
     */
    private String baiJiaType;
    private String userId;
    private String createTime;
    private String amount;
    private String payType;
    private String payTime;

    /**
     * 0：未支付
     * 1：已支付
     * 2：已退款
     */
    private String payStatus;
    private String couponAmount;
    private String totalAmount;
    private String refundAmount;

    /**
     * 数量
     */
    private Long count;

    /**
     * 分组字段
     */
    private String groupField;

    private List<BaiJiaInfo> list;
}
