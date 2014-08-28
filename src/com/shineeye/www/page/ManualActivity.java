
package com.shineeye.www.page;

import com.shineeye.www.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * @Class Name : ManualActivity
 * @Description: 操作手册页面
 * @Author yuanliangfeng@ceehoo.cn
 * @Date 2014年6月21日 下午4:02:11
 */
public class ManualActivity extends Activity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);
    }

    @Override
    public void onClick(View arg0) {
        finish();
    }

}
