package se;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;

class useCashPanel extends JPanel{
	static JPanel totalPanel = new JPanel();
	JPanel labelPanel = new JPanel();
	static JLabel userName = new JLabel();
	static JLabel userBalance = new JLabel();
	JButton backBtn = new JButton("�ڷΰ���");
	
	static String account = null;
	
	Border border = BorderFactory.createTitledBorder(""); // �׵θ��߰�
	
	useCashPanel(){		
		setLayout(new BorderLayout(30, 30));
		
		userName.setHorizontalAlignment(JLabel.CENTER); //�� �߾�����
		userBalance.setHorizontalAlignment(JLabel.CENTER); //�� �߾�����
		
		labelPanel.setLayout(new GridLayout(2,0));
		labelPanel.add(userName);
		labelPanel.add(userBalance);
		totalPanel.setBorder(border);
		
		add(labelPanel, BorderLayout.NORTH);
		add(totalPanel, BorderLayout.CENTER);
		add(backBtn, BorderLayout.SOUTH);
	}
	
	static void setAccount(String str){
		account = str;
		setTable();
		userName.setText(SE.getAccountName(account) + "���� ��� ���� �Դϴ�.");
		userBalance.setText("�ܾ� : " + SE.getAccountBalance(account) + " ��");
	}
	
	static void setTable(){
		totalPanel.removeAll(); //�г� �ʱ�ȭ
		
		String str = SE.setTable(account);
		String strLine[] = str.split("\\|"); //�� ����
		
		String header[] = {"����", "�ݾ�", "����", "��¥", "�ð�"};
		String data[][] = new String[strLine.length][header.length];
		
		for(int i=0;i<strLine.length;i++){
			String s[] = strLine[i].split("_"); //���� ����
			
			for(int j=0;j<s.length;j++){
				data[i][j] = s[j];
			}
		}
		
		//���̴� ���� �߿�! table ���̰� -> ��ũ�� ���� -> ��ũ�� ���̱�
		JTable table = new JTable(data, header);
		totalPanel.add(table, BorderLayout.CENTER);
		
		JScrollPane scrollpane = new JScrollPane(table);
		totalPanel.add(scrollpane, BorderLayout.CENTER);
		
		table.setPreferredScrollableViewportSize(new Dimension(430,150));
	}
}
