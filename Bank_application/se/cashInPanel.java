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

class cashInPanel extends JPanel {
	
	String input = "";
	
	ImageIcon cashoutImage = new ImageIcon(".\\Images\\Cashin\\cashInimage.jpg");
	JLabel imageLabel = new JLabel();
	
	JLabel cashinLabel = new JLabel("Choose the amount of money you want to put in.");

	JButton CashingIn = new JButton("±Ý   ¾×   ¼³   Á¤");
	JPanel mixedPanel = new JPanel(); // cashOutPanelÀÇ °¡Àå ¿Ü°û ÆÐ³Î
	JPanel C_InWarningPanel = new JPanel(); // Ãâ±ÝÆÐ³Î °æ°í ¸Þ¼¼Áö ÆÐ³Î_°¡¿îµ¥
	JPanel LCashoutPanel = new JPanel(); // Ãâ±Ý ±Ý¾× ¼±ÅÃ ÆÐ³Î_¿ÞÂÊ
	JPanel RCashoutPanel = new JPanel(); // Ãâ±Ý ±Ý¾× ¼±ÅÃ ÆÐ³Î_¿À¸¥ÂÊ
	JPanel C_INPanel = new JPanel(); // ±Ý¾× Ç¥½Ã ¹× Ãâ±Ý¹öÆ° ÆÐ³Î
	
	protected JTextField CashIn_Field;

	//DefaultListModel payModel = new DefaultListModel();

	int result = 0;
	JLabel resultLabel = new JLabel("ÀÔ±Ý ±Ý¾× : " + result + " ¿ø");
	JButton C_INBtn = new JButton("Cash In"); // Ãâ±Ý ¹öÆ°
	JButton cancelBtn = new JButton("Cancel"); // Ãë¼Ò ¹öÆ°
	JButton backBtn = new JButton("µÚ·Î°¡±â"); //µÚ·Î°¡±â ¹öÆ°

	Border border = BorderFactory.createTitledBorder(""); // Å×µÎ¸® Ãß°¡

	cashInPanel() {
		setLayout(new BorderLayout());
		cashinLabel.setHorizontalAlignment(JLabel.CENTER); // ¶óº§ Áß¾ÓÁ¤·Ä
		add(cashinLabel, BorderLayout.NORTH); //
		add(mixedPanel, BorderLayout.CENTER);

		C_InWarningPanel.setBorder(border);

		mixedPanel.setLayout(new BorderLayout()); // ¾Æ·§ºÎºÐ ÆÐ³ÎÀ» 3¿­·Î Á¶Á¤
		mixedPanel.add(C_InWarningPanel, BorderLayout.CENTER);
		mixedPanel.add(LCashoutPanel, BorderLayout.WEST);
		mixedPanel.add(RCashoutPanel, BorderLayout.EAST);
		mixedPanel.add(C_INPanel, BorderLayout.SOUTH);
		
		C_InWarningPanel.add(imageLabel);
		imageLabel.setIcon(cashoutImage);

		// (±Ý¾×Ç¥½Ã + Ãâ±Ý¹öÆ° ÆÐ³Î)
		C_INPanel.setLayout(new GridLayout(3, 0, 5, 5));
		resultLabel.setHorizontalAlignment(JLabel.CENTER); // ¶óº§ Áß¾ÓÁ¤·Ä	
		C_INPanel.add(resultLabel); // Ãâ±Ý ±Ý¾× Ç¥½Ã ·¹ÀÌºí
		C_INPanel.add(C_INBtn); // Ãâ±Ý ¹öÆ°
		C_INPanel.add(backBtn); // µÚ·Î°¡±â ¹öÆ°
		
		C_INBtn.addMouseListener(new CashInClicked()); // CashOut¹öÆ° Å¬¸¯ ÇÒ¶§ ¹ÝÀÀ

		LCashoutPanel.setLayout(new GridLayout(6, 0, 5, 5)); // ¼¼·Î 6Ä­ °¡·Î´Â ÀÏ·Ä·Î    //¿©±â±îÁö ¼öÁ¤ÇßÀ½
																// ÁöÁ¤ÇØÁÖ±â À§ÇØ 0À¸·Î																



		RCashoutPanel.setLayout(new GridLayout(6, 0, 5, 5)); // Ãâ±Ý_¿À¸¥ÂÊ

		RCashoutPanel.add(CashingIn);
		
		CashingIn.addMouseListener(new Clicked());
	}

	private class Clicked extends MouseAdapter {
		public void mouseClicked(MouseEvent c) {
			System.out.println("±Ý¾× ÀÔ±Ý ±Ý¾× ¼³Á¤µÊ");		
			
			if(c.getSource() == CashingIn){
				if(SE.smartState == true){
					input = JOptionPane.showInputDialog(mixedPanel, "±Ý¾×À» ÀÔ·ÂÇÏ¼¼¿ä", "¿¹) 100000"); // °ýÈ£ ¾ÕÂÊ¿¡ mixedPanelÀ» ³ÖÀ½À¸·Î½á ÆÐ³ÎÀÇ °¡¿îµ¥¿¡ ´ëÈ­»óÀÚ »ý¼º
					System.out.println("\"" + input + "\"" + "ÀÇ ±Ý¾×À» ÀÔ·ÂÇÏ¿´½À´Ï´Ù.");
					result = Integer.parseInt(input);
					resultLabel.setText("ÀÔ±Ý ±Ý¾× : " + result + " ¿ø");
					
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
	
	private class CashInClicked extends MouseAdapter{ //CashOut ¹öÆ°
		public void mouseClicked(MouseEvent e) {
			if(e.getSource() == C_INBtn){
				
				if(result == 0){ //°áÀçÇÒ ±Ý¾× ¾øÀ½, #Âü°í 3
					JOptionPane.showMessageDialog(mixedPanel, "±Ý¾×À» ¼³Á¤ÇÏ½Ê½Ã¿À!", "ÀÔ±Ý ¿À·ù", JOptionPane.WARNING_MESSAGE);
				}
				else{					
					if(SE.smartInterworkState){ //¿¬µ¿ ÁßÀÌ¸é ½º¸¶Æ®ÆùÀ¸·Î °è»ê
						if(SE.banking(SmartPhonePanel.userAccountNumber.getText(), "ATM", result, '+', true)){ //°Å·¡ ÁøÇà , ÀÔ±ÝÀ» ÇÒ½Ã¿£ '-'¸¦ +·Î ¹Ù²Ù¸é µÈ´Ù.
							System.out.println("°Å·¡ ¿Ï·á!" + result + "ÀÔ±Ý µÇ¾ú½À´Ï´Ù."); 
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
								
								if(SE.banking(SmartPhonePanel.userAccountNumber.getText(), "ATM", result, '+', true)){ //°Å·¡ ÁøÇà
								}
							}
						}
					}
				}
			}
		}
	}
}