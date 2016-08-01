package com.example.administrator.lightspec.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.example.administrator.lightspec.R;
import com.example.administrator.lightspec.beans.MyUSBDevice;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * create an instance of this fragment.
 */
public class FragmentSetting extends Fragment {


    /**
     *控件
     */
    private View mView ;
    private RadioGroup radioGroupBount, radioGroupStrTail ;

    // Required empty public constructor
    public FragmentSetting() {}



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_fragment_setting, container, false);
        radioGroupBount = (RadioGroup) mView.findViewById(R.id.id_settingBountrate);
        radioGroupStrTail = (RadioGroup) mView.findViewById(R.id.id_settingBStrTail);

        int bountrate = MyUSBDevice.getBaudRate();
        if(bountrate==57600){
            radioGroupBount.check(R.id.id_bount57600);
        }else if (bountrate==115200){
            radioGroupBount.check(R.id.id_bount115200);
        }else if(bountrate == 38400){
            radioGroupBount.check(R.id.id_bount38400);
        }else if(bountrate == 921600){
            radioGroupBount.check(R.id.id_bount921600);
        }

        String strTail = MyUSBDevice.getTail();
        if("\n".equals(strTail)){
            radioGroupStrTail.check(R.id.id_strTail_1);
        }else if("\r".equals(strTail)){
            radioGroupStrTail.check(R.id.id_strTail_2);
        }

        radioGroupBount.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(checkedId == R.id.id_bount57600){
                    MyUSBDevice.setBaudRate(57600);
                }else if(checkedId == R.id.id_bount115200){
                    MyUSBDevice.setBaudRate(115200);
                }else if(checkedId == R.id.id_bount38400){
                    MyUSBDevice.setBaudRate(38400);
                }else if(checkedId == R.id.id_bount921600){
                    MyUSBDevice.setBaudRate(921600);
                }
            }
        });

        radioGroupStrTail.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.id_strTail_1){
                    MyUSBDevice.setTail("\n");
                }else if(checkedId == R.id.id_strTail_2){
                    MyUSBDevice.setTail("\r\n");
                }
            }
        });
        return mView ;
    }



}
