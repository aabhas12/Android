package com.example.androidcrm;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class ListViewAdapter extends BaseAdapter {
 
	// Declare Variables
	Context mContext;
	LayoutInflater inflater;
	private List<WorldPopulation> worldpopulationlist = null;
	private ArrayList<WorldPopulation> arraylist;
 
	public ListViewAdapter(Context context,
			List<WorldPopulation> worldpopulationlist) {
		mContext = context;
		this.worldpopulationlist = worldpopulationlist;
		inflater = LayoutInflater.from(mContext);
		this.arraylist = new ArrayList<WorldPopulation>();
		this.arraylist.addAll(worldpopulationlist);
	}
 
	public class ViewHolder {
		TextView name;
		TextView companyname;
	
		TextView phone;
		ImageView flag;
	}
 
	@Override
	public int getCount() {
		return worldpopulationlist.size();
	}
 
	@Override
	public WorldPopulation getItem(int position) {
		return worldpopulationlist.get(position);
	}
 
	@Override
	public long getItemId(int position) {
		return position;
	}
 
	public View getView(final int position, View view, ViewGroup parent) {
		final ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.fifth, null);
			// Locate the TextViews in listview_item.xml
			holder.name = (TextView) view.findViewById(R.id.txt);
			holder.companyname = (TextView) view.findViewById(R.id.phon);
			
			holder.phone = (TextView) view.findViewById(R.id.age);
			// Locate the ImageView in listview_item.xml
			holder.flag = (ImageView) view.findViewById(R.id.imageView1);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		// Set the results into TextViews
		holder.name.setText(worldpopulationlist.get(position).getname());
		holder.companyname.setText(worldpopulationlist.get(position).getcompanyname());
		holder.phone.setText(worldpopulationlist.get(position)
				.phone());
		if (worldpopulationlist.get(position)
				.rating().equals("Hot (Probable conversion within 1 month)")) {
			CharacterDrawable drawable = new CharacterDrawable(Character.toUpperCase(worldpopulationlist.get(position).getname().charAt(0)), 0xFFff4411);
			holder.flag.setImageDrawable(drawable); 
		} else if (worldpopulationlist.get(position)
				.rating().equals("Warm (Positive Interest)")) {
			CharacterDrawable drawable = new CharacterDrawable(Character.toUpperCase(worldpopulationlist.get(position).getname().charAt(0)), 0xFFff9933);
			holder.flag.setImageDrawable(drawable); 
		} else if (worldpopulationlist.get(position)
				.rating().equals("Cold")) {
			CharacterDrawable drawable = new CharacterDrawable(Character.toUpperCase(worldpopulationlist.get(position).getname().charAt(0)), 0xff0099FF);
			holder.flag.setImageDrawable(drawable); 
		}
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
		worldpopulationlist.clear();
		if (charText.length() == 0) {
			worldpopulationlist.addAll(arraylist);
		} else {
			for (WorldPopulation wp : arraylist) {
				if (wp.getname().toLowerCase(Locale.getDefault())
						.contains(charText)) {
					worldpopulationlist.add(wp);
				}
			}
		}
		notifyDataSetChanged();
	}
 
}
