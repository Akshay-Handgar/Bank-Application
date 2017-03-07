package se;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.Border;


class atmPanel extends JPanel { // MainFrame 
	JLabel welcomeLabel = new JLabel("Welcome to National Korean Bank [ ATM ]");
	
	ImageIcon main_gif = new ImageIcon(".\\Images\\MainPageImg\\main_frame_gif.gif");
	
	JButton cashOut = new JButton("Cash Out");	//withdraw
	JButton cashIn = new JButton("Cash In");	//deposit	
	JButton cashMove = new JButton("Cash Transfer");	
	JButton useCash = new JButton("Usage");	/
	JButton checkCash = new JButton("checkCash");	
	
	JPanel mixedPanel = new JPanel(); 
	JPanel funcPanel = new JPanel(); 
	JPanel imagePanel = new JPanel(); 
	JPanel crtAccountPanel = new JPanel(); 
	
	Border border = BorderFactory.createTitledBorder(""); 
	JLabel imageLabel = new JLabel();
	
	JButton createAccountBtn = new JButton("createAccount"); 
	

	
	createAccountPanel cap = null;
	atmPanel() {   //panel design
		setLayout(new BorderLayout());
		welcomeLabel.setHorizontalAlignment(JLabel.CENTER); 
		add(welcomeLabel,BorderLayout.NORTH);
		add(mixedPanel,BorderLayout.CENTER);
		mixedPanel.setLayout(new BorderLayout()); 
		mixedPanel.add(funcPanel,BorderLayout.WEST);
		mixedPanel.add(imagePanel,BorderLayout.CENTER);
		mixedPanel.add(crtAccountPanel,BorderLayout.EAST);
		
		imagePanel.add(imageLabel);
		imageLabel.setIcon(main_gif);
		
		
		/*
		imagePanel = new JPanel(){
			{setOpaque(false);}
			public void paintComponent(Graphics g){
				g.drawImage(main_gif,0,0,this);
				super.paintComponent(g);
			}
		};*/
		
		funcPanel.setLayout(new GridLayout(8,0));
		funcPanel.add(cashIn);
		funcPanel.add(cashOut);
		funcPanel.add(cashMove);
		funcPanel.add(useCash);
		funcPanel.add(checkCash);
		
		//imagePanel 
		
		crtAccountPanel.setLayout(new GridLayout(8,0));
		crtAccountPanel.add(createAccountBtn);
		
		checkCash.addMouseListener(new checkCashClicked());
		
	}
	
	private class checkCashClicked extends MouseAdapter { 
		public void mouseClicked(MouseEvent c) {
			if(c.getSource() == checkCash){
				if(SE.smartInterworkState){ 
					JOptionPane.showMessageDialog(mixedPanel, "잔액은 " + SE.getAccountBalance(SmartPhonePanel.userAccountNumber.getText()) + "원 입니다");
				}
				else{ 
					Object[] options = {"연동하기", "계좌", "취소"};
					int select = JOptionPane.showOptionDialog(mixedPanel, 
						"연동하시겠습니까?", "결제 수단 선택", 
						JOptionPane.YES_NO_CANCEL_OPTION, 
						JOptionPane.QUESTION_MESSAGE, 
						null, options, options[2]);
					
					if(select == 0){
						if(SE.smartState == false){ 
								JOptionPane.showMessageDialog(mixedPanel, "스마트폰을 켜주세요", "스마트폰 오류", JOptionPane.WARNING_MESSAGE);
						}
						else if(SE.smartLoginState == false){ 
							JOptionPane.showMessageDialog(mixedPanel, "로그인 해주세요", "로그인 오류", JOptionPane.WARNING_MESSAGE);
						}
						else{ 
							SE.smartInterworkState(true); 
							
							JOptionPane.showMessageDialog(mixedPanel, "잔액은 " + SE.getAccountBalance(SmartPhonePanel.userAccountNumber.getText())+"원 입니다");
						}
					}
					else if(select == 1){ 
						String id = JOptionPane.showInputDialog(mixedPanel, "ID를 입력해주세요.", "계좌 입력", JOptionPane.QUESTION_MESSAGE);
						
						if(id != null){ 
							JPasswordField passwd = new JPasswordField();
							int ok = JOptionPane.showConfirmDialog(mixedPanel, passwd, "Enter Password", JOptionPane.WARNING_MESSAGE);
							
							if(ok == JOptionPane.OK_OPTION){ 
								String user = SE.checkUserInfo(id, passwd.getText());
								
								if(user == null){ 
									JOptionPane.showMessageDialog(mixedPanel, "아이디 또는 비밀번호를 다시 입력해 주세요", "로그인 오류", JOptionPane.WARNING_MESSAGE);
								}
								else{ 
									JOptionPane.showMessageDialog(mixedPanel, "잔액은 " + SE.getAccountBalance(user)+"원 입니다");
								}
							}
						}
					}
					else if(select == 2){ } //cancle
				}
			}
		}
	}
}
