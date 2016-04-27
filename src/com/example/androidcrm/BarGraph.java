package com.example.androidcrm;

import java.util.ArrayList;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

public class BarGraph {
funnel f = new funnel();
	public Intent getIntent(Context context) {

		ArrayList<Long> y = new ArrayList<Long>();
System.out.println("sdad"+f.wonopp);
y.add(f.leads+f.opp+f.wonopp);
y.add(f.opp);

y.add(f.wonopp);
		CategorySeries series = new CategorySeries("Bar1");
		for (int i = 0; i < y.size(); i++) {
			series.add("Bar" + (i + 1), y.get(i));
		}

		XYMultipleSeriesDataset dataSet = new XYMultipleSeriesDataset(); // collection
																			// of
																			// series
																			// under
																			// one
																			// object.,there
																			// could
																			// any
		dataSet.addSeries(series.toXYSeries()); // number of series

		// customization of the chart

		XYSeriesRenderer renderer = new XYSeriesRenderer(); // one renderer for
		
		// one series
		
		renderer.setColor(Color.RED);
		renderer.setDisplayChartValues(true);
		renderer.setChartValuesSpacing((float) 3.5d);
		renderer.setLineWidth((float) 3.5d);
		

		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer(); // collection
																				// multiple
																				// values
																				// for
																				// one
																				// renderer
																				// or
																				// series
		mRenderer.addSeriesRenderer(renderer);
		mRenderer.setChartTitle("Performance");
		// mRenderer.setXTitle("xValues");
		mRenderer.setYTitle("No Of People");
		mRenderer.setZoomButtonsVisible(true);
		mRenderer.setShowLegend(true);
		mRenderer.setZoomEnabled(false);
		mRenderer.setPanEnabled(false);
		mRenderer.setShowGridX(true); // this will show the grid in graph
		mRenderer.setShowGridY(true);
		// mRenderer.setAntialiasing(true);
		mRenderer.setBarSpacing(.5); // adding spacing between the line or
										// stacks
		mRenderer.setApplyBackgroundColor(true);
		mRenderer.setBackgroundColor(Color.WHITE);
		mRenderer.setXAxisMin(0);
		// mRenderer.setYAxisMin(.5);
		mRenderer.setXAxisMax(3);
		mRenderer.setYAxisMax(50);
		//
		mRenderer.setXLabels(0);
		mRenderer.addXTextLabel(1, "LEADS");
		mRenderer.addXTextLabel(2, "Oppo");
		mRenderer.addXTextLabel(3, "Converted");

		mRenderer.setPanEnabled(true, true); // will fix the chart position
		Intent intent = ChartFactory.getBarChartIntent(context, dataSet,
				mRenderer, Type.STACKED);

		return intent;
	}

	
}
