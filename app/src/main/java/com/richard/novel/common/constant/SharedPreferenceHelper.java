package com.richard.novel.common.constant;



import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;
import com.richard.novel.common.base.App;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SharedPreferenceHelper {
    /**
     * SharedPreferences的名字
     */
    private static final String NAME = "Xiaou";
	private static int mode = Context.MODE_PRIVATE;    

	public static void save(String key, String value) {
		SharedPreferences settings = getContext().getSharedPreferences(NAME, mode);
		Editor editor = settings.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static void save(String key, float value) {
		SharedPreferences settings = getContext().getSharedPreferences(NAME, mode);
		Editor editor = settings.edit();
		editor.putFloat(key, value);
		editor.commit();
	}
	public static void save(String key, double value) {
		SharedPreferences settings = getContext().getSharedPreferences(NAME, mode);
		Editor editor = settings.edit();
		float newvalue = (float) value;
		editor.putFloat(key, newvalue);
		editor.commit();
	}
	public static String getString(String key) {
		return getString(key, "");
	}

	public static String getString(String key, String defaultValue) {
		SharedPreferences settings = getContext().getSharedPreferences(NAME, mode);
		return settings.getString(key, defaultValue);
	}

	public static void save(String key, boolean value) {
		SharedPreferences settings = getContext().getSharedPreferences(NAME, mode);
		Editor editor = settings.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public static void save(String key, int value) {
		SharedPreferences settings = getContext().getSharedPreferences(NAME, mode);
		Editor editor = settings.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public static void save(String key, long value) {
		SharedPreferences settings = getContext().getSharedPreferences(NAME, mode);
		Editor editor = settings.edit();
		editor.putLong(key, value);
		editor.commit();
	}

	public static boolean getBoolean(String key) {
		return getBoolean(key, false);

	}

	public static boolean getBoolean(String key, boolean defualtVal) {
		SharedPreferences settings = getContext().getSharedPreferences(NAME, mode);
		return settings.getBoolean(key, defualtVal);

	}

	public static int getInt(String key) {
		SharedPreferences settings = getContext().getSharedPreferences(NAME, mode);

		return settings.getInt(key, 0);
	}
	public static int getInt(String key, int defaultVal) {
		SharedPreferences settings = getContext().getSharedPreferences(NAME, mode);
		return settings.getInt(key, defaultVal);
	}
	public static long getLong(String key, long defaultValue) {
		SharedPreferences settings = getContext().getSharedPreferences(NAME, mode);

		return settings.getLong(key, defaultValue);
	}
	

	public static Float getFloat(String key, float defaultValue) {
		SharedPreferences settings = getContext().getSharedPreferences(NAME, mode);
		
		return settings.getFloat(key, defaultValue);
	}	

    public static double getDouble(String key, double defaultValue) {
        SharedPreferences settings = getContext().getSharedPreferences(NAME, mode);
        double doubleVal = settings.getFloat(key, (float) defaultValue);
        return doubleVal;
    }

	/**
	 * 保存对象
	 * @param object
	 */
	public static <T> void setObject(String key , Object object) {
		SharedPreferences settings = getContext().getSharedPreferences(NAME, mode);
		Editor editor = settings.edit();
		Gson gson = new Gson();
		//为空不能返回，必须可以设置为空
		if (object == null){
			editor.putString(key, "");
			editor.commit();
			return;
		}
		//转换成json数据，再保存
		String strJson = gson.toJson(object);
		editor.putString(key, strJson);
		editor.commit();
	}

	/**
	 *
	 * @param key
     * @param <T>
     */
	public static <T> void deleteObject(String key) {
		SharedPreferences settings = getContext().getSharedPreferences(NAME, mode);
		Editor editor = settings.edit();
		editor.putString(key, null);
		editor.commit();
	}

	/**
	 * 获取对象信息
	 * @param type
	 * @return
     */
	public static <T> T getObject(String key,Class<T> type) {

		SharedPreferences settings = getContext().getSharedPreferences(NAME, mode);
		String strJson = settings.getString(key,"");

		if (null == strJson) {
			return null;
		}
		Gson gson = new Gson();
		T object = gson.fromJson(strJson, type);
		return object;

	}


	   /**
    * POI搜索纪录
    * @param datalist
    */
    public static <T> void setObjectList(String key, List<T> datalist) {
        if (null == datalist || datalist.size() <= 0){
			return;
		}

        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(datalist);
		SharedPreferences settings = getContext().getSharedPreferences(NAME, mode);
		Editor editor = settings.edit();
		editor.putString(key, strJson);
		editor.commit();

    }


   /**
    * POI搜索纪录
    * @return
    */
    public static <T> List<T> getObjectList(String key, Class<T[]> type) {
        List<T> datalist=new ArrayList<T>();
		SharedPreferences settings = getContext().getSharedPreferences(NAME, mode);
		String strJson = settings.getString(key,"");
        if ("".equals(strJson)) {
            return datalist;
        }
        Gson gson = new Gson();
        T[] list = gson.fromJson(strJson, type);
        datalist.addAll(Arrays.asList(list));
        return datalist;

    }

	public static Context getContext() {
		return App.getInstance();
	}

}
