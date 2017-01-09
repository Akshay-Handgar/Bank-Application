package se;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

class shopPanel extends JPanel{ //MainFrame �Ҽ�
	JLabel shop = new JLabel("SHOP");
	
	JLabel imageLabel = new JLabel();
	
	JPanel totalPanel = new JPanel(); //��ü �г�, (image + payment)
	JPanel imagePanel = new JPanel(); //�̹��� �г�
	JPanel paymentPanel = new JPanel(); //�ŷ� �г�, (shopItem + pay)
	//.\\MainPageImg\\main_frame_gif.gif"
	ImageIcon shop_gif = new ImageIcon(".\\Images\\MainPageImg\\main_frame_gif.gif");
	ImageIcon dosmas_gif = new ImageIcon(".\\Images\\Shop\\dosmas.gif");
	ImageIcon cgv_gif = new ImageIcon(".\\Images\\Shop\\cgv.gif");
	ImageIcon daeseong_gif = new ImageIcon(".\\Images\\Shop\\daeseong.gif");
	ImageIcon everland_gif = new ImageIcon(".\\Images\\Shop\\everland.gif");
	ImageIcon spoany_gif = new ImageIcon(".\\Images\\Shop\\spoany.gif");
	
	JPanel shopItemPanel = new JPanel(); //���� & ���� �г�
	JPanel payPanel = new JPanel(); //���� �г� (�հ� + button)
	JPanel buttonPanel = new JPanel(); //���� �г��� ��ư �κ� (payment + cancel)
	
	String[] shopStr = {"������ ����ּ���", "DosMas(��������)", "�뼺��", "�Ⱦ� �ｺ", "CGV", "��������"}; //���� ���
	DefaultListModel itemModel = new DefaultListModel(); //JList ��Ʈ�� �ϴ� ��ü
	DefaultListModel payModel = new DefaultListModel(); 
	static int thisShopBenefit = 0; //���� ������ ���� ��ȣ, 0 = ���� ����, 1 = ����, 2 = �
	
	JComboBox shopCb = new JComboBox(shopStr); //���� �޺��ڽ�
	JList itemList = new JList(); //������ ���� ��ǰ&���� ����Ʈ
	JList payList = new JList(); //���� ����Ʈ

	
	int result = 0;
	JLabel resultLabel = new JLabel("�հ�: " + result + "��");
	JButton paymentBtn = new JButton("Buy"); //���� ��ư
	JButton cancelBtn = new JButton("Cancel"); //��� ��ư
	
	Border border = BorderFactory.createTitledBorder(""); // �׵θ��߰�
	
	shopPanel(){
		setLayout(new BorderLayout());
		add(shop, BorderLayout.NORTH); //��
		shop.setHorizontalAlignment(JLabel.CENTER); //�� �߾�����
		add(totalPanel, BorderLayout.CENTER);
		
		//��ü �г�, (image + payment)
		totalPanel.setLayout(new GridLayout(2, 0));
		totalPanel.add(imagePanel); //�̹��� �г�
		imagePanel.setBorder(border); //�̹��� �κ� ǥ�ÿ�
		totalPanel.add(paymentPanel); //�ŷ� �г�
		
		//Shop�г��� ���� �� default �̹���_���� �̹����� ��
		imagePanel.add(imageLabel);
		imageLabel.setIcon(shop_gif);		
		
		//�ŷ� �г�, (shopItem + pay)
		paymentPanel.setLayout(new GridLayout(0, 2));
		paymentPanel.add(shopItemPanel); //���� & ���� �г�
		paymentPanel.add(payPanel); //���� �г�
		
		//���� & ���� �г�
		shopItemPanel.setLayout(new BorderLayout());
		shopItemPanel.add(shopCb, BorderLayout.NORTH);
		shopItemPanel.add(new JScrollPane(itemList), BorderLayout.CENTER);
		shopItemPanel.setBorder(border);
		
		shopCb.addActionListener(new ShopClicked());
		itemList.addMouseListener(new ItemListClicked());
		
		//���� �г� (�հ� + ���� + ��ư �г�)
		payPanel.setLayout(new BorderLayout());
		resultLabel.setHorizontalAlignment(JLabel.CENTER); //�� �߾�����
		payPanel.add(resultLabel, BorderLayout.NORTH);
		
		payPanel.add(payList, BorderLayout.CENTER); //���� ���� ���� ��ũ�� �ٿ��ߵ�
		JScrollPane scrollPane = new JScrollPane(payList); //��ũ�� ����
		payPanel.add(scrollPane, BorderLayout.CENTER); //��ũ�� ���̱�
		
		payList.addMouseListener(new PayListClicked());
		
		buttonPanel.setLayout(new GridLayout(0, 2));
		buttonPanel.add(cancelBtn);
		buttonPanel.add(paymentBtn);
		
		payPanel.add(buttonPanel, BorderLayout.SOUTH);
		payPanel.setBorder(border);
		
		paymentBtn.addMouseListener(new BuyClicked());
		cancelBtn.addMouseListener(new CancelClicked());
	}
	
