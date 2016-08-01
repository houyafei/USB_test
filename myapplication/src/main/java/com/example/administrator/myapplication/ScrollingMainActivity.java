package com.example.administrator.myapplication;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.myapplication.usb.MyUSBDevice;
import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialPort;
import com.hoho.android.usbserial.driver.UsbSerialProber;

import java.util.ArrayList;
import java.util.List;

public class ScrollingMainActivity extends AppCompatActivity {

    private static final String TAG = "Houyafei" ;
    private static final String ACTION_USB_PERMISSION = " com.philips.usb.permission";
    private UsbManager mUsbManager ;
    private  Intent mIntent ;
    private PendingIntent mPermissionIntent;
    //相关控件
    private ProgressBar mProgressBar ;
    private TextView mTextDevices ;
    private Button mBtnFoundDevices ;
    private static final int MESSAGE_REFRESH = 101;
    private static final long REFRESH_TIMEOUT_MILLIS = 5000;

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_REFRESH:
                    refreshDeviceList();

                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }

    };

    public final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbDevice device = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);

                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if(device != null){

                            //text.append(device.toString());
                            //显示跳转按钮
                            mBtnFoundDevices.setVisibility(View.VISIBLE);
                        }else{

                        }
                    }
                    else {

                        //添加提示消息
                        mTextDevices.append(getResources().getString(R.string.prompt1));

                        Log.d(TAG, "permission denied for device " + device);
                    }
                }
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //USB管理器
        mUsbManager = (UsbManager)getSystemService(Context.USB_SERVICE);

        mIntent = this.getIntent();

        initView();

        //开始查找USB设备
        mHandler.sendEmptyMessage(MESSAGE_REFRESH);

        //注册监听
        mPermissionIntent = PendingIntent.getBroadcast(ScrollingMainActivity.this, 0, new Intent(ACTION_USB_PERMISSION), 0);
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_USB_PERMISSION);
        registerReceiver(mUsbReceiver, filter);

    }

    //初始化控件
    private void initView() {
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mTextDevices = (TextView) findViewById(R.id.progressBarTitle);
        mBtnFoundDevices = (Button) findViewById(R.id.id_start_test);
        mBtnFoundDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ScrollingMainActivity.this,MainActivity.class));
            }
        });
        mBtnFoundDevices.setVisibility(View.GONE);

        //用来测试
        findViewById(R.id.id_start_test4hyf).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ScrollingMainActivity.this,MainActivity.class));
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

       unregisterReceiver(mUsbReceiver);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //unregisterReceiver(mUsbReceiver);
    }

    private void refreshDeviceList() {
        showProgressBar();

        new AsyncTask<Void, Void, List<UsbSerialPort>>() {
            @Override
            protected List<UsbSerialPort> doInBackground(Void... params) {
                Log.d(TAG, "Refreshing device list ...");
                SystemClock.sleep(1000);

                final List<UsbSerialDriver> drivers =
                        UsbSerialProber.getDefaultProber().findAllDrivers(mUsbManager);

                final List<UsbSerialPort> result = new ArrayList<UsbSerialPort>();
                for (final UsbSerialDriver driver : drivers) {
                    final List<UsbSerialPort> ports = driver.getPorts();
                    Log.d(TAG, String.format("+ %s: %s port%s",
                            driver, Integer.valueOf(ports.size()), ports.size() == 1 ? "" : "s"));
                    result.addAll(ports);
                }

                return result;
            }

            @Override
            protected void onPostExecute(List<UsbSerialPort> result) {
                if(result.size()!=0){
                    mTextDevices.setText(
                            String.format("%s device(s) found", Integer.valueOf(result.size())));

                    //配置USB设备端口
                    MyUSBDevice.setsPort(result.get(0));

                    //判断是否拥有USB读取权限
                    if(getUSBPermission(mIntent)){
                        //显示跳转按钮
                        mBtnFoundDevices.setVisibility(View.VISIBLE);
                    }else{
                        mUsbManager.requestPermission(result.get(0).getDriver().getDevice(), mPermissionIntent);
                        //添加提示消息
                        //mTextDevices.append(getResources().getString(R.string.prompt1));
                    }

                    Log.d(TAG, "Done refreshing, " + result.size() + " entries found.");
                }else{
                    mTextDevices.setText(
                            String.format("%s device(s) found", Integer.valueOf(result.size())));
                    //添加提示消息
                    mTextDevices.append(getResources().getString(R.string.prompt));

                }
                hideProgressBar();
            }

        }.execute((Void) null);
    }


    //判断是否拥有USB读取权限
    private boolean getUSBPermission(Intent intent) {
        return intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false);
    }



    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
        mTextDevices.setText("正在查找设备。。。");
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }
}
