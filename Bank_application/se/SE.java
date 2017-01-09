package se;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

/* SE - main Ŭ����, smartPhoneFrame ���ݱ� & ���� ����� ����
 * 
 * �޼ҵ� ���� ����
 * smartState - ����Ʈ�� ������ ���� �ٲٱ�
 * smartLoginState - ����Ʈ�� �α��� ���� �ٲٱ�
 * smartInterworkState - ����Ʈ�� ���� ���� �ٲٱ�
 * openSmart - ����Ʈ�� ������ ����
 * closeSmart - ����Ʈ�� ������ �ݱ�
 * 
 * setInterest - ���� ����ϴ� �޼ҵ�
 * 
 * checkUserInfo - ���̵�� ��й�ȣ�� �޾� ������ �Ǵ��ϴ� �޼ҵ� 
 * getAccountName - ���¹�ȣ�� �� ���� ��ȯ�ϴ� �޼ҵ� 
 * getAccountBalance - ���¹�ȣ�� �ܾ��� ��ȯ�ϴ� �޼ҵ� 
 * getAccountBenefit - ���¹�ȣ�� ������ ��ȯ�ϴ� �޼ҵ� 
 * banking - ���� �ŷ��� �ϴ� �޼ҵ�
 * setAccountInfo - ������ ������ ���� �޼ҵ� 
 * getMonthBenefit - �� ���� ���� �ҷ�����
 * 
 * smartPhoneMessage - ����Ʈ���� �˾� ����
 * loadDate - ���� ��¥ �ҷ�����
 * loadTime - ���� �ð� �ҷ�����
 */
public class SE { //Software Engineer
	static boolean smartState = false; //����Ʈ�� ������ ���� (���� �� or ����), �ٸ� Ŭ�������� ����Ϸ��� static�� �پ���Ѵ�.
	static boolean smartLoginState = false; //����Ʈ�� �α��� ���� (�α��� or �α� �ƿ�)
	static boolean smartInterworkState = false; //����Ʈ�� ���� ���� (���� �� or ����)
	static MainFrame mf = null;
	static SmartPhoneFrame spf = null;
	
	static String nowDate = null; //���� ��¥�� �����ϴ� �Լ�, ��꼭�� ���� ������ �ð� ���� ���ֱ� ���� ���
	static String nowTime = null; //���� �ð��� �����ϴ� �Լ�
	static int monthBenefit = 0; //�̴��� ����
	
	public static void main(String[] args) {
		setInterest(); //���� ���
		monthBenefit = getMonthBenefit(); //�̴��� ���� �ޱ�
		openSmart(); //����Ʈ�� ������ ����
		mf = new MainFrame(); //�⺻ ������ (atmPanel, createAccountPanel, shopPanel���� ����)
	}
	
	//���� ��ȭ �޼ҵ� *******
	static void smartState(boolean state){ //����Ʈ�� ������ ���� �ٲٱ�, ����Ʈ ���������� - ���� ��Ű�� + �ڱ� ��ü �� #���� 1
		smartState = state;
	}
	
	static void smartLoginState(boolean state){ //����Ʈ�� �α��� ���� �ٲٱ�
		smartLoginState = state;
		
		if(smartLoginState == false){ //�α׾ƿ��ϸ�
			smartInterworkState(false); //������ Ǯ����
		}
	}
	
	static void smartInterworkState(boolean state){ //����Ʈ�� ���� ���� �ٲٱ�
		smartInterworkState = state;
		
		if(state){ //���� ��..
			SmartPhonePanel.interworkBtn.setText("���� ��..");
		}
		else{
			SmartPhonePanel.interworkBtn.setText("�����ϱ�");
		}
	}
	
	static void openSmart(){ //����Ʈ�� ������ ����
		spf = new SmartPhoneFrame(); //���� �������� �޴����� ����Ѵ�.
		
		spf.setSize(300, 500);
		spf.setTitle("SmartPhone");
        spf.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE); //������ ���Ḧ ����! #���� 1
        
