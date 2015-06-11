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
		btn_start = (Button) findViewById(R.id.btn_start);
		btn_end = (Button) findViewById(R.id.btn_end);
		btn_close = (Button) findViewById(R.id.btn_logout);
		tv_content = (TextView) findViewById(R.id.tv_content);
		tv_user = (TextView) findViewById(R.id.tv_user);
		alarmManager = (AlarmManager) getApplicationContext().getSystemService(
				Context.ALARM_SERVICE);
		tv_user.setText("尊敬的"
				+ new SharedPreferencesUtils(getApplicationContext())
						.getVehicleNo() + "车主！");
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

	class CustomReceiver extends BroadcastReceiver {

		String locationMsg = "";

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			locationMsg = intent.getStringExtra("newLoca");
			tv_content.setText(locationMsg);
			ToastUtils.show(context, locationMsg);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onClick(View v) {
		final Intent intent = new Intent(Intents.START_LOCSTART_SERVICE);
		switch (v.getId()) {
		case R.id.btn_start:

			startService(intent);
			mPendingIntent = PendingIntent.getService(getApplicationContext(),
					0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
					System.currentTimeMillis(), ALARM_INTERVAL_TIME,
					mPendingIntent);
			ToastUtils.show(context, "定位开始");
			break;
		case R.id.btn_end:
			alarmManager.cancel(mPendingIntent);
			stopService(intent);
			ToastUtils.show(context, "定位结束");
			break;

		case R.id.btn_logout:
			AlertDialog.Builder builder = new Builder(MainActivity.this);
			builder.setMessage("确定退出本次定位服务吗？")
					.setPositiveButton("确认", new Dialog.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							new HttpUtil(getApplicationContext()).new uploadAsyncTask()
									.execute(DBService.getInstance(
											getApplicationContext())
											.loadAllGpsInfoModels());
							stopService(intent);
							alarmManager.cancel(mPendingIntent);
							unregisterReceiver(locationReceiver);
							android.os.Process.killProcess(android.os.Process
									.myPid());

						}

					}).setNegativeButton("取消", new Dialog.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
						}

					}).create().show();

			break;

		default:
			break;
		}

	}
}
