package zhuimengren.zhuimengren;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;

import zhuimengren.zhuimengren.activity.LoginActivity;
import zhuimengren.zhuimengren.activity.WelcomeActivity;
import zhuimengren.zhuimengren.base.BaseActivity;
import zhuimengren.zhuimengren.config.SharedConfig;

//软件入口，闪屏界面。
public class MainActivity extends BaseActivity {
    private boolean first;    //判断是否第一次打开软件
    private View view;
    private Context context;
    private Animation animation;

    private SharedPreferences shared;
    private static int TIME = 1000;                                        // 进入主程序的延迟时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = View.inflate(this, R.layout.activity_main, null);
        setContentView(view);
        context = this;                            //得到上下文
        shared = new SharedConfig(context).GetConfig();    // 得到配置文件

    }

    @Override
    protected void onResume() {
        into();
        super.onResume();

    }

    @Override

    public void onPause() {
        super.onPause();
    }

    // 进入主程序的方法
    private void into() {

        // 如果网络可用则判断是否第一次进入，如果是第一次则进入欢迎界面
        first = shared.getBoolean("First", true);

        // 设置动画效果是alpha，在anim目录下的alpha.xml文件中定义动画效果
        animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
        // 给view设置动画效果
        view.startAnimation(animation);
        animation.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation arg0) {
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            // 这里监听动画结束的动作，在动画结束的时候开启一个线程，这个线程中绑定一个Handler,并
            // 在这个Handler中调用goHome方法，而通过postDelayed方法使这个方法延迟500毫秒执行，达到
            // 达到持续显示第一屏500毫秒的效果
            @Override
            public void onAnimationEnd(Animation arg0) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent;
                        //如果第一次，则进入引导页WelcomeActivity
                        if (first) {
                            intent = new Intent(MainActivity.this,
                                    WelcomeActivity.class);
                        } else {
                            intent = new Intent(MainActivity.this,
                                    LoginActivity.class);
                        }
                        startActivity(intent);
                        // 设置Activity的切换效果
                        overridePendingTransition(R.anim.in_from_right,
                                R.anim.out_to_left);
                        MainActivity.this.finish();
                    }
                }, TIME);
            }
        });

    }
}
