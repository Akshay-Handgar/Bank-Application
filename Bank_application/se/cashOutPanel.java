package se;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;			// ÀÔ·Â µ¥ÀÌÅÍÀÇ ÇüÅÂ ½Çº°
import java.util.regex.*;	// ÀÔ·Â µ¥ÀÌÅÍÀÇ ÇüÅÂ ½Äº°

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.JOptionPane;

class cashOutPanel extends JPanel {
	
	String input = "";
	
	JLabel cashoutLabel = new JLabel("Choose the amount of money you want to take out.");

	ImageIcon cashoutImage = new ImageIcon(".\\Images\\Cashout\\cashoutImage.jpg");
	JLabel imageLabel = new JLabel();
	
	JButton ThirtyTW = new JButton("30000       £Ü");
	JButton FiftyTW = new JButton("50000       £Ü");
	JButton HTW = new JButton("100000     £Ü");
	JButton HFiftyTW = new JButton("150000     £Ü");
	JButton TwoHTW = new JButton("200000     £Ü");
	JButton ThreeHTW = new JButton("300000     £Ü");

	JButton FourHTW = new JButton("400000  £Ü");
	JButton FiveHTW = new JButton("500000  £Ü");
	JButton SixHTW = new JButton("600000   £Ü");
	JButton SevenHTW = new JButton("700000   £Ü");
	JButton MW = new JButton("1000000  £Ü");
	JButton ETC = new JButton("±â               Å¸");

	JPanel mixedPanel = new JPanel(); // cashOutPanelÀÇ °¡Àå ¿Ü°û ÆÐ³Î
	JPanel C_OutWarningPanel = new JPanel(); // Ãâ±ÝÆÐ³Î °æ°í ¸Þ¼¼Áö ÆÐ³Î_°¡¿îµ¥
	JPanel LCashoutPanel = new JPanel(); // Ãâ±Ý ±Ý¾× ¼±ÅÃ ÆÐ³Î_¿ÞÂÊ
	JPanel RCashoutPanel = new JPanel(); // Ãâ±Ý ±Ý¾× ¼±ÅÃ ÆÐ³Î_¿À¸¥ÂÊ
	JPanel C_OUTPanel = new JPanel(); // ±Ý¾× Ç¥½Ã ¹× Ãâ±Ý¹öÆ° ÆÐ³Î
	
	protected JTextField ETC_Field;

	DefaultListModel payModel = new DefaultListModel();

	int result = 0;
	JLabel resultLabel = new JLabel("Ãâ±Ý ±Ý¾× : " + result + " ¿ø");
	JButton C_OUTBtn = new JButton("Cash Out"); // Ãâ±Ý ¹öÆ°
	JButton cancelBtn = new JButton("Cancel"); // Ãë¼Ò ¹öÆ°
	JButton backBtn = new JButton("µÚ·Î°¡±â"); //µÚ·Î°¡±â ¹öÆ°
	
	Border border = BorderFactory.createTitledBorder(""); // Å×µÎ¸® Ãß°¡

