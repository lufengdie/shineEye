
package com.shineeye.www.tools;

import android.content.Context;
import android.widget.Toast;

/**
 * @Class Name : ToastUtil
 * @Description:
 * @Author yuanliangfeng@ceehoo.cn
 * @Date 2014年6月20日 下午11:51:31
 */
public class ToastUtil {

    public static void showLongToast(String message) {
        showToast(message, Toast.LENGTH_LONG);
    }

    public static void showShortToast(String message) {
        showToast(message, Toast.LENGTH_SHORT);
    }

    /**
     * @Description:
     * @param message
     * @param duration
     */
    private static void showToast(String message, int duration) {
        Context context = KittApplication.getContext();
        Toast.makeText(context, message, duration).show();
    }

}
