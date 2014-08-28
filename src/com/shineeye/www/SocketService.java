
package com.shineeye.www;

import com.shineeye.www.tools.Logger;

import org.apache.http.util.ByteArrayBuffer;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Locale;

/**
 * Socket连接服务
 * 
 * @author Administrator
 */
public class SocketService extends Service {

    private boolean read = true;

    private void connectServer() {
        Socket socket = null;
        // 大厅灯：（默认用户密码为1234）0xFF FF 01 02 03 04 00 01 FF 01 64 64 64 00 00 00 00
        // 00 00 00 00 00 00 00 00 00 00 00 00 35 AA AA
        try {
            // IP地址和端口号（对应服务端），我这的IP是本地路由器的IP地址
            socket = new Socket("182.92.0.108", 7001);
            // 发送给服务端的消息
            String msg = new String("SET：888888，0000000000000D");
            Logger.d("认证请求字节数组:" + msg);

            // 第二个参数为True则为自动flush
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                    socket.getOutputStream())), true);
            out.println(msg);
            InputStream reader = socket.getInputStream();
            byte[] buffer = new byte[256];
            int lenght;
            while (read) {
                if (reader.available() != 0) {
                    if ((lenght = reader.read(buffer)) != -1) {
                        ByteArrayBuffer byteBuffer = new ByteArrayBuffer(lenght);
                        byteBuffer.append(buffer, 0, lenght);
                        byte[] data = byteBuffer.buffer();
                        Logger.d("认证响应字节数组:" + new String(data));
                    }
                }
                Thread.sleep(3 * 1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                // 关闭Socket
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Client:Socket error");
                }
            }
            System.out.println("Client:Socket closed");
        }
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
        new Thread() {
            public void run() {
                connectServer();
            }
        }.start();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

}
