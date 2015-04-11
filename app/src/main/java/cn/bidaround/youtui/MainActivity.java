package cn.bidaround.youtui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import cn.bidaround.point.PointActivity;
import cn.bidaround.point.YtLog;
import cn.bidaround.youtui_template.YouTuiViewType;
import cn.bidaround.youtui_template.YtTemplate;
import cn.bidaround.ytcore.ErrorInfo;
import cn.bidaround.ytcore.YtShareListener;
import cn.bidaround.ytcore.data.ShareData;
import cn.bidaround.ytcore.data.YtPlatform;
import cn.bidaround.ytcore.login.AuthListener;
import cn.bidaround.ytcore.login.AuthLogin;
import cn.bidaround.ytcore.login.AuthUserInfo;
import zhuimengren.zhuimengren.R;

public class MainActivity extends Activity implements OnClickListener {
	private Button popupBt, listBt, whitegrid_bt,main_point_bt;
	private View main_sina_imageview, main_qq_imageview, main_tencentwb_imageview;
	private YtTemplate temp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main_youtui);
		YtTemplate.init(this);
		initView();
	}

	void initView() {
		// 黑色样式的分享框
		popupBt = (Button) findViewById(R.id.popup_bt);
		popupBt.setOnClickListener(this);
		// 白的样式
		listBt = (Button) findViewById(R.id.list_bt);
		listBt.setOnClickListener(this);
		// 白色网格样式
		whitegrid_bt = (Button) findViewById(R.id.whitegrid_bt);
		whitegrid_bt.setOnClickListener(this);
		// 新浪第三方登录
		main_sina_imageview = findViewById(R.id.main_sina_imageview);
		main_sina_imageview.setOnClickListener(this);
		// qq第三方登录
		main_qq_imageview = findViewById(R.id.main_qq_imageview);
		main_qq_imageview.setOnClickListener(this);
		// 腾讯微博第三方登录
		main_tencentwb_imageview = findViewById(R.id.main_tencentwb_imageview);
		main_tencentwb_imageview.setOnClickListener(this);
		//打开积分界面
		main_point_bt = (Button) findViewById(R.id.main_point_bt);
		main_point_bt.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.you_tui, menu);
		MenuItem item = menu.findItem(R.id.action_settings);
		item.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				
				return false;
			}
		});
		return true;
	}

	@Override
	protected void onDestroy() {
		// 调用
		YtTemplate.release(this);
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		/**黑色viewpager模板*/
		case R.id.popup_bt:
			// 分享的数据
			ShareData shareData = new ShareData();
			shareData.isAppShare = true;
			// 打开黑色样式的分享界面
			YtTemplate blackTemp = new YtTemplate(this, YouTuiViewType.BLACK_POPUP, true);
			blackTemp.setShareData(shareData);
			// 分享监听类
			YtShareListener listener1 = new YtShareListener() {
				@Override
				public void onSuccess(ErrorInfo arg0) {
					YtLog.i("sina share onSuccess", arg0.getErrorMessage());
				}

				@Override
				public void onPreShare() {

				}

				@Override
				public void onError(ErrorInfo arg0) {
					YtLog.i("sina share onError", arg0.getErrorMessage());
				}

				@Override
				public void onCancel() {

				}
			};

			/** 分别为每个平台添加监听事件,如果不需要可以不添加*/
			blackTemp.addListener(YtPlatform.PLATFORM_QQ, listener1);
			blackTemp.addListener(YtPlatform.PLATFORM_QZONE, listener1);
			blackTemp.addListener(YtPlatform.PLATFORM_RENN, listener1);
			blackTemp.addListener(YtPlatform.PLATFORM_SINAWEIBO, listener1);
			blackTemp.addListener(YtPlatform.PLATFORM_TENCENTWEIBO, listener1);
			blackTemp.addListener(YtPlatform.PLATFORM_WECHAT, listener1);
			blackTemp.addListener(YtPlatform.PLATFORM_WECHATMOMENTS, listener1);
			/** 分别给每个平台添加分享数据,如果不设置 分享的数据为blackTemp.setShareData(shareData)中设置的数据*/
			blackTemp.addData(YtPlatform.PLATFORM_QQ, shareData);
			blackTemp.addData(YtPlatform.PLATFORM_QZONE, shareData);
			blackTemp.addData(YtPlatform.PLATFORM_RENN, shareData);
			blackTemp.addData(YtPlatform.PLATFORM_SINAWEIBO, shareData);
			blackTemp.addData(YtPlatform.PLATFORM_TENCENTWEIBO, shareData);
			blackTemp.addData(YtPlatform.PLATFORM_WECHAT, shareData);
			blackTemp.addData(YtPlatform.PLATFORM_WECHATMOMENTS, shareData);
			blackTemp.addData(YtPlatform.PLATFORM_MESSAGE, shareData);
			blackTemp.addData(YtPlatform.PLATFORM_EMAIL, shareData);
			blackTemp.addData(YtPlatform.PLATFORM_MORE_SHARE, shareData);
			// 调出分享界面
			blackTemp.show();
			temp = blackTemp;
			break;
			/**白色列表模板*/
		case R.id.list_bt:
			// 打开白色样式的分享
			YtShareListener listener2 = new YtShareListener() {
				@Override
				public void onSuccess(ErrorInfo arg0) {
					YtLog.i("sina share onSuccess", arg0.getErrorMessage());
				}

				@Override
				public void onPreShare() {

				}

				@Override
				public void onError(ErrorInfo arg0) {
					YtLog.i("sina share onError", arg0.getErrorMessage());
				}

				@Override
				public void onCancel() {

				}
			};

			ShareData contentShareData = new ShareData();
			contentShareData.isAppShare = false;
			contentShareData.setDescription("友推积分组件");
			contentShareData.setTitle("友推分享");
			contentShareData.setText("通过友推积分组件，开发者几行代码就可以为应用添加分享送积分功能，并提供详尽的后台统计数据，除了本身具备的分享功能外，开发者也可将积分功能单独集成在已有分享组件的app上，快来试试吧 ");
			contentShareData.setTarget_url("http://youtui.mobi/");
			contentShareData.setImageUrl("http://cdnup.b0.upaiyun.com/media/image/default.png");

			YtTemplate whiteTemplate = new YtTemplate(this, YouTuiViewType.WHITE_LIST, true);
			whiteTemplate.setShareData(contentShareData);
			/** 分别为每个平台添加监听事件,如果不需要可以不添加*/
			whiteTemplate.addListener(YtPlatform.PLATFORM_QQ, listener2);
			whiteTemplate.addListener(YtPlatform.PLATFORM_QZONE, listener2);
			whiteTemplate.addListener(YtPlatform.PLATFORM_RENN, listener2);
			whiteTemplate.addListener(YtPlatform.PLATFORM_SINAWEIBO, listener2);
			whiteTemplate.addListener(YtPlatform.PLATFORM_TENCENTWEIBO, listener2);
			whiteTemplate.addListener(YtPlatform.PLATFORM_WECHAT, listener2);
			whiteTemplate.addListener(YtPlatform.PLATFORM_WECHATMOMENTS, listener2);
			/** 分别给每个平台添加分享数据,如果不设置 分享的数据为blackTemp.setShareData(shareData)中设置的数据*/
			whiteTemplate.addData(YtPlatform.PLATFORM_QQ, contentShareData);
			whiteTemplate.addData(YtPlatform.PLATFORM_QZONE, contentShareData);
			whiteTemplate.addData(YtPlatform.PLATFORM_RENN, contentShareData);
			whiteTemplate.addData(YtPlatform.PLATFORM_SINAWEIBO, contentShareData);
			whiteTemplate.addData(YtPlatform.PLATFORM_TENCENTWEIBO, contentShareData);
			whiteTemplate.addData(YtPlatform.PLATFORM_WECHAT, contentShareData);
			whiteTemplate.addData(YtPlatform.PLATFORM_WECHATMOMENTS, contentShareData);
			whiteTemplate.addData(YtPlatform.PLATFORM_MESSAGE, contentShareData);
			whiteTemplate.addData(YtPlatform.PLATFORM_EMAIL, contentShareData);
			whiteTemplate.addData(YtPlatform.PLATFORM_MORE_SHARE, contentShareData);
			// 调出分享界面
			whiteTemplate.show();
			temp = whiteTemplate;
			break;
			/**白色网格列表*/
		case R.id.whitegrid_bt:
			// ShareData使用内容分享类型分享类型
			ShareData whiteViewShareData = new ShareData();
			whiteViewShareData.isAppShare = false;
			whiteViewShareData.setDescription("友推积分组件");
			whiteViewShareData.setTitle("友推分享");
			whiteViewShareData.setText("通过友推积分组件，开发者几行代码就可以为应用添加分享送积分功能，并提供详尽的后台统计数据，除了本身具备的分享功能外，开发者也可将积分功能单独集成在已有分享组件的app上，快来试试吧 ");
			whiteViewShareData.setTarget_url("http://youtui.mobi/");
			whiteViewShareData.setImageUrl("http://cdnup.b0.upaiyun.com/media/image/default.png");
			// shareData.setImagePath(Environment.getExternalStorageDirectory()+YoutuiConstants.FILE_SAVE_PATH+"demo.png");

			YtTemplate whiteGridTemplate = new YtTemplate(this, YouTuiViewType.WHITE_GRID, true);
			whiteGridTemplate.setShareData(whiteViewShareData);

			YtShareListener whiteViewListener = new YtShareListener() {

				@Override
				public void onSuccess(ErrorInfo error) {
					YtLog.e("----", error.getErrorMessage());
				}

				@Override
				public void onPreShare() {

				}

				@Override
				public void onError(ErrorInfo error) {
					YtLog.e("----", error.getErrorMessage());
				}

				@Override
				public void onCancel() {

				}
			};
			/** 添加分享结果监听,如果开发者不需要处理回调事件则不必设置 */
			whiteGridTemplate.addListener(YtPlatform.PLATFORM_QQ, whiteViewListener);
			whiteGridTemplate.addListener(YtPlatform.PLATFORM_QZONE, whiteViewListener);
			whiteGridTemplate.addListener(YtPlatform.PLATFORM_RENN, whiteViewListener);
			whiteGridTemplate.addListener(YtPlatform.PLATFORM_SINAWEIBO, whiteViewListener);
			whiteGridTemplate.addListener(YtPlatform.PLATFORM_TENCENTWEIBO, whiteViewListener);
			whiteGridTemplate.addListener(YtPlatform.PLATFORM_WECHAT, whiteViewListener);
			whiteGridTemplate.addListener(YtPlatform.PLATFORM_WECHATMOMENTS, whiteViewListener);
			/**
			 * 为每个平台添加分享数据,如果不单独添加,分享的为whiteViewTemplate.setShareData(
			 * whiteViewShareData)设置的分享数据
			 */
			whiteGridTemplate.addData(YtPlatform.PLATFORM_QQ, whiteViewShareData);
			whiteGridTemplate.addData(YtPlatform.PLATFORM_QZONE, whiteViewShareData);
			whiteGridTemplate.addData(YtPlatform.PLATFORM_RENN, whiteViewShareData);
			whiteGridTemplate.addData(YtPlatform.PLATFORM_SINAWEIBO, whiteViewShareData);
			whiteGridTemplate.addData(YtPlatform.PLATFORM_TENCENTWEIBO, whiteViewShareData);
			whiteGridTemplate.addData(YtPlatform.PLATFORM_WECHAT, whiteViewShareData);
			whiteGridTemplate.addData(YtPlatform.PLATFORM_WECHATMOMENTS, whiteViewShareData);
			whiteGridTemplate.addData(YtPlatform.PLATFORM_MESSAGE, whiteViewShareData);
			whiteGridTemplate.addData(YtPlatform.PLATFORM_EMAIL, whiteViewShareData);
			whiteGridTemplate.addData(YtPlatform.PLATFORM_MORE_SHARE, whiteViewShareData);

			whiteGridTemplate.show();
			temp = whiteGridTemplate;
			break;
			/**新浪微博第三方登录*/
		case R.id.main_sina_imageview:
			
			AuthLogin sinaAuth = new AuthLogin();
			AuthListener sinaListener = new AuthListener() {

				@Override
				public void onAuthFail(Activity arg0) {

				}

				@Override
				public void onAuthCancel(Activity arg0) {

				}

				@Override
				public void onAuthSucess(Activity arg0, AuthUserInfo arg1) {

				}
			};
			sinaAuth.sinaAuth(this, sinaListener);
			break;
			/** qq第三方登录 */
		case R.id.main_qq_imageview:	
			AuthLogin qqLogin = new AuthLogin();

			AuthListener qqListener = new AuthListener() {

				@Override
				public void onAuthSucess(Activity act, AuthUserInfo userInfo) {
					Log.i("qqGender:", userInfo.getQqGender());
					Log.i("qqImageUrl:", userInfo.getQqImageUrl());
					Log.i("qqNickName:", userInfo.getQqNickName());
					Log.i("qqOpenid:", userInfo.getQqOpenid());
				}

				@Override
				public void onAuthFail(Activity act) {

				}

				@Override
				public void onAuthCancel(Activity act) {

				}
			};

			qqLogin.qqAuth(this, qqListener);
			break;
			/** 腾讯微博第三方登录 */
		case R.id.main_tencentwb_imageview:
			AuthLogin tencentWbLogin = new AuthLogin();
			AuthListener tencentWbListener = new AuthListener() {
				@Override
				public void onAuthSucess(Activity act, AuthUserInfo userInfo) {

					Log.i("tencentWbName:", userInfo.getTencentWbName());
					Log.i("tencentWbNick:", userInfo.getTencentWbNick());
					Log.i("tencentWbOpenid:", userInfo.getTencentWbOpenid());
					Log.i("tencentWbHead:", userInfo.getTencentWbHead());
				}

				@Override
				public void onAuthFail(Activity act) {

				}

				@Override
				public void onAuthCancel(Activity act) {

				}
			};

			tencentWbLogin.tencentWbAuth(this, tencentWbListener);
			break;
			/**打开积分界面*/
		case R.id.main_point_bt:		
			Intent it = new Intent(this, PointActivity.class);
			startActivity(it);
			break;
		default:
			break;
		}

	}
}
