package com.eebbk.systemuimodedemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {
	
	// or you may define it yourself -_-||.
	public static final int SYSTEM_UI_FLAG_TRANSLUCENT_BAR  = 0x00000800;
	public static final int SYSTEM_UI_FLAG_IMMERSIVE_STICKY = 0x00001000;
	public static final int SYSTEM_UI_FLAG_CUSTOM_BAR_COLOR = 0x00002000;
	// after update sdk, you can access View.xx directly.
	//public static final int SYSTEM_UI_FLAG_IMMERSIVE_STICKY = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
	//public static final int SYSTEM_UI_FLAG_TRANSLUCENT_BAR = View.SYSTEM_UI_FLAG_TRANSLUCENT_BAR;
	
    private final static int MSG_UI_CHANGE_SYSTEM_FLAG = 100;
    private final static int MSG_UI_POPUP_WINDOW = 101;
	
	private Rect mRect = new Rect();
	
	private MyContent mContent;
	private TextView mTvSizeInfo;
	
	private ListView mLvTest;
	private String[] mStrings = Cheeses.sCheeseStrings;
	
	private CheckBox mCbModeShowFullScreen;
	private CheckBox mCbModeHideNav;
	private CheckBox mCbModeTransSystemUI;
	private CheckBox mCbModeLowProfile;
	private CheckBox mCbModeCustomBarColor;
	
	private Button mBtnShowNotification;
	private Button mBtnGoToNormal;
	private Button mBtnGoToLowProfile;
	private Button mBtnGoToFullScreen;
	private Button mBtnGoToTranslucent;
	private Button mBtnGoToWallpaper;
	private Button mBtnCustomOpaque;
	private Button mBtnCustomTrans;
	
    private Button mBtnClick;
    private boolean mPopup = false;
    
    private PopupWindow mWin = null;
    private Handler mUIHandler = null;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mContent = (MyContent) findViewById(R.id.content);
		mTvSizeInfo = (TextView) findViewById(R.id.tv_size_info);
		mLvTest = (ListView) findViewById(R.id.lv_test);
		
		mCbModeShowFullScreen = (CheckBox) findViewById(R.id.cb_mode_show_full_screen);
		mCbModeHideNav = (CheckBox) findViewById(R.id.cb_mode_hide_nav);
		mCbModeTransSystemUI = (CheckBox) findViewById(R.id.cb_mode_trans_system_ui);
		mCbModeLowProfile = (CheckBox) findViewById(R.id.cb_mode_low_profile);
		mCbModeCustomBarColor = (CheckBox) findViewById(R.id.cb_mode_custom_bar_color);
		
		mBtnShowNotification = (Button) findViewById(R.id.btn_show_notification);
		mBtnGoToNormal = (Button) findViewById(R.id.btn_goto_normal);
		mBtnGoToLowProfile = (Button) findViewById(R.id.btn_goto_low_profile);
		mBtnGoToFullScreen = (Button) findViewById(R.id.btn_goto_full_screen);
		mBtnGoToTranslucent = (Button) findViewById(R.id.btn_goto_translucent);
		mBtnGoToWallpaper = (Button) findViewById(R.id.btn_goto_wallpaper);
		mBtnCustomOpaque = (Button) findViewById(R.id.btn_custom_opaque);
		mBtnCustomTrans = (Button) findViewById(R.id.btn_custom_trans);
		mBtnClick = (Button) findViewById(R.id.btn_click);
		
        mLvTest.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mStrings));
		
		mContent.setupListener();
		
		mCbModeShowFullScreen.setOnClickListener(this);
		mCbModeHideNav.setOnClickListener(this);
		mCbModeTransSystemUI.setOnClickListener(this);
		mCbModeLowProfile.setOnClickListener(this);
		mCbModeCustomBarColor.setOnClickListener(this);
		
		mBtnShowNotification.setOnClickListener(this);
		mBtnGoToNormal.setOnClickListener(this);
		mBtnGoToLowProfile.setOnClickListener(this);
		mBtnGoToFullScreen.setOnClickListener(this);
		mBtnGoToTranslucent.setOnClickListener(this);
		mBtnGoToWallpaper.setOnClickListener(this);
		mBtnCustomOpaque.setOnClickListener(this);
		mBtnCustomTrans.setOnClickListener(this);
		mBtnClick.setOnClickListener(this);
		
		initHandler();
	}
	
    private void initHandler() {
        if (null == mUIHandler) {
            mUIHandler = new Handler(new Handler.Callback() {

                @Override
                public boolean handleMessage(Message msg) {
                    try {
                        switch (msg.what) {
                        case MSG_UI_CHANGE_SYSTEM_FLAG:
                            handleUIChangeSystemFlag();
                            break;

                        default:
                            break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return false;
                }

            });

        }
    }

    private void handleUIPopupWindow() {
        popupWindow();
        handleUIChangeSystemFlag();
        mPopup = false;
    }

    private void handleUIChangeSystemFlag() {
        View content = mWin.getContentView();
        //View parent = (View) content.getParent();
        //if (null == parent) {
        //  parent = content;
        //}
        //Log.d("test", "parent view=" + parent);
        int vis = content.getSystemUiVisibility();
        //if (0 == (vis & View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) 
        //      || 0 == (vis & View.SYSTEM_UI_FLAG_TRANSLUCENT_BAR)) {
        //  vis |= (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_TRANSLUCENT_BAR);
        //  content.setSystemUiVisibility(vis);
        //}
        vis &= ~(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_TRANSLUCENT_BAR);
        content.setSystemUiVisibility(vis);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        if (mPopup) {
            mUIHandler.sendEmptyMessageDelayed(MSG_UI_POPUP_WINDOW, 500);
            //popupWindow();
            //handleUIChangeSystemFlag();
            //mUIHandler.sendEmptyMessageDelayed(MSG_UI_CHANGE_SYSTEM_FLAG, 2000);
            //mPopup = false;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPopup = true;
    }
	
    @Override
    public void onAttachedToWindow() {
    	updateStatus();
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
    	super.onConfigurationChanged(newConfig);
    	
    	Drawable bk = getResources().getDrawable(R.drawable.test_bk);
    	mContent.setBackground(bk);
    	
    	updateStatus();
    }
	
	@Override
	public void onClick(View v) {
		if (v.equals(mCbModeShowFullScreen)) {
			switchShowFullScreen(mCbModeShowFullScreen.isChecked());
		} else if (v.equals(mCbModeHideNav)) {
			switchHideNav(mCbModeHideNav.isChecked());
		} else if (v.equals(mCbModeTransSystemUI)) {
			switchTransSystemUI(mCbModeTransSystemUI.isChecked());
		} else if (v.equals(mCbModeLowProfile)) {
			switchLowProfile(mCbModeLowProfile.isChecked());
		} else if (v.equals(mCbModeCustomBarColor)) {
			switchCustomBarColor(mCbModeCustomBarColor.isChecked());
		}
		
		if (v.equals(mBtnShowNotification)) {
			showNotification();
		} else if (v.equals(mBtnGoToNormal)) {
			goToNormal();
		} else if (v.equals(mBtnGoToLowProfile)) {
			goToLowProfile();
		} else if (v.equals(mBtnGoToFullScreen)) {
			goToFullScreen();
		} else if (v.equals(mBtnGoToTranslucent)) {
			goToTranslucent();
		} else if (v.equals(mBtnGoToWallpaper)) {
			goToWallpaper();
		} else if (v.equals(mBtnCustomOpaque)) {
			setCustomBarOpaque();
		} else if (v.equals(mBtnCustomTrans)) {
			setCustomBarTrans();
		} else if (v.equals(mBtnClick)) {
			popupWindow();
		}
	}
	
    private String getDisplaySize() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        return String.format("DisplayMetrics = (%d x %d)", dm.widthPixels, dm.heightPixels);
    }
    
    private String getViewSize() {
        return String.format("View = (%d,%d - %d,%d)",
                mContent.getLeft(), mContent.getTop(),
                mContent.getRight(), mContent.getBottom());
    }
    
    private String getWindowSize() {
        Window win = getWindow();
        win.getDecorView().getWindowVisibleDisplayFrame(mRect);
        return String.format("win = (%d,%d - %d,%d)",
        		mRect.left, mRect.top,
        		mRect.right, mRect.bottom);
    }
    
    private void refreshSizes() {
    	mTvSizeInfo.setText(getDisplaySize() + ", " + getWindowSize() + ", " + getViewSize());
    }
    
    private void updateStatus() {
    	final int systemUIVis = mContent.getSystemUiVisibility();
    	int is = systemUIVis & SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
    	int ts = systemUIVis & SYSTEM_UI_FLAG_TRANSLUCENT_BAR;
    	if (0 != is) {
    		mCbModeShowFullScreen.setChecked(true);
    	} else {
    		mCbModeShowFullScreen.setChecked(false);
    	}
    	if ((systemUIVis & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) != 0) {
    		mCbModeHideNav.setChecked(true);
    	} else {
    		mCbModeHideNav.setChecked(false);
    	}
    	if (0 != ts) {
    		mCbModeTransSystemUI.setChecked(true);
    	} else {
    		mCbModeTransSystemUI.setChecked(false);
    	}
    	if ((systemUIVis & View.SYSTEM_UI_FLAG_LOW_PROFILE) != 0) {
    		mCbModeLowProfile.setChecked(true);
    	} else {
    		mCbModeLowProfile.setChecked(false);
    	}
    	if ((systemUIVis & SYSTEM_UI_FLAG_CUSTOM_BAR_COLOR) != 0) {
    		mCbModeCustomBarColor.setChecked(true);
    	} else {
    		mCbModeCustomBarColor.setChecked(false);
    	}
    }
    
    private void switchShowFullScreen(boolean on) {
    	int systemUIVis = mContent.getSystemUiVisibility();
    	if (on) {
    		systemUIVis |= SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
    	} else {
    		systemUIVis &= ~SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
    	}
    	mContent.setSystemUiVisibility(systemUIVis);
    }
    
    private void switchHideNav(boolean on) {
    	int systemUIVis = mContent.getSystemUiVisibility();
    	if (on) {
    		systemUIVis |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    	} else {
    		systemUIVis &= ~View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    	}
    	mContent.setSystemUiVisibility(systemUIVis);
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
    
    private void switchLowProfile(boolean on) {
    	int systemUIVis = mContent.getSystemUiVisibility();
    	if (on) {
    		systemUIVis |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
    	} else {
    		systemUIVis &= ~View.SYSTEM_UI_FLAG_LOW_PROFILE;
    	}
    	mContent.setSystemUiVisibility(systemUIVis);
    }
    
    private void switchCustomBarColor(boolean on) {
    	int systemUIVis = mContent.getSystemUiVisibility();
    	if (on) {
    		systemUIVis |= SYSTEM_UI_FLAG_CUSTOM_BAR_COLOR;
    	} else {
    		systemUIVis &= ~SYSTEM_UI_FLAG_CUSTOM_BAR_COLOR;
    	}
    	mContent.setSystemUiVisibility(systemUIVis);
    }
    
    private void showNotification() {
        NotificationManager nm = (NotificationManager) 
        		this.getSystemService(Context.NOTIFICATION_SERVICE);
        
        int ID = 100;
        
        Intent viewIntent = new Intent(this, MainActivity.class);
        viewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | 
        		Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | 
        		Intent.FLAG_ACTIVITY_SINGLE_TOP | 
        		Intent.FLAG_ACTIVITY_CLEAR_TOP);
        viewIntent.setAction(Intent.ACTION_MAIN);
        viewIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        
        PendingIntent intent = PendingIntent.getActivity(
        		this, ID, viewIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        
        Notification n = null;
        n = new Notification.Builder(this).
        		setContentTitle("Title haha").
        		setContentText("bo fang ying yue").
        		setSmallIcon(R.drawable.ic_launcher).
        		setContentIntent(intent).
        		setTicker("coming a notification").
        		build();
        
        n.flags |= Notification.FLAG_AUTO_CANCEL;
        //n.flags |= Notification.FLAG_NO_CLEAR;
        
        nm.cancel(ID);
        nm.notify(ID, n);
    }
    
    private void goToNormal() {
    	Intent intent = new Intent(this, NormalActivity.class);
    	startActivity(intent);
    }
    
    private void goToLowProfile() {
    	Intent intent = new Intent(this, LowProfileActivity.class);
    	startActivity(intent);
    }
    
    private void goToFullScreen() {
    	Intent intent = new Intent(this, FullScreenActivity.class);
    	startActivity(intent);
    }
    
    private void goToTranslucent() {
    	Intent intent = new Intent(this, TranslucentActivity.class);
    	startActivity(intent);
    }

    private void goToWallpaper() {
    	Intent intent = new Intent(this, WallpaperActivity.class);
    	startActivity(intent);
    }
    
    private void setCustomBarOpaque() {
    	// the resource must be drawable !!
    	Utils.setCustomBarColor(mContent, getPackageName(), "custom_bar_opaque");
    }
    
    private void setCustomBarTrans() {
    	// the resource must be drawable !!
    	Utils.setCustomBarColor(mContent, getPackageName(), "custom_bar_trans");
    }
    
    private void popupWindow() {
        LayoutInflater inflater = getLayoutInflater();
        View content = inflater.inflate(R.layout.popup_view, null);
        PopupWindow win = new PopupWindow(this);
        win.setWidth(200);
        win.setHeight(150);
        win.setContentView(content);
        win.setOutsideTouchable(true);
        win.setFocusable(true);
        win.showAsDropDown(mBtnClick);
        mWin = win;
            
        /*View parent = (View) content.getParent();
        if (null == parent) {
            parent = content;
        }
        Log.d("test", "parent view=" + parent);
        int vis = parent.getSystemUiVisibility();
        vis &= ~View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        vis &= ~View.SYSTEM_UI_FLAG_TRANSLUCENT_BAR;
        parent.setSystemUiVisibility(vis);*/
    }
    
    public static class MyContent extends RelativeLayout implements View.OnSystemUiVisibilityChangeListener {
    	
    	private MainActivity mContext = null;
    	
		public MyContent(Context context) {
			super(context);
			init(context);
		}
    	
		public MyContent(Context context, AttributeSet attrs) {
			super(context, attrs);
			init(context);
		}
		
		public MyContent(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
			init(context);
		}
		
		private void init(Context context) {
			mContext = (MainActivity) context;
		}
		
		private void setupListener() {
			setOnSystemUiVisibilityChangeListener(this);
		}
		
        @Override
        public void onSizeChanged(int w, int h, int oldw, int oldh) {
        	Log.d("test", String.format("MyContent onSizeChanged: w=%d, h=%d, oldw=%d, oldh=%d",
        			w, h, oldw, oldh));
        	mContext.refreshSizes();
        }
        @Override
        public void onSystemUiVisibilityChange(int visibility) {
        	Log.d("test", String.format("MyContent onSystemUiVisibilityChange: vis=0x%x", visibility));
        	mContext.updateStatus();
        	mContext.refreshSizes();
        }
    	
    }
    
}
