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

class MainFrame extends JFrame{ static //�⺻ ������ (atmPanel, createAccountPanel, shopPanel���� ����)
	CardLayout cardLayout = new CardLayout(10, 10); //����, #���� 1
	Container c; //�ڷΰ��� ��ư��, #���� 3
	
	private MenuBar mb = new MenuBar(); //�޴��� #���� 2
	
	private Menu move = new Menu("MOVE"); //atmPanel, shopPanel ���� �̵�
	private Menu open = new Menu("OPEN"); //����Ʈ�� ȭ�� ����
	
	private MenuItem moveToAtm = new MenuItem("ATM");
	private MenuItem moveToShop = new MenuItem("SHOP");
	private Container con;
	private ImageIcon icon_image, main_image;//������ ������ �̹��� ���� 
	
	static CheckboxMenuItem openSmart = new CheckboxMenuItem("Smart Phone", SE.smartState);
	//��ư�̺�Ʈ�� �ֱ����� ���� �гε��� ��������  
	atmPanel atm = new atmPanel(); 
	cashInPanel c_in = new cashInPanel();
	cashOutPanel c_out = new cashOutPanel(); //CashOut Panel �� ����
	createAccountPanel ca = new createAccountPanel();
	useCashPanel useCash = new useCashPanel();
	CashTransferPanel ctf = new CashTransferPanel();
	MainFrame(){ //����Ʈ ���������� - ���� ��Ű�� + �ڱ� ��ü ��
		setSize(500, 400);
		setTitle("National Korea Bank");
		icon_image = new ImageIcon("logo_small_2.jpg"); //Ÿ��Ʋ�ٿ� �� ������ �̹���
		this.setIconImage(icon_image.getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //�����ִ� ��� ������ ����
		
		setLayout(cardLayout); 
		

		shopPanel shop = new shopPanel();
		add(ctf,"ctf");
		add(atm, "atm");
		add(ca, "ca");
		add(c_in, "c_in");
		add(c_out, "c_out");
		add(useCash, "useCash");
		add(shop, "shop");
		
		setMenubar(); //�޴��� �ٿ��ֱ�
		
		cardLayout.show(getContentPane(), "atm"); //�⺻ ȭ�� ����
		
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
		con=this.getContentPane();//�����гο����� �⺻�۾� ���� ȹ��
	}
	private class backBtnListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if((e.getSource() == c_in.backBtn) || (e.getSource() == c_out.backBtn)
					|| (e.getSource() == useCash.backBtn)|| (e.getSource() == ca.backBtn)
					|| (e.getSource() == ctf.backBtn)){
				cardLayout.show(getContentPane(), "atm"); //atmȭ������ ���ư���
			}
		}
	}
	private class moveCashBtnListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
				if(e.getSource() == atm.cashMove){
					CashTransferPanel.init(); //ȭ�� �ʱ�ȭ
					cardLayout.show(getContentPane(), "ctf"); //cashTransfer ȭ��
				}
		}
	}
	
	private class createAccountListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(e.getSource() == atm.createAccountBtn){
				createAccountPanel.init(); //�ʱ�ȭ
				cardLayout.show(getContentPane(), "ca"); //caȭ��
			}
		}
	}
	
	private class cashInBtnListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(e.getSource() == atm.cashIn){
				cardLayout.show(getContentPane(), "c_in"); //c_in ȭ��
			}
		}
		
	}
	
	private class cashOutBtnListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(e.getSource() == atm.cashOut){
				cardLayout.show(getContentPane(), "c_out"); //c_out ȭ��
			}
		}
		
	}
	
	private class useCashBtnListener implements ActionListener{ //��볻�� ȭ��
		public void actionPerformed(ActionEvent e){
			if(e.getSource() == atm.useCash){
				if(SE.smartInterworkState){ //���� ���̸� ����Ʈ�� ����
					useCashPanel.setAccount(SmartPhonePanel.userAccountNumber.getText());
					cardLayout.show(getContentPane(), "useCash"); //useCash ȭ��
				}
				else{ //���� ���� -> �����ϱ� or ���¹�ȣ or ���
					Object[] options = {"�����ϱ�", "����", "���"};
					
					int select = JOptionPane.showOptionDialog(SE.mf, 
							"Ȯ���� ���¸� �������ּ���.", "����", 
							JOptionPane.YES_NO_CANCEL_OPTION, 
							JOptionPane.QUESTION_MESSAGE, 
							null, options, options[2]);
					
					if(select == 0){ //�����ϱ�
						if(SE.smartState == false){ //����Ʈ�� ����������
							JOptionPane.showMessageDialog(SE.mf, "����Ʈ���� ���ּ���", "����Ʈ�� ����", JOptionPane.WARNING_MESSAGE);
						}
						else if(SE.smartLoginState == false){ //�α��� �ȵ�����
							JOptionPane.showMessageDialog(SE.mf, "�α��� ���ּ���", "�α��� ����", JOptionPane.WARNING_MESSAGE);
						}
						else{ //�����ϱ�
							SE.smartInterworkState(true); //�����ϱ�
							
							useCashPanel.setAccount(SmartPhonePanel.userAccountNumber.getText());
							cardLayout.show(getContentPane(), "useCash"); //useCash ȭ��
						}
					}
					else if(select == 1){ //���� �ŷ� #���� 4, ���� 5
						String id = JOptionPane.showInputDialog(SE.mf, "ID�� �Է����ּ���.", "���� �Է�", JOptionPane.QUESTION_MESSAGE);
						
						if(id != null){ //�Է��� ������
							JPasswordField passwd = new JPasswordField();
							int ok = JOptionPane.showConfirmDialog(SE.mf, passwd, "Enter Password", JOptionPane.WARNING_MESSAGE);
							
							if(ok == JOptionPane.OK_OPTION){ //Ȯ�� ��ư ������ ��
								String user = SE.checkUserInfo(id, passwd.getText());
								
								if(user == null){ //���� �ƴϴ� -> ���� ���� �˸�
									JOptionPane.showMessageDialog(SE.mf, "���̵� �Ǵ� ��й�ȣ�� �ٽ� �Է��� �ּ���", "�α��� ����", JOptionPane.WARNING_MESSAGE);
								}
								else{ //���̴�!!
									useCashPanel.setAccount(user);
									cardLayout.show(getContentPane(), "useCash"); //useCash ȭ��
								}
							}
						}
						
					}
					else if(select == 2){ } //���
				}
			}
		}
	}
	
	private void setMenubar(){ //�޴��� �ٿ��ֱ�
		setMenuBar(mb);
		
		mb.add(move);
		mb.add(open);
		
		move.add(moveToAtm);
		moveToAtm.addActionListener(new MoveMenuEvent()); //#����3
		move.addSeparator(); //������
		move.add(moveToShop);
		moveToShop.addActionListener(new MoveMenuEvent());
		
		open.add(openSmart);
		openSmart.addItemListener(new OpenMenuEvent()); //#���� 4
	}
	
	public class MoveMenuEvent implements ActionListener{ //�޴��� �̺�Ʈ
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == moveToAtm){
				cardLayout.show(getContentPane(), "atm"); //ȭ�� �ٲٱ�
			}
			else if(e.getSource() == moveToShop){
				cardLayout.show(getContentPane(), "shop"); //ȭ�� �ٲٱ�
			}
		}
	}
	
	private class OpenMenuEvent implements ItemListener{ //#���� 4
		public void itemStateChanged(ItemEvent e) {
			if(e.getStateChange() == ItemEvent.SELECTED){ //on
				if(SE.smartState == false){ //�������� ��
					SE.openSmart(); //����Ʈ�� �����ֱ�
				}	
			}
			else if(e.getStateChange() == ItemEvent.DESELECTED){ //off
				if(SE.smartState == true){ //�������� ��
					SE.closeSmart(); //����Ʈ�� �ݾ��ֱ�
				}
			}
		}
	}
}

/*
#���� 1 cardLayout ����
	http://blog.naver.com/since890513/220181967925
#���� 2 �޴��� �����
	http://blog.naver.com/munjh4200/50176425790
#���� 3 �̺�Ʈ (����/�ܺ�/����)
	http://blog.naver.com/jjonghun1004/220759522958
#���� 4 comboboxMenu �̺�Ʈ
	http://kin.naver.com/qna/detail.nhn?d1id=1&dirId=1040201&docId=64571626&qb=amF2YSBDaGVja2JveE1lbnVJdGVtIGV2ZW50&enc=utf8&section=kin&rank=1&search_sort=0&spq=0&pid=TuxKewoRR00ssuLBQcCsssssssK-199922&sid=QaqSsqKwAMCM4REhspuv5w%3D%3D
	
*/

/*
# ����5
   �����̴� �̹��� gif ���� 
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
 #���� 6
 �������� ���� 
 http://egloos.zum.com/icegeo/v/300509 
 http://blog.naver.com/PostView.nhn?blogId=helloworld8&logNo=220076589506&parentCategoryNo=&categoryNo=&viewDate=&isShowPopularPosts=false&from=postView
 
*/
