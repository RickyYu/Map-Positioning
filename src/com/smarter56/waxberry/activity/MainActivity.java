package com.smarter56.waxberry.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.smarter56.waxberry.R;
import com.smarter56.waxberry.R.id;
import com.smarter56.waxberry.R.layout;
import com.smarter56.waxberry.util.CustomBDLocationListener;
import com.smarter56.waxberry.util.HttpUtil;
import com.smarter56.waxberry.util.SharedPreferencesUtils;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	private final static String TAG = MainActivity.class.getSimpleName();
	private static final String START_LOCSTART_SERVICE = "START_LOCATING_SERVICE";
	private static final int ALARM_INTERVAL_TIME = 1000 * 120;// 2分钟
	private Button btn_start, btn_end, btn_close;
	private TextView tv_content, tv_user;
	private CustomReceiver locationReceiver;
	private AlarmManager alarmManager;
	private PendingIntent mPendingIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btn_start = (Button) findViewById(R.id.btn_start);
		btn_end = (Button) findViewById(R.id.btn_end);
		btn_close = (Button) findViewById(R.id.btn_close);
		tv_content = (TextView) findViewById(R.id.tv_content);
		tv_user = (TextView) findViewById(R.id.tv_user);
		alarmManager = (AlarmManager) getApplicationContext().getSystemService(
				Context.ALARM_SERVICE);
		tv_user.setText("当前用户：" + new SharedPreferencesUtils(getApplicationContext()).getPhoneNo());
		// 启动service，定时2分钟激活。
		btn_start.setOnClickListener(this);
		btn_end.setOnClickListener(this);
		btn_close.setOnClickListener(this);
		locationReceiver = new CustomReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("NEW LOCATION SENT");
		registerReceiver(locationReceiver, intentFilter);

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub

		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i("MainActivity", "-----------------onDestroy-----------------");
		unregisterReceiver(locationReceiver);
	}

	class CustomReceiver extends BroadcastReceiver {

		String locationMsg = "";

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			locationMsg = intent.getStringExtra("newLoca");
			tv_content.setText(locationMsg);
			Log.i(TAG, "handle");
			Log.i("ricky", "handle");
			Toast.makeText(getApplicationContext(), locationMsg, 3000).show();
		}
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(START_LOCSTART_SERVICE);
		switch (v.getId()) {
		case R.id.btn_start:
			Log.i(TAG, "-----------------btn_start-----------------");

			startService(intent);
			mPendingIntent = PendingIntent.getService(getApplicationContext(),
					0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
					System.currentTimeMillis(), ALARM_INTERVAL_TIME,
					mPendingIntent);
			Toast.makeText(getApplicationContext(), "定位开始", Toast.LENGTH_SHORT)
					.show();
			break;
		case R.id.btn_end:
			// TODO Auto-generated method stub
			Log.i(TAG, "-----------------btn_end-----------------");
			alarmManager.cancel(mPendingIntent);

			stopService(intent);
			Toast.makeText(getApplicationContext(), "定位结束", Toast.LENGTH_SHORT)
					.show();
			break;

		case R.id.btn_close:
			stopService(intent);
			unregisterReceiver(locationReceiver);
			android.os.Process.killProcess(android.os.Process.myPid());
			break;

		default:
			break;
		}

	}
}
