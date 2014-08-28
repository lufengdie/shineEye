
package com.shineeye.www.page;

import com.shineeye.www.R;
import com.shineeye.www.tools.PreferencesBase;
import com.shineeye.www.tools.ToastUtil;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * @Class Name : PasswordSettingActivity
 * @Description: 密码设置页面
 * @Author yuanliangfeng@ceehoo.cn
 * @Date 2014年6月18日 下午11:04:59
 */
public class PasswordSettingActivity extends Activity implements OnClickListener {

    private EditText passEt;
    private String passStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_setting);
        passEt = (EditText) findViewById(R.id.passEt);
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

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.leftButton:
                hideInput();
                finish();
                break;

            case R.id.rightButton:
                if (checkInput()) {
                    PreferencesBase.setValueForTargetKey(PreferencesBase.PassWord, passStr);
                    ToastUtil.showShortToast("重设成功");
                    finish();
                }
                break;

            default:
                break;
        }
    }

    private boolean checkInput() {
        passStr = passEt.getEditableText().toString();
        if (passStr.length() < 16) {
            ToastUtil.showShortToast("密码太短");
            passEt.requestFocus();
            return false;
        }
        hideInput();
        return true;
    }

}
