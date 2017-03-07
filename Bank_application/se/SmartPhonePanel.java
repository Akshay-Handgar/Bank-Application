package se;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

class SmartPhonePanel extends JPanel { //Smart phone Access Frame. 
	JLabel smartPhoneLabel = new JLabel("Android");
	JPanel totalPanel = new JPanel(); //Balance, Account, Benefit
	JPanel pictogramPanel = new JPanel();
	
	JPanel balancePanel = new JPanel(); 
	JPanel accountNumberPanel = new JPanel(); 
	JPanel benefitPanel = new JPanel(); 
	
	JButton myBenefitBtn = new JButton("내 혜택 보기");
	JButton bankBtn = new JButton("은행");
	JButton homeBtn = new JButton("HOME");
	static JButton interworkBtn = new JButton("연동하기"); 
	
	Border balanceBorder = BorderFactory.createTitledBorder("잔액");
	Border acoountBorder = BorderFactory.createTitledBorder("계좌");
	Border benefitBorder = BorderFactory.createTitledBorder("이 달의 혜택");
	
	static JLabel userName = new JLabel("고객 명"); 
	static JLabel userAccountNumber = new JLabel("계좌 번호"); 
	static JLabel balanceLabel = new JLabel("잔액"); 
	static JLabel benefit = new JLabel("혜택");
	
	public SmartPhonePanel(CardLayout cardLayout, Container c){
		setLayout(new BorderLayout());
		add(smartPhoneLabel,BorderLayout.NORTH);
		smartPhoneLabel.setHorizontalAlignment(JLabel.CENTER); //라벨 중앙정렬
		add(totalPanel,BorderLayout.CENTER);
		add(pictogramPanel,BorderLayout.SOUTH);
		
		totalPanel.setLayout(new GridLayout(3,0));
		totalPanel.add(balancePanel);
		totalPanel.add(accountNumberPanel);
		totalPanel.add(benefitPanel);
		
		balancePanel.setBorder(balanceBorder);
		accountNumberPanel.setBorder(acoountBorder);
		benefitPanel.setBorder(benefitBorder);
		
		balancePanel.add(balanceLabel);
		accountNumberPanel.add(userAccountNumber);
		benefitPanel.add(benefit);
		benefitPanel.add(myBenefitBtn);
		
		myBenefitBtn.addActionListener(new ActionListener(){ //내 혜택 버튼
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == myBenefitBtn){ //로그인 끊기, 고객 정보 초기화, 홈화면으로
					String account = userAccountNumber.getText();
					String str[] = {"할인 없음", "음식 할인", "운동 할인", "영화 할인", "놀이 할인"}; //Kind of benefit
					int benefit = SE.getAccountBenefit(account);
					
					JOptionPane.showMessageDialog(benefitPanel, str[benefit], "내 혜택", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		homeBtn.addActionListener(new ActionListener(){ //Home button
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == homeBtn){
					SE.smartLoginState(false); 
					cardLayout.show(c, "access"); 
				}
			}
		});
		
		interworkBtn.addActionListener(new ActionListener(){ //interlock
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == interworkBtn){
					if(SE.smartInterworkState == false){ 
						SE.smartInterworkState(true);
					}
					else{ 
						SE.smartInterworkState(false);
					}
				}
			}
		});
		
		pictogramPanel.add(bankBtn);
		pictogramPanel.add(homeBtn);
		pictogramPanel.add(interworkBtn);
	}
	
	static void initUserInfo(String account){ //if login status, bring the customer data 
		userName.setText(SE.getAccountName(account));
		userAccountNumber.setText(account);
		balanceLabel.setText(SE.getAccountBalance(account) + " 원");
		
		String str[] = {"혜택 없음", "음식!", "운동!", "영화!", "놀이공원!"};
		benefit.setText(str[SE.monthBenefit]);
	}
	
	static void delUserInfo(){ //logout
		userName.setText("고객 정보 없음");
		userAccountNumber.setText("고객 정보 없음");
		balanceLabel.setText("고객 정보 없음");
		benefit.setText("고객 정보 없음");
	}

	static void setBalance(int balance){ //update balance
		balanceLabel.setText(balance + " 원");
	}
}


