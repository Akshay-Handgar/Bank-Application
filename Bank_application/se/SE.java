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

/* SE - main 클래스, smartPhoneFrame 여닫기 & 파일 입출력 관리
 * 
 * 메소드 간단 정리
 * smartState - 스마트폰 프레임 상태 바꾸기
 * smartLoginState - 스마트폰 로그인 상태 바꾸기
 * smartInterworkState - 스마트폰 연동 상태 바꾸기
 * openSmart - 스마트폰 프레임 열기
 * closeSmart - 스마트폰 프레임 닫기
 * 
 * setInterest - 이자 계산하는 메소드
 * 
 * checkUserInfo - 아이디와 비밀번호를 받아 고객인지 판단하는 메소드 
 * getAccountName - 계좌번호의 고객 명을 반환하는 메소드 
 * getAccountBalance - 계좌번호의 잔액을 반환하는 메소드 
 * getAccountBenefit - 계좌번호의 혜택을 반환하는 메소드 
 * banking - 은행 거래를 하는 메소드
 * setAccountInfo - 계좌의 내역을 적는 메소드 
 * getMonthBenefit - 이 달의 혜택 불러오기
 * 
 * smartPhoneMessage - 스마트폰에 팝업 띄우기
 * loadDate - 현재 날짜 불러오기
 * loadTime - 현재 시간 불러오기
 */
public class SE { //Software Engineer
	static boolean smartState = false; //스마트폰 프레임 상태 (실행 중 or 종료), 다른 클래스에서 사용하려면 static이 붙어야한다.
	static boolean smartLoginState = false; //스마트폰 로그인 상태 (로그인 or 로그 아웃)
	static boolean smartInterworkState = false; //스마트폰 연동 상태 (실행 중 or 종료)
	static MainFrame mf = null;
	static SmartPhoneFrame spf = null;
	
	static String nowDate = null; //현재 날짜를 저장하는 함수, 계산서와 내역 사이의 시간 차를 없애기 위해 사용
	static String nowTime = null; //현재 시간를 저장하는 함수
	static int monthBenefit = 0; //이달의 혜택
	
	public static void main(String[] args) {
		setInterest(); //이자 계산
		monthBenefit = getMonthBenefit(); //이달의 혜택 받기
		openSmart(); //스마트폰 프레임 열기
		mf = new MainFrame(); //기본 프레임 (atmPanel, createAccountPanel, shopPanel으로 구성)
	}
	
	//상태 변화 메소드 *******
	static void smartState(boolean state){ //스마트폰 프레임 상태 바꾸기, 디폴트 접근제한자 - 같은 패키지 + 자기 객체 내 #참고 1
		smartState = state;
	}
	
	static void smartLoginState(boolean state){ //스마트폰 로그인 상태 바꾸기
		smartLoginState = state;
		
		if(smartLoginState == false){ //로그아웃하면
			smartInterworkState(false); //연동도 풀린다
		}
	}
	
	static void smartInterworkState(boolean state){ //스마트폰 연동 상태 바꾸기
		smartInterworkState = state;
		
		if(state){ //연동 중..
			SmartPhonePanel.interworkBtn.setText("연동 중..");
		}
		else{
			SmartPhonePanel.interworkBtn.setText("연동하기");
		}
	}
	
	static void openSmart(){ //스마트폰 프레임 열기
		spf = new SmartPhoneFrame(); //메인 프레임의 메뉴에서 사용한다.
		
		spf.setSize(300, 500);
		spf.setTitle("SmartPhone");
        spf.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE); //완전한 종료를 위해! #참고 1
        
