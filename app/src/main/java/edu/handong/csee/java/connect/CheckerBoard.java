package edu.handong.csee.java.connect;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.TimerTask;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import edu.handong.csee.java.connect.image.ConnectImage;

public class CheckerBoard extends JPanel implements MouseListener, ActionListener {
	ConnectImage img;
	int gameNum = 0;        //게임 횟수 
	int gameWinNum = 0;     //내가 이긴 게임 횟수 
	int stoneMode = 0;      //1이면 내가 흑, 컴퓨터가 백 / 2이면 내가 백, 컴퓨터가 흑 
	int dontMode = 0;       //1이면 dont 모드
	int dontNum = 0;        //dont 개수 
	int dontClick = 0;      //dont 모드일때 클릭 횟수 
	ArrayList<Point> vc;    //내 돌 
	ArrayList<Point> vcCom; //컴퓨터 돌 
	ArrayList<Point> dont;  //착수 금지점 
	int[][] result = new int[19][19];  //게임 돌 현황 
	int turn = 0;  //1이면 내 턴, 2이면 컴퓨터 턴 
	int click = 0; //play모드일때 (dont모드 0인 경) 바둑돌 놓은 횟수 
//	Timer timer;

	public CheckerBoard() {
		setBounds(0, 0, 800, 800);
		img = new ConnectImage();
		vc = new ArrayList<>();
		vcCom = new ArrayList<>();
		dont = new ArrayList<>();
		
		this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		
//		timer = new Timer(1000*30, new ActionListener()
//		{
//			public void actionPerformed(ActionEvent e)
//			{
//				if(turn == 1) { //내 차례 
//					
//				}
//			}
//		});
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img.board, 0, 0, this.getWidth(), this.getHeight(), null);
		
		for(int i=0; i< dont.size(); i++) {
			g.setColor(Color.red);
			g.fillOval(dont.get(i).x-10, dont.get(i).y-10, 20, 20);
		}

