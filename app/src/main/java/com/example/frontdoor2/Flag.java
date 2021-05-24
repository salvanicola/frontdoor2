package com.example.frontdoor2;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Flag {
	private static boolean debug = true;
	private static String sUrl = "http://10.0.2.2:8085";

	Flag() {
	}

	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	public static String getFlag(String username, String password) throws Exception {
		String urlParameters;
		if (debug) {
			urlParameters = "username=testuser&password=passtestuser123";
		} else {
			urlParameters = "username=" + username + "&password=" + password;
		}
		int postDataLength = urlParameters.getBytes(StandardCharsets.UTF_8).length;
		HttpURLConnection conn = (HttpURLConnection) new URL(sUrl + "?" + urlParameters).openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("charset", "utf-8");
		conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
		conn.setUseCaches(false);
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String content = "";
		while (true) {
			String line = rd.readLine();
			if (line == null) {
				return content;
			}
			content = content + line + "\n";
		}
	}
}