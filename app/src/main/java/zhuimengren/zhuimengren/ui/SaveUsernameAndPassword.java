package zhuimengren.zhuimengren.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created by __追梦人 on 2015/3/15.
 */
public class SaveUsernameAndPassword {
    private static SaveUsernameAndPassword preference = null;
    private SharedPreferences sharedPreference;
    private String packageName = "";

    private static final String LOGIN_NAME = "loginName"; //登录名
    private static final String PASSWORD = "password";  //密码
    private static final String IS_SAVE_PWD = "isSavePwd"; //是否保留密码

    public static synchronized SaveUsernameAndPassword getInstance(Context context){
        if(preference == null)
            preference = new SaveUsernameAndPassword(context);
        return preference;
    }


    public SaveUsernameAndPassword(Context context){
        packageName = context.getPackageName() + "_preferences";
        sharedPreference = context.getSharedPreferences(
                packageName, context.MODE_PRIVATE);
    }


    public String getLoginName(){
        String loginName = sharedPreference.getString(LOGIN_NAME, "");
        return loginName;
    }


    public void SetLoginName(String loginName){
        Editor editor = sharedPreference.edit();
        editor.putString(LOGIN_NAME, loginName);
        editor.commit();
    }


    public String getPassword(){
        String password = sharedPreference.getString(PASSWORD, "");
        return password;
    }


    public void SetPassword(String password){
        Editor editor = sharedPreference.edit();
        editor.putString(PASSWORD, password);
        editor.commit();
    }


    public boolean IsSavePwd(){
        Boolean isSavePwd = sharedPreference.getBoolean(IS_SAVE_PWD, false);
        return isSavePwd;
    }


    public void SetIsSavePwd(Boolean isSave){
        Editor edit = sharedPreference.edit();
        edit.putBoolean(IS_SAVE_PWD, isSave);
        edit.commit();
    }

}
