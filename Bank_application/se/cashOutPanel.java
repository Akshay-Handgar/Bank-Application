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

class cashOutPanel extends JPanel {
	
	String input = "";
	
	JLabel cashoutLabel = new JLabel("Choose the amount of money you want to take out.");

	ImageIcon cashoutImage = new ImageIcon(".\\Images\\Cashout\\cashoutImage.jpg");
	JLabel imageLabel = new JLabel();
	
	JButton ThirtyTW = new JButton("30000       ��");
	JButton FiftyTW = new JButton("50000       ��");
	JButton HTW = new JButton("100000     ��");
	JButton HFiftyTW = new JButton("150000     ��");
	JButton TwoHTW = new JButton("200000     ��");
	JButton ThreeHTW = new JButton("300000     ��");

	JButton FourHTW = new JButton("400000  ��");
	JButton FiveHTW = new JButton("500000  ��");
	JButton SixHTW = new JButton("600000   ��");
	JButton SevenHTW = new JButton("700000   ��");
	JButton MW = new JButton("1000000  ��");
	JButton ETC = new JButton("��               Ÿ");

	JPanel mixedPanel = new JPanel(); // cashOutPanel�� ���� �ܰ� �г�
	JPanel C_OutWarningPanel = new JPanel(); // ����г� ��� �޼��� �г�_���
	JPanel LCashoutPanel = new JPanel(); // ��� �ݾ� ���� �г�_����
	JPanel RCashoutPanel = new JPanel(); // ��� �ݾ� ���� �г�_������
	JPanel C_OUTPanel = new JPanel(); // �ݾ� ǥ�� �� ��ݹ�ư �г�
	
	protected JTextField ETC_Field;

	DefaultListModel payModel = new DefaultListModel();

	int result = 0;
	JLabel resultLabel = new JLabel("��� �ݾ� : " + result + " ��");
	JButton C_OUTBtn = new JButton("Cash Out"); // ��� ��ư
	JButton cancelBtn = new JButton("Cancel"); // ��� ��ư
	JButton backBtn = new JButton("�ڷΰ���"); //�ڷΰ��� ��ư
	
	Border border = BorderFactory.createTitledBorder(""); // �׵θ� �߰�

	cashOutPanel() {
		setLayout(new BorderLayout());
		cashoutLabel.setHorizontalAlignment(JLabel.CENTER); // �� �߾�����
		add(cashoutLabel, BorderLayout.NORTH); //
		add(mixedPanel, BorderLayout.CENTER);

		mixedPanel.setLayout(new BorderLayout()); // �Ʒ��κ� �г��� 3���� ����
		mixedPanel.add(C_OutWarningPanel, BorderLayout.CENTER);
		mixedPanel.add(LCashoutPanel, BorderLayout.WEST);
		mixedPanel.add(RCashoutPanel, BorderLayout.EAST);
		mixedPanel.add(C_OUTPanel, BorderLayout.SOUTH);
		
		C_OutWarningPanel.setBorder(border);
		C_OutWarningPanel.add(imageLabel);
		imageLabel.setIcon(cashoutImage);

		// (�ݾ�ǥ�� + ��ݹ�ư �г�)
		C_OUTPanel.setLayout(new GridLayout(3, 0, 5, 5));
		resultLabel.setHorizontalAlignment(JLabel.CENTER); // �� �߾�����	
		C_OUTPanel.add(resultLabel, BorderLayout.NORTH); // ��� �ݾ� ǥ�� ���̺�
		C_OUTPanel.add(C_OUTBtn); // ��� ��ư
		C_OUTPanel.add(backBtn); // �ڷΰ��� ��ư
		
		

		C_OUTBtn.addMouseListener(new CashOutClicked()); // CashOut��ư Ŭ�� �Ҷ� ����

		LCashoutPanel.setLayout(new GridLayout(6, 0, 5, 5)); // ���� 6ĭ ���δ� �Ϸķ�
																// �������ֱ� ���� 0����
																// ����_��� ����
		LCashoutPanel.add(ThirtyTW);
		LCashoutPanel.add(FiftyTW);
		LCashoutPanel.add(HTW);
		LCashoutPanel.add(HFiftyTW);
		LCashoutPanel.add(TwoHTW);
		LCashoutPanel.add(ThreeHTW);

		ThirtyTW.addMouseListener(new Clicked());
		FiftyTW.addMouseListener(new Clicked());
		HTW.addMouseListener(new Clicked());
		HFiftyTW.addMouseListener(new Clicked());
		TwoHTW.addMouseListener(new Clicked());
		ThreeHTW.addMouseListener(new Clicked());

		RCashoutPanel.setLayout(new GridLayout(6, 0, 5, 5)); // ���_������
		RCashoutPanel.add(FourHTW);
		RCashoutPanel.add(FiveHTW);
		RCashoutPanel.add(SixHTW);
		RCashoutPanel.add(SevenHTW);
		RCashoutPanel.add(MW);
		RCashoutPanel.add(ETC);
		
		FourHTW.addMouseListener(new Clicked());
		FiveHTW.addMouseListener(new Clicked());
		SixHTW.addMouseListener(new Clicked());
		SevenHTW.addMouseListener(new Clicked());
		MW.addMouseListener(new Clicked());
		ETC.addMouseListener(new Clicked());
	}

