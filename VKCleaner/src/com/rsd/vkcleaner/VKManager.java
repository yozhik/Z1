package com.rsd.vkcleaner;

import com.perm.kate.api.Api;

public class VKManager {
	
	private static Api api = null;
	
	public VKManager(){}
	
	public static void initApiInstance(String access_token, String api_id)
	{
		if(api == null && access_token != null && api_id != null)
		{
			api = new Api(access_token, api_id);
		}
	}
	
	public static Api getApiInstance()
	{
		return api;
	}
}
