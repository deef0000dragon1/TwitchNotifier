package tech.deef.TwitchNotifier.startup;

import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import tech.deef.TwitchNotifier.live.ChannelStatus;


/**
 * main class to test and deploy.
 * Curently set up using hard coded names.
 */
public class Startup extends JFrame {


	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		Startup outline = new Startup();
		outline.createGui();
		while (true) {
			outline.createGui();
			try {
				Thread.sleep(1000 * 60);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// setVisible(false);
		}

	}

	public Startup() {
		super();
	}

	private void createGui() {
		//main pannel that holds everything
		JPanel mainPanel = new JPanel();

		//lists of the names and channels corosponding to those names
		ArrayList<String> channelNames = new ArrayList<String>();
		ArrayList<ChannelStatus> channelInfo = new ArrayList<ChannelStatus>();

		
		//hard coded names for testing.
		channelNames.add("protonjon");
		channelNames.add("skrumpie");
		channelNames.add("geoff");
		channelNames.add("spikevegeta");
		channelNames.add("luke_lafr");
		channelNames.add("totalbiscuit");
		channelNames.add("linustech");
		channelNames.add("gamesdonequick");

		//set gridlayput as a verticle list. 
		GridLayout mainLayout = new GridLayout(channelNames.size(), 1);

		//use name from list of names to create a channel and add to list of channels
		for (String nameOfChannel : channelNames) {
			channelInfo.add(new ChannelStatus(nameOfChannel));
		}

		for (ChannelStatus channel : channelInfo) {
			//create the gui layout for the panel. Not effective to what I need.
			//replacing with a more effective method. 
			//basic problem can be seen with the height of offline channels 
			//vs the height of online ones. 
			//also a problem is that online channels are not at the top.
			GridBagConstraints constraints = new GridBagConstraints();
			constraints.weighty = (channel.isLive() ? 200 : 0);
			System.out.println(channel.isLive() + "   " + channel.getUserName());
			mainPanel.add(channel.getPanel(), constraints);
		}

		
		mainPanel.setLayout(mainLayout);
		
		setSize(800, 75*channelNames.size());
		add(mainPanel);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