        spf.setVisible(true); 
	}
	
	static void closeSmart(){ //����Ʈ�� ������ �ݱ�, SmartPhoneFrame���� �ڼ��� �ٷ�
		spf.closeSmart();
	} //���� ��ȭ �޼ҵ� �� *******
	
	
	//DB ���� �޼ҵ� ******
	static Connection DBconnect(){ //�����ͺ��̽� ���� �޼ҵ�
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance(); //db connecter�� driver�� ž��
			
			Connection conn = null; //�����ͺ��̽��� �����ϱ� ���� ���� 
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank" ,"root" ,"3guswltmfrl"); //DB����

			return conn;
		}
		catch(Exception e){ 
			System.out.println("DB ����");
		}
		return null;
	}
	
	/* setInterest - ���� ����ϴ� �޼ҵ�
	 * �Ű� ���� - void
	 * ��ȯ�� - void
	 * userInfo table ��� #���� 2, ���¹�ȣ table ��� #���� 3
	 * �ܾ��� ���� �̻��� ���¿� ���� �������� ���� �ܾ��� 1%�� ������ 1�Ͽ� ���ڷ� �ٴ´ٰ� ����
	 * ������ "�Ա�|����"�� ǥ��, ����/��/1|00:00���� ����
	 */
	static void setInterest(){ //���� ����ϴ� �޼ҵ�
		LinkedList <String> accountList = new LinkedList <String>(); //���µ�
		LinkedList <Integer> balanceList = new LinkedList <Integer>(); //������ �ܾ׵�
		LinkedList <String> dayList = new LinkedList <String>(); //������ ������ �ŷ� ��¥
		
		Connection conn = DBconnect(); //DB ����!
		
		PreparedStatement pstmt = null; //SQL ������ �����ϱ� ���� ������ 
		ResultSet rs = null; //Select ���� �������� �� ����� �����ϱ� ���� ����
		
		String account = null;
		int balance = 0;
		String day = null;
		
		try{ 
			//�ܾ��� ���� �̻��� ���µ��� ��������
			pstmt = conn.prepareStatement("select account_number, balance from userinfo where balance >= 10000");
			rs = pstmt.executeQuery(); //��ɹ� ����!
			while(rs.next()){ 
				accountList.add(rs.getString("account_number")); //���� ����
				balanceList.add(rs.getInt("balance")); //�ܾ� ����
			}
			
			//������ �ŷ� ��¥ ��������
			nowDate = loadDate(); //���� ��¥
			String n[] = nowDate.split("/"); //n[0] = ����, n[1] = ��, n[2] = ��
			int num = 0;
			
			for(int i=0;i<accountList.size();i++){
				account = accountList.get(i);
				
				//������ ���� ã��
				pstmt = conn.prepareStatement("select max(num) from `" + account + "`"); //num �� ���� ū �� ã��!
				rs = pstmt.executeQuery(); //��ɹ� ����!
				if(rs.next()){
					num = rs.getInt("max(num)");
				}
				
				//��¥ ã��
				pstmt = conn.prepareStatement("select date from `" + account + "` where num = " + num);
				rs = pstmt.executeQuery(); //��ɹ� ����!
				if(rs.next()){
					dayList.add(rs.getString("date"));
				}
			}
		}
		catch(Exception ex) {
			System.out.println("handle the error");
		}
		finally{ //db�ڿ� ��ȯ
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	        if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	        if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}	
		
		//���� ����!!
		for(int i=0;i<accountList.size();i++){
			account = accountList.get(i);
			balance = balanceList.get(i);
			day = dayList.get(i);
			
			//���� ���� �� �ִ��� Ȯ��
			String d[] = day.split("/"); //������ ��¥, d[0] = ����, d[1] = ��, d[2] = ��
			String n[] = nowDate.split("/"); //���� ��¥, n[0] = ����, n[1] = ��, n[2] = ��
			boolean check = false;
		
			if(Integer.parseInt(n[0]) > Integer.parseInt(d[0])){ //������ ������ ũ��
				check = true;
			}
			else if(Integer.parseInt(n[0]) == Integer.parseInt(d[0])){ //���� ������ ������ ������ ������
				if(Integer.parseInt(n[1]) == Integer.parseInt(d[1])){ //���� ���� ũ��
					check = true;
				}
			}
			
			//���� ������!
			if(check = true){
				int interestDay = ((Integer.parseInt(n[0]) - Integer.parseInt(d[0])) * 12) - //���� ���� ���
							  (Integer.parseInt(d[1]) - Integer.parseInt(n[1])); //�� ���� ���, ���ڸ� �޴� �� ��
				int interest = 0; //���� ���ϴ� ��
				
				int year = Integer.parseInt(d[0]); //����
				int month = Integer.parseInt(d[1])+1; //��¥
				
				for(int j=0;j<interestDay;j++, month++){
					if(month == 13){ month = 1; year++; } //������ �Ѿ
					
					nowDate = year + "/" + month + "/1"; //�Ա�  ��¥ ����!
					nowTime = "0:0"; //�Ա� �ð� ����!
					interest = (int)(balance*0.01); //�Ա� �ݾ� ����!, �� ���� 1%!
					balance += interest; //�ܾ����� ���
					
					banking(account, (month + "�� ����"), interest, '+', false); //���� �ֱ�ֱ�
				}
			}
		}
	}
	
	/* checkUserInfo - ���̵�(�� ��)�� ��й�ȣ�� �޾� ������ �Ǵ��ϴ� �޼ҵ�
	 * �Ű� ���� - id = �� ��, password = ��й�ȣ
	 * ��ȯ�� - String = ���� ���� �� ���� ���� ��ȣ�� ��ȯ, ���� �ƴ� �� null ��ȯ
	 * userInfo table ��� #���� 2 
	 */
	static String checkUserInfo(String id, String password){ //���̵�(�� ��)�� ��й�ȣ�� �޾� ������ �Ǵ��ϴ� �޼ҵ�
		if(id == null || password == null){ //��ĭ�� ������
			return null;
		}
		else{
			Connection conn = DBconnect(); //DB ����!
			
			PreparedStatement pstmt = null; //SQL ������ �����ϱ� ���� ������ 
			ResultSet rs = null; //Select ���� �������� �� ����� �����ϱ� ���� ����
			
			try{
				pstmt = conn.prepareStatement("select * from userinfo where name = ? AND password = ?"); //id && pwd Ȯ��
				pstmt.setString(1, id); //?�� �����͸� ���ε�
				pstmt.setString(2, password);  
				
				rs = pstmt.executeQuery(); //��ɹ� ����!
				
				if(rs.next()){ //ã��!
					//System.out.println(rs.getString("account_number")); //��ȯ�Ǵ� ���¹�ȣ �� ���Ȯ��
					return rs.getString("account_number");
				}
			}
			catch(Exception ex) {
				System.out.println("handle the error");
			}
			finally{ //db�ڿ� ��ȯ
				if (rs != null) try { rs.close(); } catch(SQLException ex) {}
		        if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
		        if (conn != null) try { conn.close(); } catch(SQLException ex) {}
			}
			return null;
		}
	}
	
	/* getAccountName - ���¹�ȣ�� �� ���� ��ȯ�ϴ� �޼ҵ�
	 * �Ű� ���� - account = ���¹�ȣ
	 * ��ȯ�� - String = ���°� ���� �� ������ �� �� ��ȯ, ���� �� null ��ȯ
	 * userInfo table ��� #���� 2 
	 */
	static String getAccountName(String account){ //���¹�ȣ�� �� ���� ��ȯ�ϴ� �޼ҵ�
		Connection conn = DBconnect(); //DB ����!
		
		PreparedStatement pstmt = null; //SQL ������ �����ϱ� ���� ������ 
		ResultSet rs = null; //Select ���� �������� �� ����� �����ϱ� ���� ����
		
		try{
			pstmt = conn.prepareStatement("select * from userinfo where account_number = ?"); //�Լ����ڿ� ��ġ�ϴ� ���� Ȯ��
			pstmt.setString(1, account);  //?�� �����͸� ���ε�
			
			rs = pstmt.executeQuery(); //��ɹ� ����!
			
			if(rs.next()){
				//System.out.println(rs.getString("name")); //��ȯ�Ǵ� ���ܱݾ� ���Ȯ��
				return rs.getString("name");
			}
		}
		catch(Exception ex) {
			System.out.println("handle the error");
		}
		finally{ //db�ڿ� ��ȯ
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	        if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	        if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		return null;
	}
	
	/* getAccountBalance - ���¹�ȣ�� �ܾ��� ��ȯ�ϴ� �޼ҵ�
	 * �Ű� ���� - account = ���¹�ȣ
	 * ��ȯ�� - int = ���°� ���� �� ������ �ܾ��� ��ȯ, ���� �� -1 ��ȯ
	 * userInfo table ��� #���� 2 
	 */
	static int getAccountBalance(String account){ //���¹�ȣ�� �ܾ��� ��ȯ�ϴ� �޼ҵ�
		Connection conn = DBconnect(); //DB ����!
		
		PreparedStatement pstmt = null; //SQL ������ �����ϱ� ���� ������ 
		ResultSet rs = null; //Select ���� �������� �� ����� �����ϱ� ���� ����
		
		try{
			pstmt = conn.prepareStatement("select * from userinfo where account_number = ?"); //�Լ����ڿ� ��ġ�ϴ� ���� Ȯ��
			pstmt.setString(1, account);  //?�� �����͸� ���ε�
			
			rs = pstmt.executeQuery(); //��ɹ� ����!
			
			if(rs.next()){
				//System.out.println(rs.getInt("balance")); //��ȯ�Ǵ� ���ܱݾ� ���Ȯ��
				return rs.getInt("balance");
			}
		}
		catch(Exception ex) {
			System.out.println("handle the error");
		}
		finally{ //db�ڿ� ��ȯ
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	        if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	        if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		return -1;
	}
	
	/* getAccountBenefit - ���¹�ȣ�� ������ ��ȯ�ϴ� �޼ҵ�
	 * �Ű� ���� - account = ���¹�ȣ
	 * ��ȯ�� - String = ���°� ���� �� ������ ������ ��ȯ, ���� �� -1 ��ȯ
	 * userInfo table ��� #���� 2 
	 */
	static int getAccountBenefit(String account){ //���¹�ȣ�� ������ ��ȯ�ϴ� �޼ҵ�
		Connection conn = DBconnect(); //DB ����!
		
		PreparedStatement pstmt = null; //SQL ������ �����ϱ� ���� ������ 
		ResultSet rs = null; //Select ���� �������� �� ����� �����ϱ� ���� ����
		
		try{
			pstmt = conn.prepareStatement("select * from userinfo where account_number = ?"); //�Լ����ڿ� ��ġ�ϴ� ���� Ȯ��
			pstmt.setString(1, account);  //?�� �����͸� ���ε�
			
			rs = pstmt.executeQuery(); //��ɹ� ����!
			
			if(rs.next()){
				//System.out.println(rs.getInt("benefit")); //��ȯ�Ǵ� ���ܱݾ� ���Ȯ��
				return rs.getInt("benefit");
			}
		}
		catch(Exception ex) {
			System.out.println("handle the error");
		}
		finally{ //db�ڿ� ��ȯ
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	        if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	        if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		return -1;
	}
	
	/* banking - ���� �ŷ��� �ϴ� �޼ҵ�
	 * �Ű� ���� - account = ���¹�ȣ, shop = ���� ���(ex. ATM, ��������..), money = �ŷ� ��, op = �Ա�(+), ���(-), smart = ����Ʈ�� �ŷ��ΰ�
	 * ��ȯ�� - boolean = �ŷ� �Ϸ�� true, �ŷ� �̿Ϸ�� false
	 * ���¹�ȣ table ��� #���� 3
	 */
	static boolean banking(String account, String content, int money, char op, boolean smart){ //���� �ŷ��� �ϴ� �޼ҵ�
		if((op == '-') && (getAccountBalance(account) < money)){ //����ε� �ܾ��� ���� 
			JOptionPane.showMessageDialog(mf, "������ �ܾ��� �����մϴ�", "���� ����", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		else{ //�ܾ��� �ִ� 
			int benefit = 0; //����, ó���� ���� ��� �޾ƿ��� -> ���� �ݾ�
			
			if(content.contains("ATM") || (content.contains("����"))){ //ATM �ŷ�
				if(content.contains("ATM")){
					nowTime = loadTime();
				}
				setAccountInfo(account, content, money, op); //���� ���� ����
			}
			else{ //���� �ŷ� -> ������ �˾�
				String str[] = {"���� ����", "���� ����", "� ����", "��ȭ ����", "���� ����"}; //���� ����
				String benefitStr = "���� ����";
				
				if(monthBenefit == shopPanel.thisShopBenefit){ //�� ���� �����̴�!
					benefitStr = "�� ���� ����"; //���� ���� ����
					benefit = (int)(money * 0.1); //10% ����!
					System.out.println("�̴��� ����");
				}
				else{ //���� ã��
					if(getAccountBenefit(account) == shopPanel.thisShopBenefit){ //���� �޴´�!
						benefitStr = str[benefit]; //���� ���� ����
						benefit = (int)(money * 0.1); //10% ����!
						System.out.println("�� ����!");
					}
				}
				
				nowDate = loadDate(); //���� ��¥ 
				nowTime = loadTime(); //�ð� ����
				setAccountInfo(account, content, money - benefit, op); //���� ���� ����
				
				String bill = "�ŷ�ó �� : " + content + "\r\n" +
							  "�ŷ��� �� : " + getAccountName(account) + "\r\n" + 
							  "���� �� : " + nowDate + "|" + nowTime + "\r\n" +
							  "��   �� : " + money + "\r\n" +
							  "���� �ݾ� : " + benefit + " (" + benefitStr + ")" + "\r\n" +
							  "���� �ݾ� : " + (money - benefit);
				
				JOptionPane.showMessageDialog(mf, bill, "������", JOptionPane.INFORMATION_MESSAGE); //���� �����ӿ� �˾�
			}
			
			if(smart){ //����Ʈ�� ������
				smartPhoneMessage((money - benefit), op); //����Ʈ���� �˾�
				SmartPhonePanel.setBalance(getAccountBalance(account)); //����Ʈ�� �ܾ� ����
			}
			return true;
		}
	}
	
	/* setAccountInfo - ������ ������ ���� �޼ҵ�
	 * �Ű� ���� - account = ���¹�ȣ, content = ���� ���(ex. ATM, ��������..), money = �ŷ� ��, op = �Ա�(+), ���(-)
	 * ��ȯ�� - void
	 * userInfo table ��� #���� 2, ���¹�ȣ table ��� #���� 3
	 */
	static void setAccountInfo(String account, String content, int money, char op){ //������ ������ ���� �޼ҵ�
		int balance = getAccountBalance(account); //�ܾ� �޾ƿ���
		
		Connection conn = DBconnect(); //DB ����!
		
		PreparedStatement pstmt = null; //SQL ������ �����ϱ� ���� ������ 
		ResultSet rs = null; //Select ���� �������� �� ����� �����ϱ� ���� ����
		
		int num = 1; //privacy key
		String type = null; //�����
		
		try{
			pstmt = conn.prepareStatement("select max(num) from `" + account + "`"); //num �� ���� ū �� ã��!
			
			rs = pstmt.executeQuery(); //��ɹ� ����!
			
			if(rs.next()){
				num += rs.getInt("max(num)");
			}
			
			if(op == '+'){ type = "�Ա�"; balance += money; }
			else{		   type = "���"; balance -= money; }
			
			//���� �߰��ϱ�
			String sql = "insert into `" + account + "`" +
						 "(num, type, money, content, date, time)values" +
						 "(" + num + ", '" + type + "', " + money + ", '" + content + "', '" + nowDate + "', '" + nowTime + "')";
			
			pstmt.executeUpdate(sql); //��ɹ� ����!
				
			//�ܾ� ����
			sql = "update userinfo set balance:=" + balance + " where account_number = '" + account + "'";
			
			pstmt.executeUpdate(sql); //��ɹ� ����! 
		}
		catch(Exception ex) {
			System.out.println("handle the error");
		}
		finally{ //db�ڿ� ��ȯ
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	        if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	        if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
	}
	
	static int getMonthBenefit(){ //�� ���� ���� �ҷ�����
		Connection conn = DBconnect(); //DB ����!
		
		PreparedStatement pstmt = null; //SQL ������ �����ϱ� ���� ������ 
		ResultSet rs = null; //Select ���� �������� �� ����� �����ϱ� ���� ����
		
		try{
			nowDate = loadDate(); //���� ��¥ �ޱ�
			String now[] = nowDate.split("/"); //now[0] = ����, now[1] = ��, now[2] = ��
			
			pstmt = conn.prepareStatement("select * from monthbenefit where month = " + now[1]);
			
			rs = pstmt.executeQuery(); //��ɹ� ����!
			
			if(rs.next()){
				return rs.getInt("benefit");
			}
		}
		catch(Exception ex) {
			System.out.println("handle the error");
		}
		finally{ //db�ڿ� ��ȯ
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	        if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	        if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		return -1;
	}
	
	static String setTable(String account){ //��� ����
		Connection conn = DBconnect(); //DB ����!
		
		PreparedStatement pstmt = null; //SQL ������ �����ϱ� ���� ������ 
		ResultSet rs = null; //Select ���� �������� �� ����� �����ϱ� ���� ����
		
		String str = "";
		
		try{
			pstmt = conn.prepareStatement("select * from `" + account + "`");
			rs = pstmt.executeQuery();
			
			while(rs.next()){                                                     
				str += (rs.getString("type") + "_" + 
			  		    rs.getInt("money") + "_" +
						rs.getString("content") + "_" +
						rs.getString("date") + "_" +
						rs.getString("time") + "|");
			}
		}
		catch(Exception ex) {
			System.out.println("handle the error");
		}
		finally{ //db�ڿ� ��ȯ
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	        if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	        if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		return str;
	}
	
	static String setAccountNumber(){ //���� ��ȣ ������
		Connection conn = DBconnect(); //DB ����!
		
		PreparedStatement pstmt = null; //SQL ������ �����ϱ� ���� ������ 
		ResultSet rs = null; //Select ���� �������� �� ����� �����ϱ� ���� ����
		
		Random random = new Random();
		
		try{
			String num = null;
			boolean check = true;
			
			do{
				num = "110-"; //110-###-######
				num += (random.nextInt(899)+100) + "-" + (random.nextInt(899999)+100000);
				
				pstmt = conn.prepareStatement("select account_number from userinfo");
				rs = pstmt.executeQuery();
				
				while(rs.next()){
					if(num.equals(rs.getString("account_number"))){
						check = false;
					}
				}
			}while(check == false);
			
			return num;
		}
		catch(Exception ex) {
			System.out.println("handle the error");
		}
		finally{ //db�ڿ� ��ȯ
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	        if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	        if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		
		return null;
	}
	
	static void createUser(String name, String accountNumber, String passwd, String hobby){ //���� ���� �޼ҵ�
		Connection conn = DBconnect(); //DB ����!
		
		PreparedStatement pstmt = null; //SQL ������ �����ϱ� ���� ������ 
		ResultSet rs = null; //Select ���� �������� �� ����� �����ϱ� ���� ����
		
		boolean check = false;
		
		try{
			//���� ���̺� �����
			pstmt = conn.prepareStatement("create table `" + accountNumber +
										  "` (num int(11), type varchar(45), money int(11)," +
										  " content varchar(45), date varchar(45), time varchar(45))");
			pstmt.executeUpdate();
			
			//userinfo�� ���� �־��ֱ�
			pstmt = conn.prepareStatement("insert into userinfo " +
					 "(name, account_number, password, balance, benefit)values" +
					 "('" + name + "', '" + accountNumber + "', " + passwd + ", " + 0 + ", '" + hobby + "')");
		
			pstmt.executeUpdate(); //��ɹ� ����!

			check = true;
		}
		catch(Exception ex) {
			System.out.println("handle the error");
		}
		finally{ //db�ڿ� ��ȯ
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	        if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	        if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}

		//���� �ݾ� �Ա� 10����
		if(check){
			banking(accountNumber, "ATM", 100000, '+', false);
		}
	}
	//DB ���� �޼ҵ� �� ******
	
	static void smartPhoneMessage(int money, char op){ //����Ʈ���� �˾� ����
		if(op == '+'){ //�Ա�
			JOptionPane.showMessageDialog(spf, money + "���� �Ա� �Ǿ����ϴ�", "�ŷ� �˶�", JOptionPane.INFORMATION_MESSAGE);
		}
		else if(op == '-'){ //���
			JOptionPane.showMessageDialog(spf, money + "���� ��� �Ǿ����ϴ�", "�ŷ� �˶�", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	static String loadDate(){ //���� ��¥ �ҷ�����
		Calendar cal = Calendar.getInstance(); //�޷� �Լ�, ���� ��¥�� �ð��� �� �� �ִ�.
		
		String str = cal.get(Calendar.YEAR) + "/" + //����
					 (cal.get(Calendar.MONTH)+1) + "/" + //��
					 cal.get(Calendar.DATE); //��
		return str;
	}
	
	static String loadTime(){ //���� �ð� �ҷ�����
		Calendar cal = Calendar.getInstance(); //�޷� �Լ�, ���� ��¥�� �ð��� �� �� �ִ�.
		
		String str = cal.get(Calendar.HOUR_OF_DAY) + ":" + //�ð�
					 cal.get(Calendar.MINUTE); //��
		return str;
	}
}

/*
���� 1 ���� ������ ����
	http://bvc12.tistory.com/139
���� 2 userInfo table = �� ������ ��� �ִ� ����
	String �� �� | String ���� ��ȣ (-����) | String ��й�ȣ (4�ڸ�) | int �ܾ� | int ����
���� 3 ���¹�ȣ table = �� ���� ������ ��� �ִ� ����
	int num | String type = ����� | int money | String content = �ŷ� ��� | String date = ��¥ | String time = �ð�
*/