package top.xiesen.analy.map;

import org.apache.commons.lang.StringUtils;
import org.apache.flink.api.common.functions.MapFunction;
import top.xiesen.analy.entity.BaiJiaInfo;
import top.xiesen.analy.entity.CarrierInfo;
import top.xiesen.analy.util.CarrierUtils;
import top.xiesen.analy.util.HbaseUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 败家 map
 * @className top.xiesen.analy.map.YearBaseMap
 * @Author 谢森
 * @Email xiesen310@163.com
 * @Date 2020/2/4 21:15
 */
public class BaiJiaMap implements MapFunction<String, BaiJiaInfo> {
    @Override
    public BaiJiaInfo map(String s) throws Exception {
        if (StringUtils.isBlank(s)) {
            return null;
        }

        String[] orderInfos = s.split(",");
        String id = orderInfos[0];
        String productId = orderInfos[1];
        String productTypeId = orderInfos[2];
        String createTime = orderInfos[3];
        String amount = orderInfos[4];
        String payType = orderInfos[5];
        String payTime = orderInfos[6];
        String payStatus = orderInfos[7];
        String couponAmount = orderInfos[8];
        String totalAmount = orderInfos[9];
        String refundAmount = orderInfos[10];
        String num = orderInfos[11];
        String userId = orderInfos[12];


        BaiJiaInfo baiJiaInfo = new BaiJiaInfo();
        baiJiaInfo.setUserId(userId);
        baiJiaInfo.setCreateTime(createTime);
        baiJiaInfo.setAmount(amount);
        baiJiaInfo.setPayType(payType);
        baiJiaInfo.setPayTime(payTime);
        baiJiaInfo.setPayStatus(payStatus);
        baiJiaInfo.setCouponAmount(couponAmount);
        baiJiaInfo.setTotalAmount(totalAmount);
        baiJiaInfo.setRefundAmount(refundAmount);

        String groupField = "baiJia==" + userId;
        baiJiaInfo.setGroupField(groupField);
        baiJiaInfo.setCount(1L);
        List<BaiJiaInfo> list = new ArrayList<>();
        list.add(baiJiaInfo);

        return baiJiaInfo;
    }
}
