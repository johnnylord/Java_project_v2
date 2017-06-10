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
			    Graphics2D g2d = (Graphics2D) bi.createGraphics();
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
