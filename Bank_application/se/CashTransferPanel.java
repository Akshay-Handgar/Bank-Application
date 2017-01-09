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
	JLabel account = new JLabel("����");
	JLabel money = new JLabel("�ݾ�");
	JLabel passwd = new JLabel("���");
	
	static JTextField accountTF = new JTextField(13);
	static JTextField moneyTF = new JTextField(13);
	static JPasswordField passwdTF = new JPasswordField(13);
	
	
	JButton cashTransferBtn = new JButton("�۱�");
	JButton backBtn = new JButton("�ڷΰ���");
	
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
				
		//��ư�߰�
		btnPanel.add(cashTransferBtn);
		btnPanel.add(backBtn);
		
		//�г��߰�
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
				JOptionPane.showMessageDialog(accountPanel, "��ĭ�� �Է����ּ���", "�ŷ� ����", JOptionPane.WARNING_MESSAGE);
			}
			else{
				if(e.getSource() == cashTransferBtn){
					result=Integer.parseInt(moneyTF.getText());
					if(SE.smartInterworkState){ //���� ���̸� ����Ʈ������ ���
						JOptionPane.showMessageDialog(accountPanel, "�ŷ� �Ϸ�!", "�ŷ� �˶�", JOptionPane.INFORMATION_MESSAGE);
						SE.banking(SmartPhonePanel.userAccountNumber.getText(), SE.getAccountName(SmartPhonePanel.userAccountNumber.getText())+" ATM �۱�", result, '-', true); //�����ȴ뼭�� ���
						SE.banking(accountTF.getText(), SE.getAccountName(accountTF.getText()) +" ATM �Ա�", result, '+', false); //�޴��� �Ա�
					}
					else{ //���� ���� -> �����ϱ� or ���
						Object[] options = {"�����ϱ�", "����", "���"};
						int select = JOptionPane.showOptionDialog(accountPanel, 
								"�����Ͻðڽ��ϱ�?", "���� ���� ����", 
								JOptionPane.YES_NO_CANCEL_OPTION, 
								JOptionPane.QUESTION_MESSAGE, 
								null, options, options[2]);
						
						if(select == 0){//�����ϱ�
							if(SE.smartState == false){ //����Ʈ�� ����������
								JOptionPane.showMessageDialog(accountPanel, "����Ʈ���� ���ּ���", "����Ʈ�� ����", JOptionPane.WARNING_MESSAGE);
							}
							else if(SE.smartLoginState == false){ //�α��� �ȵ�����
								JOptionPane.showMessageDialog(accountPanel, "�α��� ���ּ���", "�α��� ����", JOptionPane.WARNING_MESSAGE);
							}
							else{ //�����ϱ�
								SE.smartInterworkState(true); //�����ϱ�
								JOptionPane.showMessageDialog(accountPanel, "�ŷ� �Ϸ�!", "�ŷ� �˶�", JOptionPane.INFORMATION_MESSAGE);
								SE.banking(SmartPhonePanel.userAccountNumber.getText(), SE.getAccountName(SmartPhonePanel.userAccountNumber.getText())+" ATM �۱�", result, '-', true); //�����ȴ뼭�� ���
								SE.banking(accountTF.getText(), SE.getAccountName(accountTF.getText()) +" ATM �Ա�", result, '+', false); //�޴��� �Ա�
							}
						}
						else if(select == 1){ //����
							String id = JOptionPane.showInputDialog(accountPanel, "ID�� �Է����ּ���.", "���� �Է�", JOptionPane.QUESTION_MESSAGE);
							
							if(id != null){ //�Է��� ������
								JPasswordField passwd = new JPasswordField();
								int ok = JOptionPane.showConfirmDialog(accountPanel, passwd, "Enter Password", JOptionPane.WARNING_MESSAGE);
								
								if(ok == JOptionPane.OK_OPTION){ //Ȯ�� ��ư ������ ��
									String user = SE.checkUserInfo(id, passwd.getText());
									
									if(user == null){ //���� �ƴϴ� -> ���� ���� �˸�
										JOptionPane.showMessageDialog(accountPanel, "���̵� �Ǵ� ��й�ȣ�� �ٽ� �Է��� �ּ���", "�α��� ����", JOptionPane.WARNING_MESSAGE);
									}
									else{ //���̴�!! -> �ŷ�, ����Ʈ���� ���� ���¸� �˶� �߰�
										if((SE.smartLoginState == true) && (SmartPhonePanel.userAccountNumber.getText().equals(user))){ 
											JOptionPane.showMessageDialog(accountPanel, "�ŷ� �Ϸ�!", "�ŷ� �˶�", JOptionPane.INFORMATION_MESSAGE);
											SE.banking(user, SE.getAccountName(user)+" ATM �۱�", result, '-', true); //�����ȴ뼭�� ���
											SE.banking(accountTF.getText(), SE.getAccountName(accountTF.getText()) +" ATM �Ա�", result, '+', false); //�޴��� �Ա�
	
										}
										else{
											JOptionPane.showMessageDialog(accountPanel, "�ŷ� �Ϸ�!", "�ŷ� �˶�", JOptionPane.INFORMATION_MESSAGE);
											SE.banking(user, SE.getAccountName(user)+" ATM �۱�", result, '-', false); //�����ȴ뼭�� ���
											SE.banking(accountTF.getText(), SE.getAccountName(accountTF.getText()) +" ATM �Ա�", result, '+', false); //�޴��� �Ա�
	
										}
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

}

