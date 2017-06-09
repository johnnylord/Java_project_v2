package packageData;

import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;

//user setting 

public class UserData{ 

		// User Setting 
	public ImageIcon photo;
	public String playerID;
	public String playerGender;

	public UserData(ImageIcon photo, String playerID, String playerGender){
		this.photo = photo;
		this.playerID = playerID;
		this.playerGender = playerGender;
	}
}
