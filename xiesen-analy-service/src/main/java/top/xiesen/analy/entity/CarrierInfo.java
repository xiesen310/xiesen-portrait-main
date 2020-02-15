package top.xiesen.analy.entity;

import lombok.Data;

/**
 * @Description
 * @className top.xiesen.analy.entity.CarrierInfo
 * @Author 谢森
 * @Email xiesen310@163.com
 * @Date 2020/2/15 9:36
 */
@Data
public class CarrierInfo {
    /**
     * 运行商
     */
    private String carrier;

    /**
     * 数量
     */
    private Long count;

    /**
     * 分组字段
     */
    private String groupField;
    
}
