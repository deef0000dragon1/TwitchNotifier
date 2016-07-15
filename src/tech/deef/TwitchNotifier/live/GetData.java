package tech.deef.TwitchNotifier.live;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * This class serves two purposes.
 *		The first is to get and download the data from a given user
 *			In this, the method checks for an empty string
 *			If the string is empty or null, it returns null
 *		The other purpose is to get the data from the empty string API lookup
 *			This data is the data for the top users of the channel page. 
 *			I have yet to parse this data for information or detail.
 */

public class GetData {
	public static String getData(String UserName){
		//return null for blank or null usernames.
		if(UserName == null || UserName == "" ){
			return null;
		}
		
		
		URL inputURL;
	    InputStream inputStream = null;
	    BufferedReader bufferedReader;
	    String line = null;

	    try {
	    	//look at the feasibility of streams vs channels for the source. 
	        inputURL = new URL("https://api.twitch.tv/kraken/streams/" + UserName);
	        inputStream = inputURL.openStream();  // throws an IOException
	        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

	        line = bufferedReader.readLine();
	        
	        //generic pre-created exceptions.
	    } catch (MalformedURLException mue) {
	         mue.printStackTrace();
	    } catch (IOException ioe) {
	         ioe.printStackTrace();
	    } finally {
	        try {
	            if (inputStream != null) inputStream.close();
	        } catch (IOException ioe) {
	            // nothing to see here
	        }
	    }
		return line;
	}
	
	public static String getMainData(){
		
		URL url;
	    InputStream is = null;
	    BufferedReader br;
	    String line = null;

	    try {
	        url = new URL("https://api.twitch.tv/kraken/streams/");
	        is = url.openStream();  // throws an IOException
	        br = new BufferedReader(new InputStreamReader(is));

	        line = br.readLine();
	    } catch (MalformedURLException mue) {
	         mue.printStackTrace();
	    } catch (IOException ioe) {
	         ioe.printStackTrace();
	    } finally {
	        try {
	            if (is != null) is.close();
	        } catch (IOException ioe) {
	            // nothing to see here
	        }
	    }
		return line;
	}
	
	
}
