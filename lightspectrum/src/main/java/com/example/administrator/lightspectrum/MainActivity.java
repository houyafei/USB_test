package com.example.administrator.lightspectrum;

import android.content.Context;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialPort;
import com.hoho.android.usbserial.driver.UsbSerialProber;

import java.io.IOException;
import java.util.List;



public class MainActivity extends AppCompatActivity {

    private UsbSerialPort port ;

    private Button btn ;
    private TextView textView;

    private static final int ReadData = 0x990;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            if(msg.what==ReadData){
                readData();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.id_conent);
        btn.setText("发送命令：*mear:dark 500 1 7");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    senCmd();
            }
        });

        // Find all available drivers from attached devices.
        UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);
        List<UsbSerialDriver> availableDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(manager);
        if (availableDrivers.isEmpty()) {
            return;
        }

// Open a connection to the first available driver.
        UsbSerialDriver driver = availableDrivers.get(0);
        UsbDeviceConnection connection = manager.openDevice(driver.getDevice());
        if (connection == null) {
            // You probably need to call UsbManager.requestPermission(driver.getDevice(), ..)
            return;
        }

// Read some data! Most have just one port (port 0).
        List<UsbSerialPort> ports = driver.getPorts();
        port = ports.get(0);
        try {
            port.open(connection);
        } catch (IOException e) {
            e.printStackTrace();
        }
        readData();

    }

    private void readData() {
        try {
            port.setParameters(115200, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE);
            byte buffer[] = new byte[999999];
            int numBytesRead = port.read(buffer, 1000);

            final String msgAsc = new String(buffer,0,numBytesRead) ;
            textView.post(new Runnable() {
                @Override
                public void run() {
                    textView.setText(msgAsc);
                }
            });
        } catch (IOException e) {
            // Deal with error.
        } finally {
           // port.close();
        }
    }

    private void senCmd() {
        if (port!=null){
            try {
               int len = port.write("*mear:dark 500 1 7\r\n".getBytes(),200);
                if(len>0){
                    mHandler.sendEmptyMessage(ReadData);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String hex2String(byte[] data){
        return new String(data);
    }
}
