package com.smarter56.waxberry.activity;

import java.util.regex.Pattern;

import com.smarter56.waxberry.R;
import com.smarter56.waxberry.util.SharedPreferencesUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	private EditText etPhoneNo;
	private Button btnLogin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		etPhoneNo = (EditText) findViewById(R.id.et_phoneno);
		btnLogin = (Button) findViewById(R.id.btn_login);
	
		btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String strPhoneNo = etPhoneNo.getText().toString().trim();
				if (isPhoneNumber(strPhoneNo)) {
					new SharedPreferencesUtils(getApplicationContext()).setPhoneNo(strPhoneNo);
					startActivity(new Intent(LoginActivity.this,
							MainActivity.class));
					finish();

				} else {
					Toast.makeText(getApplicationContext(), "请输入正确的手机号吗", 3000)
							.show();
				}

			}
		});

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
