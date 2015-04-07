package zhuimengren.zhuimengren.listener;

import android.app.Activity;
import android.os.AsyncTask;
import android.text.format.DateUtils;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.HashMap;

import zhuimengren.zhuimengren.adapter.NewListAdapter;
import zhuimengren.zhuimengren.data.FragmentTabActivityList;
import zhuimengren.zhuimengren.dialog.ToastDialog;
import zhuimengren.zhuimengren.inter.IGetJsonDataCallBack;
import zhuimengren.zhuimengren.utils.NetUtils;

/**
 * Created by Administrator on 2015/3/24.
 */
public class MyOnRefreshListener2 implements PullToRefreshBase.OnRefreshListener2<ListView> {
    public static final int HTTP_REQUEST_SUCCESS = -1;
    public static final int HTTP_REQUEST_ERROR = 0;
    public FragmentTabActivityList fragmentTabActivityList = new FragmentTabActivityList();

    private ArrayList<HashMap<String, String>> list;

    private NewListAdapter newAdapter;
    private PullToRefreshListView mPtflv;
    private Activity activity;
    private int page = 2;

    public MyOnRefreshListener2() {

    }

    public MyOnRefreshListener2(PullToRefreshListView ptflv) {
        this.mPtflv = ptflv;
    }

    public MyOnRefreshListener2(PullToRefreshListView ptflv, Activity activity, NewListAdapter newAdapter) {
        this.mPtflv = ptflv;
        this.activity = activity;
        this.newAdapter = newAdapter;
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        // 下拉刷新
        String label = DateUtils.formatDateTime(activity.getApplicationContext(),
                System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
                        | DateUtils.FORMAT_SHOW_DATE
                        | DateUtils.FORMAT_ABBREV_ALL);

        refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
        new GetNewsTask(mPtflv, activity, newAdapter).execute();

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        // 上拉加载
        new GetNewsTask(mPtflv, activity, newAdapter).execute();
    }

    /**
     * 请求网络获得新闻信息
     *
     * @author Louis
     */
    public class GetNewsTask extends AsyncTask<String, Void, Integer> {
        private PullToRefreshListView mPtrlv;
        private Activity activity;
        private NewListAdapter newAdapter;

        public GetNewsTask(PullToRefreshListView ptrlv) {
            this.mPtrlv = ptrlv;
        }

        public GetNewsTask(PullToRefreshListView ptrlv, Activity activity, NewListAdapter newAdapter) {
            this.mPtrlv = ptrlv;
            this.activity = activity;
            this.newAdapter = newAdapter;
        }

        @Override
        protected Integer doInBackground(String... params) {
            if (NetUtils.isOpenNetwork(activity)) {
                try {
                    Thread.sleep(1000);
                    return HTTP_REQUEST_SUCCESS;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return HTTP_REQUEST_ERROR;
        }

        private int page1;

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            switch (result) {
                case HTTP_REQUEST_SUCCESS:
                    fragmentTabActivityList.getDataJson(page, new IGetJsonDataCallBack() {
                        @Override
                        public void getJsonDataCallBack(String result) {
                            list = fragmentTabActivityList.GetJsonToArrayList(result);
                            if (list == null) {
                                ToastDialog.showShort(activity, "Sorry，没有更多数据了!");
                            } else {
                                newAdapter.addNews(list);
                                page++;
                                newAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                    break;
                case HTTP_REQUEST_ERROR:
                    ToastDialog.showShort(activity, "请检查网络!");
                    break;
            }
            mPtrlv.onRefreshComplete();
        }

    }
}
