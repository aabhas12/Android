package com.example.androidcrm;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class line1 extends Activity {
	int[] count1=new int[12];
	int[] count2=new int[12];
	int[] count3=new int[12];
	int[] count4=new int[12];
	
	private String[] mMonth = new String[] { "Jan", "Feb", "Mar", "Apr", "May",
			"Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
	long a[], b1[];
@Override
public void onBackPressed() {
	// TODO Auto-generated method stub
Intent i = new Intent(line1.this,funnel.class);

startActivity(i);
	super.onBackPressed();
}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.charts3);
		Bundle b = getIntent().getExtras();
		a = b.getLongArray("10");
		b1 = b.getLongArray("11");
		// Getting reference to the button btn_chart
	
for(int i= 0 ;i<12;i++)
{
	count1[i]=0;
	count2[i]=0;
	count3[i]=0;
	count4[i]=0;
}
	openChart();
			

		// Setting event click listener for the button btn_chart of the
		// MainActivity layout
	
	}

	private void openChart() {
		for (int i = 0; i < a.length; i++) {
			if (b1[i] == 1) {
				if (a[i] == 11) {
					count1[0]++;
				} else if (a[i] == 12) {
					count2[0]++;
				} else if (a[i] == 13) {
					count3[0]++;
				} else {
					count4[0]++;
				}
			}
			else if (b1[i] == 2) {
				if (a[i] == 11) {
					count1[1]++;
				} else if (a[i] == 12) {
					count2[1]++;
				} else if (a[i] == 13) {
					count3[1]++;
				} else {
					count4[1]++;
				}
			}
			else if (b1[i] == 3) {
				if (a[i] == 11) {
					count1[2]++;
				} else if (a[i] == 12) {
					count2[2]++;
				} else if (a[i] == 13) {
					count3[2]++;
				} else {
					count4[2]++;
				}
			}
			else if (b1[i] == 4) {
				if (a[i] == 11) {
					count1[3]++;
				} else if (a[i] == 12) {
					count2[3]++;
				} else if (a[i] == 13) {
					count3[3]++;
				} else {
					count4[3]++;
				}
			}
			else if (b1[i] == 5) {
				if (a[i] == 11) {
					count1[4]++;
				} else if (a[i] == 12) {
					count2[4]++;
				} else if (a[i] == 13) {
					count3[4]++;
				} else {
					count4[4]++;
				}
			}
			else if (b1[i] == 6) {
				if (a[i] == 11) {
					count1[5]++;
				} else if (a[i] == 12) {
					count2[5]++;
				} else if (a[i] == 13) {
					count3[5]++;
				} else {
					count4[5]++;
				}
			}
			else if (b1[i] == 7) {
				if (a[i] == 11) {
					count1[6]++;
				} else if (a[i] == 12) {
					count2[6]++;
				} else if (a[i] == 13) {
					count3[6]++;
				} else {
					count4[6]++;
				}
			}
			else if (b1[i] == 8) {
				if (a[i] == 11) {
					count1[7]++;
				} else if (a[i] == 12) {
					count2[7]++;
				} else if (a[i] == 13) {
					count3[7]++;
				} else {
					count4[7]++;
				}
			}
			else if (b1[i] == 9) {
				if (a[i] == 11) {
					count1[8]++;
				} else if (a[i] == 12) {
					count2[8]++;
				} else if (a[i] == 13) {
					count3[8]++;
				} else {
					count4[8]++;
				}
			}
			else if (b1[i] == 10) {
				if (a[i] == 11) {
					count1[9]++;
				} else if (a[i] == 12) {
					count2[9]++;
				} else if (a[i] == 13) {
					count3[9]++;
				} else {
					count4[9]++;
				}
			}
			else if (b1[i] == 11) {
				if (a[i] == 11) {
					count1[10]++;
				} else if (a[i] == 12) {
					count2[10]++;
				} else if (a[i] == 13) {
					count3[10]++;
				} else {
					count4[10]++;
				}
			}
			else if (b1[i] == 12) {
				if (a[i] == 11) {
					count1[11]++;
				} else if (a[i] == 12) {
					count2[11]++;
				} else if (a[i] == 13) {
					count3[11]++;
				} else {
					count4[11]++;
				}
			}

		}
		int[] x = {0, 1, 2, 3, 4, 5, 6, 7, 8,9,10,11 };
	// Creating an XYSeries for Income
		XYSeries incomeSeries = new XYSeries("Contacted");
		// Creating an XYSeries for Expense
		XYSeries expenseSeries = new XYSeries("Open");
		XYSeries incomeSeries1 = new XYSeries("Qualified");
		// Creating an XYSeries for Expense
		XYSeries expenseSeries1 = new XYSeries("Unqualified");
		// Adding data to Income and Expense Series
		for (int i = 0; i < x.length; i++) {
			incomeSeries.add(x[i], count1[i]);
			expenseSeries.add(x[i], count2[i]);
			incomeSeries1.add(x[i], count3[i]);
			expenseSeries1.add(x[i], count4[i]);
			
		}

		// Creating a dataset to hold each series
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		// Adding Income Series to the dataset
		dataset.addSeries(incomeSeries);
		// Adding Expense Series to dataset
		dataset.addSeries(expenseSeries);
		dataset.addSeries(expenseSeries1);
		dataset.addSeries(expenseSeries1);

		// Creating XYSeriesRenderer to customize incomeSeries
		XYSeriesRenderer incomeRenderer = new XYSeriesRenderer();
		incomeRenderer.setColor(Color.BLACK);
		incomeRenderer.setPointStyle(PointStyle.CIRCLE);
		incomeRenderer.setFillPoints(true);
		incomeRenderer.setLineWidth(3);
		incomeRenderer.setDisplayChartValues(true);

		XYSeriesRenderer expenseRenderer1 = new XYSeriesRenderer();
		expenseRenderer1.setColor(Color.RED);
		expenseRenderer1.setPointStyle(PointStyle.CIRCLE);
		expenseRenderer1.setFillPoints(true);
		expenseRenderer1.setLineWidth(3);
		expenseRenderer1.setDisplayChartValues(true);
		
		XYSeriesRenderer expenseRenderer2 = new XYSeriesRenderer();
		expenseRenderer2.setColor(Color.BLUE);
		expenseRenderer2.setPointStyle(PointStyle.CIRCLE);
		expenseRenderer2.setFillPoints(true);
		expenseRenderer2.setLineWidth(3);
		expenseRenderer2.setDisplayChartValues(true);
		// Creating XYSeriesRenderer to customize expenseSeries
		XYSeriesRenderer expenseRenderer = new XYSeriesRenderer();
		expenseRenderer.setColor(Color.YELLOW);
		expenseRenderer.setPointStyle(PointStyle.CIRCLE);
		expenseRenderer.setFillPoints(true);
		expenseRenderer.setLineWidth(3);
		expenseRenderer.setDisplayChartValues(true);

		// Creating a XYMultipleSeriesRenderer to customize the whole chart
		XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
		multiRenderer.setXLabels(0);
		multiRenderer.setChartTitle("Performance");
		  
		 Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
		 Date currentTime = localCalendar.getTime();
		  int currentYear = localCalendar.get(Calendar.YEAR);
		 multiRenderer.setXTitle("Year "+currentYear);
		multiRenderer.setYTitle("Number of Persons");
		multiRenderer.setZoomButtonsVisible(true);
		for (int i = 0; i < x.length; i++) {
			multiRenderer.addXTextLabel(i , mMonth[i]);
		}

		// Adding incomeRenderer and expenseRenderer to multipleRenderer
		// Note: The order of adding dataseries to dataset and renderers to
		// multipleRenderer
		// should be same
		multiRenderer.addSeriesRenderer(incomeRenderer);
		multiRenderer.addSeriesRenderer(expenseRenderer);
		multiRenderer.addSeriesRenderer(expenseRenderer2);
		multiRenderer.addSeriesRenderer(expenseRenderer1);
		// Creating an intent to plot line chart using dataset and
		// multipleRenderer
		Intent intent = ChartFactory.getLineChartIntent(getBaseContext(),
				dataset, multiRenderer);

		// Start Activity
		startActivity(intent);
	}
}