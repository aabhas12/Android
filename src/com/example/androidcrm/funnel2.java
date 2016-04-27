package com.example.androidcrm;

import android.app.Activity;
import android.os.Bundle;

import com.artfulbits.aiCharts.ChartView;
import com.artfulbits.aiCharts.Base.ChartPalette;
import com.artfulbits.aiCharts.Base.ChartPoint;
import com.artfulbits.aiCharts.Base.ChartSeries;

public class funnel2 extends Activity
{
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setTitle("FUNNEL CHART");
		setContentView(R.layout.chart2);
		Bundle b = getIntent().getExtras();
		
		ChartView chartView = (ChartView)findViewById(R.id.chartView);
		ChartSeries series = chartView.getSeries().get(0);
		
		ChartPalette pallete = ChartPalette.rangePalette(0xFF143587, 0xFFD68F5A, 3);
		
		chartView.setPalette(pallete);
		
		double[] ingredients = {b.getLong("12"),b.getLong("11"),b.getLong("10")};
		String[] ingredientNames = { "Leads:"+b.getLong("12"),"OPP:"+b.getLong("11"),"WON OPP:"+b.getLong("10")};
		
		for (int i = 0; i < ingredientNames.length; i++)
		{
			ChartPoint point = series.getPoints().addXY(i, ingredients[i]);
			
			point.setLabel(ingredientNames[i]);
			
			
		}
	}
}
	