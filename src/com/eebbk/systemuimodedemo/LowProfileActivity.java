package com.eebbk.systemuimodedemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LowProfileActivity extends Activity implements View.OnClickListener {
	
	private View mContent = null;
	private TextView mTv = null;
	private Button mBtnTest = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_custom);
		
		mContent = findViewById(R.id.content);
		mTv = (TextView) findViewById(R.id.tv_1);
		mBtnTest = (Button) findViewById(R.id.btn_test);
		
		mContent.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
		
		mTv.setText("LowProfile SystemUI Activity");
		mBtnTest.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.equals(mBtnTest)) {
			onBtnTest();
		}
	}
	
	private void onBtnTest() {
		finish();
	}

}
