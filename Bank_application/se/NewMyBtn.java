package se;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.border.BevelBorder;

public class NewMyBtn {

	NewMyBtn(){
		
	}
	void myBtn(JButton Btn){
		Btn.setBackground(Color.LIGHT_GRAY);
		Btn.setBorder(new BevelBorder(BevelBorder.RAISED));
		Btn.setForeground(Color.BLACK); //¿ä°Ç ±Û¾¾»ö
		
	}
}
