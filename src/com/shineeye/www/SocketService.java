
package com.shineeye.www;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import org.apache.http.util.ByteArrayBuffer;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.shineeye.www.tools.Logger;
import com.shineeye.www.tools.NetConnection;
import com.shineeye.www.tools.StringUtil;

/**
 * Socket连接服务
 * 
 * @author Administrator
 */
public class SocketService extends Service {

	/** 发送空闲 **/
	private boolean idel = true;
	private Handler handler;
	private Message msg;
	private String str;
	private String resultStr;
    private int sendCount = 0;
    private static SocketService socketService;
    /** 接收回执超时时间 **/
    private final int READ_TIMEOUT = 10;
    /** 重发上限 **/
    private final int SEND_MAX = 3;
    
    
    public static SocketService getInstance(){
    	if (socketService == null) {
			socketService = new SocketService();
		}
    	return socketService;
    }
    
    private void init(){
    	sendCount = 0;
    	resultStr = "发送失败";
    }

    private void sendMsgToServer() {
    	new Thread(){
    		public void run() {
    			Socket socket = null;
    	        InputStream reader = null;
    	        PrintWriter writer = null;
    	        try {
    	            // IP地址和端口号（对应服务端），我这的IP是本地路由器的IP地址
    	            socket = new Socket("182.92.0.108", 7001);
    	            sendCount++;
    	            Logger.i("发送请求:" + str);

    	            // 第二个参数为True则为自动flush
    	            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
    	                    socket.getOutputStream())), true);
    	            writer.println(str);
    	            reader = socket.getInputStream();
    	            byte[] buffer = new byte[256];
    	            int lenght;
    	            int read = 0;
    	            while (read < READ_TIMEOUT) {
    	                if (reader.available() != 0) {
    	                    if ((lenght = reader.read(buffer)) != -1) {
    	                        ByteArrayBuffer byteBuffer = new ByteArrayBuffer(lenght);
    	                        byteBuffer.append(buffer, 0, lenght);
    	                        byte[] data = byteBuffer.buffer();
    	                        resultStr = new String(data, "UTF-8");
    	                        Logger.d("响应信息:" + resultStr);
    	                    }
    	                }
    	                read++;
    	                Thread.sleep(1 * 1000);
    	            }
    	        } catch (Exception e) {
    	            e.printStackTrace();
    	            Logger.i("socket连接异常：", e);
    	        } finally {
    	        	if (writer != null) {
    	                writer.close();
    	            }
    	        	if (reader != null) {
    					try {
    						reader.close();
    					} catch (IOException e) {
    						e.printStackTrace();
    						Logger.i("断开socket 读取流 时异常：", e);
    					}
    				}
    	            if (socket != null) {
    	                // 关闭Socket
    	                try {
    	                    socket.close();
    	                } catch (IOException e) {
    	                    e.printStackTrace();
    	                    Logger.i("关闭socket连接时异常：", e);
    	                }
    	            }
    	            Logger.i("socket连接已断开");
    	            resendMsg();
    	        }
    		};
    	}.start();
    }
    
    /**
     * 重新发送消息
     */
    private void resendMsg(){
    	if (sendCount < SEND_MAX) {
    		Logger.i("第" + sendCount + "次重连");
			sendMsgToServer();
		}else{
			callback();
		}
    }
    
    /**
     * 回调结果
     */
    private void callback(){
    	if (handler == null || msg == null) {
			return;
		}
		Bundle data = msg.getData();
		data.putString("result", resultStr);
		msg.setData(data);
		handler.sendMessage(msg);
		idel = true;
    }
    
    /**
     * 发送消息
     * @param str
     * @param handler
     * @param msg
     * @return
     */
    public synchronized boolean sendMessage(String str, Handler handler, Message msg){
    	if (idel && StringUtil.isNotEmpty(str)) {
    		init();
    		this.handler = handler;
    		this.msg = msg;
    		this.str = str;
    		if (NetConnection.isNetworkAvailable()) {
    			idel = false;
    			sendMsgToServer();
			}else{
				resultStr = "暂时无网络,请稍后再试";
				callback();
			}
    		return true;
    	}
    	return false;
    }

    /**
     * 填充密码值
     */
    private void getPassword() {
        String passwordStr = "root";
        try {
            byte[] passBytes = passwordStr.getBytes("US-ASCII");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 填充Mac地址
     */
    private void getMac() {

    }

    /**
     * 获取Router标识：sn_phone
     */
    private void getRouterID() {

    }

    /**
     * 获取phone串号标识：sn_router
     */
    private void getPhoneID() {

    }

    /**
     * 获取MCU串号标识：sn_mcu
     */
    private void getMcuID() {

    }


    @Override
    public void onCreate() {
        super.onCreate();
        Logger.i("SocketService onCreate");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return socketBinder;
    }
    
    private SocketBinder socketBinder = new SocketBinder();
    
    public class SocketBinder extends Binder{
    	
    	public SocketService getService(){
    		return SocketService.this;
    	}
    	
    }

}
