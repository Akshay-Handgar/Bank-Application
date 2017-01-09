package se;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

class AccessPanel extends JPanel{
	JLabel smartPhoneLabel = new JLabel("Android");
	JPanel labelPanel = new JPanel();
	JLabel idLabel = new JLabel("ID");
	JLabel passwdLabel = new JLabel("passwd");
	
	JPanel totalPanel = new JPanel(); //����κ� ��ü
	JPanel fieldPanel = new JPanel();
	
	JTextField idField = new JTextField(8);
	JPasswordField passwdField = new JPasswordField(8);
	JButton accessBtn = new JButton("����");
	
	Border border = BorderFactory.createTitledBorder("");
	
	AccessPanel(CardLayout cardLayout, Container c){
		setSize(300, 500);
		setLayout(new BorderLayout());
		
		smartPhoneLabel.setHorizontalAlignment(JLabel.CENTER); //�� �߾�����
		
		totalPanel.setLayout(new GridLayout(0,2));
		totalPanel.setBorder(border);
		for(int i =0; i<8;i++){
			totalPanel.add(new JPanel()); //�ڸ�ä��� �г�
		}
		totalPanel.add(idLabel);
		totalPanel.add(idField);
		totalPanel.add(passwdLabel);
		totalPanel.add(passwdField);
		for(int i =0; i<14;i++){
			totalPanel.add(new JPanel()); //�ڸ�ä��� �г�
		}
		
		//���� ��ư
		accessBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) { //���� ��ư �̺�Ʈ, �� ���� Ȯ���ϱ� -> ���� ���/�����
				if(e.getSource() == accessBtn){
					String str = SE.checkUserInfo(idField.getText(), passwdField.getText()); //�� ���� Ȯ��
					
					if(str == null){ //���� �ƴϴ� -> ���� ���� �˸�
						passwdField.setText(""); //�ʱ�ȭ �����ֱ�
						JOptionPane.showMessageDialog(SE.spf, "���̵� �Ǵ� ��й�ȣ�� �ٽ� �Է��� �ּ���", "�α��� ����", JOptionPane.WARNING_MESSAGE);
					}
					else{ //���̴�!! -> ȭ�� �ٲٱ�
						idField.setText(""); //�ʱ�ȭ �����ֱ�
						passwdField.setText(""); //�ʱ�ȭ �����ֱ�
					
						SE.smartLoginState(true); //�α���!
						SmartPhonePanel.initUserInfo(str); //�� ���� �޾ƿ���
						cardLayout.show(c, "smartPhone"); //����Ʈ�� ȭ������ �ٲٱ�
					}
				}
			}
		}); 
		
		add(smartPhoneLabel,BorderLayout.NORTH);
		add(totalPanel,BorderLayout.CENTER);
		add(accessBtn,BorderLayout.SOUTH);
		
		setVisible(true);
	}
}
