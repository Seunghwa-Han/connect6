package edu.handong.csee.java.connect;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import edu.handong.csee.java.connect.image.ConnectImage;

public class MenuPanel extends JPanel implements ActionListener {

	static JButton btnGameState;
	static JButton btnDontRandom;
	static JButton btnDontSelf;
	static JLabel turnLabel;
	static JLabel txt;
	static JLabel myStone;
	static JLabel winLoseLabel;
	static JLabel stone1;
	static JLabel stone2;
	CheckerBoard board;
	MenuPanel menu;

	ConnectImage img;

	public MenuPanel(CheckerBoard board) {
		setLayout(null);
		setBounds(800, 0, 200, 800);
		this.board = board;
		menu = this;

		txt = new JLabel("착수 금지점 모드");
		btnGameState = new JButton("게임 시작하기");
		btnDontRandom = new JButton("랜덤 설정");
		btnDontSelf = new JButton("직접 설정");
		turnLabel = new JLabel();
		myStone = new JLabel();
		winLoseLabel = new JLabel();
		stone1 = new JLabel();
		stone2 = new JLabel();
		img = new ConnectImage();

		btnGameState.setBounds(35, 30, 130, 50);

		txt.setBounds(0, 90, 200, 30);
		txt.setHorizontalAlignment(JLabel.CENTER);

		btnDontRandom.setBounds(10, 120, 90, 50);
		btnDontSelf.setBounds(110, 120, 90, 50);

		turnLabel.setBackground(new Color(255, 255, 243));
		turnLabel.setOpaque(true);
		turnLabel.setFont(new Font("Kohinoor Bangla", Font.BOLD, 18));
		turnLabel.setHorizontalAlignment(JLabel.CENTER);
		turnLabel.setBounds(0, 200, 200, 50);

		myStone.setBackground(new Color(255, 255, 243));
		myStone.setOpaque(true);
		myStone.setFont(new Font("Kohinoor Bangla", Font.PLAIN, 18));
		myStone.setHorizontalAlignment(JLabel.CENTER);
		myStone.setBounds(0, 250, 200, 50);

		winLoseLabel.setFont(new Font("Kohinoor Bangla", Font.PLAIN, 18));
		winLoseLabel.setBackground(new Color(255, 255, 243));
		winLoseLabel.setOpaque(true);
		winLoseLabel.setHorizontalAlignment(JLabel.CENTER);
		winLoseLabel.setBounds(0, 700, 200, 50);

		stone1.setBounds(50, 310, 50, 50);
		stone2.setBounds(110, 310, 50, 50);

		add(btnGameState);
		add(txt);
		add(btnDontRandom);
		add(btnDontSelf);
		add(turnLabel);
		add(myStone);
		add(winLoseLabel);
		add(stone1);
		add(stone2);
		btnGameState.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton) e.getSource();
		if (btn.getText().equals("게임 시작하기")) {
			btnGameState.setText("착수 금지점 세팅 중...");
			btnDontRandom.addActionListener(this);
			btnDontSelf.addActionListener(this);
			JOptionPane.showConfirmDialog(null, "착수 금지점 설정 모드를 선택하세요!", "착수 금지점 세팅", JOptionPane.YES_NO_OPTION);
			winLoseLabel.setText(Integer.toString(board.gameWinNum).concat("승 ")
					.concat(Integer.toString(board.gameNum - board.gameWinNum)).concat("패"));
			board.gameNum++;
		} else if (btn.getText().equals("다시 시작")) {
			board.gameNum++;
			board.vc.clear();
			board.vcCom.clear();
			board.dont.clear();
			board.result = new int[19][19];
			board.click = 0;
			board.dontClick = 0;
			board.dontNum = 0;
			board.repaint();
			turnLabel.setText("");
			myStone.setText("");
			stone1.setVisible(false);
			stone2.setVisible(false);
			btnDontRandom.setForeground(Color.black);
			btnDontSelf.setForeground(Color.black);
			btnGameState.setText("착수 금지점 세팅 중...");
			btnDontRandom.addActionListener(this);
			btnDontSelf.addActionListener(this);
			JOptionPane.showConfirmDialog(null, "착수 금지점 설정 모드를 선택하세요!", "착수 금지점 세팅", JOptionPane.YES_NO_OPTION);
		} else if (btn == btnDontRandom) {
			btnDontRandom.removeActionListener(this);
			btnDontSelf.removeActionListener(this);
			btnDontSelf.setForeground(Color.LIGHT_GRAY);
			btnGameState.setText("게임중~~");

			Random rnd = new Random();
			int dontNum = rnd.nextInt(6);

			for (int i = 0; i < dontNum; i++) {
				int x = rnd.nextInt(19);
				int y = rnd.nextInt(19);
				if (x == 9 && y == 9) {
					i--;
					continue;
				}
				if (board.result[y][x] == 3) {
					i--;
					continue;
				}
				board.dont.add(new Point((int) (x * 41.7 + 26), (int) (y * 41.6 + 28)));
				board.result[y][x] = 3;
			}

			if (((int) (Math.random() * 100)) % 2 == 0) {
				board.stoneMode = 1; // 내가 흑, 컴퓨터가 백
//				board.turn = 2;   // 컴퓨터 차례
				turnLabel.setText("컴퓨터 차례");
				myStone.setText("내 돌: 검정돌");
//				JOptionPane.showConfirmDialog(null,"검정돌로 플레이하세요.", "게임 시작",JOptionPane.YES_NO_OPTION);
				board.result[9][9] = 1;
				board.vc.add(new Point(401, 402));

				stone1.setIcon(new ImageIcon(img.black2));
				stone2.setIcon(new ImageIcon(img.black2));
				stone1.setVisible(false);
				stone2.setVisible(false);
				
				Util.comWhiteFirst(board.vcCom, board.dont);
				board.repaint();
				
				Timer timer = new Timer(1000, event -> {
					board.turn = 1;
					MenuPanel.turnLabel.setText("내 차례");
					MenuPanel.stone1.setVisible(true);
					MenuPanel.stone2.setVisible(true);
				});
				timer.setRepeats(false);
				timer.start();

			} else {
				board.stoneMode = 2; // 내가 백, 컴퓨터가 흑
				board.turn = 1; // 내 차례
				turnLabel.setText("내 차례");
				myStone.setText("내 돌: 하얀돌");
//				JOptionPane.showConfirmDialog(null,"하얀돌로 플레이하세요.", "게임 시작",JOptionPane.YES_NO_OPTION);
				board.result[9][9] = 2;
				board.vcCom.add(new Point(401, 402));

				stone1.setIcon(new ImageIcon(img.white2));
				stone2.setIcon(new ImageIcon(img.white2));
				stone1.setVisible(true);
				stone2.setVisible(true);
				menu.revalidate();
			}
			board.repaint();
			board.addMouseListener(board);

		} else if (btn == btnDontSelf) {
			btnDontRandom.removeActionListener(this);
			btnDontSelf.removeActionListener(this);
			btnDontRandom.setForeground(Color.LIGHT_GRAY);
//			btnGameState.setText("게임중~~");

			prohibition chooseDontNum = new prohibition();
			chooseDontNum.setVisible(true);
		}
	}

	class prohibition extends JFrame {
		Integer num[] = { 0, 1, 2, 3, 4, 5 };
		JLabel txt;
		JLabel result;
		JButton ok;
		JButton cancel;
		JComboBox chooser;
		JFrame prohibitFrame;

		prohibition() {
			prohibitFrame = this;
			setLayout(null);
			setBounds(300, 300, 500, 300);
			ok = new JButton("확인");
			cancel = new JButton("취소");
			txt = new JLabel("착수 금지점 개수를 설정하세요.");
			result = new JLabel("0개");
			chooser = new JComboBox<Integer>(num);
			txt.setBounds(140, 50, 300, 30);
			txt.setFont(new Font("Kohinoor Bangla", Font.PLAIN, 18));
			chooser.setBounds(100, 100, 300, 50);
			result.setFont(new Font("Kohinoor Bangla", Font.BOLD, 18));
			result.setBounds(235, 160, 100, 30);
			ok.setBounds(330, 210, 70, 40);
			cancel.setBounds(410, 210, 70, 40);

			chooser.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Integer comboNum = (int) chooser.getSelectedItem();
					result.setText(comboNum.toString().concat("개"));
				}
			});
			ok.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JButton btn = (JButton) e.getSource();
					if (btn == ok) {
						board.dontNum = (int) chooser.getSelectedItem();
						if (board.dontNum != 0) {
							menu.txt.setForeground(Color.red);
							menu.txt.setText("착수 금지점 ".concat(Integer.toString(board.dontNum).concat("개를 설정하세요.")));
							board.dontMode = 1;
						} else { // 착수 금지점 0개
							if (((int) (Math.random() * 100)) % 2 == 0) {
								board.stoneMode = 1; // 내가 흑, 컴퓨터가 백
								board.turn = 2; // 컴퓨터 차례
								turnLabel.setText("컴퓨터 차례");
								myStone.setText("내 돌: 검정돌");
								board.result[9][9] = 1;
								board.vc.add(new Point(401, 402));

								stone1.setIcon(new ImageIcon(img.black2));
								stone2.setIcon(new ImageIcon(img.black2));
								stone1.setVisible(false);
								stone2.setVisible(false);
								menu.revalidate();
								
								Util.comWhiteFirst(board.vcCom, board.dont);
								board.repaint();
								
								Timer timer = new Timer(1000, event -> {
									board.turn = 1;
									MenuPanel.turnLabel.setText("내 차례");
									MenuPanel.stone1.setVisible(true);
									MenuPanel.stone2.setVisible(true);
								});
								timer.setRepeats(false);
								timer.start();
							} else {
								board.stoneMode = 2; // 내가 백, 컴퓨터가 흑
								board.turn = 1; // 내 차례
								turnLabel.setText("내 차례");
								myStone.setText("내 돌: 하얀돌");
								board.result[9][9] = 2;
								board.vcCom.add(new Point(401, 402));

								stone1.setIcon(new ImageIcon(img.white2));
								stone2.setIcon(new ImageIcon(img.white2));
								stone1.setVisible(true);
								stone2.setVisible(true);
								menu.revalidate();
							}
							board.repaint();
						}
						menu.revalidate();
						board.addMouseListener(board);
						prohibitFrame.dispose();
					}
				}
			});
			cancel.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JButton btn = (JButton) e.getSource();
					if (btn == cancel) {
						btnDontRandom.setForeground(Color.black);
						btnDontRandom.addActionListener(menu);
						btnDontSelf.addActionListener(menu);
						prohibitFrame.dispose();
					}
				}
			});
			add(chooser);
			add(result);
			add(txt);
			add(ok);
			add(cancel);
		}
	}
}
