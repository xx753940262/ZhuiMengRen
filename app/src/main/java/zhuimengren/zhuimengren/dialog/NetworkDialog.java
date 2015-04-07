package zhuimengren.zhuimengren.dialog;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

/**
 * Created by __追梦人 on 2015/3/17.
 */
public class NetworkDialog {
    public static void AlertNetworkDialog(final Context context) {
        // 如果网络不可用，则弹出对话框，对网络进行设置
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("没有可用的网络");
        builder.setMessage("是否对网络进行设置?");
        builder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = null;
                        try {
                            String sdkVersion = android.os.Build.VERSION.SDK;
                            if (Integer.valueOf(sdkVersion) > 10) {
                                intent = new Intent(
                                        android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                            } else {
                                intent = new Intent();
                                ComponentName comp = new ComponentName(
                                        "com.android.settings",
                                        "com.android.settings.WirelessSettings");
                                intent.setComponent(comp);
                                intent.setAction("android.intent.action.VIEW");
                            }
                            context.startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.setCancelable(false);
        builder.show();
    }
}
