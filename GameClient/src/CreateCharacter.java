
public class CreateCharacter {
	
	public Character_Data[] character = new Character_Data[12];
	public Character_skill character_skill = new Character_skill();
	
	public CreateCharacter()
	{
		Character_Data char1 = new Character_Data("亞瑟王",40,35,30,1,
				"誓約勝利之劍（攻擊階段\n\n\t特殊3\t該回合對全體攻擊一次",
				"",
				Character_Data.Atk,Character_Data.no_skill,
				Character_Data.self,Character_Data.no_skill,
				3,0);
		
		Character_Data char2 = new Character_Data("高文",40,30,35,1,
				"聖者的數字（攻擊階段\n\n\t特殊2\t增加自身攻擊20，30%機率再加15",
				"",
				Character_Data.Atk,Character_Data.no_skill,
				Character_Data.self,Character_Data.no_skill,
				2,0);
		
		Character_Data char3 = new Character_Data("莫德雷德",50,25,30,1,
				"不忠的信仰（攻擊階段\n\n\t特殊3\t降低自身15血量\n\t\t該回合增加40攻擊",
				"",
				Character_Data.Atk,Character_Data.no_skill,
				Character_Data.self,Character_Data.no_skill,
				3,0);
		
		Character_Data char4 = new Character_Data("蘭斯洛特",45,30,30,1,
				"湖之騎士（攻擊階段\n\n\t特殊2\t增加自身攻擊25",
				"",
				Character_Data.Atk,Character_Data.no_skill,
				Character_Data.self,Character_Data.no_skill,
				2,0);
		
		Character_Data char5 = new Character_Data("加雷斯",35,30,35,2,
				"隱蔽（攻擊階段\n\n\t特殊2\t自身血量減少10點，攻擊力增加30",
				"捨己為人（攻擊、防禦階段\n\n\t特殊3\t自身血量歸零\n\t\t回覆一名隊友  30血",
				Character_Data.Atk,Character_Data.both,
				Character_Data.self,Character_Data.teammate_1,
				2,3);

		Character_Data char6 = new Character_Data("貝迪維爾",35,30,35,2,
				"守護誓約（防禦階段\n\n\t特殊2\t增加全體防禦10",
				"銀色之腕（攻擊階段\n\n\t特殊2\t增加自身攻擊20",
				Character_Data.def,Character_Data.Atk,
				Character_Data.teammate_all,Character_Data.self,
				2,2);
		
		Character_Data char7 = new Character_Data("崔斯坦",35,35,30,2,
				"傳承的祝福（攻擊階段\n\n\t特殊1\t增加自身攻擊10",
				"悲傷的結局（攻擊階段\n\n\t特殊3\t自身血量歸零\n\t\t我方全體回復15血",
				Character_Data.Atk,Character_Data.Atk,
				Character_Data.self,Character_Data.self,
				1,3);
		
		Character_Data char8 = new Character_Data("摩根勒菲",30,30,40,2,
				"治癒之聲（防禦、攻擊階段\n\n\t特殊2\t回復單一隊友10血量",
				"變化（攻擊階段\n\n\t特殊3\t一回合內將自身攻擊力數值\n\t\t變為己方其中一人",
				Character_Data.both,Character_Data.Atk,
				Character_Data.teammate_1,Character_Data.teammate_1,
				2,3);
		
		Character_Data char9 = new Character_Data("加拉哈德",30,40,30,2,
				"聖杯祝福（防禦階段\n\n\t特殊1\t自身該回合增加10防禦",
				"忠貞騎士（防禦階段\n\n\t特殊3\t防禦增加5，將敵方攻擊轉移至自身",
				Character_Data.def,Character_Data.def,
				Character_Data.self,Character_Data.self,
				1,3);
		
		Character_Data char10 = new Character_Data("珀西瓦",30,35,35,2,
				"堅毅精神（防禦階段\n\n\t特殊2\t提升己方一人防禦15",
				"聖杯守護者（攻擊、防禦階段\n\n\t特殊3\t獲得攻擊/防禦骰  各2個",
				Character_Data.def,Character_Data.both,
				Character_Data.teammate_1,Character_Data.self,
				2,3);
		
		Character_Data char11 = new Character_Data("梅林",20,35,45,2,
				"永遠的阿瓦隆（攻擊、防禦階段\n\n\t特殊3   指定一人回復15血",
				"夢魔之子（攻擊階段\n\n\t特殊2\t增加一人攻擊20",
				Character_Data.both,Character_Data.Atk,
				Character_Data.teammate_1,Character_Data.teammate_1,
				3,2);
		
		Character_Data char12 = new Character_Data("閨妮維雅",20,40,40,2,
				"受詛咒的愛情（防禦階段\n	\n\t特殊2\t指定一人防禦增加30\n\t\t自身血量減10",
				"亡國之妻（攻擊階段\n\n\t特殊3\t指定一人攻擊增加40\n\t\t指定的人血量減20",
				Character_Data.def,Character_Data.Atk,
				Character_Data.teammate_1,Character_Data.teammate_1,
				2,3);
		
		character[0]=char1;
		character[1]=char2;
		character[2]=char3;
		character[3]=char4;
		character[4]=char5;
		character[5]=char6;
		character[6]=char7;
		character[7]=char8;
		character[8]=char9;
		character[9]=char10;
		character[10]=char11;
		character[11]=char12;
		
	}
}
