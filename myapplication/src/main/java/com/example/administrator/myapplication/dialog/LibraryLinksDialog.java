package com.example.administrator.myapplication.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/1.
 */
public class LibraryLinksDialog extends DialogFragment {


    private AlertDialog.Builder builder ;


    private Button btnCancel  ;

//    列表数据
    private ListView linkListView ;

    private ListAdapter adapter ;


//    列表对应的名称，可以在这里添加新的链接
    private String[] dataLists = {"Wiley","Sciencedirect","APS","AIP",
                                    "ACS","OSA","RSC","IOP",
                                    "PNAS","NAR",
                                    "IEEE","Web of Science",
                                    "Angewandte","AM"
                                };

//    列表对应的链接
    private String[] urlLists ={
        "http://as.wiley.com/WileyCDA/Section/index.html",
        "http://www.sciencedirect.com/",
        "https://www.aps.org/",
        "http://scitation.aip.org/",
        "http://pubs.acs.org/",
        "http://www.osa.org/en-us/home/",
        "http://pubs.rsc.org/",
        "http://www.iop.org/",
        "http://m.pnas.org/",
        "http://m.nar.oxfordjournals.org/",
        "https://m.ieee.org/index.html",
        "http://thomsonreuters.com/en/products-services/scholarly-scientific-research/scholarly-search-and-discovery/web-of-science.html",
        "http://onlinelibrary.wiley.com/journal/10.1002/%28ISSN%291521-3773",
        "http://onlinelibrary.wiley.com/journal/10.1002/(ISSN)1521-4095"
        } ;


    private List<String> datas = new ArrayList<>();
    private List<String> urls = new ArrayList<>() ;

    /**
     * 构造方法
     *
     */
    public LibraryLinksDialog(){


        for (String data : dataLists) {
            datas.add(data);
        }

        for (String  url : urlLists) {
            urls.add(url);
        }
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.library_list_dialog, null);

        iniView(view);

        builder.setView(view);

        return builder.create();
    }


    private void iniView(View view){
        linkListView = (ListView) view.findViewById(R.id.listLink);

        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_expandable_list_item_1,datas);

        linkListView.setAdapter(adapter);

        linkListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse(urls.get((int) id))));
            }
        });

        //关闭对话框
        btnCancel = (Button) view.findViewById(R.id.cancelDialog);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        
    }


}
