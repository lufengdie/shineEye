
package com.shineeye.www.page;

import com.shineeye.www.R;
import com.shineeye.www.tools.ToastUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * @Class Name : SetSleepTimeActivity
 * @Description: 设置睡眠模式时间页面
 * @Author yuanliangfeng@ceehoo.cn
 * @Date 2014年6月21日 上午3:02:08
 */
public class SetSleepTimeActivity extends Activity implements OnClickListener {

    private EditText minutesEt;
    private int sleepTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_sleep_time);
        minutesEt = (EditText) findViewById(R.id.minutesEt);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.leftButton:
                finish();
                break;

            case R.id.rightButton:

                if (checkInput()) {
                    Intent intent = new Intent();
                    intent.putExtra("data", sleepTime);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                break;

            default:
                break;
        }
    }

    private boolean checkInput() {
        String time = minutesEt.getEditableText().toString();
        if (time == null || "".equals(time)) {
            ToastUtil.showShortToast("睡眠时间不能为空");
            minutesEt.requestFocus();
            return false;
        }
        sleepTime = Integer.valueOf(time);
        if (sleepTime == 0) {
            minutesEt.requestFocus();
            ToastUtil.showShortToast("不能设置0分钟");
            return false;
        }
        if (sleepTime > 200) {
            minutesEt.requestFocus();
            ToastUtil.showShortToast("不能大于200分钟");
            return false;
        }
        hideInput();
        return true;
    }

    /**
     * Description：隐藏输入法
     */
    private void hideInput() {
        // 是否存在焦点
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
