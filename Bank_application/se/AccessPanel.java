package se;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

class AccessPanel extends JPanel{
	JLabel smartPhoneLabel = new JLabel("Android");
	JPanel labelPanel = new JPanel();
	JLabel idLabel = new JLabel("ID");
	JLabel passwdLabel = new JLabel("passwd");
	
	JPanel totalPanel = new JPanel(); 
	JPanel fieldPanel = new JPanel();
	
	JTextField idField = new JTextField(8);
	JPasswordField passwdField = new JPasswordField(8);
	JButton accessBtn = new JButton("접속"); //connect
	
	Border border = BorderFactory.createTitledBorder("");
	
	AccessPanel(CardLayout cardLayout, Container c){
		setSize(300, 500);
		setLayout(new BorderLayout());
		
		smartPhoneLabel.setHorizontalAlignment(JLabel.CENTER); //Label central arry
		totalPanel.setLayout(new GridLayout(0,2));
		totalPanel.setBorder(border);
		for(int i =0; i<8;i++){
			totalPanel.add(new JPanel()); 
		}
		totalPanel.add(idLabel);
		totalPanel.add(idField);
		totalPanel.add(passwdLabel);
		totalPanel.add(passwdField);
		for(int i =0; i<14;i++){
			totalPanel.add(new JPanel()); 
		}
		
		//Access Button
		accessBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) { //connect Button event
				if(e.getSource() == accessBtn){
					String str = SE.checkUserInfo(idField.getText(), passwdField.getText()); //Check the cutomer Info
					if(str == null){ //Fail to connect
						passwdField.setText(""); 
						JOptionPane.showMessageDialog(SE.spf, "아이디 또는 비밀번호를 다시 입력해 주세요", "로그인 오류", JOptionPane.WARNING_MESSAGE);
					}
					else{ //success to connect
						idField.setText(""); 
						passwdField.setText(""); 
					
						SE.smartLoginState(true); //Login
						SmartPhonePanel.initUserInfo(str); //Bring the cutomer Info
						cardLayout.show(c, "smartPhone"); //Change page to Smart phone pange
					}
				}
			}
		}); 
		
		add(smartPhoneLabel,BorderLayout.NORTH);
		add(totalPanel,BorderLayout.CENTER);
		add(accessBtn,BorderLayout.SOUTH);
		
		setVisible(true);
	}
}
