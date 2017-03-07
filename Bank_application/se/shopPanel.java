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

class shopPanel extends JPanel{ 
	JLabel shop = new JLabel("SHOP");
	
	JLabel imageLabel = new JLabel();
	
	JPanel totalPanel = new JPanel(); //전체 패널, (image + payment)
	JPanel imagePanel = new JPanel(); //이미지 패널
	JPanel paymentPanel = new JPanel(); //거래 패널, (shopItem + pay)
	//.\\MainPageImg\\main_frame_gif.gif"
	ImageIcon shop_gif = new ImageIcon(".\\Images\\MainPageImg\\main_frame_gif.gif");
	ImageIcon dosmas_gif = new ImageIcon(".\\Images\\Shop\\dosmas.gif");
	ImageIcon cgv_gif = new ImageIcon(".\\Images\\Shop\\cgv.gif");
	ImageIcon daeseong_gif = new ImageIcon(".\\Images\\Shop\\daeseong.gif");
	ImageIcon everland_gif = new ImageIcon(".\\Images\\Shop\\everland.gif");
	ImageIcon spoany_gif = new ImageIcon(".\\Images\\Shop\\spoany.gif");
	
	JPanel shopItemPanel = new JPanel(); //상점 & 물건 패널
	JPanel payPanel = new JPanel(); //결제 패널 (합계 + button)
	JPanel buttonPanel = new JPanel(); //결제 패널의 버튼 부분 (payment + cancel)
	
	String[] shopStr = {"상점을 골라주세요", "DosMas(도스마스)", "대성장", "안양 헬스", "CGV", "에버랜드"}; //상점 목록
	DefaultListModel itemModel = new DefaultListModel(); 
	DefaultListModel payModel = new DefaultListModel(); 
	static int thisShopBenefit = 0; //현재 상점의 혜택 번호, 0 = 상점 없음, 1 = 음식, 2 = 운동
	
	JComboBox shopCb = new JComboBox(shopStr); //상점 콤보박스
	JList itemList = new JList(); //상점에 따른 물품&가격 리스트
	JList payList = new JList(); //내역 리스트

	
	int result = 0;
	JLabel resultLabel = new JLabel("합계: " + result + "원");
	JButton paymentBtn = new JButton("Buy"); //결제 버튼
	JButton cancelBtn = new JButton("Cancel"); //취소 버튼
	
	Border border = BorderFactory.createTitledBorder(""); // 테두리추가
	
	shopPanel(){
		setLayout(new BorderLayout());
		add(shop, BorderLayout.NORTH); 
		shop.setHorizontalAlignment(JLabel.CENTER);=
		add(totalPanel, BorderLayout.CENTER);
		
		//전체 패널, (image + payment)
		totalPanel.setLayout(new GridLayout(2, 0));
		totalPanel.add(imagePanel); //이미지 패널
		imagePanel.setBorder(border); //이미지 부분 표시용
		totalPanel.add(paymentPanel); //거래 패널
		
		//Shop패널의 선택 전 default 이미지_메인 이미지와 동
		imagePanel.add(imageLabel);
		imageLabel.setIcon(shop_gif);		
		
		//거래 패널, (shopItem + pay)
		paymentPanel.setLayout(new GridLayout(0, 2));
		paymentPanel.add(shopItemPanel); //상점 & 물건 패널
		paymentPanel.add(payPanel); //결제 패널
		
		//상점 & 물건 패널
		shopItemPanel.setLayout(new BorderLayout());
		shopItemPanel.add(shopCb, BorderLayout.NORTH);
		shopItemPanel.add(new JScrollPane(itemList), BorderLayout.CENTER);
		shopItemPanel.setBorder(border);
		
		shopCb.addActionListener(new ShopClicked());
		itemList.addMouseListener(new ItemListClicked());
		
		//결제 패널 (합계 + 내역 + 버튼 패널)
		payPanel.setLayout(new BorderLayout());
		resultLabel.setHorizontalAlignment(JLabel.CENTER); 
		payPanel.add(resultLabel, BorderLayout.NORTH);
		
		payPanel.add(payList, BorderLayout.CENTER); 
		JScrollPane scrollPane = new JScrollPane(payList); //스크롤 생성
		payPanel.add(scrollPane, BorderLayout.CENTER); //스크롤 붙이기
		
		payList.addMouseListener(new PayListClicked());
		
		buttonPanel.setLayout(new GridLayout(0, 2));
		buttonPanel.add(cancelBtn);
		buttonPanel.add(paymentBtn);
		
		payPanel.add(buttonPanel, BorderLayout.SOUTH);
		payPanel.setBorder(border);
		
		paymentBtn.addMouseListener(new BuyClicked());
		cancelBtn.addMouseListener(new CancelClicked());
	}
	
