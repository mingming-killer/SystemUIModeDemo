package com.eebbk.systemuimodedemo;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class WallpaperActivity extends Activity implements View.OnClickListener {
	
	public static final int SYSTEM_UI_FLAG_IMMERSIVE_STICKY = 0x00000008;
	public static final int SYSTEM_UI_FLAG_TRANSLUCENT_BAR = 0x00000800;
	
	private View mContent = null;
	private TextView mTv = null;
	private Button mBtnTest = null;
	private CheckBox mCbModeTransSystemUI;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wallpaper);
		
		mContent = findViewById(R.id.content);
		mTv = (TextView) findViewById(R.id.tv_1);
		mBtnTest = (Button) findViewById(R.id.btn_test);
		mCbModeTransSystemUI = (CheckBox) findViewById(R.id.cb_mode_trans_system_ui);
		
		//mContent.setSystemUiVisibility(SYSTEM_UI_FLAG_TRANSLUCENT_BAR);
		//switchTransSystemUI(true);
		
		mTv.setText("Wallpaper Activity");
		mBtnTest.setOnClickListener(this);
		mCbModeTransSystemUI.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.equals(mBtnTest)) {
			onBtnTest();
		} else if (v.equals(mCbModeTransSystemUI)) {
			if (Build.VERSION.SDK_INT >= 19) {
				setTranslucentStatus(mCbModeTransSystemUI.isChecked());
			} else {
				switchTransSystemUI(mCbModeTransSystemUI.isChecked());
			}
		}
	}
	
	private void onBtnTest() {
		finish();
	}
	
    private void switchTransSystemUI(boolean on) {
    	int systemUIVis = mContent.getSystemUiVisibility();
    	if (on) {
    		systemUIVis |= SYSTEM_UI_FLAG_TRANSLUCENT_BAR;
    	} else {
    		systemUIVis &= ~SYSTEM_UI_FLAG_TRANSLUCENT_BAR;
    	}
    	mContent.setSystemUiVisibility(systemUIVis);
    }
    
    // This is for test in real 4.4
	public static final int FLAG_TRANSLUCENT_STATUS = 0x04000000;
	public static final int FLAG_TRANSLUCENT_NAVIGATION = 0x08000000;
	
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        //final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        final int bits = (FLAG_TRANSLUCENT_STATUS | FLAG_TRANSLUCENT_NAVIGATION);
        if (on) {
            winParams.flags |=  bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

}
