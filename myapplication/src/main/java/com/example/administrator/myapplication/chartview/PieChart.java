package com.example.administrator.myapplication.chartview;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

public class PieChart {

	//对外返回一个饼状图
	public View execute(Context context) {

		//设置各个区域的颜色
		int[] colors = new int[]{Color.RED, Color.YELLOW, Color.BLUE,
				Color.CYAN, Color.GREEN, Color.GRAY};

		//图像设置
		DefaultRenderer renderer = buildCategoryRenderer(colors);

		//图形数据添加
		CategorySeries categorySeries = new CategorySeries("Vehicles Chart");
		categorySeries.add("Carbon", 30);
		categorySeries.add("PAH", 20);
		categorySeries.add("Bacteria", 60);
		categorySeries.add("Carbon", 30);
		categorySeries.add("PAH", 20);
		categorySeries.add("Bacteria", 60);

		//返回饼状图
		return ChartFactory
				.getPieChartView(context, categorySeries, renderer);
	}

	 protected DefaultRenderer buildCategoryRenderer(int[] colors) {

	  		DefaultRenderer renderer = new DefaultRenderer();
	  		for (int color : colors) {
	   			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
	   			r.setColor(color);
	   			renderer.addSeriesRenderer(r);
	  		}

	  		//
	  		renderer.setShowLabels(true);
	  		//�
	  		renderer.setShowLegend(false);

	  		//
	  		renderer.setLabelsTextSize(15);
	  		renderer.setLabelsColor(Color.BLACK);
	  		renderer.setZoomEnabled(false);
	  		renderer.setPanEnabled(false);

	  		return renderer;
	 }
	}
