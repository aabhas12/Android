package com.example.androidcrm;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class ListViewAdapter3 extends BaseAdapter {
 
	// Declare Variables
	Context mContext;
	LayoutInflater inflater;
	private List<WorldPopulation3> worldpopulationlist3 = null;
	private ArrayList<WorldPopulation3> arraylist;
 
	public ListViewAdapter3(Context context,
			List<WorldPopulation3> worldpopulationlist3) {
		mContext = context;
		this.worldpopulationlist3 = worldpopulationlist3;
		inflater = LayoutInflater.from(mContext);
		this.arraylist = new ArrayList<WorldPopulation3>();
		this.arraylist.addAll(worldpopulationlist3);
	}
 
	public class ViewHolder {
		TextView name;
		TextView cust;
	
		TextView mobile;
		TextView email;
		ImageView flag;
	}
 
	@Override
	public int getCount() {
		return worldpopulationlist3.size();
	}
 
	@Override
	public WorldPopulation3 getItem(int position) {
		return worldpopulationlist3.get(position);
	}
 
	@Override
	public long getItemId(int position) {
		return position;
	}
 
	public View getView(final int position, View view, ViewGroup parent) {
		final ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.conta, null);
			// Locate the TextViews in listview_item.xml
			holder.name = (TextView) view.findViewById(R.id.name21);
			holder.cust = (TextView) view.findViewById(R.id.cname);
			
			holder.mobile = (TextView) view.findViewById(R.id.mob1);
			holder.email = (TextView) view.findViewById(R.id.email);
			
			// Locate the ImageView in listview_item.xml
			holder.flag = (ImageView) view.findViewById(R.id.imageView4);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		// Set the results into TextViews
		holder.name.setText(worldpopulationlist3.get(position).getname());
		holder.cust.setText(worldpopulationlist3.get(position).getcust());
		holder.mobile.setText(worldpopulationlist3.get(position)
				.getmobile());
		holder.email.setText(worldpopulationlist3.get(position)
				.getemail());
		 Random rand = new Random();
		 int r = rand.nextInt(6);
		
		 int[] colo= {0xff009900,0xff3366CC,0xff990000,0xffFF6600,0xff9900FF,0xffCC33CC};
			CharacterDrawable drawable = new CharacterDrawable(Character.toUpperCase(worldpopulationlist3.get(position).getname().charAt(0)), colo[r]);
			holder.flag.setImageDrawable(drawable); 
		
		return view;
	}

		// Set the results into ImageView
		
	/*	// Listen for ListView Item Click
		view.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
				// Send single item click data to SingleItemView Class
				Intent intent = new Intent(mContext, third.class);
				// Pass all data rank
				intent.putExtra("name",
						(worldpopulationlist.get(position).getname()));
				// Pass all data country
				intent.putExtra("comname",
						(worldpopulationlist.get(position).getcompanyname()));
				// Pass all data population
				intent.putExtra("phone",
						(worldpopulationlist.get(position).phone()));
				// Pass all data flag
				intent.putExtra("rating",
						worldpopulationlist.get(position).rating());
				// Start SingleItemView Class
				mContext.startActivity(intent);
			}
		});
 
		return view;
	}
 */
	// Filter Class
	public void filter(String charText) {
		charText = charText.toLowerCase(Locale.getDefault());
		worldpopulationlist3.clear();
		if (charText.length() == 0) {
			worldpopulationlist3.addAll(arraylist);
		} else {
			for (WorldPopulation3 wp : arraylist) {
				if (wp.getname().toLowerCase(Locale.getDefault())
						.contains(charText)) {
					worldpopulationlist3.add(wp);
				}
			}
		}
		notifyDataSetChanged();
	}
 
}
