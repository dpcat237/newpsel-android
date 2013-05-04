package com.dpcat237.nps.helper;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class GenericHelper {
	public static String generateKey(Context context) {
	    final int outputKeyLength = 256;
	    String result = ""; 
	    
	    @SuppressWarnings("static-access")
		SharedPreferences userPref = context.getSharedPreferences("UserPreference", context.MODE_PRIVATE);
	    Boolean registered = userPref.getBoolean("registered", false);
	    
	    if (registered) {
	    	result = userPref.getString("appKey", "");
	    } else {
	    	try {
	    		SecureRandom secureRandom = new SecureRandom();
			    // Do *not* seed secureRandom! Automatically seeded from system entropy.
			    KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			    keyGenerator.init(outputKeyLength, secureRandom);
			    SecretKey key = keyGenerator.generateKey();

			    SharedPreferences.Editor editor = userPref.edit();
			    editor.putString("appKey", key.getEncoded().toString());
				editor.putBoolean("registered", true);
				editor.commit();
				
				result = userPref.getString("appKey", "");
	    	} catch(Exception e) {
	    		Log.e("GenericHelper - generateKey","Error", e);
	    	}
	    }

		return result;
	}
	
	public static boolean hasConnection(Context context) {
	    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

	    NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
	    if (wifiNetwork != null && wifiNetwork.isConnected()) {
	      return true;
	    }

	    NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
	    if (mobileNetwork != null && mobileNetwork.isConnected()) {
	      return true;
	    }

	    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
	    if (activeNetwork != null && activeNetwork.isConnected()) {
	      return true;
	    }

	    return false;
	  }
	 
	public static Boolean checkLogged(Context context) {
		 @SuppressWarnings("static-access")
		 SharedPreferences userPref = context.getSharedPreferences("UserPreference", context.MODE_PRIVATE);
		 Boolean logged = userPref.getBoolean("logged", false);
		 
		 return logged;
	}
	
	public static void doLogin(Context context) {
		@SuppressWarnings("static-access")
		SharedPreferences userPref = context.getSharedPreferences("UserPreference", context.MODE_PRIVATE);
		SharedPreferences.Editor editor = userPref.edit();
		editor.putBoolean("logged", true);
		editor.commit();
	}
	 
	public static String sha1Password(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String pwd = sha1("sc_"+text);
		String appPwd = sha1("checkPwd_"+pwd);
		return appPwd;
	}
	
	public static String sha1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        byte[] sha1hash = md.digest();
        return convertToHex(sha1hash);
    }
	
	private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }
}
