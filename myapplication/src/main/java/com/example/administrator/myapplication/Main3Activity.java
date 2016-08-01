package com.example.administrator.myapplication;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;


import com.example.administrator.myapplication.adpters.MyAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main3Activity extends AppCompatActivity {

    private RecyclerView
            enviromentRecycleView,
            foodRecycleView,
            personRecycleView,
            medicalRecycleView,
            materialRecycleView,
            nanobioRecycleView,
            nanophotoRecycleView ;

    //每个列表对应的数据
    private List<HashMap<String,Object>> data4environment = new ArrayList<>();
    private List<HashMap<String,Object>> data4food = new ArrayList<>();
    private List<HashMap<String,Object>> data4person = new ArrayList<>();
    private List<HashMap<String,Object>> data4medical = new ArrayList<>();
    private List<HashMap<String,Object>> data4material = new ArrayList<>();
    private List<HashMap<String,Object>> data4nanobio = new ArrayList<>();
    private List<HashMap<String,Object>> data4nanophoto = new ArrayList<>();

    //每个列表对应的视图适配器
    private MyAdapter adapter ; //普通适配器
    private MyAdapter adapter4food ;
    private MyAdapter adapter4person ;
    private MyAdapter adapter4medical;
    private MyAdapter adapter4material ;
    private MyAdapter adapter4nanobio ;
    private MyAdapter adapter4nanophoto ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        initView();
        iniDatas();
        showEnvironmentList();
        showFoodList();
        showPersonList();
        showMedicalList();
        showMaterialList();
        showNanobioList();
        showNanophotoList();

    }

    //设置EnvironmentList()
    private void showEnvironmentList() {
        //设置Adapter
        adapter = new MyAdapter(getApplicationContext(),data4environment);
        enviromentRecycleView.setAdapter(adapter);
        //设置布局样式
        LinearLayoutManager linearLayoutManager  = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        enviromentRecycleView.setLayoutManager(linearLayoutManager);
        //设置监听
        adapter.setOnItemListener(new MyAdapter.OnItemListener() {
            @Override
            public void onClickListerner(View view, int pos) {
                // Toast.makeText(getApplicationContext(),dataLists.get(pos),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClickListerner(View view, int pos) {
                startActivity(new Intent(Main3Activity.this, Main31Activity.class));
            }
        });
    }

    //设置FoodList
    private void showFoodList() {
        //设置Adapter
        adapter4food = new MyAdapter(getApplicationContext(),data4food);
        foodRecycleView.setAdapter(adapter4food);

        //设置布局样式
        LinearLayoutManager linearLayoutManager  = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL,false);
        foodRecycleView.setLayoutManager(linearLayoutManager);
        //设置监听
        adapter4food.setOnItemListener(new MyAdapter.OnItemListener() {
            @Override
            public void onClickListerner(View view, int pos) {
                // Toast.makeText(getApplicationContext(),dataLists.get(pos),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClickListerner(View view, int pos) {
                //Toast.makeText(getApplicationContext(),data4environment.get(pos)+"good",Toast.LENGTH_SHORT).show();
               startActivity(new Intent(Main3Activity.this, Main31Activity.class));

            }
        });
    }

        //设置PersonList
    private void showPersonList() {
        //设置Adapter
        adapter4person = new MyAdapter(getApplicationContext(),data4person);
        personRecycleView.setAdapter(adapter4person);

        //设置布局样式
        LinearLayoutManager linearLayoutManager  = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL,false);
        personRecycleView.setLayoutManager(linearLayoutManager);
        //设置监听
        adapter4person.setOnItemListener(new MyAdapter.OnItemListener() {
            @Override
            public void onClickListerner(View view, int pos) {
                // Toast.makeText(getApplicationContext(),dataLists.get(pos),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClickListerner(View view, int pos) {
                //Toast.makeText(getApplicationContext(),data4environment.get(pos)+"good",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Main3Activity.this, Main31Activity.class));

            }
        });
    }
    //设置MedicalList
    private void showMedicalList() {
        //设置Adapter
        adapter4medical = new MyAdapter(getApplicationContext(),data4medical);
        medicalRecycleView.setAdapter(adapter4medical);

        //设置布局样式
        LinearLayoutManager linearLayoutManager  = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL,false);
        medicalRecycleView.setLayoutManager(linearLayoutManager);
        //设置监听
        adapter4medical.setOnItemListener(new MyAdapter.OnItemListener() {
            @Override
            public void onClickListerner(View view, int pos) {
                // Toast.makeText(getApplicationContext(),dataLists.get(pos),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClickListerner(View view, int pos) {
                //Toast.makeText(getApplicationContext(),data4environment.get(pos)+"good",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Main3Activity.this, Main31Activity.class));
            }
        });
    }


    //设置MaterialList
    private void showMaterialList() {
        //设置Adapter
        adapter4material = new MyAdapter(getApplicationContext(),data4material);
        materialRecycleView.setAdapter(adapter4material);

        //设置布局样式
        LinearLayoutManager linearLayoutManager  = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL,false);
        materialRecycleView.setLayoutManager(linearLayoutManager);
        //设置监听
        adapter4material.setOnItemListener(new MyAdapter.OnItemListener() {
            @Override
            public void onClickListerner(View view, int pos) {
                // Toast.makeText(getApplicationContext(),dataLists.get(pos),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClickListerner(View view, int pos) {
                //Toast.makeText(getApplicationContext(),data4environment.get(pos)+"good",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Main3Activity.this, Main31Activity.class));

            }
        });
    }

        //设置NanobioList
    private void showNanobioList() {
        //设置Adapter
        adapter4nanobio = new MyAdapter(getApplicationContext(),data4nanobio);
        nanobioRecycleView.setAdapter(adapter4nanobio);

        //设置布局样式
        LinearLayoutManager linearLayoutManager  = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL,false);
        nanobioRecycleView.setLayoutManager(linearLayoutManager);
        //设置监听
        adapter4nanobio.setOnItemListener(new MyAdapter.OnItemListener() {
            @Override
            public void onClickListerner(View view, int pos) {
                // Toast.makeText(getApplicationContext(),dataLists.get(pos),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClickListerner(View view, int pos) {
                //Toast.makeText(getApplicationContext(),data4environment.get(pos)+"good",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Main3Activity.this, Main31Activity.class));

            }
        });
    }


            //设置NanophotoList
    private void showNanophotoList() {
        //设置Adapter
        adapter4nanophoto = new MyAdapter(getApplicationContext(),data4nanophoto);
        nanophotoRecycleView.setAdapter(adapter4nanophoto);

        //设置布局样式
        LinearLayoutManager linearLayoutManager  = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL,false);
        nanophotoRecycleView.setLayoutManager(linearLayoutManager);
        //设置监听
        adapter4nanophoto.setOnItemListener(new MyAdapter.OnItemListener() {
            @Override
            public void onClickListerner(View view, int pos) {
                // Toast.makeText(getApplicationContext(),dataLists.get(pos),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClickListerner(View view, int pos) {
                //Toast.makeText(getApplicationContext(),data4environment.get(pos)+"good",Toast.LENGTH_SHORT).show();
              startActivity(new Intent(Main3Activity.this, Main31Activity.class));

            }
        });
    }


    //每个列表度应的数据
    private void iniDatas() {

        //环境列表数据
        String[] environment = {"Air","Water","Voc","PM2.5","Pollen","Bacteria","Virus"};
//        int[]  environmentImage = {R.drawable.chart,R.drawable.img01,
//                R.drawable.chart,R.drawable.chart,
//                R.drawable.img01,R.drawable.chart,R.drawable.chart};
        int[]  environmentImage = {R.mipmap.chart,R.mipmap.img01,
                R.mipmap.chart,R.mipmap.chart,
                R.mipmap.img01,R.mipmap.chart,R.mipmap.chart};
        for (int i=0;i<environment.length&i<environmentImage.length;i++){
            HashMap<String,Object> map = new HashMap<>();
            map.put("NAME",environment[i]);
            map.put("IMAGE",environmentImage[i]);
            data4environment.add(map);
        }

        //food列表数据
        String[] food = {"Air","Water","Voc","PM2.5","Pollen","Bacteria","Virus"};
        int[]  foodImage = {R.mipmap.chart,R.mipmap.img01,
                R.mipmap.chart,R.mipmap.chart,
                R.mipmap.img01,R.mipmap.chart,R.mipmap.chart};
        for (int i=0;i<environment.length&i<environmentImage.length;i++){
            HashMap<String,Object> map = new HashMap<>();
            map.put("NAME",environment[i]);
            map.put("IMAGE",environmentImage[i]);
            data4food.add(map);
        }

        //person列表数据
        String[] person = {"Air","Water","Voc","PM2.5","Pollen","Bacteria","Virus"};
        int[]  personImage = {R.mipmap.chart,R.mipmap.img01,
                R.mipmap.chart,R.mipmap.chart,
                R.mipmap.img01,R.mipmap.chart,R.mipmap.chart};
        for (int i=0;i<person.length&i<personImage.length;i++){
            HashMap<String,Object> map = new HashMap<>();
            map.put("NAME",person[i]);
            map.put("IMAGE",personImage[i]);
            data4person.add(map);
        }

         //medical列表数据
        String[] medical = {"Air","Water","Voc","PM2.5","Pollen","Bacteria","Virus"};
        int[]  medicalImage = {R.mipmap.chart,R.mipmap.img01,
                R.mipmap.chart,R.mipmap.chart,
                R.mipmap.img01,R.mipmap.chart,R.mipmap.chart};
        for (int i=0;i<medical.length&i<medicalImage.length;i++){
            HashMap<String,Object> map = new HashMap<>();
            map.put("NAME",medical[i]);
            map.put("IMAGE",medicalImage[i]);
            data4medical.add(map);
        }

        //material列表数据
        String[] material = {"Air","Water","Voc","PM2.5","Pollen","Bacteria","Virus"};
        int[]  materialImage = {R.mipmap.chart,R.mipmap.img01,
                R.mipmap.chart,R.mipmap.chart,
                R.mipmap.img01,R.mipmap.chart,R.mipmap.chart};
        for (int i=0;i<material.length&i<materialImage.length;i++){
            HashMap<String,Object> map = new HashMap<>();
            map.put("NAME",material[i]);
            map.put("IMAGE",materialImage[i]);
            data4material.add(map);
        }

                 //nanobio列表数据
        String[] nanobio = {"Air","Water","Voc","PM2.5","Pollen","Bacteria","Virus"};
        int[]  nanobioImage = {R.mipmap.chart,R.mipmap.img01,
                R.mipmap.chart,R.mipmap.chart,
                R.mipmap.img01,R.mipmap.chart,R.mipmap.chart};
        for (int i=0;i<nanobio.length&i<nanobioImage.length;i++){
            HashMap<String,Object> map = new HashMap<>();
            map.put("NAME",nanobio[i]);
            map.put("IMAGE",nanobioImage[i]);
            data4nanobio.add(map);
        }

        //nanophoto列表数据
        String[] nanophoto = {"Air","Water","Voc","PM2.5","Pollen","Bacteria","Virus"};
        int[]  nanophotoImage = {R.mipmap.chart,R.mipmap.img01,
                R.mipmap.chart,R.mipmap.chart,
                R.mipmap.img01,R.mipmap.chart,R.mipmap.chart};
        for (int i=0;i<nanophoto.length&i<nanophotoImage.length;i++){
            HashMap<String,Object> map = new HashMap<>();
            map.put("NAME",nanophoto[i]);
            map.put("IMAGE",nanophotoImage[i]);
            data4nanophoto.add(map);
        }
    }

    //所有的列表视图
    private void initView() {

        //环境列表视图
        enviromentRecycleView = (RecyclerView) findViewById(R.id.environment);

        //
        foodRecycleView = (RecyclerView) findViewById(R.id.food);


        personRecycleView= (RecyclerView) findViewById(R.id.person);


        medicalRecycleView= (RecyclerView) findViewById(R.id.medical);


        materialRecycleView= (RecyclerView) findViewById(R.id.material);


        nanobioRecycleView= (RecyclerView) findViewById(R.id.nanobio);

        nanophotoRecycleView= (RecyclerView) findViewById(R.id.nanophoto);
    }

}
