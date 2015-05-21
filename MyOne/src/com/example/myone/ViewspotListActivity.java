package com.example.myone;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ViewspotListActivity extends Activity {
	String[] vsarray = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewspot_list);
		
		vsarray = this.getResources().getStringArray(R.array.strarr_spinner_viewspots);
		ListView lv_viewspot = (ListView) this.findViewById(R.id.lv_viewspots);
		lv_viewspot.setAdapter(new ViewspotAdapter(vsarray));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.viewspot_list, menu);
		return true;
	}
	
	private class ViewspotAdapter extends BaseAdapter {
		String[] vsarray = null;
		
		public ViewspotAdapter(String[] vsArray) {
			// TODO Auto-generated constructor stub
			vsarray = vsArray;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if (vsarray == null) {
				return 0;
			}
			else {
				return vsarray.length;
			}
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			TextView mTextView = null;
			if (vsarray != null) {
				mTextView = new TextView(getApplicationContext());
	            mTextView.setText(vsarray[position]);
	            mTextView.setTextSize(14);
	            mTextView.setTextColor(Color.GRAY);
			}            
            return mTextView;
		}
		
	}

}