	cashOutPanel() {
		setLayout(new BorderLayout());
		cashoutLabel.setHorizontalAlignment(JLabel.CENTER); // ¶óº§ Áß¾ÓÁ¤·Ä
		add(cashoutLabel, BorderLayout.NORTH); //
		add(mixedPanel, BorderLayout.CENTER);

		mixedPanel.setLayout(new BorderLayout()); // ¾Æ·§ºÎºÐ ÆÐ³ÎÀ» 3¿­·Î Á¶Á¤
		mixedPanel.add(C_OutWarningPanel, BorderLayout.CENTER);
		mixedPanel.add(LCashoutPanel, BorderLayout.WEST);
		mixedPanel.add(RCashoutPanel, BorderLayout.EAST);
		mixedPanel.add(C_OUTPanel, BorderLayout.SOUTH);
		
		C_OutWarningPanel.setBorder(border);
		C_OutWarningPanel.add(imageLabel);
		imageLabel.setIcon(cashoutImage);

		// (±Ý¾×Ç¥½Ã + Ãâ±Ý¹öÆ° ÆÐ³Î)
		C_OUTPanel.setLayout(new GridLayout(3, 0, 5, 5));
		resultLabel.setHorizontalAlignment(JLabel.CENTER); // ¶óº§ Áß¾ÓÁ¤·Ä	
		C_OUTPanel.add(resultLabel, BorderLayout.NORTH); // Ãâ±Ý ±Ý¾× Ç¥½Ã ·¹ÀÌºí
		C_OUTPanel.add(C_OUTBtn); // Ãâ±Ý ¹öÆ°
		C_OUTPanel.add(backBtn); // µÚ·Î°¡±â ¹öÆ°
		
		

		C_OUTBtn.addMouseListener(new CashOutClicked()); // CashOut¹öÆ° Å¬¸¯ ÇÒ¶§ ¹ÝÀÀ

		LCashoutPanel.setLayout(new GridLayout(6, 0, 5, 5)); // ¼¼·Î 6Ä­ °¡·Î´Â ÀÏ·Ä·Î
																// ÁöÁ¤ÇØÁÖ±â À§ÇØ 0À¸·Î
																// ¼³Á¤_Ãâ±Ý ¿ÞÂÊ
		LCashoutPanel.add(ThirtyTW);
		LCashoutPanel.add(FiftyTW);
		LCashoutPanel.add(HTW);
		LCashoutPanel.add(HFiftyTW);
		LCashoutPanel.add(TwoHTW);
		LCashoutPanel.add(ThreeHTW);

		ThirtyTW.addMouseListener(new Clicked());
		FiftyTW.addMouseListener(new Clicked());
		HTW.addMouseListener(new Clicked());
		HFiftyTW.addMouseListener(new Clicked());
		TwoHTW.addMouseListener(new Clicked());
		ThreeHTW.addMouseListener(new Clicked());

		RCashoutPanel.setLayout(new GridLayout(6, 0, 5, 5)); // Ãâ±Ý_¿À¸¥ÂÊ
		RCashoutPanel.add(FourHTW);
		RCashoutPanel.add(FiveHTW);
		RCashoutPanel.add(SixHTW);
		RCashoutPanel.add(SevenHTW);
		RCashoutPanel.add(MW);
		RCashoutPanel.add(ETC);
		
		FourHTW.addMouseListener(new Clicked());
		FiveHTW.addMouseListener(new Clicked());
		SixHTW.addMouseListener(new Clicked());
		SevenHTW.addMouseListener(new Clicked());
		MW.addMouseListener(new Clicked());
		ETC.addMouseListener(new Clicked());
	}

	private class Clicked extends MouseAdapter {
		public void mouseClicked(MouseEvent c) {
			System.out.println("±Ý¾× ¼±ÅÃµÊ");
			if(c.getSource() == ThirtyTW){
				if(SE.smartState == true){
					result = 30000;
					resultLabel.setText("Ãâ±Ý ±Ý¾× : " + result + " ¿ø");}}
			else if(c.getSource() == FiftyTW){
				if(SE.smartState == true){
					result = 50000;
					resultLabel.setText("Ãâ±Ý ±Ý¾× : " + result + " ¿ø");}}
			else if(c.getSource() == HTW){
				if(SE.smartState == true){
					result = 100000;
					resultLabel.setText("Ãâ±Ý ±Ý¾× : " + result + " ¿ø");}}
			else if(c.getSource() == HFiftyTW){
				if(SE.smartState == true){
					result = 150000;
					resultLabel.setText("Ãâ±Ý ±Ý¾× : " + result + " ¿ø");}}
			else if(c.getSource() == TwoHTW){
				if(SE.smartState == true){
					result = 200000;
					resultLabel.setText("Ãâ±Ý ±Ý¾× : " + result + " ¿ø");}}
			else if(c.getSource() == ThreeHTW){
				if(SE.smartState == true){
					result = 300000;
					resultLabel.setText("Ãâ±Ý ±Ý¾× : " + result + " ¿ø");}}
			
			if(c.getSource() == FourHTW){
				if(SE.smartState == true){
					result = 400000;
					resultLabel.setText("Ãâ±Ý ±Ý¾× : " + result + " ¿ø");}}
			else if(c.getSource() == FiveHTW){
				if(SE.smartState == true){
					result = 500000;
					resultLabel.setText("Ãâ±Ý ±Ý¾× : " + result + " ¿ø");}}
			else if(c.getSource() == SixHTW){
				if(SE.smartState == true){
					result = 600000;
					resultLabel.setText("Ãâ±Ý ±Ý¾× : " + result + " ¿ø");}}
			else if(c.getSource() == SevenHTW){
				if(SE.smartState == true){
					result = 700000;
					resultLabel.setText("Ãâ±Ý ±Ý¾× : " + result + " ¿ø");}}
			else if(c.getSource() == MW){
				if(SE.smartState == true){
					result = 1000000;
					resultLabel.setText("Ãâ±Ý ±Ý¾× : " + result + " ¿ø");}}
			
			else if(c.getSource() == ETC){
				if(SE.smartState == true){
					input = JOptionPane.showInputDialog(mixedPanel, "±Ý¾×À» ÀÔ·ÂÇÏ¼¼¿ä", "¿¹) 100000"); // °ýÈ£ ¾ÕÂÊ¿¡ mixedPanelÀ» ³ÖÀ½À¸·Î½á ÆÐ³ÎÀÇ °¡¿îµ¥¿¡ ´ëÈ­»óÀÚ »ý¼º
					System.out.println("\"" + input + "\"" + "ÀÇ ±Ý¾×À» ÀÔ·ÂÇÏ¿´½À´Ï´Ù.");
					result = Integer.parseInt(input);
					resultLabel.setText("Ãâ±Ý ±Ý¾× : " + result + " ¿ø");
					
					/*   if (Pattern.matches("[0-9]", input)){
						    System.out.printf("¼ýÀÚ");
							resultLabel.setText("Ãâ±Ý ±Ý¾× : " + result + " ¿ø");    
					   }
						   else if (Pattern.matches("[A-Z]",input))
						    System.out.printf("´ë¹®ÀÚ");
						   else if (Pattern.matches("[a-z]", input))
						    System.out.printf("¼Ò¹®ÀÚ");
						   else if (Pattern.matches("[°¡-ÆR]", input))
						    System.out.println("ÇÑ±Û");
						   else
						    System.out.printf("±âÅ¸");*/
				}
				
			}
		}
	}
	
