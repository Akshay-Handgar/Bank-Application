package se;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;			
import java.util.regex.*;	

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
	
	JButton ThirtyTW = new JButton("30000       ￦");
	JButton FiftyTW = new JButton("50000       ￦");
	JButton HTW = new JButton("100000     ￦");
	JButton HFiftyTW = new JButton("150000     ￦");
	JButton TwoHTW = new JButton("200000     ￦");
	JButton ThreeHTW = new JButton("300000     ￦");

	JButton FourHTW = new JButton("400000  ￦");
	JButton FiveHTW = new JButton("500000  ￦");
	JButton SixHTW = new JButton("600000   ￦");
	JButton SevenHTW = new JButton("700000   ￦");
	JButton MW = new JButton("1000000  ￦");
	JButton ETC = new JButton("기               타");

	JPanel mixedPanel = new JPanel(); // cashOutPanel의 가장 외곽 패널
	JPanel C_OutWarningPanel = new JPanel(); // 출금패널 경고 메세지 패널_가운데
	JPanel LCashoutPanel = new JPanel(); // 출금 금액 선택 패널_왼쪽
	JPanel RCashoutPanel = new JPanel(); // 출금 금액 선택 패널_오른쪽
	JPanel C_OUTPanel = new JPanel(); // 금액 표시 및 출금버튼 패널
	
	protected JTextField ETC_Field;

	DefaultListModel payModel = new DefaultListModel();

	int result = 0;
	JLabel resultLabel = new JLabel("출금 금액 : " + result + " 원");
	JButton C_OUTBtn = new JButton("Cash Out"); // 출금 버튼
	JButton cancelBtn = new JButton("Cancel"); // 취소 버튼
	JButton backBtn = new JButton("뒤로가기"); //뒤로가기 버튼
	
	Border border = BorderFactory.createTitledBorder(""); // 테두리 추가

	cashOutPanel() {
		setLayout(new BorderLayout());
		cashoutLabel.setHorizontalAlignment(JLabel.CENTER); 
		add(cashoutLabel, BorderLayout.NORTH); //
		add(mixedPanel, BorderLayout.CENTER);

		mixedPanel.setLayout(new BorderLayout()); 
		mixedPanel.add(C_OutWarningPanel, BorderLayout.CENTER);
		mixedPanel.add(LCashoutPanel, BorderLayout.WEST);
		mixedPanel.add(RCashoutPanel, BorderLayout.EAST);
		mixedPanel.add(C_OUTPanel, BorderLayout.SOUTH);
		
		C_OutWarningPanel.setBorder(border);
		C_OutWarningPanel.add(imageLabel);
		imageLabel.setIcon(cashoutImage);

		C_OUTPanel.setLayout(new GridLayout(3, 0, 5, 5));
		resultLabel.setHorizontalAlignment(JLabel.CENTER); 
		C_OUTPanel.add(resultLabel, BorderLayout.NORTH); // 출금 금액 표시 레이블
		C_OUTPanel.add(C_OUTBtn); // 출금 버튼
		C_OUTPanel.add(backBtn); // 뒤로가기 버튼
		
		

		C_OUTBtn.addMouseListener(new CashOutClicked()); // CashOut버튼 클릭 할때 반응

		LCashoutPanel.setLayout(new GridLayout(6, 0, 5, 5)); 
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

		RCashoutPanel.setLayout(new GridLayout(6, 0, 5, 5)); 
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
			System.out.println("금액 선택됨");
			if(c.getSource() == ThirtyTW){
				if(SE.smartState == true){
					result = 30000;
					resultLabel.setText("출금 금액 : " + result + " 원");}}
			else if(c.getSource() == FiftyTW){
				if(SE.smartState == true){
					result = 50000;
					resultLabel.setText("출금 금액 : " + result + " 원");}}
			else if(c.getSource() == HTW){
				if(SE.smartState == true){
					result = 100000;
					resultLabel.setText("출금 금액 : " + result + " 원");}}
			else if(c.getSource() == HFiftyTW){
				if(SE.smartState == true){
					result = 150000;
					resultLabel.setText("출금 금액 : " + result + " 원");}}
			else if(c.getSource() == TwoHTW){
				if(SE.smartState == true){
					result = 200000;
					resultLabel.setText("출금 금액 : " + result + " 원");}}
			else if(c.getSource() == ThreeHTW){
				if(SE.smartState == true){
					result = 300000;
					resultLabel.setText("출금 금액 : " + result + " 원");}}
			
			if(c.getSource() == FourHTW){
				if(SE.smartState == true){
					result = 400000;
					resultLabel.setText("출금 금액 : " + result + " 원");}}
			else if(c.getSource() == FiveHTW){
				if(SE.smartState == true){
					result = 500000;
					resultLabel.setText("출금 금액 : " + result + " 원");}}
			else if(c.getSource() == SixHTW){
				if(SE.smartState == true){
					result = 600000;
					resultLabel.setText("출금 금액 : " + result + " 원");}}
			else if(c.getSource() == SevenHTW){
				if(SE.smartState == true){
					result = 700000;
					resultLabel.setText("출금 금액 : " + result + " 원");}}
			else if(c.getSource() == MW){
				if(SE.smartState == true){
					result = 1000000;
					resultLabel.setText("출금 금액 : " + result + " 원");}}
			
			else if(c.getSource() == ETC){
				if(SE.smartState == true){
					input = JOptionPane.showInputDialog(mixedPanel, "금액을 입력하세요", "예) 100000"); //패널의 가운데에 대화상자 생성
					System.out.println("\"" + input + "\"" + "의 금액을 입력하였습니다.");
					result = Integer.parseInt(input);
					resultLabel.setText("출금 금액 : " + result + " 원");
				}
				
			}
		}
	}
	
	private class CashOutClicked extends MouseAdapter{ //CashOut utton
		public void mouseClicked(MouseEvent e) {
			if(e.getSource() == C_OUTBtn){
				
				if(result == 0){ 
					JOptionPane.showMessageDialog(mixedPanel, "금액을 설정하십시오!", "출금 오류", JOptionPane.WARNING_MESSAGE);
				}
				else{					
					if(SE.smartInterworkState){ 
						if(SE.banking(SmartPhonePanel.userAccountNumber.getText(), "ATM", result, '-', true)){ //입금을 할시엔 '-'를 +로
							System.out.println("거래 완료!" + result + "출금 되었습니다."); 						
} // 미완료시 false
					}
					else{ 
						Object[] options = {"연동하기", "취소"};
						int select = JOptionPane.showOptionDialog(mixedPanel, 
								"연동하시겠습니까?", "결제 오류", 
								JOptionPane.YES_NO_CANCEL_OPTION, 
								JOptionPane.QUESTION_MESSAGE, 
								null, options, options[1]);
						
						if(select == 1){ } //취소
						else{ //연동하기
							if(SE.smartState == false){ 
								JOptionPane.showMessageDialog(mixedPanel, "스마트폰을 켜주세요", "스마트폰 오류", JOptionPane.WARNING_MESSAGE);
							}
							else if(SE.smartLoginState == false){ 
								JOptionPane.showMessageDialog(mixedPanel, "로그인 해주세요", "로그인 오류", JOptionPane.WARNING_MESSAGE);
							}
							else{ 
								SE.smartInterworkState(true);
								
								if(SE.banking(SmartPhonePanel.userAccountNumber.getText(), "ATM", result, '-', true)){
								}
							}
						}
					}
				}
			}
		}
	}
}
