package zhuimengren.zhuimengren.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import zhuimengren.zhuimengren.AppManager;
import zhuimengren.zhuimengren.MainActivity;
import zhuimengren.zhuimengren.R;
import zhuimengren.zhuimengren.activity.LoginDialogActivity;
import zhuimengren.zhuimengren.activity.SettingsActivity;

/**
 * 应用程序UI工具包：封装UI相关的一些操作
 * Created by __追梦人 on 2015/3/11.
 */
public class UIHelper {
    /**
     * 显示登录页面
     *
     */
    public static void showLoginDialog(Context context)
    {
        Intent intent = new Intent(context,LoginDialogActivity.class);
        if(context instanceof MainActivity)
            intent.putExtra("LOGINTYPE", LoginDialogActivity.LOGIN_MAIN);
        else if(context instanceof SettingsActivity)
            intent.putExtra("LOGINTYPE", LoginDialogActivity.LOGIN_SETTING);
        else
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    /**
     * 发送App异常崩溃报告
     * @param cont
     * @param crashReport
     */
    public static void sendAppCrashReport(final Context cont, final String crashReport)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(cont);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setTitle(R.string.app_error);
        builder.setMessage(R.string.app_error_message);
        builder.setPositiveButton(R.string.submit_report, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //发送异常报告
                Intent i = new Intent(Intent.ACTION_SEND);
                //i.setType("text/plain"); //模拟器
                i.setType("message/rfc822") ; //真机
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"jxsmallmouse@163.com"});
                i.putExtra(Intent.EXTRA_SUBJECT,"开源中国Android客户端 - 错误报告");
                i.putExtra(Intent.EXTRA_TEXT,crashReport);
                cont.startActivity(Intent.createChooser(i, "发送错误报告"));
                //退出
                AppManager.getAppManager().AppExit(cont);
            }
        });
        builder.setNegativeButton(R.string.sure, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //退出
                AppManager.getAppManager().AppExit(cont);
            }
        });
        builder.show();
    }

}