		if (stoneMode == 1) { // 내가 흑, 컴퓨터가 백
			for (int i = 0; i < vc.size(); i++) {
				g.drawImage(img.black, vc.get(i).x - 15, vc.get(i).y - 15, 30, 30, null);
			}
			for (int i = 0; i < vcCom.size(); i++) {
				g.drawImage(img.white, vcCom.get(i).x - 15, vcCom.get(i).y - 15, 30, 30, null);
			}
		} else if (stoneMode == 2) { // 컴퓨터가 흑, 내가 백
			for (int i = 0; i < vc.size(); i++) {
				g.drawImage(img.white, vc.get(i).x - 15, vc.get(i).y - 15, 30, 30, null);
			}
			for (int i = 0; i < vcCom.size(); i++) {
				g.drawImage(img.black, vcCom.get(i).x - 15, vcCom.get(i).y - 15, 30, 30, null);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Point p = e.getPoint();
		System.out.println(p);
		double x,y;
		int i,j;  //result의 인덱스 j가 첫번째 인덱스, i가 두번째 인덱스 
		if((p.getX()-26)*10%417 <= 208.5) {        //범위 조정 
			x = ((int)((p.getX()-26)/41.7))*41.7 +26;
			i=(int)((p.getX()-26)/41.7);
		}
		else {
			x = ((int)((p.getX()-26)/41.7))*41.7+41.7 +26;
			i= (int)((p.getX()-26)/41.7)+1;
		}
		if((p.getY()-28)*10%416 <=208) {
			 y= ((int)((p.getY()-28)/41.6))*41.6 +28;
			 j= (int)((p.getY()-28)/41.6);
		}
		else {
			 y= ((int)((p.getY()-28)/41.6))*41.6+41.6 +28;
			 j= (int)((p.getY()-28)/41.6)+1;
		}
		
		if(dontMode == 1) {  //착수 금지점 모드 
			if(result[j][i]==3) {
				JOptionPane.showConfirmDialog(null, "같은 자리에 중복해서 둘 수 없습니다.", "Warning",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			result[j][i] = 3;
			dontClick++;
			dont.add(new Point((int)x,(int)y));
			if(dontClick >= dontNum) {
				dontMode = 0;
				MenuPanel.btnGameState.setText("게임중~~");
				MenuPanel.txt.setForeground(Color.black);
				MenuPanel.txt.setText("착수 금지점 모드");
				if (((int) (Math.random() * 100)) % 2 == 0) {
					stoneMode = 1; // 내가 흑, 컴퓨터가 백
//					turn = 2;   // 컴퓨터 차례
					MenuPanel.turnLabel.setText("컴퓨터 차례");
					MenuPanel.myStone.setText("내 돌: 검정돌");
					result[9][9] = 1;
					vc.add(new Point(401, 402));
					
					MenuPanel.stone1.setIcon(new ImageIcon(img.black2));
					MenuPanel.stone2.setIcon(new ImageIcon(img.black2));
					MenuPanel.stone1.setVisible(false);
					MenuPanel.stone2.setVisible(false);
					
					Util.comWhiteFirst(vcCom, dont);
					repaint();
					
					Timer timer = new Timer(1000, event -> {
						turn = 1;
						MenuPanel.turnLabel.setText("내 차례");
						MenuPanel.stone1.setVisible(true);
						MenuPanel.stone2.setVisible(true);
					});
					timer.setRepeats(false);
					timer.start();
				} else {
					stoneMode = 2; // 내가 백, 컴퓨터가 흑
					turn = 1; // 내 차례
					MenuPanel.turnLabel.setText("내 차례");
					MenuPanel.myStone.setText("내 돌: 하얀돌");
					result[9][9] = 2;
					vcCom.add(new Point(401, 402));
					
					MenuPanel.stone1.setIcon(new ImageIcon(img.white2));
					MenuPanel.stone2.setIcon(new ImageIcon(img.white2));
					MenuPanel.stone1.setVisible(true);
					MenuPanel.stone2.setVisible(true);
				}
				repaint();
				return;
			}
			MenuPanel.txt.setText("착수 금지점 ".concat(Integer.toString(dontNum-dontClick)).concat("개를 설정하세요."));
			repaint();
			return;
		}
		//여기서부터는 플레이 mode 
		Play("sound/baduk_sound.wav");  
		
		if(result[j][i]==1 || result[j][i]==2) {  //검정돌이나 하얀돌이 이미 놔져있을 때 
			JOptionPane.showConfirmDialog(null, "같은 자리에 중복해서 둘 수 없습니다.", "Warning",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		else if(result[j][i]==3) {  //착수금지점일 때 
			JOptionPane.showConfirmDialog(null, "착수 금지점에는 바둑알을 둘 수 없습니다.", "Warning",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		result[j][i] = turn;  //바둑돌 놓기 
		System.out.println((p.getX()-26)*10%417);
		System.out.println("("+j+","+i+")");
		
		if(turn == 1) {  //내 차례일때 vc에 add
			vc.add(new Point((int)x,(int)y));
			MenuPanel.stone2.setVisible(false);
		}
//		else {  //컴퓨터 차례 일때는 vcCom에 add
//			vcCom.add(new Point((int)x,(int)y));
//		}
		repaint();
		
		int gameResult = badukWin(j,i);
		if(gameResult == 1) {  //내가 이긴경우 
			MenuPanel.btnGameState.setText("다시 시작");
			gameWinNum++;
			MenuPanel.winLoseLabel.setText(Integer.toString(gameWinNum).concat("승 ").concat(Integer.toString(gameNum-
					gameWinNum)).concat("패"));
			MenuPanel.turnLabel.setText("승리");
			MenuPanel.stone1.setVisible(false);
			MenuPanel.stone2.setVisible(false);
			JOptionPane.showConfirmDialog(null, "이겼습니다 !!", "Win", JOptionPane.WARNING_MESSAGE);
			removeMouseListener(this);
			return;
		}
		else if(gameResult == 2) {  //컴퓨터가 이긴 경우 
			MenuPanel.btnGameState.setText("다시 시작");
			MenuPanel.winLoseLabel.setText(Integer.toString(gameWinNum).concat("승 ").concat(Integer.toString(gameNum-
					gameWinNum)).concat("패"));
			MenuPanel.turnLabel.setText("패배");
			MenuPanel.stone1.setVisible(false);
			MenuPanel.stone2.setVisible(false);
			JOptionPane.showConfirmDialog(null, "졌습니다 ...", "Lose", JOptionPane.WARNING_MESSAGE);
			removeMouseListener(this);
			return;
		}
		
		if(++click%2==0) {  //바둑돌 놓은 횟수 증가하고, 바둑돌 놓은 횟수가 2의 배수이면 turn 변경 
			if(turn == 1) {  //내 턴이었으면 컴퓨터 턴으로 변경 
				MenuPanel.stone1.setVisible(false);
//				turn = 2;
				MenuPanel.turnLabel.setText("컴퓨터 차례");
				 Timer timer = new Timer(2000, event -> {
						MenuPanel.turnLabel.setText("내 차례");
						MenuPanel.stone1.setVisible(true);
						MenuPanel.stone2.setVisible(true);
		            });
		            timer.setRepeats(false);
		            timer.start();
			}
//			else {  //컴퓨터 턴이었으면 내 턴으로 변경 
//				turn =1;
//				MenuPanel.turnLabel.setText("내 차례");
//				MenuPanel.stone1.setVisible(true);
//				MenuPanel.stone2.setVisible(true);
//			}
		}
//		for (double x = 26; x <= 777; x += 41.7) {
//			for (double y = 28; y <= 777; y += 41.6) {
//				if (p.getX() >= x - 10 && p.getX() <= x + 10 && p.getY() >= y - 10 && p.getY() <= y + 10) {
//					if (vc.contains(new Point((int) x, (int) y))) {
//						JOptionPane.showConfirmDialog(null, "같은 자리에 중복해서 둘 수 없습니다.", "Warning",
//								JOptionPane.WARNING_MESSAGE);
//						return;
//					}
//					System.out.println(" x: " + x + " y: " + y);
//					vc.add(new Point((int) x, (int) y));
//					result[i][j] = 1;
//					repaint();
//					System.out.println("i " + i + " j " + j);
//					if (badukWin(i, j)) {
//						JOptionPane.showConfirmDialog(null, "이김", "Win", JOptionPane.WARNING_MESSAGE);
//						return;
//					}
//				}
//				j++;
//			}
//			i++;
//			j = 0;
//		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public int badukWin(int i2, int j2) {
		
		int count = 0, count2 = 0;
		int i, j = j2;
		for (i = i2 - 1; i >= 0; i--) { // 왼쪽 오른쪽 체크
			if(i<0) break;
			if (result[i][j] == turn)
				count++;
			else if (result[i][j] != turn)
				break;
		}
		for (i = i2 + 1; i <= 18; i++) {
			if(i>18) break;
			if (result[i][j] == turn)
				count2++;
			else if (result[i][j] != turn)
				break;
		}
		if (count + count2 == 5)
			return turn;
//		System.out.println("왼쪽 "+count+" 오른쪽 "+count2);

		i = i2;
		count = 0;
		count2 = 0;
		for (j = j2 - 1; j >= 0; j--) { // 위 아래 체크
			if(j<0) break;
			if (result[i][j] == turn)
				count++;
			else if (result[i][j] != turn)
				break;
		}
		for (j = j2 + 1; j <= 18; j++) {
			if(j>18) break;
			if (result[i][j] == turn)
				count2++;
			else if (result[i][j] != turn)
				break;
		}
		if (count + count2 == 5)
			return turn;

		j = j2 - 1;
		count = 0;
		count2 = 0;
		for (i = i2 - 1; i >= 0 && j>=0; i--,j--) { // '\' 대각선 체크
			if(i<0 && j<0) break;
			if (result[i][j] == turn)
				count++;
			else if (result[i][j] != turn)
				break;
		}
		j = j2 + 1;
		for (i = i2 + 1; i <= 18 && j<=18; i++, j++) {
			if(i>18 && j>18) break;
			if (result[i][j] == turn)
				count2++;
			else if (result[i][j] != turn)
				break;
		}
		if (count + count2 == 5)
			return turn;
//		System.out.println("대각선 왼쪽 "+count+" 대각선 오른쪽 "+count2);

		count = 0;
		count2 = 0;
		j = j2 - 1;
		for (i = i2 + 1; i <= 18 && j >= 0; i++, j--) { // '/' 대각선 체크
			if(i>18 && j<0) break;
			if (result[i][j] == turn)
				count++;
			else if (result[i][j] != turn)
				break;
		}
		j = j2 + 1;
		for (i = i2 - 1; i >= 0 && j <= 18; i--, j++) {
			if(i<0 && j>18) break;
			if (result[i][j] == turn)
				count2++;
			else if (result[i][j] != turn)
				break;
		}
		if (count + count2 == 5)
			return turn;
//		System.out.println("대각선 오른쪽 " + count + " 대각선 왼쪽 " + count2);

		return 0;
	}
	
	public void Play(String fileName) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File(fileName));
			Clip clip = AudioSystem.getClip();
			clip.stop();
			clip.open(ais);
			clip.start();
		} catch (Exception ex) {
		}
	}
}
