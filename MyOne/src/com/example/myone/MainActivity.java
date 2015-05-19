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
import android.os.Bundle;
import android.provider.Settings;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private static final String ACTIVITY_TAG = "MainActivity";
	private ViewspotDBManager dbmgr;
	private ViewspotService viewspotSrv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initialGPS();

		// initialService();

		try {
			ShowViewspotLocationFromFile();
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

		EditText ed_viewspot = (EditText) this.findViewById(R.id.ed_viewspot);
		ed_viewspot.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((EditText) v).setText("");
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void Locate_onClick_Event(View view) {
		DisplayToast(getResources().getText(R.string.str_toast_locat)
				.toString());

		getLocation();
	}

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

		String vlstr = "����:" + ed_viewspot.getText().toString().trim() + ";"
				+ "����:" + ed_long.getText().toString().trim() + ";" + "γ��:"
				+ ed_lat.getText().toString().trim() + "\r\n";

		try {
			SaveViewspotLocationToFile(vlstr);
			DisplayToast(getResources().getText(
					R.string.str_toast_saveview_success).toString());
			ShowViewspotLocationFromFile();
		} catch (Exception e) {
			DisplayToast(getResources().getText(
					R.string.str_toast_saveview_fail).toString());
			e.printStackTrace();
		}
	}

	private void SaveViewspotLocationToFile(String vlstring)
			throws FileNotFoundException, IOException {
		FileOutputStream fos = openFileOutput("ViewspotLocation.txt",
				Context.MODE_APPEND);
		fos.write(vlstring.getBytes());
		fos.close();
	}

	private void ShowViewspotLocationFromFile() throws FileNotFoundException,
			IOException {
		FileInputStream fis = openFileInput("ViewspotLocation.txt");
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = fis.read(buffer)) != -1) {
			// ��ÿ�ζ���������д���ڴ���
			outStream.write(buffer, 0, len);
		}
		// �õ�������ڴ��е����е�����
		byte[] data = outStream.toByteArray();

		String vslstr = new String(data);

		if (vslstr != null && vslstr.trim().length() > 0) {
			String[] vslarray = vslstr.split("\\r\\n");
			
			ListView lv_vl = (ListView) this.findViewById(R.id.lv_vlfile);
			lv_vl.setAdapter(new ArrayAdapter<String>(
					this,
					android.R.layout.simple_expandable_list_item_1,
					vslarray));
					
			//TextView showvl = (TextView) this.findViewById(R.id.tv_vlfile);
			//showvl.setText(vslstr);
		}

		outStream.close();
		fis.close();
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

	private void initialService() {
		dbmgr = new ViewspotDBManager(this);
		viewspotSrv = new ViewspotService(dbmgr);
		DisplayToast("�������ݷ���");
	}

	/**
	 * ����GPS�豸
	 */
	private void initialGPS() {
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
	private void getLocation() {
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
		locationManager.requestLocationUpdates(provider, 100 * 1000, 500,
				new LocationListener() {

					@Override
					public void onLocationChanged(Location location) {
						// TODO Auto-generated method stub
						updateToNewLocation(location);
						Log.i(MainActivity.ACTIVITY_TAG,
								"ʱ�䣺" + location.getTime());
						Log.i(MainActivity.ACTIVITY_TAG,
								"���ȣ�" + location.getLongitude());
						Log.i(MainActivity.ACTIVITY_TAG,
								"γ�ȣ�" + location.getLatitude());
						Log.i(MainActivity.ACTIVITY_TAG,
								"���Σ�" + location.getAltitude());
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
			ed_long.setText("��ȡʧ��");
			ed_lat.setText("��ȡʧ��");

		}

	}

	private void DisplayToast(String msg) {
		Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
	}

}
