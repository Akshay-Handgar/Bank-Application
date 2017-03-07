package se;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Btn_UI {
	ImageIcon black_basic = new ImageIcon("black_basic.png");
	ImageIcon black_pressed = new ImageIcon("black_pressed.png");
	ImageIcon green_basic = new ImageIcon("green_basic.png");
	ImageIcon green_pressed = new ImageIcon("green_pressed.png");
	ImageIcon red_basic = new ImageIcon("red_basic.png");
	ImageIcon red_pressed = new ImageIcon("red_pressed.png");
	
	public Btn_UI(){ 
		
	}
	void myBtn(JButton Btn,String str,String btn_Color){ 
		Image resizeImage_basic;
		Image resizeImage_pressed;
		switch(btn_Color){
		case "black":
			resizeImage_basic = black_basic.getImage();
			resizeImage_pressed = black_pressed.getImage();
			break;
		case "green":
			resizeImage_basic = green_basic.getImage();
			resizeImage_pressed = green_pressed.getImage();
			break;
		case "red":
			resizeImage_basic = red_basic.getImage();
			resizeImage_pressed = red_pressed.getImage();
			break;
		default:
			resizeImage_basic = black_basic.getImage();
			resizeImage_pressed = black_pressed.getImage();
		}
		Btn.setIcon(black_basic);
		Dimension maxDms = new Dimension(300,30);
		Dimension minDms = new Dimension(100,20);
		Btn.setMaximumSize(maxDms);
		Btn.setMinimumSize(minDms);
		
		//button design
		Btn.setBorderPainted(false); 
		Btn.setFocusPainted(false); 
		Btn.setContentAreaFilled(false); 
		Btn.setText(str);
		System.out.println(Btn.getMaximumSize()+"||||||");
		//Btn.size
		Btn.setHorizontalTextPosition(Btn.CENTER); //Central array of Text
		//System.out.println("width"+width+"|||height"+height); //button distance
	}
}
