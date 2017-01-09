package se;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;			// �Է� �������� ���� �Ǻ�
import java.util.regex.*;	// �Է� �������� ���� �ĺ�

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.JOptionPane;

class cashInPanel extends JPanel {
	
	String input = "";
	
	ImageIcon cashoutImage = new ImageIcon(".\\Images\\Cashin\\cashInimage.jpg");
	JLabel imageLabel = new JLabel();
	
	JLabel cashinLabel = new JLabel("Choose the amount of money you want to put in.");

	JButton CashingIn = new JButton("��   ��   ��   ��");
	JPanel mixedPanel = new JPanel(); // cashOutPanel�� ���� �ܰ� �г�
	JPanel C_InWarningPanel = new JPanel(); // ����г� ��� �޼��� �г�_���
	JPanel LCashoutPanel = new JPanel(); // ��� �ݾ� ���� �г�_����
	JPanel RCashoutPanel = new JPanel(); // ��� �ݾ� ���� �г�_������
	JPanel C_INPanel = new JPanel(); // �ݾ� ǥ�� �� ��ݹ�ư �г�
	
	protected JTextField CashIn_Field;

	//DefaultListModel payModel = new DefaultListModel();

	int result = 0;
	JLabel resultLabel = new JLabel("�Ա� �ݾ� : " + result + " ��");
	JButton C_INBtn = new JButton("Cash In"); // ��� ��ư
	JButton cancelBtn = new JButton("Cancel"); // ��� ��ư
	JButton backBtn = new JButton("�ڷΰ���"); //�ڷΰ��� ��ư

	Border border = BorderFactory.createTitledBorder(""); // �׵θ� �߰�

	cashInPanel() {
		setLayout(new BorderLayout());
		cashinLabel.setHorizontalAlignment(JLabel.CENTER); // �� �߾�����
		add(cashinLabel, BorderLayout.NORTH); //
		add(mixedPanel, BorderLayout.CENTER);

		C_InWarningPanel.setBorder(border);

		mixedPanel.setLayout(new BorderLayout()); // �Ʒ��κ� �г��� 3���� ����
		mixedPanel.add(C_InWarningPanel, BorderLayout.CENTER);
		mixedPanel.add(LCashoutPanel, BorderLayout.WEST);
		mixedPanel.add(RCashoutPanel, BorderLayout.EAST);
		mixedPanel.add(C_INPanel, BorderLayout.SOUTH);
		
		C_InWarningPanel.add(imageLabel);
		imageLabel.setIcon(cashoutImage);

		// (�ݾ�ǥ�� + ��ݹ�ư �г�)
		C_INPanel.setLayout(new GridLayout(3, 0, 5, 5));
		resultLabel.setHorizontalAlignment(JLabel.CENTER); // �� �߾�����	
		C_INPanel.add(resultLabel); // ��� �ݾ� ǥ�� ���̺�
		C_INPanel.add(C_INBtn); // ��� ��ư
		C_INPanel.add(backBtn); // �ڷΰ��� ��ư
		
		C_INBtn.addMouseListener(new CashInClicked()); // CashOut��ư Ŭ�� �Ҷ� ����

		LCashoutPanel.setLayout(new GridLayout(6, 0, 5, 5)); // ���� 6ĭ ���δ� �Ϸķ�    //������� ��������
																// �������ֱ� ���� 0����																



		RCashoutPanel.setLayout(new GridLayout(6, 0, 5, 5)); // ���_������

		RCashoutPanel.add(CashingIn);
		
		CashingIn.addMouseListener(new Clicked());
	}

	private class Clicked extends MouseAdapter {
		public void mouseClicked(MouseEvent c) {
			System.out.println("�ݾ� �Ա� �ݾ� ������");		
			
			if(c.getSource() == CashingIn){
				if(SE.smartState == true){
					input = JOptionPane.showInputDialog(mixedPanel, "�ݾ��� �Է��ϼ���", "��) 100000"); // ��ȣ ���ʿ� mixedPanel�� �������ν� �г��� ����� ��ȭ���� ����
					System.out.println("\"" + input + "\"" + "�� �ݾ��� �Է��Ͽ����ϴ�.");
					result = Integer.parseInt(input);
					resultLabel.setText("�Ա� �ݾ� : " + result + " ��");
					
					/*   if (Pattern.matches("[0-9]", input)){
						    System.out.printf("����");
							resultLabel.setText("��� �ݾ� : " + result + " ��");    
					   }
						   else if (Pattern.matches("[A-Z]",input))
						    System.out.printf("�빮��");
						   else if (Pattern.matches("[a-z]", input))
						    System.out.printf("�ҹ���");
						   else if (Pattern.matches("[��-�R]", input))
						    System.out.println("�ѱ�");
						   else
						    System.out.printf("��Ÿ");*/
				}
				
			}
		}
	}
	
	private class CashInClicked extends MouseAdapter{ //CashOut ��ư
		public void mouseClicked(MouseEvent e) {
			if(e.getSource() == C_INBtn){
				
				if(result == 0){ //������ �ݾ� ����, #���� 3
					JOptionPane.showMessageDialog(mixedPanel, "�ݾ��� �����Ͻʽÿ�!", "�Ա� ����", JOptionPane.WARNING_MESSAGE);
				}
				else{					
					if(SE.smartInterworkState){ //���� ���̸� ����Ʈ������ ���
						if(SE.banking(SmartPhonePanel.userAccountNumber.getText(), "ATM", result, '+', true)){ //�ŷ� ���� , �Ա��� �ҽÿ� '-'�� +�� �ٲٸ� �ȴ�.
							System.out.println("�ŷ� �Ϸ�!" + result + "�Ա� �Ǿ����ϴ�."); 
						} // �̿Ϸ�� false
					}
					else{ //���� ���� -> �����ϱ� or ���
						Object[] options = {"�����ϱ�", "���"};
						int select = JOptionPane.showOptionDialog(mixedPanel, 
								"�����Ͻðڽ��ϱ�?", "���� ����", 
								JOptionPane.YES_NO_CANCEL_OPTION, 
								JOptionPane.QUESTION_MESSAGE, 
								null, options, options[1]);
						
						if(select == 1){ } //���
						else{ //�����ϱ�
							if(SE.smartState == false){ //����Ʈ�� ����������
								JOptionPane.showMessageDialog(mixedPanel, "����Ʈ���� ���ּ���", "����Ʈ�� ����", JOptionPane.WARNING_MESSAGE);
							}
							else if(SE.smartLoginState == false){ //�α��� �ȵ�����
								JOptionPane.showMessageDialog(mixedPanel, "�α��� ���ּ���", "�α��� ����", JOptionPane.WARNING_MESSAGE);
							}
							else{ //�����ϱ�
								SE.smartInterworkState(true); //�����ϱ�
								
								if(SE.banking(SmartPhonePanel.userAccountNumber.getText(), "ATM", result, '+', true)){ //�ŷ� ����
								}
							}
						}
					}
				}
			}
		}
	}
}