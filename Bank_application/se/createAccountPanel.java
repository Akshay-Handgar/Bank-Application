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

class createAccountPanel extends JPanel{ //MainFrame �Ҽ�
	JLabel createAccountLabel = new JLabel("create Account"); //���� ��
	
	JPanel totalPanel = new JPanel(); //�Ʒ� �ΰ� ��ģ �г�
	JPanel checkedUserPanel = new JPanel(); //����Ȯ�� �г�
	JPanel accountInfoPanel = new JPanel(); //�����г�
	JPanel BtnPanel = new JPanel(); //�ڷΰ���,������ư �г�
	
	JButton backBtn = new JButton("�ڷΰ���"); //�ڷΰ����ư
	JButton createBtn = new JButton("����"); //������ư
	
	JPanel userInfoPanel = new JPanel(); //�̸� ���� �ֹ� ��ĥ�г�
	JPanel userInfoFieldPanel = new JPanel(); //�̸� ���� �ֹ� �ʵ� ��ĥ�г�
	JLabel nameLabel = new JLabel("��         ��"); //�̸�
	JLabel phoneNumberLabel = new JLabel("��ȭ��ȣ"); //����
	JLabel resisterNumberLabel = new JLabel("�ֹι�ȣ"); //�ֹ�
	static JTextField nameField = new JTextField(10); 
	static JTextField phoneNumberField = new JTextField(10);
	static JTextField resisterNumberField = new JTextField(10);
	static JButton userCheckBtn = new JButton("����Ȯ��");
	
	JPanel accountPasswdPanel = new JPanel(); //���¹�ȣ,��� ��ĥ�г�
	JPanel accountPasswdFieldPanel = new JPanel(); //���¹�ȣ,����� �ʵ带 ��ĥ �г�
	JLabel accountNumberLabel = new JLabel("���¹�ȣ"); //���¹�ȣ
	JLabel passwdLabel = new JLabel("��й�ȣ"); //���
	JLabel checkedPasswdLabel = new JLabel("��й�ȣ���Է�"); //���Ȯ��
	static JTextField accountNumberField = new JTextField(10); 
	static JPasswordField passwdField = new JPasswordField(10);
	static JPasswordField checkedPasswdField = new JPasswordField(10);
	static JButton reCreate = new JButton("��й�ȣ Ȯ��"); //���¹�ȣ,��й�ȣ �������ư
	
	JPanel hobbyPanel = new JPanel(); //��� �κ�
	JRadioButton[] rb = new JRadioButton[4]; //����, �, ��ȭ, ���̰���
	static ButtonGroup radioGroup = new ButtonGroup();
	
	createAccountPanel(){
		setLayout(new BorderLayout());
		add(createAccountLabel,BorderLayout.NORTH);
		createAccountLabel.setHorizontalAlignment(JLabel.CENTER); //�� �߾�����
		
		totalPanel.setLayout(new BoxLayout(totalPanel,BoxLayout.Y_AXIS));
		
		//�̸���ȭ��ȣ�ֹι�ȣ �κ� �г�
		checkedUserPanel.setLayout(new GridLayout(0, 3)); //�� .... 
		
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
		checkedUserPanel.add(userCheckBtn); //����Ȯ�� ��ư
		
		//���¹�ȣ ��й�ȣ �κ��г�
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
		accountInfoPanel.add(reCreate); //����� ��ư
		
		//��� �κ� �г�, ����, �, ��ȭ, ���̰���
		hobbyPanel.setBorder(BorderFactory.createTitledBorder("��� ����"));
		
		rb[0] = new JRadioButton("����");
		rb[1] = new JRadioButton("�");
		rb[2] = new JRadioButton("��ȭ");
		rb[3] = new JRadioButton("����");
		
		radioGroup.add(rb[0]);
		radioGroup.add(rb[1]);
		radioGroup.add(rb[2]);
		radioGroup.add(rb[3]);

		hobbyPanel.add(rb[0]);
		hobbyPanel.add(rb[1]);
		hobbyPanel.add(rb[2]);
		hobbyPanel.add(rb[3]);
		
		//�ڷΰ���, ������ư �г�
		backBtn.addMouseListener(new MyMouseAdapter());
		createBtn.addMouseListener(new MyMouseAdapter());
		
		BtnPanel.add(backBtn);
		BtnPanel.add(createBtn);
		
		//��ü ������ ����
		totalPanel.add(checkedUserPanel); //�̸� ��ȭ��ȣ �ֹι�ȣ
		totalPanel.add(accountInfoPanel); //���¹�ȣ ��й�ȣ ���Է�
		totalPanel.add(hobbyPanel); //���
		totalPanel.add(BtnPanel); //���, ����
		
		add(totalPanel,BorderLayout.CENTER);
		
		//������ ��Ȱ��ȭ
		reCreate.setEnabled(false);
		accountNumberField.setEditable(false);
		passwdField.setEditable(false);
		checkedPasswdField.setEditable(false);
	}
	
	static void init(){ //�ʱ�ȭ
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
			if(e.getSource() == userCheckBtn){ //���� Ȯ�� ��ư
				if(userCheckBtn.isEnabled()){
					if(nameField.getText().equals("")){ } 
					else{ //�̸� �г��� null�� �ƴϸ�
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
			else if(e.getSource() == reCreate){ //��й�ȣ Ȯ�� ��ư
				if(reCreate.isEnabled()){
					if(passwdField.getText().equals(checkedPasswdField.getText())){ //����� ������
						if(passwdField.getText().length() == 4){
							reCreate.setEnabled(false);
						}
						else{
							JOptionPane.showMessageDialog(SE.mf, "��й�ȣ�� 4�ڸ��� ������ּ���!", "���� ���� �˶�", JOptionPane.WARNING_MESSAGE);
						}
					}
					else{ //����� �ٸ���
						JOptionPane.showMessageDialog(SE.mf, "��й�ȣȭ Ȯ���� �ٸ��ϴ�!", "���� ���� �˶�", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
			else if(e.getSource() == createBtn){ //����
				if(reCreate.isEnabled() || userCheckBtn.isEnabled()){ //�Ÿ���
					JOptionPane.showMessageDialog(SE.mf, "Ȯ�� ��ư�� �����ּ���!", "���� ���� �˶�", JOptionPane.WARNING_MESSAGE);
				}
				else{
					if(radioGroup.getSelection() != null){ //��� üũ �Ϸ�
						String str = null;
						
						if(rb[0].isSelected()){ str = "1"; }
						else if(rb[1].isSelected()){ str = "2"; }
						else if(rb[2].isSelected()){ str = "3"; }
						else if(rb[3].isSelected()){ str = "4"; }
						
						SE.createUser(nameField.getText(), accountNumberField.getText(), passwdField.getText(), str);
						JOptionPane.showMessageDialog(SE.mf, "���� �Ϸ�!", "���� ���� �˶�", JOptionPane.INFORMATION_MESSAGE);
						backBtn.doClick();
					}
					else{
						JOptionPane.showMessageDialog(SE.mf, "��̸� �������ּ���!", "���� ���� �˶�", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		}
	}
}
