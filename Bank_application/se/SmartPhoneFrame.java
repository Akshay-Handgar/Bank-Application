package se;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.Border;


class SmartPhoneFrame extends JFrame{ 
	CardLayout cardLayout = new CardLayout(10, 10);
	Container c; 
	
	SmartPhoneFrame(){ 
		c = getContentPane();
		c.setLayout(cardLayout);
		
		c.add("access", new AccessPanel(cardLayout, c)); 
		c.add("smartPhone", new SmartPhonePanel(cardLayout, c)); 
		
		cardLayout.show(getContentPane(), "access"); 
		
		SE.smartState(true); 
		SE.mf.openSmart.setState(true); 
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){ 
				closeSmart();
			}
		});
	}
	
	void closeSmart(){ 
		SE.smartState(false); 
		SE.smartLoginState(false); 
		SE.smartInterworkState(false); 
		SE.mf.openSmart.setState(false); 
		dispose(); 
	}
}
