
package com.shineeye.www.tools;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @Class Name : PreferencesBase
 * @Description:
 * @Author yuanlf
 * @Date 2014年6月5日 下午7:23:31
 */
public class PreferencesBase {

    /** 默认配置文件名称 **/
    private static final String DETAULT_CONFIG_FILE = "KittConfig";
    /** 默认配置文件 **/
    private static SharedPreferences preferences;

    /** 用户密码 **/
    public static final String PassWord = "passWord";

    /**
     * @Description 获得默认共享文件
     * @return 共享文件
     */
    protected static SharedPreferences getDefaultFile() {
        if (preferences == null) {
            preferences = KittApplication.getContext().getSharedPreferences(DETAULT_CONFIG_FILE,
                    Context.MODE_PRIVATE);
        }
        return preferences;
    }

    /**
     * @Description 获得指定共享文件
     * @param name 要操作的共享文件名称
     * @return 共享文件
     */
    protected static SharedPreferences getTargetFile(String name) {
        return KittApplication.getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    /**
     * @Description 获得指定键对应的String值
     * @param targetKey 要查询的键名
     * @return 对应的值
     */
    public static String getStringByTargetKey(String targetKey) {
        return getDefaultFile().getString(targetKey, "");
    }

    /**
     * @Description 获得指定键对应的String值
     * @param targetKey 要查询的键名
     * @param defaultValue 默认值
     * @return 对应的值
     */
    protected static String getStringByTargetKey(String targetKey, String defaultValue) {
        return getDefaultFile().getString(targetKey, defaultValue);
    }

    /**
     * @Description 获得指定键对应的String值
     * @param targetKey 要查询的键名
     * @return 对应的值
     */
    public static float getFloatByTargetKey(String targetKey) {
        return getDefaultFile().getFloat(targetKey, 0);
    }

    /**
     * @Description 获得指定键对应的Long值
     * @param targetKey 要查询的键名
     * @return 对应的值
     */
    public static long getLongByTargetKey(String targetKey) {
        return getDefaultFile().getLong(targetKey, 0);
    }

    /**
     * @Description 获得指定键对应的Int值
     * @param targetKey 要查询的键名
     * @return 对应的值
     */
    public static int getIntByTargetKey(String targetKey) {
        return getDefaultFile().getInt(targetKey, 0);
    }

    protected static int getIntByTargetKey(String targetKey, int defaultValue) {
        return getDefaultFile().getInt(targetKey, defaultValue);
    }

    /**
     * 为指定的键设值
     * 
     * @param targetKey 要操作的键名
     * @param value 对应的值
     * @return true表示设置成功，否则失败
     */
    public static boolean setValueForTargetKey(String targetKey, String value) {
        return getDefaultFile().edit().putString(targetKey, value).commit();
    }

    /**
     * @Description 获得指定键对应的Boolean值
     * @param targetKey 要查询的键名
     * @return 对应的值
     */
    public static boolean getBooleanByTargetKey(String targetKey) {
        return getDefaultFile().getBoolean(targetKey, false);
    }

    /**
     * 为指定的键设值
     * 
     * @param targetKey 要操作的键名
     * @param value 对应的值
     * @return true表示设置成功，否则失败
     */
    public static boolean setValueForTargetKey(String targetKey, boolean value) {
        return getDefaultFile().edit().putBoolean(targetKey, value).commit();
    }

    /**
     * 为指定的键设值
     * 
     * @param targetKey 要操作的键名
     * @param value 对应的值
     * @return true表示设置成功，否则失败
     */
    public static boolean setValueForTargetKey(String targetKey, int value) {
        return getDefaultFile().edit().putInt(targetKey, value).commit();
    }

    /**
     * 为指定的键设值
     * 
     * @param targetKey 要操作的键名
     * @param value 对应的值
     * @return true表示设置成功，否则失败
     */
    public static boolean setValueForTargetKey(String targetKey, float value) {
        return getDefaultFile().edit().putFloat(targetKey, value).commit();
    }

    /**
     * 为指定的键设值
     * 
     * @param targetKey 要操作的键名
     * @return true表示设置成功，否则失败
     */
    public static boolean removeValueForTargetKey(String targetKey) {
        return getDefaultFile().edit().remove(targetKey).commit();
    }

}
