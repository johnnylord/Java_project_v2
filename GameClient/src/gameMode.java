import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*; 
import java.awt.event.ActionListener;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.lang.*;

public class gameMode extends JFrame {
	
	private static JPanel contentPane;
	private static String[] character = {"亞瑟王","高文","莫德雷德","蘭斯洛特",
										 "加雷斯","貝迪維爾","崔斯坦","摩根勒菲",
										 "加拉哈德","珀西瓦","梅林","閨妮維雅"};
	private static CreateCharacter character_data = new CreateCharacter();
	
	private static Random rand = new Random();
	
	private static JPanel dice_state = new JPanel();
		private static JLabel atk_icon = new JLabel("123");
		private static JLabel def_icon = new JLabel("123");
		private static JLabel spec_icon = new JLabel("123");
		private static JLabel atk_num = new JLabel("0");
		private static JLabel def_num = new JLabel("0");
		private static JLabel spec_num = new JLabel("10");
	
		
	//角色選單	
	private static JPanel character_state = new JPanel();
		//select mode1
		private static JPanel skill1 = new JPanel();
			private static JButton active1 = new JButton("Active");
			private static JTextPane skill_describe1 = new JTextPane();
		private static JPanel skill2 = new JPanel();
			private static JButton active2 = new JButton("Active");
			private static JTextPane skill_describe2 = new JTextPane();
		
		//select mode2
		private static JPanel select_char = new JPanel();
			private static JLabel active_character = new JLabel("USE CHARACTER");
			private static JButton launch_select = new JButton("Launch");
			private static JButton cancel_select = new JButton("Cancel");

		//select mode3
		private static JPanel attack_select = new JPanel();
			private static JLabel who_attack = new JLabel("Attecker");
			private static JLabel attack_who = new JLabel("Attack which character");
			private static JButton Attack_attack_select = new JButton("Attack");
			private static JButton Cancel_attack_select = new JButton("Cancel");
		
		//select mode4
		private static JPanel skill_use = new JPanel();
			private static JLabel who_attack_skill_use = new JLabel("Attecker");
			private static JLabel attack_who_skill_use = new JLabel("Attack which character");
			private static JButton skill_use_yes = new JButton("YES");
			private static JButton skill_use_no = new JButton("NO");
			private static JLabel UseSkill_text = new JLabel("Use Skill?");
			private static JLabel Attack_text = new JLabel("Attack");
		
		//select mode5
		private static	JPanel def_dise_choose = new JPanel();
			private static	JTextField def_dise_enter = new JTextField();
			private static	JTextPane def_choose_text = new JTextPane();
			private static	JButton def_dise_choose_OK = new JButton("OK");
			
		//select mode6
		private static JPanel attack_dise_choose = new JPanel();
			private static JTextField atk_dise_enter = new JTextField();
			private static JTextPane atk_choose_text = new JTextPane();
			private static JButton atk_dise_choose_OK = new JButton("OK");
			private static JButton atk_dise_choose_cancel = new JButton("Cancel");
		
		//select mode7
		private static	JPanel thorw_dise = new JPanel();
			private static	JLabel dise_0 = new JLabel("1");
			private static	JLabel dise_1 = new JLabel("1");
			private static	JLabel dise_2 = new JLabel("1");
			private static	JLabel dise_3 = new JLabel("1");
			private static  JLabel text_this_stage = new JLabel("本回合獲得的骰子：");
			
			
	/*private static JPanel chatroom = new JPanel();
		private static JTextField dialog = new JTextField();
		private static JTextPane textPane = new JTextPane();
		private static JButton send_message = new JButton("Send");	*/
			
		
	
	
	
	//character_button_select
	private static JButton opponent_character_1 = new JButton("");
	private static JButton opponent_character_2 = new JButton("");
	private static JButton opponent_character_3 = new JButton("");
	private static JButton self_character_1 = new JButton("");
	private static JButton self_character_2 = new JButton("");
	private static JButton self_character_3 = new JButton("");
	private static JButton character_button_select[] = new JButton[]{self_character_1,opponent_character_1,
																	 self_character_2,opponent_character_2,
																	 self_character_3,opponent_character_3};
	
