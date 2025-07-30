package PushPushGame;

import javax.swing.*;
import java.awt.*;
import javax.sound.sampled.*;
import java.io.File;

public class Start extends JFrame { // JFrame을 상속받아 GUI 창 역할을 함
	
	private Clip clip; // 배경 음악 재생을 위한 Clip 객체
    CardLayout cardLayout; // 여러 화면을 전환하기 위한 CardLayout
    JPanel container; // CardLayout이 적용될 패널
    int maxStage = 3; // 최대 스테이지 수

    public Start() { // 생성자: 프로그램 시작 시 실행
        setTitle("Push Push Maze"); // 창 제목 설정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 닫기 버튼 클릭 시 프로그램 종료

        cardLayout = new CardLayout(); // CardLayout 객체 생성
        container = new JPanel(cardLayout); // CardLayout이 적용된 JPanel 생성

        container.add(new PushPushGame.MainMenu(this), "MainMenu"); // MainMenu 패널 추가
        container.add(new Help(this), "Help"); // Help 패널 추가

        add(container); // JFrame에 container 패널 추가

        pack(); // 컴포넌트 크기에 맞게 창 크기 조절
        setVisible(true); // 창을 화면에 표시
        
        playMusic("audio/background.wav"); // 음악 재생
    }
    
    private void playMusic(String filepath) { // 음악 재생 메서드
        try {
            File file = new File(filepath);
            AudioInputStream stream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(stream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private String playerName = "Unknown"; // 플레이어 이름 기본값

    public void setPlayerName(String name) { // 플레이어 이름 설정 메서드
        this.playerName = name;
    }

    public String getPlayerName() { // 플레이어 이름 반환 메서드
        return playerName;
    }

    public void startStage(int stage) { // 특정 스테이지를 시작하는 메서드
        String stageName = "Game" + stage; // 패널 이름 생성
        Map map = new Map(this, stage); // 해당 스테이지의 맵 생성
        map.setName(stageName); // 패널 이름 설정
        container.add(map, stageName); // container에 해당 스테이지 패널 추가
        cardLayout.show(container, stageName); // 해당 스테이지 화면으로 전환
        pack(); // 창 크기 재조정
    }

    public void switchTo(String name) { // 특정 화면으로 전환하는 메서드
        cardLayout.show(container, name); // 지정한 이름의 화면으로 전환
    }

    public static void main(String[] args) { // 프로그램 시작 지점
    	new Start();
    }
}
