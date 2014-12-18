package com.eebbk.systemuimodedemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TranslucentActivity extends Activity implements View.OnClickListener {
	
	public static final int SYSTEM_UI_FLAG_IMMERSIVE_STICKY = 0x00000008;
	public static final int SYSTEM_UI_FLAG_TRANSLUCENT_BAR = 0x00000800;
	
	private View mContent = null;
	private TextView mTv = null;
	private Button mBtnTest = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_normal);
		
		mContent = findViewById(R.id.content);
		mTv = (TextView) findViewById(R.id.tv_1);
		mBtnTest = (Button) findViewById(R.id.btn_test);
		
		mContent.setSystemUiVisibility(SYSTEM_UI_FLAG_TRANSLUCENT_BAR);
		
		mTv.setText("Translucent SystemUI Activity");
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
