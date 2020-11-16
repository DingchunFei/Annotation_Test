package com.fei.bean;

public class TestClass {

    public static void printDay(ResultEnum re){
        switch(re){
            case FIFI: System.out.println(ResultEnum.FIFI.getCode()+" "+ResultEnum.FIFI.getMsg()); break;
            case SUCCESS: System.out.println(ResultEnum.SUCCESS.getCode()+" "+ResultEnum.SUCCESS.getMsg()); break;
            case FAIL: System.out.println(ResultEnum.FAIL.getCode()+" "+ResultEnum.FAIL.getMsg());break;
            case SUSPEND: System.out.println(ResultEnum.SUSPEND.getCode()+" "+ResultEnum.SUSPEND.getMsg());break;
        }
        //由于重写了ResultEnum的toString，因此变成了格式化输出
        System.out.println(re);
    }

    public static void main(String[] args) {
        printDay(ResultEnum.FIFI);
    }
}
