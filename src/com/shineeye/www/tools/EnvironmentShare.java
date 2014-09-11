
package com.shineeye.www.tools;

import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings.Secure;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @Class Name : EnvironmentShare
 * @Description: 该类为 硬件检测的 公共类。
 * @Author yuanlf
 * @Date 2014年5月28日 下午5:33:11
 */
public class EnvironmentShare {

    /** 当前应用存放资源文件夹 **/
    private static final String dunKaiResourceDir = "/ShineEye";

    /** 日志 文件夹 **/
    private static final String logDir = "/log";
    /** Log日志名称 前缀 **/
    private static final String LOG_FILE_NAME_PREFIX = "log-";
    /** Log日志名称 后缀 **/
    private static final String LOG_FILE_NAME_SUFFIX = ".txt";
    /** 当前应用名称,应用启动时会对其赋值 **/
    public static String appName = "smart-";

    /** 下载文件存储 文件夹 **/
    private static final String DownDir = "/supplementaryBooks";

    /**
     * @Description 检测当前设备SD是否可用
     * @return 返回"true"表示可用，否则不可用
     */
    public static boolean haveSdCard() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            // DialogDisplayer.showToast(context, "SD不存在！", true);
            return false;
        }
    }

    /**
     * @Description 获得SD卡根目录路径
     * @return SD卡根目录路径 如果SD不存在，则返回null
     */
    public static String getSdCardAbsolutePath() {
        if (haveSdCard()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            return null;
        }
    }

    /**
     * @Description 获得日志文件
     * @return 日志文件，如果为null表示创建失败
     */
    private static File getLogFile() {
        File logFile = null;
        File logDirFile = new File(getSDResourceDir().getAbsolutePath() + logDir);
        try {
            if (!logDirFile.exists() && !logDirFile.mkdirs()) {
                // Logger.i("日志文件夹创建失败！");
                return null;
            }
            Calendar calendar = Calendar.getInstance();
            String dataTime = calendar.get(Calendar.YEAR) + "-"
                    + (calendar.get(Calendar.MONTH) + 1) + "-"
                    + calendar.get(Calendar.DAY_OF_MONTH);
            logFile = new File(logDirFile.getAbsolutePath() + "/" + appName + "-"
                    + LOG_FILE_NAME_PREFIX + dataTime + LOG_FILE_NAME_SUFFIX);
            if (!logFile.exists()) {
                if (logFile.createNewFile()) {
//                    deleteEarliestLog(logDirFile, logFile);
                } else {
                    // Logger.i("创建日志文件失败！");
                    return null;
                }
            }
        } catch (IOException e) {
            Logger.e("EnvironmentShare...exception" + ExceptionUtil.getExceptionStack(e));
        }
        return logFile;
    }

    /**
     * @Description 删除最早的日志
     */
    private static void deleteEarliestLog(File logDirFile, File logFile) {
        // 将当前目录下不属于当天的日志删除掉
        for (File currentFile : logDirFile.listFiles()) {
            String fileName = currentFile.getName();
            if (!fileName.contains(logFile.getName())) {
                if (currentFile.delete()) {
                    Logger.d("成功删除日志: " + fileName);
                } else {
                    Logger.d("删除日志: " + fileName + " 失败");
                }
            }
        }
    }

    /**
     * 将日志写在SD卡指定文件中
     * 
     * @param message 要写的信息
     */
    public static void print(String message) {
        if (message == null) {
            return;
        }
        File logFile = getLogFile();
        RandomAccessFile dataOut = null;
        if (logFile != null) {
            try {
                // 用于输入换行符的字节码
                byte[] c = new byte[] {
                        0x0d, 0x0a
                };
                // 拼接输出信息，时间 + 信息 + 换行
                String currentDateTimeStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                        Locale.CHINA).format(new Date());
                message = currentDateTimeStr + ":　　" + message + new String(c, "utf-8");
                dataOut = new RandomAccessFile(logFile, "rw");
                // 调至流的尾部
                dataOut.seek(dataOut.length());
                dataOut.write(message.getBytes("utf-8"));
            } catch (FileNotFoundException e) {
                Logger.e("EnvironmentShare...exception" + ExceptionUtil.getExceptionStack(e));
            } catch (IOException e) {
                Logger.e("EnvironmentShare...exception" + ExceptionUtil.getExceptionStack(e));
            } finally {
                if (dataOut != null) {
                    try {
                        dataOut.close();
                    } catch (IOException e) {
                        Logger.e("EnvironmentShare...exception"
                                + ExceptionUtil.getExceptionStack(e));
                    }
                }
            }
        }
    }

    /**
     * @Description 获得当前应用存放资源文件的文件夹
     * @return 存储 资源文件的文件夹
     */
    public static File getSDResourceDir() {
        File tmsResourceDir = new File(EnvironmentShare.getSdCardAbsolutePath() + dunKaiResourceDir);
        if (!tmsResourceDir.exists()) {
            tmsResourceDir.mkdirs();
        }
        return tmsResourceDir;
    }

    /**
     * @Description 判断 SD 卡剩余容量够不够
     * @param sizeMb 大小
     * @return boolean 是否足够
     */
    public static boolean isAvaiableSpace(int sizeMb) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String sdcard = Environment.getExternalStorageDirectory().getPath();
            StatFs statFs = new StatFs(sdcard);
            long blockSize = statFs.getBlockSize();
            long blocks = statFs.getAvailableBlocks();
            long availableSpare = (blocks * blockSize) / (1024 * 1024);
            // int availableSpare = (int)
            // (statFs.getBlockSize()*((long)statFs.getAvailableBlocks()-4))/(1024*1024);//以比特计算
            // 换算成MB
            if (sizeMb > availableSpare) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * Description：获取下载文件夹
     * 
     * @return
     */
    protected static File getDownLoadDir() {
        File downLoadDirFile = new File(getSDResourceDir().getAbsolutePath() + DownDir);
        if (!downLoadDirFile.exists() && !downLoadDirFile.mkdirs()) {
            Logger.i("下载文件夹创建失败！");
            return null;
        }
        return downLoadDirFile;
    }

    /**
     * 生成下载文件
     * 
     * @param fileName 文件名
     * @return
     */
    public static File getDownLoadFile(String fileName) {
        if (StringUtil.isEmpty(fileName)) {
            return null;
        }
        File downloadFile = null;
        try {
            // long dataTime = Calendar.getInstance().getTimeInMillis();
            // if (StringUtil.isEmpty(fileName)) {
            // fileName = appName + "-" + dataTime;
            // }
            downloadFile = new File(getDownLoadDir().getAbsolutePath() + "/" + fileName);
            if (!downloadFile.exists() && !downloadFile.createNewFile()) {
                Logger.i("创建下载文件失败！");
                return null;
            }
        } catch (IOException e) {
            Logger.e("EnvironmentShare...exception" + ExceptionUtil.getExceptionStack(e));
        }
        return downloadFile;
    }

    /**
     * @Description: 返回设备ID
     * @return
     */
    public static String getDeviceId() {
        // TelephonyManager teleManager = (TelephonyManager)
        // SmartApplication.getAppContext().getSystemService(Context.TELEPHONY_SERVICE);
        // String deviceId = teleManager.getDeviceId();
        // return deviceId;
        return Secure.getString(KittApplication.getContext().getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);
    }

}
