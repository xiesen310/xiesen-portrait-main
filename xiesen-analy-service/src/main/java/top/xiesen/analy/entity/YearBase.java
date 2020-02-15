package top.xiesen.analy.entity;

import lombok.Data;

/**
 * @Description 年代标签实体类
 * @className top.xiesen.analy.entity.YearBase
 * @Author 谢森
 * @Email xiesen310@163.com
 * @Date 2020/2/4 21:16
 */
public class YearBase {
    /**
     * 年代类型
     */
    private String yearType;

    /**
     * 数量
     */
    private Long count;

    /**
     * 分组字段
     */
    private String groupField;


    public String getYearType() {
        return yearType;
    }

    public void setYearType(String yearType) {
        this.yearType = yearType;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getGroupField() {
        return groupField;
    }

    public void setGroupField(String groupField) {
        this.groupField = groupField;
    }
}
