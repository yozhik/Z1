package com.rsd.vkcleaner;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class BackgroundService extends Service {

	//Class for clients to access.
	public class LocalBinder extends Binder {
		BackgroundService getService() {
			return BackgroundService.this;
	    }
	}
	
	// This is the object that receives interactions from clients.  See
    // RemoteService for a more complete example.
    private final IBinder mBinder = new LocalBinder();
    
	private static final String tag = "BackgroundService";
	private static boolean running = false;
	private Thread currentTask = null;
	
	
	
	
    
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		Log.i(tag, ">>>>>>>>>>>>>>onBind()");
		return mBinder;
	}
	
	@Override
    public void onCreate() {
		Log.i(tag, ">>>>>>>>>>>>>>onCreate()");
		running = false;
    }
	
	@Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(tag, "onStartCommand() start id " + startId + ": " + intent);
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return START_STICKY;
    }

	@Override
    public void onDestroy() {
		Log.i(tag, "onDestroy()<<<<<<<<<<<<<<<<");
		running = false;
    }
	
	
	public void startTask(Runnable r)
	{
		running = true;
		Log.i(tag, "-----------start.Runnable-----------");

		final Thread first = new Thread(r);
		currentTask = first;
		first.start();

		Thread second = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try{
					first.join();
				}
				catch(InterruptedException e){
					
				}
				running = false;
			}
		});
		
		second.start();
		
	}
	
	public void cancelTask()
	{
		Log.i(tag, "-----------cancelTask()-----------");
		try{
			if (currentTask != null){
				currentTask.interrupt();
			}
			running = false;
		}
		catch(SecurityException e){
			e.printStackTrace();
		}
	}
	
	public boolean isRunning()
	{
		return running;
	}
}
