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
import java.lang.Integer;

import android.os.Bundle;
import android.os.SystemProperties;
import com.softwinner.SecureFile;
import java.lang.Integer;
import java.lang.String;
import java.lang.Exception;
public class DisplayTrimmingPreference extends SeekBarDialogPreference implements
        SeekBar.OnSeekBarChangeListener{

    private SeekBar mSeekBar;
    
    private int OldValue;
    
    private int MAXIMUM_VALUE = 100;
    private int MINIMUM_VALUE = 90;
	private String DISPLAY_AREA_RADIO = "display.area_radio";
    private DisplayManagerAw mDisplayManagerAw;
    private static boolean mIsPositiveResult;
    
    public DisplayTrimmingPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDisplayManagerAw = (DisplayManagerAw) context.getSystemService(Context.DISPLAY_SERVICE_AW);
    }
    
    @Override
	public void createActionButtons() {

	}

	
	@Override
	protected View onCreateDialogView() {
		LayoutInflater inflater = LayoutInflater.from(getContext());
		return inflater.inflate(R.layout.preference_dialog_screen_trimming, null);
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
	
    
    protected void onBindDialogView(View view){
        super.onBindDialogView(view);
        
        
        mSeekBar = getSeekBar(view);
        mSeekBar.setMax(MAXIMUM_VALUE-MINIMUM_VALUE);
        OldValue = getSysInt();
        Log.d("staturation","" + OldValue);
        mSeekBar.setProgress(OldValue - MINIMUM_VALUE);
        mSeekBar.setOnSeekBarChangeListener(this);
    }
    
    public void onProgressChanged(SeekBar seekBar, int progress,
            boolean fromTouch){
        setDisplayPercent(progress + MINIMUM_VALUE);
    }
    @Override
    protected void onDialogClosed(boolean positiveResult){
    	if(mIsPositiveResult){
            putSysInt(mSeekBar.getProgress() + MINIMUM_VALUE);
        }else{
            setDisplayPercent(OldValue);
        }
        super.onDialogClosed(mIsPositiveResult);
    }

    private int getSysInt()
    {
    	SecureFile file = new SecureFile(DISPLAY_AREA_RADIO);
		if(!file.exists())
		{
			putSysInt(95);
		}
		byte[] ret = new byte[255];
        file.read(ret);
		String st = new String(ret).substring(0,2);
		int retInt = 95;
		try{
			retInt = Integer.valueOf(st).intValue();
			if(retInt == 10) retInt = MAXIMUM_VALUE;
		}catch(Exception e){
			Log.d("chen","file is broken,rebuild it again");
			file.delete();
			putSysInt(retInt);
		}
		Log.d("chen","save " + st + " in /private,value is " + retInt);
		return retInt;
    }
    private boolean putSysInt(int value)
    {
        SecureFile file = new SecureFile(DISPLAY_AREA_RADIO);
		if(!file.exists()) file.createFile();
		byte[] bt = String.valueOf(value).getBytes();
		boolean ret = file.write(bt,false);
		String st = new String(bt);
		Log.d("chen","file content is " + st + " value is " + value);

		Log.d("chen","write value to " + file.getPath() + " is " + getSysInt());
		return ret;
    }
    private void setDisplayPercent(int value) {
        mDisplayManagerAw.setDisplayAreaPercent(0,value);
    }
    
    /*implements method in SeekBar.OnSeekBarChangeListener*/
    @Override
    public void onStartTrackingTouch(SeekBar arg0) {
        // NA
        
    }
    /*implements method in SeekBar.OnSeekBarChangeListener*/
    @Override
    public void onStopTrackingTouch(SeekBar arg0) {
        // NA
        
    }

}
