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
	 * Create the frame.
	 */
	
	
	
}
