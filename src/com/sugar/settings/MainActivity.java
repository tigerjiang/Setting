
package com.sugar.settings;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.android.settings.R;

public class MainActivity extends Activity implements OnClickListener {
    private Button mNetSetBtn, mDisplaySetBtn, mQrBtn, mUpdateBtn, mDeviceInfoBtn, mMoreSetBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNetSetBtn = (Button) findViewById(R.id.net_set);
        mDisplaySetBtn = (Button) findViewById(R.id.display_set);
        mQrBtn = (Button) findViewById(R.id.qr_set);
        mUpdateBtn = (Button) findViewById(R.id.update_set);
        mDeviceInfoBtn = (Button) findViewById(R.id.common_info);
        mMoreSetBtn = (Button) findViewById(R.id.more_set);

        mDisplaySetBtn.setOnClickListener(this);
        mQrBtn.setOnClickListener(this);
        mDeviceInfoBtn.setOnClickListener(this);
        mUpdateBtn.setOnClickListener(this);

        mMoreSetBtn.setOnClickListener(this);
        mNetSetBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.net_set:
                intent.setClass(this, NetworkConfigActivity.class);
                break;
            case R.id.display_set:
                intent.setClass(this, DisplayConfigActivity.class);
                break;
            case R.id.qr_set:
                intent.setClass(this, QRActivity.class);
                break;
            case R.id.update_set:
                intent.setComponent(new ComponentName("com.android.systemupdate", "com.android.systemupdate.SystemUpdateActivity"));
                break;
            case R.id.common_info:
                intent.setClass(this, AboutActivity.class);
                break;
                
            case R.id.more_set:
                intent.setComponent(new ComponentName("com.android.settings",
                        "com.android.settings.Settings"));
                break;
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