        spf.setVisible(true); 
	}
	
	static void closeSmart(){ //스마트폰 프레임 닫기, SmartPhoneFrame에서 자세히 다룸
		spf.closeSmart();
	} //상태 변화 메소드 끝 *******
	
	
	//DB 접근 메소드 ******
	static Connection DBconnect(){ //데이터베이스 연결 메소드
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance(); //db connecter를 driver에 탑재
			
			Connection conn = null; //데이터베이스에 접속하기 위한 변수 
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank" ,"root" ,"3guswltmfrl"); //DB연결

			return conn;
		}
		catch(Exception e){ 
			System.out.println("DB 오류");
		}
		return null;
	}
	
	/* setInterest - 이자 계산하는 메소드
	 * 매개 변수 - void
	 * 반환형 - void
	 * userInfo table 사용 #참고 2, 계좌번호 table 사용 #참고 3
	 * 잔액이 만원 이상인 계좌에 달의 마지막날 기준 잔액의 1%가 다음달 1일에 이자로 붙는다고 가정
	 * 내역엔 "입금|이자"로 표현, 연도/달/1|00:00으로 기입
	 */
	static void setInterest(){ //이자 계산하는 메소드
		LinkedList <String> accountList = new LinkedList <String>(); //계좌들
		LinkedList <Integer> balanceList = new LinkedList <Integer>(); //계좌의 잔액들
		LinkedList <String> dayList = new LinkedList <String>(); //계좌의 마지막 거래 날짜
		
		Connection conn = DBconnect(); //DB 연결!
		
		PreparedStatement pstmt = null; //SQL 구문을 실행하기 위한 변수들 
		ResultSet rs = null; //Select 구문 실행했을 때 결과를 저장하기 위한 변수
		
		String account = null;
		int balance = 0;
		String day = null;
		
		try{ 
			//잔액이 만원 이상인 계좌들을 수집수집
			pstmt = conn.prepareStatement("select account_number, balance from userinfo where balance >= 10000");
			rs = pstmt.executeQuery(); //명령문 실행!
			while(rs.next()){ 
				accountList.add(rs.getString("account_number")); //계좌 수집
				balanceList.add(rs.getInt("balance")); //잔액 수집
			}
			
			//마지막 거래 날짜 수집수집
			nowDate = loadDate(); //현재 날짜
			String n[] = nowDate.split("/"); //n[0] = 연도, n[1] = 월, n[2] = 일
			int num = 0;
			
			for(int i=0;i<accountList.size();i++){
				account = accountList.get(i);
				
				//마지막 내역 찾기
				pstmt = conn.prepareStatement("select max(num) from `" + account + "`"); //num 중 가장 큰 값 찾기!
				rs = pstmt.executeQuery(); //명령문 실행!
				if(rs.next()){
					num = rs.getInt("max(num)");
				}
				
				//날짜 찾기
				pstmt = conn.prepareStatement("select date from `" + account + "` where num = " + num);
				rs = pstmt.executeQuery(); //명령문 실행!
				if(rs.next()){
					dayList.add(rs.getString("date"));
				}
			}
		}
		catch(Exception ex) {
			System.out.println("handle the error");
		}
		finally{ //db자원 반환
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	        if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	        if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}	
		
		//이자 이자!!
		for(int i=0;i<accountList.size();i++){
			account = accountList.get(i);
			balance = balanceList.get(i);
			day = dayList.get(i);
			
			//이자 붙을 수 있는지 확인
			String d[] = day.split("/"); //마지막 날짜, d[0] = 연도, d[1] = 월, d[2] = 일
			String n[] = nowDate.split("/"); //현재 날짜, n[0] = 연도, n[1] = 월, n[2] = 일
			boolean check = false;
		
			if(Integer.parseInt(n[0]) > Integer.parseInt(d[0])){ //현재의 연도가 크면
				check = true;
			}
			else if(Integer.parseInt(n[0]) == Integer.parseInt(d[0])){ //현재 연도와 마지막 연도가 같으면
				if(Integer.parseInt(n[1]) == Integer.parseInt(d[1])){ //현재 월이 크면
					check = true;
				}
			}
			
			//이자 붙이자!
			if(check = true){
				int interestDay = ((Integer.parseInt(n[0]) - Integer.parseInt(d[0])) * 12) - //연도 차이 계산
							  (Integer.parseInt(d[1]) - Integer.parseInt(n[1])); //달 차이 계산, 이자를 받는 날 수
				int interest = 0; //이자 구하는 애
				
				int year = Integer.parseInt(d[0]); //연도
				int month = Integer.parseInt(d[1])+1; //날짜
				
				for(int j=0;j<interestDay;j++, month++){
					if(month == 13){ month = 1; year++; } //연도가 넘어감
					
					nowDate = year + "/" + month + "/1"; //입금  날짜 설정!
					nowTime = "0:0"; //입금 시간 설정!
					interest = (int)(balance*0.01); //입금 금액 설정!, 월 이자 1%!
					balance += interest; //잔액으로 고고
					
					banking(account, (month + "월 이자"), interest, '+', false); //이자 넣기넣기
				}
			}
		}
	}
	
	/* checkUserInfo - 아이디(고객 명)와 비밀번호를 받아 고객인지 판단하는 메소드
	 * 매개 변수 - id = 고객 명, password = 비밀번호
	 * 반환형 - String = 고객이 맞을 시 고객의 계좌 번호를 반환, 고객이 아닐 시 null 반환
	 * userInfo table 사용 #참고 2 
	 */
	static String checkUserInfo(String id, String password){ //아이디(고객 명)와 비밀번호를 받아 고객인지 판단하는 메소드
		if(id == null || password == null){ //빈칸이 있으면
			return null;
		}
		else{
			Connection conn = DBconnect(); //DB 연결!
			
			PreparedStatement pstmt = null; //SQL 구문을 실행하기 위한 변수들 
			ResultSet rs = null; //Select 구문 실행했을 때 결과를 저장하기 위한 변수
			
			try{
				pstmt = conn.prepareStatement("select * from userinfo where name = ? AND password = ?"); //id && pwd 확인
				pstmt.setString(1, id); //?에 데이터를 바인딩
				pstmt.setString(2, password);  
				
				rs = pstmt.executeQuery(); //명령문 실행!
				
				if(rs.next()){ //찾음!
					//System.out.println(rs.getString("account_number")); //반환되는 계좌번호 값 출력확인
					return rs.getString("account_number");
				}
			}
			catch(Exception ex) {
				System.out.println("handle the error");
			}
			finally{ //db자원 반환
				if (rs != null) try { rs.close(); } catch(SQLException ex) {}
		        if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
		        if (conn != null) try { conn.close(); } catch(SQLException ex) {}
			}
			return null;
		}
	}
	
	/* getAccountName - 계좌번호의 고객 명을 반환하는 메소드
	 * 매개 변수 - account = 계좌번호
	 * 반환형 - String = 계좌가 있을 시 계좌의 고객 명 반환, 없을 시 null 반환
	 * userInfo table 사용 #참고 2 
	 */
	static String getAccountName(String account){ //계좌번호의 고객 명을 반환하는 메소드
		Connection conn = DBconnect(); //DB 연결!
		
		PreparedStatement pstmt = null; //SQL 구문을 실행하기 위한 변수들 
		ResultSet rs = null; //Select 구문 실행했을 때 결과를 저장하기 위한 변수
		
		try{
			pstmt = conn.prepareStatement("select * from userinfo where account_number = ?"); //함수인자와 일치하는 계좌 확인
			pstmt.setString(1, account);  //?에 데이터를 바인딩
			
			rs = pstmt.executeQuery(); //명령문 실행!
			
			if(rs.next()){
				//System.out.println(rs.getString("name")); //반환되는 통잔금액 출력확인
				return rs.getString("name");
			}
		}
		catch(Exception ex) {
			System.out.println("handle the error");
		}
		finally{ //db자원 반환
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	        if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	        if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		return null;
	}
	
	/* getAccountBalance - 계좌번호의 잔액을 반환하는 메소드
	 * 매개 변수 - account = 계좌번호
	 * 반환형 - int = 계좌가 있을 시 계좌의 잔액을 반환, 없을 시 -1 반환
	 * userInfo table 사용 #참고 2 
	 */
	static int getAccountBalance(String account){ //계좌번호의 잔액을 반환하는 메소드
		Connection conn = DBconnect(); //DB 연결!
		
		PreparedStatement pstmt = null; //SQL 구문을 실행하기 위한 변수들 
		ResultSet rs = null; //Select 구문 실행했을 때 결과를 저장하기 위한 변수
		
		try{
			pstmt = conn.prepareStatement("select * from userinfo where account_number = ?"); //함수인자와 일치하는 계좌 확인
			pstmt.setString(1, account);  //?에 데이터를 바인딩
			
			rs = pstmt.executeQuery(); //명령문 실행!
			
			if(rs.next()){
				//System.out.println(rs.getInt("balance")); //반환되는 통잔금액 출력확인
				return rs.getInt("balance");
			}
		}
		catch(Exception ex) {
			System.out.println("handle the error");
		}
		finally{ //db자원 반환
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	        if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	        if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		return -1;
	}
	
	/* getAccountBenefit - 계좌번호의 혜택을 반환하는 메소드
	 * 매개 변수 - account = 계좌번호
	 * 반환형 - String = 계좌가 있을 시 계좌의 혜택을 반환, 없을 시 -1 반환
	 * userInfo table 사용 #참고 2 
	 */
	static int getAccountBenefit(String account){ //계좌번호의 혜택을 반환하는 메소드
		Connection conn = DBconnect(); //DB 연결!
		
		PreparedStatement pstmt = null; //SQL 구문을 실행하기 위한 변수들 
		ResultSet rs = null; //Select 구문 실행했을 때 결과를 저장하기 위한 변수
		
		try{
			pstmt = conn.prepareStatement("select * from userinfo where account_number = ?"); //함수인자와 일치하는 계좌 확인
			pstmt.setString(1, account);  //?에 데이터를 바인딩
			
			rs = pstmt.executeQuery(); //명령문 실행!
			
			if(rs.next()){
				//System.out.println(rs.getInt("benefit")); //반환되는 통잔금액 출력확인
				return rs.getInt("benefit");
			}
		}
		catch(Exception ex) {
			System.out.println("handle the error");
		}
		finally{ //db자원 반환
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	        if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	        if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		return -1;
	}
	
	/* banking - 은행 거래를 하는 메소드
	 * 매개 변수 - account = 계좌번호, shop = 결제 장소(ex. ATM, 도스마스..), money = 거래 금, op = 입금(+), 출금(-), smart = 스마트폰 거래인가
	 * 반환형 - boolean = 거래 완료시 true, 거래 미완료시 false
	 * 계좌번호 table 사용 #참고 3
	 */
	static boolean banking(String account, String content, int money, char op, boolean smart){ //은행 거래를 하는 메소드
		if((op == '-') && (getAccountBalance(account) < money)){ //출금인데 잔액이 없다 
			JOptionPane.showMessageDialog(mf, "계좌의 잔액이 부족합니다", "결제 오류", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		else{ //잔액이 있다 
			int benefit = 0; //혜택, 처음엔 할인 목록 받아오기 -> 할인 금액
			
			if(content.contains("ATM") || (content.contains("이자"))){ //ATM 거래
				if(content.contains("ATM")){
					nowTime = loadTime();
				}
				setAccountInfo(account, content, money, op); //계좌 내역 쓰기
			}
			else{ //상점 거래 -> 영수증 팝업
				String str[] = {"할인 없음", "음식 할인", "운동 할인", "영화 할인", "놀이 할인"}; //할인 종류
				String benefitStr = "할인 없음";
				
				if(monthBenefit == shopPanel.thisShopBenefit){ //이 달의 혜택이다!
					benefitStr = "이 달의 혜택"; //할인 내역 쓰기
					benefit = (int)(money * 0.1); //10% 할인!
					System.out.println("이달의 혜택");
				}
				else{ //혜택 찾기
					if(getAccountBenefit(account) == shopPanel.thisShopBenefit){ //할인 받는다!
						benefitStr = str[benefit]; //할인 내역 쓰기
						benefit = (int)(money * 0.1); //10% 할인!
						System.out.println("내 혜택!");
					}
				}
				
				nowDate = loadDate(); //현재 날짜 
				nowTime = loadTime(); //시간 저장
				setAccountInfo(account, content, money - benefit, op); //계좌 내역 쓰기
				
				String bill = "거래처 명 : " + content + "\r\n" +
							  "거래자 명 : " + getAccountName(account) + "\r\n" + 
							  "결제 일 : " + nowDate + "|" + nowTime + "\r\n" +
							  "합   계 : " + money + "\r\n" +
							  "할인 금액 : " + benefit + " (" + benefitStr + ")" + "\r\n" +
							  "결제 금액 : " + (money - benefit);
				
				JOptionPane.showMessageDialog(mf, bill, "영수증", JOptionPane.INFORMATION_MESSAGE); //메인 프레임에 팝업
			}
			
			if(smart){ //스마트폰 결제시
				smartPhoneMessage((money - benefit), op); //스마트폰에 팝업
				SmartPhonePanel.setBalance(getAccountBalance(account)); //스마트폰 잔액 갱신
			}
			return true;
		}
	}
	
	/* setAccountInfo - 계좌의 내역을 적는 메소드
	 * 매개 변수 - account = 계좌번호, content = 결제 장소(ex. ATM, 도스마스..), money = 거래 금, op = 입금(+), 출금(-)
	 * 반환형 - void
	 * userInfo table 사용 #참고 2, 계좌번호 table 사용 #참고 3
	 */
	static void setAccountInfo(String account, String content, int money, char op){ //계좌의 내역을 적는 메소드
		int balance = getAccountBalance(account); //잔액 받아오기
		
		Connection conn = DBconnect(); //DB 연결!
		
		PreparedStatement pstmt = null; //SQL 구문을 실행하기 위한 변수들 
		ResultSet rs = null; //Select 구문 실행했을 때 결과를 저장하기 위한 변수
		
		int num = 1; //privacy key
		String type = null; //입출금
		
		try{
			pstmt = conn.prepareStatement("select max(num) from `" + account + "`"); //num 중 가장 큰 값 찾기!
			
			rs = pstmt.executeQuery(); //명령문 실행!
			
			if(rs.next()){
				num += rs.getInt("max(num)");
			}
			
			if(op == '+'){ type = "입금"; balance += money; }
			else{		   type = "출금"; balance -= money; }
			
			//내용 추가하기
			String sql = "insert into `" + account + "`" +
						 "(num, type, money, content, date, time)values" +
						 "(" + num + ", '" + type + "', " + money + ", '" + content + "', '" + nowDate + "', '" + nowTime + "')";
			
			pstmt.executeUpdate(sql); //명령문 실행!
				
			//잔액 수정
			sql = "update userinfo set balance:=" + balance + " where account_number = '" + account + "'";
			
			pstmt.executeUpdate(sql); //명령문 실행! 
		}
		catch(Exception ex) {
			System.out.println("handle the error");
		}
		finally{ //db자원 반환
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	        if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	        if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
	}
	
	static int getMonthBenefit(){ //이 달의 혜택 불러오기
		Connection conn = DBconnect(); //DB 연결!
		
		PreparedStatement pstmt = null; //SQL 구문을 실행하기 위한 변수들 
		ResultSet rs = null; //Select 구문 실행했을 때 결과를 저장하기 위한 변수
		
		try{
			nowDate = loadDate(); //현재 날짜 받기
			String now[] = nowDate.split("/"); //now[0] = 연도, now[1] = 월, now[2] = 일
			
			pstmt = conn.prepareStatement("select * from monthbenefit where month = " + now[1]);
			
			rs = pstmt.executeQuery(); //명령문 실행!
			
			if(rs.next()){
				return rs.getInt("benefit");
			}
		}
		catch(Exception ex) {
			System.out.println("handle the error");
		}
		finally{ //db자원 반환
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	        if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	        if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		return -1;
	}
	
	static String setTable(String account){ //사용 내역
		Connection conn = DBconnect(); //DB 연결!
		
		PreparedStatement pstmt = null; //SQL 구문을 실행하기 위한 변수들 
		ResultSet rs = null; //Select 구문 실행했을 때 결과를 저장하기 위한 변수
		
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
		finally{ //db자원 반환
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	        if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	        if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		return str;
	}
	
	static String setAccountNumber(){ //계좌 번호 생성기
		Connection conn = DBconnect(); //DB 연결!
		
		PreparedStatement pstmt = null; //SQL 구문을 실행하기 위한 변수들 
		ResultSet rs = null; //Select 구문 실행했을 때 결과를 저장하기 위한 변수
		
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
		finally{ //db자원 반환
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	        if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	        if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		
		return null;
	}
	
	static void createUser(String name, String accountNumber, String passwd, String hobby){ //유저 생성 메소드
		Connection conn = DBconnect(); //DB 연결!
		
		PreparedStatement pstmt = null; //SQL 구문을 실행하기 위한 변수들 
		ResultSet rs = null; //Select 구문 실행했을 때 결과를 저장하기 위한 변수
		
		boolean check = false;
		
		try{
			//계좌 테이블 만들기
			pstmt = conn.prepareStatement("create table `" + accountNumber +
										  "` (num int(11), type varchar(45), money int(11)," +
										  " content varchar(45), date varchar(45), time varchar(45))");
			pstmt.executeUpdate();
			
			//userinfo에 정보 넣어주기
			pstmt = conn.prepareStatement("insert into userinfo " +
					 "(name, account_number, password, balance, benefit)values" +
					 "('" + name + "', '" + accountNumber + "', " + passwd + ", " + 0 + ", '" + hobby + "')");
		
			pstmt.executeUpdate(); //명령문 실행!

			check = true;
		}
		catch(Exception ex) {
			System.out.println("handle the error");
		}
		finally{ //db자원 반환
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	        if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	        if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}

		//기초 금액 입금 10마넌
		if(check){
			banking(accountNumber, "ATM", 100000, '+', false);
		}
	}
	//DB 접근 메소드 끝 ******
	
	static void smartPhoneMessage(int money, char op){ //스마트폰에 팝업 띄우기
		if(op == '+'){ //입금
			JOptionPane.showMessageDialog(spf, money + "원이 입금 되었습니다", "거래 알람", JOptionPane.INFORMATION_MESSAGE);
		}
		else if(op == '-'){ //출금
			JOptionPane.showMessageDialog(spf, money + "원이 출금 되었습니다", "거래 알람", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	static String loadDate(){ //현재 날짜 불러오기
		Calendar cal = Calendar.getInstance(); //달력 함수, 현재 날짜와 시간을 알 수 있다.
		
		String str = cal.get(Calendar.YEAR) + "/" + //연도
					 (cal.get(Calendar.MONTH)+1) + "/" + //월
					 cal.get(Calendar.DATE); //일
		return str;
	}
	
	static String loadTime(){ //현재 시간 불러오기
		Calendar cal = Calendar.getInstance(); //달력 함수, 현재 날짜와 시간을 알 수 있다.
		
		String str = cal.get(Calendar.HOUR_OF_DAY) + ":" + //시간
					 cal.get(Calendar.MINUTE); //분
		return str;
	}
}

/*
참고 1 접근 제한자 설명
	http://bvc12.tistory.com/139
참고 2 userInfo table = 고객 정보가 들어 있는 파일
	String 고객 명 | String 계좌 번호 (-포함) | String 비밀번호 (4자리) | int 잔액 | int 혜택
참고 3 계좌번호 table = 고객 개인 정보가 들어 있는 파일
	int num | String type = 입출금 | int money | String content = 거래 장소 | String date = 날짜 | String time = 시간
*/