package tech.deef.TwitchNotifier.live;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * 
 * 
 *
 */

public class ChannelStatus {

	boolean streamIsLive = true;
	String game;
	int viewers;
	boolean playlist;
	String title;
	int views;
	int followers;
	String url;
	
	JPanel panel;

	String userName = null;
	String rawData = null;
	String[] data = null;

	public ChannelStatus(String name) {
		userName = name;
		updateData();
	}

	/*
	 * updates the raw data and parsed data from the website. then calls the set
	 * data method to reset all fields.
	 */
	public void updateData() {
		rawData = GetData.getData(userName);
		data = rawData.split(",");

		// base "uninitated" data
		streamIsLive = true;
		game = "uninitiated";
		viewers = 0;
		playlist = false;
		title = "Uninitiated";
		views = 0;
		followers = 0;
		url = "https://www.twitch.tv/" + userName;

		shouldSetData();
	}

	/*
	 * determines if the channel is offline or online checks previous status if
	 * status is offline, and still offline, no change if status online and now
	 * offline, changes to offline if online at all, updates data
	 * 
	 */
	private void shouldSetData() {
		if (data[0].equals("{\"stream\":null")) {
			// if already offline, no change
			if (streamIsLive == false) {
				return;
			}
			// if just weant offline, set offline data
			streamIsLive = false;
			setDataOffline();
		} else {
			// if not offline, set the data as if online.
			streamIsLive = true;
			setDataOnline();
		}
	}

	/* 
	 * adjusts the data to reflect an offline status.
	 * 
	 * */
	public void setDataOffline() {
		streamIsLive = false;
		game = "Offline";
		viewers = 0;

		title = "Offline";
		

		playlist = false;
	}

	/*
	 * adjusts the data to reflect an online status.
	 * this includes setting all attributes necessary
	 * 
	 * */
	public void setDataOnline() {
		streamIsLive = true;
		game = data[1].trim().substring(8, data[1].length() - 1); //sets game from data
		viewers = Integer.parseInt(data[2].trim().substring(10)); //reads and sets the number of viewers

		title = data[14].trim().substring(10, data[14].length() - 1); //sets the title
		views = Integer.parseInt(data[32].trim().substring(8)); //sets the total number of channel views
		followers = Integer.parseInt(data[33].trim().substring(12)); //sets the total number of followers
		url = data[31].substring(7, data[31].length() - 1); //sets the url. This should not change. 

		//sets if the playlist is or is not a playlist.
		if (data[7].trim().equals("\"is_playlist\":false")) {
			playlist = false;
		} else {
			playlist = true;
		}
	}

	//updates the pannel and returns it. 
	public JPanel getPanel(){
		updatePanel();
		return panel;
	}
	
	//updates the panel
	//DEPRICATING DUE TO INFLEXIBILITY
	private void updatePanel(){
		updateData();
		panel = new JPanel();
		JLabel userLabel = new JLabel(userName + ": ");
		JLabel gameLabel = new JLabel("Gmae: " + game + ", ");
		JLabel viewersLabel = new JLabel("Viewers: " + viewers);
		JLabel titleLabel = new JLabel(title);
		JLabel viewsLabel = new JLabel("Views: " + views + ", ");
		JLabel followersLabel = new JLabel("Followers: " + followers);
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		
		//create and set the layouts
		GridLayout mainLayout = new GridLayout((isLive()?3:2),1);
		FlowLayout layout1 = new FlowLayout();
		FlowLayout layout2 = new FlowLayout();
		FlowLayout layout3 = new FlowLayout();
		panel.setLayout(mainLayout);
		panel1.setLayout(layout1);
		panel2.setLayout(layout2);
		panel3.setLayout(layout3);
		
		//user: TITlE 
		//game, viewers 
		//views, followers
		
		panel1.add(userLabel);
		panel1.add(titleLabel);
		panel1.setBackground(new Color(50,50,50));
		
		panel2.add(gameLabel);
		panel2.add(viewersLabel);
		panel2.setBackground(new Color(50,50,50));

		
		panel3.add(viewsLabel);
		panel3.add(followersLabel);
		panel3.setBackground(new Color(50,50,50));

		
		if(isLive()){
			userLabel.setForeground(new Color(0,100,0));
			titleLabel.setForeground(new Color(0,100,0));
			gameLabel.setForeground(new Color(0,100,0));
			viewersLabel.setForeground(new Color(0,100,0));
			viewsLabel.setForeground(new Color(0,100,0));
			followersLabel.setForeground(new Color(0,100,0));
			userLabel.setBackground(new Color(0,0,0));
			
		}else{
			userLabel.setForeground(new Color(200,0,0));
			titleLabel.setForeground(new Color(200,0,0));
			gameLabel.setForeground(new Color(200,0,0));
			viewersLabel.setForeground(new Color(200,0,0));
			viewsLabel.setForeground(new Color(200,0,0));
			followersLabel.setForeground(new Color(200,0,0));
		}
		
		
		panel.add(panel1);
		//add pannel 2
		if(isLive()){
			panel.add(panel2);
		}
		panel.add(panel3);
		panel.setBorder(BorderFactory.createLineBorder(Color.black));
	}
	
	/*
	 * **************RETURN STATEMENTS**************
	 * The statements below return the information necessary for the
	 * creation of a display structure or any other use. 
	 * */
	
	public boolean isLive() {
		return streamIsLive;
	}

	public String getGame() {
		return game;
	}

	public int getViewers() {
		return viewers;
	}

	public boolean isPlaylist() {
		return playlist;
	}

	public String getTitle() {
		return title;
	}

	public int getViews() {
		return views;
	}

	public int getFollowers() {
		return followers;
	}

	public String getUrl() {
		return url;
	}

	public String getUserName() {
		return userName;
	}

	public void printString() {
		for (String dataPart : data) {
			System.out.println(dataPart);
		}
	}

	//prints all data in formatted manner
	public void printData() {
		System.out.println("\nIs live:\t" + streamIsLive + "\nGame:\t\t" + game + "\nViewers:\t" + viewers
				+ "\nTitle:\t\t" + title + "\nViews:\t\t" + views + "\nFollowers:\t" + followers + "\nURL:\t\t" + url
				+ "\nIs Playlist:\t" + playlist);
	}

	//prints all data in raw manner. 
	public String toString() {
		return rawData;
	}
}
