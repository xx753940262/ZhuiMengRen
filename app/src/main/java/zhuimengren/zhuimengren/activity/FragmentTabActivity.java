package zhuimengren.zhuimengren.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import zhuimengren.zhuimengren.R;
import zhuimengren.zhuimengren.base.BaseActivity;
import zhuimengren.zhuimengren.ui.InitPullToRefreshListView;
import zhuimengren.zhuimengren.ui.InitSlidingmenu;

/**
 * Created by __追梦人 on 2015/2/15.
 */


public class FragmentTabActivity extends BaseActivity implements OnClickListener {
    private ViewPager vpViewPager = null;
    private SlidingMenu slidingMenu = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frament_tab);
        slidingMenu = new InitSlidingmenu().initSlidingmenu(FragmentTabActivity.this);
        init();
        vpViewPager = new InitPullToRefreshListView().initPullToRefreshListView(FragmentTabActivity.this);
    }

    private void init() {
        findViewById(R.id.bNew).setOnClickListener(this);
        findViewById(R.id.bPersonal).setOnClickListener(this);
        ((TextView) findViewById(R.id.tvTag1)).setOnClickListener(this);
        ((TextView) findViewById(R.id.tvTag2)).setOnClickListener(this);
        ((TextView) findViewById(R.id.tvTag3)).setOnClickListener(this);
        ((RelativeLayout) slidingMenu.findViewById(R.id.setting_btn)).setOnClickListener(this);
        ((ImageView) slidingMenu.findViewById(R.id.imageView1)).setOnClickListener(this);
        ((TextView) slidingMenu.findViewById(R.id.share)).setOnClickListener(this);
        ((TextView) slidingMenu.findViewById(R.id.textView_BaiDuMap)).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvTag1:
                vpViewPager.setCurrentItem(0);
                break;
            case R.id.tvTag2:
                vpViewPager.setCurrentItem(1);
                break;
            case R.id.tvTag3:
                vpViewPager.setCurrentItem(2);
                break;
            case R.id.bNew:
                slidingMenu.showMenu();
                break;
            case R.id.bPersonal:
                slidingMenu.showSecondaryMenu();
                break;
            case R.id.setting_btn:
                Intent intent = new Intent(FragmentTabActivity.this, SettingsActivity.class);
                startActivity(intent);
                this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.imageView1:
                Intent intent1 = new Intent(FragmentTabActivity.this, com.zxing.android.CaptureActivity.class);
                startActivity(intent1);
                break;
            case R.id.share:
                Intent intentshare = new Intent(FragmentTabActivity.this, cn.bidaround.youtui.MainActivity.class);
                startActivity(intentshare);
                break;
            case R.id.textView_BaiDuMap:
                Intent intenttextView_BaiDuMap = new Intent(FragmentTabActivity.this, BaiDuMap.class);
                startActivity(intenttextView_BaiDuMap);
                break;
        }
    }


}
