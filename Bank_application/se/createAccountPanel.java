package se;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

class createAccountPanel extends JPanel{ //MainFrame 소속
	JLabel createAccountLabel = new JLabel("create Account"); //맨위 라벨
	
	JPanel totalPanel = new JPanel(); //아래 두개 합친 패널
	JPanel checkedUserPanel = new JPanel(); //본인확인 패널
	JPanel accountInfoPanel = new JPanel(); //생성패널
	JPanel BtnPanel = new JPanel(); //뒤로가기,생성버튼 패널
	
	JButton backBtn = new JButton("뒤로가기"); //뒤로가기버튼
	JButton createBtn = new JButton("생성"); //생성버튼
	
	JPanel userInfoPanel = new JPanel(); //이름 전번 주민 합칠패널
	JPanel userInfoFieldPanel = new JPanel(); //이름 전번 주민 필드 합칠패널
	JLabel nameLabel = new JLabel("이         름"); //이름
	JLabel phoneNumberLabel = new JLabel("전화번호"); //전번
	JLabel resisterNumberLabel = new JLabel("주민번호"); //주민
	static JTextField nameField = new JTextField(10); 
	static JTextField phoneNumberField = new JTextField(10);
	static JTextField resisterNumberField = new JTextField(10);
	static JButton userCheckBtn = new JButton("본인확인");
	
	JPanel accountPasswdPanel = new JPanel(); //계좌번호,비번 합칠패널
	JPanel accountPasswdFieldPanel = new JPanel(); //계좌번호,비번의 필드를 합칠 패널
	JLabel accountNumberLabel = new JLabel("계좌번호"); //계좌번호
	JLabel passwdLabel = new JLabel("비밀번호"); //비번
	JLabel checkedPasswdLabel = new JLabel("비밀번호재입력"); //비번확인
	static JTextField accountNumberField = new JTextField(10); 
	static JPasswordField passwdField = new JPasswordField(10);
	static JPasswordField checkedPasswdField = new JPasswordField(10);
	static JButton reCreate = new JButton("비밀번호 확인"); //계좌번호,비밀번호 재생성버튼
	
	JPanel hobbyPanel = new JPanel(); //흥미 부분
	JRadioButton[] rb = new JRadioButton[4]; //음식, 운동, 영화, 놀이공원
	static ButtonGroup radioGroup = new ButtonGroup();
	
	createAccountPanel(){
		setLayout(new BorderLayout());
		add(createAccountLabel,BorderLayout.NORTH);
		createAccountLabel.setHorizontalAlignment(JLabel.CENTER); //라벨 중앙정렬
		
		totalPanel.setLayout(new BoxLayout(totalPanel,BoxLayout.Y_AXIS));
		
		//이름전화번호주민번호 부분 패널
		checkedUserPanel.setLayout(new GridLayout(0, 3)); //흠 .... 
		
		userInfoPanel.setLayout(new GridLayout(3, 0));
		userInfoPanel.add(nameLabel);
		userInfoPanel.add(phoneNumberLabel);
		userInfoPanel.add(resisterNumberLabel);
		
		userInfoFieldPanel.setLayout(new GridLayout(3, 0));
		userInfoFieldPanel.add(nameField);
		userInfoFieldPanel.add(phoneNumberField);
		userInfoFieldPanel.add(resisterNumberField);
		
		userCheckBtn.addMouseListener(new MyMouseAdapter());
		
		checkedUserPanel.add(userInfoPanel);
		checkedUserPanel.add(userInfoFieldPanel);
		checkedUserPanel.add(userCheckBtn); //본인확인 버튼
		
		//계좌번호 비밀번호 부분패널
		accountInfoPanel.setLayout(new GridLayout(0, 3));
		
		accountPasswdPanel.setLayout(new GridLayout(3, 0));
		accountPasswdPanel.add(accountNumberLabel);
		accountPasswdPanel.add(passwdLabel);
		accountPasswdPanel.add(checkedPasswdLabel);
		
		accountPasswdFieldPanel.setLayout(new GridLayout(3, 0));
		
		accountPasswdFieldPanel.add(accountNumberField);
		accountPasswdFieldPanel.add(passwdField);
		accountPasswdFieldPanel.add(checkedPasswdField);
		
		reCreate.addMouseListener(new MyMouseAdapter());
		
		accountInfoPanel.add(accountPasswdPanel);
		accountInfoPanel.add(accountPasswdFieldPanel);
		accountInfoPanel.add(reCreate); //재생성 버튼
		
		//흥미 부분 패널, 음식, 운동, 영화, 놀이공원
		hobbyPanel.setBorder(BorderFactory.createTitledBorder("흥미 선택"));
		
		rb[0] = new JRadioButton("음식");
		rb[1] = new JRadioButton("운동");
		rb[2] = new JRadioButton("영화");
		rb[3] = new JRadioButton("놀이");
		
		radioGroup.add(rb[0]);
		radioGroup.add(rb[1]);
		radioGroup.add(rb[2]);
		radioGroup.add(rb[3]);

		hobbyPanel.add(rb[0]);
		hobbyPanel.add(rb[1]);
		hobbyPanel.add(rb[2]);
		hobbyPanel.add(rb[3]);
		
		//뒤로가기, 생성버튼 패널
		backBtn.addMouseListener(new MyMouseAdapter());
		createBtn.addMouseListener(new MyMouseAdapter());
		
		BtnPanel.add(backBtn);
		BtnPanel.add(createBtn);
		
		//전체 프레임 에드
		totalPanel.add(checkedUserPanel); //이름 전화번호 주민번호
		totalPanel.add(accountInfoPanel); //계좌번호 비밀번호 재입력
		totalPanel.add(hobbyPanel); //흥미
		totalPanel.add(BtnPanel); //취소, 생성
		
		add(totalPanel,BorderLayout.CENTER);
		
		//시작은 비활성화
		reCreate.setEnabled(false);
		accountNumberField.setEditable(false);
		passwdField.setEditable(false);
		checkedPasswdField.setEditable(false);
	}
	
