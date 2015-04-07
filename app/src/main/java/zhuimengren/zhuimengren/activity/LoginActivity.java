package zhuimengren.zhuimengren.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONObject;

import zhuimengren.zhuimengren.R;
import zhuimengren.zhuimengren.base.BaseActivity;
import zhuimengren.zhuimengren.config.SharedConfig;
import zhuimengren.zhuimengren.dialog.NetworkDialog;
import zhuimengren.zhuimengren.dialog.ToastDialog;
import zhuimengren.zhuimengren.ui.SaveUsernameAndPassword;
import zhuimengren.zhuimengren.utils.NetUtils;

/**
 * Created by __追梦人 on 2015/2/14.
 */
public class LoginActivity extends BaseActivity implements OnClickListener {
    private Context context;
    private EditText username_edit;
    private EditText password_edit;
    private CheckBox chk_keep_pwd;
    private String Result = null;
    private boolean isNetError;
    private int count;
    private static final int HTTP_OK = 200;
    private static String url = "http://218.58.71.13:8057/Login/Index";
// private static String url = "http://192.168.0.114:8057/Login/Index";
    /**
     * 登录loading提示框
     */
    private ProgressDialog proDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        context = this;          // 得到网络管理器
        init();
        if (NetUtils.isOpenNetwork(context) == false) {
            NetworkDialog.AlertNetworkDialog(this);
        }
    }

    public void init() {
        ((Button) findViewById(R.id.clearConfig)).setOnClickListener(this);
        ((Button) findViewById(R.id.signin_button)).setOnClickListener(this);

        username_edit = (EditText) findViewById(R.id.username_edit);
        password_edit = (EditText) findViewById(R.id.password_edit);
        chk_keep_pwd = (CheckBox) findViewById(R.id.saveUsernameAndPassword_checkbox);
        chk_keep_pwd.setChecked(
                SaveUsernameAndPassword.getInstance(context).IsSavePwd());
    }

    @Override
    protected void onResume() {
        super.onResume();
        context = this;
        chk_keep_pwd.setChecked(SaveUsernameAndPassword.getInstance(context).IsSavePwd());
        if (chk_keep_pwd.isChecked()) {
            username_edit.setText(SaveUsernameAndPassword.getInstance(context)
                    .getLoginName());
            password_edit
                    .setText(SaveUsernameAndPassword.getInstance(context).getPassword());
        }
    }

    private void GetLoginInfo(String username, String password) {

        RequestParams params = new RequestParams();
        params.addQueryStringParameter("username", username);
        params.addQueryStringParameter("password", password);
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(1000 * 10);
        http.send(HttpRequest.HttpMethod.POST,
                url,
                params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {

                        boolean loginState = ValidateLogin(responseInfo.result, responseInfo.statusCode);
                        if (loginState) {
                            ToastDialog.showShort(LoginActivity.this, "登陆成功!");
                            Intent intent = new Intent(LoginActivity.this, FragmentTabActivity.class);
                            startActivity(intent);
                            finish();
                            proDialog.dismiss();
                        } else {
                            // 通过调用handler来通知UI主线程更新UI,
                            Message message = new Message();
                            Bundle bundle = new Bundle();
                            bundle.putBoolean("isNetError", isNetError);
                            message.setData(bundle);
                            loginHandler.sendMessage(message);
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {

                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clearConfig:
                new SharedConfig(LoginActivity.this).ClearConfig();
                ToastDialog.showShort(LoginActivity.this, "成功清除!");
                break;
            case R.id.signin_button:
                if (NetUtils.isOpenNetwork(context) == false) {
                    NetworkDialog.AlertNetworkDialog(LoginActivity.this);
                } else {
                    proDialog = new ProgressDialog(LoginActivity.this);
                    proDialog.setTitle("登陆提示");
                    proDialog.setMessage("登录中,请稍后...");
                    proDialog.setCancelable(true);
                    proDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    proDialog.setIndeterminate(false);
                    proDialog.show();
                    // 开一个线程进行登录验证,主要是用于失败,成功可以直接通过startAcitivity(Intent)转向
                    new Thread(new LoginFailureHandler()).start();
                }
                break;
            case R.id.saveUsernameAndPassword_checkbox:
                chk_keep_pwd.setChecked(!chk_keep_pwd.isChecked());
                break;
        }
    }

    /**
     * 是否保存用户名、密码
     */
    private void IfSaveUsernameAndPassword(String Username, String Password) {
        if (chk_keep_pwd.isChecked()) {
            SaveUsernameAndPassword.getInstance(context)
                    .SetLoginName(Username);
            SaveUsernameAndPassword.getInstance(context)
                    .SetPassword(Password);
            SaveUsernameAndPassword.getInstance(context)
                    .SetIsSavePwd(chk_keep_pwd.isChecked());
        } else {
            SaveUsernameAndPassword.getInstance(context)
                    .SetLoginName("");
            SaveUsernameAndPassword.getInstance(context)
                    .SetPassword("");
            SaveUsernameAndPassword.getInstance(context)
                    .SetIsSavePwd(chk_keep_pwd.isChecked());
        }
    }

    private boolean ValidateLogin(String result, int statusCode) {
        boolean loginState = false;
        try {
            JSONObject jsonObject = new JSONObject(result.toString());
            Result = jsonObject.get("Status").toString();
            if (statusCode != HTTP_OK) {
                isNetError = true;
                return false;
            } else {
                if (Result.equals("0")) {
                    loginState = false;
                } else if (Result.equals("1")) {
                    loginState = true;
                }
            }
        } catch (Exception e) {
        }
        return loginState;
    }

    /**
     * 登录后台通知更新UI线程,主要用于登录失败,通知UI线程更新界面
     */
    Handler loginHandler = new Handler() {
        public void handleMessage(Message msg) {
            isNetError = msg.getData().getBoolean("isNetError");
            if (proDialog != null) {
                proDialog.dismiss();
            }
            if (isNetError) {
                ToastDialog.showShort(LoginActivity.this, "登陆失败:\n" +
                        "1.请检查您网络连接.\n" +
                        "2.请联系我们.!");
            } else {
                ToastDialog.showShort(LoginActivity.this, "登陆失败,请输入正确的用户名和密码!");
            }
        }
    };

    class LoginFailureHandler implements Runnable {
        @Override
        public void run() {
            //获取用户名、密码
            String UserName = username_edit.getText().toString().trim();
            String Password = password_edit.getText().toString().trim();
            IfSaveUsernameAndPassword(UserName, Password);
            GetLoginInfo(UserName, Password);
        }
    }
}