	private class CashOutClicked extends MouseAdapter{ //CashOut ¹öÆ°
		public void mouseClicked(MouseEvent e) {
			if(e.getSource() == C_OUTBtn){
				
				if(result == 0){ //°áÀçÇÒ ±Ý¾× ¾øÀ½, #Âü°í 3
					JOptionPane.showMessageDialog(mixedPanel, "±Ý¾×À» ¼³Á¤ÇÏ½Ê½Ã¿À!", "Ãâ±Ý ¿À·ù", JOptionPane.WARNING_MESSAGE);
				}
				else{					
					if(SE.smartInterworkState){ //¿¬µ¿ ÁßÀÌ¸é ½º¸¶Æ®ÆùÀ¸·Î °è»ê
						if(SE.banking(SmartPhonePanel.userAccountNumber.getText(), "ATM", result, '-', true)){ //°Å·¡ ÁøÇà , ÀÔ±ÝÀ» ÇÒ½Ã¿£ '-'¸¦ +·Î ¹Ù²Ù¸é µÈ´Ù.
							System.out.println("°Å·¡ ¿Ï·á!" + result + "Ãâ±Ý µÇ¾ú½À´Ï´Ù."); 
						} // ¹Ì¿Ï·á½Ã false
					}
					else{ //¿¬µ¿ ¾ÈÇÔ -> ¿¬µ¿ÇÏ±â or Ãë¼Ò
						Object[] options = {"¿¬µ¿ÇÏ±â", "Ãë¼Ò"};
						int select = JOptionPane.showOptionDialog(mixedPanel, 
								"¿¬µ¿ÇÏ½Ã°Ú½À´Ï±î?", "°áÁ¦ ¿À·ù", 
								JOptionPane.YES_NO_CANCEL_OPTION, 
								JOptionPane.QUESTION_MESSAGE, 
								null, options, options[1]);
						
						if(select == 1){ } //Ãë¼Ò
						else{ //¿¬µ¿ÇÏ±â
							if(SE.smartState == false){ //½º¸¶Æ®Æù ¾ÈÄÑÁ®ÀÖÀ½
								JOptionPane.showMessageDialog(mixedPanel, "½º¸¶Æ®ÆùÀ» ÄÑÁÖ¼¼¿ä", "½º¸¶Æ®Æù ¿À·ù", JOptionPane.WARNING_MESSAGE);
							}
							else if(SE.smartLoginState == false){ //·Î±×ÀÎ ¾ÈµÇÀÖÀ½
								JOptionPane.showMessageDialog(mixedPanel, "·Î±×ÀÎ ÇØÁÖ¼¼¿ä", "·Î±×ÀÎ ¿À·ù", JOptionPane.WARNING_MESSAGE);
							}
							else{ //¿¬µ¿ÇÏ±â
								SE.smartInterworkState(true); //¿¬µ¿ÇÏ±â
								
								if(SE.banking(SmartPhonePanel.userAccountNumber.getText(), "ATM", result, '-', true)){ //°Å·¡ ÁøÇà
								}
							}
						}
					}
				}
			}
		}
	}
}