package zhuimengren.zhuimengren.listener;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import zhuimengren.zhuimengren.R;

/**
 * Created by __追梦人 on 2015/3/24.
 */
public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
    private int offset; // 间隔
    private int cursorWidth; // 游标的长度
    private int originalIndex = 0;
    private ImageView cursor = null;
    private Animation animation = null;
    private Activity activity;

    public MyOnPageChangeListener(int tagNum, Activity activity) {
        this.activity = activity;
        initCursor(tagNum, activity);
    }

    /**
     * 根据tagd的数量初始化游标的位置
     */
    public void initCursor(int tagNum, Activity activity) {
        cursorWidth = BitmapFactory.decodeResource(activity.getResources(),
                R.drawable.cursor).getWidth();
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        offset = ((dm.widthPixels / tagNum) - cursorWidth) / 2;

        cursor = (ImageView) activity.findViewById(R.id.ivCursor);
        Matrix matrix = new Matrix();
        matrix.setTranslate(offset, 0);
        cursor.setImageMatrix(matrix);
    }

    @Override
    public void onPageSelected(int arg0) {
        int one = 2 * offset + cursorWidth;
        int two = one * 2;

        switch (originalIndex) {
            case 0:
                if (arg0 == 1) {
                    animation = new TranslateAnimation(0, one, 0, 0);
                }
                if (arg0 == 2) {
                    animation = new TranslateAnimation(0, two, 0, 0);
                }
                break;
            case 1:
                if (arg0 == 0) {
                    animation = new TranslateAnimation(one, 0, 0, 0);
                }
                if (arg0 == 2) {
                    animation = new TranslateAnimation(one, two, 0, 0);
                }
                break;
            case 2:
                if (arg0 == 1) {
                    animation = new TranslateAnimation(two, one, 0, 0);
                }
                if (arg0 == 0) {
                    animation = new TranslateAnimation(two, 0, 0, 0);
                }
                break;
        }
        animation.setFillAfter(true);
        animation.setDuration(300);
        cursor.startAnimation(animation);
        originalIndex = arg0;
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

}
