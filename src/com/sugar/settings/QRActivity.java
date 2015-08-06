package com.sugar.settings;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.android.settings.R;

public class QRActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.qr_layout);
		/*Fragment currFragment = QRFragment.newInstance();
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.fragment_container, currFragment);
		ft.setTransition(FragmentTransaction.TRANSIT_NONE);
		ft.commit();*/
	}
}
