package com.robusoft.tars.bridge;

import android.app.Application;

import com.robusoft.tars.bridge.bean.GameDimens;


public class GameApplication extends Application{

	@Override
	public void onCreate() {
		super.onCreate();
		GameDimens.initDimens(getResources().getDisplayMetrics().density);
	}
	
}
