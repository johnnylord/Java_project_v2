package packageData;

import java.awt.*;
import java.awt.image.*;


public class PacketData{
 
	private UserData userData;	// User Setting
	private SelectData selectData; //  Hero selecting
	private GameData gameData; // to do... data when game start;
	private String msg; // Chat room msg

	public PacketData(UserData userData,SelectData selectData,GameData gameData, String msg){
		this.userData = userData;
		this.selectData = selectData;
		this.gameData = gameData;
		this.msg = msg;
		System.out.println("Hello");
	}

	public UserData getUserData(){
		return userData;
	}

	public SelectData getSelectData(){
		return selectData;
	}

	public GameData getGameData(){
		return gameData;
	}

	public String getMsg(){
		return msg;
	}
};
