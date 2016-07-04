package com.example.administrator.lightspec.fragments;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.administrator.lightspec.R;
import com.example.administrator.lightspec.beans.MyUSBDevice;
import com.hoho.android.usbserial.driver.UsbSerialPort;
import com.hoho.android.usbserial.util.SerialInputOutputManager;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_showUSBDatas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_showUSBDatas extends Fragment {

    //
    private static final String CMD = "command";

    //
    private static final int SENDCMD = 0x990;
    private static final int SHOWDATA = 0x991;
    private static final String TAG = "houyafei";

    //端口号
    private static UsbSerialPort sPort = null;
    // 获取的字符命令
    private String mCmd;
    private PendingIntent mPermissionIntent;

    //控件
    private View mView;
    private Button mBntCleardata;
    private RadioGroup mRadioGroup;
    private TextView mTextMsg;
    private ScrollView mScrollView;

    private enum ShowMode {
        SHOWHEXONLY, SHOWASCIIONLY, SHOWALL;
    }

    private ShowMode radioChoice = ShowMode.SHOWALL;
    //
    private byte[] mData;

    //Handler
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SENDCMD:
                    sendCmdMsg(mCmd);
                    break;
                case SHOWDATA:
                    updateReceivedData(mData);
                    break;
            }

        }
    };


    private final ExecutorService mExecutor = Executors.newSingleThreadExecutor();

    private SerialInputOutputManager mSerialIoManager;


    private final SerialInputOutputManager.Listener mListener =
            new SerialInputOutputManager.Listener() {

                @Override
                public void onRunError(Exception e) {
                    Log.d(TAG, "Runner stopped.");
                }

                @Override
                public void onNewData(final byte[] data) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateReceivedData(data);
//                            Toast.makeText(getContext(), "有数据显示", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            };


    private static final String ACTION_USB_PERMISSION =
            "com.android.example.USB_PERMISSION";

    public final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbDevice device = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);

                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if(device != null){
                            openUSBdevice();
                            //text.append(device.toString());
                        }else{
                        }
                    }
                    else {
                        Log.d(TAG, "permission denied for device " + device);
                    }
                }
            }
        }
    };



    public Fragment_showUSBDatas() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @param cmd Parameter 1.
     * @return A new instance of fragment Fragment_showUSBDatas.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_showUSBDatas newInstance(String cmd) {
        Fragment_showUSBDatas fragment = new Fragment_showUSBDatas();
        Bundle args = new Bundle();
        args.putString(CMD, cmd);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCmd = getArguments().getString(CMD);

        }
        sPort = MyUSBDevice.getsPort();

        //注册监听
        mPermissionIntent = PendingIntent.getBroadcast(getActivity(), 0, new Intent(ACTION_USB_PERMISSION), 0);
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_USB_PERMISSION);
        getActivity().registerReceiver(mUsbReceiver, filter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_fragment_show_usbdatas, container, false);

        mBntCleardata = (Button) mView.findViewById(R.id.cleardata);
        mRadioGroup = (RadioGroup) mView.findViewById(R.id.id_RadioGroup);
        mTextMsg = (TextView) mView.findViewById(R.id.showMsg);

        mScrollView = (ScrollView) mView.findViewById(R.id.id_scrollView);

        mBntCleardata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextMsg.setText("");
            }
        });

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.id_radioHex:
                        radioChoice = ShowMode.SHOWHEXONLY;
                        break;
                    case R.id.id_radioasc:
                        radioChoice = ShowMode.SHOWASCIIONLY;
                        break;
                    case R.id.id_radioshowAll:
                        radioChoice = ShowMode.SHOWALL;
                        break;
                }


            }
        });

        return mView ;
    }


    @Override
    public void onPause() {
        super.onPause();
        stopIoManager();
        if (sPort != null) {
            try {
                sPort.close();
            } catch (IOException e) {
                // Ignore.
            }
            sPort = null;
        }

        getActivity().unregisterReceiver(mUsbReceiver);
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "Resumed, port=" + sPort);
        openUSBdevice();
        sendCmdMsg(mCmd);
    }

    private void openUSBdevice() {
        sPort = MyUSBDevice.getsPort();
        if (sPort == null) {
            mTextMsg.setText("No serial device.");
        } else {
            final UsbManager usbManager = (UsbManager) getActivity().getSystemService(Context.USB_SERVICE);

            UsbDeviceConnection connection = usbManager.openDevice(sPort.getDriver().getDevice());
            if (connection == null) {
                mTextMsg.setText("Opening device failed");
                usbManager.requestPermission(sPort.getDriver().getDevice(), mPermissionIntent);
                return;
            }

            try {
                sPort.open(connection);
                //蓝牙波特率57600
                //光谱检测设备波特率115200
                sPort.setParameters(MyUSBDevice.getBaudRate(), 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE);



            } catch (IOException e) {
                Log.e(TAG, "Error setting up device: " + e.getMessage(), e);
                mTextMsg.setText("Error opening device: " + e.getMessage());
                try {
                    sPort.close();
                } catch (IOException e2) {
                    // Ignore.
                }
                sPort = null;
                return;
            }
            mTextMsg.setText("Serial device: " + sPort.getClass().getSimpleName());
        }

        onDeviceStateChange();
    }

    private void stopIoManager() {
        if (mSerialIoManager != null) {
            Log.i(TAG, "Stopping io manager ..");
            mSerialIoManager.stop();
            mSerialIoManager = null;
        }
    }

    private void startIoManager() {
        if (sPort != null) {
            Log.i(TAG, "Starting io manager ..");
            mSerialIoManager = new SerialInputOutputManager(sPort, mListener);
            mExecutor.submit(mSerialIoManager);
        }
    }

    private void onDeviceStateChange() {
        stopIoManager();
        startIoManager();
    }


    /**
     * 发送命令
     * @param mCmd
     */
    private void sendCmdMsg(String mCmd) {
        if (sPort != null) {
            Log.i(TAG, "Starting io manager ..");
            try {
                int len = sPort.write(mCmd.getBytes(),100);
                Log.i(TAG, "发送数据成功:" + len);
                //Toast.makeText(getContext(),"发送数据成功:"+len,Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

    private void updateReceivedData(byte[] data) {
//        final String message = "Read " + data.length + " bytes: \n"
//                + HexDump.dumpHexString(data) + "\n\n";
        String msgHex =  bytes2hexString(data) ;
        String msgAsc =  hex2String(data) ;
        switch(radioChoice){
            case SHOWALL:
                mTextMsg.append(msgAsc+"\n"+msgHex+"\n");
                break;
            case SHOWASCIIONLY:
                mTextMsg.append(msgAsc+"\n");
                break;
            case SHOWHEXONLY:
                mTextMsg.append(msgHex+"\n");
                break;
        }
        mScrollView.smoothScrollTo(0, mTextMsg.getBottom());
    }


    /**
     * Starts the activity, using the supplied driver instance.
     *
     * @param context
     * @param port
     */
    public static void show(Context context, UsbSerialPort port,String mCmd) {
        sPort = port;
        ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, Fragment_showUSBDatas.newInstance(mCmd)) // 取代原来Fragment的类
                .commit();
    }

    public String bytes2hexString(byte[] data){
        final StringBuilder stringBuilder = new StringBuilder(data.length);
        for(byte byteChar : data)
            stringBuilder.append(String.format("%02X ", byteChar));
        return stringBuilder.toString();
    }

    public String hex2String(byte[] data){
        return new String(data);
    }

}
