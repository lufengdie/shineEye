
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
public class CopyOfSocketService extends Service {

    private boolean read = true;

    private void connectServer() {
        Socket socket = null;
        // 大厅灯：（默认用户密码为1234）0xFF FF 01 02 03 04 00 01 FF 01 64 64 64 00 00 00 00
        // 00 00 00 00 00 00 00 00 00 00 00 00 35 AA AA
        try {
            byte[] message = new byte[64];
            // 头标识
            message[0] = (byte) 0xAA;
            // 密码标识,超级密码root:0x72 0x6F 0x6F 0x74
            message[1] = (byte) 0x72;
            message[2] = (byte) 0x6F;
            message[3] = (byte) 0x6F;
            message[4] = (byte) 0x74;
            // Phone 串号标识,超级测试号：00:00:00:00:00:00
            message[5] = (byte) 0x00;
            message[6] = (byte) 0x00;
            message[7] = (byte) 0x00;
            message[8] = (byte) 0x00;
            message[9] = (byte) 0x00;
            message[10] = (byte) 0x00;
            // Router 串号标识,超级测试号：00:00:00:00:00:00
            message[11] = (byte) 0x00;
            message[12] = (byte) 0x00;
            message[13] = (byte) 0x00;
            message[14] = (byte) 0x00;
            message[15] = (byte) 0x00;
            message[16] = (byte) 0x00;
            // Mcu 串号标识,超级测试号：00-00-00-00
            message[17] = (byte) 0x00;
            message[18] = (byte) 0x00;
            message[19] = (byte) 0x00;
            message[20] = (byte) 0x00;
            // 认证标志,0xFF
            message[21] = (byte) 0xFF;
            // 数据载荷
            getAutherPayload(message, 22, 61);
            getCount(message);
            message[63] = (byte) 0xBB;
            // IP地址和端口号（对应服务端），我这的IP是本地路由器的IP地址
            socket = new Socket("182.92.0.108", 7001);
            // 发送给服务端的消息
            String msg = new String(message);
            Logger.d("认证请求字节数组:" + getHexString(message));

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
                        Logger.d("认证响应字节数组:" + getHexString(data));
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

    /**
     * 求和获取校验位
     */
    private void getCount(byte[] message) {
        byte count = 0;
        for (int i = 1; i < 62; i++) {
            count += message[i];
        }
        message[62] = count;
        System.out.println("校验位为:" + count);
    }

    /**
     * 填充认证时数据载荷数据，均为0x00
     */
    private void getAutherPayload(byte[] message, int start, int end) {
        for (int i = start; i < end + 1; i++) {
            message[i] = 0x00;
        }
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

    /**
     * @Description:获取字节数组的16进制字符串
     * @param bytes 字节数组
     * @return
     */
    private String getHexString(byte[] bytes) {
        StringBuffer strBuffer = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            strBuffer.append("," + hex.toUpperCase(Locale.CHINA));
        }
        String str = strBuffer.toString();
        str = str.startsWith(",") ? str.substring(1) : str;
        return str;
    }

}
