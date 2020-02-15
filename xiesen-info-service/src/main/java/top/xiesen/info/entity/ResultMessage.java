package top.xiesen.info.entity;

import lombok.Data;

/**
 * @Description 结果集封装
 * @className top.xiesen.info.entity.ResultMessage
 * @Author 谢森
 * @Email xiesen310@163.com
 * @Date 2020/2/15 14:58
 */
@Data
public class ResultMessage {
    /**
     * 状态 fail 、 success
     */
    private String status;

    /**
     * 消息内容
     */
    private String message;
}