	private class ShopClicked implements ActionListener{ //���� ���� #���� 1
		public void actionPerformed(ActionEvent e){
			if(e.getSource() == shopCb){
				itemModel.clear();
				payModel.clear();
				setResult(null, 'x');
				
				switch(shopCb.getSelectedIndex()){ //��ǰ(space)|(space)����("��") �������� �ۼ�				
				case 1: //��������
					thisShopBenefit = 1; //����
					itemModel.addElement("�⺻ �θ��� | 3000��");
					itemModel.addElement("ġŲ �θ��� | 4000��");
					itemModel.addElement("�ſ� �θ��� | 5000��");
					itemModel.addElement("�θ��� ��Ʈ | 8000��");					
					imagePanel.add(imageLabel);		// �������� �������� ���� �������� �̹����� �ٱϴ�.
					imageLabel.setIcon(dosmas_gif);	//			
					itemList.setModel(itemModel);
					break;
				case 2: //�뼺��
					thisShopBenefit = 1; //����
					itemModel.addElement("¥��� | 2000��");
					itemModel.addElement("«�� | 3000��");
					itemModel.addElement("������(��) | 5000��");
					itemModel.addElement("������(��) | 8000��");
					itemModel.addElement("������(��) | 10000��");
					itemModel.addElement("������ | 13000��");
					itemModel.addElement("�뼺�� Ǫ�� ��Ʈ | 20000��");
					imagePanel.add(imageLabel);		
					imageLabel.setIcon(daeseong_gif);
					itemList.setModel(itemModel);
					break;
				case 3: //�ｺ��
					thisShopBenefit = 2; //�
					itemModel.addElement("�Ϸ� �̿�� | 2000��");
					itemModel.addElement("������ �̿�� | 10000��");
					itemModel.addElement("�Ѵ� �̿�� | 20000��");
					itemModel.addElement("���� �̿�� | 30000��");
					imagePanel.add(imageLabel);		
					imageLabel.setIcon(spoany_gif);
					itemList.setModel(itemModel);
					break;
				case 4: //CGV
					thisShopBenefit = 3; //��ȭ
					itemModel.addElement("��ȭ Ƽ�� | 7000��");
					itemModel.addElement("CGV �޺� | 10000��");
					itemModel.addElement("��¡�� �޺�| 12000��");
					imagePanel.add(imageLabel);		
					imageLabel.setIcon(cgv_gif);
					itemList.setModel(itemModel);
					break;
				case 5: //���̰���
					thisShopBenefit = 4; //���̰���
					itemModel.addElement("���� �̿�� | 20000��");
					itemModel.addElement("Ʈ�� 5 | 9000��");
					itemModel.addElement("���� �̿�� | 8000��");
					itemModel.addElement("�ػ��� | 2000��");
					itemModel.addElement("�Ǵ� �Ӹ��� | 10000��");
					imagePanel.add(imageLabel);		
					imageLabel.setIcon(everland_gif);
					itemList.setModel(itemModel);
					break;
				default:
					thisShopBenefit = 0;
					break;
				}
				
				itemList.setModel(itemModel);
			}
		}
	}
	
	private void setResult(String str, char op){ //�հ� ���ϱ�
		if(str == null){ //�ʱ�ȭ
			result = 0;
		}
		else{
			String s[] = str.split("\\|"); //���ݸ� ������, s[0] = ��ǰ, s[1] = ����
			s[1] = s[1].replace("��", "").trim();
			
			if(op == '+'){ //���ϱ�!
				result += Integer.valueOf(s[1]);
			}
			else if(op == '-'){ //����!
				result -= Integer.valueOf(s[1]);
			}
		}
		resultLabel.setText(("�հ�: " + result + "��"));
	}
	
	private class ItemListClicked extends MouseAdapter{ //���� ����
		public void mouseClicked(MouseEvent e){
			if(e.getClickCount() == 2){
				payModel.addElement(itemList.getSelectedValue()); //���� �ֱ�!
				payList.setModel(payModel);
				
				payList.setSelectedIndex(payModel.getSize()-1); 
				payList.ensureIndexIsVisible(payModel.getSize()-1); //���⸦ �������� ��ũ�� ������, #���� 2
				
				setResult((String)itemList.getSelectedValue(), '+'); //�հ� ���ϱ�
			}
		}
	}
	
	private class PayListClicked extends MouseAdapter{ //���� ����
		public void mouseClicked(MouseEvent e){
			if(e.getClickCount() == 2){
				if(payList.getSelectedIndex() >= 0){
					setResult((String)payList.getSelectedValue(), '-'); //�հ� ���ϱ�
					
					payModel.remove(payList.getSelectedIndex()); //���� ����!
				}
			}
		}
	}
	
