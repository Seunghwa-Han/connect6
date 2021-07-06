package edu.handong.csee.java.connect.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ConnectImage {
	public BufferedImage white;
	public BufferedImage black;
	public BufferedImage board;
	public BufferedImage white2;
	public BufferedImage black2;
	
			
	public ConnectImage() {
		try {
		    File img = new File("./img/white.png");
		    File img2 = new File("./img/black.png");
		    File img3 = new File("./img/checkerboard.png");
		    File img4 = new File("./img/black2.png");
		    File img5 = new File("./img/white2.png");
		   
		    white = ImageIO.read(img); 
		    black = ImageIO.read(img2);
		    board = ImageIO.read(img3);
		    black2 = ImageIO.read(img4);
		    white2 = ImageIO.read(img5);
		   
		} catch (IOException e) { 
		    e.printStackTrace(); 
		}
	}
}
