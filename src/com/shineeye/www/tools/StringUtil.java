
package com.shineeye.www.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @Class Name : StringUtil
 * @Description: Utility class for string-related processing.
 * @Author yuanlf
 * @Date 2014年5月29日 上午11:53:38
 */
public class StringUtil {

    /**
     * 去除 指定字符串中 的分隔符，并返回字符数组
     * 
     * @param targetStr String类型 目标字符串
     * @param mark String类型 要去除的分隔符
     * @return 去掉分隔符后的字符数组
     */
    public static String[] splitMark(String targetStr, String mark) {
        String[] strArray = new String[] {};
        if (!"".equals(targetStr)) {
            if (targetStr.indexOf(mark) != -1) {
                if ("|".equals(mark)) {
                    strArray = targetStr.split("\\|");
                } else {
                    strArray = targetStr.split(mark);
                }
            } else {
                strArray = new String[] {
                    targetStr
                };
            }
        }
        return strArray;
    }

    /**
     * The string is empty if it is null or contains only space (both
     * single-byte and double-byte space counts).
     * 
     * @param str The string to be tested
     * @return true if the string is empty
     */
    public static boolean isEmpty(String str) {
        if ((str == null) || trim(str).equals("") || str.equalsIgnoreCase("null")) {
            return true;
        }
        return false;
    }

    /**
     * The string is not empty if it is not null or contains some char.
     * 
     * @param str The string to be tested
     * @return true if the string is empty
     */
    public static boolean isNotEmpty(String str) {
        if ((str == null) || trim(str).equals("") || str.equalsIgnoreCase("null")) {
            return false;
        }
        return true;
    }

    /**
     * The object is empty if it is null or contains only space (both
     * single-byte and double-byte space counts).
     * 
     * @param obj The Object to be tested
     * @return true if the Object is empty
     */
    public static boolean isEmpty(Object obj) {
        if ((null == obj) || obj.equals("")) {
            return true;
        }
        return false;
    }

    /**
     * 是否为空 包括 null字符串
     * 
     * @param input
     * @return
     */
    public static boolean isNullEmpty(String input) {
        if ((null == input) || input.equals("") || input.equalsIgnoreCase("null")) {
            return true;
        }
        return false;
    }

    /**
     * 过滤显示的null字符串
     */

    public static String StringFilteNull(String input) {
        if ((null == input) || input.equals("") || input.equalsIgnoreCase("null")) {
            return "";
        }
        return input;
    }

    /**
     * To delete the space on beginning and end of the string. Both single-byte
     * and double-byte space will be deleted.
     * 
     * @param str String
     * @return trimed string
     */
    public static String trim(String str) {
        if ((str == null) || str.trim().equals("")) {
            return "";
        }

        String newStr = str.trim();
        boolean startFull = newStr.startsWith("　"); // 12288
        boolean endFull = newStr.endsWith("　"); // 12288
        boolean startHalf = newStr.startsWith(" "); // 97
        boolean endHalf = newStr.endsWith(" "); // 97

        while (startFull || endFull || startHalf || endHalf) {
            startFull = newStr.startsWith("　"); // 12288
            endFull = newStr.endsWith("　"); // 12288

            if (startFull) {
                if (newStr.length() == 1) {
                    return "";
                }

                newStr = newStr.substring(1);
            }

            if (endFull) {
                if (newStr.length() == 1) {
                    return "";
                }

                newStr = newStr.substring(0, newStr.length() - 1);
            }

            startHalf = newStr.startsWith(" "); // 97
            endHalf = newStr.endsWith(" "); // 97

            if (startHalf) {
                newStr = newStr.substring(1);
            }

            if (endHalf) {
                newStr = newStr.substring(0, newStr.length() - 1);
            }
        }

        return newStr;
    }

    /**
     * 格式化字符串，将指定后缀加在字符串前 format string,put prefix before strInput.
     * 
     * @param strInput input string
     * @param prefix fill char
     * @param length >0
     * @return String
     */
    public static String stringFormat(String strInput, String prefix, int length) {
        String strResult = "";
        if (isNotEmpty(strInput) && prefix != null && length > 0) {
            strResult = strInput.trim();
            while (strResult.length() < length) {
                strResult = prefix + strResult;
            }
        }
        return strResult;
    }

    /**
     * 格式化字符串，将指定后缀加在字符串后 format string,put suffix after strInput.
     * 
     * @param strInput input string
     * @param suffix fill char
     * @param length >0
     * @return String
     */
    public static String stringFormatSuffix(String strInput, String suffix, int length) {
        String strResult = "";
        if (isNotEmpty(strInput) && suffix != null && length > 0) {
            strResult = strInput.trim();
            while (strResult.length() < length) {
                strResult = suffix + strResult;
            }
        }
        return strResult;
    }

