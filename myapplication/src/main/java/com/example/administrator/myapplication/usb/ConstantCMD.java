package com.example.administrator.myapplication.usb;

/**
 * Created by Administrator on 2016/7/2.
 */
public class ConstantCMD {





    // xx yy L/H
    public static  final  String CMD_1 = "*meas:dark 500 1 1\r\n";

    //ASCII
    public static  final  String CMD_2 = "*meas:dark 500 1 2\r\n";

    //len xx yy
    public static final String CMD_3 = "*meas:dark 500 1 3\r\n";

    //ASCII
    public static  final  String CMD_4 = "*meas:dark 500 1 4\r\n";


    //len xx yy H/L
    public static  final  String CMD_6 = "*meas:dark 500 1 6\r\n";

    // XX YY H/L
    public static  final  String CMD_5 = "*meas:dark 500 1 5\r\n";

    //ASCII  波长与像素
    public static  final  String CMD_7 = "*meas:dark 500 1 7\r\n";

    public byte[] cmd(String cmdStr){

        return cmdStr.getBytes();
    }
}
