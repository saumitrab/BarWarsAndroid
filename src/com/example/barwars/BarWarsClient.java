package com.example.barwars;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.example.barwars.models.Challenge;
import com.example.barwars.models.User;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class BarWarsClient {
	private static final String BARWARS_BASE_URL = "http://54.218.18.2/";
	private static final String BARWARS_VERSION = "v1/";
	
	private static final String BARWARS_API_UPC = "upc/";
	private static final String BARWARS_API_PROFILE = "profile/";
	private static final String BARWARS_API_CHALLENGE = "challenge/";
	private static final String BARWARS_API_SOLVE = "solve/";
	
	
	private static AsyncHttpClient client = new AsyncHttpClient();

	// GET 
	public static void getBarcodeDesc(Challenge challenge, AsyncHttpResponseHandler responseHandler) {
		client.get(getBarWarsApiUrl(BARWARS_API_UPC + challenge.getBarcode()), responseHandler);
	}
	
	public static void getUserProfileInfo(User user, AsyncHttpResponseHandler responseHandler) {
		client.get(getBarWarsApiUrl(BARWARS_API_PROFILE + user.getUUID()), responseHandler);
	}
	
	public static void getChallengesList(User user, AsyncHttpResponseHandler responseHandler) {
		client.get(getBarWarsApiUrl(BARWARS_API_CHALLENGE + user.getUUID()), responseHandler);
	}
	
	public static void getChallengesList(User user, String query, AsyncHttpResponseHandler responseHandler) {
		String url = getBarWarsApiUrl(BARWARS_API_CHALLENGE + user.getUUID()) + "?filter=" + query;
		client.get(url, responseHandler);
	}
	
	
	// POST
	public static void postUserProfile(User user, AsyncHttpResponseHandler responseHandler) {
		JSONObject jsonUser = new JSONObject();
		JSONObject jsonRequestBody = new JSONObject();
		try {
			jsonUser.put("uuid", user.getUUID());
			jsonUser.put("name", user.getDisplayName());
			
			jsonRequestBody.put("user", jsonUser);
		} catch (JSONException e) {
			Log.d("DEBUG", "postUserProfile Exception");
			e.printStackTrace();
		}
		RequestParams params = new RequestParams();
		params.put("user", jsonRequestBody.toString());
		client.post(getBarWarsApiUrl(BARWARS_API_PROFILE), params, responseHandler);
		// TODO : Verify on server
	}
	
	public static void postCreateNewChallenge(User user, Challenge challenge, AsyncHttpResponseHandler responseHandler){
		JSONObject jsonUserBarcode = new JSONObject();
		JSONObject jsonRequestBody = new JSONObject();
		try {
			jsonUserBarcode.put("uuid", user.getUUID());
			jsonUserBarcode.put("barcode", challenge.getBarcode());
			
			jsonRequestBody.put("user_barcode", jsonUserBarcode);
		} catch (JSONException e) {
			Log.d("DEBUG", "postCreateNewChallenge Exception");
			e.printStackTrace();
		}
		RequestParams params = new RequestParams();
		params.put("user_barcode", jsonRequestBody.toString());
		client.post(getBarWarsApiUrl(BARWARS_API_CHALLENGE), params, responseHandler);
		// TODO: Verify on server 
	}
	
	public static void postSolveChallenge(User user, Challenge challenge, AsyncHttpResponseHandler responseHandler){
		JSONObject jsonUserChallenge = new JSONObject();
		JSONObject jsonRequestBody = new JSONObject();
		try {
			jsonUserChallenge.put("uuid", user.getUUID());
			jsonUserChallenge.put("cid", challenge.getChallengeId());
			jsonUserChallenge.put("barcode", challenge.getBarcode());
			
			jsonRequestBody.put("user_challenge_barcode", jsonUserChallenge);
		} catch (JSONException e) {
			Log.d("DEBUG", "postNewChallenge Exception");
			e.printStackTrace();
		}
		RequestParams params = new RequestParams();
		params.put("user_challenge_barcode", jsonRequestBody.toString());
		client.post(getBarWarsApiUrl(BARWARS_API_SOLVE), params, responseHandler);
		// TODO: Verify on server 
	}
	

	private static String getBarWarsApiUrl(String relativeUrl) {
		return BARWARS_BASE_URL + BARWARS_VERSION + relativeUrl + "/";
	}
}

