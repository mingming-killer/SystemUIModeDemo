package com.eebbk.systemuimodedemo;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class GetCustomActivity extends Activity implements View.OnClickListener {
	
	// or you may define it yourself -_-||.
	public static final int SYSTEM_UI_FLAG_TRANSLUCENT_BAR  = 0x00000800;
	public static final int SYSTEM_UI_FLAG_IMMERSIVE_STICKY = 0x00001000;
	public static final int SYSTEM_UI_FLAG_CUSTOM_BAR_COLOR = 0x00002000;
	
    // this is custom status bar elements: 
    public static final int STATUS_BAR_BACKGROUND = 0;
    public static final int STATUS_BAR_CLOCK = 1;
    public static final int STATUS_BAR_BATTERY = 2;
    public static final int STATUS_BAR_BATTERY_CHARGE = 3;
    public static final int STATUS_BAR_BATTERY_NO_HEALTH = 4;
    public static final int STATUS_BAR_WIFI = 5;
    public static final int STATUS_BAR_WIFI_ACT = 6;
    public static final int STATUS_BAR_ALARM = 7;
    public static final int STATUS_BAR_SOUND = 8;
    public static final int STATUS_BAR_CUSTOM_MAX_ITEM = 9;
	
	private View mContent = null;
	
	private Button mBtnBack = null;
	private Button mBtnSet = null;
	
	private ImageView mIvBackground = null;
	private ImageView mIvClock = null;
	private ImageView mIvBattery = null;
	private ImageView mIvBatteryCharge = null;
	private ImageView mIvBatteryNoHealth = null;
	private ImageView mIvWifi = null;
	private ImageView mIvWifiAct = null;
	private ImageView mIvAlarm = null;
	private ImageView mIvSound = null;
	private ImageView[] mIvShows = new ImageView[STATUS_BAR_CUSTOM_MAX_ITEM];
	
	private String[] mCustomColors = new String[STATUS_BAR_CUSTOM_MAX_ITEM];
	private int[] mCustomTypes = new int[] {
		STATUS_BAR_BACKGROUND,
		STATUS_BAR_CLOCK,
		STATUS_BAR_BATTERY,
		STATUS_BAR_BATTERY_CHARGE,
		STATUS_BAR_BATTERY_NO_HEALTH,
		STATUS_BAR_WIFI,
		STATUS_BAR_WIFI_ACT,
		STATUS_BAR_ALARM,
		STATUS_BAR_SOUND,	
	};
	
	private Context mCustomCtx = null;
	private Resources mCustomRes = null;
	private String mCustomPkg = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_custom);
		
		mContent = findViewById(R.id.content);
		
		mBtnBack = (Button) findViewById(R.id.btn_back);
		mBtnSet = (Button) findViewById(R.id.btn_set);
		
		mIvBackground = (ImageView) findViewById(R.id.iv_background);
		mIvClock = (ImageView) findViewById(R.id.iv_clock);
		mIvBattery = (ImageView) findViewById(R.id.iv_battery);
		mIvBatteryCharge = (ImageView) findViewById(R.id.iv_battery_charge);
		mIvBatteryNoHealth = (ImageView) findViewById(R.id.iv_battery_no_health);
		mIvWifi = (ImageView) findViewById(R.id.iv_wifi);
		mIvWifiAct = (ImageView) findViewById(R.id.iv_wifi_act);
		mIvAlarm = (ImageView) findViewById(R.id.iv_alarm);
		mIvSound = (ImageView) findViewById(R.id.iv_sound);
		
		mIvShows[STATUS_BAR_BACKGROUND] = mIvBackground;
		mIvShows[STATUS_BAR_CLOCK] = mIvClock;
		mIvShows[STATUS_BAR_BATTERY] = mIvBattery;
		mIvShows[STATUS_BAR_BATTERY_CHARGE] = mIvBatteryCharge;
		mIvShows[STATUS_BAR_BATTERY_NO_HEALTH] = mIvBatteryNoHealth;
		mIvShows[STATUS_BAR_WIFI] = mIvWifi;
		mIvShows[STATUS_BAR_WIFI_ACT] = mIvWifiAct;
		mIvShows[STATUS_BAR_ALARM] = mIvAlarm;
		mIvShows[STATUS_BAR_SOUND] = mIvSound;
		
		mBtnBack.setOnClickListener(this);
		mBtnSet.setOnClickListener(this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		getLastCustomBarColor();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		// when the activity is onPause we cancel the custom bar color
	    int systemUIVis = mContent.getSystemUiVisibility();
	    systemUIVis &= ~SYSTEM_UI_FLAG_CUSTOM_BAR_COLOR;
	    mContent.setSystemUiVisibility(systemUIVis);
	}
	
	private void getLastCustomBarColor() {
		String[] customInfos = Utils.getLastCustomBarColor(mContent);
		if (null == customInfos) {
			Log.d("test", "getLastCustomBarColor return null !");
			return;
		}
		
		// get custom provider package.
		// this is in the first position in return datas.
		try {
			mCustomPkg = customInfos[0];
			mCustomCtx = createPackageContext(mCustomPkg, 0);
			mCustomRes = mCustomCtx.getResources();
			Log.d("test", "get custom provider pkg=" + mCustomPkg
					+ ", customCtx=" + mCustomCtx + ", customRes=" + mCustomRes);
		} catch (Exception e) {
			e.printStackTrace();
			mCustomPkg = null;
			mCustomCtx = null;
			mCustomRes = null;
		}
		if (null == mCustomRes) {
			Log.d("test", "getLastCustomBarColor load provider context and resources failed !");
			return;
		}
		
		// and then is the custom bar items
		for (int i = 0; i < STATUS_BAR_CUSTOM_MAX_ITEM; i++) {
			try {
				ImageView iv = mIvShows[i];
				mCustomColors[i] = customInfos[1 + i];
				Drawable customDr = Utils.loadCustomBarColor(mCustomPkg, mCustomRes, mCustomColors[i]);
				Log.d("test", "getLastCustomBarColor: No." + i + ", resName=" + mCustomColors[i] 
						+ ", drawable=" + customDr);
				iv.setImageDrawable(customDr);
			} catch (Exception e) {
				mCustomColors[i] = null;
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onClick(View v) {
		if (v.equals(mBtnBack)) {
			finish();
		} else if (v.equals(mBtnSet)) {
			onBtnSetClicked();
		}
	}
	
	private void onBtnSetClicked() {
		Utils.setCustomBarColor(mContent, mCustomPkg, 
				mCustomTypes, mCustomColors);
		
    	int systemUIVis = mContent.getSystemUiVisibility();
    	systemUIVis |= SYSTEM_UI_FLAG_CUSTOM_BAR_COLOR;
    	mContent.setSystemUiVisibility(systemUIVis);
	}
	
}
