package top.xiesen.analy.entity;

import lombok.Data;

/**
 * @Description email 实体
 * @className top.xiesen.analy.entity.EmailInfo
 * @Author 谢森
 * @Email xiesen310@163.com
 * @Date 2020/2/15 10:51
 */
@Data
public class EmailInfo {
    /**
     * email 类型
     */
    private String emailType;

    /**
     * 数量
     */
    private Long count;

    /**
     * 分组字段
     */
    private String groupField;
}
