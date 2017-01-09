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


class atmPanel extends JPanel { // MainFrame �Ҽ�
	JLabel welcomeLabel = new JLabel("Welcome to National Korean Bank [ ATM ]");
	
	ImageIcon main_gif = new ImageIcon(".\\Images\\MainPageImg\\main_frame_gif.gif");
	
	JButton cashOut = new JButton("Cash Out");	//���
	JButton cashIn = new JButton("Cash In");	//�Ա�		
	JButton cashMove = new JButton("Cash Transfer");	//������ü
	JButton useCash = new JButton("Usage");	//��볻��
	JButton checkCash = new JButton("checkCash");	//�ܾ�Ȯ��
	
	JPanel mixedPanel = new JPanel(); //atmPanel�� �Ϻ� ��� ����� ���� �г�
	JPanel funcPanel = new JPanel(); //�Ա�,���,������ü,��볻��,�ܾ�Ȯ��
	JPanel imagePanel = new JPanel(); //�̹��� �г�
	JPanel crtAccountPanel = new JPanel(); //���� ���� �г�
	
	Border border = BorderFactory.createTitledBorder(""); // �׵θ��߰�
	JLabel imageLabel = new JLabel();
	
	JButton createAccountBtn = new JButton("createAccount"); //���»�����ư
	

	
	createAccountPanel cap = null;
	atmPanel() {
		setLayout(new BorderLayout());
		welcomeLabel.setHorizontalAlignment(JLabel.CENTER); //�� �߾�����
		add(welcomeLabel,BorderLayout.NORTH);
		add(mixedPanel,BorderLayout.CENTER);
		
		//funcPanel.setBorder(border);
		//imagePanel.setBorder(border);
		//crtAccountPanel.setBorder(border);
		
		mixedPanel.setLayout(new BorderLayout()); //�Ʒ��κ� �г��� 3����
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
		
		//imagePanel �̹����κ�
		
		crtAccountPanel.setLayout(new GridLayout(8,0));
		crtAccountPanel.add(createAccountBtn);
		
		checkCash.addMouseListener(new checkCashClicked());
		
	}
	
	private class checkCashClicked extends MouseAdapter { //�ܾ� Ȯ��!
		public void mouseClicked(MouseEvent c) {
			if(c.getSource() == checkCash){
				if(SE.smartInterworkState){ //���� ���̸� ����Ʈ�� �ܾ�
					JOptionPane.showMessageDialog(mixedPanel, "�ܾ��� " + SE.getAccountBalance(SmartPhonePanel.userAccountNumber.getText()) + "�� �Դϴ�");
				}
				else{ //���� ���� -> �����ϱ� or ���� or ���
					Object[] options = {"�����ϱ�", "����", "���"};
					int select = JOptionPane.showOptionDialog(mixedPanel, 
						"�����Ͻðڽ��ϱ�?", "���� ���� ����", 
						JOptionPane.YES_NO_CANCEL_OPTION, 
						JOptionPane.QUESTION_MESSAGE, 
						null, options, options[2]);
					
					if(select == 0){//�����ϱ�
						if(SE.smartState == false){ //����Ʈ�� ����������
								JOptionPane.showMessageDialog(mixedPanel, "����Ʈ���� ���ּ���", "����Ʈ�� ����", JOptionPane.WARNING_MESSAGE);
						}
						else if(SE.smartLoginState == false){ //�α��� �ȵ�����
							JOptionPane.showMessageDialog(mixedPanel, "�α��� ���ּ���", "�α��� ����", JOptionPane.WARNING_MESSAGE);
						}
						else{ //�����ϱ�
							SE.smartInterworkState(true); //�����ϱ�
							
							JOptionPane.showMessageDialog(mixedPanel, "�ܾ��� " + SE.getAccountBalance(SmartPhonePanel.userAccountNumber.getText())+"�� �Դϴ�");
						}
					}
					else if(select == 1){ //����
						String id = JOptionPane.showInputDialog(mixedPanel, "ID�� �Է����ּ���.", "���� �Է�", JOptionPane.QUESTION_MESSAGE);
						
						if(id != null){ //�Է��� ������
							JPasswordField passwd = new JPasswordField();
							int ok = JOptionPane.showConfirmDialog(mixedPanel, passwd, "Enter Password", JOptionPane.WARNING_MESSAGE);
							
							if(ok == JOptionPane.OK_OPTION){ //Ȯ�� ��ư ������ ��
								String user = SE.checkUserInfo(id, passwd.getText());
								
								if(user == null){ //���� �ƴϴ� -> ���� ���� �˸�
									JOptionPane.showMessageDialog(mixedPanel, "���̵� �Ǵ� ��й�ȣ�� �ٽ� �Է��� �ּ���", "�α��� ����", JOptionPane.WARNING_MESSAGE);
								}
								else{ //���̴�!! -> �ŷ�, ����Ʈ���� ���� ���¸� �˶� �߰�
									JOptionPane.showMessageDialog(mixedPanel, "�ܾ��� " + SE.getAccountBalance(user)+"�� �Դϴ�");
								}
							}
						}
					}
					else if(select == 2){ } //���
				}
			}
		}
	}
}
