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
 
public class ListViewAdapter1 extends BaseAdapter {
 
	// Declare Variables
	Context mContext;
	LayoutInflater inflater;
	private List<WorldPopulation1> worldpopulationlist1 = null;
	private ArrayList<WorldPopulation1> arraylist;
 
	public ListViewAdapter1(Context context,
			List<WorldPopulation1> worldpopulationlist1) {
		mContext = context;
		this.worldpopulationlist1 = worldpopulationlist1;
		inflater = LayoutInflater.from(mContext);
		this.arraylist = new ArrayList<WorldPopulation1>();
		this.arraylist.addAll(worldpopulationlist1);
	}
 
	public class ViewHolder {
		TextView name;
		TextView type;
	
		TextView website;
		TextView email;
		ImageView flag;
	}
 
	@Override
	public int getCount() {
		return worldpopulationlist1.size();
	}
 
	@Override
	public WorldPopulation1 getItem(int position) {
		return worldpopulationlist1.get(position);
	}
 
	@Override
	public long getItemId(int position) {
		return position;
	}
 
	public View getView(final int position, View view, ViewGroup parent) {
		final ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.sixth, null);
			// Locate the TextViews in listview_item.xml
			holder.name = (TextView) view.findViewById(R.id.cutomername1);
			holder.type = (TextView) view.findViewById(R.id.customertype);
			
			holder.website = (TextView) view.findViewById(R.id.customerwebsite);
			holder.email = (TextView) view.findViewById(R.id.customeremail);
			
			// Locate the ImageView in listview_item.xml
			holder.flag = (ImageView) view.findViewById(R.id.imageView2);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		// Set the results into TextViews
		holder.name.setText(worldpopulationlist1.get(position).getname());
		holder.type.setText(worldpopulationlist1.get(position).gettype());
		holder.website.setText(worldpopulationlist1.get(position)
				.getwebsite());
		holder.email.setText(worldpopulationlist1.get(position)
				.getemail());
		 Random rand = new Random();
		 int r = rand.nextInt(5);
		
		 int[] colo= {0xff009900,0xff3366CC,0xff990000,0xffFF6600,0xff9900FF,0xffCC33CC};
			CharacterDrawable drawable = new CharacterDrawable(Character.toUpperCase(worldpopulationlist1.get(position).getname().charAt(0)), colo[r]);
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
		worldpopulationlist1.clear();
		if (charText.length() == 0) {
			worldpopulationlist1.addAll(arraylist);
		} else {
			for (WorldPopulation1 wp : arraylist) {
				if (wp.getname().toLowerCase(Locale.getDefault())
						.contains(charText)) {
					worldpopulationlist1.add(wp);
				}
			}
		}
		notifyDataSetChanged();
	}
 
}
