package packageData;

import java.awt.*;
import java.awt.image.*;

//user setting 

public class UserData{ 

		// User Setting 
	public BufferedImage photo;
	public String playerID;
	public String playerGender;
	public String playerMotto;

	public UserData(BufferedImage photo, String playerID, String playerGender, String playerMotto){
		this.photo = photo;
		this.playerID = playerID;
		this.playerGender = playerGender;
		this.playerMotto = playerMotto;
	}
}
