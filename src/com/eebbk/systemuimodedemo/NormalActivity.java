package com.eebbk.systemuimodedemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NormalActivity extends Activity implements View.OnClickListener {
	
	@SuppressWarnings("unused")
	private View mContent = null;
	@SuppressWarnings("unused")
	private TextView mTv = null;
	private Button mBtnTest = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_normal);
		
		mContent = findViewById(R.id.content);
		mTv = (TextView) findViewById(R.id.tv_1);
		mBtnTest = (Button) findViewById(R.id.btn_test);
		
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
