
public class Character_Data {
	
	private String name;
	private int attack;
	public int now_atk;
	private int defence;
	public int now_def;
	public int hp;
	//public int now_hp;
	
	private int skill_num;
	private String[] skill = new String[2];
	private int[] skill_specNeed = new int[2];
	private int[] skill_use_stage = new int[2];
	private int[] skill_use_char = new int[2];
	

	public static int no_skill = 0;
	
	public static int Atk = 1;
	public static int def = 2;
	public static int both = 3;
	
	public static int self = 1;
	public static int teammate_1 = 2;
	public static int teammate_all = 3;
	
	public Character_Data(String name,int attack,int defence,int hp,int skill_num,
			String skill1,String skill2,
			int skill1_use_stage,int skill2_use_stage,
			int skill1_use_char,int skill2_use_char,
			int skill1_specNeed,int skill2_specNeed)
	{
		this.name = name;
		this.attack = attack;
		this.now_atk = attack;
		this.defence = defence;
		this.now_def = defence;
		this.hp = hp;
		//this.now_hp = hp;
		
		this.skill_num = skill_num;
		this.skill[0] = skill1;
		this.skill[1] = skill2;
		
		this.skill_use_stage[0] = skill1_use_stage;
		this.skill_use_stage[1] = skill2_use_stage;
		this.skill_use_char[0] = skill1_use_char;
		this.skill_use_char[1] = skill2_use_char;
		this.skill_specNeed[0] = skill1_specNeed;
		this.skill_specNeed[1] = skill2_specNeed;
	}
	
	public String get_name(){return this.name;}
	public int get_attack(){return this.attack;}
	public int get_defence(){return this.defence;}
	
	public int get_hp(){return this.hp;}
	public void set_hp(int hp){ this.hp = hp;}
	
	
	public int get_now_attack(){return this.now_atk;}
	public int get_now_defence(){return this.now_def;}
	public void set_now_attack(int atk){this.now_atk = atk;}
	public void set_now_defence(int def){this.now_def = def;}
	public void reset_now_attack(){this.now_atk = this.attack;}
	public void reset_now_defence(){this.now_def = this.defence;}
	
	
	public int get_skill_num(){return this.skill_num;}
	public String get_skill1(){return this.skill[0];}
	public String get_skill2(){return this.skill[1];}

	public int skill1_use_stage(){return this.skill_use_stage[0];}
	public int skill2_use_stage(){return this.skill_use_stage[1];}
	public int skill1_use_char(){return this.skill_use_char[0];}
	public int skill2_use_char(){return this.skill_use_char[1];}
	public int skill1_specNeed(){return this.skill_specNeed[0];}
	public int skill2_specNeed(){return this.skill_specNeed[1];}
	

}