	private class BuyClicked extends MouseAdapter{ //Buy ��ư
		public void mouseClicked(MouseEvent e) {
			if(e.getSource() == paymentBtn){
				if(result == 0){ //���� �Ұ� ����, #���� 3
					JOptionPane.showMessageDialog(totalPanel, "������ ����ּ���", "���� ����", JOptionPane.WARNING_MESSAGE);
				}
				else{					
					if(SE.smartInterworkState){ //���� ���̸� ����Ʈ������ ���
						if(SE.banking(SmartPhonePanel.userAccountNumber.getText(), shopCb.getSelectedItem() + "", result, '-', true)){ //�ŷ� ����
							payClear(); //���� �ʱ�ȭ
						}
					}
					else{ //���� ���� -> �����ϱ� or ���
						Object[] options = {"�����ϱ�", "���� �ŷ�", "���"};
						int select = JOptionPane.showOptionDialog(totalPanel, 
								"���� ������ �������ּ���.", "���� ����", 
								JOptionPane.YES_NO_CANCEL_OPTION, 
								JOptionPane.QUESTION_MESSAGE, 
								null, options, options[2]);
						
						if(select == 0){ //�����ϱ�
							if(SE.smartState == false){ //����Ʈ�� ����������
								JOptionPane.showMessageDialog(totalPanel, "����Ʈ���� ���ּ���", "����Ʈ�� ����", JOptionPane.WARNING_MESSAGE);
							}
							else if(SE.smartLoginState == false){ //�α��� �ȵ�����
								JOptionPane.showMessageDialog(totalPanel, "�α��� ���ּ���", "�α��� ����", JOptionPane.WARNING_MESSAGE);
							}
							else{ //�����ϱ�
								SE.smartInterworkState(true); //�����ϱ�
								
								if(SE.banking(SmartPhonePanel.userAccountNumber.getText(), shopCb.getSelectedItem() + "", result, '-', true)){ //�ŷ� ����
									payClear(); //���� �ʱ�ȭ
								}
							}
						}
						else if(select == 1){ //���� �ŷ� #���� 4, ���� 5
							String id = JOptionPane.showInputDialog(totalPanel, "ID�� �Է����ּ���.", "���� �Է�", JOptionPane.QUESTION_MESSAGE);
							
							if(id != null){ //�Է��� ������
								JPasswordField passwd = new JPasswordField();
								int ok = JOptionPane.showConfirmDialog(totalPanel, passwd, "Enter Password", JOptionPane.WARNING_MESSAGE);
								
								if(ok == JOptionPane.OK_OPTION){ //Ȯ�� ��ư ������ ��
									String user = SE.checkUserInfo(id, passwd.getText());
									
									if(user == null){ //���� �ƴϴ� -> ���� ���� �˸�
										JOptionPane.showMessageDialog(totalPanel, "���̵� �Ǵ� ��й�ȣ�� �ٽ� �Է��� �ּ���", "�α��� ����", JOptionPane.WARNING_MESSAGE);
									}
									else{ //���̴�!! -> �ŷ�, ����Ʈ���� ���� ���¸� �˶� �߰�
										if((SE.smartLoginState == true) && (SmartPhonePanel.userAccountNumber.getText().equals(user))){ 
											if(SE.banking(user, shopCb.getSelectedItem() + "", result, '-', true)){ //�ŷ� ����
												payClear(); //���� �ʱ�ȭ
											}
										}
										else{
											if(SE.banking(user, shopCb.getSelectedItem() + "", result, '-', false)){ //�ŷ� ����
												payClear(); //���� �ʱ�ȭ
											}
										}
									}
								}
							}
							
						}
						else if(select == 2){ } //���
					}
				}
			}
		}
	}
	
	private class CancelClicked extends MouseAdapter{ //Cancel ��ư
		public void mouseClicked(MouseEvent e) {
			if(e.getSource() == cancelBtn){
				payClear(); //���� �ʱ�ȭ
			}
		}
	}
	
	private void payClear(){ //���� �ʱ�ȭ
		payModel.clear(); //�ʱ�ȭ�ϱ�
		payList.setModel(payModel);
		setResult(null, '0');
	}
}

/*
���� 1 JList ����
	http://blog.naver.com/zladnrms/220216712385
���� 2 List �� ����
	http://blog.daum.net/dmno21/29
���� 3 �⺻ ��ȭ ����
 	http://hallang.tistory.com/137
 ���� 4 �Է� �޴� ��ȭ����
 	http://hallang.tistory.com/136
 ���� 5 �н����� ��ȭ����
 	http://stackoverflow.com/questions/8881213/joptionpane-to-get-password
*/
