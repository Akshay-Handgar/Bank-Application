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


class atmPanel extends JPanel { // MainFrame 소속
	JLabel welcomeLabel = new JLabel("Welcome to National Korean Bank [ ATM ]");
	
	ImageIcon main_gif = new ImageIcon(".\\Images\\MainPageImg\\main_frame_gif.gif");
	
	JButton cashOut = new JButton("Cash Out");	//출급
	JButton cashIn = new JButton("Cash In");	//입금		
	JButton cashMove = new JButton("Cash Transfer");	//계좌이체
	JButton useCash = new JButton("Usage");	//사용내역
	JButton checkCash = new JButton("checkCash");	//잔액확인
	
	JPanel mixedPanel = new JPanel(); //atmPanel의 하부 모든 기능을 넣을 패널
	JPanel funcPanel = new JPanel(); //입금,출금,계좌이체,사용내역,잔액확인
	JPanel imagePanel = new JPanel(); //이미지 패널
	JPanel crtAccountPanel = new JPanel(); //계좌 생성 패널
	
	Border border = BorderFactory.createTitledBorder(""); // 테두리추가
	JLabel imageLabel = new JLabel();
	
	JButton createAccountBtn = new JButton("createAccount"); //계좌생성버튼
	

	
	createAccountPanel cap = null;
	atmPanel() {
		setLayout(new BorderLayout());
		welcomeLabel.setHorizontalAlignment(JLabel.CENTER); //라벨 중앙정렬
		add(welcomeLabel,BorderLayout.NORTH);
		add(mixedPanel,BorderLayout.CENTER);
		
		//funcPanel.setBorder(border);
		//imagePanel.setBorder(border);
		//crtAccountPanel.setBorder(border);
		
		mixedPanel.setLayout(new BorderLayout()); //아랫부분 패널을 3열로
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
		
		//imagePanel 이미지부분
		
		crtAccountPanel.setLayout(new GridLayout(8,0));
		crtAccountPanel.add(createAccountBtn);
		
		checkCash.addMouseListener(new checkCashClicked());
		
	}
	
	private class checkCashClicked extends MouseAdapter { //잔액 확인!
		public void mouseClicked(MouseEvent c) {
			if(c.getSource() == checkCash){
				if(SE.smartInterworkState){ //연동 중이면 스마트폰 잔액
					JOptionPane.showMessageDialog(mixedPanel, "잔액은 " + SE.getAccountBalance(SmartPhonePanel.userAccountNumber.getText()) + "원 입니다");
				}
				else{ //연동 안함 -> 연동하기 or 계좌 or 취소
					Object[] options = {"연동하기", "계좌", "취소"};
					int select = JOptionPane.showOptionDialog(mixedPanel, 
						"연동하시겠습니까?", "결제 수단 선택", 
						JOptionPane.YES_NO_CANCEL_OPTION, 
						JOptionPane.QUESTION_MESSAGE, 
						null, options, options[2]);
					
					if(select == 0){//연동하기
						if(SE.smartState == false){ //스마트폰 안켜져있음
								JOptionPane.showMessageDialog(mixedPanel, "스마트폰을 켜주세요", "스마트폰 오류", JOptionPane.WARNING_MESSAGE);
						}
						else if(SE.smartLoginState == false){ //로그인 안되있음
							JOptionPane.showMessageDialog(mixedPanel, "로그인 해주세요", "로그인 오류", JOptionPane.WARNING_MESSAGE);
						}
						else{ //연동하기
							SE.smartInterworkState(true); //연동하기
							
							JOptionPane.showMessageDialog(mixedPanel, "잔액은 " + SE.getAccountBalance(SmartPhonePanel.userAccountNumber.getText())+"원 입니다");
						}
					}
					else if(select == 1){ //계좌
						String id = JOptionPane.showInputDialog(mixedPanel, "ID를 입력해주세요.", "계좌 입력", JOptionPane.QUESTION_MESSAGE);
						
						if(id != null){ //입력이 있으면
							JPasswordField passwd = new JPasswordField();
							int ok = JOptionPane.showConfirmDialog(mixedPanel, passwd, "Enter Password", JOptionPane.WARNING_MESSAGE);
							
							if(ok == JOptionPane.OK_OPTION){ //확인 버튼 눌렀을 시
								String user = SE.checkUserInfo(id, passwd.getText());
								
								if(user == null){ //고객이 아니다 -> 접속 실패 알림
									JOptionPane.showMessageDialog(mixedPanel, "아이디 또는 비밀번호를 다시 입력해 주세요", "로그인 오류", JOptionPane.WARNING_MESSAGE);
								}
								else{ //고객이다!! -> 거래, 스마트폰과 같은 계좌면 알람 뜨게
									JOptionPane.showMessageDialog(mixedPanel, "잔액은 " + SE.getAccountBalance(user)+"원 입니다");
								}
							}
						}
					}
					else if(select == 2){ } //취소
				}
			}
		}
	}
}
