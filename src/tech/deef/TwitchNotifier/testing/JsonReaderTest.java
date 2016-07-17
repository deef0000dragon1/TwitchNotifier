package tech.deef.TwitchNotifier.testing;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.*;
import org.json.simple.parser.*;

/**
 * This class is designed to test a JSONReader class and determine if it will be
 * usable for the Twitch Reader This is for specific scenario and developer
 * testing It will have little to no error handling.
 */
public class JsonReaderTest {

	private static final String SPACING = "    ";
	private static final boolean PRINT = true;

	/*
	 * Test the JSON Reader
	 */
	public static void TestJsonReader() {
		JSONObject data = new JSONObject();

		JSONParser parser = new JSONParser();
		String s = getFollowerData();

		try {
			JSONObject array = (JSONObject) parser.parse(s);

			// printCheck(array.toJSONString() + "\n");

			JSONParser(0, array);

		} catch (ParseException pe) {

			printCheck("position: " + pe.getPosition() + "\n");
			printCheck(pe + "\n");
		}

	}

	/*
	 * This function takes a Json object and prints it in a recursive format.
	 * 
	 * Find object type, and switch based uponn the type for JSON Objects, Print
	 * the title, followed by "{" followed by a recursive parse
	 * 
	 * 
	 */
	public static void JSONParser(int tabs, Object o) {

		try {
			switch (o.getClass().toString()) {
			case "class org.json.simple.JSONObject":
				// handle JsonObject

				// make it a json
				JSONObject json = (JSONObject) o;
				// get the keys
				ArrayList<String> keys = new ArrayList(json.keySet());
				// print opening brace
				printCheck("\n"+nTabs(tabs) + "{\n");
				// loop through the parts of the json.
				for (String key : keys) {
					// print the key
					printCheck(nTabs(tabs + 1) + key + ":");
					// recurse into the parser with another level out
					JSONParser(tabs + 1, json.get(key));

				}
				// print the closing brace
				printCheck(nTabs(tabs) + "}\n");
				break;

			case "class org.json.simple.JSONArray":
				// print the opening bracket
				printCheck("[");
				//printCheck("\n");
				// make object into array
				JSONArray array = (JSONArray) o;

				// loop through all parts of array
				for (Object i : array) {
					// recurse into array
					JSONParser(tabs, i);

					// output test
					// printCheck(nTabs(tabs+1) + i.getClass().toString() +
					// "\n");
				}
				// print closing bracket
				printCheck(nTabs(tabs) + "]\n");
				break;

			case "class java.lang.String":

				String string = (String) o;
				// print string
				printCheck("\"" + string + "\"\n");

				break;

			case "class java.lang.Long":

				long num = (long) 0;
				// print the number
				printCheck(num + "\n");
				break;

			case "class java.lang.Boolean":
				boolean bool = (boolean) o;
				// print the bool
				printCheck(bool + "\n");

				break;

			default:
				// if the datatype is not avaliable, make obvoius it is not.
				printCheck("{" + o.getClass().toString() + "} Previous string UNKNOWN\n");

			}
		} catch (NullPointerException e) {
			printCheck("null\n");
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

	// prints the spacing of a specific type once for each layer put in the
	// function
	public static String nTabs(int n) {
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < n; i++) {
			s.append(SPACING);
		}
		return s.toString();
	}

	// print checking function. If PRINT is false, the input wil not print.
	public static void printCheck(String s) {
		if (PRINT) {
			System.out.print(s);
		}
	}
}