	//character_label_state
	private static JLabel opponent_character_1_data = new JLabel("New label");
	private static JLabel opponent_character_2_data = new JLabel("New label");
	private static JLabel opponent_character_3_data = new JLabel("New label");
	private static JLabel self_character_1_data = new JLabel("New label");
	private static JLabel self_character_2_data = new JLabel("New label");
	private static JLabel self_character_3_data = new JLabel("New label");
	private static JLabel character_label_state[] = new JLabel[]{self_character_1_data , opponent_character_1_data ,
																 self_character_2_data , opponent_character_2_data , 
																 self_character_3_data , opponent_character_3_data};
	
	
	//左中選單
	private static JLabel stage = new JLabel("New label");
	private static JButton ready = new JButton("Ready");
	private static JButton attack = new JButton("Attack");
	private static JButton end = new JButton("End Phase");
	private static JButton surrender = new JButton("Surrender");
	
	
	
	
	//存放該場遊戲雙方所選擇的角色
	//private static int[] selected = new int[]{1,2,3,4,5,6};
	private static int[] selected = new int[]{1,2,7,8,11,4};
	private static boolean[] character_alive = new boolean[]{true,true,true,true,true,true};
	
	//現在的階段
	private static final int atk_stage = 1;
	private static final int def_stage = 2;
	private static int phase_stage = atk_stage; //1 as atk , 2 as def
	
	//右邊角色選單的條件判斷
	private static int character_state_mode = 7;
	private static boolean end_using_skill = true;
	
	//在選擇發動技能時所按下的角色(會不斷變更
	private static int window_character = 0;
	//儲存發動技能的角色
	private static int window_skillUse_character = 0;
	
	//判斷按下的技能是哪個
	private static int skill_select = 0;
	
	//判斷攻擊時是否有選好2個角色
	private static int attack_judge = -1;
	private static int attacker_judge = -1;
	
	private static int attacker_test = 1;
	private static int attack_test = 0;
	
	
	public static boolean attack_all = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gameMode frame = new gameMode(selected);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public gameMode(int[] s) {	
		windows_construct(s);
		null_construct();
	}
	
	private void character_windows_show_nothing()//清空(完全不顯示
	{
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
	}

