package com.vgpt.androidpaintings.utils;

import android.util.Log;

import com.vgpt.androidpaintings.BuildConfig;



public class LogUtils {

	public static String tag = "vgpt";

	public static void v(Exception e, String info) {
		if (BuildConfig.DEBUG) {

			Log.v(tag, info, e);
		}

	}
	public static void v(String info) {
		if (BuildConfig.DEBUG) {

			Log.v(tag, info);
		}

	}
	public static void e(Exception e, String info) {

		if (BuildConfig.DEBUG) {

			Log.e(tag, info, e);
		}
	}

	public static void e(Exception e) {

		if (BuildConfig.DEBUG) {

			Log.e(tag, "", e);
		}

	}

}
