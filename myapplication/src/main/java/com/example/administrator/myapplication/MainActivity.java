package com.example.administrator.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication.chartview.LineChart;
import com.example.administrator.myapplication.usb.ConstantCMD;
import com.example.administrator.myapplication.usb.MyUSBDevice;
import com.example.administrator.myapplication.util.ImageDataSaveUtil;
import com.hoho.android.usbserial.driver.UsbSerialPort;
import com.hoho.android.usbserial.util.SerialInputOutputManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "houyafei";


    private TextView mReport ;
    private FrameLayout mFramelayout;
    private ImageView mImageView ;

    private int mCmdNum ;

    //图片存储的位置
    private Uri mOutPutFileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mReport = (TextView) findViewById(R.id.id_report);
        mFramelayout = (FrameLayout) findViewById(R.id.is_chart);
        mImageView  = (ImageView) findViewById(R.id.id_image);
    }

    /*
      所有按钮的控制
     */
    public void btnFuntion(View view){

        switch(view.getId()){
            case R.id.id_spectra:
                mCmdNum = 5 ;
                sendCmdMsg(ConstantCMD.CMD_5);
                break;
            case R.id.id_Photo:
                openCamera();


                //startActivityForResult(intent,1);
                break;
            case R.id.id_spectroscopy:
                startActivity(new Intent(MainActivity.this,Main2Activity.class));
                break;

            case R.id.id_scenario:
                startActivity(new Intent(MainActivity.this,Main4Activity.class));
                break;
                
            case R.id.id_standard:
                startActivity(new Intent(MainActivity.this,Main3Activity.class));
                break;

        }

    }

    //打开相机拍照
    private void openCamera() {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

        //文件夹light
        String path = Environment.getExternalStorageDirectory().toString()+"/light";
        File path1 = new File(path);
        if(!path1.exists()){
            path1.mkdirs();
        }
        File file = new File(path1,System.currentTimeMillis()+".jpg");
        mOutPutFileUri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mOutPutFileUri);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

            if (requestCode == 1) {

               mImageView.setImageBitmap(BitmapFactory.decodeFile(mOutPutFileUri.getPath()));
               //扫描图库
                Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                scanIntent.setData(Uri.fromFile(new File(mOutPutFileUri.getPath())));
                getBaseContext().sendBroadcast(scanIntent);

            }

    }

    @Override
    protected void onResume() {
        super.onResume();
        openUSBdevice();
    }


    //***************发送命令 获取数据***********************

    //端口号
    private static UsbSerialPort sPort = null;

    private final ExecutorService mExecutor = Executors.newSingleThreadExecutor();

    private SerialInputOutputManager mSerialIoManager;


    private final SerialInputOutputManager.Listener mListener =
            new SerialInputOutputManager.Listener() {

                @Override
                public void onRunError(Exception e) {
                    Log.d(TAG, "MainAcitity + Runner stopped.");
                }

                @Override
                public void onNewData(final byte[] data) {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateReceivedData(data);
                        }
                    });
                }
            };


    private void openUSBdevice() {
        sPort = MyUSBDevice.getsPort();
        if (sPort == null) {
            Toast.makeText(MainActivity.this, "No serial device.", Toast.LENGTH_SHORT).show();

        } else {
            final UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);

            UsbDeviceConnection connection = usbManager.openDevice(sPort.getDriver().getDevice());
            if (connection == null) {
                Toast.makeText(MainActivity.this, "Opening device failed", Toast.LENGTH_SHORT).show();

            }

            try {
                sPort.open(connection);
                //蓝牙波特率57600
                //光谱检测设备波特率115200
                sPort.setParameters(115200, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE);



            } catch (IOException e) {
                Log.e(TAG, "Error setting up device: " + e.getMessage(), e);
                try {
                    sPort.close();
                } catch (IOException e2) {
                    // Ignore.
                }
                sPort = null;
                return;
            }
           // mTextMsg.setText("Serial device: " + sPort.getClass().getSimpleName());
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
    private enum ShowMode {
        SHOWHEXONLY, SHOWASCIIONLY, SHOWALL;
    }

    private ShowMode radioChoice = ShowMode.SHOWALL;


    private ArrayList<Byte> dataList = new ArrayList<>();

    private void updateReceivedData(byte[] data) {
//        final String message = "Read " + data.length + " bytes: \n"
//                + HexDump.dumpHexString(data) + "\n\n";
        for (byte bytedata:data) {
            dataList.add(bytedata);
        }

        if(dataList.size()>1000 && (mCmdNum==5|mCmdNum==3|mCmdNum==6)){

                mFramelayout.removeAllViews();
            mFramelayout.addView(new LineChart(MainActivity.this, dataList).getView());
            dataList.clear();

        }else if(dataList.size()>300 ){
            String msgAsc =  hex2String(data) ;
            if(mCmdNum==2){
                ImageDataSaveUtil.saveLogData(msgAsc+"\n","CMD_2");
            }else if(mCmdNum==4){
                ImageDataSaveUtil.saveLogData(msgAsc+"\n","CMD_4");
            }else if(mCmdNum==7){
                ImageDataSaveUtil.saveLogData(msgAsc+"\n","CMD_7");
            }
            mReport.append(msgAsc+"\n");
        }

       // mScrollView.smoothScrollTo(0, mTextMsg.getBottom());
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
