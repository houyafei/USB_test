package com.example.administrator.lightspec.beans;

import com.hoho.android.usbserial.driver.UsbSerialPort;

/**
 * Created by Administrator on 2016/7/2.
 */
public  class MyUSBDevice {

    //通信端口
    private static UsbSerialPort sPort ;

    //波特率
    private static int baudRate = 115200;

    //命令结尾字符
    private static String tail = "\r" ;

    public static int getBaudRate() {
        return baudRate;
    }

    public static void setBaudRate(int baudRate) {
        MyUSBDevice.baudRate = baudRate;
    }

    public static UsbSerialPort getsPort() {
        return sPort;
    }

    public static void setsPort(UsbSerialPort sPort) {
        MyUSBDevice.sPort = sPort;
    }

    public static String getTail() {
        return tail;
    }

    public static void setTail(String tail) {
        MyUSBDevice.tail = tail;
    }
}
