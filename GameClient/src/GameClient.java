import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import ch.qos.logback.classic.joran.action.InsertFromJNDIAction;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.File;
import java.io.IOException;
import java.awt.event.*;
import java.net.*;
import java.util.Scanner;
import javax.sound.sampled.*;
import event.*;
import packageData.*;
import javax.swing.DefaultComboBoxModel;

public class GameClient {

	public static String SERVERIP = "127.0.0.1";
	public static int PORT = 9487;
	public static JFrame frame;
	public static String gKey; // my GaneClient key
	public static String enemyEventClientKey; // EnemyID
	public static String enemyGameClientKey; // Enemy GameClient key
	public static boolean firstSelect;

	//public static Socket ClientSock;
	public static Mixer mixer;
	public static Clip musicBeforeGame;
	public static Clip musicForGame;
	public static UserData userData;

	// Select predefined data
	private static int picked[] = new int[6]; 


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
		frame.setBounds(960-640, 540-480, 1280, 960);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		// StartGame button
		// When start Game Button pressed, Connect to the server
		JButton StartButton = new JButton("Start Game");
		StartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

					// Connect to server and get the EventClient.thisKey
					EventClient.initialize();

					gKey = EventClient.addReference(this,EventClient.getKey());

					if(EventClient.getKey() != null){
						// Clean the Component in the JFrame's ContentPane
						frame.getContentPane().removeAll();
						frame.getContentPane().doLayout();
						frame.getContentPane().update(frame.getContentPane().getGraphics());
						GameClient.secondScene();
					}
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
	public static JLabel confirmedPhoto = null;
	public static Icon confirmedIcon = null;
	public static int photoIndex = 1, webcam_check = 0;
	public static Webcam webcam = Webcam.getDefault();
	public static WebcamPanel panel = null;
	public static void secondScene() {

		// Text Field for client to enter "PlayerID" 
		JTextField PlayerID = new JTextField("Enter Player ID here.", 30);
		PlayerID.setBackground(Color.PINK);
		PlayerID.setFont(new Font("Liberation Mono", Font.BOLD | Font.ITALIC, 24));
		PlayerID.setLocation(865,50); // 50 + 760
		PlayerID.setSize(365,80);
		String PlayerID_String = PlayerID.getText();
		
		// Text Field for client to enter "PlayGender" 
		JTextField PlayerGender = new JTextField("Enter Player Gender here.", 30);
		PlayerGender.setFont(new Font("Liberation Mono", Font.BOLD | Font.ITALIC, 24));
		PlayerGender.setBackground(Color.PINK);
		PlayerGender.setLocation(865,295); 
		PlayerGender.setSize(365,80);
		String PlayerGender_String = PlayerGender.getText();
		
		// Text Field for client to enter "PlayerMotto" 
		JTextField PlayerMotto = new JTextField("Enter Player Motto here.", 30);
		PlayerMotto.setFont(new Font("Liberation Mono", Font.BOLD | Font.ITALIC, 24));
		PlayerMotto.setBackground(Color.PINK);
		PlayerMotto.setLocation(865,540); 
		PlayerMotto.setSize(365,80);
		String PlayerMotto_String = PlayerMotto.getText();
		
		// Button for client to confirm photo
		JButton ConfirmPicture = new JButton("Confirm");
		ConfirmPicture.setBackground(Color.GREEN);
		ConfirmPicture.setForeground(Color.BLUE);
		ConfirmPicture.setFont(new Font("DejaVu Sans", Font.BOLD | Font.ITALIC, 24));
		ConfirmPicture.setLocation(50,850); 
		ConfirmPicture.setSize(230,80);
		
		// Button for client to take webcam's picture
		JButton TakePicture = new JButton("Take Picture");
		TakePicture.setBackground(Color.GREEN);
		TakePicture.setForeground(Color.BLUE);
		TakePicture.setFont(new Font("DejaVu Sans", Font.BOLD | Font.ITALIC, 24));
		TakePicture.setLocation(315,850); 
		TakePicture.setSize(230,80);
		
		// Button for client to use default photo
		JButton Default = new JButton("Use Default");
		Default.setBackground(Color.GREEN);
		Default.setForeground(Color.BLUE);
		Default.setFont(new Font("DejaVu Sans", Font.BOLD | Font.ITALIC, 24));
		Default.setLocation(580,850); 
		Default.setSize(230,80);
		
		// set default photo data
		String[] filepath = {
				"../resource/image/photo_cotaduck.jpg",
				"../resource/image/photo_fbhead.jpeg",
				"../resource/image/photo_littleboy.jpeg",
				"../resource/image/photo_monkey.jpeg",
				"../resource/image/photo_nothing.jpeg",
				"../resource/image/photo_youareloser.jpeg"
		};
		Icon[] defaultPhotoArray = new ImageIcon[6];
		for(int i=0; i<6; ++i)
		{
			defaultPhotoArray[i] = new ImageIcon(filepath[i]);
		}
		
		// JLabel for showing up default photo
		JLabel showup_default_photo = new JLabel(defaultPhotoArray[0]); 
		showup_default_photo.setLocation(50,50);
		showup_default_photo.setSize(760,754);
		
		// 'Confirm' button's action listener 
		ConfirmPicture.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				// set the other buttons' color
				TakePicture.setBackground(Color.RED);
				TakePicture.setForeground(Color.BLACK);
				Default.setBackground(Color.RED);
				Default.setForeground(Color.BLACK);
				
				// remove the other buttons' action listener
				for(ActionListener remove_take_listener : TakePicture.getActionListeners()) {
					TakePicture.removeActionListener(remove_take_listener);
				}
				for(ActionListener remove_default_listener : Default.getActionListeners()) {
					Default.removeActionListener(remove_default_listener);
				}
				
				// set and display choosed photo
				if(webcam_check == 1)
					confirmedIcon = new ImageIcon("./takedPhoto.png");
				else
					confirmedIcon = new ImageIcon(filepath[photoIndex-1]);
				
				confirmedPhoto = new JLabel(confirmedIcon);
				confirmedPhoto.setLocation(50,50);
				confirmedPhoto.setSize(760,754);

				// remove useless component
				webcam.close();
				frame.getContentPane().remove(panel);
				frame.getContentPane().remove(showup_default_photo);
				frame.getContentPane().add(confirmedPhoto);
				frame.getContentPane().doLayout();
				frame.getContentPane().update(frame.getContentPane().getGraphics());
			}
		});
		
		// 'Take Picture' button's action listener 
		TakePicture.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				// set 'Default' button's color
				Default.setBackground(Color.RED);
				Default.setForeground(Color.BLACK);
				
				// remove 'Default' button's action listener
				for(ActionListener remove_default_listener : Default.getActionListeners()) {
					Default.removeActionListener(remove_default_listener);
				}
				
				// take pictures
				BufferedImage takedPhoto = webcam.getImage();
			    BufferedImage bi = new BufferedImage(760, 754,BufferedImage.TRANSLUCENT);
			    Graphics2D g2d = bi.createGraphics();
			    g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
			    g2d.drawImage(takedPhoto, 0, 0, 760, 754, null);
			    g2d.dispose();
				try
				{
					ImageIO.write(bi, "PNG", new File("takedPhoto.png"));
					//ImageIO.write(takedPhoto, "PNG", new File("takedPhoto.png"));
				} catch (IOException e1)
				{
					e1.printStackTrace();
				}
				webcam_check = 1;
			}
		});
		
		
		Default.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// set 'take picture' button's action listener
				TakePicture.setBackground(Color.RED);
				TakePicture.setForeground(Color.BLACK);
				
				// remove 'take picture' button's action listener
				for(ActionListener remove_take_listener : TakePicture.getActionListeners()) {
					TakePicture.removeActionListener(remove_take_listener);
				}
				
				// show the default photo that can be choosed
				frame.getContentPane().remove(panel);
				frame.getContentPane().add(showup_default_photo);
				frame.getContentPane().doLayout();
				frame.getContentPane().update(frame.getContentPane().getGraphics());
				
				// consecutively show default photo
				switch(photoIndex)
				{
					case 0:
						showup_default_photo.setIcon(defaultPhotoArray[photoIndex]);
						photoIndex = 1;
					break;
					case 1:
						showup_default_photo.setIcon(defaultPhotoArray[photoIndex]);
						photoIndex = 2;
					break;
					case 2:
						showup_default_photo.setIcon(defaultPhotoArray[photoIndex]);
						photoIndex = 3;
					break;
					case 3:
						showup_default_photo.setIcon(defaultPhotoArray[photoIndex]);
						photoIndex = 4;
					break;
					case 4:
						showup_default_photo.setIcon(defaultPhotoArray[photoIndex]);
						photoIndex = 5;
					break;
					case 5:
						showup_default_photo.setIcon(defaultPhotoArray[photoIndex]);
						photoIndex = 0;
					break;
				}
				webcam_check = 2;
			}
		});
		
		// Label for client to find opponent
		Icon opponentIcon = new ImageIcon("../resource/image/findopponent_button.png");
		JLabel FindOpponent = new JLabel(opponentIcon);	
		FindOpponent.setLocation(878,691); 
		FindOpponent.setSize(336,237);

		FindOpponent.addMouseListener(new MouseListener(){
			//popUp reminder
			public void mouseClicked(MouseEvent e){
				System.out.println("Clicked...");
				GameClient.scene2Reminder();
			}
			public void mouseEntered(MouseEvent e){
				;
			}	
			public void mouseExited(MouseEvent e){
				;
			}	
			public void mousePressed(MouseEvent e){
				;
			}	
			public void mouseReleased(MouseEvent e){
				;
			}		
		});
		
		// here should add listener for FindOpponent Label
		
		// check whether webcam exists
		if(webcam == null)
		{
			// if webcam is not exist, disable 'take picture' button
			// and show default photo
			TakePicture.setBackground(Color.RED);
			TakePicture.setForeground(Color.BLACK);
			frame.getContentPane().remove(panel);
			frame.getContentPane().add(showup_default_photo);
			frame.getContentPane().doLayout();
			frame.getContentPane().update(frame.getContentPane().getGraphics());
			for(ActionListener remove_take_listener : TakePicture.getActionListeners()) {
				TakePicture.removeActionListener(remove_take_listener);
			}
		}
		else
		{
			// if webcam exist, open webcamPanel automatically
			panel = new WebcamPanel(webcam, true);
			panel.setMirrored(false);
		}	

		panel.setLocation(50,50);
		panel.setSize(760,754);
		
		// add all components to frame
		frame.getContentPane().add(panel);
		frame.getContentPane().add(PlayerID);
		frame.getContentPane().add(PlayerGender);
		frame.getContentPane().add(PlayerMotto);
		frame.getContentPane().add(ConfirmPicture);
		frame.getContentPane().add(TakePicture);
		frame.getContentPane().add(Default);
		frame.getContentPane().add(FindOpponent);
		frame.getContentPane().doLayout();
		frame.getContentPane().update(frame.getContentPane().getGraphics());
	}

	public static void scene2Reminder(){
		JFrame reminder = new JFrame("Reminder");
		reminder.setBounds(960-200, 540-100, 400, 200);
		reminder.getContentPane().setLayout(null);
		reminder.setVisible(true);
		JButton btn1 = new JButton("Yes");
		btn1.setBounds(100-40,100+25,80,50);
		JButton btn2 = new JButton("No");
		btn2.setBounds(300-40,100+25,80,50);

		JLabel msg = new JLabel("Find Opponent ?");
		msg.setFont(new Font("DejaVu Sans Condensed", Font.PLAIN, 20));
		msg.setBounds(200-75,75,200,25);
		
		btn1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				// Send to tell server group me and other player
				EventCient.send("GameServer::match($...)", new Object[]{EventClient.getKey(), gKey},null);
				reminder.dispose();
			}
		});
		reminder.add(msg);
		reminder.add(btn1);
		reminder.add(btn2);
	}

	/*
	 *Third Scene:
	 * Select the Heros 
	 */
	public static void thirdScene() {
		String[] character = {"亞瑟王","高文","莫德雷德","蘭斯洛特","加雷斯","貝迪維爾","崔斯坦","摩根勒菲","加拉哈德","珀西瓦","梅林","閨妮維雅"};
		JLabel character_data = new JLabel("");
		JComboBox comboBox = new JComboBox<String>(character);
		JLabel label = new JLabel("");
		JButton btnNewButton = new JButton("選擇");
		JLabel player1_character_1 = new JLabel("選擇角色中");
		JLabel player1_character_2 = new JLabel("選擇角色中");
		JLabel player1_character_3 = new JLabel("選擇角色中");
		JLabel player2_character_1 = new JLabel("選擇角色中");
		JLabel player2_character_2 = new JLabel("選擇角色中");
		JLabel player2_character_3 = new JLabel("選擇角色中");
		JLabel select[] = new JLabel[]{player1_character_1,player2_character_1,player1_character_2,player2_character_2,player1_character_3,player2_character_3};
		int seleted[] = new int[12];

		// refresh the frame
		frame.getContentPane().removeAll();
		frame.getContentPane().doLayout();
		frame.getContentPane().update(frame.getContentPane().getGraphics());
		
		// add Combo box 
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"亞瑟王","高文","莫德雷德","蘭斯洛特","加雷斯","貝迪維爾","崔斯坦","摩根勒菲","加拉哈德","珀西瓦","梅林","閨妮維雅"}));
		comboBox.setForeground(Color.BLACK);
		comboBox.setFont(new Font("標楷體", Font.BOLD, 40));
		comboBox.setBounds(512, 40, 200, 50);
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int index = comboBox.getSelectedIndex();
				
				if (index != 0) {
					String content = comboBox.getSelectedItem().toString();
					System.out.println("index = " + index + ", character=" + content);
				}
				
				//change the picture
				Icon img = new ImageIcon("../resource/image/" + character[index] + ".png");
				character_data.setIcon(img);
				
			}
		} );


		// used to display the selected car
		ImageIcon display = new ImageIcon("../resource/image/亞瑟王.png");
		display.setImage(display.getImage().getScaledInstance(400,590,Image.SCALE_DEFAULT));
		Icon img = display;
		character_data.setIcon(img);
		character_data.setBounds(425, 150, 400, 590);
		
		//press the "select" button (action)
		btnNewButton.setFont(new Font("標楷體", Font.BOLD, 30));
		btnNewButton.setBounds(559, 802, 131, 56);
		label.setBounds(47, 94, 57, 19);
		btnNewButton.setEnabled((firstSelect)? true:false);
		
		
		// the placeholder for selected card
		player1_character_1.setBounds(110, 10, 200, 295);
		player1_character_2.setBounds(110, 305, 200, 295);	
		player1_character_3.setBounds(110, 600, 200, 295);
		player2_character_1.setBounds(967, 10, 200, 295);
		player2_character_2.setBounds(967, 305, 200, 295);
		player2_character_3.setBounds(967, 600, 200, 295);
		

		// add all the component to the frame
		frame.getContentPane().add(comboBox);
		frame.getContentPane().add(character_data);
		frame.getContentPane().add(btnNewButton);
		frame.getContentPane().add(label);
		frame.getContentPane().add(player1_character_1);
		frame.getContentPane().add(player1_character_2);
		frame.getContentPane().add(player1_character_3);
		frame.getContentPane().add(player2_character_1);
		frame.getContentPane().add(player2_character_2);
		frame.getContentPane().add(player2_character_3);		
		frame.getContentPane().update(frame.getContentPane().getGraphics());
	}

	public static void setPair(String key,boolean firstSelect) {
		GameClient.enemyEventClientKey = key;
		GameClient.firstSelect = firstSelect;
	}

	public static void setGKey(String enemyGKey){
		GameClient.enemyGameClientKey = enemyGKey;
		//Change to third scene
		GameClient.thirdScene();
	}
}
