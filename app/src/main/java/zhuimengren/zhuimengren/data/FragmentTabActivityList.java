package zhuimengren.zhuimengren.data;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import zhuimengren.zhuimengren.inter.IGetJsonDataCallBack;

/**
 * Created by __追梦人 on 2015/3/19.
 */
public class FragmentTabActivityList {
    /**
     * 获取N条模拟的新闻数据<br>
     * 打包成ArrayList返回
     */
    private ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
    private HashMap<String, String> map = null;

    private String Url = "http://218.58.71.13:8057/AFSLaws/GetModeljhList?";
    // private String Url = "http://192.168.0.114:8057/AFSLaws/GetModeljhList?";

    public void getDataJson(int page, IGetJsonDataCallBack iGetJsonDataCallBack) {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("page", "" + page + "");
        params.addQueryStringParameter("rows", "1");
        GetJson(Url, params, iGetJsonDataCallBack);
    }

    public void GetJson(String url, RequestParams params, final IGetJsonDataCallBack iGetJsonDataCallBack) {
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(1000 * 10);
        http.send(HttpRequest.HttpMethod.POST,
                url,
                params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        iGetJsonDataCallBack.getJsonDataCallBack(responseInfo.result);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {

                    }
                });
    }

    public ArrayList<HashMap<String, String>> GetJsonToArrayList(String result) {
        JSONObject jsonObject = null;
        JSONArray jsonArray = null;

        try {
            jsonObject = new JSONObject(result.toString());
            jsonArray = new JSONArray(jsonObject.get("Result").toString());
            if (jsonObject.get("Result").toString().length() != 2) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    // 每条记录又由几个Object对象组成
                    JSONObject item = jsonArray.getJSONObject(i);
                    String name = item.getString("LawName");
                    list = new ArrayList<HashMap<String, String>>();
                    map = new HashMap<String, String>();
                    if (i % 2 == 0) {
                        map.put("uri",
                                "http://images.china.cn/attachement/jpg/site1000/20131029/001fd04cfc4813d9af0118.jpg");
                    } else {
                        map.put("uri",
                                "http://photocdn.sohu.com/20131101/Img389373139.jpg");
                    }
                    map.put("title", name);
                    map.put("content", "内容为：" + name);
                    map.put("review", i + "跟帖");
                    list.add(map);

                }
            } else {
                list = null;
                return list;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
