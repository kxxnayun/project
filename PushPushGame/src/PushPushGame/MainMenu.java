package PushPushGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenu extends JPanel { // JPanel을 상속받아 메인 메뉴 화면을 구성
	private Image backgroundImage; // 배경 이미지 변수
	
    public MainMenu(Start frame) { // 생성자: Start 클래스의 객체를 인자로 받아서 GUI 구성
        setLayout(new BorderLayout()); // 패널 레이아웃 설정
        
        backgroundImage = new ImageIcon("img/background.png").getImage(); // 배경 이미지 로드
        
        JLabel title = new JLabel("Push Push Maze", SwingConstants.CENTER); // 타이틀 라벨 생성 및 가운데 정렬
        title.setFont(new Font("Arial", Font.BOLD, 35)); // 폰트 설정
        title.setBorder(BorderFactory.createEmptyBorder(50, 0, 40, 0)); // 위쪽에 여백 생성
        title.setForeground(Color.WHITE); // 글자 색상 흰색 지정
        add(title, BorderLayout.NORTH); // 타이틀을 상단에 추가
        
        JPanel panel = new JPanel(); // 버튼을 위치시킬 패널 생성
        panel.setLayout(null); // 절대 위치 사용
        panel.setOpaque(false); // 배경 투명하게 설정 -> 배경 이미지 보임
        
        // Start 버튼
        JButton start = new JButton("Start"); 
        start.setBounds(150, 120, 100, 40); // 위치 및 크기 설정
        start.setBackground(Color.WHITE); // 버튼 배경 색 설정
        start.setForeground(Color.BLACK); // 버튼 글자 색 설정
        start.setFont(new Font("Arial", Font.BOLD, 15)); // 폰트 설정
        panel.add(start); // 패널에 버튼 추가
        
        // Help 버튼
        JButton help = new JButton("Help");
        help.setBounds(150, 180, 100, 40);
        help.setBackground(Color.WHITE);
        help.setForeground(Color.BLACK);
        help.setFont(new Font("Arial", Font.BOLD, 15));
        panel.add(help);
        
        // 라디오 버튼 생성 (스테이지 선택)
        JRadioButton stage1 = new JRadioButton("Stage 1");
        JRadioButton stage2 = new JRadioButton("Stage 2");
        JRadioButton stage3 = new JRadioButton("Stage 3");
        ButtonGroup group = new ButtonGroup(); //버튼 그룹으로 묶기

        // 각 버튼의 위치 및 크기 설정
        stage1.setBounds(70, 80, 100, 30);
        stage2.setBounds(170, 80, 100, 30);
        stage3.setBounds(270, 80, 100, 30);
        
        // 라디오 버튼 배경 투명하게 설정
        stage1.setOpaque(false);
        stage2.setOpaque(false);
        stage3.setOpaque(false);
        
        // 라디오 버튼 글자 색 흰색으로 설정
        stage1.setForeground(Color.WHITE);
        stage2.setForeground(Color.WHITE);
        stage3.setForeground(Color.WHITE);
        
        // 패널에 라디오 버튼 추가
        group.add(stage1);
        group.add(stage2);
        group.add(stage3);
        
        // 기본 선택
        stage1.setSelected(true);
        
        // 패널에 라디오 버튼 추가
        panel.add(stage1);
        panel.add(stage2);
        panel.add(stage3);
        
        add(panel); // 패널 추가

        // Start 버튼 클릭 시 실행될 액션 리스너
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String playerName = null; // 플레이어 이름 변수 선언
            	
            	// 플레이어 이름을 입력할 때까지 반복
                while (playerName == null || playerName.trim().isEmpty()) { 
                    playerName = JOptionPane.showInputDialog(MainMenu.this, "플레이어 이름을 입력하세요"); // 입력창 표시
                    if (playerName == null) {
                        // 취소 버튼 누르면 루프 탈출
                        return;
                    }
                    if (playerName.trim().isEmpty()) { // 입력값이 공백일 경우 창 표시
                        JOptionPane.showMessageDialog(MainMenu.this, "플레이어 이름을 다시 입력하세요");
                    }
                }
                
                // 입력된 이름을 frame에 전달
                frame.setPlayerName(playerName.trim());
                
                // 선택된 스테이지 확인
                int selectedStage = 1;
                if (stage2.isSelected()) 
                	selectedStage = 2;
                else if (stage3.isSelected()) 
                	selectedStage = 3;

                frame.startStage(selectedStage); // 해당 스테이지 시작
            }
        });
        
        // Help 버튼 클릭 시 실행될 액션 리스너
        help.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.switchTo("Help"); // Help 화면으로 전환
            }
        });

    }
    
    // 배경 이미지를 그리는 메서드
    @Override
    protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	g.drawImage(backgroundImage, 0, 0, 400, 400, this);
    }
    
    // 패널의 기본 크기 설정
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(400, 400);
    }

}
