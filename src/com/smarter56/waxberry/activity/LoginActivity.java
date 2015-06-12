package com.smarter56.waxberry.activity;

import java.util.regex.Pattern;

import com.smarter56.waxberry.R;
import com.smarter56.waxberry.util.SharedPreferencesUtils;
import com.smarter56.waxberry.util.ToastUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author Ricky
 */
public class LoginActivity extends Activity {
	private EditText etPhoneNo, etVehicleNo;
	private Button btnLogin;
	private SharedPreferencesUtils utils;
	private Activity activity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		activity = LoginActivity.this;
		utils = new SharedPreferencesUtils(activity);
		if (utils.getAutoLogin()) {
			startActivity(new Intent(LoginActivity.this, MainActivity.class));
			finish();
		}
		etPhoneNo = (EditText) findViewById(R.id.et_phoneno);
		etVehicleNo = (EditText) findViewById(R.id.et_vehicleno);
		btnLogin = (Button) findViewById(R.id.btn_login);

		btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String strPhoneNo = etPhoneNo.getText().toString().trim();
				String strVehicleNo = etVehicleNo.getText().toString().trim();
				if (!isPhoneNumber(strPhoneNo)) {
					ToastUtils.show(activity, "请输入正确的手机号码！");
					return;
				}
				if (isBlank(strVehicleNo)) {
					ToastUtils.show(activity, "车牌号不能为空！");
					return;
				}
				utils.setAutoLogin();
				utils.setPhoneNo(strPhoneNo);
				utils.setVehicleNo(strVehicleNo);
				utils.setTotalMeters(0);
				startActivity(new Intent(LoginActivity.this, MainActivity.class));
				finish();

			}
		});

	}

	public void check(String phoneNo, String vehicelNo) {

	}

	/**
	 * 字符串为 null 或者为 "" 时返回 true
	 */
	public static boolean isBlank(String str) {
		return str == null || "".equals(str.trim()) ? true : false;
	}

	/**
	 * if the str is a phone number
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isPhoneNumber(String str) {
		Pattern pattern = Pattern.compile("^[1][3, 4, 5, 6, 7, 8][0-9]{9}$");
		return pattern.matcher(str).matches();
	}

}
