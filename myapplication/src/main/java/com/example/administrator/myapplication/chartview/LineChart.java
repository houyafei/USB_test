package com.example.administrator.myapplication.chartview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

import com.example.administrator.myapplication.util.ImageDataSaveUtil;

import org.achartengine.ChartFactory;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/23.
 */
public class LineChart {

    private ArrayList<Byte> dataList ;

    private ArrayList<Integer> mData  = new ArrayList<>();

    private Context mContext ;

    public LineChart(Context context,ArrayList<Byte> dataList) {

        mContext = context;

        this.dataList  = dataList ;
    }

    private ArrayList<Integer> byteList2IntList(ArrayList<Byte> dataList){

         final ArrayList<Integer> data  = new ArrayList<>();

        for (int i = 2; i <dataList.size()-2; i+=2) {
           int dataEle = (dataList.get(i)&0xff)*256+(dataList.get(i+1)&0xff);
            if(dataEle<0){
                dataEle = - dataEle;
            }
            data.add(dataEle);
        }

         new Thread(){
             @Override
             public void run() {
                 super.run();
                 ImageDataSaveUtil.saveLogData(data);
             }


         }.start();
        return data ;
    }

    public View getView(){

        if (dataList==null) return null;

        mData = byteList2IntList(dataList);

        return execute(mData);

    }

    public View execute(ArrayList<Integer> datas){

        //图标 图例
        String[] titles = {"Pixel value"} ;

        //XY数据---x
        List<double[]> Xvalues= new ArrayList<double[]>();
        double[] datax = new double[datas.size()];

        //XY数据---y
        List<double[]> Yvalues = new ArrayList<double[]>();
        double[] datay = new double[datas.size()];

        //装入X,Y对应的数据
        for (int i = 0; i < datas.size(); i++) {
            datax[i] = i+1 ;
            int dataysrc = datas.get(i);
            if(dataysrc<0){
                datay[i] = -dataysrc;
            }else{
                datay[i] = dataysrc;
            }

            //Log.i("houyafei", "-----------准备x,y "+datax[i]);
        }

        Xvalues.add(datax);
        Yvalues.add(datay);
        Log.i("houyafei", "-----------准备x,y " + datay.length + "--" + datax.length);

        //设置数据
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();  /* 创建 XYMultipleSeriesDataset对象, 图表的总数据集 */
        int titlesLength = titles.length;                                           /* 获取图表中折线个数 */
        for (int i = 0; i < titlesLength; i++) {
            XYSeries series = new XYSeries(titles[i]);          /* 创建一个 XYSeries对象, 单个折线图数据 */
            double[] vX = Xvalues.get(i);
            double[] vY = Yvalues.get(i); /* 获取本柱状图数值数组 */
            int seriesLength = vX.length;                                        /* 获取单个折线图值的个数 */
            for (int k = 0; k < seriesLength; k++) {
                series.add(vX[k],vY[k]);                                             /* 将具体的值设置给 CategorySeries对象, 单个折线图数据 */
            }
            dataset.addSeries(series);                         /* 将单个折线图数据XYSeries对象 设置给 图表数据集 XYMultipleSeriesDataset对象 */
        }
        Log.i("houyafei", "-----------x,y添加ok ");

        // 渲染器
        // 设置曲线数据点
        int[] colors = new int[] { Color.CYAN };

        XYMultipleSeriesRenderer seriesRender = new XYMultipleSeriesRenderer();
        for (int i = 0; i < titlesLength; i++) {
            XYSeriesRenderer r = new XYSeriesRenderer();
            r.setColor(colors[i]);
            r.setPointStrokeWidth(15.5f);
            r.setFillPoints(true);
            r.setLineWidth(5);
            XYSeriesRenderer.FillOutsideLine fill = new XYSeriesRenderer.FillOutsideLine(XYSeriesRenderer.FillOutsideLine.Type.BOUNDS_ABOVE);
            fill.setColor(0x99999999);
            r.addFillOutsideLine(fill);
            seriesRender.addSeriesRenderer(r);
        }

        seriesRender.setApplyBackgroundColor(true); // 设置背景
        seriesRender.setBackgroundColor(0xF0F8FF);

        seriesRender.setAxesColor(Color.BLACK);
        seriesRender.setMarginsColor(0xF0F8FF);
        seriesRender.setShowGrid(true);
        seriesRender.setGridColor(Color.BLACK);
        //seriesRender.setXAxisMax(10);

        seriesRender.setAxisTitleTextSize(30); // 设置坐标轴标题字体大小
        seriesRender.setChartTitleTextSize(40); // 设置图表标题字体大小

        seriesRender.setLabelsTextSize(10); // 设置标签字体大小
        /***可以实现说明文字部分不可见****/
        seriesRender.setShowLegend(false);

        // renderer.setZoomInLimitY(1);
//				seriesRender.setYAxisMin(50);
//				seriesRender.setYAxisMax(150);
        seriesRender.setXAxisMin(300);
        seriesRender.setXAxisMax(1100);
        seriesRender.setXLabels(20); // 设置x轴数据标签数量，同样也显示网格数量
        // seriesRender.setXAxisMax(8);
        //seriesRender.setPointSize(10f); // 设置绘制的点的大小

        seriesRender.setXLabelsAlign(Paint.Align.CENTER);
        seriesRender.setYLabelsAlign(Paint.Align.RIGHT);
        seriesRender.setMarginsColor(Color.TRANSPARENT); //设置周围区域颜色
        //seriesRender.setMargins(new int[] {20, 50, 50, 10}); // top, left,
        // bottom, right

        //图像的缩放控制
        seriesRender.setPanEnabled(true, true);
        seriesRender.setZoomEnabled(true);
        seriesRender.setZoomEnabled(true, true);


        Log.i("houyafei", "-----------数据渲染ok ");
        View Lineview =
                ChartFactory.getLineChartView(mContext, dataset,
                        seriesRender);
        Log.i("houyafei", "-----------产生图表ok ");
        return Lineview ;
    }

}
