package zhuimengren.zhuimengren.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.List;

/**
 * Created by __追梦人 on 2015/3/15.
 */
public class MyPagerAdapter extends PagerAdapter {
    private List<View> views;

    public MyPagerAdapter(List<View> views) {
        this.views = views;
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        // 将指定的view从viewPager中移除
        ((ViewPager) container).removeView(views.get(position));
    }

    @Override
    public Object instantiateItem(View container, int position) {
        // 将view添加到viewPager中
        ((ViewPager) container).addView(views.get(position));
        return views.get(position);
    }

    public View getItemAtPosition(int position) {
        return views.get(position);
    }

}
