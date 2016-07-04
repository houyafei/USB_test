package com.example.administrator.lightspec.constantpkg;

/**
 * Created by Administrator on 2016/7/2.
 */
public class ConstantCMD {

    public static  final  String CMD_4 = "*meas:dark 500 1 4";

    public static  final  String CMD_7 = "*meas:dark 500 1 7";


    public static  final  String CMD_1 = "*meas:dark 500 1 1";

    public static  final  String CMD_2 = "*meas:dark 500 1 2";

    public byte[] cmd(String cmdStr){

        return cmdStr.getBytes();
    }
}
