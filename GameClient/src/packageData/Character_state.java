package packageData;
import java.awt.*;
import java.awt.image.*;
import java.io.*;

public class Character_state implements Serializable{
	
	private int now_atk;
	private int now_def;
	private int hp;
	private boolean alive;
	
	
	public int get_now_attack(){return this.now_atk;}
	public int get_now_defence(){return this.now_def;}
	public int get_hp(){return this.hp;}
	public boolean get_alive(){return this.alive;}
	
	public void set_now_attack(int atk){this.now_atk = atk;}
	public void set_now_defence(int def){this.now_def = def;}
	public void set_hp(int hp){ this.hp = hp;}
	public void set_alive(boolean alive){this.alive = alive;}
}