	private class ShopClicked implements ActionListener{ //상점 고르기
		public void actionPerformed(ActionEvent e){
			if(e.getSource() == shopCb){
				itemModel.clear();
				payModel.clear();
				setResult(null, 'x');
				
				switch(shopCb.getSelectedIndex()){ //상품(space)|(space)가격("원") 형식		
				case 1: //도스마스
					thisShopBenefit = 1; //음식
					itemModel.addElement("기본 부리또 | 3000원");
					itemModel.addElement("치킨 부리또 | 4000원");
					itemModel.addElement("매운 부리또 | 5000원");
					itemModel.addElement("부리또 세트 | 8000원");					
					imagePanel.add(imageLabel);		
					imageLabel.setIcon(dosmas_gif);			
					itemList.setModel(itemModel);
					break;
				case 2: //대성장
					thisShopBenefit = 1; //음식
					itemModel.addElement("짜장면 | 2000원");
					itemModel.addElement("짬뽕 | 3000원");
					itemModel.addElement("탕수육(소) | 5000원");
					itemModel.addElement("탕수육(중) | 8000원");
					itemModel.addElement("탕수육(대) | 10000원");
					itemModel.addElement("깐쇼새우 | 13000원");
					itemModel.addElement("대성장 푸짐 세트 | 20000원");
					imagePanel.add(imageLabel);		
					imageLabel.setIcon(daeseong_gif);
					itemList.setModel(itemModel);
					break;
				case 3: //헬스장
					thisShopBenefit = 2; //운동
					itemModel.addElement("하루 이용권 | 2000원");
					itemModel.addElement("일주일 이용권 | 10000원");
					itemModel.addElement("한달 이용권 | 20000원");
					itemModel.addElement("세달 이용권 | 30000원");
					imagePanel.add(imageLabel);		
					imageLabel.setIcon(spoany_gif);
					itemList.setModel(itemModel);
					break;
				case 4: //CGV
					thisShopBenefit = 3; //영화
					itemModel.addElement("영화 티켓 | 7000원");
					itemModel.addElement("CGV 콤보 | 10000원");
					itemModel.addElement("오징어 콤보| 12000원");
					imagePanel.add(imageLabel);		
					imageLabel.setIcon(cgv_gif);
					itemList.setModel(itemModel);
					break;
				case 5: //놀이공원
					thisShopBenefit = 4; //놀이공원
					itemModel.addElement("자유 이용권 | 20000원");
					itemModel.addElement("트랙 5 | 9000원");
					itemModel.addElement("오후 이용권 | 8000원");
					itemModel.addElement("솜사탕 | 2000원");
					itemModel.addElement("판다 머리띠 | 10000원");
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
	
	private void setResult(String str, char op){ //합계 구하기
		if(str == null){ 
			result = 0;
		}
		else{
			String s[] = str.split("\\|"); // s[0] = 상품, s[1] = 가격
			s[1] = s[1].replace("원", "").trim();
			
			if(op == '+'){ 
				result += Integer.valueOf(s[1]);
			}
			else if(op == '-'){ 
				result -= Integer.valueOf(s[1]);
			}
		}
		resultLabel.setText(("합계: " + result + "원"));
	}
	
	private class ItemListClicked extends MouseAdapter{ //물건 고르기
		public void mouseClicked(MouseEvent e){
			if(e.getClickCount() == 2){
				payModel.addElement(itemList.getSelectedValue()); 
				payList.setModel(payModel);
				
				payList.setSelectedIndex(payModel.getSize()-1); 
				payList.ensureIndexIsVisible(payModel.getSize()-1); 
				
				setResult((String)itemList.getSelectedValue(), '+'); //합계 구하기
			}
		}
	}
	
	private class PayListClicked extends MouseAdapter{ //물건 빼기
		public void mouseClicked(MouseEvent e){
			if(e.getClickCount() == 2){
				if(payList.getSelectedIndex() >= 0){
					setResult((String)payList.getSelectedValue(), '-'); //합계 구하기
					
					payModel.remove(payList.getSelectedIndex()); //물건 제거
				}
			}
		}
	}
	
	private class BuyClicked extends MouseAdapter{ //Buy 버튼
		public void mouseClicked(MouseEvent e) {
			if(e.getSource() == paymentBtn){
				if(result == 0){ 
					JOptionPane.showMessageDialog(totalPanel, "물건을 골라주세요", "구매 오류", JOptionPane.WARNING_MESSAGE);
				}
				else{					
					if(SE.smartInterworkState){ 
						if(SE.banking(SmartPhonePanel.userAccountNumber.getText(), shopCb.getSelectedItem() + "", result, '-', true)){ //거래 진행
							payClear(); 
						}
					}
					else{ 
						Object[] options = {"연동하기", "계좌 거래", "취소"};
						int select = JOptionPane.showOptionDialog(totalPanel, 
								"결제 수단을 선택해주세요.", "결제 선택", 
								JOptionPane.YES_NO_CANCEL_OPTION, 
								JOptionPane.QUESTION_MESSAGE, 
								null, options, options[2]);
						
						if(select == 0){ //연동하기
							if(SE.smartState == false){ 
								JOptionPane.showMessageDialog(totalPanel, "스마트폰을 켜주세요", "스마트폰 오류", JOptionPane.WARNING_MESSAGE);
							}
							else if(SE.smartLoginState == false){ 
								JOptionPane.showMessageDialog(totalPanel, "로그인 해주세요", "로그인 오류", JOptionPane.WARNING_MESSAGE);
							}
							else{ 
								SE.smartInterworkState(true); 
								
								if(SE.banking(SmartPhonePanel.userAccountNumber.getText(), shopCb.getSelectedItem() + "", result, '-', true)){ //거래 진행
									payClear(); 
								}
							}
						}
						else if(select == 1){ 
							String id = JOptionPane.showInputDialog(totalPanel, "ID를 입력해주세요.", "계좌 입력", JOptionPane.QUESTION_MESSAGE);
							
							if(id != null){
								JPasswordField passwd = new JPasswordField();
								int ok = JOptionPane.showConfirmDialog(totalPanel, passwd, "Enter Password", JOptionPane.WARNING_MESSAGE);
								
								if(ok == JOptionPane.OK_OPTION){ 
									String user = SE.checkUserInfo(id, passwd.getText());
									
									if(user == null){ 
										JOptionPane.showMessageDialog(totalPanel, "아이디 또는 비밀번호를 다시 입력해 주세요", "로그인 오류", JOptionPane.WARNING_MESSAGE);
									}
									else{ 
										if((SE.smartLoginState == true) && (SmartPhonePanel.userAccountNumber.getText().equals(user))){ 
											if(SE.banking(user, shopCb.getSelectedItem() + "", result, '-', true)){ 
												payClear(); 
											}
										}
										else{
											if(SE.banking(user, shopCb.getSelectedItem() + "", result, '-', false)){ 
												payClear(); 
											}
										}
									}
								}
							}
							
						}
						else if(select == 2){ } //cancle
					}
				}
			}
		}
	}
	
	private class CancelClicked extends MouseAdapter{ //Cancel 버튼
		public void mouseClicked(MouseEvent e) {
			if(e.getSource() == cancelBtn){
				payClear(); //내역 초기화
			}
		}
	}
	
	private void payClear(){ //내역 초기화
		payModel.clear(); //초기화하기
		payList.setModel(payModel);
		setResult(null, '0');
	}
}

/*
참고 1 JList 사용법
	http://blog.naver.com/zladnrms/220216712385
참고 2 List 끝 보기
	http://blog.daum.net/dmno21/29
참고 3 기본 대화 상자
 	http://hallang.tistory.com/137
 참고 4 입력 받는 대화상자
 	http://hallang.tistory.com/136
 참고 5 패스워드 대화상자
 	http://stackoverflow.com/questions/8881213/joptionpane-to-get-password
*/
