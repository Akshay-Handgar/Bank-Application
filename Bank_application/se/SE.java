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

/* SE - main Class, smartPhoneFrame Manage Data of input and output
 * 
 * Methods
 * smartState - Switching the smart phone frame status
 * smartLoginState - Switching the smart phone Login status
 * smartInterworkState - Switching the smart phone Interlocked status. 
 * openSmart - Open the smart phone Frame.
 * closeSmart - Close the smart phone Frame.
 * 
 * setInterest - Method Computes interest
 * 
 * checkUserInfo - Login method 
 * getAccountName - A method that returns the customer name of the account.
 * getAccountBalance - A method that returns the balance of the account.
 * getAccountBenefit - A method that returns the benefit of the account.
 * banking - A method that deals with bank.
 * setAccountInfo - A method that write the content of account. 
 * getMonthBenefit - Bring the benefit of this month.
 * 
 * loadDate - Bring the date
 * loadTime - Bring the Time
 */
public class SE { //Software Engineer
	static boolean smartState = false; 
	static boolean smartLoginState = false; 
	static boolean smartInterworkState = false; 
	static MainFrame mf = null;
	static SmartPhoneFrame spf = null;
	
	static String nowDate = null; 
	static String nowTime = null; 
	static int monthBenefit = 0; 
	
	public static void main(String[] args) {
		setInterest(); 
		monthBenefit = getMonthBenefit(); 
		openSmart(); 
		mf = new MainFrame(); 
	}
	
	//Status change method******
	static void smartState(boolean state){ //Smart phone frame status
		smartState = state;
	}
	
	static void smartLoginState(boolean state){  //Smart phone Login status
		smartLoginState = state;
		
		if(smartLoginState == false){ 
			smartInterworkState(false); 
		}
	}
	
	static void smartInterworkState(boolean state){  //Smart phone interlock status
		smartInterworkState = state;
		
		if(state){
			SmartPhonePanel.interworkBtn.setText("연동 중..");
		}
		else{
			SmartPhonePanel.interworkBtn.setText("연동하기");
		}
	}
	
	static void openSmart(){ 
		spf = new SmartPhoneFrame(); 
		
		spf.setSize(300, 500);
		spf.setTitle("SmartPhone");
        spf.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE); 
        
