package com.example.demo;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


@SpringBootApplication
public class DemoApplicationGET {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(DemoApplicationGET.class, args);

		String stringUrl = "https://api.bitbucket.org/1.0/repositories";
		String userpass = "username" + ":" + "password";
		String basicAuth = "Basic " + new String(new Base64().encode(userpass.getBytes()));

		URL url = new URL(stringUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();

		con.setRequestProperty("X-Requested-With", "Curl");
		con.setDoOutput(true);
		con.setDoInput(true);
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept", "application/json");
		con.setRequestMethod("POST");
		con.setRequestProperty("Authorization", basicAuth);
		con.connect();

		JSONObject jsonObj = new JSONObject();
		try {
			jsonObj.put("name", "test2");

		} catch (JSONException e) {
			e.printStackTrace();
		}

		OutputStream os = con.getOutputStream();
		OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
		os.write(jsonObj.toString().getBytes("UTF-8"));
		osw.flush();
		os.close();

		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String line = null;
		while ((line = br.readLine()) != null) {
			sb.append(line + "\n");
		}
		br.close();
		System.out.println("" + sb.toString());
	}
}
