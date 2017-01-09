package se;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.Border;


class SmartPhoneFrame extends JFrame{ //스마트폰 프레임, 메뉴에서 킬 수 있다.
	CardLayout cardLayout = new CardLayout(10, 10);
	Container c; //뒤로가기 버튼용, #참고 3
	
	SmartPhoneFrame(){ //디폴트 접근제한자 - 같은 패키지 + 자기 객체 내
		c = getContentPane();
		c.setLayout(cardLayout);
		
		c.add("access", new AccessPanel(cardLayout, c)); //접속 화면 패널
		c.add("smartPhone", new SmartPhonePanel(cardLayout, c)); //스마트폰 화면 패널
		
		cardLayout.show(getContentPane(), "access"); //기본 화면 띄우기
		
		SE.smartState(true); //스마프폰 프레임  상태, 같은 패키지 SE의 메소드 사용
		SE.mf.openSmart.setState(true); //메인프레임 메뉴 open
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){ //윈도우 종료 이벤트 #참고 2
				closeSmart();
			}
		});
	}
	
	void closeSmart(){ //메인 프레임의 메뉴에서 사용해서 밖으로 빼놨음
		SE.smartState(false); //스마트폰 프레임 상태, 같은 패키지 SE의 메소드 사용
		SE.smartLoginState(false); //스마트폰 로그아웃
		SE.smartInterworkState(false); //스마트폰 연동 끊기
		SE.mf.openSmart.setState(false); //메인프레임 메뉴 open
		dispose(); //dispose = 지정 프레임 종료, exit = 모든 프레임 종료
	}
}

/*
#참고 1 setDefault 인자 설명
	http://junside.tistory.com/11
	 
#참고 2 서브 창 닫기
	http://blog.naver.com/PostView.nhn?blogId=wcwtmt&logNo=10172572539

#참고 3 카드 레이아웃
	http://msource.tistory.com/5
*/