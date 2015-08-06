package com.sugar.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.settings.SettingsPreferenceFragment;

public class WeatherSetting extends SettingsPreferenceFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d("Change", "change weather");
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	public WeatherSetting() {
		super();
	}

	@Override
	public void onAttach(Activity activity) {
		Log.d("Change", "onAttach()");
		Intent intent = new Intent("android.intent.action.weathercity");
		activity.sendBroadcast(intent);
		super.onAttach(activity);
	}
}
