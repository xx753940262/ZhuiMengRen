package zhuimengren.zhuimengren.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import zhuimengren.zhuimengren.R;
import zhuimengren.zhuimengren.adapter.AdvAdapter;
import zhuimengren.zhuimengren.adapter.MyPagerAdapter;
import zhuimengren.zhuimengren.adapter.NewListAdapter;
import zhuimengren.zhuimengren.base.BaseActivity;
import zhuimengren.zhuimengren.data.FragmentTabActivityList;
import zhuimengren.zhuimengren.inter.IGetJsonDataCallBack;
import zhuimengren.zhuimengren.listener.MyOnPageChangeListener;
import zhuimengren.zhuimengren.listener.MyOnRefreshListener2;

/**
 * Created by __追梦人 on 2015/3/25.
 */
public class InitPullToRefreshListView extends BaseActivity {
    private ViewPager vpViewPager = null;
    private List<View> views = null;
    private AdvViewPager vpAdv = null;
    private ViewGroup vg = null;
    private ImageView[] imageViews = null;
    private List<View> advs = new AdvAdapter().advs;
    private int currentPage = 0;
    private Activity activity;
    private MyOnPageChangeListener myOnPageChangeListener;
    private PullToRefreshListView ptrlvHeadLineNews = null;
    private PullToRefreshListView ptrlvEntertainmentNews = null;
    private PullToRefreshListView ptrlvFinanceNews = null;
    private NewListAdapter newAdapter = null;
    private FragmentTabActivityList fragmentTabActivityList = new FragmentTabActivityList();
    private ArrayList<HashMap<String, String>> list;

    public ViewPager initPullToRefreshListView(Activity activity) {
        this.activity = activity;
        return Init(activity);
    }

    private ViewPager Init(Context context) {
        views = new ArrayList<View>();
        views.add(LayoutInflater.from(context).inflate(R.layout.head_lines, null));
        views.add(LayoutInflater.from(context).inflate(R.layout.entertainment, null));
        views.add(LayoutInflater.from(context).inflate(R.layout.finance, null));
        myOnPageChangeListener = new MyOnPageChangeListener(views.size(), activity);
        vpViewPager = (ViewPager) activity.findViewById(R.id.vpViewPager1);

        vpViewPager.setAdapter(new MyPagerAdapter(views));
        vpViewPager.setOnPageChangeListener(myOnPageChangeListener);

        MyPagerAdapter myPagerAdapter = (MyPagerAdapter) vpViewPager
                .getAdapter();
        View v1 = myPagerAdapter.getItemAtPosition(0);
        View v2 = myPagerAdapter.getItemAtPosition(1);
        View v3 = myPagerAdapter.getItemAtPosition(2);
        ptrlvHeadLineNews = (PullToRefreshListView) v1.findViewById(R.id.ptrlvHeadLineNews);
        ptrlvEntertainmentNews = (PullToRefreshListView) v2.findViewById(R.id.ptrlvEntertainmentNews);
        ptrlvFinanceNews = (PullToRefreshListView) v3.findViewById(R.id.ptrlvFinanceNews);
        fragmentTabActivityList.getDataJson(1, new IGetJsonDataCallBack() {
            @Override
            public void getJsonDataCallBack(String result) {
                list = fragmentTabActivityList.GetJsonToArrayList(result);
                newAdapter = new NewListAdapter(activity, list);
                initPullToRefreshListView(ptrlvHeadLineNews, newAdapter);
                initPullToRefreshListView(ptrlvEntertainmentNews, newAdapter);
                initPullToRefreshListView(ptrlvFinanceNews, newAdapter);
            }
        });

        return vpViewPager;
    }

    /**
     * 初始化PullToRefreshListView<br>
     * 初始化在PullToRefreshListView中的ViewPager广告栏
     *
     * @param rtflv
     * @param adapter
     */
    public void initPullToRefreshListView(PullToRefreshListView rtflv,
                                          NewListAdapter adapter) {
        rtflv.setMode(PullToRefreshBase.Mode.BOTH);
        rtflv.setOnRefreshListener(new MyOnRefreshListener2(rtflv, activity, newAdapter));
        rtflv.setAdapter(adapter);

        if (rtflv.getId() == R.id.ptrlvHeadLineNews) {
            RelativeLayout rlAdv = (RelativeLayout) LayoutInflater.from(activity)
                    .inflate(R.layout.sliding_advertisement, null);
            vpAdv = (AdvViewPager) rlAdv.findViewById(R.id.vpAdv);
            vg = (ViewGroup) rlAdv.findViewById(R.id.viewGroup);

            ImageView iv;
            iv = new ImageView(activity);
            iv.setBackgroundResource(R.drawable.new_img1);
            advs.add(iv);

            iv = new ImageView(activity);
            iv.setBackgroundResource(R.drawable.new_img2);
            advs.add(iv);

            iv = new ImageView(activity);
            iv.setBackgroundResource(R.drawable.new_img3);
            advs.add(iv);

            iv = new ImageView(activity);
            iv.setBackgroundResource(R.drawable.new_img4);
            advs.add(iv);

            vpAdv.setAdapter(new AdvAdapter());
            vpAdv.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                @Override
                public void onPageSelected(int arg0) {
                    currentPage = arg0;
                    for (int i = 0; i < advs.size(); i++) {
                        if (i == arg0) {
                            imageViews[i]
                                    .setBackgroundResource(R.drawable.banner_dian_focus);
                        } else {
                            imageViews[i]
                                    .setBackgroundResource(R.drawable.banner_dian_blur);
                        }
                    }
                }

                @Override
                public void onPageScrolled(int arg0, float arg1, int arg2) {

                }

                @Override
                public void onPageScrollStateChanged(int arg0) {

                }
            });

            imageViews = new ImageView[advs.size()];
            ImageView imageView;
            for (int i = 0; i < advs.size(); i++) {
                imageView = new ImageView(activity);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(20, 20));
                imageViews[i] = imageView;
                if (i == 0) {
                    imageViews[i]
                            .setBackgroundResource(R.drawable.banner_dian_focus);
                } else {
                    imageViews[i]
                            .setBackgroundResource(R.drawable.banner_dian_blur);
                }
                vg.addView(imageViews[i]);
            }

            rtflv.getRefreshableView().addHeaderView(rlAdv, null, false);


            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    vpAdv.setCurrentItem(msg.what);
                    super.handleMessage(msg);
                }
            };
            new Thread(new Runnable() {

                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(5000);
                            currentPage++;
                            if (currentPage > advs.size() - 1) {
                                currentPage = 0;
                            }
                            handler.sendEmptyMessage(currentPage);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }

}
