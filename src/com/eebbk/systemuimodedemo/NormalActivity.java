package com.eebbk.systemuimodedemo;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class NormalActivity extends Activity implements View.OnClickListener {
	
	@SuppressWarnings("unused")
	private View mContent = null;
	private ImageView mIvTest1 = null;
	private ImageView mIvTest2 = null;
	private Button mBtnTest = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_normal);
		
		mContent = findViewById(R.id.content);
		mBtnTest = (Button) findViewById(R.id.btn_test);
		mIvTest1 = (ImageView) findViewById(R.id.iv_test1);
		mIvTest2 = (ImageView) findViewById(R.id.iv_test2);
		
		mBtnTest.setOnClickListener(this);
		
		Drawable dr1 = getResources().getDrawable(R.drawable.stat_sys_wifi);
		dr1.setLevel(5);
		Drawable wifiDr = dr1.getCurrent();
		mIvTest1.setImageDrawable(wifiDr);
		
		Drawable dr2 = getResources().getDrawable(R.drawable.stat_sys_wifi_act);
		dr2.setLevel(0);
		Drawable wifiActDr = dr2.getCurrent();
		mIvTest2.setImageDrawable(wifiActDr);
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
