package zhuimengren.zhuimengren.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import zhuimengren.zhuimengren.R;
import zhuimengren.zhuimengren.ui.UpdateManager;


public class SettingsActivity extends GesturedetectorActivity implements OnClickListener {
    private UpdateManager mUpdateManager;
    private TextView title;
    private TextView right_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        initView();
        initData();
    }

    private void initView() {
        title = (TextView) findViewById(R.id.title);
        ((TextView) findViewById(R.id.update_text)).setOnClickListener(this);
        right_text = (TextView) findViewById(R.id.right_text);
        right_text.setVisibility(View.VISIBLE);
        right_text.setClickable(true);
        right_text.setOnClickListener(this);
    }

    private void initData() {
        title.setText("设置");
        right_text.setText("意见反馈");
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
//		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right_text:
                break;
            case R.id.update_text:
                //这里来检测版本是否需要更新
                mUpdateManager = new UpdateManager(this);
                mUpdateManager.checkUpdateInfo();
                break;
            default:
                break;
        }
    }
}
