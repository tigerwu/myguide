package com.example.myone;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private static final String ACTIVITY_TAG="MainActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		openGPSSettings();

		Button btnLocation = (Button) findViewById(R.id.btn_locat);
		btnLocation.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DisplayToast(getResources().getText(R.string.str_toast_locat)
						.toString());
				
				getLocation();
			}

		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * 开启GPS设备
	 */
	private void openGPSSettings() {
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
	private void getLocation()
    {
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
        locationManager.requestLocationUpdates(
        		provider, 
        		100 * 1000, 
        		500,
                new LocationListener(){

					@Override
					public void onLocationChanged(Location location) {
						// TODO Auto-generated method stub
						updateToNewLocation(location);
						Log.i(MainActivity.ACTIVITY_TAG, "时间："+location.getTime());
			            Log.i(MainActivity.ACTIVITY_TAG, "经度："+location.getLongitude());
			            Log.i(MainActivity.ACTIVITY_TAG, "纬度："+location.getLatitude());
			            Log.i(MainActivity.ACTIVITY_TAG, "海拔："+location.getAltitude()); 
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
						
					}});
    }
	
	private void updateToNewLocation(Location location) {

        TextView tv_long;
        TextView tv_lat;
        tv_long = (TextView) this.findViewById(R.id.tv_long);
        tv_lat = (TextView) this.findViewById(R.id.tv_lat);
        if (location != null) {
            double  latitude = location.getLatitude();
            double longitude= location.getLongitude();
            tv_long.setText("经度:" + longitude);
            tv_lat.setText("纬度:" +  latitude);
        } else {
        	tv_long.setText("经度:无法获取");
            tv_lat.setText("纬度:无法获取");
        }

    }

	private void DisplayToast(String msg) {
		Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
	}

}
