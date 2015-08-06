package com.sugar.settings;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.settings.R;

public class QRFragment extends Fragment{



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View mView = inflater.inflate(R.layout.qr_fragment, null);
		return mView;
	}

	public static QRFragment newInstance() {
		QRFragment qrFragment = new QRFragment();
		return qrFragment;
	}

}
