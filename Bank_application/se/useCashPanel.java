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
	JButton backBtn = new JButton("뒤로가기");
	
	static String account = null;
	
	Border border = BorderFactory.createTitledBorder(""); 
	
	useCashPanel(){		
		setLayout(new BorderLayout(30, 30));
		
		userName.setHorizontalAlignment(JLabel.CENTER); 
		userBalance.setHorizontalAlignment(JLabel.CENTER); 
		
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
		userName.setText(SE.getAccountName(account) + "님의 사용 내역 입니다.");
		userBalance.setText("잔액 : " + SE.getAccountBalance(account) + " 원");
	}
	
	static void setTable(){
		totalPanel.removeAll(); 
		
		String str = SE.setTable(account);
		String strLine[] = str.split("\\|"); 
		
		String header[] = {"유형", "금액", "내용", "날짜", "시간"};
		String data[][] = new String[strLine.length][header.length];
		
		for(int i=0;i<strLine.length;i++){
			String s[] = strLine[i].split("_"); 
			
			for(int j=0;j<s.length;j++){
				data[i][j] = s[j];
			}
		}
		
		JTable table = new JTable(data, header);
		totalPanel.add(table, BorderLayout.CENTER);
		
		JScrollPane scrollpane = new JScrollPane(table);
		totalPanel.add(scrollpane, BorderLayout.CENTER);
		
		table.setPreferredScrollableViewportSize(new Dimension(430,150));
	}
}