	private class Clicked extends MouseAdapter {
		public void mouseClicked(MouseEvent c) {
			System.out.println("�ݾ� ���õ�");
			if(c.getSource() == ThirtyTW){
				if(SE.smartState == true){
					result = 30000;
					resultLabel.setText("��� �ݾ� : " + result + " ��");}}
			else if(c.getSource() == FiftyTW){
				if(SE.smartState == true){
					result = 50000;
					resultLabel.setText("��� �ݾ� : " + result + " ��");}}
			else if(c.getSource() == HTW){
				if(SE.smartState == true){
					result = 100000;
					resultLabel.setText("��� �ݾ� : " + result + " ��");}}
			else if(c.getSource() == HFiftyTW){
				if(SE.smartState == true){
					result = 150000;
					resultLabel.setText("��� �ݾ� : " + result + " ��");}}
			else if(c.getSource() == TwoHTW){
				if(SE.smartState == true){
					result = 200000;
					resultLabel.setText("��� �ݾ� : " + result + " ��");}}
			else if(c.getSource() == ThreeHTW){
				if(SE.smartState == true){
					result = 300000;
					resultLabel.setText("��� �ݾ� : " + result + " ��");}}
			
			if(c.getSource() == FourHTW){
				if(SE.smartState == true){
					result = 400000;
					resultLabel.setText("��� �ݾ� : " + result + " ��");}}
			else if(c.getSource() == FiveHTW){
				if(SE.smartState == true){
					result = 500000;
					resultLabel.setText("��� �ݾ� : " + result + " ��");}}
			else if(c.getSource() == SixHTW){
				if(SE.smartState == true){
					result = 600000;
					resultLabel.setText("��� �ݾ� : " + result + " ��");}}
			else if(c.getSource() == SevenHTW){
				if(SE.smartState == true){
					result = 700000;
					resultLabel.setText("��� �ݾ� : " + result + " ��");}}
			else if(c.getSource() == MW){
				if(SE.smartState == true){
					result = 1000000;
					resultLabel.setText("��� �ݾ� : " + result + " ��");}}
			
			else if(c.getSource() == ETC){
				if(SE.smartState == true){
					input = JOptionPane.showInputDialog(mixedPanel, "�ݾ��� �Է��ϼ���", "��) 100000"); // ��ȣ ���ʿ� mixedPanel�� �������ν� �г��� ����� ��ȭ���� ����
					System.out.println("\"" + input + "\"" + "�� �ݾ��� �Է��Ͽ����ϴ�.");
					result = Integer.parseInt(input);
					resultLabel.setText("��� �ݾ� : " + result + " ��");
					
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
	
	private class CashOutClicked extends MouseAdapter{ //CashOut ��ư
		public void mouseClicked(MouseEvent e) {
			if(e.getSource() == C_OUTBtn){
				
				if(result == 0){ //������ �ݾ� ����, #���� 3
					JOptionPane.showMessageDialog(mixedPanel, "�ݾ��� �����Ͻʽÿ�!", "��� ����", JOptionPane.WARNING_MESSAGE);
				}
				else{					
					if(SE.smartInterworkState){ //���� ���̸� ����Ʈ������ ���
						if(SE.banking(SmartPhonePanel.userAccountNumber.getText(), "ATM", result, '-', true)){ //�ŷ� ���� , �Ա��� �ҽÿ� '-'�� +�� �ٲٸ� �ȴ�.
							System.out.println("�ŷ� �Ϸ�!" + result + "��� �Ǿ����ϴ�."); 
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
								
								if(SE.banking(SmartPhonePanel.userAccountNumber.getText(), "ATM", result, '-', true)){ //�ŷ� ����
								}
							}
						}
					}
				}
			}
		}
	}
}