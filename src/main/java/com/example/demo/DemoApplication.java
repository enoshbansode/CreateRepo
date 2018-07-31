package com.example.demo;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(DemoApplication.class, args);

		String stringUrl = "http://URL:PORT/rest/api/1.0/projects/TESTPRO/repos";
		String userpass = "USERNAME" + ":" + "PASSWORD";
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
			jsonObj.put("name", "myrepo1");
			jsonObj.put("scmId", "git");
			jsonObj.put("forkable", true);

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
