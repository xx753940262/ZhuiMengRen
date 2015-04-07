package zhuimengren.zhuimengren.ui;

import android.app.Activity;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import zhuimengren.zhuimengren.R;

/**
 * Created by __追梦人 on 2015/3/19.
 */
public class InitSlidingmenu {
    private SlidingMenu slidingMenu;

    public SlidingMenu initSlidingmenu(Activity activity) {
        // 设置抽屉菜单
        slidingMenu = new SlidingMenu(activity);
        slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN); // 触摸边界拖出菜单
        slidingMenu.setMenu(R.layout.left_drawer_fragment);
        slidingMenu.setSecondaryMenu(R.layout.slidingmenu_right);
        slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        // 将抽屉菜单与主页面关联起来
        slidingMenu.attachToActivity(activity, SlidingMenu.SLIDING_CONTENT);
        return slidingMenu;
    }
}
