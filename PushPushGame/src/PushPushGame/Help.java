package PushPushGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Help extends JPanel {
	private Image helpImage; // 배경으로 사용할 이미지 변수
	
    public Help(Start frame) { // Start 클래스에서 Help 화면을 생성할 때 호출됨
    	setLayout(null); 
    	helpImage = new ImageIcon("img/help1.png").getImage();
    	
    	// 도움말 문구 라벨 생성
    	JLabel label1 = new JLabel("▶ 방향키로 캐릭터를 이동시킬 수 있습니다.");
    	JLabel label2 = new JLabel("▶ 다이아를 수집하면 점수가 증가합니다.");
    	JLabel label3 = new JLabel("▶ 크리퍼에게 닿으면 게임이 종료됩니다.");
    	JLabel label4 = new JLabel("Help");
    	
    	// 각 라벨의 위치와 크기 설정
    	label1.setBounds(50, 220, 300, 30);
    	label2.setBounds(50, 260, 300, 30);
    	label3.setBounds(50, 300, 300, 30);
    	label4.setBounds(170, 50, 300, 30);
    	
    	// 라벨에 사용할 글꼴 정의
    	Font labelFont = new Font("바탕체", Font.BOLD, 13);
    	Font labelFont1 = new Font("바탕체", Font.BOLD, 30);
    	
    	// 라벨에 글꼴 적용
    	label1.setFont(labelFont);
    	label2.setFont(labelFont);
    	label3.setFont(labelFont);
    	label4.setFont(labelFont1);
    	
    	// 라벨 색상 하얀색으로 설정
    	label1.setForeground(Color.WHITE);
    	label2.setForeground(Color.WHITE);
    	label3.setForeground(Color.WHITE);	
    	label4.setForeground(Color.WHITE);
    	
    	// 라벨 추가
    	add(label1);
    	add(label2);
    	add(label3);
    	add(label4);
    	
    	// '뒤로' 버튼 생성
        JButton back = new JButton("뒤로");
        
        // 버튼 클릭 시 메인 메뉴로 전환하는 이벤트 리스너 등록
        back.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		frame.switchTo("MainMenu");
        	}
        });
        
        // 버튼을 패널에 추가
        add(back);
        
        // 버튼 위치 및 크기 설정
        back.setBounds(150, 350, 100, 30);

    }
    
    // 배경 이미지를 그리기 위한 오버라이딩
    @Override
    protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	g.drawImage(helpImage, 0, 0, 400, 400, this);
    }
    
    // 패널 기본 크기를 400x400으로 설정
    // 프레임에서 pack() 호출 시 사용
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(400, 400);
    }

}