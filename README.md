新增加的2种模式：

1： View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY： 
应用进入全屏，隐藏虚拟按键。进行交互操作，不会退出全屏模式。在屏幕底部向上滑动，可以暂停唤出虚拟按（一小段时间后自动隐藏）。

2： View.SYSTEM_UI_FLAG_TRANSLUCENT_BAR：
应用进入全屏，虚拟按键半透明重叠在应用底部。

新增的模式在更新了 sdk 后，可以直接通过 View.xx 来访问。
没有更新 sdk 的话，可以暂自己定义成 framework 一样的值来访问：（还是更新下 sdk 比较好）
public static final int SYSTEM_UI_FLAG_IMMERSIVE_STICKY = 0x00001000;
public static final int SYSTEM_UI_FLAG_TRANSLUCENT_BAR = 0x00000800;


--------------------------------------------------------
--------------------------------------------------------


3： View.SYSTEM_UI_FLAG_CUSTOM_BAR_COLOR：
设置这个标志后，能够自定义系统状态栏的一些元素。在开启这个标志前请使用：

// pkg：包名，就是这个自定义 drawable 所在的 apk 的包名
// types：自定义的元素类型
// resName： 自定义 drawable 的名字，注意资源一定要是 drawable
View.setCustomBarColor(String pkg, int[] types String[] resNames)

type 的目前支持的类型有：
    public static final int STATUS_BAR_BACKGROUND = View.STATUS_BAR_BACKGROUND;
    public static final int STATUS_BAR_CLOCK = View.STATUS_BAR_CLOCK;
    public static final int STATUS_BAR_BATTERY = View.STATUS_BAR_BATTERY;
    public static final int STATUS_BAR_BATTERY_CHARGE = View.STATUS_BAR_BATTERY_CHARGE;
    public static final int STATUS_BAR_WIFI = View.STATUS_BAR_WIFI;
    public static final int STATUS_BAR_WIFI_ACT = View.STATUS_BAR_WIFI_ACT;
    public static final int STATUS_BAR_ALARM = View.STATUS_BAR_ALARM;
    public static final int STATUS_BAR_SOUND = View.STATUS_BAR_SOUND;

resNames 要和 types 一一对应，然后每种 type 的 drawable 类型都要和原来系统使用的类型一致。具体的可以看 demo 里面的例子。资源类型照着 demo 的模板来就行了。


--------------------------------------------------------
--------------------------------------------------------

4.2 原有模式：
1： View.SYSTEM_UI_FLAG_HIDE_NAVIGATION：
应用进入全屏，隐藏虚拟按键，但是只要有任何交互操作（例如触摸屏幕），就会退出全屏。

2： View.SYSTEM_UI_FLAG_LOW_PROFILE：
状态栏隐藏，虚拟按钮变成几个不显眼的小原点，只要一点击虚拟按钮，就会还原。


注意：上面这几种模式不要混合一起使用。

效果自己跑 Demo 看。

有问题来找我——胡明明

