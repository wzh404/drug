package com.wannengyongyao.drug.common;

/**
 * Created by @author wangzunhui on 2018/3/15.
 */
public enum ResultCode {
    OK(0),
    PLEASE_LOGIN(1000),
    UPLOAD_FILE_FAILED(1001);

    private int code;

    public int getCode() {
        return code;
    }

    ResultCode(int code){
        this.code = code;
    }
}