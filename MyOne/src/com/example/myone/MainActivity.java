package com.example.myone;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.example.myone.db.ViewspotDBManager;
import com.example.myone.service.implement.ViewspotService;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private static final String ACTIVITY_TAG = "MainActivity";
	private final String VSLFILENAME = "ViewspotLocation.txt";
	private ViewspotDBManager dbmgr;
	private ViewspotService viewspotSrv;
	
	private Handler mHandler = new Handler();
	private ScrollView mScrollView;  
	private LocationManager locationManager;
	private myLocationListener mylocalistener = new myLocationListener();  
	
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Intent intent = null;
		switch (item.getItemId()) {  
        case R.id.action_settings:
        	DisplayToast("你选择了初始化");
        	getApplicationContext().deleteFile(VSLFILENAME);
        	try {
				ShowViewspotLocation();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	break;
        case R.id.action_autoguidid:
        	DisplayToast("你选择了自动导航");
        	intent = new Intent();
        	intent.setClass(MainActivity.this, ViewspotListActivity.class);
        	MainActivity.this.startActivity(intent);
        	//MainActivity.this.finish();
        	break;
        case R.id.action_manualguidid:
        	DisplayToast("你选择了手动导航");
        	intent = new Intent();
        	intent.setClass(MainActivity.this, ViewspotListActivity.class);
        	MainActivity.this.startActivity(intent);
        	break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initialGPS();

		// initialService();

		try {
			ShowViewspotLocation();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Spinner spinner_viewspots = (Spinner) this
				.findViewById(R.id.spinner_viewspots);
		spinner_viewspots
				.setOnItemSelectedListener(new ViewspotsOnItemSelectedListener());

		mScrollView = (ScrollView) findViewById(R.id.scroll);  
		EditText ed_viewspot = (EditText) this.findViewById(R.id.ed_viewspot);
		ed_viewspot.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((EditText) v).setText("");
				
				mHandler.postDelayed(new Runnable() {  
		              
		            @Override  
		            public void run() {  
		                //将ScrollView滚动到底  
		                mScrollView.fullScroll(View.FOCUS_DOWN);  
		            }  
		        }, 100);  
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		Log.i(MainActivity.ACTIVITY_TAG, "onPause");
		if (this.locationManager != null) {
			this.locationManager.removeUpdates(mylocalistener);
		}
		super.onPause();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		Log.i(MainActivity.ACTIVITY_TAG, "onRestart");
		if (locationManager != null) {
			getLocation();
		}
		super.onRestart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		Log.i(MainActivity.ACTIVITY_TAG, "onResume");
		
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		Log.i(MainActivity.ACTIVITY_TAG, "onStart");
		
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		Log.i(MainActivity.ACTIVITY_TAG, "onStop");
		
		super.onStop();
	}

	public void Locate_onClick_Event(View view) {
		DisplayToast(getResources().getText(R.string.str_toast_locat)
				.toString());

		getLocation();
	}

	/**
	 * 保存按钮事件
	 * @param view
	 */
	public void SaveViewLocat_onClick_Event(View view) {
		EditText ed_viewspot = (EditText) this.findViewById(R.id.ed_viewspot);
		EditText ed_long = (EditText) this.findViewById(R.id.ed_long);
		EditText ed_lat = (EditText) this.findViewById(R.id.ed_lat);

		if (ed_viewspot.getText().toString() == null
				|| ed_viewspot.getText().toString().trim().length() == 0) {
			DisplayToast(getResources().getText(R.string.str_view_input)
					.toString());
			return;
		}

		if (ed_long.getText().toString() == null
				|| ed_long.getText().toString().trim().length() == 0) {
			DisplayToast(getResources().getText(R.string.str_ed_long)
					.toString());
			return;
		}

		if (ed_lat.getText().toString() == null
				|| ed_lat.getText().toString().trim().length() == 0) {
			DisplayToast(getResources().getText(R.string.str_ed_lat).toString());
			return;
		}

		String vlstr = "景点:" + ed_viewspot.getText().toString().trim() + ";"
				+ "经度:" + ed_long.getText().toString().trim() + ";" + "纬度:"
				+ ed_lat.getText().toString().trim() + "\r\n";

		try {
			SaveViewspotLocationToFile(vlstr);
			DisplayToast(getResources().getText(
					R.string.str_toast_saveview_success).toString());
			ShowViewspotLocation();
		} catch (Exception e) {
			DisplayToast(getResources().getText(
					R.string.str_toast_saveview_fail).toString());
			e.printStackTrace();
		}
	}
	
	/**
	 * 保存景点经纬度到文件
	 * @param vlstring
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void SaveViewspotLocationToFile(String vlstring)
			throws FileNotFoundException, IOException {
		FileOutputStream fos = openFileOutput(VSLFILENAME,
				Context.MODE_APPEND);
		fos.write(vlstring.getBytes());
		fos.close();
	}
	
	/**
	 * 从文件获取景点经纬度
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private String[] getViewspotLocationFromFile()  
			throws FileNotFoundException, IOException {
		String[] vslarray = null;
		
		FileInputStream fis = openFileInput("ViewspotLocation.txt");
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = fis.read(buffer)) != -1) {
			// 把每次读到的数据写到内存中
			outStream.write(buffer, 0, len);
		}
		// 得到存放在内存中的所有的数据
		outStream.close();
		fis.close();
		
		byte[] data = outStream.toByteArray();

		String vslstr = new String(data);

		if (vslstr != null && vslstr.trim().length() > 0) {
			vslarray = vslstr.split("\\r\\n");
		}
		return vslarray;		
	}
	
	/**
	 * 显示景点经纬度到列表
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void ShowViewspotLocation() 
			throws IOException {
		ListView lv_vl = (ListView) this.findViewById(R.id.lv_vlfile);
		
		try {
			String[] vslarray = getViewspotLocationFromFile();
			if (vslarray != null) {				
				
				lv_vl.setAdapter(new ViewspotLocationAdapter(vslarray));
						
				//TextView showvl = (TextView) this.findViewById(R.id.tv_vlfile);
				//showvl.setText(vslstr);
			}
			else {
				lv_vl.setAdapter(null);
			}
		}
		catch (FileNotFoundException ex) {
			lv_vl.setAdapter(null);
			ex.printStackTrace();
		}

		
	}

	private class ViewspotsOnItemSelectedListener implements
			OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			String str_viewspot = parent.getItemAtPosition(position).toString();
			EditText ed_viewspot = (EditText) MainActivity.this
					.findViewById(R.id.ed_viewspot);
			ed_viewspot.setText(str_viewspot);
			// DisplayToast("SpinnerViewspot: position=" + position + " id=" +
			// id + "-" + str);
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			DisplayToast("SpinnerViewspot unselected");
		}

	}
	
	private class ViewspotLocationAdapter extends BaseAdapter {
		String[] vslarray = null;
		
		public ViewspotLocationAdapter(String[] vslarray2) {
			// TODO Auto-generated constructor stub
			vslarray = vslarray2;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if (vslarray == null) {
				return 0;
			}
			else {
				return vslarray.length;
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
			if (vslarray != null) {
				mTextView = new TextView(getApplicationContext());
	            mTextView.setText(vslarray[position]);
	            mTextView.setTextSize(12);
	            mTextView.setTextColor(Color.BLUE);
			}            
            return mTextView;
		}
		
	}

	private void initialService() {
		dbmgr = new ViewspotDBManager(this);
		viewspotSrv = new ViewspotService(dbmgr);
		DisplayToast("启动数据服务");
	}

	/**
	 * 开启GPS设备
	 */
	private void initialGPS() {
		LocationManager alm = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
		if (alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
			DisplayToast("GPS模块正常");
		} else {
			DisplayToast("请开启GPS！");
			Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
			startActivityForResult(intent, 0); // 此为设置完成后返回到获取界面
		}
	}

	/**
	 * 获取定位
	 */
	private void getLocation() {
		// 获取位置管理服务
		
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
		locationManager.requestLocationUpdates(provider, 5 * 1000, 25, mylocalistener);
	}
	
	private class myLocationListener implements LocationListener {
		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			updateToNewLocation(location);
			Log.i(MainActivity.ACTIVITY_TAG,
					"时间：" + location.getTime());
			Log.i(MainActivity.ACTIVITY_TAG,
					"经度：" + location.getLongitude());
			Log.i(MainActivity.ACTIVITY_TAG,
					"纬度：" + location.getLatitude());
			Log.i(MainActivity.ACTIVITY_TAG,
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
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
		
	}

	private void updateToNewLocation(Location location) {

		EditText ed_long;
		EditText ed_lat;
		ed_long = (EditText) this.findViewById(R.id.ed_long);
		ed_lat = (EditText) this.findViewById(R.id.ed_lat);
		if (location != null) {
			Double latitude = location.getLatitude();
			Double longitude = location.getLongitude();
			ed_long.setText(longitude.toString().trim());
			ed_lat.setText(latitude.toString().trim());
		} else {
			ed_long.setText("获取失败");
			ed_lat.setText("获取失败");

		}

	}

	private void DisplayToast(String msg) {
		Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
	}
	
	private void TestMP3() {
		MediaPlayer mp = MediaPlayer.create(this,R.raw.zoo1); 
		try {
			mp.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		mp.start();
	}

}
