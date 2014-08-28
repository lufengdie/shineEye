
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
 * @Class Name : SetTimingOpenActivity
 * @Description: 设置定时开灯时间页面
 * @Author yuanliangfeng@ceehoo.cn
 * @Date 2014年6月21日 上午3:46:40
 */
public class SetTimingOpenActivity extends Activity implements OnClickListener {

    private EditText hoursEt;
    private EditText minutesEt;
    private int hours;
    private int minutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_timing_open);
        minutesEt = (EditText) findViewById(R.id.minutesEt);
        hoursEt = (EditText) findViewById(R.id.hoursEt);
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
                    intent.putExtra("hours", hours);
                    intent.putExtra("minutes", minutes);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                break;

            default:
                break;
        }
    }

    private boolean checkInput() {
        String hourStr = hoursEt.getEditableText().toString();
        String minuteStr = minutesEt.getEditableText().toString();
        if (hourStr == null || "".equals(hourStr)) {
            ToastUtil.showShortToast("定时小时不能为空");
            hoursEt.requestFocus();
            return false;
        }
        hours = Integer.valueOf(hourStr);
        if (hours > 23) {
            hoursEt.requestFocus();
            ToastUtil.showShortToast("不能大于23时");
            return false;
        }
        minutes = Integer.valueOf(minuteStr);
        if (minutes > 60) {
            minutesEt.requestFocus();
            ToastUtil.showShortToast("不能大于60分钟");
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
