新增加的2种模式：

1： View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY： 
应用进入全屏，隐藏虚拟按键。进行交互操作，不会退出全屏模式。在屏幕底部向上滑动，可以暂停唤出虚拟按（一小段时间后自动隐藏）。

2： View.SYSTEM_UI_FLAG_TRANSLUCENT_BAR：
应用进入全屏，虚拟按键半透明重叠在应用底部。

新增的模式在更新了 sdk 后，可以直接通过 View.xx 来访问。
没有更新 sdk 的话，可以暂自己定义成 framework 一样的值来访问：（还是更新下 sdk 比较好）
public static final int SYSTEM_UI_FLAG_IMMERSIVE_STICKY = 0x00001000;
public static final int SYSTEM_UI_FLAG_TRANSLUCENT_BAR = 0x00000800;


3： View.SYSTEM_UI_FLAG_CUSTOM_BAR_COLOR：
设置这个标志后，系统状态栏会使用应用设置的自定义 drawable 作为背景。可以使用不透明的，也可以使用透明的。自定义透明的背景要在半透明模式下才会有透明效果。在开启这个标志前请使用：

// pkg：包名，就是这个自定义 drawable 所在的 apk 的包名
// resName： 自定义 drawable 的名字，注意资源一定要是 drawable
View.setCustomBarColor(String pkg, String resName)

去设置定义的状态栏背景。否则开启后如果没用设置自定义 drawable 是无效的，或是之前设置的 drawable。

4.2 原有模式：
1： View.SYSTEM_UI_FLAG_HIDE_NAVIGATION：
应用进入全屏，隐藏虚拟按键，但是只要有任何交互操作（例如触摸屏幕），就会退出全屏。

2： View.SYSTEM_UI_FLAG_LOW_PROFILE：
状态栏隐藏，虚拟按钮变成几个不显眼的小原点，只要一点击虚拟按钮，就会还原。


注意：上面这几种模式不要混合一起使用。

效果自己跑 Demo 看。

有问题来找我——胡明明

