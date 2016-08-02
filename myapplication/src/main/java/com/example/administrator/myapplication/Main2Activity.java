package com.example.administrator.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.myapplication.chartview.NumberPickerDialog;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main2Activity extends AppCompatActivity {

   private  String nowstr ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void main2Btn(View view){

        setValues(view.getId());

        switch(view.getId()){

            case R.id.id_excting:
                //setValues();
                break;
            case R.id.id_emission:
                break;
            case R.id.integration:
                break;
            case R.id.gain:
                break;
            case R.id.mode:
                break;
            case R.id.wave:
            break;

        }
    }

    private void setValues(final int id) {

        String buttonString = (String) ((Button)findViewById(id)).getText();

        //提取字符串中的数字
        Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?");
        Matcher matcherNumber = pattern.matcher(buttonString);
        String nowNumber = "0";
        if(matcherNumber.find()){
            nowNumber = matcherNumber.group(0);
        }

        //提取字符串中的文字
//提取字符串中的数字
        Pattern pattern2 = Pattern.compile("[a-zA-Z]*");
        Matcher matcherstr = pattern2.matcher(buttonString);
        nowstr = "";
        if(matcherstr.find()){
            nowstr = matcherstr.group();
        }

//        NumberPickerDialog.OnMyNumberSetListener listener = new NumberPickerDialog.OnMyNumberSetListener() {
//            @Override
//            public void onNumberSet(String number, int mode) {
//
//                ((Button)findViewById(id)).append(number);
//
//            }
//        };
        new NumberPickerDialog(this, new NumberPickerDialog.OnMyNumberSetListener() {
            @Override
            public void onNumberSet(String number, int mode) {
                ((Button)findViewById(id)).setText(nowstr+number);
                Toast.makeText(Main2Activity.this, number, Toast.LENGTH_SHORT).show();
            }
        }, nowNumber, 0).show();

    }

}
