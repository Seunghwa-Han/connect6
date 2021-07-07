package edu.handong.csee.java.connect;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class Util {
	
	public static void comWhiteFirst(ArrayList<Point> vcCom, ArrayList<Point> dont) {
		
		Random rnd = new Random();
		int firstX=0,firstY=0;
		int secondX=0,secondY=0;
		
		while(true) {
			firstX = rnd.nextInt(3)+8;
			firstY = rnd.nextInt(3)+8;
			int check =0;
			for(int i=0; i<dont.size(); i++) {
				if(dont.get(i).x == (double)firstX*41.7+26 && dont.get(i).y== firstY*41.6+28)
					check = 1;
			}
			if(check !=1 && !(firstX==9 && firstY==9))
				break;
		}
		
		while(true) {
			int rand = rnd.nextInt(3);
			if(rand == 0) {
				secondX = firstX+1;
			}else if(rand == 1) {
				secondX = firstX-1;
			}
			else if(rand == 2) {
				secondX = firstX;
			}
			if(secondX>=8 && secondX<=10)
				break;
		}
		
		while(true) {
			int rand = rnd.nextInt(3);
			if(rand == 0) {
				secondY = firstY+1;
			}else if(rand == 1) {
				secondY = firstY-1;
			}
			else if(rand == 2) {
				secondY = firstY;
			}
			if(secondY>=8 && secondY<=10 && !(secondX==9 &&secondY==9) && !(secondX==firstX &&secondY==firstY))
				break;
		}
		System.out.println(firstX +" "+firstY+"        "+secondX+" "+secondY);
		vcCom.add(new Point((int)(firstX*41.7+26), (int)(firstY*41.6+28)));
		vcCom.add(new Point((int)(secondX*41.7+26), (int)(secondY*41.6+28)));
	}
	
	public static void comBlackFirst(ArrayList<Point> vcCom, ArrayList<Point> dont) {
		
	}
}
