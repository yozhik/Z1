package com.rsd.vkcleaner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.perm.kate.api.Api;

public class VKCleanerActivity extends Activity implements OnClickListener {
    
	private static final String tag = "VKCleanerActivity";
	
	private Account account=new Account();
	private Api api;
	private final int REQUEST_LOGIN=1;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        
        //login button handler
        Button login_btn = (Button) findViewById(R.id.login_btn);
        login_btn.setOnClickListener(this);
        
        account.restore(this);
        if(account.access_token!=null)
        {
            api=new Api(account.access_token, "2904017");
            startMenuActivity();
        }
    }
    
    @Override
    public void onClick(View v)
    {
    	int choice = v.getId();
    	switch(choice)
    	{
    		case R.id.login_btn:
    			startLoginActivity();
    			break;
    		
    	}
    }
    
    
    private void startLoginActivity() {
    	Intent intent = new Intent(VKCleanerActivity.this, LoginActivity.class);
        startActivityForResult(intent, REQUEST_LOGIN);
    }
    
    private void startMenuActivity() {
    	Intent intent = new Intent(VKCleanerActivity.this, MenuActivity.class);
    	intent.putExtra("user_id", account.user_id);
    	startActivity(intent);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_LOGIN) {
            if (resultCode == RESULT_OK) { 
                account.access_token=data.getStringExtra("token");
                account.user_id=data.getLongExtra("user_id", 0);
                account.save(VKCleanerActivity.this);
                api=new Api(account.access_token, "2904017");
                startMenuActivity();
            }
        }
    }
}