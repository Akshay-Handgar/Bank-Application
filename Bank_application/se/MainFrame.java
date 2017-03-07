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

class MainFrame extends JFrame{ static //Basic Frame. Consist of (atmPanel, createAccountPanel, shopPanel)
	CardLayout cardLayout = new CardLayout(10, 10);
	Container c; //Back to page Button
	
	private MenuBar mb = new MenuBar(); //Menu Bar
	
	private Menu move = new Menu("MOVE"); //move atmPanel to shopPanel 
	private Menu open = new Menu("OPEN"); //open smart phone frame
	
	private MenuItem moveToAtm = new MenuItem("ATM");
	private MenuItem moveToShop = new MenuItem("SHOP");
	private Container con;
	private ImageIcon icon_image, main_image;//insert Image 
	
	static CheckboxMenuItem openSmart = new CheckboxMenuItem("Smart Phone", SE.smartState);
	
	atmPanel atm = new atmPanel(); 
	cashInPanel c_in = new cashInPanel();
	cashOutPanel c_out = new cashOutPanel(); //Connect to CashOut Panel
	createAccountPanel ca = new createAccountPanel();
	useCashPanel useCash = new useCashPanel();
	CashTransferPanel ctf = new CashTransferPanel();
	MainFrame(){ 
		setSize(500, 400);
		setTitle("National Korea Bank");
		icon_image = new ImageIcon("logo_small_2.jpg"); //Title bar Icon image
		this.setIconImage(icon_image.getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
		setLayout(cardLayout); 
		

		shopPanel shop = new shopPanel();
		add(ctf,"ctf");
		add(atm, "atm");
		add(ca, "ca");
		add(c_in, "c_in");
		add(c_out, "c_out");
		add(useCash, "useCash");
		add(shop, "shop");
		
		setMenubar(); 
		
		cardLayout.show(getContentPane(), "atm");
		
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
		con=this.getContentPane();
	}
	private class backBtnListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if((e.getSource() == c_in.backBtn) || (e.getSource() == c_out.backBtn)
					|| (e.getSource() == useCash.backBtn)|| (e.getSource() == ca.backBtn)
					|| (e.getSource() == ctf.backBtn)){
				cardLayout.show(getContentPane(), "atm"); //Back to atm page
			}
		}
	}
	private class moveCashBtnListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
				if(e.getSource() == atm.cashMove){
					CashTransferPanel.init(); //Clear to page
					cardLayout.show(getContentPane(), "ctf"); //cashTransfer page
				}
		}
	}
	
	private class createAccountListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(e.getSource() == atm.createAccountBtn){
				createAccountPanel.init(); //
				cardLayout.show(getContentPane(), "ca"); //ca page
			}
		}
	}
	
	private class cashInBtnListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(e.getSource() == atm.cashIn){
				cardLayout.show(getContentPane(), "c_in"); //c_in page
			}
		}
		
	}
	
	private class cashOutBtnListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(e.getSource() == atm.cashOut){
				cardLayout.show(getContentPane(), "c_out"); //c_out page
			}
		}
		
	}
	
	private class useCashBtnListener implements ActionListener{ //Usage details page
		public void actionPerformed(ActionEvent e){
			if(e.getSource() == atm.useCash){
				if(SE.smartInterworkState){ 
					useCashPanel.setAccount(SmartPhonePanel.userAccountNumber.getText());
					cardLayout.show(getContentPane(), "useCash"); //useCash page
				}
				else{ 
					Object[] options = {"연동하기", "계좌", "취소"};
					
					int select = JOptionPane.showOptionDialog(SE.mf, 
							"확인할 계좌를 선택해주세요.", "선택", 
							JOptionPane.YES_NO_CANCEL_OPTION, 
							JOptionPane.QUESTION_MESSAGE, 
							null, options, options[2]);
					
					if(select == 0){ //connect the smart phone
						if(SE.smartState == false){ //turn off the smart phone
							JOptionPane.showMessageDialog(SE.mf, "스마트폰을 켜주세요", "스마트폰 오류", JOptionPane.WARNING_MESSAGE);
						}
						else if(SE.smartLoginState == false){ //Isn't Login
							JOptionPane.showMessageDialog(SE.mf, "로그인 해주세요", "로그인 오류", JOptionPane.WARNING_MESSAGE);
						}
						else{ //connect
							SE.smartInterworkState(true); 
							
							useCashPanel.setAccount(SmartPhonePanel.userAccountNumber.getText());
							cardLayout.show(getContentPane(), "useCash"); 
						}
					}
					else if(select == 1){ //Account transaction
						String id = JOptionPane.showInputDialog(SE.mf, "ID를 입력해주세요.", "계좌 입력", JOptionPane.QUESTION_MESSAGE);
						
						if(id != null){ 
							JPasswordField passwd = new JPasswordField();
							int ok = JOptionPane.showConfirmDialog(SE.mf, passwd, "Enter Password", JOptionPane.WARNING_MESSAGE);
							
							if(ok == JOptionPane.OK_OPTION){ 
								String user = SE.checkUserInfo(id, passwd.getText());
								
								if(user == null){ //fail to connect
									JOptionPane.showMessageDialog(SE.mf, "아이디 또는 비밀번호를 다시 입력해 주세요", "로그인 오류", JOptionPane.WARNING_MESSAGE);
								}
								else{ //success to connect
									useCashPanel.setAccount(user);
									cardLayout.show(getContentPane(), "useCash"); 
								}
							}
						}
						
					}
					else if(select == 2){ } //cancle
				}
			}
		}
	}
	
	private void setMenubar(){ //Add the MenuBar
		setMenuBar(mb);
		
		mb.add(move);
		mb.add(open);
		
		move.add(moveToAtm);
		moveToAtm.addActionListener(new MoveMenuEvent()); 
		move.addSeparator(); 
		move.add(moveToShop);
		moveToShop.addActionListener(new MoveMenuEvent());
		
		open.add(openSmart);
		openSmart.addItemListener(new OpenMenuEvent()); 
	}
	
	public class MoveMenuEvent implements ActionListener{ //Event of MenuBar
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == moveToAtm){
				cardLayout.show(getContentPane(), "atm"); 
			}
			else if(e.getSource() == moveToShop){
				cardLayout.show(getContentPane(), "shop"); 
			}
		}
	}
	
	private class OpenMenuEvent implements ItemListener{ /
		public void itemStateChanged(ItemEvent e) {
			if(e.getStateChange() == ItemEvent.SELECTED){ //on
				if(SE.smartState == false){ 
					SE.openSmart(); //If page is closed, open again.
				}	
			}
			else if(e.getStateChange() == ItemEvent.DESELECTED){ //off
				if(SE.smartState == true){ 
					SE.closeSmart(); 
				}
			}
		}
	}
}
/*
 #Reference.
 Add the Voice File. 
 http://egloos.zum.com/icegeo/v/300509 
 http://blog.naver.com/PostView.nhn?blogId=helloworld8&logNo=220076589506&parentCategoryNo=&categoryNo=&viewDate=&isShowPopularPosts=false&from=postView
*/
