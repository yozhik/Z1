package com.rsd.vkcleaner;

import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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

public class ProgressActivity extends Activity implements OnClickListener
{
	private static final String tag = "ProgressActivity";
	
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
        Log.d(tag, "ProgressActivity.hashcode: " + this.hashCode());
        
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
        
        executeNewTask();
    }
	
	@Override
    public void onClick(View v)
    {
    	int choice = v.getId();
    	switch(choice)
    	{
    		case R.id.cancel_btn:
    			Log.d(tag, "Cancel pressed");
    			break;
    			
    		case R.id.advert_holder:
    			Log.d(tag, "Image clicked");
    			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
    			startActivity(browserIntent);
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

	private void executeNewTask()
	{
		Log.d(tag, "executeNewTask");
		Log.d(tag, "executeNewTask.hashcode: " + this.hashCode());
		Log.d(tag, "ProgressActivity.executeNewTask.hashcode: " + ProgressActivity.this.hashCode());
		//declarate work to do
		t = new Thread(new Runnable() {
			
			Message msg = null;
			
			@Override
			public void run() {

				try {
					ProgressStatus obj = new ProgressStatus();
					obj.min = 0;
					obj.max = 100;
					
					TimeUnit.SECONDS.sleep(1);
					msg = h.obtainMessage(ProgressStatus.WORK_STARTED, obj);
					
					
					TimeUnit.SECONDS.sleep(1);
					
					for (int i=0; i<obj.max; i++)
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
		});
		
		//start new work in background
		t.start();
		Log.d(tag, "after t.start();");
	}
	
	
	//------------------end PRIVATE SECTION----------------------------------

}
