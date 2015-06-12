package com.smarter56.waxberry.activity;

import java.util.List;

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
import com.smarter56.waxberry.dao.GpsInfoModel;
import com.smarter56.waxberry.util.CustomBDLocationListener;
import com.smarter56.waxberry.util.DBService;
import com.smarter56.waxberry.util.HttpUtil;
import com.smarter56.waxberry.util.Intents;
import com.smarter56.waxberry.util.SharedPreferencesUtils;
import com.smarter56.waxberry.util.HttpUtil.uploadAsyncTask;
import com.smarter56.waxberry.util.ToastUtils;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Ricky
 */
public class MainActivity extends Activity implements OnClickListener {

	private final static String TAG = MainActivity.class.getSimpleName();
	private static final int ALARM_INTERVAL_TIME = 1000 * 900;// 15分钟
	private Button btn_start, btn_end, btn_close;
	private TextView tv_content, tv_user;
	private CustomReceiver locationReceiver;
	private AlarmManager alarmManager;
	private PendingIntent mPendingIntent;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = getApplicationContext();
		Log.i("ricky", "DBService.getInstance(context).countInfoModels()="
				+ DBService.getInstance(context).countInfoModels());
		btn_start = (Button) findViewById(R.id.btn_start);
		btn_end = (Button) findViewById(R.id.btn_end);
		btn_close = (Button) findViewById(R.id.btn_logout);
		tv_content = (TextView) findViewById(R.id.tv_content);
		tv_user = (TextView) findViewById(R.id.tv_user);
		alarmManager = (AlarmManager) getApplicationContext().getSystemService(
				Context.ALARM_SERVICE);
		tv_user.setText("尊敬的"
				+ new SharedPreferencesUtils(context).getVehicleNo() + "车主！");
		// 启动service，定时2分钟激活。
		btn_start.setOnClickListener(this);
		btn_end.setOnClickListener(this);
		btn_close.setOnClickListener(this);
		locationReceiver = new CustomReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(Intents.ACTION_REFRESH_LOCATION);
		registerReceiver(locationReceiver, intentFilter);

	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i(TAG, "-----------------onDestroy-----------------");
		unregisterReceiver(locationReceiver);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onClick(View v) {
		final Intent intent = new Intent(Intents.START_LOCSTART_SERVICE);
		switch (v.getId()) {
		case R.id.btn_start:

			startService(intent);
			mPendingIntent = PendingIntent.getService(context, 0, intent,
					PendingIntent.FLAG_UPDATE_CURRENT);
			alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
					System.currentTimeMillis(), ALARM_INTERVAL_TIME,
					mPendingIntent);
			tv_content.setText("定位已开始！");
			ToastUtils.show(context, "定位开始");
			break;
		case R.id.btn_end:
			alarmManager.cancel(mPendingIntent);
			stopService(intent);
			tv_content.setText("定位已结束！");
			ToastUtils.show(context, "定位结束");
			break;

		case R.id.btn_logout:
			alarmManager.cancel(mPendingIntent);
			stopService(intent);
			if (DBService.getInstance(context).countInfoModels() > 0) {
				new HttpUtil(context).new uploadAsyncTask().execute(DBService
						.getInstance(context).loadAllGpsInfoModels());
			}
			finish();

			break;

		default:
			break;
		}

	}

	class CustomReceiver extends BroadcastReceiver {

		String locationMsg = "";

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			locationMsg = intent.getStringExtra("gpsInfo");
			// tv_content.setText(locationMsg);
			ToastUtils.show(context, locationMsg);
		}
	}

}
