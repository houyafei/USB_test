package com.example.administrator.myapplication.adpters;



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
 * Created by Administrator on 2016/7/29.
 */
public class SecondAdapter extends RecyclerView.Adapter<MySecondViewHolder>{


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

    public SecondAdapter(Context context, List<HashMap<String,Object>> dataList) {
        this.context = context ;

        this.dataList = dataList;

        inflater = LayoutInflater.from(context);
    }



    @Override
    public MySecondViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建item 布局！！！！！！！！！！！！！！！！
        View view = inflater.inflate(R.layout.item_layout02,parent,false);
        //
        MySecondViewHolder holder = new MySecondViewHolder(view);
        return holder ;
    }

    @Override
    public void onBindViewHolder(final MySecondViewHolder holder, final int position) {

        //给每个控件设置值
       // holder.textView.setText(datas.get(position));

        //设置每一项内容
        HashMap<String,Object> dataMap = dataList.get(position);
        holder.textViewName.setText((String) dataMap.get("NAME"));
        holder.textViewIndex.setText((String) dataMap.get("INDEX"));

        //在这里实现控件的
        if (onItemListener!=null){
            holder.textViewName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position =  holder.getLayoutPosition();
                    onItemListener.onClickListerner(holder.textViewName,position);
                }
            });

            holder.textViewIndex.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition(); ///!!!!!!!!!!!!!!!!!!!!!!!用这个获取所点击的item的位置
                    //调用接口中的方法
                    onItemListener.onLongClickListerner(holder.textViewName, position);

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
class MySecondViewHolder extends RecyclerView.ViewHolder{

    TextView textViewIndex ;
    TextView textViewName ;

    public MySecondViewHolder(View itemView) {
        super(itemView);

        textViewIndex = (TextView) itemView.findViewById(R.id.index);
        textViewName = (TextView) itemView.findViewById(R.id.name);
        
    }

}