        spf.setVisible(true); 
	}
	
	static void closeSmart(){ 
		spf.closeSmart();
	} 
	
	
	//DB Access methed ******
	static Connection DBconnect(){ 
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance(); //Put db connecter in driver
			
			Connection conn = null; 
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank" ,"root" ,"3guswltmfrl"); 

			return conn;
		}
		catch(Exception e){ 
			System.out.println("DB Error");
		}
		return null;
	}
	
	/* setInterest - A method that calculates interest.
	   Monthly interest rate  is 1%
	 */
	static void setInterest(){ 
		LinkedList <String> accountList = new LinkedList <String>(); 
		LinkedList <Integer> balanceList = new LinkedList <Integer>(); 
		LinkedList <String> dayList = new LinkedList <String>(); 
		
		Connection conn = DBconnect(); 
		
		PreparedStatement pstmt = null;  
		ResultSet rs = null; 
		
		String account = null;
		int balance = 0;
		String day = null;
		
		try{ 
			//Interest condition - The balance of Account is more than 10,000 won
			pstmt = conn.prepareStatement("select account_number, balance from userinfo where balance >= 10000");
			rs = pstmt.executeQuery(); 
			while(rs.next()){ //collecting
				accountList.add(rs.getString("account_number")); 
				balanceList.add(rs.getInt("balance")); 
			}
			
			//Interest standard: Last Trading date
			nowDate = loadDate(); 
			String n[] = nowDate.split("/"); //n[0] = year, n[1] = month, n[2] = day
			int num = 0;
			//Collecting Data
			for(int i=0;i<accountList.size();i++){
				account = accountList.get(i);
				
				pstmt = conn.prepareStatement("select max(num) from `" + account + "`"); 
				rs = pstmt.executeQuery(); 
				if(rs.next()){
					num = rs.getInt("max(num)");
				}
				
				pstmt = conn.prepareStatement("select date from `" + account + "` where num = " + num);
				rs = pstmt.executeQuery(); 
				if(rs.next()){
					dayList.add(rs.getString("date"));
				}
			}
		}
		catch(Exception ex) {
			System.out.println("handle the error");
		}
		finally{ 
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	        if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	        if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}	
		
		
		for(int i=0;i<accountList.size();i++){
			account = accountList.get(i);
			balance = balanceList.get(i);
			day = dayList.get(i);
			
			//Check the interest standard
			String d[] = day.split("/"); 
			String n[] = nowDate.split("/"); 
			boolean check = false;
		
			if(Integer.parseInt(n[0]) > Integer.parseInt(d[0])){ 
				check = true;
			}
			else if(Integer.parseInt(n[0]) == Integer.parseInt(d[0])){ 
				if(Integer.parseInt(n[1]) == Integer.parseInt(d[1])){
					check = true;
				}
			}
			
			//Add interest
			if(check = true){
				int interestDay = ((Integer.parseInt(n[0]) - Integer.parseInt(d[0])) * 12) - 
							  (Integer.parseInt(d[1]) - Integer.parseInt(n[1])); 
				int interest = 0; 
				
				int year = Integer.parseInt(d[0]); 
				int month = Integer.parseInt(d[1])+1; 
				
				for(int j=0;j<interestDay;j++, month++){
					if(month == 13){ month = 1; year++; } 
					
					nowDate = year + "/" + month + "/1"; 
					nowTime = "0:0"; 
					interest = (int)(balance*0.01); 
					balance += interest; 
					
					banking(account, (month + "월 이자"), interest, '+', false); 
				}
			}
		}
	}
	
	/* checkUserInfo - Login method */
	static String checkUserInfo(String id, String password){ 
		if(id == null || password == null){ 
			return null;
		}
		else{
			Connection conn = DBconnect(); //DB connect
			
			PreparedStatement pstmt = null; 
			ResultSet rs = null; 
			
			try{
				pstmt = conn.prepareStatement("select * from userinfo where name = ? AND password = ?"); //check id && pwd 
				pstmt.setString(1, id); //binding
				pstmt.setString(2, password);  
				
				rs = pstmt.executeQuery(); //excute
				
				if(rs.next()){ 
					return rs.getString("account_number");
				}
			}
			catch(Exception ex) {
				System.out.println("handle the error");
			}
			finally{ 
				if (rs != null) try { rs.close(); } catch(SQLException ex) {}
		        if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
		        if (conn != null) try { conn.close(); } catch(SQLException ex) {}
			}
			return null;
		}
	}
	
	/* getAccountName - A method that returns the customer name of the account.
	 */
	static String getAccountName(String account){ 
		Connection conn = DBconnect(); //DB connect
		
		PreparedStatement pstmt = null; 
		ResultSet rs = null; 
		
		try{
			pstmt = conn.prepareStatement("select * from userinfo where account_number = ?"); 
			pstmt.setString(1, account);  //binding
			
			rs = pstmt.executeQuery(); //excute!
			
			if(rs.next()){
				return rs.getString("name");
			}
		}
		catch(Exception ex) {
			System.out.println("handle the error");
		}
		finally{ 
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	        if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	        if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		return null;
	}
	
	/* getAccountBalance - A method that returns the customer name of the account.
	 */
	static int getAccountBalance(String account){
		Connection conn = DBconnect(); //DB connect
		
		PreparedStatement pstmt = null; 
		ResultSet rs = null; 
	
		try{
			pstmt = conn.prepareStatement("select * from userinfo where account_number = ?"); 
			pstmt.setString(1, account);  //binding
			
			rs = pstmt.executeQuery(); //excute
			
			if(rs.next()){
				return rs.getInt("balance");
			}
		}
		catch(Exception ex) {
			System.out.println("handle the error");
		}
		finally{ 
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	        if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	        if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		return -1;
	}
	
	/* getAccountBenefit - A method that returns the benefit of the account
	 */
	static int getAccountBenefit(String account){ 
		Connection conn = DBconnect(); 
		
		PreparedStatement pstmt = null; 
		ResultSet rs = null; 
		
		try{
			pstmt = conn.prepareStatement("select * from userinfo where account_number = ?"); 
			pstmt.setString(1, account);  
			
			rs = pstmt.executeQuery(); 
			
			if(rs.next()){
				return rs.getInt("benefit");
			}
		}
		catch(Exception ex) {
			System.out.println("handle the error");
		}
		finally{ 
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	        if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	        if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		return -1;
	}
	
	/* banking - Bank transaction class
	 * account = Account number, shop = calculate program(ex. ATM), money = money, op = deposit(+), withdraw(-)
	 */
	static boolean banking(String account, String content, int money, char op, boolean smart){ 
		if((op == '-') && (getAccountBalance(account) < money)){ //No money
			JOptionPane.showMessageDialog(mf, "계좌의 잔액이 부족합니다", "결제 오류", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		else{ 
			int benefit = 0; 
			
			if(content.contains("ATM") || (content.contains("이자"))){ 
				if(content.contains("ATM")){
					nowTime = loadTime();
				}
				setAccountInfo(account, content, money, op); 
			}
			else{ 
				String str[] = {"할인 없음", "음식 할인", "운동 할인", "영화 할인", "놀이 할인"}; //Kind of discount
				String benefitStr = "할인 없음"; 
				
				if(monthBenefit == shopPanel.thisShopBenefit){ 
					benefitStr = "이 달의 혜택"; 
					benefit = (int)(money * 0.1); //10% discount
					System.out.println("이달의 혜택");
				}
				else{
					if(getAccountBenefit(account) == shopPanel.thisShopBenefit){ 
						benefitStr = str[benefit]; 
						benefit = (int)(money * 0.1); /
						System.out.println("내 혜택!");
					}
				}
				
				nowDate = loadDate(); 
				nowTime = loadTime();
				setAccountInfo(account, content, money - benefit, op); //Store the discount date/time 				
				String bill = "거래처 명 : " + content + "\r\n" +
							  "거래자 명 : " + getAccountName(account) + "\r\n" + 
							  "결제 일 : " + nowDate + "|" + nowTime + "\r\n" +
							  "합   계 : " + money + "\r\n" +
							  "할인 금액 : " + benefit + " (" + benefitStr + ")" + "\r\n" +
							  "결제 금액 : " + (money - benefit);
				
				JOptionPane.showMessageDialog(mf, bill, "영수증", JOptionPane.INFORMATION_MESSAGE); 
			}
			
			if(smart){ //calculated by smart phone
				smartPhoneMessage((money - benefit), op); 
				SmartPhonePanel.setBalance(getAccountBalance(account)); 
			}
			return true;
		}
	}
	
	/* setAccountInfo - The method that records the account history.
	 */
	static void setAccountInfo(String account, String content, int money, char op){ 
		int balance = getAccountBalance(account); 
		
		Connection conn = DBconnect(); 
		
		PreparedStatement pstmt = null; 
		ResultSet rs = null; 
		
		int num = 1; //privacy key
		String type = null; 
		
		try{
			pstmt = conn.prepareStatement("select max(num) from `" + account + "`"); 
			
			rs = pstmt.executeQuery(); 
			
			if(rs.next()){
				num += rs.getInt("max(num)");
			}
			
			if(op == '+'){ type = "입금"; balance += money; }
			else{		   type = "출금"; balance -= money; }
			
			//Add content
			String sql = "insert into `" + account + "`" +
						 "(num, type, money, content, date, time)values" +
						 "(" + num + ", '" + type + "', " + money + ", '" + content + "', '" + nowDate + "', '" + nowTime + "')";
			
			pstmt.executeUpdate(sql); 
				
			//modify amount
			sql = "update userinfo set balance:=" + balance + " where account_number = '" + account + "'";
			
			pstmt.executeUpdate(sql); 
		}
		catch(Exception ex) {
			System.out.println("handle the error");
		}
		finally{ 
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	        if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	        if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
	}
	
	static int getMonthBenefit(){ //Bring the monthly benefit
		Connection conn = DBconnect(); /
		
		PreparedStatement pstmt = null; 
		ResultSet rs = null; 
		
		try{
			nowDate = loadDate(); 
			String now[] = nowDate.split("/"); //now[0] = yaer, now[1] = month, now[2] = day
			
			pstmt = conn.prepareStatement("select * from monthbenefit where month = " + now[1]);
			
			rs = pstmt.executeQuery(); 
			
			if(rs.next()){
				return rs.getInt("benefit");
			}
		}
		catch(Exception ex) {
			System.out.println("handle the error");
		}
		finally{ 
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	        if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	        if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		return -1;
	}
	
	static String setTable(String account){ 
		Connection conn = DBconnect(); 
		
		PreparedStatement pstmt = null; 
		ResultSet rs = null; 
		
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
		finally{ 
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	        if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	        if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		return str;
	}
	
	static String setAccountNumber(){ //Create Account
		Connection conn = DBconnect(); 
		
		PreparedStatement pstmt = null;  
		ResultSet rs = null; 
		
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
		finally{ 
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	        if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	        if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		
		return null;
	}
	
	static void createUser(String name, String accountNumber, String passwd, String hobby){ 
		Connection conn = DBconnect(); 
		
		PreparedStatement pstmt = null; 
		ResultSet rs = null; 
		
		boolean check = false;
		
		try{
			//Create account table
			pstmt = conn.prepareStatement("create table `" + accountNumber +
										  "` (num int(11), type varchar(45), money int(11)," +
										  " content varchar(45), date varchar(45), time varchar(45))");
			pstmt.executeUpdate();
			
			//Input data in userinfo
			pstmt = conn.prepareStatement("insert into userinfo " +
					 "(name, account_number, password, balance, benefit)values" +
					 "('" + name + "', '" + accountNumber + "', " + passwd + ", " + 0 + ", '" + hobby + "')");
		
			pstmt.executeUpdate(); 

			check = true;
		}
		catch(Exception ex) {
			System.out.println("handle the error");
		}
		finally{ 
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	        if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	        if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}

		if(check){
			banking(accountNumber, "ATM", 100000, '+', false);
		}
	}
	
	static void smartPhoneMessage(int money, char op){ 
		if(op == '+'){ //deposit
			JOptionPane.showMessageDialog(spf, money + "원이 입금 되었습니다", "거래 알람", JOptionPane.INFORMATION_MESSAGE);
		}
		else if(op == '-'){ //withdraw
			JOptionPane.showMessageDialog(spf, money + "원이 출금 되었습니다", "거래 알람", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	static String loadDate(){ 
		Calendar cal = Calendar.getInstance(); 
		
		String str = cal.get(Calendar.YEAR) + "/" + 
					 (cal.get(Calendar.MONTH)+1) + "/" + 
					 cal.get(Calendar.DATE); 
		return str;
	}
	
	static String loadTime(){ 
		Calendar cal = Calendar.getInstance(); 
		
		String str = cal.get(Calendar.HOUR_OF_DAY) + ":" + 
					 cal.get(Calendar.MINUTE); 
		return str;
	}
}