	private void null_construct()//清空(有條件判斷
	{
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
				check_use_skill_construct();
			}
			else //
			{
				character_windows_show_nothing();
			}
		}
		
	}
	private void skill_active_construct(int card) //mode1 查看角色技能
	{
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
		
		//mode1
		skill1.setVisible(true);  
		skill2.setVisible((character_data.character[selected[card]].get_skill_num()==1)?false:true) ; 
		skill_describe1.setText(character_data.character[selected[card]].get_skill1());
		skill_describe2.setText(character_data.character[selected[card]].get_skill2());
		
		if(phase_stage == atk_stage) //atk stage
		{
			//skill1 judge can use
			if( (card%2)==0 &&
			   (character_data.character[selected[window_character]].skill1_use_stage() == Character_Data.Atk || 
			    character_data.character[selected[window_character]].skill1_use_stage() == Character_Data.both))
			{	active1.setEnabled(true);	}
			else{	active1.setEnabled(false);	}
			
			//skill2 judge can use
			if( (card%2)==0 && phase_stage == 1 &&
				(character_data.character[selected[window_character]].skill2_use_stage() == Character_Data.Atk || 
				 character_data.character[selected[window_character]].skill2_use_stage() == Character_Data.both))
			{	active2.setEnabled(true);	}
			else{	active2.setEnabled(false);	}
		}
		if( phase_stage == def_stage )
		{
			//skill1 judge can use
			if( (card%2)==0 &&
			   (character_data.character[selected[window_character]].skill1_use_stage() == Character_Data.def || 
			    character_data.character[selected[window_character]].skill1_use_stage() == Character_Data.both))
			{	active1.setEnabled(true);	}
			else{	active1.setEnabled(false);	}
			
			//skill2 judge can use
			if( (card%2)==0 && phase_stage == 2 &&
				(character_data.character[selected[window_character]].skill2_use_stage() == Character_Data.def || 
				 character_data.character[selected[window_character]].skill2_use_stage() == Character_Data.both))
			{	active2.setEnabled(true);	}
			else{	active2.setEnabled(false);	}
		}
		
	}
	private void select_lunch_construct() //mode2 選擇作用對象
	{
		character_state_mode = 2;
		
		//mode1
		skill1.setVisible(false);  
		skill2.setVisible(false); 
		//mode2
		select_char.setVisible(true);
			active_character.setIcon(null);
			active_character.setText("");	
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
	}
	private void select_attack_construct() //mode3 選擇攻擊對象
	{
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
			who_attack.setText("Attecker");
			attack_who.setText("Attack which character");
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
	}
	private void check_use_skill_construct() //mode 選擇要否執行技能
	{
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
		
		ImageIcon img = new ImageIcon(this.getClass().getResource(character[selected[attacker_test]]+".png"));
		img.setImage(img.getImage().getScaledInstance(200,295,Image.SCALE_DEFAULT));
		who_attack_skill_use.setIcon(img);

		img = new ImageIcon(this.getClass().getResource(character[selected[attack_test]]+".png"));
		img.setImage(img.getImage().getScaledInstance(200,295,Image.SCALE_DEFAULT));
		attack_who_skill_use.setIcon(img);
		
	}
	private void def_Dise_enter_construct() //mode5 選擇防禦骰量
	{
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
	}
	private void atk_Dise_enter_construct() //mode6 選擇攻擊骰量
	{
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
	}
	
	
	//選擇技能發動對項 對應到角色button //mode2時按下
	//選擇技能發動對象
	private void skill_active_select(int card){
		ImageIcon img = new ImageIcon(this.getClass().getResource(character[selected[card]]+".png"));
		img.setImage(img.getImage().getScaledInstance(200,295,Image.SCALE_DEFAULT));
		active_character.setIcon(img);
		active_character.setText("USE CHARACTER");
		
		launch_select.setEnabled(false);
		if(skill_select==1){
			//判斷發動對象是否正確
			if(character_data.character[selected[window_skillUse_character]].skill1_use_char() == Character_Data.teammate_1 && (card%2)==0)
			{
				launch_select.setEnabled(true);
			}
			if(character_data.character[selected[window_skillUse_character]].skill1_use_char() == Character_Data.self && card==window_skillUse_character)
			{
				launch_select.setEnabled(true);
			}
			if(character_data.character[selected[window_skillUse_character]].skill1_use_char() == Character_Data.teammate_all && (card%2)==0)
			{
				launch_select.setEnabled(true);
			}
		}
		if(skill_select==2){
			//判斷發動對象是否正確
			if(character_data.character[selected[window_skillUse_character]].skill2_use_char() == Character_Data.teammate_1 && (card%2)==0)
			{
				launch_select.setEnabled(true);
			}
			if(character_data.character[selected[window_skillUse_character]].skill2_use_char() == Character_Data.self && card==window_skillUse_character)
			{
				launch_select.setEnabled(true);
			}
			if(character_data.character[selected[window_skillUse_character]].skill2_use_char() == Character_Data.teammate_all && (card%2)==0)
			{
				launch_select.setEnabled(true);
			}
		}
	}
	//選擇攻擊對象 對應到角色BUTTON //左中選單ATTACK 按下時
	//選擇攻擊對象
	private void attack_select(int card){
		if((card%2)==0){
			attacker_judge = card;
			ImageIcon img = new ImageIcon(this.getClass().getResource(character[selected[card]]+".png"));
			img.setImage(img.getImage().getScaledInstance(200,295,Image.SCALE_DEFAULT));
			who_attack.setIcon(img);
			who_attack.setText("");
		}
		else
		{
			attack_judge = card;
			ImageIcon img = new ImageIcon(this.getClass().getResource(character[selected[card]]+".png"));
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
	private void windows_stage_select()
	{
		stage.setText((phase_stage ==1)?"攻擊階段":"防禦階段");
		if((phase_stage ==1))
		{
			ready.setEnabled(true);
			attack.setEnabled(false);	
			end.setEnabled(false);	
		}
		if(phase_stage ==2)
		{
			ready.setEnabled(false);
			attack.setEnabled(false);
			end.setEnabled(false);	
		}
	}
	
	
	//設定準備階段按下後所出現的骰子
	private void set_dise_icon(int i,int j)
	{
		//System.out.println(x);
		String jpg = (i==0)? "sword.png":
					 (i==1)? "sword_2.png":
					 (i==2)? "shield.png":
					 (i==3)? "shield_2.png":
					 (i==4)? "special.png":"spec_2.png";

		ImageIcon img = new ImageIcon(this.getClass().getResource(jpg));
		img.setImage(img.getImage().getScaledInstance(120,120,Image.SCALE_DEFAULT));
		
		if(j==0) dise_0.setIcon(img);
		if(j==1) dise_1.setIcon(img);
		if(j==2) dise_2.setIcon(img);
		if(j==3) dise_3.setIcon(img);
	}
	
	private void throw_Dise()
	{
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
	private void windows_construct(int[] s)
	{
		//整個WINDOWS FORM
		windows_form_construct();
		
		//角色目前狀況
		windows_character_button_construct(s);
		windows_character_state_construct(s);
		characterButton_listener();
		
		//左中選單
		windows_stage_select_construct();
		windows_stage_select(); //判斷攻擊、防禦階段 可使用按鈕有差別
		stage_listener();
		
		//右邊骰子介面
		windows_dice_state_construct();
		//右邊角色介面(還有一個獨立的FUNC 來建構
		windows_characterState_construct();
		//角色發動技能的資訊
		skill_launch_message();
		
		
	}	
	//整個WINDOWS FORM
	
	private void windows_form_construct()
	{
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 960);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	}

	//角色目前狀況
	
	private void characterButton_listener()
	{
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

	private void windows_character_button_construct(int[] s)
	{
		//icon button self1	
		self_character_1.setBounds(50, 550, 200, 295);
		contentPane.add(self_character_1);
		
		//icon button opp1		
		opponent_character_1.setBounds(50, 50, 200, 295);
		contentPane.add(opponent_character_1);

		//icon button self2
		self_character_2.setBounds(275, 550, 200, 295);
		contentPane.add(self_character_2);
				
		//icon button opp2
		opponent_character_2.setBounds(275, 50, 200, 295);
		contentPane.add(opponent_character_2);
		
		//icon button self3
		self_character_3.setBounds(500, 550, 200, 295);
		contentPane.add(self_character_3);
		
		//icon button opp3
		opponent_character_3.setBounds(500, 50, 200, 295);
		contentPane.add(opponent_character_3);
		
		//set icon
		for(int i=0;i<6;i++)
		{
			selected[i]=s[i];
			ImageIcon img = new ImageIcon(this.getClass().getResource(character[selected[i]]+".png"));
			img.setImage(img.getImage().getScaledInstance(200,295,Image.SCALE_DEFAULT));
			character_button_select[i].setIcon(img);
			character_button_select[i].setText("");
		}
	}
	private void windows_character_state_construct(int[] s)
	{
		opponent_character_1_data.setHorizontalAlignment(SwingConstants.CENTER);
		//text opp1
		opponent_character_1_data.setBounds(50, 375, 200, 20);
		contentPane.add(opponent_character_1_data);
		opponent_character_2_data.setHorizontalAlignment(SwingConstants.CENTER);
		
		//text opp2
		opponent_character_2_data.setBounds(275, 375, 200, 20);
		contentPane.add(opponent_character_2_data);
		opponent_character_3_data.setHorizontalAlignment(SwingConstants.CENTER);
		
		//text opp3
		opponent_character_3_data.setBounds(500, 375, 200, 20);
		contentPane.add(opponent_character_3_data);
		self_character_1_data.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		//text self1
		self_character_1_data.setBounds(50, 507, 200, 20);
		contentPane.add(self_character_1_data);
		self_character_2_data.setHorizontalAlignment(SwingConstants.CENTER);
		
		//text self2
		self_character_2_data.setBounds(275, 508, 200, 20);
		contentPane.add(self_character_2_data);
		self_character_3_data.setHorizontalAlignment(SwingConstants.CENTER);
		
		//text self3
		self_character_3_data.setBounds(500, 508, 200, 20);
		contentPane.add(self_character_3_data);
		
		//set initial state
		for(int i=0;i<6;i++)
		{
			selected[i]=s[i];
			String state = "Atk "+ character_data.character[selected[i]].get_attack() + 
					 	   " / Def "+ character_data.character[selected[i]].get_defence() + 
					 	   " / Hp " + character_data.character[selected[i]].get_hp(); 
			character_label_state[i].setText(state);
		}
	}
	
	
	//左中選單
	private void stage_listener()
	{
		ready.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ready.setEnabled(false);
				attack.setEnabled(true);
				end.setEnabled(true);		
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
				//準備接受敵人攻擊
				phase_stage = (phase_stage ==1)? 2:1;//之後會刪去 畢竟是接收敵人是否結束
				returnToOriginalState();
				windows_stage_select();
				end_using_skill = false;
				null_construct();
			}
		});
		
		
		surrender.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int surrender = JOptionPane.showConfirmDialog(null, "確定投降?","",JOptionPane.YES_NO_OPTION);
				//System.out.println(surrender);
				if(surrender==0) //contentPane.setVisible(false);//法二傳送至主機端(請她delete)
					System.exit(0);
			}
		});
	}
	private void windows_stage_select_construct()
	{
		//stage text
		stage.setFont(new Font("標楷體", Font.PLAIN, 20));
		stage.setText("");
		stage.setBounds(14, 13, 94, 24);
		contentPane.add(stage);
				
		//button ready
		ready.setBounds(50, 435, 100, 30);
		ready.setEnabled((phase_stage ==1)?true:false);
		contentPane.add(ready);
		
		//button attack	
		attack.setBounds(234, 435, 100, 30);
		attack.setEnabled((phase_stage ==1)?true:false);
		contentPane.add(attack);
		
		
		//button end
		end.setBounds(423, 435, 100, 30);
		contentPane.add(end);
		
		
		//button surrender
		surrender.setBounds(600, 435, 100, 30);
		contentPane.add(surrender);
	}
	
	//目前骰子剩於狀況
	

	private void windows_dice_state_construct()
	{
		dice_state.setBackground(Color.LIGHT_GRAY);
		dice_state.setBounds(725, 25, 535, 124);
		contentPane.add(dice_state);
		dice_state.setLayout(null);
		
		//icon atk
		atk_icon.setBounds(15, 25, 70, 70);
		atk_icon.setBackground(Color.BLACK);
		dice_state.add(atk_icon);
		ImageIcon img1 = new ImageIcon(this.getClass().getResource("sword.png"));
		img1.setImage(img1.getImage().getScaledInstance(70,70,Image.SCALE_DEFAULT));
		atk_icon.setIcon(img1);
		
		//icon def
		def_icon.setBounds(190, 25, 70, 70);
		dice_state.add(def_icon);
		ImageIcon img2 = new ImageIcon(this.getClass().getResource("shield.png"));
		img2.setImage(img2.getImage().getScaledInstance(80,80,Image.SCALE_DEFAULT));
		def_icon.setIcon(img2);
		
		//icon spec
		spec_icon.setBounds(372, 25, 70, 70);
		dice_state.add(spec_icon);
		ImageIcon img3 = new ImageIcon(this.getClass().getResource("special.png"));
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
	private void windows_characterState_construct()
	{
		character_state.setBackground(Color.LIGHT_GRAY);
		character_state.setBounds(725, 162, 535, 445);
		contentPane.add(character_state);
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
		private void characterState_skill_data_listener()
		{
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
		private void windows_characterState_skill_data_construct()
		{
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
			skill1.add(skill_describe1);
			skill2.setBackground(Color.LIGHT_GRAY);
	
			//skill panel2
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
			skill2.add(skill_describe2);
			
		}
		
		//select mode 2 選擇技能施放對象
		//select mode 2 選擇要發動的對象
		private void characterState_skill_listener() //發動技能所需的action(ATTACK PHASE)//仍需要發動技能後產生的效果
		{
			launch_select.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(skill_select==1){		
						//判斷spec骰子是否足夠
						if(character_data.character[selected[window_skillUse_character]].skill1_specNeed() > Integer.valueOf(spec_num.getText())  )
						{
							JOptionPane.showMessageDialog(null, "骰子數不足需要"+character_data.character[selected[window_skillUse_character]].skill1_specNeed()+"個");
							null_construct();
						}
						else{
							String tmp = Integer.toString((Integer.valueOf(spec_num.getText()) - character_data.character[selected[window_skillUse_character]].skill1_specNeed()));
							int tmp1 = (window_character==0||window_skillUse_character==0)? ((window_character==2||window_skillUse_character==2)? 4:2):0;
							int skill_type = character_data.character_skill.character_skill_array[2*selected[window_skillUse_character]+skill_select-1].skill_action(
									character_data.character[selected[window_skillUse_character]],
									character_data.character[selected[window_character]], 
									character_data.character[selected[tmp1]]);
							
							if(skill_type==character_data.character_skill.attack_all)
								attack_all = true;
								
							//setNowState();
							spec_num.setText(tmp);
							null_construct();
						}
					}
					else if(skill_select==2)
					{
						//判斷spec骰子是否足夠
						if(character_data.character[selected[window_skillUse_character]].skill2_specNeed() > Integer.valueOf(spec_num.getText())  )
						{
							JOptionPane.showMessageDialog(null, "骰子數不足需要"+character_data.character[selected[window_skillUse_character]].skill2_specNeed()+"個");
							null_construct();
						}
						else{
							String tmp = Integer.toString((Integer.valueOf(spec_num.getText()) - character_data.character[selected[window_skillUse_character]].skill2_specNeed()));
							int tmp1 = (window_character==0||window_skillUse_character==0)? ((window_character==2||window_skillUse_character==2)? 4:2):0;
							int skill_type = -1;
							
							if(character_data.character[selected[window_skillUse_character]].get_name().equals("崔斯坦")	)
							{
								int tmp2 = (window_skillUse_character==0)? 2:0;
								int tmp3 = (window_skillUse_character==0)? 4:
													(window_skillUse_character==2)?4:2;
								character_data.character_skill.character_skill_array[2*selected[window_skillUse_character]+skill_select-1].skill_action(
										character_data.character[selected[window_skillUse_character]],
										character_data.character[selected[tmp2]], 
										character_data.character[selected[tmp3]]);
								System.out.println("tmp2 = "+tmp2);
								System.out.println("tmp3 = "+tmp3);
								
								character_button_select[window_skillUse_character].setEnabled(false);
								character_alive[window_skillUse_character] = false;
								
							}
							if(character_data.character[selected[window_skillUse_character]].get_name().equals("加雷斯"))
							{
								if(window_character == window_skillUse_character)
									JOptionPane.showMessageDialog(null, "該技能不能對自己使用");
								else
								{	character_data.character_skill.character_skill_array[2*selected[window_skillUse_character]+skill_select-1].skill_action(
											character_data.character[selected[window_skillUse_character]],
											character_data.character[selected[window_character]], 
											character_data.character[selected[tmp1]]);
								
								character_button_select[window_skillUse_character].setEnabled(false);	
								character_alive[window_skillUse_character] = false;
								}	
							}
							else
							{
								skill_type = character_data.character_skill.character_skill_array[2*selected[window_skillUse_character]+skill_select-1].skill_action(
										character_data.character[selected[window_skillUse_character]],
										character_data.character[selected[window_character]], 
										character_data.character[selected[tmp1]]);
							}
							
							
							System.out.println(tmp1);
							
							if(skill_type==character_data.character_skill.change_attack)
								attack_test = window_skillUse_character;
							if(skill_type==character_data.character_skill.add_dise)
							{
								def_num.setText(Integer.toString((Integer.valueOf(def_num.getText()) +2)));
								atk_num.setText(Integer.toString((Integer.valueOf(def_num.getText()) +2)));
							}
								
							//setNowState();
							spec_num.setText(tmp);
							null_construct();
						}
					}
					check_HP();
					setNowState();
				}
			});
			
			cancel_select.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					null_construct();
				}
			});
		}
		private void windows_characterState_skill_useCharacter_construct()
		{
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
		private void characterState_attackCharacterSelect_listener()
		{
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
		private void windows_characterState_attackCharacterSelect_construct()
		{
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
		private void characterState_useSkillCheck_listener()
		{
			skill_use_yes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for(int i=0;i<=4;i=i+2)
					{
						//如果全都是攻擊階段的技能
						if(character_data.character[selected[i]].skill1_use_stage()==Character_Data.def||
						   character_data.character[selected[i]].skill2_use_stage()==Character_Data.def)
						{
							//
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
		private void windows_characterState_useSkillCheck_construct()
		{
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
		private void characterStates_defDise_choose_listener()
		{
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
							phase_stage = (phase_stage ==1)? 2:1;
							windows_stage_select();
							null_construct();
							
							//***********************************************************
							
							int damage = character_data.character[selected[attacker_test]].get_attack() - 
									(character_data.character[selected[attack_test]].get_now_defence() + use_dise*10);						
							if(damage>0)
							{
								character_data.character[selected[attack_test]].set_hp(character_data.character[selected[attack_test]].get_hp() - damage);
								if(character_data.character[selected[attack_test]].get_hp()<0)
								{
									check_HP();
									setNowState();
									//character_button_select[attack_test].setEnabled(false);
									//character_alive[attack_test] = false;
								}
							}				
							character_state_mode = 7;  //此處之後要另外寫
							returnToOriginalState();

							//***********************************************************
						}	
					}catch(Exception ex)
					{
						JOptionPane.showMessageDialog(null, "輸入錯誤");
						def_dise_enter.setText("");
					}
				}
			});
		}
		private void windows_characterStates_defDise_choose_construct()
		{
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
		private void characterStates_atkDise_choose_listener()
		{
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
							null_construct();//等待回復//傳送封包
							
							
							//***********************************************************
							int atk = character_data.character[selected[attacker_judge]].get_now_attack()+use_dise*10;
							System.out.println(character_data.character[selected[attacker_judge]].get_name() +"發動攻擊");
							System.out.println("攻擊："+atk);
							//***********************************************************
							
							returnToOriginalState();
						}	
					}catch(Exception ex)
					{
						JOptionPane.showMessageDialog(null, "輸入錯誤");
						def_dise_enter.setText("");
					}
				}
			});
			atk_dise_choose_cancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					null_construct();
				}
			});
		}
		private void windows_characterStates_atkDise_choose_construct()
		{
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

		//select mode 7
		private void windows_characterStates_throwDise_construct()
		{
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

		private void returnToOriginalState()
		{
			for(int i=0;i<6;i++)
			{
				character_data.character[selected[i]].reset_now_attack();
				character_data.character[selected[i]].reset_now_defence();				
				String state = "Atk "+ character_data.character[selected[i]].get_now_attack() + 
					 	   " / Def "+ character_data.character[selected[i]].get_now_defence() + 
					 	   " / Hp " + character_data.character[selected[i]].get_hp();
				character_label_state[i].setText(state);
				character_button_select[i].setEnabled(character_alive[i]);
			}
		}
		private void setNowState()
		{
			for(int i=0;i<6;i++)
			{		
				String state = "Atk "+ character_data.character[selected[i]].get_now_attack() + 
					 	   " / Def "+ character_data.character[selected[i]].get_now_defence() + 
					 	   " / Hp " + character_data.character[selected[i]].get_hp();
				character_label_state[i].setText(state);
				character_button_select[i].setEnabled(character_alive[i]);
			}
		}
		
		
		private void check_HP()
		{
			for(int i=0;i<6;i++)
			{
				if(character_data.character[selected[i]].get_hp() <=0)
				{
					character_alive[i] = false;
				}
			}
		}
		private void skill_launch_message()
		{
			/*JTextArea skill_message = new JTextArea();
			skill_message.setBounds(725, 634, 535, 280);
			contentPane.add(skill_message);	
			JScrollPane scrollable = new JScrollPane();
			scrollable.setBounds(0, 0, 535, 280);
			chatContent.setEditable(false);
			chatContent.setLineWrap(true);
			chatContent.setRows(35); //
			scrollable.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollable.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			chat.getContentPane().add(scrollable);*/
		}
}
