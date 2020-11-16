package com.fei.bean;

public enum ResultEnum {
    FIFI,
    SUCCESS(1,"result shows success"),
    FAIL(1,"result shows fail"),
    SUSPEND(1,"result shows suspension");

    private Integer code;
    private String msg;

    ResultEnum(){
    }

    ResultEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "code is ===> " + code + " msg is ===> " + msg;
    }
}
