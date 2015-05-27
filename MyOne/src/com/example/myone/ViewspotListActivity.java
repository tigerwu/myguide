package com.example.myone;

import java.io.IOException;

import com.example.myone.model.VLocation;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
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
	private static final String ACTIVITY_TAG = "ViewspotListActivity";
	
	private String[] vsarray = null;
	private MediaPlayer[] mps = null;
	private VLocation[] locas = null;
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		int i;
		for(i = 0; i < 8 ; i++) {
			mps[i].release();
		}
		super.onDestroy();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewspot_list);
		
		getLocation();
		
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
			} catch (Exception e) {
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
	
	/**
	 * 获取定位
	 */
	private void getLocation() {
		// 获取位置管理服务
		LocationManager locationManager;
		String serviceName = Context.LOCATION_SERVICE;
		locationManager = (LocationManager) this.getSystemService(serviceName);
		// 查找到服务信息
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE); // 高精度
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW); // 低功耗

		String provider = locationManager.getBestProvider(criteria, true); // 获取GPS信息
		Location location = locationManager.getLastKnownLocation(provider); // 通过GPS获取位置
		updateToNewLocation(location);
		// 设置监听器，自动更新的最小时间为间隔N秒(1秒为1*1000，这样写主要为了方便)或最小位移变化超过N米
		locationManager.requestLocationUpdates(provider, 5 * 1000, 25,
				new LocationListener() {

					@Override
					public void onLocationChanged(Location location) {
						// TODO Auto-generated method stub
						updateToNewLocation(location);
						Log.i(ViewspotListActivity.ACTIVITY_TAG,
								"时间：" + location.getTime());
						Log.i(ViewspotListActivity.ACTIVITY_TAG,
								"经度：" + location.getLongitude());
						Log.i(ViewspotListActivity.ACTIVITY_TAG,
								"纬度：" + location.getLatitude());
						Log.i(ViewspotListActivity.ACTIVITY_TAG,
								"海拔：" + location.getAltitude());
					}

					@Override
					public void onProviderDisabled(String provider) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onProviderEnabled(String provider) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onStatusChanged(String provider, int status,
							Bundle extras) {
						// TODO Auto-generated method stub

					}
				});
	}

	private void updateToNewLocation(Location location) {
		
	}
	
	public VLocation[] createViewLocas() {
		locas = new VLocation[8];
		
		locas[0] = new VLocation();
		locas[0].setLatitude(121.39208623);
		locas[0].setLongitude(31.22641032);
		
		locas[1] = new VLocation();
		locas[1].setLatitude(121.39217446);
		locas[1].setLongitude(31.22628108);
		
		locas[2] = new VLocation();
		locas[2].setLatitude(121.39244637);
		locas[2].setLongitude(31.22569108);
		
		locas[3] = new VLocation();
		locas[3].setLatitude(121.39377488);
		locas[3].setLongitude(31.22516123);
		
		locas[4] = new VLocation();
		locas[4].setLatitude(121.39563189);
		locas[4].setLongitude(31.2251556);
		
		locas[5] = new VLocation();
		locas[5].setLatitude(121.396269);
		locas[5].setLongitude(31.226171);
		
		locas[6] = new VLocation();
		locas[6].setLatitude(121.39590147);
		locas[6].setLongitude(31.22807943);
		
		locas[7] = new VLocation();
		locas[7].setLatitude(121.39486268);
		locas[7].setLongitude(31.22862123);
		
		return locas;
	}
	
	public MediaPlayer[] createLocalMp3(){  
        /** 
         * 创建音频文件的方法： 
         * 1、播放资源目录的文件：MediaPlayer.create(MainActivity.this,R.raw.beatit);//播放res/raw 资源目录下的MP3文件 
         * 2:播放sdcard卡的文件：mediaPlayer=new MediaPlayer(); 
         *   mediaPlayer.setDataSource("/sdcard/beatit.mp3");//前提是sdcard卡要先导入音频文件 
         */  
		mps = new MediaPlayer[8];
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
         
        return mps;  
    }  
	
	private void DisplayToast(String msg) {
		Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
	}

}
