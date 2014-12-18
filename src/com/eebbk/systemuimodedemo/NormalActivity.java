package com.eebbk.systemuimodedemo;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.NinePatch;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NormalActivity extends Activity implements View.OnClickListener {
	
	@SuppressWarnings("unused")
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
		
		mBtnTest.setOnClickListener(this);
		
		// for test .9 file
		//Resources res = getResources();
		//Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.custom_bar_trans);
		//NinePatchDrawable ndr = new NinePatchDrawable(
		//		new NinePatch(bmp, bmp.getNinePatchChunk(), "9-patch"));
		//mTv.setBackground(ndr);
		mTv.setBackgroundResource(R.drawable.custom_bar_trans);
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
