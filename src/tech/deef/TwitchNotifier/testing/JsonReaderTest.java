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

	private static final String SPACING = "  ";
	/*
	 * Test the JSON Reader
	 */
	public static void TestJsonReader() {
		JSONObject data = new JSONObject();

		JSONParser parser = new JSONParser();
		String s = getFollowerData();

		try {
			JSONObject array = (JSONObject)parser.parse(s);
			
			//System.out.println(array.toJSONString());
			
			JSONParser(0,array);
			
			
		} catch (ParseException pe) {

			System.out.println("position: " + pe.getPosition());
			System.out.println(pe);
		}

	}
	
	
	/*
	 * This function takes a Json object and prints it in a recursive format. 
	 * 
	 * Find object type, and switch based uponn the type
	 * 		for JSON Objects, Print the title, followed by "{" followed by a recursive parse
	 * 
	 * 
	 * */
	public static void JSONParser(int tabs, Object o){
		
		switch(o.getClass().toString()){
		case "class org.json.simple.JSONObject":
			//handle JsonObject
			
			//make it a json
			JSONObject json = (JSONObject)o; 
			//get the keys
			ArrayList<String> keys = new ArrayList(json.keySet());
			//loop through the parts of the json. 
			System.out.println("{");
			for(String key: keys){
				
				System.out.print(nTabs(tabs+1) + key + ":");
				JSONParser(tabs+1, json.get(key));
				
			}
			System.out.println(nTabs(tabs) + "}");
			break;
			
			
		/*case "class org.json.simple.JSONArray":
			
			break;
			*/
			
		case "class java.lang.String":
			
			String string = (String)o;
			System.out.println("\""+string+"\"");
			
			break;
			
		case "class java.lang.Long":
		
			long num = (long)0;
			System.out.println(num);
			break;
			
		default:
			
			System.out.println("{" + o.getClass().toString() + "} Previous string UNKNOWN");
			
		}
		
		
		
		
		
		
		/*ArrayList<String> list = new ArrayList(o.keySet());
		
		System.out.println(o.getClass());


		for(String str : list){
			System.out.println(o.get(str).getClass());
			
			JSONObject temp = (JSONObject) o.get(str);
			ArrayList list2 = new ArrayList(temp.entrySet());
			for(Object obj2: list2){
				System.out.println(nTabs(1)+obj2.getClass());
			}
			
		}*/
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

	public static String nTabs(int n){
		StringBuffer s = new StringBuffer();
		for(int i = 0; i < n; i++){
			s.append(SPACING);
		}
		return s.toString();
	}
}
