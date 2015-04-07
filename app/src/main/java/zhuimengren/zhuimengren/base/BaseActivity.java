package zhuimengren.zhuimengren.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import zhuimengren.zhuimengren.AppManager;
import zhuimengren.zhuimengren.dialog.ToastDialog;

/**
 * 应用程序Activity的基类
 * Created by Administrator on 2015/3/11.
 */
public class BaseActivity extends Activity {
    private boolean isExit = false; // 标记是否要退出
    private Timer timer = null;
    private Context context;
    private TimerTask timeTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timer = new Timer();
        //添加Activity到堆栈
        AppManager.getAppManager().addActivity(this);
        ToastDialog.isShow = true;//ToastDialog全局管理，当为true时显示，为false时不显示
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //结束Activity&从堆栈中移除
        AppManager.getAppManager().finishActivity(this);
    }

    @Override
    public void onBackPressed() {
        if (isExit) {
            AppManager.getAppManager().finishActivity();
        } else {
            isExit = true;
            Toast.makeText(this, "再按一次退出_追梦人", Toast.LENGTH_SHORT)
                    .show();
            timeTask = new TimerTask() {

                @Override
                public void run() {
                    isExit = false;
                }
            };
            timer.schedule(timeTask, 2000);
        }
    }

    /*
     * 返回
     */
    public void doBack(View view) {
        AppManager.getAppManager().finishActivity();
    }
}
