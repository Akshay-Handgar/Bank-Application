package se;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class CashTransferPanel extends JPanel{
	JLabel account = new JLabel("계좌");
	JLabel money = new JLabel("금액");
	JLabel passwd = new JLabel("비번");
	
	static JTextField accountTF = new JTextField(13);
	static JTextField moneyTF = new JTextField(13);
	static JPasswordField passwdTF = new JPasswordField(13);
	
	
	JButton cashTransferBtn = new JButton("송금");
	JButton backBtn = new JButton("뒤로가기");
	
	JPanel accountPanel = new JPanel();
	JPanel moneyPanel = new JPanel();
	JPanel passwdPanel = new JPanel();
	JPanel btnPanel = new JPanel();
	int result;
	public CashTransferPanel(){
		setLayout(new GridLayout(4,0));

		accountPanel.add(account);
		accountPanel.add(accountTF);
		moneyPanel.add(money);
		moneyPanel.add(moneyTF);
		passwdPanel.add(passwd);
		passwdPanel.add(passwdTF);
				
		//버튼추가
		btnPanel.add(cashTransferBtn);
		btnPanel.add(backBtn);
		
		//패널추가
		add(accountPanel);
		add(moneyPanel);
		add(passwdPanel);
		add(btnPanel);
		
		cashTransferBtn.addMouseListener(new CTFBtnClicked());
	}
	static void init(){
		accountTF.setText("");
		moneyTF.setText("");
		passwdTF.setText("");
	}
	private class CTFBtnClicked extends MouseAdapter{ 
		public void mouseClicked(MouseEvent e) {
			if(accountTF.getText().equals("")||moneyTF.getText().equals("")||passwdTF.getText().equals("")){
				JOptionPane.showMessageDialog(accountPanel, "빈칸을 입력해주세요", "거래 오류", JOptionPane.WARNING_MESSAGE);
			}
			else{
				if(e.getSource() == cashTransferBtn){
					result=Integer.parseInt(moneyTF.getText());
					if(SE.smartInterworkState){ //연동 중이면 스마트폰으로 계산
						JOptionPane.showMessageDialog(accountPanel, "거래 완료!", "거래 알람", JOptionPane.INFORMATION_MESSAGE);
						SE.banking(SmartPhonePanel.userAccountNumber.getText(), SE.getAccountName(SmartPhonePanel.userAccountNumber.getText())+" ATM 송금", result, '-', true); //연동된대서는 출금
						SE.banking(accountTF.getText(), SE.getAccountName(accountTF.getText()) +" ATM 입금", result, '+', false); //받는쪽 입금
					}
					else{ //연동 안함 -> 연동하기 or 취소
						Object[] options = {"연동하기", "계좌", "취소"};
						int select = JOptionPane.showOptionDialog(accountPanel, 
								"연동하시겠습니까?", "결제 수단 선택", 
								JOptionPane.YES_NO_CANCEL_OPTION, 
								JOptionPane.QUESTION_MESSAGE, 
								null, options, options[2]);
						
						if(select == 0){//연동하기
							if(SE.smartState == false){ //스마트폰 안켜져있음
								JOptionPane.showMessageDialog(accountPanel, "스마트폰을 켜주세요", "스마트폰 오류", JOptionPane.WARNING_MESSAGE);
							}
							else if(SE.smartLoginState == false){ //로그인 안되있음
								JOptionPane.showMessageDialog(accountPanel, "로그인 해주세요", "로그인 오류", JOptionPane.WARNING_MESSAGE);
							}
							else{ //연동하기
								SE.smartInterworkState(true); //연동하기
								JOptionPane.showMessageDialog(accountPanel, "거래 완료!", "거래 알람", JOptionPane.INFORMATION_MESSAGE);
								SE.banking(SmartPhonePanel.userAccountNumber.getText(), SE.getAccountName(SmartPhonePanel.userAccountNumber.getText())+" ATM 송금", result, '-', true); //연동된대서는 출금
								SE.banking(accountTF.getText(), SE.getAccountName(accountTF.getText()) +" ATM 입금", result, '+', false); //받는쪽 입금
							}
						}
						else if(select == 1){ //계좌
							String id = JOptionPane.showInputDialog(accountPanel, "ID를 입력해주세요.", "계좌 입력", JOptionPane.QUESTION_MESSAGE);
							
							if(id != null){ //입력이 있으면
								JPasswordField passwd = new JPasswordField();
								int ok = JOptionPane.showConfirmDialog(accountPanel, passwd, "Enter Password", JOptionPane.WARNING_MESSAGE);
								
								if(ok == JOptionPane.OK_OPTION){ //확인 버튼 눌렀을 시
									String user = SE.checkUserInfo(id, passwd.getText());
									
									if(user == null){ //고객이 아니다 -> 접속 실패 알림
										JOptionPane.showMessageDialog(accountPanel, "아이디 또는 비밀번호를 다시 입력해 주세요", "로그인 오류", JOptionPane.WARNING_MESSAGE);
									}
									else{ //고객이다!! -> 거래, 스마트폰과 같은 계좌면 알람 뜨게
										if((SE.smartLoginState == true) && (SmartPhonePanel.userAccountNumber.getText().equals(user))){ 
											JOptionPane.showMessageDialog(accountPanel, "거래 완료!", "거래 알람", JOptionPane.INFORMATION_MESSAGE);
											SE.banking(user, SE.getAccountName(user)+" ATM 송금", result, '-', true); //연동된대서는 출금
											SE.banking(accountTF.getText(), SE.getAccountName(accountTF.getText()) +" ATM 입금", result, '+', false); //받는쪽 입금
	
										}
										else{
											JOptionPane.showMessageDialog(accountPanel, "거래 완료!", "거래 알람", JOptionPane.INFORMATION_MESSAGE);
											SE.banking(user, SE.getAccountName(user)+" ATM 송금", result, '-', false); //연동된대서는 출금
											SE.banking(accountTF.getText(), SE.getAccountName(accountTF.getText()) +" ATM 입금", result, '+', false); //받는쪽 입금
	
										}
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

}

