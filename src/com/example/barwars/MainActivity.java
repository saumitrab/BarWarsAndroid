package com.example.barwars;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class MainActivity extends FragmentActivity {

	Button btnSubmitChallenge;
	Button btnFakeDebugScan;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnSubmitChallenge = (Button) findViewById(R.id.btnSubmitChallenge);
		btnSubmitChallenge.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
		    	integrator.initiateScan();				
			}
		});
		
		btnFakeDebugScan = (Button) findViewById(R.id.btnDebugFakeScan);
		btnFakeDebugScan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FAKEonActivityResult();
			}
		});
	}

	
	public void FAKEonActivityResult() {
		AsyncHttpClient ahClient = new AsyncHttpClient();
    	String scanResultQuery = "http://54.218.18.2/v1/upc/12345/";
    	Log.d("DEBUG", "SearchQuery: " + scanResultQuery);
    	ahClient.get(scanResultQuery, new JsonHttpResponseHandler() {    	
			@Override
			public void onSuccess(JSONObject response) {
				String barcodeDesc = "";
				try {
					//Get image results from JSON
					barcodeDesc = response.get("desc").toString();
					Toast.makeText(MainActivity.this, barcodeDesc, Toast.LENGTH_SHORT).show();
					Log.d("DEBUG", barcodeDesc);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				Intent submitIntent = new Intent(MainActivity.this, SubmitChallenge.class);
	    	    // TODO: GET PLAIN TEXT 
	    	    submitIntent.putExtra("barcodeDesc", barcodeDesc);
	    	    //submitIntent.putExtra("type", type);
				startActivity(submitIntent);
			}
			
			@Override
			public void onFailure(Throwable arg0, String arg1) {
				Toast.makeText(MainActivity.this, "Failed to scan", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	  IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
    	  if (scanResult != null) {
    	    String barcode;
    	    String type;
    	    
    	    barcode = scanResult.getContents();
    	    type =    scanResult.getFormatName();
    	    
        	AsyncHttpClient ahClient = new AsyncHttpClient();
        	String scanResultQuery = "http://54.218.18.2/v1/upc/" + barcode + "/";
        	Log.d("DEBUG", "SearchQuery: " + scanResultQuery);
        	Toast.makeText(this, "Request: " + scanResultQuery , Toast.LENGTH_SHORT).show();
        	ahClient.get(scanResultQuery, new JsonHttpResponseHandler() {    	
    			@Override
    			public void onSuccess(JSONObject response) {
    				String barcodeDesc = "";
    				try {
    					//Get image results from JSON
    					barcodeDesc = response.get("desc").toString();
    					Toast.makeText(MainActivity.this, barcodeDesc, Toast.LENGTH_SHORT).show();
    					Log.d("DEBUG", barcodeDesc);
    				} catch (Exception e) {
    					e.printStackTrace();
    				}
    				
    				Intent submitIntent = new Intent(MainActivity.this, SubmitChallenge.class);
    	    	    // TODO: GET PLAIN TEXT 
    	    	    submitIntent.putExtra("barcodeDesc", barcodeDesc);
    	    	    //submitIntent.putExtra("type", type);
    				startActivity(submitIntent);
    			}
    			
    			@Override
    			public void onFailure(Throwable arg0, String arg1) {
    				Toast.makeText(MainActivity.this, "Failed to scan", Toast.LENGTH_SHORT).show();
    			}
    		});
    	    
    	    
    	    
    	    //EditText etBarcode = (EditText) findViewById(R.id.etBarcode);
    	    //EditText etType    = (EditText) findViewById(R.id.etType);
    	    
    	    //etBarcode.setText(barcode);
    	    //etType.setText(type);
    	  }
    	  // else continue with any other code you need in the method
    	  //...
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