    /**
     * LinkedHashMap<String,Object> 转换为Hashtable<String,String>
     * 
     * @param inputList
     * @return
     */
    public static List<Hashtable<String, String>> LinkedHMPTohashtable(
            List<LinkedHashMap<String, Object>> inputList) {
        List<Hashtable<String, String>> listRES = new ArrayList<Hashtable<String, String>>();
        if (inputList != null && inputList.size() > 0) {
            Hashtable<String, String> resHTB = null;
            LinkedHashMap<String, Object> resHmp = null;
            // 遍历list对象取出属性值
            for (int j = 0; j < inputList.size(); j++) {
                resHmp = inputList.get(j);
                resHTB = new Hashtable<String, String>();
                for (Iterator<?> it1 = resHmp.keySet().iterator(); it1.hasNext();) {
                    String key = it1.next() + "";
                    // 返回值以键值对的方式从保存在map
                    if (StringUtil.isNullEmpty(resHmp.get(key) + "")) {
                        resHTB.put(key, "");
                    } else {
                        resHTB.put(key, resHmp.get(key).toString());
                    }
                }
                // 将map放list中保存，返回list
                listRES.add(resHTB);
            }
        }
        return listRES;
    }

    /**
     * LinkedHashMap<String,Object> 转换为Hashtable<String,String>
     * 
     * @param inputList
     * @return
     */
    public static List<Hashtable<String, String>> LinkedHMPTohashtable(
            List<LinkedHashMap<String, Object>> inputList, String tableName) {
        List<Hashtable<String, String>> listRES = new ArrayList<Hashtable<String, String>>();
        if (inputList != null && inputList.size() > 0) {
            Hashtable<String, String> resHTB = null;
            LinkedHashMap<String, Object> resHmp = null;
            // 遍历list对象取出属性值
            for (int j = 0; j < inputList.size(); j++) {
                resHmp = inputList.get(j);
                resHTB = new Hashtable<String, String>();
                for (Iterator<?> it1 = resHmp.keySet().iterator(); it1.hasNext();) {
                    String key = it1.next() + "";
                    // 返回值以键值对的方式从保存在map
                    if (StringUtil.isNullEmpty(resHmp.get(key) + "")) {
                        resHTB.put(key, "");
                    } else {
                        resHTB.put(key, resHmp.get(key).toString());
                    }
                }
                resHTB.put("tableName", tableName);
                // 将map放list中保存，返回list
                listRES.add(resHTB);
            }
        }
        return listRES;
    }

    /**
     * 压缩翻转图片
     * 
     * @param path 图片所在路径
     * @return
     */

    public static Bitmap imageDeal(byte[] imgbyte) {
        // 图片加载操作
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;// 如果设置为true，解码器将返回null（无位图）
        // 传入文件路径名，则会按照路径名的文件的完整路径名被解码。
        // 返回结果解码成一个位图
        Bitmap bitmap = BitmapFactory.decodeByteArray(imgbyte, 0, imgbyte.length, options);
        options.inJustDecodeBounds = false;// 如果设置为false，将返回解码后位图
        // 计算缩放比
        int be = (int) (options.outHeight / (float) 200);// options.outHeight返回位图的结果高度，如果有错误解码或没有位图时则返回-1
        if (be <= 0) be = 1;
        options.inSampleSize = be;// 设置缩放比例值
        bitmap = BitmapFactory.decodeByteArray(imgbyte, 0, imgbyte.length, options);
        int w = bitmap.getWidth();// 返回位图的宽度
        int h = bitmap.getHeight();// 返回位图的高度
        // 旋转图片
        Matrix matrix = new Matrix();// 矩阵坐标转换
        matrix.postScale(0.8f, 0.8f);
        matrix.postRotate(90);
        // 返回一个变换后的位图
        Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        return bitmap2;
    }

    /**
     * 解析 param map 添加到 URL 后面
     * 
     * @param uri
     * @param parammap
     * @return
     */
    public static String addParametersForMap(String uri, Map<String, String> parammap) {
        if (parammap == null || parammap.size() == 0)
            return uri;
        else {
            boolean isfirst = true;
            StringBuilder uribuffer = new StringBuilder(uri);
            Set<Entry<String, String>> set = parammap.entrySet();
            for (Entry<String, String> entry : set) {
                if (isfirst) {
                    uribuffer.append("?");
                    isfirst = false;
                } else {
                    uribuffer.append("&");
                }
                uribuffer.append(entry.getKey());
                uribuffer.append("=");
                uribuffer.append(entry.getValue());
            }
            return uribuffer.toString();
        }
    }

    /**
     * 得到当前日期字符串 YYYY-MM-DD HH:MM:SS
     */
    public static String getNowDateString() {

        Calendar calendar = new GregorianCalendar();
        Date trialTime = new Date();
        calendar.setTime(trialTime);
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        String hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        String minute = String.valueOf(calendar.get(Calendar.MINUTE));
        String second = String.valueOf(calendar.get(Calendar.SECOND));

        return year + "-" + StringUtil.stringFormat(month, "0", 2) + "-"
                + StringUtil.stringFormat(day, "0", 2) + " "
                + StringUtil.stringFormat(hour, "0", 2) + ":"
                + StringUtil.stringFormat(minute, "0", 2) + ":"
                + StringUtil.stringFormat(second, "0", 2);
    }

}
