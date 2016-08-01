package com.example.administrator.myapplication;




import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;


import com.example.administrator.myapplication.adpters.MyAdapter;
import com.example.administrator.myapplication.adpters.SecondAdapter;
import com.example.administrator.myapplication.chartview.PieChart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main41Activity extends AppCompatActivity {


	private RecyclerView unknowRecyvlerView, airRecyclerView ;

    //显示饼状图的区域
    private FrameLayout showPieChartView ;

    //每个列表对应的数据
    private List<HashMap<String,Object>> data4unknow = new ArrayList<>();
    private List<HashMap<String,Object>> data4air = new ArrayList<>();

    //每个列表对应的视图适配器
    private MyAdapter adapter ; 
    private SecondAdapter adapter4air ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main41);

        initView();

        iniDataList();

        showRecyclerViewList();

        showPieChart();
    }



    //初始化控件
    private void initView(){

    	unknowRecyvlerView = (RecyclerView)findViewById(R.id.id_unknown);

    	airRecyclerView = (RecyclerView)findViewById(R.id.id_air_qulquantity);

        showPieChartView = (FrameLayout) findViewById(R.id.piechart);

    }

    //显示饼状图
    private void showPieChart() {
        showPieChartView.removeAllViews();
        showPieChartView.addView(new PieChart().execute(getApplicationContext()));
    }

    //初始化数据
    private void iniDataList(){

    	//环境列表数据
        String[] unknow = {"PM","Carbon","PAH","Bacteria","Virus","A","B"};
        int[]  unknowImage = {R.mipmap.chart,R.mipmap.img01,
                R.mipmap.chart,R.mipmap.chart,
                R.mipmap.img01,R.mipmap.chart,R.mipmap.chart};
                
        for (int i=0;i<unknow.length&i<unknowImage.length;i++){
            HashMap<String,Object> map = new HashMap<>();
            map.put("NAME",unknow[i]);
            map.put("IMAGE",unknowImage[i]);
            data4unknow.add(map);
        }

        //food列表数据
        String[] airName = {"PM2.5","PM10","SO2","NO2","O3","CO2","TVOCs",
    						"Formalde","Bacteria","Virus","Pollen","Spore","UV","Temp"};
    	String[] airIndex = {"123.1","47","21.3","334","2345","78.9","23.9",
    						"90.8","89.2","8E14","167.2","89","128.9","90.2"};
        

        for (int i=0;i<airName.length&i<airIndex.length;i++){
            HashMap<String,Object> map = new HashMap<>();
            map.put("NAME",airName[i]);
            map.put("INDEX",airIndex[i]);
            data4air.add(map);
        }

    }


    //
    private void showRecyclerViewList(){

    	//
    	//设置Adapter
        adapter = new MyAdapter(getApplicationContext(),data4unknow);
        unknowRecyvlerView.setAdapter(adapter);
        //设置布局样式
        LinearLayoutManager linearLayoutManager  = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        unknowRecyvlerView.setLayoutManager(linearLayoutManager);
        //设置监听
        adapter.setOnItemListener(new MyAdapter.OnItemListener() {
            @Override
            public void onClickListerner(View view, int pos) {
                // Toast.makeText(getApplicationContext(),dataLists.get(pos),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClickListerner(View view, int pos) {
                Toast.makeText(getApplicationContext(),(String)data4unknow.get(pos).get("Name"),Toast.LENGTH_SHORT).show();
            }
        });


    	//
    	//设置Adapter
        adapter4air = new SecondAdapter(getApplicationContext(),data4air);
        airRecyclerView.setAdapter(adapter4air);
        //设置布局样式
        LinearLayoutManager linearLayoutManager02  = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        airRecyclerView.setLayoutManager(linearLayoutManager02);
        //设置监听
        adapter4air.setOnItemListener(new SecondAdapter.OnItemListener() {
            @Override
            public void onClickListerner(View view, int pos) {
                // Toast.makeText(getApplicationContext(),dataLists.get(pos),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClickListerner(View view, int pos) {
                Toast.makeText(getApplicationContext(),(String)data4air.get(pos).get("INDEX"),Toast.LENGTH_SHORT).show();
            }
        });
    }

}