	static void init(){ //초기화
		nameField.setText("");
		phoneNumberField.setText("");
		resisterNumberField.setText("");
		
		accountNumberField.setText("");
		passwdField.setText("");
		checkedPasswdField.setText("");
		
		userCheckBtn.setEnabled(true);
		nameField.setEditable(true);
		phoneNumberField .setEditable(true);
		resisterNumberField.setEditable(true);
		
		reCreate.setEnabled(false);
		accountNumberField.setEditable(false);
		passwdField.setEditable(false);
		checkedPasswdField.setEditable(false);
		
		radioGroup.clearSelection();
	}
	
	class MyMouseAdapter extends MouseAdapter{
		public void mouseClicked(MouseEvent e){
			if(e.getSource() == userCheckBtn){ //본인 확인 버튼
				if(userCheckBtn.isEnabled()){
					if(nameField.getText().equals("")){ } 
					else{ //이름 패널이 null이 아니면
						userCheckBtn.setEnabled(false);
						nameField.setEditable(false);
						phoneNumberField .setEditable(false);
						resisterNumberField.setEditable(false);
						
						accountNumberField.setText(SE.setAccountNumber());
						
						reCreate.setEnabled(true);
						passwdField.setEditable(true);
						checkedPasswdField.setEditable(true);
					}
				}
			}
			else if(e.getSource() == reCreate){ //비밀번호 확인 버튼
				if(reCreate.isEnabled()){
					if(passwdField.getText().equals(checkedPasswdField.getText())){ //비번이 같으면
						if(passwdField.getText().length() == 4){
							reCreate.setEnabled(false);
						}
						else{
							JOptionPane.showMessageDialog(SE.mf, "비밀번호는 4자리로 만들어주세요!", "계좌 생성 알람", JOptionPane.WARNING_MESSAGE);
						}
					}
					else{ //비번이 다르면
						JOptionPane.showMessageDialog(SE.mf, "비밀번호화 확인이 다릅니다!", "계좌 생성 알람", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
			else if(e.getSource() == createBtn){ //생성
				if(reCreate.isEnabled() || userCheckBtn.isEnabled()){ //거르기
					JOptionPane.showMessageDialog(SE.mf, "확인 버튼을 눌러주세요!", "계좌 생성 알람", JOptionPane.WARNING_MESSAGE);
				}
				else{
					if(radioGroup.getSelection() != null){ //흥미 체크 완료
						String str = null;
						
						if(rb[0].isSelected()){ str = "1"; }
						else if(rb[1].isSelected()){ str = "2"; }
						else if(rb[2].isSelected()){ str = "3"; }
						else if(rb[3].isSelected()){ str = "4"; }
						
						SE.createUser(nameField.getText(), accountNumberField.getText(), passwdField.getText(), str);
						JOptionPane.showMessageDialog(SE.mf, "생성 완료!", "계좌 생성 알람", JOptionPane.INFORMATION_MESSAGE);
						backBtn.doClick();
					}
					else{
						JOptionPane.showMessageDialog(SE.mf, "흥미를 선택해주세요!", "계좌 생성 알람", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		}
	}
}
