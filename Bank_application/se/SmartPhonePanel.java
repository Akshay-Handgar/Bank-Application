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

class SmartPhonePanel extends JPanel { //스마트폰 접속 프레임, 개인 정보와 혜택 등을 볼 수 있다.
	JLabel smartPhoneLabel = new JLabel("Android");
	JPanel totalPanel = new JPanel(); //잔액,계좌번호,혜택
	JPanel pictogramPanel = new JPanel(); //픽토그램
	
	JPanel balancePanel = new JPanel(); //잔액
	JPanel accountNumberPanel = new JPanel(); //계좌번호
	JPanel benefitPanel = new JPanel(); //혜택
	
	JButton myBenefitBtn = new JButton("내 혜택 보기");
	JButton bankBtn = new JButton("은행");
	JButton homeBtn = new JButton("HOME");
	static JButton interworkBtn = new JButton("연동하기"); //SE에서 사용
	
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
					String str[] = {"할인 없음", "음식 할인", "운동 할인", "영화 할인", "놀이 할인"}; //할인 종류
					int benefit = SE.getAccountBenefit(account);
					
					JOptionPane.showMessageDialog(benefitPanel, str[benefit], "내 혜택", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		homeBtn.addActionListener(new ActionListener(){ //홈 버튼
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == homeBtn){ //로그인 끊기, 고객 정보 초기화, 홈화면으로
					SE.smartLoginState(false); //로그인 끊기
					cardLayout.show(c, "access"); //접속 화면으로 바꾸기
				}
			}
		});
		
		interworkBtn.addActionListener(new ActionListener(){ //연동 버튼
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == interworkBtn){
					if(SE.smartInterworkState == false){ //연동 안한 상태 -> 연동하기
						SE.smartInterworkState(true);
					}
					else{ //연동한 상태 -> 연동 끊기
						SE.smartInterworkState(false);
					}
				}
			}
		});
		
		pictogramPanel.add(bankBtn);
		pictogramPanel.add(homeBtn);
		pictogramPanel.add(interworkBtn);
	}
	
	static void initUserInfo(String account){ //로그인시 고객 정보 받아오기
		userName.setText(SE.getAccountName(account));
		userAccountNumber.setText(account);
		balanceLabel.setText(SE.getAccountBalance(account) + " 원");
		
		String str[] = {"혜택 없음", "음식!", "운동!", "영화!", "놀이공원!"};
		benefit.setText(str[SE.monthBenefit]);
	}
	
	static void delUserInfo(){ //로그아웃시 고객 정보 지우기
		userName.setText("고객 정보 없음");
		userAccountNumber.setText("고객 정보 없음");
		balanceLabel.setText("고객 정보 없음");
		benefit.setText("고객 정보 없음");
	}

	static void setBalance(int balance){ //잔액 갱신
		balanceLabel.setText(balance + " 원");
	}
}


