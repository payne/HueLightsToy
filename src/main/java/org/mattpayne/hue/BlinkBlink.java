package org.mattpayne.hue;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class BlinkBlink {

	public static void main(String[] args) {
		try {
			BlinkBlink blinkBlink = new BlinkBlink();
			blinkBlink.run();
			System.out.println("Normal Termination.");
		} catch (Exception bland) {
			bland.printStackTrace();
		}
	}

	private DefaultHttpClient httpClient;

	public BlinkBlink() {

	}

	private void run() throws ClientProtocolException, IOException,
			InterruptedException {
		// http://stackoverflow.com/questions/17484892/http-400-put-request-using-apache-httpclient

		boolean state = true;
		for (int i = 0; i < 1000; i++) {
			httpClient = new DefaultHttpClient();
			String urlStr = "http://10.0.1.2/api/newdeveloper/lights/1/state";
			// 127.0.0.1:8000 to go through tcpmon
			// https://code.google.com/p/tcpmon/
			urlStr = "http://127.0.0.1:8000/api/newdeveloper/lights/1/state";
			HttpPut putRequest = new HttpPut(urlStr);

			String body = "{'on':" + state
					+ ", 'sat':255, 'bri':255,'hue':12345}";
			body = body.replace('\'', '"');

			StringEntity input = new StringEntity(body);
			input.setContentType("text/json");

			putRequest.setEntity(input);
			HttpResponse response = httpClient.execute(putRequest);

			System.out.println(response);
			state = !state;
			httpClient.close();
			Thread.sleep(500L);
		}
	}

}
