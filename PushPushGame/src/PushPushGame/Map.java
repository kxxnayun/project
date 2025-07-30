package PushPushGame;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.Timer;
import javax.sound.sampled.*;
import java.io.File;

public class Map extends JPanel implements KeyListener, ActionListener {
	
    final int TILE = 40; // 벽 하나 크기
    final int ROWS = 10; // 세로
    final int COLS = 10; // 가로

    int[][] maze = new int[ROWS][COLS]; // 미로 배열, 각 칸의 상태를 저장

    // 플레이어 위치 좌표
    int playerX = 1;
    int playerY = 1;
    
    // 출구 위치
    int exitX = COLS - 2;
    int exitY = ROWS - 2;
    
    // 현재 점수
    int score = 0;
    
    // 플레이어가 출구에 도달했는지 여부
    boolean escape = false;
    
    // 게임 경과 시간
    int time = 0;

    // 게임 시작 프레임
    Start frame;
    
    // 현제 스테이지 번호
    int stage;
    
    // 적의 위치를 저장하는 리스트
    ArrayList<Point> enemies = new ArrayList<>();
    
    // 적 움직임, 위치를 랜덤으로 설정하기 위해 난수를 생성
    Random random = new Random();
    
    // 이미지 객체
    Image wallImage;
    Image playerImage;
    Image enemyImage;
    Image doorImage;
    Image coinImage;
    
    // 게임 타이머
    private Timer gameTimer;
    
    // 적 움직임 타이머
    private Timer enemyTimer;
    
