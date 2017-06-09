import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import ch.qos.logback.classic.joran.action.InsertFromJNDIAction;
import java.awt.Rectangle;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.net.*;
import java.util.Scanner;
import javax.sound.sampled.*;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import event.*;

public class GameClient {

	public static String SERVER_IP = "192.168.5.141";
	public static int SERVER_PORT = 6000;
	public static JFrame frame;
	//public static Socket ClientSock;
	public static Mixer mixer;
	public static Clip musicBeforeGame;
	public static Clip musicForGame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws Exception {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameClient window = new GameClient();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/*
	 * Create the application. Add the background music
	 */
	public GameClient() throws Exception {
		initialize();
		
		// background-music
		// Find the default mixer
		int i;
		Mixer.Info[] info = AudioSystem.getMixerInfo();
		for (i = 0; i < info.length; i++) {
			if(info[i].getName().contains("default"))
				break;
		}
		mixer = AudioSystem.getMixer(info[i]);
		
		//get the Clip Dataline Connected to the mixer
		DataLine.Info dataInfo = new DataLine.Info(Clip.class, null);
		musicBeforeGame = (Clip) mixer.getLine(dataInfo);

		// the input Source: bgMusic
		// Through the Dataline, let bgMusic data go into mixer and play.
		File bgMusic = new File("../resource/music/bgmusic2.wav");
		AudioInputStream audioInput = AudioSystem.getAudioInputStream(bgMusic);
		musicBeforeGame.open(audioInput);
		musicBeforeGame.loop(Clip.LOOP_CONTINUOUSLY);
	}

	/*
	 * Initialize the contents of the frame. 
	 * Start Game: Connect to the server,
	 * and Change to the next scene Exit Game: Exit the game immediately
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);

		// Setting Backgroung image
		JLabel contentPane = new JLabel();
		contentPane.setIcon(new ImageIcon("../resource/image/homepage.jpg"));
		frame.setContentPane(contentPane);

		// Setting the size and location of the frame [ width: 1280px , height:960px ]
		frame.setBounds(100, 100, 1280, 960);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		// StartGame button
		// When start Game Button pressed, Connect to the server
		JButton StartButton = new JButton("Start Game");
		StartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					// Clean the Component in the JFrame's ContentPane
					frame.getContentPane().removeAll();
					frame.getContentPane().doLayout();
					frame.getContentPane().update(frame.getContentPane().getGraphics());
					GameClient.secondScene();	
			}
		});

		// add the button to the contentPane in JFrame
		StartButton.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 20));
		StartButton.setBounds(140, 750, 1000, 50);
		frame.getContentPane().add(StartButton);

		// Exit Button
		// When Pressed, Exit the game immediately
		JButton ExitButton = new JButton("Exit Game");
		ExitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(1);
			}
		});
		ExitButton.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 20));
		ExitButton.setBounds(140, 825, 1000, 50);
		frame.getContentPane().add(ExitButton);
	}

	/*
	 *Second Scene:
	 * Change to the user setting. And build the packet based on user setting 
	 * After setting, send the packet to the Server
	 */
	public static void secondScene() {
		// webcam's panel
		Webcam webcam = Webcam.getDefault();
		WebcamPanel panel = new WebcamPanel(webcam, true);
		panel.setMirrored(true);
		panel.setLocation(50, 50);
		panel.setSize(760, 754);

		// Text Field for client to enter "PlayerID"
		JTextField PlayerID = new JTextField("Enter Player ID here.", 30);
		PlayerID.setBackground(Color.PINK);
		PlayerID.setFont(new Font("Liberation Mono", Font.BOLD | Font.ITALIC, 24));
		PlayerID.setLocation(865, 50); // 50 + 760
		PlayerID.setSize(365, 80);
		String PlayerID_String = PlayerID.getText();

		// Text Field for client to enter "PlayGender"
		JTextField PlayerGender = new JTextField("Enter Player Gender here.", 30);
		PlayerGender.setFont(new Font("Liberation Mono", Font.BOLD | Font.ITALIC, 24));
		PlayerGender.setBackground(Color.PINK);
		PlayerGender.setLocation(865, 295);
		PlayerGender.setSize(365, 80);
		String PlayerGender_String = PlayerGender.getText();

		// Text Field for client to enter "PlayerMotto"
		JTextField PlayerMotto = new JTextField("Enter Player Motto here.", 30);
		PlayerMotto.setFont(new Font("Liberation Mono", Font.BOLD | Font.ITALIC, 24));
		PlayerMotto.setBackground(Color.PINK);
		PlayerMotto.setLocation(865, 540);
		PlayerMotto.setSize(365, 80);
		String PlayerMotto_String = PlayerMotto.getText();

		// Button for client to confirm photo
		JButton ConfirmPicture = new JButton("Confirm");
		ConfirmPicture.setFont(new Font("DejaVu Sans", Font.BOLD | Font.ITALIC, 18));
		ConfirmPicture.setLocation(50, 850);
		ConfirmPicture.setSize(230, 80);

		// Button for client to take webcam's picture
		JButton TakePicture = new JButton("Take Picture");
		TakePicture.setFont(new Font("DejaVu Sans", Font.BOLD | Font.ITALIC, 18));
		TakePicture.setLocation(315, 850);
		TakePicture.setSize(230, 80);

		// Button for client to use default photo
		JButton Default = new JButton("Use Default");
		Default.setFont(new Font("DejaVu Sans", Font.BOLD | Font.ITALIC, 18));
		Default.setLocation(580, 850);
		Default.setSize(230, 80);

		// Label for client to find opponent
		Icon opponentIcon = new ImageIcon("../resource/image/findopponent_button.png");
		JLabel FindOpponent = new JLabel(opponentIcon);
		FindOpponent.setLocation(878, 691);
		FindOpponent.setSize(336, 237);

		// add all components to frame
		frame.getContentPane().add(panel);
		frame.getContentPane().add(PlayerID);
		frame.getContentPane().add(PlayerGender);
		frame.getContentPane().add(PlayerMotto);
		frame.getContentPane().add(ConfirmPicture);
		frame.getContentPane().add(TakePicture);
		frame.getContentPane().add(Default);
		frame.getContentPane().add(FindOpponent);
		frame.getContentPane().add(FindOpponent);
		frame.getContentPane().update(frame.getContentPane().getGraphics());
	}
}
