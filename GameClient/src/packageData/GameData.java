package packageData;
import java.awt.*;
import java.awt.image.*;
import java.io.*;

//GameData 

public class GameData implements Serializable{ 

	// Data
	public static int attack_pack = 0;
	public static int defense_pack = 1;
	public static int endPhase_pack = 2;
	public static int surrend_pack = 3;
	
	private int packet_type;
	private int attacker;
	private int attacked;
	public Character_state[] character = new Character_state[6];

	// Constructor
	public GameData(int packet_type,int attacker,int attacked){
		this.packet_type = packet_type;
		this.attacker = attacker;
		this.attacked = attacked;
		for(int i=0;i<6;i++)
		{
			character[i] = new Character_state();
		}
		
	}
	
	public GameData(int packet_type){	
		this.packet_type = packet_type;	
		for(int i=0;i<6;i++)
		{
			character[i] = new Character_state();
		}
	}
	
	public void set_attack(int attacked)
	{
		this.attacked = attacked;
	}
	
	public int get_packet_type(){	return this.packet_type;	}
	public int get_attacker(){	return this.attacker;	}
	public int get_attacked(){	return this.attacked;	}
	public Character_state get_character_state(int index){	return this.character[index];	}

};
