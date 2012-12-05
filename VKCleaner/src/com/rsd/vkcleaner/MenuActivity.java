package com.rsd.vkcleaner;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MenuActivity extends Activity implements OnClickListener, OnItemClickListener, ServiceConnection
{
	private static final String tag = "MenuActivity";
	public static final String SELECTED_ITEM = "SELECTED_ITEM";
	final int DIALOG_BUSY = 1;
	
    private ListView listView1;
    boolean mIsBound = false;

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);
        setupHeader();
        setupMenu();
        
        //start background service
        doBindService();
        Intent g1 = new Intent(this, BackgroundService.class);
		startService(g1);
    }
    
    @Override
    public void onItemClick (AdapterView<?> parent, View view, int position, long id)
    {
    	MenuItem selectedItem = (MenuItem)listView1.getItemAtPosition(position);
    	switch(selectedItem.id)
    	{
    		case MenuItemConstants.REMOVE_WALLPOSTS:
    			Log.e(tag, "onItemClick(): " + position + "  " + selectedItem.title);
    			startBackgroundTask(selectedItem.id);
    			break;
    			
    		case MenuItemConstants.REMOVE_MESSAGES:
    			Log.e(tag, "onItemClick(): " + position + "  " + selectedItem.title);
    			//Intent g2 = new Intent(this, BackgroundService.class);
    			//stopService(g2);
    			break;
    			
    		case MenuItemConstants.REMOVE_FRIENDS:
    			Log.e(tag, "onItemClick(): " + position + "  " + selectedItem.title);
    			break;
    			
    		case MenuItemConstants.REMOVE_PHOTOS:
    			//doUnbindService();
    			Log.e(tag, "onItemClick(): " + position + "  " + selectedItem.title);
    			break;
    			
    		case MenuItemConstants.REMOVE_VIDEOS:
    			Log.e(tag, "onItemClick(): " + position + "  " + selectedItem.title);
    			break;
    			
    		case MenuItemConstants.REMOVE_AUDIOS:
    			Log.e(tag, "onItemClick(): " + position + "  " + selectedItem.title);
    			break;
    			
    		case MenuItemConstants.REMOVE_GROUPS:
    			Log.e(tag, "onItemClick(): " + position + "  " + selectedItem.title);
    			break;
    	}
    	
    }
    
    @Override
    public void onClick(View v)
    {
    	int choice = v.getId();
    	switch(choice)
    	{
    		case R.id.logout_btn:
    			Intent k = new Intent(this, ProgressActivity.class);
    			startActivity(k);
    			break;
    		
    	}
    }
    
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_BUSY) {
          AlertDialog.Builder adb = new AlertDialog.Builder(this);
          adb.setTitle(R.string.dlg_title);
          adb.setMessage(R.string.dlg_body);
          adb.setIcon(android.R.drawable.ic_menu_info_details);
          adb.setNeutralButton(R.string.ok_btn, onOkDialogButtonListener);
          return adb.create();
        }
        return super.onCreateDialog(id);
    }
    
    DialogInterface.OnClickListener onOkDialogButtonListener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
          switch (which) {
          case Dialog.BUTTON_POSITIVE:
            break;
          case Dialog.BUTTON_NEGATIVE:
            break; 
          case Dialog.BUTTON_NEUTRAL:
            break;
          }
        }
      };
      
    @Override
    public void onDestroy(){
    	super.onDestroy();
    	doUnbindService();
    }
    
	public void onServiceConnected (ComponentName name, IBinder service)
    {
    	Log.e(tag, "onServiceConnected(): ");
    	// This is called when the connection with the service has been
        // established, giving us the service object we can use to
        // interact with the service.  Because we have bound to a explicit
        // service that we know is running in our own process, we can
        // cast its IBinder to a concrete class and directly access it.
    	BackgroundServiceHolder.setInstance( ((BackgroundService.LocalBinder)service).getService() );
    }
    
    public void onServiceDisconnected (ComponentName name)
    {
    	Log.e(tag, "onServiceDisconnected(): ");
    	// This is called when the connection with the service has been
        // unexpectedly disconnected -- that is, its process crashed.
        // Because it is running in our same process, we should never
        // see this happen.
    	BackgroundServiceHolder.setInstance( null );
    }
    
    
    //private section
    private void doBindService()
    {
    	Log.e(tag, "doBindService(): ");
    	// Establish a connection with the service.  We use an explicit
        // class name because we want a specific service implementation that
        // we know will be running in our own process (and thus won't be
        // supporting component replacement by other applications).
        bindService(new Intent(MenuActivity.this, 
                BackgroundService.class), this, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }
    
    void doUnbindService() 
    {
    	Log.e(tag, "doUnbindService(): ");
        if (mIsBound) {
            // Detach our existing connection.
            unbindService(this);
            mIsBound = false;
        }
    } 
    
    private void setupHeader()
    {
    	//logout button handler
        Button logout_btn = (Button) findViewById(R.id.logout_btn);
        logout_btn.setOnClickListener(this);
        
        TextView txtHeader_field = (TextView) findViewById(R.id.txtHeader);
        Intent g = getIntent();
        long k = g.getLongExtra("user_id", 0);
        txtHeader_field.setText(k+"");
    }
    
    private void setupMenu()
    {
    	ArrayList<MenuItem> listData = new ArrayList<MenuItem>();
        listData.add(new MenuItem(MenuItemConstants.REMOVE_WALLPOSTS, R.drawable.menu_item_icon, this.getResources().getString(R.string.item0)));
        listData.add(new MenuItem(MenuItemConstants.REMOVE_MESSAGES, R.drawable.menu_item_icon, this.getResources().getString(R.string.item1)));
        listData.add(new MenuItem(MenuItemConstants.REMOVE_FRIENDS, R.drawable.menu_item_icon, this.getResources().getString(R.string.item2)));
        listData.add(new MenuItem(MenuItemConstants.REMOVE_PHOTOS, R.drawable.menu_item_icon, this.getResources().getString(R.string.item3)));
        listData.add(new MenuItem(MenuItemConstants.REMOVE_VIDEOS, R.drawable.menu_item_icon, this.getResources().getString(R.string.item4)));
        listData.add(new MenuItem(MenuItemConstants.REMOVE_AUDIOS, R.drawable.menu_item_icon, this.getResources().getString(R.string.item5)));
        listData.add(new MenuItem(MenuItemConstants.REMOVE_GROUPS, R.drawable.menu_item_icon, this.getResources().getString(R.string.item6)));
        
        MenuAdapter adapter = new MenuAdapter(this, 
                R.layout.menu_item_row, listData);
        
        
        listView1 = (ListView)findViewById(R.id.listView1);
        listView1.setAdapter(adapter);
        listView1.setOnItemClickListener(this);
    }
    
    private void startBackgroundTask(int selectedItem)
    {
    	boolean isRunning = BackgroundServiceHolder.getInstance().isRunning();
    	
    	if (!isRunning)
    	{
    		//if we have no current background operations - can start a new one
    		Intent k2 = new Intent(this, ProgressServiceActivity.class);
    		k2.putExtra(SELECTED_ITEM, selectedItem);
    		startActivity(k2);
    	}
    	else
    	{
    		Log.e(tag, "Please wait service is currently running...");
    		showDialog(DIALOG_BUSY);
    	}
    }
}
