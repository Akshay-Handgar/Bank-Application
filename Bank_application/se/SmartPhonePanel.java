package se;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

class SmartPhonePanel extends JPanel { //����Ʈ�� ���� ������, ���� ������ ���� ���� �� �� �ִ�.
	JLabel smartPhoneLabel = new JLabel("Android");
	JPanel totalPanel = new JPanel(); //�ܾ�,���¹�ȣ,����
	JPanel pictogramPanel = new JPanel(); //����׷�
	
	JPanel balancePanel = new JPanel(); //�ܾ�
	JPanel accountNumberPanel = new JPanel(); //���¹�ȣ
	JPanel benefitPanel = new JPanel(); //����
	
	JButton myBenefitBtn = new JButton("�� ���� ����");
	JButton bankBtn = new JButton("����");
	JButton homeBtn = new JButton("HOME");
	static JButton interworkBtn = new JButton("�����ϱ�"); //SE���� ���
	
	Border balanceBorder = BorderFactory.createTitledBorder("�ܾ�");
	Border acoountBorder = BorderFactory.createTitledBorder("����");
	Border benefitBorder = BorderFactory.createTitledBorder("�� ���� ����");
	
	static JLabel userName = new JLabel("�� ��"); 
	static JLabel userAccountNumber = new JLabel("���� ��ȣ"); 
	static JLabel balanceLabel = new JLabel("�ܾ�"); 
	static JLabel benefit = new JLabel("����");
	
	public SmartPhonePanel(CardLayout cardLayout, Container c){
		setLayout(new BorderLayout());
		add(smartPhoneLabel,BorderLayout.NORTH);
		smartPhoneLabel.setHorizontalAlignment(JLabel.CENTER); //�� �߾�����
		add(totalPanel,BorderLayout.CENTER);
		add(pictogramPanel,BorderLayout.SOUTH);
		
		totalPanel.setLayout(new GridLayout(3,0));
		totalPanel.add(balancePanel);
		totalPanel.add(accountNumberPanel);
		totalPanel.add(benefitPanel);
		
		balancePanel.setBorder(balanceBorder);
		accountNumberPanel.setBorder(acoountBorder);
		benefitPanel.setBorder(benefitBorder);
		
		balancePanel.add(balanceLabel);
		accountNumberPanel.add(userAccountNumber);
		benefitPanel.add(benefit);
		benefitPanel.add(myBenefitBtn);
		
		myBenefitBtn.addActionListener(new ActionListener(){ //�� ���� ��ư
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == myBenefitBtn){ //�α��� ����, �� ���� �ʱ�ȭ, Ȩȭ������
					String account = userAccountNumber.getText();
					String str[] = {"���� ����", "���� ����", "� ����", "��ȭ ����", "���� ����"}; //���� ����
					int benefit = SE.getAccountBenefit(account);
					
					JOptionPane.showMessageDialog(benefitPanel, str[benefit], "�� ����", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		homeBtn.addActionListener(new ActionListener(){ //Ȩ ��ư
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == homeBtn){ //�α��� ����, �� ���� �ʱ�ȭ, Ȩȭ������
					SE.smartLoginState(false); //�α��� ����
					cardLayout.show(c, "access"); //���� ȭ������ �ٲٱ�
				}
			}
		});
		
		interworkBtn.addActionListener(new ActionListener(){ //���� ��ư
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == interworkBtn){
					if(SE.smartInterworkState == false){ //���� ���� ���� -> �����ϱ�
						SE.smartInterworkState(true);
					}
					else{ //������ ���� -> ���� ����
						SE.smartInterworkState(false);
					}
				}
			}
		});
		
		pictogramPanel.add(bankBtn);
		pictogramPanel.add(homeBtn);
		pictogramPanel.add(interworkBtn);
	}
	
	static void initUserInfo(String account){ //�α��ν� �� ���� �޾ƿ���
		userName.setText(SE.getAccountName(account));
		userAccountNumber.setText(account);
		balanceLabel.setText(SE.getAccountBalance(account) + " ��");
		
		String str[] = {"���� ����", "����!", "�!", "��ȭ!", "���̰���!"};
		benefit.setText(str[SE.monthBenefit]);
	}
	
	static void delUserInfo(){ //�α׾ƿ��� �� ���� �����
		userName.setText("�� ���� ����");
		userAccountNumber.setText("�� ���� ����");
		balanceLabel.setText("�� ���� ����");
		benefit.setText("�� ���� ����");
	}

	static void setBalance(int balance){ //�ܾ� ����
		balanceLabel.setText(balance + " ��");
	}
}


