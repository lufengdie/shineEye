
package com.shineeye.www.tools;

import com.shineeye.www.SocketService;
import com.shineeye.www.SocketService.SocketBinder;

import android.app.Application;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * @Class Name : KittApplication
 * @Description:
 * @Author yuanliangfeng@ceehoo.cn
 * @Date 2014年6月20日 下午1:54:59
 */
public class KittApplication extends Application {

    private static Context context;
    private SocketService soketService;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getBaseContext();
        bindService();
    }

    public static Context getContext() {
        return context;
    }
    
    private ServiceConnection serviceConnection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName componentName) {
			Logger.i("socekt服务连接断开");
		}
		
		@Override
		public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
			SocketBinder socketBinder = (SocketBinder)iBinder;
			soketService = socketBinder.getService();
			soketService.sendMessage("SET:888888,000000000000", null, null);
		}
	};
    
    private void bindService(){
    	bindService(new Intent(this, SocketService.class), serviceConnection, Service.BIND_AUTO_CREATE);
    }
    
    
}
