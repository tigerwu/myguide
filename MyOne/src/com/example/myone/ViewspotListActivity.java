package com.example.myone;

import java.io.IOException;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewspotListActivity extends Activity {
	private String[] vsarray = null;
	private MediaPlayer[] mps = null;
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		int i;
		for(i = 0; i < 20 ; i++) {
			mps[i].release();
		}
		super.onDestroy();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewspot_list);
		
		createLocalMp3();
		
		vsarray = this.getResources().getStringArray(R.array.strarr_spinner_viewspots);
		ListView lv_viewspot = (ListView) this.findViewById(R.id.lv_viewspots);
		lv_viewspot.setAdapter(new ViewspotAdapter(vsarray));
		
		lv_viewspot.setOnItemClickListener(new ViewspotLVListener());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.viewspot_list, menu);
		return true;
	}
	
	private class ViewspotLVListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			TextView itemtxview = (TextView)view;
			String str_viewspot = itemtxview.getText().toString();
			
			try {
				mps[position].prepare();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			mps[position].start();
			
			DisplayToast(str_viewspot);
			
		}
		
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
	            mTextView.setTextSize(24);
	            mTextView.setTextColor(Color.GRAY);
			}            
            return mTextView;
		}
		
	}
	
	public MediaPlayer[] createLocalMp3(){  
        /** 
         * 创建音频文件的方法： 
         * 1、播放资源目录的文件：MediaPlayer.create(MainActivity.this,R.raw.beatit);//播放res/raw 资源目录下的MP3文件 
         * 2:播放sdcard卡的文件：mediaPlayer=new MediaPlayer(); 
         *   mediaPlayer.setDataSource("/sdcard/beatit.mp3");//前提是sdcard卡要先导入音频文件 
         */  
		mps = new MediaPlayer[20];
		mps[0]=MediaPlayer.create(this,R.raw.zoo1); 
        mps[0].stop();
        mps[1]=MediaPlayer.create(this,R.raw.zoo2); 
        mps[1].stop();
        mps[2]=MediaPlayer.create(this,R.raw.zoo3); 
        mps[2].stop();
        mps[3]=MediaPlayer.create(this,R.raw.zoo4);  
        mps[3].stop();
        mps[4]=MediaPlayer.create(this,R.raw.zoo5);  
        mps[4].stop();
        mps[5]=MediaPlayer.create(this,R.raw.zoo6);  
        mps[5].stop();
        
        mps[6]=MediaPlayer.create(this,R.raw.zoo7);  
        mps[6].stop();
        mps[7]=MediaPlayer.create(this,R.raw.zoo8);  
        mps[7].stop();
        mps[8]=MediaPlayer.create(this,R.raw.zoo9);  
        mps[8].stop();
        mps[9]=MediaPlayer.create(this,R.raw.zoo10);  
        mps[9].stop();
        mps[10]=MediaPlayer.create(this,R.raw.zoo11);
        mps[10].stop();
        
        mps[11]=MediaPlayer.create(this,R.raw.zoo12); 
        mps[11].stop();
        mps[12]=MediaPlayer.create(this,R.raw.zoo13); 
        mps[12].stop();
        mps[13]=MediaPlayer.create(this,R.raw.zoo14);  
        mps[13].stop();
        mps[14]=MediaPlayer.create(this,R.raw.zoo15);  
        mps[14].stop();
        mps[15]=MediaPlayer.create(this,R.raw.zoo16);  
        mps[15].stop();
        
        mps[16]=MediaPlayer.create(this,R.raw.zoo17);  
        mps[16].stop();
        mps[17]=MediaPlayer.create(this,R.raw.zoo18);  
        mps[17].stop();
        mps[18]=MediaPlayer.create(this,R.raw.zoo19);  
        mps[18].stop();
        mps[19]=MediaPlayer.create(this,R.raw.zoo20);  
        mps[19].stop();
 
        return mps;  
    }  
	
	private void DisplayToast(String msg) {
		Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
	}

}
