package com.smarter56.waxberry.activity;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smarter56.waxberry.R;
import com.smarter56.waxberry.dao.GpsInfoModel;
import com.smarter56.waxberry.helper.IntervalLocService;
import com.smarter56.waxberry.helper.LocationProvider;
import com.smarter56.waxberry.helper.Logger;
import com.smarter56.waxberry.helper.LocationProvider.LocationResultListener;
import com.smarter56.waxberry.util.DBService;
import com.smarter56.waxberry.util.HttpUtil;
import com.smarter56.waxberry.util.Constants;
import com.smarter56.waxberry.util.SharedPreferencesUtils;
import com.smarter56.waxberry.util.ToastUtils;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author Ricky
 */
public class MainActivity extends Activity implements OnClickListener {

	private static final int ALARM_INTERVAL_TIME = 30000;// 15分钟
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
		initViews();
		alarmManager = (AlarmManager) getApplicationContext().getSystemService(
				Context.ALARM_SERVICE);
		tv_user.setText("尊敬的"
				+ new SharedPreferencesUtils(context).getVehicleNo() + "车主！");
		// 启动service，定时2分钟激活。
		setOnclickListner();
		registBroadReceiver();
	
	}

	void initViews() {
		context = getApplicationContext();
		btn_start = (Button) findViewById(R.id.btn_start);
		btn_end = (Button) findViewById(R.id.btn_end);
		btn_close = (Button) findViewById(R.id.btn_logout);
		tv_content = (TextView) findViewById(R.id.tv_content);
		tv_user = (TextView) findViewById(R.id.tv_user);
	}

	void setOnclickListner() {
		btn_start.setOnClickListener(this);
		btn_end.setOnClickListener(this);
		btn_close.setOnClickListener(this);
	}

	void registBroadReceiver() {
		locationReceiver = new CustomReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(Constants.ACTION_REFRESH_LOCATION);
		registerReceiver(locationReceiver, intentFilter);
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Logger.log(this, "-----------------onDestroy-----------------");
		unregisterReceiver(locationReceiver);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onClick(View v) {
		final Intent intent = new Intent(MainActivity.this,IntervalLocService.class);
		switch (v.getId()) {
		case R.id.btn_start:
			startService(intent);
			mPendingIntent = PendingIntent.getService(context, 0, intent,
					PendingIntent.FLAG_UPDATE_CURRENT);
			alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
					System.currentTimeMillis(), ALARM_INTERVAL_TIME,
					mPendingIntent);
			tv_content.setText("定位中...！");
			ToastUtils.show(context, "开始定位");
			break;
		case R.id.btn_end:
			alarmManager.cancel(mPendingIntent);
			stopService(intent);
			tv_content.setText("定位已结束！");
			ToastUtils.show(context, "结束定位");
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
		 //ToastUtils.show(context, locationMsg);
		}
	}

}
