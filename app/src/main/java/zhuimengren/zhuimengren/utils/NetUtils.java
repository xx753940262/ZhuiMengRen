package zhuimengren.zhuimengren.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

/**
 * Created by __追梦人 on 2015/3/27.
 */
public class NetUtils {
    private Context context;
    public NetUtils(Context context) {
        this.context = context;
    }
    /**
     * 判断网络是否可用的方法
     * @param context
     * @return
     */
    public static boolean isOpenNetwork(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    /**
     * 判断wifi是否连接
     * 方式一
     * @return
     */
    public static boolean isWifiConnected(Context context) {
        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return manager.isWifiEnabled();
    }

    /**
     * 判断wifi是否连接
     * 方式二
     * @return
     */
    public boolean isOpenWifi() {
        ConnectivityManager connManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return mWifi.isConnected();
    }
}
