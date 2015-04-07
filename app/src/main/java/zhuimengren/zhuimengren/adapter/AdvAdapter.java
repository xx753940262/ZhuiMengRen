package zhuimengren.zhuimengren.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/3/15.
 */
public class AdvAdapter extends PagerAdapter {
    public static List<View> advs = new ArrayList<View>();
    @Override
    public int getCount() {
        return advs.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager) container).removeView(advs.get(position));
    }

    @Override
    public Object instantiateItem(View container, int position) {
        ((ViewPager) container).addView(advs.get(position));
        return advs.get(position);
    }

}

