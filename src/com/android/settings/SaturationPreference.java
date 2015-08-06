package com.android.settings;

/*
 ************************************************************************************
 *                                    Android Settings

 *                       (c) Copyright 2006-2010, huanglong Allwinner 
 *                                 All Rights Reserved
 *
 * File       : SaturationPreference.java
 * By         : huanglong
 * Version    : v1.0
 * Date       : 2011-9-5 16:20:00
 * Description: Add the Saturation settings to Display.
 * Update     : date                author      version     notes
 *           
 ************************************************************************************
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.os.Bundle;
import android.preference.SeekBarDialogPreference;
import android.preference.SeekBarPreference;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DisplayManagerAw;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;

public class SaturationPreference extends SeekBarDialogPreference implements
		SeekBar.OnSeekBarChangeListener {

	private SeekBar mSeekBar;

	private int OldSaturationValue;

	private int MAXIMUM_VALUE = 80;
	private int MINIMUM_VALUE = 20;
	private DisplayManagerAw mDisplayManagerAw;
	private static boolean mIsPositiveResult;

	public SaturationPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		mDisplayManagerAw = (DisplayManagerAw) context
				.getSystemService(Context.DISPLAY_SERVICE_AW);
	}

	@Override
	public void createActionButtons() {

	}

	
	@Override
	protected View onCreateDialogView() {
		LayoutInflater inflater = LayoutInflater.from(getContext());
		return inflater.inflate(R.layout.preference_dialog_saturation, null);
	}
	
	@Override
	protected void showDialog(Bundle state) {

		Builder mBuilder = new AlertDialog.Builder(getContext());

		View contentView = onCreateDialogView();

		if (contentView != null) {
			onBindDialogView(contentView);
			mBuilder.setView(contentView);
		}
		onPrepareDialogBuilder(mBuilder);
		// Create the dialog
		final Dialog dialog = mBuilder.create();
		if (state != null) {
			dialog.onRestoreInstanceState(state);
		}
		contentView.findViewById(R.id.cancel_btn).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						mIsPositiveResult = false;
						dialog.dismiss();

					}
				});
		contentView.findViewById(R.id.ok_btn).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						mIsPositiveResult = true;
						dialog.dismiss();
					}
				});
		dialog.setOnDismissListener(this);
		dialog.show();

		dialog.getWindow().setGravity(Gravity.CENTER);
		dialog.getWindow().setLayout(600, 300);
	}
	
	protected void onBindDialogView(View view) {
		super.onBindDialogView(view);

		mSeekBar = getSeekBar(view);
		mSeekBar.setMax(MAXIMUM_VALUE - MINIMUM_VALUE);
		try {
			OldSaturationValue = getSysInt();
		} catch (SettingNotFoundException snfe) {
			OldSaturationValue = MAXIMUM_VALUE;
		}
		Log.d("staturation", "" + OldSaturationValue);
		mSeekBar.setProgress(OldSaturationValue - MINIMUM_VALUE);
		mSeekBar.setOnSeekBarChangeListener(this);
	}

	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromTouch) {
		setSaturation(progress + MINIMUM_VALUE);
	}

	@Override
	protected void onDialogClosed(boolean positiveResult) {
		super.onDialogClosed(mIsPositiveResult);
		if (mIsPositiveResult) {
			putSysInt(mSeekBar.getProgress() + MINIMUM_VALUE);
		} else {
			setSaturation(OldSaturationValue);
		}
	}

	private int getSysInt() throws SettingNotFoundException {
		return Settings.System.getInt(getContext().getContentResolver(),
				Settings.System.COLOR_SATURATION, MINIMUM_VALUE);
	}

	private boolean putSysInt(int value) {
		return Settings.System.putInt(getContext().getContentResolver(),
				Settings.System.COLOR_SATURATION, value);
	}

	private void setSaturation(int saturation) {
		mDisplayManagerAw.setDisplaySaturation(0, saturation);
	}

	/* implements method in SeekBar.OnSeekBarChangeListener */
	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		// NA

	}

	/* implements method in SeekBar.OnSeekBarChangeListener */
	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		// NA

	}

}
