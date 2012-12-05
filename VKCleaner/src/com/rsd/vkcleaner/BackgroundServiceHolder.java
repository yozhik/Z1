package com.rsd.vkcleaner;

import android.util.Log;

public class BackgroundServiceHolder {
	
	 private static final String tag = "BackgroundServiceHolder";
	 private static BackgroundService mBoundService;
	 
	 public static BackgroundService getInstance()
	 {
		 return mBoundService;
	 }
	 
	 public static void setInstance(BackgroundService inst)
	 {
		 if (inst == null)
			 Log.d(tag, "setInstance(). BackgroundService in BackgroundServiceHolder is NULL!");
		 mBoundService = inst;
	 }
}
