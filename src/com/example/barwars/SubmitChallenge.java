package com.example.barwars;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.TextView;

public class SubmitChallenge extends FragmentActivity {

	String barcode;
	TextView tvShowScanResult;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_submit_challenge);
		
		barcode = getIntent().getStringExtra("barcodeDesc");
		tvShowScanResult = (TextView) findViewById(R.id.tvShowScanResult);
		tvShowScanResult.setText(barcode);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.submit_challenge, menu);
		return true;
	}

}
