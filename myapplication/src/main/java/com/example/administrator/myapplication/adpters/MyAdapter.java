package com.example.administrator.myapplication.adpters;

/**
 * Created by 310054792 on 2016/7/27.
        */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
        * 微信：yafeihou
        * Created by 侯亚飞 on 2016/6/5.
        */
public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {


    private LayoutInflater inflater ;

    private Context context ;




    private List<HashMap<String,Object>> dataList ;

    /**
     *用于设置监听的接口
     */
    public interface OnItemListener{
        void onClickListerner(View view, int pos);
        void onLongClickListerner(View view, int pos);
    }

    //定义一个接口
    private OnItemListener onItemListener ;
    //供外部使用的接口类
    public void setOnItemListener(OnItemListener onItemListener){
        this.onItemListener = onItemListener;
    }

    public MyAdapter(Context context, List<HashMap<String,Object>> dataList) {
        this.context = context ;

        this.dataList = dataList;

        inflater = LayoutInflater.from(context);
    }

//    public MyAdapter(Context context, ArrayList<String> datas) {
//        this.context = context ;
//
//        this.datas = datas;
//
//        inflater = LayoutInflater.from(context);
//    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建item 布局！！！！！！！！！！！！！！！！
        View view = inflater.inflate(R.layout.item_layout,parent,false);
        //
        MyViewHolder holder = new MyViewHolder(view);
        return holder ;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        //给每个控件设置值
       // holder.textView.setText(datas.get(position));

        //设置每一项内容
        HashMap<String,Object> dataMap = dataList.get(position);
        holder.textView.setText((String) dataMap.get("NAME"));
        holder.imageView.setImageResource((Integer) dataMap.get("IMAGE"));

        //在这里实现控件的
        if (onItemListener!=null){
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position =  holder.getLayoutPosition();
                    onItemListener.onClickListerner(holder.textView,position);
                }
            });

            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition(); ///!!!!!!!!!!!!!!!!!!!!!!!用这个获取所点击的item的位置
                    //调用接口中的方法
                    onItemListener.onLongClickListerner(holder.textView, position);

                }


            });
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

}

//自定义的内部类，用于获取所有的控件
class MyViewHolder extends RecyclerView.ViewHolder{

    TextView textView ;
    ImageView imageView;

    public MyViewHolder(View itemView) {
        super(itemView);

        textView = (TextView) itemView.findViewById(R.id.id_name);
        imageView = (ImageView) itemView.findViewById(R.id.image);
    }
}