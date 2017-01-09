package se;

import java.awt.CardLayout;
import java.awt.CheckboxMenuItem;
import java.awt.Container;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

class MainFrame extends JFrame{ static //기본 프레임 (atmPanel, createAccountPanel, shopPanel으로 구성)
	CardLayout cardLayout = new CardLayout(10, 10); //여백, #참고 1
	Container c; //뒤로가기 버튼용, #참고 3
	
	private MenuBar mb = new MenuBar(); //메뉴바 #참고 2
	
	private Menu move = new Menu("MOVE"); //atmPanel, shopPanel 사이 이동
	private Menu open = new Menu("OPEN"); //스마트폰 화면 열기
	
	private MenuItem moveToAtm = new MenuItem("ATM");
	private MenuItem moveToShop = new MenuItem("SHOP");
	private Container con;
	private ImageIcon icon_image, main_image;//페이지 아이콘 이미지 삽입 
	
	static CheckboxMenuItem openSmart = new CheckboxMenuItem("Smart Phone", SE.smartState);
	//버튼이벤트를 주기위해 다음 패널들을 전역으로  
	atmPanel atm = new atmPanel(); 
	cashInPanel c_in = new cashInPanel();
	cashOutPanel c_out = new cashOutPanel(); //CashOut Panel 과 연결
	createAccountPanel ca = new createAccountPanel();
	useCashPanel useCash = new useCashPanel();
	CashTransferPanel ctf = new CashTransferPanel();
	MainFrame(){ //디폴트 접근제한자 - 같은 패키지 + 자기 객체 내
		setSize(500, 400);
		setTitle("National Korea Bank");
		icon_image = new ImageIcon("logo_small_2.jpg"); //타이틀바에 들어갈 아이콘 이미지
		this.setIconImage(icon_image.getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //열려있는 모든 윈도우 종료
		
		setLayout(cardLayout); 
		

		shopPanel shop = new shopPanel();
		add(ctf,"ctf");
		add(atm, "atm");
		add(ca, "ca");
		add(c_in, "c_in");
		add(c_out, "c_out");
		add(useCash, "useCash");
		add(shop, "shop");
		
		setMenubar(); //메뉴바 붙여주기
		
		cardLayout.show(getContentPane(), "atm"); //기본 화면 띄우기
		
		atm.createAccountBtn.addActionListener(new createAccountListener());
		atm.cashOut.addActionListener(new cashOutBtnListener());
		atm.cashIn.addActionListener(new cashInBtnListener());
		atm.useCash.addActionListener(new useCashBtnListener());
		atm.cashMove.addActionListener(new moveCashBtnListener());
		c_in.backBtn.addActionListener(new backBtnListener());
		c_out.backBtn.addActionListener(new backBtnListener());
		useCash.backBtn.addActionListener(new backBtnListener());
		ca.backBtn.addActionListener(new backBtnListener());
		ctf.backBtn.addActionListener(new backBtnListener());
		setVisible(true);
	}
	public void init(){
		icon_image=new ImageIcon("logo_small_2.jpg");
		con=this.getContentPane();//다중패널에서의 기본작업 영역 획득
	}
	private class backBtnListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if((e.getSource() == c_in.backBtn) || (e.getSource() == c_out.backBtn)
					|| (e.getSource() == useCash.backBtn)|| (e.getSource() == ca.backBtn)
					|| (e.getSource() == ctf.backBtn)){
				cardLayout.show(getContentPane(), "atm"); //atm화면으로 돌아가기
			}
		}
	}
	private class moveCashBtnListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
				if(e.getSource() == atm.cashMove){
					CashTransferPanel.init(); //화면 초기화
					cardLayout.show(getContentPane(), "ctf"); //cashTransfer 화면
				}
		}
	}
	
	private class createAccountListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(e.getSource() == atm.createAccountBtn){
				createAccountPanel.init(); //초기화
				cardLayout.show(getContentPane(), "ca"); //ca화면
			}
		}
	}
	
	private class cashInBtnListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(e.getSource() == atm.cashIn){
				cardLayout.show(getContentPane(), "c_in"); //c_in 화면
			}
		}
		
	}
	
	private class cashOutBtnListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(e.getSource() == atm.cashOut){
				cardLayout.show(getContentPane(), "c_out"); //c_out 화면
			}
		}
		
	}
	
	private class useCashBtnListener implements ActionListener{ //사용내역 화면
		public void actionPerformed(ActionEvent e){
			if(e.getSource() == atm.useCash){
				if(SE.smartInterworkState){ //연동 중이면 스마트폰 내역
					useCashPanel.setAccount(SmartPhonePanel.userAccountNumber.getText());
					cardLayout.show(getContentPane(), "useCash"); //useCash 화면
				}
				else{ //연동 안함 -> 연동하기 or 계좌번호 or 취소
					Object[] options = {"연동하기", "계좌", "취소"};
					
					int select = JOptionPane.showOptionDialog(SE.mf, 
							"확인할 계좌를 선택해주세요.", "선택", 
							JOptionPane.YES_NO_CANCEL_OPTION, 
							JOptionPane.QUESTION_MESSAGE, 
							null, options, options[2]);
					
					if(select == 0){ //연동하기
						if(SE.smartState == false){ //스마트폰 안켜져있음
							JOptionPane.showMessageDialog(SE.mf, "스마트폰을 켜주세요", "스마트폰 오류", JOptionPane.WARNING_MESSAGE);
						}
						else if(SE.smartLoginState == false){ //로그인 안되있음
							JOptionPane.showMessageDialog(SE.mf, "로그인 해주세요", "로그인 오류", JOptionPane.WARNING_MESSAGE);
						}
						else{ //연동하기
							SE.smartInterworkState(true); //연동하기
							
							useCashPanel.setAccount(SmartPhonePanel.userAccountNumber.getText());
							cardLayout.show(getContentPane(), "useCash"); //useCash 화면
						}
					}
					else if(select == 1){ //계좌 거래 #참고 4, 참고 5
						String id = JOptionPane.showInputDialog(SE.mf, "ID를 입력해주세요.", "계좌 입력", JOptionPane.QUESTION_MESSAGE);
						
						if(id != null){ //입력이 있으면
							JPasswordField passwd = new JPasswordField();
							int ok = JOptionPane.showConfirmDialog(SE.mf, passwd, "Enter Password", JOptionPane.WARNING_MESSAGE);
							
							if(ok == JOptionPane.OK_OPTION){ //확인 버튼 눌렀을 시
								String user = SE.checkUserInfo(id, passwd.getText());
								
								if(user == null){ //고객이 아니다 -> 접속 실패 알림
									JOptionPane.showMessageDialog(SE.mf, "아이디 또는 비밀번호를 다시 입력해 주세요", "로그인 오류", JOptionPane.WARNING_MESSAGE);
								}
								else{ //고객이다!!
									useCashPanel.setAccount(user);
									cardLayout.show(getContentPane(), "useCash"); //useCash 화면
								}
							}
						}
						
					}
					else if(select == 2){ } //취소
				}
			}
		}
	}
	
	private void setMenubar(){ //메뉴바 붙여주기
		setMenuBar(mb);
		
		mb.add(move);
		mb.add(open);
		
		move.add(moveToAtm);
		moveToAtm.addActionListener(new MoveMenuEvent()); //#참고3
		move.addSeparator(); //구분자
		move.add(moveToShop);
		moveToShop.addActionListener(new MoveMenuEvent());
		
		open.add(openSmart);
		openSmart.addItemListener(new OpenMenuEvent()); //#참고 4
	}
	
	public class MoveMenuEvent implements ActionListener{ //메뉴바 이벤트
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == moveToAtm){
				cardLayout.show(getContentPane(), "atm"); //화면 바꾸기
			}
			else if(e.getSource() == moveToShop){
				cardLayout.show(getContentPane(), "shop"); //화면 바꾸기
			}
		}
	}
	
	private class OpenMenuEvent implements ItemListener{ //#참고 4
		public void itemStateChanged(ItemEvent e) {
			if(e.getStateChange() == ItemEvent.SELECTED){ //on
				if(SE.smartState == false){ //닫혀있을 때
					SE.openSmart(); //스마트폰 열어주기
				}	
			}
			else if(e.getStateChange() == ItemEvent.DESELECTED){ //off
				if(SE.smartState == true){ //열려있을 때
					SE.closeSmart(); //스마트폰 닫아주기
				}
			}
		}
	}
}

