package edu.handong.csee.java.connect;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Font;

public class Main {

	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
//				int[][] test = new int[19][19];
				
//				test[1][0] =1;
//				test[2][0] =1;
//				test[3][0] =1;
//				test[4][0] =1;
//				test[5][0] =2;
//				test[6][0] =1;
//				test[7][0] =1;
//				test[8][0] =1;
//				test[9][0] =1;
//				
//				test[1][1] =1;
//				test[3][1] =1;
//				test[4][1] =1;
//				test[5][1] =1;
//				test[7][1] =1;
//				
//				test[13][2] =1;
//				test[14][2] =1;
//				test[15][2] =1;
//				test[16][2] =1;
				
//				test[0][1] =1;
//				test[0][2] =1;
//				test[0][3] =1;
//				test[0][4] =1;
//				test[0][5] =2;
//				test[0][6] =1;
//				test[0][7] =1;
//				test[0][8] =1;
//				test[0][9] =1;
//				
//				test[1][1]=1;
//				test[1][3]=1;
//				test[1][4]=1;
//				test[1][5]=1;
//				test[1][7]=1;
//				
//				test[2][13] =1;
//				test[2][14] =1;
//				test[2][15] =1;
//				test[2][16] =1;
				
				
//				test[1][0]=1;
//				test[2][0]=1;
//				test[3][0]=1;
//				test[4][0]=1;
//				
//				test[3][1]=1;
//				test[3][2]=1;
//				test[3][3]=1;
//				
//				test[5][1]=1;
//				test[5][2]=1;
//				test[5][3]=1;
//				test[5][4]=1;
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 820);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel top = new JPanel();
		CheckerBoard board = new CheckerBoard();
		MenuPanel menu = new MenuPanel(board);
		
		top.setLayout(null);
		top.add(board);
		

		top.add(menu);
		frame.getContentPane().add(top);
		
	}

}

//textField = new JTextField();
//textField.setFont(new Font("Kohinoor Bangla", Font.BOLD, 20));
//board.add(textField);
//textField.setColumns(10);
