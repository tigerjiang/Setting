
package com.android.settings;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.Settings;
import android.util.Log;

public class BootUpReceiver extends BroadcastReceiver {
    private Handler mHandler = new Handler();

    @Override
    public void onReceive(Context context, Intent intent) {
        final Context mContext = context;
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Log.d("boot", "boot start");
            Settings.Global.putInt(mContext.getContentResolver(), Settings.Global.AUTO_TIME, 0);
            Settings.Global.putInt(mContext.getContentResolver(), Settings.Global.AUTO_TIME_ZONE, 0);
            mHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    Settings.Global.putInt(mContext.getContentResolver(), Settings.Global.AUTO_TIME,
                            1);
                    Settings.Global.putInt(mContext.getContentResolver(),
                            Settings.Global.AUTO_TIME_ZONE, 1);
                }
            }, 5000);
        }

    }

}
