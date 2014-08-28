
package com.shineeye.www.tools;

import android.app.Application;
import android.content.Context;

/**
 * @Class Name : KittApplication
 * @Description:
 * @Author yuanliangfeng@ceehoo.cn
 * @Date 2014年6月20日 下午1:54:59
 */
public class KittApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getBaseContext();
    }

    public static Context getContext() {
        return context;
    }
}
