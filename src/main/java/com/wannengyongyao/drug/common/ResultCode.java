package com.wannengyongyao.drug.common;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Created by @author wangzunhui on 2018/3/15.
 */
public enum ResultCode {
    OK(0),
    USER_PLEASE_LOGIN(1000),
    UPLOAD_FILE_FAILED(1001),
    DRUG_NOT_EXIST(1003),
    USER_PHARMACIST_EXIST(2001),
    USER_CART_DRUG_EXIST(2002),
    ORDER_NOT_EXIST(3001),
    ORDER_CANCELED(3002),
    ORDER_CANNOT_CANCEL(3003),
    ORDER_SELLER_NOT_EXIST(3004),
    ORDER_NOT_CONFIRM(3005),
    ORDER_NOT_PAYMENT(3006),
    ORDER_NOT_SHIPPED(3007),
    ORDER_INVALID_COLLECTION_CODE(3008),
    ORDER_UNAUTHORIZED(3009),
    ORDER_INVALID_STATUS(3010),
    INVALID_SMS_CODE(4001),
    NOT_REPEAT_SEND_CODE(4002),
    PHARMACIST_NOT_EXIST(5001),
    PHARMACIST_ORDER_EXIST(5002),
    COUPON_NOT_EXIST(6001),
    COUPON_USED(6002),
    COUPON_EXPIRED(6003),
    COUPON_NOT_STARTED(6004),
    COUPON_USER_ADDED(6005),
    BAD_REQUEST(9998),
    FAILED(9999);

    private int code;

    public int getCode() {
        return code;
    }

    public String getMsg(){
        return errmsgMap.get(code);
    }

    ResultCode(int code){
        this.code = code;
    }

    private static Map<Integer, String> errmsgMap = ImmutableMap.<Integer, String>builder()
            .put(USER_PLEASE_LOGIN.code, "请登录")
            .put(UPLOAD_FILE_FAILED.code, "文件上传失败")
            .put(USER_PHARMACIST_EXIST.code, "药师已结交")
            .put(USER_CART_DRUG_EXIST.code, "药师已结交")
            .put(DRUG_NOT_EXIST.code, "药品不存在")

            .put(ORDER_NOT_EXIST.code, "订单不存在")
            .put(ORDER_CANCELED.code, "订单已取消")
            .put(ORDER_CANNOT_CANCEL.code, "订单不能取消")
            .put(ORDER_NOT_CONFIRM.code, "订单未确认")
            .put(ORDER_NOT_PAYMENT.code, "订单未支付")
            .put(ORDER_NOT_SHIPPED.code, "订单未发货")
            .put(ORDER_SELLER_NOT_EXIST.code, "卖家抢单不存在")
            .put(ORDER_INVALID_COLLECTION_CODE.code, "无效的订单收货码")
            .put(ORDER_UNAUTHORIZED.code, "无权操作该订单")
            .put(ORDER_INVALID_STATUS.code, "无效的订单状态")

            .put(INVALID_SMS_CODE.code, "无效的手机验证码")
            .put(NOT_REPEAT_SEND_CODE.code, "不要重复发送手机验证码")

            .put(COUPON_NOT_EXIST.code, "优惠券不存在")
            .put(COUPON_USED.code, "优惠券已使用")
            .put(COUPON_EXPIRED.code, "优惠券已过期")
            .put(COUPON_NOT_STARTED.code, "优惠券未开始使用")
            .put(COUPON_USER_ADDED.code, "优惠券已添加")

            .put(PHARMACIST_NOT_EXIST.code, "药师不存在")
            .put(PHARMACIST_ORDER_EXIST.code, "订单已抢")
            .put(BAD_REQUEST.code, "非法请求")
            .put(FAILED.code, "操作失败")
            .build();
}