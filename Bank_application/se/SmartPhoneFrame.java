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


class SmartPhoneFrame extends JFrame{ //����Ʈ�� ������, �޴����� ų �� �ִ�.
	CardLayout cardLayout = new CardLayout(10, 10);
	Container c; //�ڷΰ��� ��ư��, #���� 3
	
	SmartPhoneFrame(){ //����Ʈ ���������� - ���� ��Ű�� + �ڱ� ��ü ��
		c = getContentPane();
		c.setLayout(cardLayout);
		
		c.add("access", new AccessPanel(cardLayout, c)); //���� ȭ�� �г�
		c.add("smartPhone", new SmartPhonePanel(cardLayout, c)); //����Ʈ�� ȭ�� �г�
		
		cardLayout.show(getContentPane(), "access"); //�⺻ ȭ�� ����
		
		SE.smartState(true); //�������� ������  ����, ���� ��Ű�� SE�� �޼ҵ� ���
		SE.mf.openSmart.setState(true); //���������� �޴� open
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){ //������ ���� �̺�Ʈ #���� 2
				closeSmart();
			}
		});
	}
	
	void closeSmart(){ //���� �������� �޴����� ����ؼ� ������ ������
		SE.smartState(false); //����Ʈ�� ������ ����, ���� ��Ű�� SE�� �޼ҵ� ���
		SE.smartLoginState(false); //����Ʈ�� �α׾ƿ�
		SE.smartInterworkState(false); //����Ʈ�� ���� ����
		SE.mf.openSmart.setState(false); //���������� �޴� open
		dispose(); //dispose = ���� ������ ����, exit = ��� ������ ����
	}
}

/*
#���� 1 setDefault ���� ����
	http://junside.tistory.com/11
	 
#���� 2 ���� â �ݱ�
	http://blog.naver.com/PostView.nhn?blogId=wcwtmt&logNo=10172572539

#���� 3 ī�� ���̾ƿ�
	http://msource.tistory.com/5
*/