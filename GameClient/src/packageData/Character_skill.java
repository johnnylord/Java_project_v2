import java.util.Random;

public class Character_skill {

	interface Skill{
		int skill_action(Character_Data chara1,Character_Data chara2,Character_Data chara3);
	}
	
	//發動者 對像 第三名
	public Skill[] character_skill_array = new Skill[]{
			//亞瑟王
			new Skill(){public int skill_action(Character_Data chara1,Character_Data chara2,Character_Data chara3){ 
				return ch1_skill1(chara1); }},
			new Skill(){public int skill_action(Character_Data chara1,Character_Data chara2,Character_Data chara3){ 
				return 0;	} },
			//高文
			new Skill(){public int skill_action(Character_Data chara1,Character_Data chara2,Character_Data chara3){ 
				return ch2_skill1(chara1); }},
			new Skill(){public int skill_action(Character_Data chara1,Character_Data chara2,Character_Data chara3){ 
				return 0;	} },
			//莫德雷得
			new Skill(){public int skill_action(Character_Data chara1,Character_Data chara2,Character_Data chara3){ 
				return ch3_skill1(chara1); }},
			new Skill(){public int skill_action(Character_Data chara1,Character_Data chara2,Character_Data chara3){ 
				return 0;	} },
			//蘭斯洛特
			new Skill(){public int skill_action(Character_Data chara1,Character_Data chara2,Character_Data chara3){ 
				return ch4_skill1(chara1); }},
			new Skill(){public int skill_action(Character_Data chara1,Character_Data chara2,Character_Data chara3){ 
				return 0;	} },
			//"加雷斯",
			new Skill(){public int skill_action(Character_Data chara1,Character_Data chara2,Character_Data chara3){ 
				return ch5_skill1(chara1); }},
			new Skill(){public int skill_action(Character_Data chara1,Character_Data chara2,Character_Data chara3){ 
				return ch5_skill2(chara1, chara2);	} },
			//"貝迪維爾",
			new Skill(){public int skill_action(Character_Data chara1,Character_Data chara2,Character_Data chara3){ 
				return ch6_skill1( chara1, chara2, chara3); }},
			new Skill(){public int skill_action(Character_Data chara1,Character_Data chara2,Character_Data chara3){ 
				return ch6_skill2( chara1);	} },
			//"崔斯坦",
			new Skill(){public int skill_action(Character_Data chara1,Character_Data chara2,Character_Data chara3){ 
				return ch7_skill1( chara1); }},
			new Skill(){public int skill_action(Character_Data chara1,Character_Data chara2,Character_Data chara3){ 
				return ch7_skill2( chara1, chara2, chara3);	} },
			//"摩根勒菲",
			new Skill(){public int skill_action(Character_Data chara1,Character_Data chara2,Character_Data chara3){ 
				return ch8_skill1( chara1, chara2); }},
			new Skill(){public int skill_action(Character_Data chara1,Character_Data chara2,Character_Data chara3){ 
				return ch8_skill2( chara1, chara2);	} },
			//"加拉哈德",
			new Skill(){public int skill_action(Character_Data chara1,Character_Data chara2,Character_Data chara3){ 
				return ch9_skill1( chara1); }},
			new Skill(){public int skill_action(Character_Data chara1,Character_Data chara2,Character_Data chara3){ 
				return ch9_skill2( chara1);	} },
			//"珀西瓦",
			new Skill(){public int skill_action(Character_Data chara1,Character_Data chara2,Character_Data chara3){ 
				return ch10_skill1( chara1, chara2); }},
			new Skill(){public int skill_action(Character_Data chara1,Character_Data chara2,Character_Data chara3){ 
				return ch10_skill2( chara1);	} },
			//"梅林",
			new Skill(){public int skill_action(Character_Data chara1,Character_Data chara2,Character_Data chara3){ 
				return ch11_skill1( chara1, chara2); }},
			new Skill(){public int skill_action(Character_Data chara1,Character_Data chara2,Character_Data chara3){ 
				return ch11_skill2( chara1, chara2);	} },
			//"閨妮維雅"
			new Skill(){public int skill_action(Character_Data chara1,Character_Data chara2,Character_Data chara3){ 
				return ch12_skill1( chara1, chara2); }},
			new Skill(){public int skill_action(Character_Data chara1,Character_Data chara2,Character_Data chara3){ 
				return ch12_skill2( chara1, chara2);	} }
	};
			

