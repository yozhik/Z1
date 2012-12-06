package com.rsd.vkcleaner;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.perm.kate.api.KException;

public class ProgressServiceActivity extends Activity implements OnClickListener
{
	private static final String tag = "ProgressServiceActivity";
	
	private ImageView advert_holder;
	private TextView progress_title;
	private TextView progress_description;
	private ProgressBar done_progress;
	private Button cancel_btn;
	
	
	Handler h;
	Thread t;
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_activity);
        setupComponents();
        Log.d(tag, "onCreate");
        Log.d(tag, "ProgressServiceActivity.hashcode: " + this.hashCode());
        
        Intent tmp = getIntent();
        int selectedItem = tmp.getIntExtra(MenuActivity.SELECTED_ITEM, -1);
        
        //message handler
        h = new Handler() 
        {
            public void handleMessage(android.os.Message msg) 
            {
            		ProgressStatus status = (ProgressStatus)msg.obj;
            		switch (msg.what) 
            		{
              			case ProgressStatus.WORK_STARTED:
              				Log.d(tag, "WORK_STARTED");
              				done_progress.setMax(status.max);
              				progress_description.setText(status.min + "");
              			break;
              			
              			case ProgressStatus.PROGRESS_UPDATED:
              				Log.d(tag, "PROGRESS_UPDATED");
              				done_progress.setProgress(status.current);
              				progress_description.setText(status.current + "");
              			break;
              			
              			case ProgressStatus.WORK_DONE:
              				Log.d(tag, "WORK_DONE");
              				progress_description.setText("Work done");	
              			break;
            		}
            };
        };
        
        boolean isBusy = BackgroundServiceHolder.getInstance().isRunning();
        //if appropriate selected menu item and service is not currently running -- start new task
        if (selectedItem != -1 && !isBusy)
        {
        	Runnable task = getExecutiveTask(selectedItem);
        	if (task != null)
        		BackgroundServiceHolder.getInstance().startTask(task);
        }
    }
	
	@Override
    public void onClick(View v)
    {
    	int choice = v.getId();
    	switch(choice)
    	{
    		case R.id.cancel_btn:
    			Log.d(tag, "Cancel pressed");
    			BackgroundServiceHolder.getInstance().cancelTask();
    			break;
    			
    		case R.id.advert_holder:
    			/*Log.d(tag, "Image clicked");
    			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
    			startActivity(browserIntent);*/
    			break;
    		
    	}
    }
	
	
	//------------------start PRIVATE SECTION----------------------------------
	
	private void setupComponents()
	{
		advert_holder = (ImageView) findViewById(R.id.advert_holder);
		progress_title = (TextView) findViewById(R.id.progress_title);
		progress_description = (TextView) findViewById(R.id.progress_description);
		done_progress = (ProgressBar) findViewById(R.id.done_progress);
		cancel_btn = (Button) findViewById(R.id.cancel_btn);
		
		//add event listeners
		cancel_btn.setOnClickListener(this);
		advert_holder.setOnClickListener(this);
	}

	private Runnable getExecutiveTask(int selectedItem)
	{
		Runnable task = null;
		
		switch(selectedItem)
    	{
    		case MenuItemConstants.REMOVE_WALLPOSTS:
    			task = getRemoveWallPostTask();
    			break;
    			
    		case MenuItemConstants.REMOVE_MESSAGES:
    			task = getRemoveMessagesTask();
    			break;
    			
    		case MenuItemConstants.REMOVE_FRIENDS:
    			
    			break;
    			
    		case MenuItemConstants.REMOVE_PHOTOS:
    			
    			break;
    			
    		case MenuItemConstants.REMOVE_VIDEOS:
    			
    			break;
    			
    		case MenuItemConstants.REMOVE_AUDIOS:
    			
    			break;
    			
    		case MenuItemConstants.REMOVE_GROUPS:
    			
    			break;
    	}
		return task;
	}

	
	private Runnable getRemoveWallPostTask()
	{
		Log.d(tag, "executeNewTask");
		Log.d(tag, "executeNewTask.hashcode: " + this.hashCode());
		Log.d(tag, "ProgressActivity.executeNewTask.hashcode: " + ProgressServiceActivity.this.hashCode());
		//declarate work to do
		Runnable task = new Runnable() {
			
			Message msg = null;
			
			@Override
			public void run() {

				try {
					ProgressStatus obj = new ProgressStatus();
					obj.min = 0;
					obj.max = 20;
					
					TimeUnit.SECONDS.sleep(1);
					msg = h.obtainMessage(ProgressStatus.WORK_STARTED, obj);
					h.sendMessage(msg);
					
					TimeUnit.SECONDS.sleep(1);
					
					for (int i=0; i<=obj.max; i++)
					{
						TimeUnit.SECONDS.sleep(1);
						obj.current = i;
						msg = h.obtainMessage(ProgressStatus.PROGRESS_UPDATED, obj);
						h.sendMessage(msg);
					}
					
					h.sendEmptyMessage(ProgressStatus.WORK_DONE);
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		return task;
	}
	
	private Runnable getRemoveMessagesTask()
	{
		Log.d(tag, "executeNewTask");
		Log.d(tag, "executeNewTask.hashcode: " + this.hashCode());
		Log.d(tag, "ProgressActivity.executeNewTask.hashcode: " + ProgressServiceActivity.this.hashCode());
		//declarate work to do
		Runnable task = new Runnable() {
			
			Message msg = null;
			
			@Override
			public void run() {

				try {
					ProgressStatus obj = new ProgressStatus();
					obj.min = 0;
					obj.max = 30;
					
					msg = h.obtainMessage(ProgressStatus.WORK_STARTED, obj);
					h.sendMessage(msg);
					
					for (int i=0; i<=obj.max; i++)
					{
						try{
							VKManager.getApiInstance().createWallPost(Account.user_id, "Test wallpost record # " + i, null,
									null, false, false, false, null, null, "", "");
						}
						catch(KException e)
						{
							e.printStackTrace();
							try{
								URL url = new URL(e.captcha_img);
								Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
								advert_holder.setImageBitmap(bmp);
							}
							catch(Exception ex){}
						
							
						}
						catch(JSONException e)
						{
							e.printStackTrace();
						}
						catch(MalformedURLException e)
						{
							e.printStackTrace();
						}
						catch(IOException e)
						{
							e.printStackTrace();
						}
						
						TimeUnit.SECONDS.sleep(1);
						obj.current = i;
						msg = h.obtainMessage(ProgressStatus.PROGRESS_UPDATED, obj);
						h.sendMessage(msg);
					}
					
					h.sendEmptyMessage(ProgressStatus.WORK_DONE);
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		return task;
	}
	//------------------end PRIVATE SECTION----------------------------------

}
