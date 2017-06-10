import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
//import ch.qos.logback.classic.joran.action.InsertFromJNDIAction;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.File;
import java.io.IOException;
import java.awt.event.*;
import java.net.*;
import javax.sound.sampled.*;
import event.*;
import packageData.*;
import javax.swing.DefaultComboBoxModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.*;
import javax.swing.UIManager.*;
import javax.swing.UIManager.LookAndFeelInfo;

public class GameClient {

	public static String SERVERIP = "172.20.10.2";
	public static int PORT = 9487;
	public static JFrame frame;
	public static String gKey; // my GaneClient key
	public static String enemyEventClientKey; // EnemyID
	public static String enemyGameClientKey; // Enemy GameClient key

	//public static Socket ClientSock;
	public static Mixer mixer;
	public static Clip musicBeforeGame;
	public static Clip musicForGame;
	public static UserData userData;

	// Scene2Data
	public static String PlayerID_String;
	public static String PlayerGender_String;
	public static JLabel confirmedPhoto = null;
	public static Icon confirmedIcon = null;
	public static int photoIndex = 1, webcam_check = 0;
	public static Webcam webcam = Webcam.getDefault();
	public static WebcamPanel panel = null;
	public static ImageIcon confirmedToSend = null;
	// Data of opponent
	public static String opponentID;
	public static String opponentGender;
	public static ImageIcon opponentPhoto;

	// Scene3Data
	public static String[] character = {"亞瑟王","高文","莫德雷德","蘭斯洛特","加雷斯","貝迪維爾","崔斯坦","摩根勒菲","加拉哈德","珀西瓦","梅林","閨妮維雅"};
	public static JLabel character_data_label = new JLabel("");
	public static JComboBox comboBox = new JComboBox<String>(character);
	public static JLabel label = new JLabel("");
	public static JButton btnNewButton = new JButton("選擇");
	public static JLabel player1_character_1 = new JLabel("選擇角色中");
	public static JLabel player1_character_2 = new JLabel("選擇角色中");
	public static JLabel player1_character_3 = new JLabel("選擇角色中");
	public static JLabel player2_character_1 = new JLabel("選擇角色中");
	public static JLabel player2_character_2 = new JLabel("選擇角色中");
	public static JLabel player2_character_3 = new JLabel("選擇角色中");
	public static JLabel select[] = new JLabel[]{player1_character_1,player2_character_1,player1_character_2,player2_character_2,player1_character_3,player2_character_3};
	public static int seleted[] = new int[12];
	public static int myCount = 0;
	public static int oppCount = 1;
	
	// Select predefined data
	public static int picked[] = new int[6]; 
	public static boolean firstSelect;

	// Scene4Data for GUI
	public static JPanel contentPane;
	public  static CreateCharacter character_data = new CreateCharacter();
	public static Random rand = new Random();
	public static JPanel dice_state = new JPanel();
		public static JLabel atk_icon = new JLabel();
		public static JLabel def_icon = new JLabel();
		public static JLabel spec_icon = new JLabel();
		public static JLabel atk_num = new JLabel("2");
		public static JLabel def_num = new JLabel("2");
		public static JLabel spec_num = new JLabel("5");
	
		
	//角色選單	
	public static JPanel character_state = new JPanel();
		//select mode1
		public static JPanel skill1 = new JPanel();
			public static JButton active1 = new JButton("Active");
			public static JTextPane skill_describe1 = new JTextPane();
		public static JPanel skill2 = new JPanel();
			public static JButton active2 = new JButton("Active");
			public static JTextPane skill_describe2 = new JTextPane();
		
		//select mode2
		public static JPanel select_char = new JPanel();
			public static JLabel active_character = new JLabel("USE CHARACTER");
			public static JButton launch_select = new JButton("Launch");
			public static JButton cancel_select = new JButton("Cancel");

		//select mode3
		public static JPanel attack_select = new JPanel();
			public static JLabel who_attack = new JLabel("Attecker");
			public static JLabel attack_who = new JLabel("Attack which character");
			public static JButton Attack_attack_select = new JButton("Attack");
			public static JButton Cancel_attack_select = new JButton("Cancel");
		
		//select mode4
		public static JPanel skill_use = new JPanel();
			public static JLabel who_attack_skill_use = new JLabel("Attecker");
			public static JLabel attack_who_skill_use = new JLabel("Attack which character");
			public static JButton skill_use_yes = new JButton("YES");
			public static JButton skill_use_no = new JButton("NO");
			public static JLabel UseSkill_text = new JLabel("Use Skill?");
			public static JLabel Attack_text = new JLabel("Attack");
		
		//select mode5
		public static	JPanel def_dise_choose = new JPanel();
			public static	JTextField def_dise_enter = new JTextField();
			public static	JTextPane def_choose_text = new JTextPane();
			public static	JButton def_dise_choose_OK = new JButton("OK");
			
		//select mode6
		public static JPanel attack_dise_choose = new JPanel();
			public static JTextField atk_dise_enter = new JTextField();
			public static JTextPane atk_choose_text = new JTextPane();
			public static JButton atk_dise_choose_OK = new JButton("OK");
			public static JButton atk_dise_choose_cancel = new JButton("Cancel");
		
		//select mode7
		public static	JPanel thorw_dise = new JPanel();
			public static	JLabel dise_0 = new JLabel("1");
			public static	JLabel dise_1 = new JLabel("1");
			public static	JLabel dise_2 = new JLabel("1");
			public static	JLabel dise_3 = new JLabel("1");
			public static  JLabel text_this_stage = new JLabel("本回合獲得的骰子：");
				
	//character_button_select
	public static JButton opponent_character_1 = new JButton("");
	public static JButton opponent_character_2 = new JButton("");
	public static JButton opponent_character_3 = new JButton("");
	public static JButton self_character_1 = new JButton("");
	public static JButton self_character_2 = new JButton("");
	public static JButton self_character_3 = new JButton("");
	public static JButton character_button_select[] = new JButton[]{self_character_1,opponent_character_1,
																	 self_character_2,opponent_character_2,
																	 self_character_3,opponent_character_3};	
	//character_label_state
	public static JLabel opponent_character_1_data = new JLabel("New label");
	public static JLabel opponent_character_2_data = new JLabel("New label");
	public static JLabel opponent_character_3_data = new JLabel("New label");
	public static JLabel self_character_1_data = new JLabel("New label");
	public static JLabel self_character_2_data = new JLabel("New label");
	public static JLabel self_character_3_data = new JLabel("New label");
	public static JLabel character_label_state[] = new JLabel[]{self_character_1_data , opponent_character_1_data ,
																 self_character_2_data , opponent_character_2_data , 
																 self_character_3_data , opponent_character_3_data};
	
	//左中選單
	public static JLabel stage = new JLabel("New label");  
	public static JButton ready = new JButton("Ready");
	public static JButton attack = new JButton("Attack");
	public static JButton end = new JButton("End Phase");
	public static JButton surrender = new JButton("Surrender");
	
	//存放該場遊戲雙方所選擇的角色
	public static Boolean[] character_alive = new Boolean[]{true,true,true,true,true,true};
	
	//現在的階段 //變成由firstselect控制
	public static final int atk_stage = 1;
	public static final int def_stage = 2;
	public static int phase_stage;
	
	//右邊角色選單的條件判斷
	public static int character_state_mode = 0;
	public static boolean end_using_skill = true;
	
	//在選擇發動技能時所按下的角色(會不斷變更
	public static int window_character = 0;
	//儲存發動技能的角色
	public static int window_skillUse_character = 0;
	
	//判斷按下的技能是哪個
	public static int skill_select = 0;
	
	//判斷攻擊時是否有選好2個角色(攻擊時)
	public static int attack_judge = -1;
	public static int attacker_judge = -1;
	
	//判斷是誰攻擊誰
	public static int attacker_test = 1;
	public static int attack_test = 0;
	
	
	public static boolean attack_all = false;
	public static int attack_all_count = 0;


	// Chat room 
	public static JTextArea chatContentDisplay = null; // Text Show in this area
	public static String get = null; 
	public static JFrame chat = null; // chatRoom JFrame 
	public static String[] filepath = {
		"../resource/image/photo_cotaduck.jpg",
		"../resource/image/photo_fbhead.jpeg",
		"../resource/image/photo_littleboy.jpeg",
		"../resource/image/photo_monkey.jpeg",
		"../resource/image/photo_nothing.jpeg",
		"../resource/image/photo_youareloser.jpeg"
	};
/*------------------------------------------------------------------------------------------------------------
/
/	Main program
/
*------------------------------------------------------------------------------------------------------------*/

