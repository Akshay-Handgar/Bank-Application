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
	
	JPanel totalPanel = new JPanel(); //가운데부분 전체
	JPanel fieldPanel = new JPanel();
	
	JTextField idField = new JTextField(8);
	JPasswordField passwdField = new JPasswordField(8);
	JButton accessBtn = new JButton("접속");
	
	Border border = BorderFactory.createTitledBorder("");
	
	AccessPanel(CardLayout cardLayout, Container c){
		setSize(300, 500);
		setLayout(new BorderLayout());
		
		smartPhoneLabel.setHorizontalAlignment(JLabel.CENTER); //라벨 중앙정렬
		
		totalPanel.setLayout(new GridLayout(0,2));
		totalPanel.setBorder(border);
		for(int i =0; i<8;i++){
			totalPanel.add(new JPanel()); //자리채우기 패널
		}
		totalPanel.add(idLabel);
		totalPanel.add(idField);
		totalPanel.add(passwdLabel);
		totalPanel.add(passwdField);
		for(int i =0; i<14;i++){
			totalPanel.add(new JPanel()); //자리채우기 패널
		}
		
		//접근 버튼
		accessBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) { //접속 버튼 이벤트, 고객 정보 확인하기 -> 접속 허용/불허용
				if(e.getSource() == accessBtn){
					String str = SE.checkUserInfo(idField.getText(), passwdField.getText()); //고객 정보 확인
					
					if(str == null){ //고객이 아니다 -> 접속 실패 알림
						passwdField.setText(""); //초기화 시켜주기
						JOptionPane.showMessageDialog(SE.spf, "아이디 또는 비밀번호를 다시 입력해 주세요", "로그인 오류", JOptionPane.WARNING_MESSAGE);
					}
					else{ //고객이다!! -> 화면 바꾸기
						idField.setText(""); //초기화 시켜주기
						passwdField.setText(""); //초기화 시켜주기
					
						SE.smartLoginState(true); //로그인!
						SmartPhonePanel.initUserInfo(str); //고객 정보 받아오기
						cardLayout.show(c, "smartPhone"); //스마트폰 화면으로 바꾸기
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
