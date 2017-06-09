import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.*;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JComboBox;
import java.awt.Font;
import java.awt.Color;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class selectMode extends JFrame {

	private static String[] character = {"亞瑟王","高文","莫德雷德","蘭斯洛特","加雷斯","貝迪維爾","崔斯坦","摩根勒菲","加拉哈德","珀西瓦","梅林","閨妮維雅"};
	private static JLabel character_data = new JLabel("");
	private static JComboBox comboBox = new JComboBox(character);
	private static JLabel label = new JLabel("");
	private static JButton btnNewButton = new JButton("選擇");
	private static JLabel player1_character_1 = new JLabel("選擇角色中");
	private static JLabel player1_character_2 = new JLabel("選擇角色中");
	private static JLabel player1_character_3 = new JLabel("選擇角色中");
	private static JLabel player2_character_1 = new JLabel("選擇角色中");
	private static JLabel player2_character_2 = new JLabel("選擇角色中");
	private static JLabel player2_character_3 = new JLabel("選擇角色中");
	
	private static JLabel select[] = new JLabel[]{player1_character_1,player2_character_1,player1_character_2,player2_character_2,player1_character_3,player2_character_3};
	private static int seleted[] = new int[12];
	private static int picked[] = new int[6];
	
	public static int count = 0; 
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					selectMode frame = new selectMode();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/*public String now_pic()
	{
		
		
	}*/

	/**
	 * Create the frame.
	 */
	public selectMode() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 960);
		getContentPane().setLayout(null);
		
		
		
		//when change the combobox (action)
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int index = comboBox.getSelectedIndex();
				
				if (index != 0) {
					String content = comboBox.getSelectedItem().toString();
					System.out.println("index = " + index + ", character=" + content);
				}
				
				//change the picture
				Image img = new ImageIcon(this.getClass().getResource(character[index]+".png")).getImage();
				character_data.setIcon(new ImageIcon(img));
				
			}
		} );
		
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"亞瑟王","高文","莫德雷德","蘭斯洛特","加雷斯","貝迪維爾","崔斯坦","摩根勒菲","加拉哈德","珀西瓦","梅林","閨妮維雅"}));
		comboBox.setForeground(Color.BLACK);
		comboBox.setFont(new Font("標楷體", Font.BOLD, 40));
		comboBox.setBounds(512, 40, 200, 50);
		getContentPane().add(comboBox);
		

		Image img = new ImageIcon(this.getClass().getResource("亞瑟王.png")).getImage();	
		character_data.setIcon(new ImageIcon(img));
		character_data.setBounds(425, 150, 400, 590);
		getContentPane().add(character_data);
		
		
		
		//press the "select" button (action)
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
					
				if(seleted[comboBox.getSelectedIndex()]==0)
				{
					//set the character image
					ImageIcon img = new ImageIcon(this.getClass().getResource(character[comboBox.getSelectedIndex()]+".png"));
					img.setImage(img.getImage().getScaledInstance(200,295,Image.SCALE_DEFAULT));
					seleted[comboBox.getSelectedIndex()]=1;
					picked[count] = comboBox.getSelectedIndex();
					select[count].setIcon(img);
					select[count].setText("");
					count++;
					
					if(count==6){
						btnNewButton.setEnabled(false);
						JOptionPane.showMessageDialog(null, "遊戲即將開始");
						gameMode gm = new gameMode(picked);
						gm.show();
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "該角色已被選走");
				}
				comboBox.setSelectedIndex(0);
			}
		});
		btnNewButton.setFont(new Font("標楷體", Font.BOLD, 30));
		btnNewButton.setBounds(559, 802, 131, 56);
		getContentPane().add(btnNewButton);
		label.setBounds(47, 94, 57, 19);
		
		getContentPane().add(label);
		
		
		player1_character_1.setBounds(110, 10, 200, 295);
		getContentPane().add(player1_character_1);
		player1_character_2.setBounds(110, 305, 200, 295);	
		getContentPane().add(player1_character_2);	
		player1_character_3.setBounds(110, 600, 200, 295);
		getContentPane().add(player1_character_3);
		player2_character_1.setBounds(967, 10, 200, 295);
		getContentPane().add(player2_character_1);
		player2_character_2.setBounds(967, 305, 200, 295);
		getContentPane().add(player2_character_2);
		player2_character_3.setBounds(967, 600, 200, 295);
		getContentPane().add(player2_character_3);
	}
	
	
}
