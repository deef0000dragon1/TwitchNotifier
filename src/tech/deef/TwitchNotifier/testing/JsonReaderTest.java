package tech.deef.TwitchNotifier.testing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.simple.*;
import org.json.simple.parser.*;


/**
 * This class is designed to test a JSONReader class and determine if it will be
 * usable for the Twitch Reader This is for specific scenario and developer
 * testing It will have little to no error handling.
 */
public class JsonReaderTest {

	/*
	 * Test the JSON Reader
	 */
	public static void TestJsonReader() {
		JSONObject data = new JSONObject();

		JSONParser parser = new JSONParser();
		String s = getFollowerData();

		try {
			Object obj = parser.parse(s);
			JSONArray array = (JSONArray)obj;
			
			
			
		} catch (ParseException pe) {

			System.out.println("position: " + pe.getPosition());
			System.out.println(pe);
		}

	}

	public static String getFollowerData() {

		URL url;
		InputStream inpusStream = null;
		BufferedReader bufferedReader;
		String data = null;

		try {
			url = new URL("https://api.twitch.tv/kraken/users/deef0000dragon1/follows/channels");
			inpusStream = url.openStream(); // throws an IOException
			bufferedReader = new BufferedReader(new InputStreamReader(inpusStream));

			data = bufferedReader.readLine();
		} catch (MalformedURLException mue) {
			mue.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (inpusStream != null)
					inpusStream.close();
			} catch (IOException ioe) {
				// nothing to see here
			}
		}
		return data;
	}

}