	public static void main(String[] args) throws Exception {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
						if ("Nimbus".equals(info.getName())) {
						    UIManager.setLookAndFeel(info.getClassName());
						    break;
						}
					}
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
	public void initialize() {
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

		// Label for gif
		JLabel cardBoy = new JLabel((Icon) new ImageIcon ("../resource/image/use1.gif"));
		cardBoy.setBounds(300,300,500,500);
		frame.getContentPane().add(cardBoy);

		// StartGame button
		// When start Game Button pressed, Connect to the server
		JButton StartButton = new JButton("Start Game");
		StartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

					// Connect to server and get the EventClient.thisKey
					EventClient.initialize(SERVERIP);
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
	public static void secondScene() {

		// Text Field for client to enter "PlayerID" 
		JTextField PlayerID = new JTextField("Enter Player ID here.", 30);
		PlayerID.setBackground(Color.PINK);
		PlayerID.setFont(new Font("Liberation Mono", Font.BOLD | Font.ITALIC, 24));
		PlayerID.setLocation(865,50); // 50 + 760
		PlayerID.setSize(365,80);
		
		// Text Field for client to enter "PlayGender" 
		JTextField PlayerGender = new JTextField("Enter Player Gender here.", 30);
		PlayerGender.setFont(new Font("Liberation Mono", Font.BOLD | Font.ITALIC, 24));
		PlayerGender.setBackground(Color.PINK);
		PlayerGender.setLocation(865,230); 
		PlayerGender.setSize(365,80);

		ImageIcon diceShow = new ImageIcon("../resource/image/dice.gif");
		diceShow.setImage(diceShow.getImage().getScaledInstance(270,224,Image.SCALE_DEFAULT));
		Icon diceIcon = diceShow;
		JLabel diceGif = new JLabel(diceIcon);
		diceGif.setBounds(919, 398, 270, 224);

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
				if(webcam_check == 1){
					confirmedToSend = new ImageIcon("./takedPhoto.png"); 
					confirmedIcon = confirmedToSend;
				}else{
					confirmedToSend = new ImageIcon(filepath[photoIndex-1]);
					confirmedIcon = confirmedToSend;
				}

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
				PlayerID_String = PlayerID.getText();
				PlayerGender_String = PlayerGender.getText();
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
		frame.getContentPane().add(ConfirmPicture);
		frame.getContentPane().add(TakePicture);
		frame.getContentPane().add(Default);
		frame.getContentPane().add(FindOpponent);
		frame.getContentPane().add(diceGif);
		frame.getContentPane().doLayout();
		frame.getContentPane().update(frame.getContentPane().getGraphics());
	}

	// PopUp reminder [ To find opponent or not]
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
				EventClient.send("GameServer::match($)",EventClient.getKey(),null);
				reminder.dispose();
			}
		});
		reminder.add(msg);
		reminder.add(btn1);
		reminder.add(btn2);
	}

	// Set enenyEventClientKey [ place this key to send(,,to[enenyEventClientKey]) ]
	public static void setPair(String key, Boolean firstSelect) {
		System.out.println("Set pair triggered.");
		GameClient.enemyEventClientKey = key;
		GameClient.firstSelect = firstSelect;

		userData = new UserData(confirmedToSend,PlayerID_String,PlayerGender_String);
		PacketData packageData = new PacketData(userData,null,null,null);
		EventClient.send("GameClient::playersInit($...)",new Object[]{gKey,packageData},key);
	}

	// Initial two players basic info
	public static void playersInit(String enemyGKey,PacketData packageData){
		GameClient.enemyGameClientKey = enemyGKey;
		opponentID = packageData.getUserData().playerID;
		opponentGender = packageData.getUserData().playerGender;
		opponentPhoto = packageData.getUserData().photo;

		System.out.println("Opponent info:");
		System.out.println(opponentID+":"+(opponentID!= null));
		System.out.println(opponentGender+":" +(opponentGender != null));
		System.out.println(opponentPhoto+":"+(opponentPhoto!=null));

		//Change to third scene
		GameClient.thirdScene();
	}

	/*
	 *Third Scene:
	 * Select the Heros 
	 */
	public static void thirdScene() {
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
				character_data_label.setIcon(img);
				
			}
		} );


		// used to display the selected car
		ImageIcon display = new ImageIcon("../resource/image/亞瑟王.png");
		display.setImage(display.getImage().getScaledInstance(400,590,Image.SCALE_DEFAULT));
		Icon img = display;
		character_data_label.setIcon(img);
		character_data_label.setBounds(425, 150, 400, 590);
		
		//press the "select" button (action)
		btnNewButton.setFont(new Font("標楷體", Font.BOLD, 30));
		btnNewButton.setBounds(559, 802, 131, 56);
		label.setBounds(47, 94, 57, 19);
		btnNewButton.setEnabled((firstSelect)? true:false);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				if(seleted[comboBox.getSelectedIndex()]==0)
				{
					//set the character image
					ImageIcon img = new ImageIcon("../resource/image/" + character[comboBox.getSelectedIndex()] + ".png");
					img.setImage(img.getImage().getScaledInstance(200,295,Image.SCALE_DEFAULT));
					
					seleted[comboBox.getSelectedIndex()]=1;
					picked[myCount] = comboBox.getSelectedIndex();
					select[myCount].setIcon(img);
					myCount+=2;
					
					btnNewButton.setEnabled(false);
					
					//when the character are all selected
					if(myCount==6 && !firstSelect){	
						EventClient.send("GameClient::my_turn_to_select($...)",new Object[]{comboBox.getSelectedIndex(),oppCount},enemyEventClientKey);
						EventClient.send("GameClient::game_is_getting_to_start()",enemyEventClientKey);	
						JOptionPane.showMessageDialog(null, "遊戲即將開始");
						//change to scene 4
						GameClient.fourthScene();
					}
					else{
						EventClient.send("GameClient::my_turn_to_select($...)",new Object[]{comboBox.getSelectedIndex(),oppCount},enemyEventClientKey);
						oppCount+=2;
					};
						
				}
				else
				{
					JOptionPane.showMessageDialog(null, "該角色已被選走");
				}
				comboBox.setSelectedIndex(0);
			}
		});
		
		// the placeholder for selected card
		player1_character_1.setBounds(110, 10, 200, 295);
		player1_character_2.setBounds(110, 305, 200, 295);	
		player1_character_3.setBounds(110, 600, 200, 295);
		player2_character_1.setBounds(967, 10, 200, 295);
		player2_character_2.setBounds(967, 305, 200, 295);
		player2_character_3.setBounds(967, 600, 200, 295);
		

		// add all the component to the frame
		frame.getContentPane().add(comboBox);
		frame.getContentPane().add(character_data_label);
		frame.getContentPane().add(btnNewButton);
		frame.getContentPane().add(label);
		frame.getContentPane().add(player1_character_1);
		frame.getContentPane().add(player1_character_2);
		frame.getContentPane().add(player1_character_3);
		frame.getContentPane().add(player2_character_1);
		frame.getContentPane().add(player2_character_2);
		frame.getContentPane().add(player2_character_3);		
		frame.getContentPane().update(frame.getContentPane().getGraphics());

		// After match successed, build the chat room
		GameClient.chatRoom();
	}

	// Used to select the Hero
	public static void my_turn_to_select(Integer select_which_character,Integer index){
		ImageIcon img = new ImageIcon("../resource/image/" + character[select_which_character] + ".png");
		img.setImage(img.getImage().getScaledInstance(200,295,Image.SCALE_DEFAULT));
		
		seleted[select_which_character]=1;
		picked[index] = select_which_character;
		select[index].setIcon(img);
		
		btnNewButton.setEnabled(true);
	}
	
	// After all hero is selected, change to fourth scene.
	public static void game_is_getting_to_start(){
		JOptionPane.showMessageDialog(null, "遊戲即將開始");
		//change to scene 4
		GameClient.fourthScene();
	}

	public static void fourthScene(){
		// refresh the frame
		frame.getContentPane().removeAll();
		int index = (int)(Math.random()*3+1);
		//set new Gameing background
		JLabel gameBg = new JLabel();
		gameBg.setIcon(new ImageIcon("../resource/image/gamebg"+index+".jpg"));
		frame.setContentPane(gameBg);

		// set new music for gameing
		// and close the bgmusic
		//	get the Clip Dataline Connected to the mixer
		try{
		musicBeforeGame.stop(); 
		DataLine.Info dataInfo = new DataLine.Info(Clip.class, null);
		musicForGame = (Clip) mixer.getLine(dataInfo);
		
		// the input Source: bgMusic
		// Through the Dataline, let bgMusic data go into mixer and play.
		File gameMusic = new File("../resource/music/musicbg"+index+".wav");
		AudioInputStream audioInput = AudioSystem.getAudioInputStream(gameMusic);
		musicForGame.open(audioInput);
		musicForGame.loop(Clip.LOOP_CONTINUOUSLY);
		}catch(Exception e){
			System.out.println("Change music error!");
		}

		phase_stage = (firstSelect)?atk_stage:def_stage;
		System.out.println("Your phase is : " + phase_stage);		
		windows_construct();
		null_construct();
		frame.getContentPane().update(frame.getContentPane().getGraphics());
	}

	//清空(完全不顯示
	public static void character_windows_show_nothing(){
		character_state_mode = 1;
		//mode1
		skill1.setVisible(false);  
		skill2.setVisible(false); 
		//mode2
		select_char.setVisible(false);
		//mode3
		attack_select.setVisible(false);
		//mode4
		skill_use.setVisible(false);		
		//mode5
		def_dise_choose.setVisible(false);
		//mode6
		attack_dise_choose.setVisible(false);
		//mode7
		thorw_dise.setVisible(false);
		frame.getContentPane().update(frame.getContentPane().getGraphics());
	}

	//清空(有條件判斷
	public static void null_construct(){
		if(end_using_skill) 
		{
			if(character_state_mode==7)
			{
				windows_stage_select();
				character_windows_show_nothing();
				character_state_mode=7;
			}
			else
			{
				character_windows_show_nothing();
			}
		}
		else{  //繼續顯示mode4
			if(character_state_mode != 4)
			{
				check_use_skill_construct_V2();
			}
			else 
			{
				character_windows_show_nothing();
			}
		}	
		frame.getContentPane().update(frame.getContentPane().getGraphics());
	}

	//mode1 查看角色技能
	public static void skill_active_construct(int card){
		character_state_mode = 1;
		//mode2
		select_char.setVisible(false);
		//mode3
		attack_select.setVisible(false);
		//mode4
		skill_use.setVisible(false);
		//mode5
		def_dise_choose.setVisible(false);
		//mode6
		attack_dise_choose.setVisible(false);
		//mode7
		thorw_dise.setVisible(false);
		//frame.getContentPane().update(frame.getContentPane().getGraphics());
		
		//mode1
		skill1.setVisible(true);  
		skill2.setVisible((character_data.character[picked[card]].get_skill_num()==1)?false:true) ; 
		skill_describe1.setText(character_data.character[picked[card]].get_skill1());
		skill_describe2.setText(character_data.character[picked[card]].get_skill2());
		
		if(phase_stage == atk_stage) //atk stage
		{
			//skill1 judge can use
			if( (card%2)==0 &&
			   (character_data.character[picked[window_character]].skill1_use_stage() == Character_Data.Atk || 
			    character_data.character[picked[window_character]].skill1_use_stage() == Character_Data.both))
			{	active1.setEnabled(true);	}
			else{	active1.setEnabled(false);	}
			
			//skill2 judge can use
			if( (card%2)==0 &&
				(character_data.character[picked[window_character]].skill2_use_stage() == Character_Data.Atk || 
				 character_data.character[picked[window_character]].skill2_use_stage() == Character_Data.both))
			{	active2.setEnabled(true);	}
			else{	active2.setEnabled(false);	}
		}
		if( phase_stage == def_stage )
		{
			//skill1 judge can use
			if( (card%2)==0 &&
			   (character_data.character[picked[window_character]].skill1_use_stage() == Character_Data.def || 
			    character_data.character[picked[window_character]].skill1_use_stage() == Character_Data.both))
			{	active1.setEnabled(true);	}
			else{	active1.setEnabled(false);	}
			
			//skill2 judge can use
			if( (card%2)==0 && phase_stage == 2 &&
				(character_data.character[picked[window_character]].skill2_use_stage() == Character_Data.def || 
				 character_data.character[picked[window_character]].skill2_use_stage() == Character_Data.both))
			{	active2.setEnabled(true);	}
			else{	active2.setEnabled(false);	}
		}	
		frame.getContentPane().update(frame.getContentPane().getGraphics());		
	}

	//mode2 選擇作用對象
	public static void select_lunch_construct(){
		character_state_mode = 2;
		
		//mode1
		skill1.setVisible(false);  
		skill2.setVisible(false); 
		//mode2
		select_char.setVisible(true);
			active_character.setIcon(null);
			active_character.setText("選擇施放對象");	
			launch_select.setEnabled(false);
		//mode3
		attack_select.setVisible(false);
		//mode4
		skill_use.setVisible(false);
		//mode5
		def_dise_choose.setVisible(false);
		//mode6
		attack_dise_choose.setVisible(false);
		//mode7
		thorw_dise.setVisible(false);
		frame.getContentPane().update(frame.getContentPane().getGraphics());
	}

	//mode3 選擇攻擊對象
	public static void select_attack_construct(){
		character_state_mode = 3;
		//mode1
		skill1.setVisible(false);  
		skill2.setVisible(false); 
		//mode2
		select_char.setVisible(false);
		//mode3
		attack_select.setVisible(true);
			who_attack.setIcon(null);
			attack_who.setIcon(null);
			who_attack.setText("由誰發動攻擊");
			attack_who.setText("攻擊誰");
			Attack_attack_select.setEnabled(false);
			attack_judge = -1;
			attacker_judge = -1;
		//mode4
		skill_use.setVisible(false);
		//mode5
		def_dise_choose.setVisible(false);
		//mode6
		attack_dise_choose.setVisible(false);
		//mode7
		thorw_dise.setVisible(false);
		frame.getContentPane().update(frame.getContentPane().getGraphics());
	}

	//mode 選擇要否執行技能  //被敵方攻擊時
	public static void check_use_skill_construct(GameData gameData){
		character_state_mode = 4;
		//mode1
		skill1.setVisible(false);  
		skill2.setVisible(false);
		//mode2
		select_char.setVisible(false);
		//mode3
		attack_select.setVisible(false);
		//mode4
		skill_use.setVisible(true);
		//mode5
		def_dise_choose.setVisible(false);
		//mode6
		attack_dise_choose.setVisible(false);
		//mode7
		thorw_dise.setVisible(false);
		
		
		attacker_test = gameData.get_attacker();
		attack_test = gameData.get_attacked();
		
		end_using_skill = false;  

		
		//***********************************************************
			System.out.println(character_data.character[picked[attacker_test]].get_name() +"發動攻擊");
			System.out.println("攻擊："+ character_data.character[picked[attacker_test]].get_now_attack());
			System.out.println(character_data.character[picked[attack_test]].get_name() +"承受攻擊");
			System.out.println("character_state_mode = " +character_state_mode);
		//***********************************************************
		
		
		frame.getContentPane().update(frame.getContentPane().getGraphics());
		//receive_attackpack_and_set_character_state(gamedata);
		 
		 
		ImageIcon img = new ImageIcon("../resource/image/"+ character[picked[attacker_test]]+".png");
		img.setImage(img.getImage().getScaledInstance(200,295,Image.SCALE_DEFAULT));
		who_attack_skill_use.setIcon(img);

		img = new ImageIcon("../resource/image/"+ character[picked[attack_test]]+".png");
		img.setImage(img.getImage().getScaledInstance(200,295,Image.SCALE_DEFAULT));
		attack_who_skill_use.setIcon(img);
	}
	public static void check_use_skill_construct_V2(){
		
		System.out.println("in check_use_skill_construct_V2");
		
		character_state_mode = 4;
		//mode1
		skill1.setVisible(false);  
		skill2.setVisible(false);
		//mode2
		select_char.setVisible(false);
		//mode3
		attack_select.setVisible(false);
		//mode4
		skill_use.setVisible(true);
		//mode5
		def_dise_choose.setVisible(false);
		//mode6
		attack_dise_choose.setVisible(false);
		//mode7
		thorw_dise.setVisible(false);
		
		//end_using_skill = false;
		//null_construct();
		 
		ImageIcon img = new ImageIcon("../resource/image/"+ character[picked[attacker_test]]+".png");
		img.setImage(img.getImage().getScaledInstance(200,295,Image.SCALE_DEFAULT));
		who_attack_skill_use.setIcon(img);

		img = new ImageIcon("../resource/image/"+ character[picked[attack_test]]+".png");
		img.setImage(img.getImage().getScaledInstance(200,295,Image.SCALE_DEFAULT));
		attack_who_skill_use.setIcon(img);
		frame.getContentPane().update(frame.getContentPane().getGraphics());
	}

	//mode5 選擇防禦骰量
	public static void def_Dise_enter_construct() {
		character_state_mode = 5;
		//mode1
		skill1.setVisible(false);  
		skill2.setVisible(false); 
		//mode2
		select_char.setVisible(false);
		//mode3
		attack_select.setVisible(false);
		//mode4
		skill_use.setVisible(false);
		//mode5 true
		def_dise_choose.setVisible(true);
		//mode6
		attack_dise_choose.setVisible(false);
		//mode7
		thorw_dise.setVisible(false);
		frame.getContentPane().update(frame.getContentPane().getGraphics());
	}
	 //mode6 選擇攻擊骰量
	public static void atk_Dise_enter_construct(){
		character_state_mode = 6;
		//mode1
		skill1.setVisible(false);  
		skill2.setVisible(false); 
		//mode2
		select_char.setVisible(false);
		//mode3
		attack_select.setVisible(false);
		//mode4
		skill_use.setVisible(false);
		//mode5
		def_dise_choose.setVisible(false);
		//mode6
		attack_dise_choose.setVisible(true);
		//mode7
		thorw_dise.setVisible(false);
		frame.getContentPane().update(frame.getContentPane().getGraphics());
	}
	
	
	
	public static void receive_attackpack_and_set_character_state(GameData gamedata){
		attacker_test = gamedata.get_attacker();
		attack_test = gamedata.get_attacked();
		for(int i=0;i<6;i++)
		{
			character_data.character[picked[i]].set_now_attack(gamedata.get_character_state(i).get_now_attack());
			character_data.character[picked[i]].set_now_defence(gamedata.get_character_state(i).get_now_defence());
			character_data.character[picked[i]].set_hp(gamedata.get_character_state(i).get_hp());
			character_alive[i] = gamedata.get_character_state(i).get_alive();	
			
			System.out.println(character_data.character[picked[i]].get_name());
			System.out.println("ATK" +character_data.character[picked[i]].get_now_attack());
			System.out.println("DEF" +character_data.character[picked[i]].get_now_defence());
			System.out.println("HP" +character_data.character[picked[i]].get_hp());
		}
		update();
		frame.getContentPane().update(frame.getContentPane().getGraphics());
		
		//can_attack_and_useSkill();
	}
	
	//選擇技能發動對項 對應到角色button //mode2時按下
	//選擇技能發動對象
	public static void skill_active_select(int card){
		ImageIcon img = new ImageIcon("../resource/image/"+ character[picked[card]]+".png");
		img.setImage(img.getImage().getScaledInstance(200,295,Image.SCALE_DEFAULT));
		active_character.setIcon(img);
		active_character.setText("USE CHARACTER");
		
		launch_select.setEnabled(false);
		if(skill_select==1){
			//判斷發動對象是否正確
			if(character_data.character[picked[window_skillUse_character]].skill1_use_char() == Character_Data.teammate_1 && (card%2)==0)
			{
				launch_select.setEnabled(true);
			}
			if(character_data.character[picked[window_skillUse_character]].skill1_use_char() == Character_Data.self && card==window_skillUse_character)
			{
				launch_select.setEnabled(true);
			}
			if(character_data.character[picked[window_skillUse_character]].skill1_use_char() == Character_Data.teammate_all && (card%2)==0)
			{
				launch_select.setEnabled(true);
			}
		}
		if(skill_select==2){
			//判斷發動對象是否正確
			if(character_data.character[picked[window_skillUse_character]].skill2_use_char() == Character_Data.teammate_1 && (card%2)==0)
			{
				launch_select.setEnabled(true);
			}
			if(character_data.character[picked[window_skillUse_character]].skill2_use_char() == Character_Data.self && card==window_skillUse_character)
			{
				launch_select.setEnabled(true);
			}
			if(character_data.character[picked[window_skillUse_character]].skill2_use_char() == Character_Data.teammate_all && (card%2)==0)
			{
				launch_select.setEnabled(true);
			}
		}
	}
	//選擇攻擊對象 對應到角色BUTTON //左中選單ATTACK 按下時
	//選擇攻擊對象
	public static void attack_select(int card){
		if((card%2)==0){
			attacker_judge = card;
			ImageIcon img = new ImageIcon("../resource/image/"+character[picked[card]]+".png");
			img.setImage(img.getImage().getScaledInstance(200,295,Image.SCALE_DEFAULT));
			who_attack.setIcon(img);
			who_attack.setText("");
		}
		else
		{
			attack_judge = card;
			ImageIcon img = new ImageIcon("../resource/image/"+character[picked[card]]+".png");
			img.setImage(img.getImage().getScaledInstance(200,295,Image.SCALE_DEFAULT));
			attack_who.setIcon(img);
			attack_who.setText("");
		}
		
		if(attacker_judge>=0 && attack_judge>=0)
		{
			Attack_attack_select.setEnabled(true);
		}	
	}
	
	
	//判斷攻擊、防禦階段 可使用按鈕有差別
	public static void windows_stage_select(){
		stage.setText((phase_stage == atk_stage)?"攻擊階段":"防禦階段");
		if((phase_stage == atk_stage))
		{
			ready.setEnabled(true);
			attack.setEnabled(false);	
			end.setEnabled(false);	
		}
		if(phase_stage == def_stage)
		{
			ready.setEnabled(false);
			attack.setEnabled(false);
			end.setEnabled(false);	
		}
		frame.getContentPane().update(frame.getContentPane().getGraphics());
	}
	
	
	//設定準備階段按下後所出現的骰子
	public static void set_dise_icon(int i,int j){
		//System.out.println(x);
		String jpg = (i==0)? "sword.png":
					 (i==1)? "sword_2.png":
					 (i==2)? "shield.png":
					 (i==3)? "shield_2.png":
					 (i==4)? "special.png":"spec_2.png";

		ImageIcon img = new ImageIcon("../resource/image/"+jpg);
		img.setImage(img.getImage().getScaledInstance(120,120,Image.SCALE_DEFAULT));
		
		if(j==0) dise_0.setIcon(img);
		if(j==1) dise_1.setIcon(img);
		if(j==2) dise_2.setIcon(img);
		if(j==3) dise_3.setIcon(img);
	}
	
	public static void throw_Dise(){
		character_state_mode = 1;
		thorw_dise.setVisible(true);
		for(int i=0;i<4;i++)
		{
			int dise = rand.nextInt(6);
			//System.out.println(dise);
			switch (dise)
			{
				case 0:
					set_dise_icon(0,i);
					atk_num.setText(Integer.toString((Integer.valueOf(atk_num.getText()) + 1)));
					break;
				case 1:
					set_dise_icon(1,i);
					atk_num.setText(Integer.toString((Integer.valueOf(atk_num.getText()) + 2)));
					break;
				case 2:
					set_dise_icon(2,i);
					def_num.setText(Integer.toString((Integer.valueOf(def_num.getText()) + 1)));
					break;
				case 3:
					set_dise_icon(3,i);
					def_num.setText(Integer.toString((Integer.valueOf(def_num.getText()) + 2)));
					break;
				case 4:
					set_dise_icon(4,i);
					spec_num.setText(Integer.toString((Integer.valueOf(spec_num.getText()) + 1)));
					break;
				case 5:
					set_dise_icon(5,i);
					spec_num.setText(Integer.toString((Integer.valueOf(spec_num.getText()) + 2)));
					break;
			}
		}	
	}
	
	
	//遊戲介面construct
	public static void windows_construct(){
		
		//角色目前狀況
		windows_character_button_construct();
		windows_character_state_construct();
		characterButton_listener();
		
		//左中選單
		windows_stage_select_construct();
		windows_stage_select(); //判斷攻擊、防禦階段 可使用按鈕有差別
		stage_listener();
		
		//右邊骰子介面
		windows_dice_state_construct();
		//右邊角色介面(還有一個獨立的FUNC 來建構
		windows_characterState_construct();		
		
	}	

	//角色目前狀況
	public static void characterButton_listener(){
		//各情況下按下會有不同影響
		//mode1 顯示角色資訊
		//mode2 選擇技能發動對項
		//mode3 選擇攻擊對象
		//mode4 無結果
		//mode5 無結果
		//mode6 無結果
		
		self_character_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				window_character = 0;
				if(character_state_mode==1) skill_active_construct(window_character); 
				if(character_state_mode==2) skill_active_select(window_character);
				if(character_state_mode==3) attack_select(window_character);
				if(character_state_mode==4);
				if(character_state_mode==5);
				if(character_state_mode==6);
				if(character_state_mode==7);
			}
		});

		opponent_character_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				window_character = 1;
				if(character_state_mode==1) skill_active_construct(window_character);
				if(character_state_mode==2) skill_active_select(window_character);
				if(character_state_mode==3) attack_select(window_character);
				if(character_state_mode==4);
				if(character_state_mode==5);
				if(character_state_mode==6);
				if(character_state_mode==7);
			}
		});

		self_character_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				window_character = 2;
				if(character_state_mode==1) skill_active_construct(window_character);
				if(character_state_mode==2) skill_active_select(window_character);
				if(character_state_mode==3) attack_select(window_character);
				if(character_state_mode==4);
				if(character_state_mode==5);
				if(character_state_mode==6);
				if(character_state_mode==7);
			}
		});

		opponent_character_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				window_character = 3;
				if(character_state_mode==1) skill_active_construct(window_character);
				if(character_state_mode==2) skill_active_select(window_character);
				if(character_state_mode==3) attack_select(window_character);
				if(character_state_mode==4);
				if(character_state_mode==5);
				if(character_state_mode==6);
				if(character_state_mode==7);
			}
		});

		self_character_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				window_character = 4;
				if(character_state_mode==1) skill_active_construct(window_character);
				if(character_state_mode==2) skill_active_select(window_character);
				if(character_state_mode==3) attack_select(window_character);
				if(character_state_mode==4);
				if(character_state_mode==5);
				if(character_state_mode==6);
				if(character_state_mode==7);
			}
		});

		opponent_character_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				window_character = 5;
				if(character_state_mode==1) skill_active_construct(window_character);
				if(character_state_mode==2) skill_active_select(window_character);
				if(character_state_mode==3) attack_select(window_character);
				if(character_state_mode==4);
				if(character_state_mode==5);
				if(character_state_mode==6);
				if(character_state_mode==7);
			}
		});
	}

	public static void windows_character_button_construct(){
		//icon button self1	
		self_character_1.setBounds(50, 550, 200, 295);
		frame.add(self_character_1);
		
		//icon button opp1		
		opponent_character_1.setBounds(50, 50, 200, 295);
		frame.add(opponent_character_1);

		//icon button self2
		self_character_2.setBounds(275, 550, 200, 295);
		frame.add(self_character_2);
				
		//icon button opp2
		opponent_character_2.setBounds(275, 50, 200, 295);
		frame.add(opponent_character_2);
		
		//icon button self3
		self_character_3.setBounds(500, 550, 200, 295);
		frame.add(self_character_3);
		
		//icon button opp3
		opponent_character_3.setBounds(500, 50, 200, 295);
		frame.add(opponent_character_3);
		
		//set icon
		for(int i=0;i<6;i++)
		{
			ImageIcon img = new ImageIcon("../resource/image/"+character[picked[i]]+".png");
			img.setImage(img.getImage().getScaledInstance(200,295,Image.SCALE_DEFAULT));
			character_button_select[i].setIcon(img);
			character_button_select[i].setText("");
		}
	}

	public static void windows_character_state_construct(){
		opponent_character_1_data.setHorizontalAlignment(SwingConstants.CENTER);
		//text opp1
		opponent_character_1_data.setBounds(50, 375, 200, 20);
		frame.add(opponent_character_1_data);
		opponent_character_2_data.setHorizontalAlignment(SwingConstants.CENTER);
		
		//text opp2
		opponent_character_2_data.setBounds(275, 375, 200, 20);
		frame.add(opponent_character_2_data);
		opponent_character_3_data.setHorizontalAlignment(SwingConstants.CENTER);
		
		//text opp3
		opponent_character_3_data.setBounds(500, 375, 200, 20);
		frame.add(opponent_character_3_data);
		self_character_1_data.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		//text self1
		self_character_1_data.setBounds(50, 507, 200, 20);
		frame.add(self_character_1_data);
		self_character_2_data.setHorizontalAlignment(SwingConstants.CENTER);
		
		//text self2
		self_character_2_data.setBounds(275, 508, 200, 20);
		frame.add(self_character_2_data);
		self_character_3_data.setHorizontalAlignment(SwingConstants.CENTER);
		
		//text self3
		self_character_3_data.setBounds(500, 508, 200, 20);
		frame.add(self_character_3_data);
		
		//set initial state
		for(int i=0;i<6;i++)
		{
			String state = "Atk "+ character_data.character[picked[i]].get_attack() + 
					 	   " / Def "+ character_data.character[picked[i]].get_defence() + 
					 	   " / Hp " + character_data.character[picked[i]].get_hp(); 
			character_label_state[i].setText(state);
		}
	}
	
	
	//左中選單
	public static void stage_listener(){
		ready.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				can_attack_and_useSkill();		
				throw_Dise();
			}
		});
		
		attack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
				select_attack_construct();
			}
		});
		
		end.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				returnToOriginalState();
				phase_stage = def_stage; 
				windows_stage_select();
				
				EventClient.send("GameClient::my_ready_turn()",enemyEventClientKey);
				wait_stage();
			}
		});
		
		
		surrender.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int surrender = JOptionPane.showConfirmDialog(null, "確定投降?","",JOptionPane.YES_NO_OPTION);
				if(surrender==0) //contentPane.setVisible(false);//法二傳送至主機端(請她delete)
				{
					//GameData packet = new GameData(GameData.surrend_pack);
					System.exit(0);
				}
			}
		});
	}

	public static void windows_stage_select_construct(){
		//stage text
		stage.setFont(new Font("標楷體", Font.PLAIN, 20));
		stage.setText("");
		stage.setBounds(14, 13, 94, 24);
		frame.add(stage);
				
		//button ready
		ready.setBounds(50, 435, 100, 30);
		frame.add(ready);
		
		//button attack	
		attack.setBounds(234, 435, 100, 30);
		frame.add(attack);
		
		
		//button end
		end.setBounds(423, 435, 100, 30);
		frame.add(end);
		
		
		//button surrender
		surrender.setBounds(600, 435, 100, 30);
		frame.add(surrender);
	}
	
	//目前骰子剩於狀況
	public static void windows_dice_state_construct(){
		dice_state.setBackground(Color.LIGHT_GRAY);
		dice_state.setBounds(725, 25, 535, 124);
		frame.add(dice_state);
		dice_state.setLayout(null);
		
		//icon atk
		atk_icon.setBounds(15, 25, 70, 70);
		atk_icon.setBackground(Color.BLACK);
		dice_state.add(atk_icon);
		ImageIcon img1 = new ImageIcon("../resource/image/sword.png");
		img1.setImage(img1.getImage().getScaledInstance(70,70,Image.SCALE_DEFAULT));
		atk_icon.setIcon(img1);
		
		//icon def
		def_icon.setBounds(190, 25, 70, 70);
		dice_state.add(def_icon);
		ImageIcon img2 = new ImageIcon("../resource/image/shield.png");
		img2.setImage(img2.getImage().getScaledInstance(80,80,Image.SCALE_DEFAULT));
		def_icon.setIcon(img2);
		
		//icon spec
		spec_icon.setBounds(372, 25, 70, 70);
		dice_state.add(spec_icon);
		ImageIcon img3 = new ImageIcon("../resource/image/special.png");
		img3.setImage(img3.getImage().getScaledInstance(70,70,Image.SCALE_DEFAULT));
		spec_icon.setIcon(img3);
		
		//text atk
		atk_num.setHorizontalAlignment(SwingConstants.CENTER);
		atk_num.setFont(new Font("新細明體", Font.PLAIN, 20));
		atk_num.setBounds(107, 51, 57, 19);
		dice_state.add(atk_num);
		
		//text def
		def_num.setHorizontalAlignment(SwingConstants.CENTER);
		def_num.setFont(new Font("新細明體", Font.PLAIN, 20));
		def_num.setBounds(280, 51, 57, 19);
		dice_state.add(def_num);
		
		//text spec
		spec_num.setHorizontalAlignment(SwingConstants.CENTER);
		spec_num.setFont(new Font("新細明體", Font.PLAIN, 20));
		spec_num.setBounds(456, 51, 57, 19);
		dice_state.add(spec_num);
	}
	
	
	//
	public static void windows_characterState_construct(){
		character_state.setBackground(Color.LIGHT_GRAY);
		character_state.setBounds(725, 162, 535, 445);
		frame.add(character_state);
		character_state.setLayout(null);
		
		
		//mode7
		windows_characterStates_throwDise_construct();
		
		//mode6
		windows_characterStates_atkDise_choose_construct();
		characterStates_atkDise_choose_listener();
		
		//mode5
		windows_characterStates_defDise_choose_construct();
		characterStates_defDise_choose_listener();
		
		//mode1
		windows_characterState_skill_data_construct();
		characterState_skill_data_listener();
		
		//mode2
		windows_characterState_skill_useCharacter_construct();
		characterState_skill_listener();
		
		//mode3
		windows_characterState_attackCharacterSelect_construct();
		characterState_attackCharacterSelect_listener();
		
		//mode4
		windows_characterState_useSkillCheck_construct();
		characterState_useSkillCheck_listener();	
	}
		//select mode 1 選擇要否發動技能
		//select mode 1 角色技能資訊查看
		public static void characterState_skill_data_listener(){
			active1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					window_skillUse_character = window_character;
					skill_select = 1;
					select_lunch_construct();
				}
			});
			
			active2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					window_skillUse_character = window_character;
					skill_select = 2;
					select_lunch_construct();
				}
			});
		}

		public static void windows_characterState_skill_data_construct(){
			//skill panel1
			skill1.setBackground(Color.LIGHT_GRAY);
			skill1.setBounds(14, 13, 507, 200);
			character_state.add(skill1);
			skill1.setLayout(null);
			
			//button skill
			active1.setBounds(394, 160, 99, 27);
			skill1.add(active1);
			
			
			//text skill
			skill_describe1.setBackground(SystemColor.menu);
			skill_describe1.setFont(new Font("標楷體", Font.PLAIN, 20));
			skill_describe1.setBounds(14, 13, 479, 134);
			skill_describe1.setEditable(false);
			skill1.add(skill_describe1);
			
	
			//skill panel2
			skill2.setBackground(Color.LIGHT_GRAY);
			skill2.setLayout(null);
			skill2.setBounds(14, 226, 507, 206);
			character_state.add(skill2);
			
			//button skill
			active2.setBounds(394, 166, 99, 27);
			skill2.add(active2);
			
			
			//text skill
			skill_describe2.setFont(new Font("標楷體", Font.PLAIN, 20));
			skill_describe2.setBackground(SystemColor.menu);
			skill_describe2.setBounds(14, 13, 479, 134);
			skill_describe2.setEditable(false);
			skill2.add(skill_describe2);	
		}
		
		//select mode 2 選擇技能施放對象
		//select mode 2 選擇要發動的對象
		//發動技能所需的action(ATTACK PHASE)//仍需要發動技能後產生的效果
		public static void characterState_skill_listener(){
			launch_select.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(skill_select==1){		
						//判斷spec骰子是否足夠
						if(character_data.character[picked[window_skillUse_character]].skill1_specNeed() > Integer.valueOf(spec_num.getText())  )
						{
							JOptionPane.showMessageDialog(null, "骰子數不足需要"+character_data.character[picked[window_skillUse_character]].skill1_specNeed()+"個");
							null_construct();
						}
						else{
							String tmp = Integer.toString((Integer.valueOf(spec_num.getText()) - character_data.character[picked[window_skillUse_character]].skill1_specNeed()));
							int tmp1 = (window_character==0||window_skillUse_character==0)? ((window_character==2||window_skillUse_character==2)? 4:2):0;
							int skill_type = character_data.character_skill.character_skill_array[2*picked[window_skillUse_character]+skill_select-1].skill_action(
									character_data.character[picked[window_skillUse_character]],
									character_data.character[picked[window_character]], 
									character_data.character[picked[tmp1]]);
							
							if(skill_type==character_data.character_skill.attack_all)
								attack_all = true;
								
							spec_num.setText(tmp);
							
							/*********************傳送封包告知對方所受傷害*/
							GameData packet = new GameData(GameData.attack_pack,reverse(attacker_judge),reverse(attack_judge));
							for(int i=0;i<6;i++)
							{
								int index = reverse(i);	
								packet.character[index].set_now_attack(character_data.character[picked[i]].get_now_attack());
								packet.character[index].set_now_defence(character_data.character[picked[i]].get_now_defence());
								packet.character[index].set_hp(character_data.character[picked[i]].get_hp());
								packet.character[index].set_alive(character_alive[i]);
							}
							EventClient.send("GameClient::receive_attackpack_and_set_character_state($)",packet,enemyEventClientKey);
							/************************************************************/	
							
						}
					}
					else if(skill_select==2)
					{
						//判斷spec骰子是否足夠
						if(character_data.character[picked[window_skillUse_character]].skill2_specNeed() > Integer.valueOf(spec_num.getText())  )
						{
							JOptionPane.showMessageDialog(null, "骰子數不足需要"+character_data.character[picked[window_skillUse_character]].skill2_specNeed()+"個");
							null_construct();
						}
						else{
							String tmp = Integer.toString((Integer.valueOf(spec_num.getText()) - character_data.character[picked[window_skillUse_character]].skill2_specNeed()));
							int tmp1 = (window_character==0||window_skillUse_character==0)? ((window_character==2||window_skillUse_character==2)? 4:2):0;
							int skill_type = -1;
							
							if(character_data.character[picked[window_skillUse_character]].get_name().equals("崔斯坦")	)
							{
								int tmp2 = (window_skillUse_character==0)? 2:0;
								int tmp3 = (window_skillUse_character==0)? 4:
													(window_skillUse_character==2)?4:2;
								character_data.character_skill.character_skill_array[2*picked[window_skillUse_character]+skill_select-1].skill_action(
										character_data.character[picked[window_skillUse_character]],
										character_data.character[picked[tmp2]], 
										character_data.character[picked[tmp3]]);
								System.out.println("tmp2 = "+tmp2);
								System.out.println("tmp3 = "+tmp3);
								
								character_button_select[window_skillUse_character].setEnabled(false);
								character_alive[window_skillUse_character] = false;
								
							}
							if(character_data.character[picked[window_skillUse_character]].get_name().equals("加雷斯"))
							{
								if(window_character == window_skillUse_character)
									JOptionPane.showMessageDialog(null, "該技能不能對自己使用");
								else
								{	character_data.character_skill.character_skill_array[2*picked[window_skillUse_character]+skill_select-1].skill_action(
											character_data.character[picked[window_skillUse_character]],
											character_data.character[picked[window_character]], 
											character_data.character[picked[tmp1]]);
								
								character_button_select[window_skillUse_character].setEnabled(false);	
								character_alive[window_skillUse_character] = false;
								}	
							}
							else
							{
								skill_type = character_data.character_skill.character_skill_array[2*picked[window_skillUse_character]+skill_select-1].skill_action(
										character_data.character[picked[window_skillUse_character]],
										character_data.character[picked[window_character]], 
										character_data.character[picked[tmp1]]);
							}
								
							System.out.println(tmp1);
							
							if(skill_type==character_data.character_skill.change_attack)
								attack_test = window_skillUse_character;
							if(skill_type==character_data.character_skill.add_dise)
							{
								def_num.setText(Integer.toString((Integer.valueOf(def_num.getText()) +2)));
								atk_num.setText(Integer.toString((Integer.valueOf(def_num.getText()) +2)));
							}

							spec_num.setText(tmp);
							
							/*********************傳送封包告知對方所受傷害*/
							GameData packet = new GameData(GameData.attack_pack,reverse(attacker_judge),reverse(attack_judge));
							for(int i=0;i<6;i++)
							{
								int index = reverse(i);	
								packet.character[index].set_now_attack(character_data.character[picked[i]].get_now_attack());
								packet.character[index].set_now_defence(character_data.character[picked[i]].get_now_defence());
								packet.character[index].set_hp(character_data.character[picked[i]].get_hp());
								packet.character[index].set_alive(character_alive[i]);
							}
							EventClient.send("GameClient::receive_attackpack_and_set_character_state($)",packet,enemyEventClientKey);
							/************************************************************/	
						}
					}
					
					/*此處呼叫func 直接改變數值  receive_attackpack_and_set_character_state*/
					
					update();
					null_construct();
				}
			});
			
			cancel_select.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					null_construct();
				}
			});
		}
		
		public static void windows_characterState_skill_useCharacter_construct(){
			select_char.setBackground(Color.LIGHT_GRAY);
			select_char.setBounds(14, 13, 507, 418);
			character_state.add(select_char);
			select_char.setLayout(null);
			
			//icon
			active_character.setBounds(152, 27, 200, 295);
			active_character.setHorizontalAlignment(SwingConstants.CENTER);
			select_char.add(active_character);
			
			//button launch
			launch_select.setBounds(130, 350, 100, 45);
			launch_select.setFont(new Font("新細明體", Font.PLAIN, 15));
			select_char.add(launch_select);
			
			//button cancel
			cancel_select.setBounds(300, 350, 100, 45);
			cancel_select.setFont(new Font("新細明體", Font.PLAIN, 15));
			select_char.add(cancel_select);		
		}
		
		//select mode 3 選擇要攻擊的對象(ATTACK PHASE)
		public static void characterState_attackCharacterSelect_listener(){
			Attack_attack_select.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(Integer.valueOf(atk_num.getText())==0)
					{
						JOptionPane.showMessageDialog(null, "至少需要1個攻擊骰才可攻擊");
					}
					else
					{	
						atk_Dise_enter_construct();
					}
				}
			});
			
			Cancel_attack_select.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					null_construct();
				}
			});
		}
		
		public static void windows_characterState_attackCharacterSelect_construct(){
			attack_select.setBackground(Color.LIGHT_GRAY);
			attack_select.setBounds(14, 13, 507, 418);
			character_state.add(attack_select);
			attack_select.setLayout(null);
			
			//icon
			who_attack.setBounds(30, 40, 200, 295);
			attack_select.add(who_attack);
			
			//icon
			attack_who.setBounds(275, 40, 200, 295);
			attack_select.add(attack_who);
			
			
			//button attack	
			Attack_attack_select.setFont(new Font("新細明體", Font.PLAIN, 15));
			Attack_attack_select.setBounds(130, 350, 100, 45);
			attack_select.add(Attack_attack_select);
			
			//button cancel
			Cancel_attack_select.setFont(new Font("新細明體", Font.PLAIN, 15));
			Cancel_attack_select.setBounds(300, 350, 100, 45);
			attack_select.add(Cancel_attack_select);		
		}
		
		//select mode 4  判斷是否要發動技能(受攻擊時)
		//select mode 4 被攻擊時決定要否發動技能
		public static void characterState_useSkillCheck_listener(){
			skill_use_yes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for(int i=0;i<=4;i=i+2)
					{
						//如果全都是攻擊階段的技能
						if(character_data.character[picked[i]].skill1_use_stage()==Character_Data.def||
						   character_data.character[picked[i]].skill2_use_stage()==Character_Data.def)
						{
							null_construct();
							break;
						}
						if(i==4)
						{
							JOptionPane.showMessageDialog(null, "無可發動的技能");
						}
					}
				}
			});
			
			skill_use_no.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					end_using_skill = true;
					def_Dise_enter_construct();
				}
			});
		}

		public static void windows_characterState_useSkillCheck_construct(){
			skill_use.setBackground(Color.LIGHT_GRAY);
			skill_use.setBounds(14, 13, 507, 418);
			character_state.add(skill_use);
			skill_use.setLayout(null);
			
			//label icon
			who_attack_skill_use.setBounds(15, 30, 200, 295);
			skill_use.add(who_attack_skill_use);
			
			//label icon
			attack_who_skill_use.setBounds(300, 30, 200, 295);
			skill_use.add(attack_who_skill_use);
			
			//button yes
			skill_use_yes.setBounds(195, 350, 100, 45);
			skill_use.add(skill_use_yes);
			skill_use_yes.setFont(new Font("新細明體", Font.PLAIN, 15));
			
			//button no
			skill_use_no.setBounds(315, 350, 100, 45);
			skill_use.add(skill_use_no);
			skill_use_no.setFont(new Font("新細明體", Font.PLAIN, 15));
			
			//text
			UseSkill_text.setBounds(100, 362, 65, 19);
			skill_use.add(UseSkill_text);
			
			//text
			Attack_text.setBounds(229, 168, 38, 19);
			skill_use.add(Attack_text);
		}
		
		//select mode 5 決定使用於防禦的骰子量
		//select mode 5 取消發動技能後決定使用的骰子量
		public static void characterStates_defDise_choose_listener(){
			def_dise_choose_OK.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try{
						int use_dise = Integer.parseInt(def_dise_enter.getText());
						//System.out.println(use_dise);
						if(use_dise> Integer.parseInt(def_num.getText()) || use_dise<0)
						{
							JOptionPane.showMessageDialog(null, "骰子數不足");
							def_dise_enter.setText("");
						}
						else
						{
							def_num.setText(Integer.toString((Integer.valueOf(def_num.getText()) - use_dise)));
							def_dise_enter.setText("");
							
							//***********************************************************
							
							int damage = character_data.character[picked[attacker_test]].get_now_attack() - 
									(character_data.character[picked[attack_test]].get_now_defence() + use_dise*10);						
							if(damage>0)
							{
								character_data.character[picked[attack_test]].set_hp(character_data.character[picked[attack_test]].get_hp() - damage);
								if(character_data.character[picked[attack_test]].get_hp()<0)
								{
									update();
								}
							}				
							
							returnToOriginalState();
							null_construct();
							wait_stage();
							
							//呼叫對方的 可以再次攻擊 can_attack_and_useSkill()
							EventClient.send("GameClient::can_attack_and_useSkill()",enemyEventClientKey);
							
							
							/*********************傳送封包告知對方所受傷害*/
							GameData packet = new GameData(GameData.defense_pack);
							for(int i=0;i<6;i++)
							{
								int index = reverse(i);	
								packet.character[index].set_now_attack(character_data.character[picked[i]].get_now_attack());
								packet.character[index].set_now_defence(character_data.character[picked[i]].get_now_defence());
								packet.character[index].set_hp(character_data.character[picked[i]].get_hp());
								packet.character[index].set_alive(character_alive[i]);
							}
							EventClient.send("GameClient::receive_attackpack_and_set_character_state($)",packet,enemyEventClientKey);
							
							/************************************************************/
							
						}	
					}catch(Exception ex)
					{
						JOptionPane.showMessageDialog(null, "輸入錯誤");
						def_dise_enter.setText("");
					}
				}
			});
		}

		public static void windows_characterStates_defDise_choose_construct(){
			def_dise_choose.setBounds(14, 13, 507, 419);
			character_state.add(def_dise_choose);
			def_dise_choose.setLayout(null);
			
			//enter def number
			def_dise_enter.setBounds(195, 241, 116, 25);
			def_dise_choose.add(def_dise_enter);
			def_dise_enter.setColumns(10);
			
			//text
			def_choose_text.setBackground(SystemColor.menu);
			def_choose_text.setFont(new Font("標楷體", Font.PLAIN, 30));
			def_choose_text.setText("請輸入要使用的防禦骰數量(每個防禦骰+10防禦");
			def_choose_text.setBounds(74, 78, 388, 105);
			def_dise_choose.add(def_choose_text);
			
			//ok button
			def_dise_choose_OK.setBounds(195, 328, 116, 27);
			def_dise_choose.add(def_dise_choose_OK);
		}
				
		//select mode 6 攻擊後決定使用的骰子量
		public static void characterStates_atkDise_choose_listener(){
			atk_dise_choose_OK.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try{
						int use_dise = Integer.parseInt(atk_dise_enter.getText());
						if(use_dise> Integer.parseInt(atk_num.getText()) || use_dise<0)
						{
							JOptionPane.showMessageDialog(null, "骰子數不足");
							atk_dise_enter.setText("");
						}
						else if(use_dise==0)
						{
							JOptionPane.showMessageDialog(null, "至少需輸入1以上");
							atk_dise_enter.setText("");
						}
						else
						{
							atk_num.setText(Integer.toString((Integer.valueOf(atk_num.getText()) - use_dise)));
							atk_dise_enter.setText("");
							
								
							//***********************************************************
							int atk = character_data.character[picked[attacker_judge]].get_now_attack()+use_dise*10;
							character_data.character[picked[attacker_judge]].set_now_attack(atk);
							System.out.println(character_data.character[picked[attacker_judge]].get_name() +"發動攻擊");
							System.out.println("攻擊："+ character_data.character[picked[attacker_judge]].get_now_attack());
							//***********************************************************
							
							
							/*此處呼叫func 直接改變數值 (GameData packet) receive_attackpack_and_set_character_state*/
							/*********************傳送封包告知對方所受傷害*/
							GameData packet = new GameData(GameData.attack_pack,reverse(attacker_judge),reverse(attack_judge));
							for(int i=0;i<6;i++)
							{
								int index = reverse(i);	
								packet.character[index].set_now_attack(character_data.character[picked[i]].get_now_attack());
								packet.character[index].set_now_defence(character_data.character[picked[i]].get_now_defence());
								packet.character[index].set_hp(character_data.character[picked[i]].get_hp());
								packet.character[index].set_alive(character_alive[i]);	
							}
							EventClient.send("GameClient::receive_attackpack_and_set_character_state($)",packet,enemyEventClientKey);
							update();
							
							/************************************************************/	
							
							
							if(attack_all)
							{
								if(character_alive[3])
								{	
									packet.set_attack(1);
									EventClient.send("GameClient::check_use_skill_construct($)",packet,enemyEventClientKey);
									/*呼叫敵方的防禦CONSTRUCT  check_use_skill_construct*/
								}
								else{
									can_attack_and_useSkill();
								}
							}
							else
							{
								/*呼叫敵方的防禦CONSTRUCT  check_use_skill_construct*/
								EventClient.send("GameClient::check_use_skill_construct($)",packet,enemyEventClientKey);
								returnToOriginalState();
							}
							null_construct();
							wait_stage();
						}	
					}catch(Exception ex)
					{
						JOptionPane.showMessageDialog(null, "輸入錯誤");
						def_dise_enter.setText("");
						ex.printStackTrace();
					}
				}
			});
			atk_dise_choose_cancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					null_construct();
				}
			});
		}

		public static void windows_characterStates_atkDise_choose_construct()	{
			attack_dise_choose.setBounds(14, 13, 507, 419);
			character_state.add(attack_dise_choose);
			attack_dise_choose.setLayout(null);
			//enter atk number
			atk_dise_enter.setColumns(10);
			atk_dise_enter.setBounds(195, 241, 116, 25);
			attack_dise_choose.add(atk_dise_enter);
			//text
			atk_choose_text.setText("請輸入要使用的攻擊骰數量(每個攻擊骰+10攻擊");
			atk_choose_text.setFont(new Font("標楷體", Font.PLAIN, 30));
			atk_choose_text.setBackground(SystemColor.menu);
			atk_choose_text.setBounds(74, 78, 388, 105);
			attack_dise_choose.add(atk_choose_text);
			//ok button
			atk_dise_choose_OK.setBounds(115, 328, 116, 27);	
			attack_dise_choose.add(atk_dise_choose_OK);		
			//cancel button
			atk_dise_choose_cancel.setBounds(293, 328, 116, 27);
			attack_dise_choose.add(atk_dise_choose_cancel);
		}

		
		//select mode 7 投骰子
		public static void windows_characterStates_throwDise_construct(){
			thorw_dise.setBounds(14, 13, 507, 419);
			character_state.add(thorw_dise);
			thorw_dise.setLayout(null);	
			//dise1
			dise_0.setBounds(84, 63, 120, 120);
			thorw_dise.add(dise_0);
			//dise2
			dise_1.setBounds(307, 63, 120, 120);
			thorw_dise.add(dise_1);
			//dise3
			dise_2.setBounds(84, 247, 120, 120);
			thorw_dise.add(dise_2);
			//dise4
			dise_3.setBounds(307, 247, 120, 120);
			thorw_dise.add(dise_3);	
			//text
			text_this_stage.setBounds(25, 13, 146, 19);
			thorw_dise.add(text_this_stage);
		}

		
		public static void my_ready_turn(){
			character_state_mode = 7;
			phase_stage = atk_stage; 
			windows_stage_select();
			null_construct();
		}
		
		public static void can_attack_and_useSkill(){
			if(attack_all)
			{
				System.out.println("attack_all_count" = attack_all_count);
				
				if(attack_all_count==0)
				{
					if(character_alive[3])
					{
						GameData packet = new GameData(GameData.defense_pack,reverse(attacker_judge),reverse(3));	
						for(int i=0;i<6;i++) //已反轉 之後收到直接使用就好
						{
							int index = reverse(i);	
							packet.character[index].set_now_attack(character_data.character[picked[i]].get_now_attack());
							packet.character[index].set_now_defence(character_data.character[picked[i]].get_now_defence());
							packet.character[index].set_hp(character_data.character[picked[i]].get_hp());
							packet.character[index].set_alive(character_alive[i]);
						}
						EventClient.send("GameClient::check_use_skill_construct($)",packet,enemyEventClientKey);
						/*呼叫敵方的防禦CONSTRUCT  check_use_skill_construct*/
						attack_all_count++;
					}
					else{
						attack_all_count++;
						can_attack_and_useSkill();
					}
				}
				if(attack_all_count==1)
				{
					if(character_alive[5])
					{
						
						GameData packet = new GameData(GameData.defense_pack,reverse(attacker_judge),reverse(5));	
						for(int i=0;i<6;i++) //已反轉 之後收到直接使用就好
						{
							int index = reverse(i);	
							packet.character[index].set_now_attack(character_data.character[picked[i]].get_now_attack());
							packet.character[index].set_now_defence(character_data.character[picked[i]].get_now_defence());
							packet.character[index].set_hp(character_data.character[picked[i]].get_hp());
							packet.character[index].set_alive(character_alive[i]);
						}
						/*呼叫敵方的防禦CONSTRUCT  check_use_skill_construct*/
						EventClient.send("GameClient::check_use_skill_construct($)",packet,enemyEventClientKey);
					}
					attack_all_count=0;
					attack_all = false;
					returnToOriginalState();
				}
			}
			else
			{
				ready.setEnabled(false);
				attack.setEnabled(true);
				end.setEnabled(true);
			}		
		}
		
	
		public static int reverse(int i){
			int index = 0;
			switch(i){
				case 0:
					index = 1;
					break;
				case 1:
					index = 0;
					break;
				case 2:
					index = 3;
					break;
				case 3:
					index = 2;
					break;
				case 4:
					index = 5;
					break;
				case 5:
					index = 4;
					break;
			}
			return index;
		}
			
		
		public static void check_HP(){
			for(int i=0;i<6;i++)
			{
				if(character_data.character[picked[i]].get_hp() <=0)
				{
					character_alive[i] = false;
				}
			}
		}
		
		public static void setNowState(){
			for(int i=0;i<6;i++)
			{		
				String state = "Atk "+ character_data.character[picked[i]].get_now_attack() + 
					 	   " / Def "+ character_data.character[picked[i]].get_now_defence() + 
					 	   " / Hp " + character_data.character[picked[i]].get_hp();
				character_label_state[i].setText(state);
				character_button_select[i].setEnabled(character_alive[i]);
			}
		}
		
		public static void update(){
			check_HP();
			setNowState();
		}

		public static void returnToOriginalState(){
			for(int i=0;i<6;i++)
			{
				character_data.character[picked[i]].reset_now_attack();
				character_data.character[picked[i]].reset_now_defence();				
				String state = "Atk "+ character_data.character[picked[i]].get_now_attack() + 
					 	   " / Def "+ character_data.character[picked[i]].get_now_defence() + 
					 	   " / Hp " + character_data.character[picked[i]].get_hp();
				character_label_state[i].setText(state);
				character_button_select[i].setEnabled(character_alive[i]);
			}
		}
		
		public static void wait_stage(){
			ready.setEnabled(false);
			attack.setEnabled(false);	
			end.setEnabled(false);
		}		

		// Show the message in the text field of chat room
		public static void recvMsg(String msg){
			chatContentDisplay.append(opponentID + ":\n" + "    " + msg);
		}

		// Chat Room GUI initialize
		public static void chatRoom(){
			chat = new JFrame("ChatRoom");
			chat.getContentPane().setBackground(Color.WHITE);
			chat.setBounds(1356, 0, 550, 600);
			chat.getContentPane().setLayout(null);

			// local Photo
			ImageIcon displayImage ;
			if(webcam_check == 1)
				displayImage = new ImageIcon("./takedPhoto.png");
			else
				displayImage = new ImageIcon(filepath[photoIndex-1]);
			displayImage.setImage(displayImage.getImage().getScaledInstance(185,186,Image.SCALE_DEFAULT));
			Icon display = displayImage;
			
			// local info
			JLabel myPhoto = new JLabel(display);
			myPhoto.setBounds(355, 301, 185, 186);
			chat.getContentPane().add(myPhoto);
			
			JLabel myGender = new JLabel(PlayerGender_String);
			myGender.setFont(new Font("DejaVu Sans Condensed", Font.BOLD, 18));
			myGender.setBounds(355, 463, 175, 70);
			chat.getContentPane().add(myGender);

			JLabel playerID_Label = new JLabel("You: " + PlayerID_String);
			playerID_Label.setFont(new Font("DejaVu Sans", Font.BOLD, 18));
			playerID_Label.setBounds(355, 500, 270, 37);
			chat.getContentPane().add(playerID_Label);

			// opponent info
			opponentPhoto.setImage(opponentPhoto.getImage().getScaledInstance(185,186,Image.SCALE_DEFAULT));
			Icon enemyIcon = opponentPhoto;
			JLabel enemyPhoto = new JLabel(enemyIcon);
			enemyPhoto.setBounds(355, 34, 185, 186);
			chat.getContentPane().add(enemyPhoto);
			
			JLabel enemyGender = new JLabel(opponentGender);
			enemyGender.setFont(new Font("DejaVu Sans Condensed", Font.BOLD, 18));
			enemyGender.setBounds(355, 199, 185, 77);
			chat.getContentPane().add(enemyGender);

			JLabel opponentID_Label = new JLabel("Eneny: " + opponentID);
			opponentID_Label.setFont(new Font("DejaVu Sans", Font.BOLD, 18));
			opponentID_Label.setBounds(355, 242, 270, 37);
			chat.getContentPane().add(opponentID_Label);
			
	
			// chat room title
			JLabel chatRoom = new JLabel("Chat Content");
			chatRoom.setFont(new Font("DejaVu Sans Light", Font.BOLD | Font.ITALIC, 20));
			chatRoom.setBounds(113, -18, 175, 70);
			chat.getContentPane().add(chatRoom);
			
			// text enter field
			JTextField inputField = new JTextField();
			inputField.setFont(new Font("DejaVu Sans Condensed", Font.PLAIN, 16));
			inputField.setBounds(12, 543, 307, 37);
			chat.getContentPane().add(inputField);
			inputField.setColumns(30);
			
			// chat message display area
			chatContentDisplay = new JTextArea("",339,418);
			chatContentDisplay.setFont(new Font("DejaVu Sans", Font.PLAIN, 16));
			JScrollPane scrollablePane = new JScrollPane(chatContentDisplay);
			scrollablePane.setBounds(12, 34, 329, 490);
			chatContentDisplay.setEditable(false);
			chatContentDisplay.setLineWrap(true);
			chatContentDisplay.setRows(10); //
			scrollablePane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollablePane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			chat.getContentPane().add(scrollablePane);
			
			// button which confirm to send msg 
			JButton enterButton = new JButton("Enter");
			enterButton.setFont(new Font("Dialog", Font.BOLD, 14));
			enterButton.setBounds(340, 544, 124, 36);
			chat.getContentPane().add(enterButton);
			
			enterButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(!inputField.getText().equals(""))
					{	
						String text = inputField.getText() + "\n";
						chatContentDisplay.append(PlayerID_String + ":\n" + "    " + text);
						inputField.setText("");
						EventClient.send("GameClient::recvMsg($)",text,enemyEventClientKey);
					}
				}
			});
			
			// KeyListener for 'Enter' key to send msg
			inputField.addKeyListener(new KeyListener(){
				public void keyPressed(KeyEvent event){
					if(event.getKeyCode() == KeyEvent.VK_ENTER)
					{
						if(!inputField.getText().equals(""))
						{	
							String text = inputField.getText() + "\n";
							chatContentDisplay.append(PlayerID_String + ":\n" + "    " + text);
							inputField.setText("");
							EventClient.send("GameClient::recvMsg($)",text,enemyEventClientKey);
						}
					}
				}
				public void keyTyped(KeyEvent event){
					;
				}
				public void keyReleased(KeyEvent event){
					;
				}
			});

			chat.setVisible(true);
			chat.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		}
		
}