/*
#참고 1 cardLayout 설명
	http://blog.naver.com/since890513/220181967925
#참고 2 메뉴바 만들기
	http://blog.naver.com/munjh4200/50176425790
#참고 3 이벤트 (내부/외부/무명)
	http://blog.naver.com/jjonghun1004/220759522958
#참고 4 comboboxMenu 이벤트
	http://kin.naver.com/qna/detail.nhn?d1id=1&dirId=1040201&docId=64571626&qb=amF2YSBDaGVja2JveE1lbnVJdGVtIGV2ZW50&enc=utf8&section=kin&rank=1&search_sort=0&spq=0&pid=TuxKewoRR00ssuLBQcCsssssssK-199922&sid=QaqSsqKwAMCM4REhspuv5w%3D%3D
	
*/

/*
# 참고5
   움직이는 이미지 gif 삽입 
class MyPanel extends JPanel {
		Image image;
		
		MyPanel() throws MalformedURLException {
			image = Toolkit.getDefaultToolkit().createImage("img/funny.gif");  
		    
		}
		
		public void paintComponent(Graphics g) {  
		    super.paintComponent(g);  
		    if (image != null) {  
		      g.drawImage(image, 0, 0, this);  
		    }  
		  } 
	} 
*/
/*
 #참고 6
 음성파일 삽입 
 http://egloos.zum.com/icegeo/v/300509 
 http://blog.naver.com/PostView.nhn?blogId=helloworld8&logNo=220076589506&parentCategoryNo=&categoryNo=&viewDate=&isShowPopularPosts=false&from=postView
 
*/
