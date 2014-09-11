package com.shineeye.www.tools;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * @Class Name : NetConnection
 * @Description 网络操作 的 工具类
 * @Author www.dunkai.cn
 * @Date 2012-6-28 下午6:55:07
 */
public class NetConnection {

	
	/**
	 * @Description 检测当前网络是否可用
	 * @return boolean类型 如果网络可用 返回 true，否则返回 false
	 */
	public static boolean isNetworkAvailable() {
		ConnectivityManager connectivity = (ConnectivityManager) KittApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
//			NetworkInfo[] info = connectivity.getAllNetworkInfo();
//			if (info != null) {
//				for (int i = 0; i < info.length; i++) {
//					if (info[i].isConnected()) {
//						return true;
//					}
//				}
//			}
			NetworkInfo network = connectivity.getActiveNetworkInfo();
			if (network != null && network.isConnected()) {
				return true;
			}
		}
		return false;
	}

}