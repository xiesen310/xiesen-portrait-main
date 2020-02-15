package top.xiesen.info.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import top.xiesen.common.log.AttentionProductLog;
import top.xiesen.common.log.BuyCartProductLog;
import top.xiesen.common.log.CollectProductLog;
import top.xiesen.common.log.ScanProductLog;
import top.xiesen.common.utils.ReadProperties;
import top.xiesen.info.entity.ResultMessage;

import javax.servlet.http.HttpServletRequest;

/**
 * kafka-topics.sh --zookeeper s103:2181/kafka011 --create --partitions 2 --replication-factor 1 --topic attentionProductLog
 * kafka-topics.sh --zookeeper s103:2181/kafka011 --create --partitions 2 --replication-factor 1 --topic buyCartProductLog
 * kafka-topics.sh --zookeeper s103:2181/kafka011 --create --partitions 2 --replication-factor 1 --topic collectProductLog
 * kafka-topics.sh --zookeeper s103:2181/kafka011 --create --partitions 2 --replication-factor 1 --topic scanProductLog
 *
 * @Description 控制器
 * @className top.xiesen.info.control.InfoInControl
 * @Author 谢森
 * @Email xiesen310@163.com
 * @Date 2020/2/15 15:00
 */
@RestController
@RequestMapping("infoLog")
public class InfoInController {
    private final String attentionProductLogTopic = ReadProperties.getKey("attentionProductLog");
    private final String buyCartProductLogTopic = ReadProperties.getKey("buyCartProductLog");
    private final String collectProductLogTopic = ReadProperties.getKey("collectProductLog");
    private final String scanProductLogTopic = ReadProperties.getKey("scanProductLog");

    @Autowired
    private KafkaProducer<String, String> kafkaProducer;

    @RequestMapping(value = "helloWord", method = RequestMethod.GET)
    public String helloWord(HttpServletRequest req) {
        String ip = req.getRemoteAddr();
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setMessage("hello:" + ip);
        resultMessage.setStatus("success");
        String result = JSONObject.toJSONString(resultMessage);
        return result;
    }

    /**
     * AttentionProductLog: {"productId":"productId"...}
     * BuyCartProductLog: {"productId":"productId"...}
     * CollectProductLog: {"productId":"productId"...}
     * ScanProductLog: {"productId":"productId"...}
     *
     * @param receiveLog
     * @param req
     * @return
     */
    @RequestMapping(value = "receiveLog", method = RequestMethod.POST)
    public String receiveLog(String receiveLog, HttpServletRequest req) {
        if (StringUtils.isBlank(receiveLog)) {
            return null;
        }
        String[] receiveArrays = receiveLog.split(":", 2);
        String className = receiveArrays[0];
        String data = receiveArrays[1];

        String resultMessage = "";

        if ("AttentionProductLog".equals(className)) {
            AttentionProductLog attentionProductLog = JSONObject.parseObject(data, AttentionProductLog.class);
            resultMessage = JSONObject.toJSONString(attentionProductLog);
            kafkaProducer.send(new ProducerRecord<>(attentionProductLogTopic, resultMessage));
        } else if ("BuyCartProductLog".equals(className)) {
            BuyCartProductLog buyCartProductLog = JSONObject.parseObject(data, BuyCartProductLog.class);
            resultMessage = JSONObject.toJSONString(buyCartProductLog);
            kafkaProducer.send(new ProducerRecord<>(buyCartProductLogTopic, resultMessage));
        } else if ("CollectProductLog".equals(className)) {
            CollectProductLog collectProductLog = JSONObject.parseObject(data, CollectProductLog.class);
            resultMessage = JSONObject.toJSONString(collectProductLog);
            kafkaProducer.send(new ProducerRecord<>(collectProductLogTopic, resultMessage));
        } else if ("ScanProductLog".equals(className)) {
            ScanProductLog scanProductLog = JSONObject.parseObject(data, ScanProductLog.class);
            resultMessage = JSONObject.toJSONString(scanProductLog);
            kafkaProducer.send(new ProducerRecord<>(scanProductLogTopic, resultMessage));
        }

        ResultMessage resultMsg = new ResultMessage();
        resultMsg.setMessage(resultMessage);
        resultMsg.setStatus("success");
        String result = JSONObject.toJSONString(resultMsg);
        return result;
    }

}