	public static int no_skill = 0;
	public static int general_skill = 1;
	public static int attack_all = 2;
	public static int change_attack = 3;
	public static int add_dise = 4;
	public static int suicide = 5;
	

	
	public int ch1_skill1(Character_Data chara1)//"誓約勝利之劍（攻擊階段\n\n\t特殊3\t該回合對全體攻擊一次"
	{
		//特殊MODE
		System.out.println("誓約勝利之劍");
		int mode = attack_all;
		return mode;
	}
	public int ch1_skill2()//
	{
		System.out.println("no_skill");
		int mode = no_skill;
		return mode;
	}
	public int ch2_skill1(Character_Data chara1)//"聖者的數字（攻擊階段\n\n\t特殊2\t增加自身攻擊20，30%機率再加15",
	{
		System.out.println("聖者的數字");
		int mode = general_skill;
		Random rand = new Random();
		if(rand.nextInt(10)<3)
		{
			chara1.set_now_attack(chara1.get_now_attack()+35);
		}
		else
		{
			chara1.set_now_attack(chara1.get_now_attack()+20);
		}
		return mode;
	}
	public int ch2_skill2()//
	{
		System.out.println("no_skill");
		int mode = 0;
		return mode;
	}
	public int ch3_skill1(Character_Data chara1)//"不忠的信仰（攻擊階段\n\n\t特殊3\t降低自身15血量\n\t\t該回合增加40攻擊",
	{
		System.out.println("不忠的信仰");
		int mode = general_skill;
		chara1.set_now_attack(chara1.get_now_attack()+40);
		chara1.set_hp(chara1.get_hp() - 15);
		return mode;
	}
	public int ch3_skill2()//
	{
		System.out.println("no_skill");
		int mode = no_skill;
		return mode;
	}
	public int ch4_skill1(Character_Data chara1)//"湖之騎士（攻擊階段\n\n\t特殊2\t增加自身攻擊25",
	{
		System.out.println("湖之騎士");
		int mode = general_skill;
		chara1.set_now_attack(chara1.get_now_attack()+25);
		return mode;
	}
	public int ch4_skill2()//
	{
		System.out.println("no_skill");
		int mode = no_skill;
		return mode;
	}
	public int ch5_skill1(Character_Data chara1)//"隱蔽（攻擊階段\n\n\t特殊2\t自身血量減少10點，攻擊力增加30",
	{
		System.out.println("隱蔽");
		int mode = general_skill;
		chara1.set_now_attack(chara1.get_now_attack()+30);
		chara1.set_hp(chara1.get_hp() - 10);
		return mode;
	}
	public int ch5_skill2(Character_Data chara1,Character_Data chara2)//"捨己為人（攻擊、防禦階段\n\n\t特殊3\t自身血量歸零\n\t\t回覆一名隊友  30血",
	{
		System.out.println("捨己為人");
		int mode = suicide;
		if(chara2.get_hp()>0);
		{
			chara2.set_hp(chara2.get_hp() + 30);
		}
		chara1.set_hp(0);
		return mode;
	}
	public int ch6_skill1(Character_Data chara1,Character_Data chara2,Character_Data chara3)//守護誓約（防禦階段\n\n\t特殊2\t增加全體防禦10",
	{
		System.out.println("守護誓約");
		int mode = general_skill;
		chara1.set_now_defence(chara1.get_now_defence() +10 );
		chara2.set_now_defence(chara2.get_now_defence() +10 );
		chara3.set_now_defence(chara3.get_now_defence() +10 );
		return mode;
	}
	public int ch6_skill2(Character_Data chara1)//"銀色之腕（攻擊階段\n\n\t特殊2\t增加自身攻擊20",
	{
		System.out.println("銀色之腕");
		int mode = general_skill;
		chara1.set_now_attack(chara1.get_now_attack()+20);
		return mode;
	}
	public int ch7_skill1(Character_Data chara1)//"傳承的祝福（攻擊階段\n\n\t特殊1\t增加自身攻擊10",
	{
		System.out.println("傳承的祝福");
		int mode = general_skill;
		chara1.set_now_defence(chara1.get_now_attack() +10 );
		return mode;
	}
	public int ch7_skill2(Character_Data chara1,Character_Data chara2,Character_Data chara3)//"悲傷的結局（攻擊階段\n\n\t特殊3\t自身血量歸零\n\t\t我方全體回復15血",
	{
		System.out.println("悲傷的結局");
		int mode = suicide;
		
		if(chara2.get_hp()>0);
		{
			chara2.set_hp(chara2.get_hp() + 15);
		}
		if(chara3.get_hp()>0);
		{
			chara3.set_hp(chara3.get_hp() + 15);
		}
		chara1.set_hp(0);
		return mode;
	}
	public int ch8_skill1(Character_Data chara1,Character_Data chara2)//"治癒之聲（防禦、攻擊階段\n\n\t特殊2\t回復單一隊友10血量",
	{
		System.out.println("治癒之聲");
		int mode = general_skill;
		chara2.set_hp(chara2.get_hp() + 10);
		return mode;
	}
	public int ch8_skill2(Character_Data chara1,Character_Data chara2)//"變化（攻擊階段\n\n\t特殊3\t一回合內將自身攻擊力數值\n\t\t變為己方其中一人",
	{
		System.out.println("變化");
		int mode = general_skill;
		chara1.set_now_attack(chara2.get_attack());
		return mode;
	}
	public int ch9_skill1(Character_Data chara1)//"聖杯祝福（防禦階段\n\n\t特殊1\t自身該回合增加10防禦",
	{
		System.out.println("聖杯祝福");
		int mode = general_skill;
		chara1.set_now_defence(chara1.get_now_defence()+10);
		return mode;
	}
	public int ch9_skill2(Character_Data chara1)//"忠貞騎士（防禦階段\n\n\t特殊3\t防禦增加5，將敵方攻擊轉移至自身",
	{
		System.out.println("忠貞騎士");
		int mode = change_attack;
		return mode;
	}
	public int ch10_skill1(Character_Data chara1,Character_Data chara2)//"堅毅精神（防禦階段\n\n\t特殊2\t提升己方一人防禦15",
	{
		System.out.println("堅毅精神");
		int mode = general_skill;
		chara2.set_now_defence(chara2.get_now_defence()+15);
		return mode;
	}
	public int ch10_skill2(Character_Data chara1)//"聖杯守護者（攻擊、防禦階段\n\n\t特殊3\t獲得攻擊/防禦骰  各2個",
	{
		System.out.println("聖杯守護者");
		int mode = add_dise;
		return mode;
	}
	public int ch11_skill1(Character_Data chara1,Character_Data chara2)//"永遠的阿瓦隆（攻擊、防禦階段\n\n\t特殊3   指定一人回復15血",
	{
		System.out.println("永遠的阿瓦隆");
		int mode = general_skill;
		chara2.set_hp(chara2.get_hp()+15);
		return mode;
	}
	public int ch11_skill2(Character_Data chara1,Character_Data chara2)//"夢魔之子（攻擊階段\n\n\t特殊2\t增加一人攻擊20",
	{
		System.out.println("夢魔之子");
		int mode = general_skill;
		chara2.set_now_attack(chara2.get_now_attack()+20);
		return mode;
	}
	public int ch12_skill1(Character_Data chara1,Character_Data chara2)//"受詛咒的愛情（防禦階段\n	\n\t特殊2\t指定一人防禦增加30\n\t\t自身血量減10",
	{
		System.out.println("受詛咒的愛情");
		int mode = general_skill;
		chara2.set_now_defence(chara2.get_now_defence()+30);
		chara1.set_hp(chara1.get_hp()-10);
		return mode;
	}
	public int ch12_skill2(Character_Data chara1,Character_Data chara2)//"亡國之妻（攻擊階段\n\n\t特殊3\t指定一人攻擊增加40\n\t\t指定的人血量減20",
	{
		System.out.println("亡國之妻");
		int mode = general_skill;
		chara2.set_now_attack(chara2.get_now_attack()+40);
		chara2.set_hp(chara2.get_hp()-20);
		return mode;
	}
	
	
}