    // 생성자: 게임 시작 프레임과 스테이지 번호를 받아 초기화
    public Map(Start frame, int stage) {
        this.frame = frame;
        this.stage = stage;
        
        // 이미지 불러오기
        wallImage = new ImageIcon("img/wall.jpg").getImage();
        playerImage = new ImageIcon("img/player.png").getImage();
        enemyImage = new ImageIcon("img/enemy.jpg").getImage();
        doorImage = new ImageIcon("img/door.png").getImage();
        coinImage = new ImageIcon("img/dia.png").getImage();
        
        // 스테이지 번호에 맞는 맵 세팅
        setMaze(stage);
        
        // 적 초기 위치 세팅
        setEnemy();
        
        // 키 입력을 받기 위해 포커스 설정 및 키 리스너 등록
        setFocusable(true);
        addKeyListener(this);
        
        // 적 움직임 타이머 생성 (0.3초마다 actionPerformed 호출)
        enemyTimer = new Timer(300, this);
        enemyTimer.start();
        
        // 게임 타이머 생성 (1초마다 시간 증가)
        gameTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                time++; // 1초 경과마다 시간 증가
                repaint(); // 화면 갱신 -> 시간 표시 변경
            }
        });
        
        gameTimer.start();
    }
    
    private void playSound(String filepath) { // 사운드 재생 메서드
        try {
            File file = new File(filepath);
            AudioInputStream stream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(stream);
            clip.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // 3차원 배열로 각 스테이지 맵 데이터 저장 (0 = 빈칸, 1 = 벽, 2 = 다이아몬드)
    private int[][][] stages = {
    	    {
    	        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
    	        {1, 0, 0, 1, 0, 2, 0, 1, 0, 1},
    	        {1, 0, 1, 1, 0, 0, 1, 0, 2, 1},
    	        {1, 0, 0, 0, 1, 0, 1, 0, 0, 1},
    	        {1, 1, 0, 2, 0, 0, 1, 1, 0, 1},
    	        {1, 0, 0, 1, 1, 0, 0, 0, 0, 1},
    	        {1, 0, 1, 0, 1, 2, 1, 1, 0, 1},
    	        {1, 0, 1, 0, 0, 0, 0, 2, 0, 1},
    	        {1, 0, 0, 1, 0, 1, 0, 1, 0, 1},
    	        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    	    },

    	    {
    	        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
    	        {1, 0, 0, 1, 0, 0, 1, 2, 0, 1},
    	        {1, 2, 1, 0, 1, 0, 0, 1, 0, 1},
    	        {1, 0, 0, 0, 0, 2, 1, 0, 0, 1},
    	        {1, 0, 1, 1, 0, 1, 1, 1, 0, 1},
    	        {1, 0, 1, 0, 0, 0, 0, 1, 0, 1},
    	        {1, 0, 1, 0, 1, 1, 0, 1, 2, 1},
    	        {1, 1, 0, 0, 0, 1, 0, 0, 0, 1},
    	        {1, 0, 1, 2, 0, 0, 1, 0, 0, 1},
    	        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    	    },

    	    {
    	        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
    	        {1, 0, 0, 2, 1, 0, 1, 2, 0, 1},
    	        {1, 0, 1, 0, 1, 0, 0, 0, 1, 1},
    	        {1, 0, 0, 0, 1, 0, 1, 0, 0, 1},
    	        {1, 1, 1, 0, 0, 0, 1, 0, 2, 1},
    	        {1, 0, 0, 1, 1, 0, 1, 1, 0, 1},
    	        {1, 2, 1, 0, 1, 0, 0, 0, 0, 1},
    	        {1, 0, 1, 0, 0, 2, 1, 0, 1, 1},
    	        {1, 0, 0, 1, 0, 1, 0, 0, 0, 1},
    	        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    	    }
    	};

    // 스테이지 번호에 따라 maze 배열 세팅
    private void setMaze(int stage) {
        maze = stages[stage - 1];
    }
    
    // 적의 초기 위치를 스테이지 난이도만큼 랜덤 배치
    private void setEnemy() {
        enemies.clear(); // 적의 기존 위치 초기화
        int enemyCount = stage; // 적 수 = 스테이지 번호
        
        for (int i = 0; i < enemyCount; i++) {
            Point enemy;
            do {
                int x = random.nextInt(COLS - 2) + 1; // 맵 가장자리 제외 나머지 칸 중 선택
                int y = random.nextInt(ROWS - 2) + 1;
                enemy = new Point(x, y);
            } while (maze[enemy.y][enemy.x] == 1 || (enemy.x == playerX && enemy.y == playerY)); // 벽, 플레이어 위치이면 안 됨
            enemies.add(enemy); // 적 배치
        }
    }
    
    // 화면 그리기 메서드
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // 맵 전체 그리기
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLS; x++) {
                if (maze[y][x] == 1) { // 1 = 벽이면
                	g.drawImage(wallImage, x * TILE, y * TILE, TILE, TILE, this); // 벽 이미지 그리기
                } else if (maze[y][x] == 2) { // 2 = 코인이면
                    g.drawImage(coinImage, x * TILE + 10, y * TILE + 10, 20, 20, this); // 코인 이미지 그리기
                }
            }
        }
        
        // 출구 이미지 그리기
        g.drawImage(doorImage, exitX * TILE + 5, exitY * TILE + 5, 30, 30, this);
        
        //플레이어가 출구에 도달하지 않았다면 플레이어 그리기
        if (escape == false) {
        	g.drawImage(playerImage, playerX * TILE + 5, playerY * TILE + 5, 30, 30, this);
        }
        
        
        // 적 그리기
        for (int i = 0; i < enemies.size(); i++) {
            Point enemy = enemies.get(i);
            g.drawImage(enemyImage, enemy.x * TILE + 5, enemy.y * TILE + 5, 30, 30, this);
        }
        
        // 점수 표시
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 20, 20);
        
        // 시간 표시
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Time: " + time + "s", 300, 20);

    }
    
    // 벽 밀기 시도 함수
    private boolean PushWall(int wallX, int wallY, int dx, int dy) {
        int nextX = wallX + dx;
        int nextY = wallY + dy;

        // 벽이 맵 밖으로 나가면 안 됨
        if (nextX <= 0 || nextY <= 0 || nextX >= COLS - 1 || nextY >= ROWS - 1) {
            return false;
        }

        // 다음 칸이 빈칸이어야 벽을 밀 수 있음
        if (maze[nextY][nextX] != 0) {
            return false;
        }

        // 벽 밀기 성공 -> 다음 칸에 벽 놓고 기존 칸 비우기
        maze[nextY][nextX] = 1;
        maze[wallY][wallX] = 0;

        return true;
    }
    
    // 플레이어 움직임 처리 함수
    private void movePlayer(int dx, int dy) {
        int newX = playerX + dx; // 이동 후 x 좌표
        int newY = playerY + dy; // 이동 후 y 좌표

        boolean moved = false; // 플레이어 이동 확인

        if (maze[newY][newX] == 1) { // 이동하려는 칸이 벽이면 벽 밀기 시도
            if (PushWall(newX, newY, dx, dy)) {
                moved = true; // 벽 밀기 성공 -> 플레이어 이동
            }
        } 
        else if (maze[newY][newX] == 0 || maze[newY][newX] == 2) { // 이동하려는 칸이 빈칸 또는 다이아몬드면 이동 가능
            moved = true;
        }

        if (moved) {
        	// 플레이어 좌표 업데이트
            playerX = newX;
            playerY = newY;
            
            // 코인을 먹은 경우 점수 증가 및 다이아몬드 제거
            if (maze[newY][newX] == 2) {
                maze[newY][newX] = 0; // 다이아몬드 사라짐
                score += 10; // 점수 10점 추가
                playSound("audio/dia.wav"); // 다이아몬드 먹는 효과음 재생
            }
            
            // 적 충돌 검사
            for (int i = 0; i < enemies.size(); i++) {
                Point enemy = enemies.get(i);
                if (playerX == enemy.x && playerY == enemy.y) { // 플레이어의 X 좌표와 Y 좌표가 일치한 경우
                	playSound("audio/enemy.wav");	// 적 충돌 효과음 재생
                    gameOver(); // 게임 오버 처리
                    return;
                }
            }
            
            // 플레이어가 출구에 도달했을 때
            if (playerX == exitX && playerY == exitY) {
                escape = true;
                String message = String.format("%s님, 스테이지 %d을(를) 클리어하셨습니다! 점수: %d점 / 시간: %d초", frame.getPlayerName(), stage, score, time);

                JOptionPane.showMessageDialog(this, message, "스테이지 클리어", JOptionPane.INFORMATION_MESSAGE);
                frame.switchTo("MainMenu"); // 화면 전환
            }

        }

        repaint();
    }

    // 적 움직임 처리
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < enemies.size(); i++) {
            Point enemy = enemies.get(i);
            int dx = 0;
            int dy = 0;
            switch (random.nextInt(4)) { // 랜덤으로 4방향 선택 후 이동 방향 결정
                case 0:
                    dx = 1; 
                    break;
                case 1:
                    dx = -1;
                    break;
                case 2:
                    dy = 1;
                    break;
                case 3:
                    dy = -1;
                    break;
            }

            int nx = enemy.x + dx; 
            int ny = enemy.y + dy;
            
            // 적이 이동할 칸이 이동 가능한지 확인
            if (nx > 0 && ny > 0 && nx < COLS - 1 && ny < ROWS - 1
                    && maze[ny][nx] != 1
                    && !(nx == exitX && ny == exitY)
                    && !(nx == playerX && ny == playerY)) {
                enemy.setLocation(nx, ny);
            }
            
            // 플레이어와 적 부딪히면 게임 오버
            if (playerX == enemy.x && playerY == enemy.y) {
                gameOver();
                return;
            }
        }

        repaint();
    }
    
    // 게임 오버
    private void gameOver() {
    		enemyTimer.stop(); // 적 움직임 타이머 정지
    		gameTimer.stop(); // 게임 타이머 정지
    		
    		JOptionPane.showMessageDialog(this, "게임 오버!");
            frame.switchTo("MainMenu");
    }
    
    // 키가 눌렸을 경우
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                movePlayer(0, -1);
                break;
            case KeyEvent.VK_DOWN:
                movePlayer(0, 1);
                break;
            case KeyEvent.VK_LEFT:
                movePlayer(-1, 0);
                break;
            case KeyEvent.VK_RIGHT:
                movePlayer(1, 0);
                break;
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {

    }
    @Override
    public void keyTyped(KeyEvent e) {

    }
    
    // 패널의 기본 크기 설정
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(400, 400);
    }
    
    
}