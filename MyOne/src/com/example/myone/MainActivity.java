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
	 * ����GPS�豸
	 */
	private void openGPSSettings() {
		LocationManager alm = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
		if (alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
			DisplayToast("GPSģ������");
		} else {
			DisplayToast("�뿪��GPS��");
			Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
			startActivityForResult(intent, 0); // ��Ϊ������ɺ󷵻ص���ȡ����
		}
	}
	
	/**
	 * ��ȡ��λ
	 */
	private void getLocation()
    {
        // ��ȡλ�ù������
        LocationManager locationManager;
        String serviceName = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) this.getSystemService(serviceName);
        // ���ҵ�������Ϣ
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE); // �߾���
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW); // �͹���

        String provider = locationManager.getBestProvider(criteria, true); // ��ȡGPS��Ϣ
        Location location = locationManager.getLastKnownLocation(provider); // ͨ��GPS��ȡλ��
        updateToNewLocation(location);
        // ���ü��������Զ����µ���Сʱ��Ϊ���N��(1��Ϊ1*1000������д��ҪΪ�˷���)����Сλ�Ʊ仯����N��
        locationManager.requestLocationUpdates(
        		provider, 
        		100 * 1000, 
        		500,
                new LocationListener(){

					@Override
					public void onLocationChanged(Location location) {
						// TODO Auto-generated method stub
						updateToNewLocation(location);
						Log.i(MainActivity.ACTIVITY_TAG, "ʱ�䣺"+location.getTime());
			            Log.i(MainActivity.ACTIVITY_TAG, "���ȣ�"+location.getLongitude());
			            Log.i(MainActivity.ACTIVITY_TAG, "γ�ȣ�"+location.getLatitude());
			            Log.i(MainActivity.ACTIVITY_TAG, "���Σ�"+location.getAltitude()); 
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
            tv_long.setText("����:" + longitude);
            tv_lat.setText("γ��:" +  latitude);
        } else {
        	tv_long.setText("����:�޷���ȡ");
            tv_lat.setText("γ��:�޷���ȡ");
        }

    }

	private void DisplayToast(String msg) {
		Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
	}

}
