package GUI;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;

import Astar.Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class GUI extends JFrame implements Runnable{

	static int rowdata;
	static int columndata;
	static Button buttons[][]= new Button[rowdata][columndata];
	static int Mode;
	static File textfile;
	Thread autoRefresh;
	 
	
	public void refresh(char[][] map){
		for(int i=0; i<map.length; i++){
			for(int j=0; j<map[0].length; j++){
				char pointer = map[i][j];
				switch(pointer){
				case 'S':{
					buttons[i][j].setForeground(Color.green);
					break;
				}
				case 'E':{
					buttons[i][j].setForeground(Color.red);
					break;
				}
				case '.':{
					buttons[i][j].setForeground(Color.DARK_GRAY);
					break;
				}
				case 'W':{
					buttons[i][j].setForeground(Color.white);
					break;
				}
				case '-':{
					buttons[i][j].setForeground(Color.ORANGE);
					break;
				}
				case '@':{
					buttons[i][j].setForeground(Color.magenta);
					break;
				}
				}
			}
		}
	}
	
	public void run() {
		this.refresh(Map);
	}

	static Main ASTAR;
	
	public GUI(int row, int column, File text) {
		Font main = new Font("맑은 고딕",0,30);
		Font sub = new Font("맑은 고딕",0,15);
		Image img = null;
		try {
			File sourceimage = new File("src/overwatch.png");
			img = ImageIO.read(sourceimage);
		} catch (IOException e) {
			System.out.println("이미지파일이 없습니다.");
		}
	
		JLabel logo = new JLabel(new ImageIcon(img));
		textfile = text;
		rowdata = row;
		columndata = column;
		
		setTitle("A-STAR Algorithm");
		setSize(500, 500);
		setVisible(true);
		Container c = getContentPane();

		c.setBackground(Color.DARK_GRAY);
		setLayout(null);
		add(new showmap());
		add(new BGR());
		add(new RUI());
		

		JLabel Title = new JLabel("A Star Algorithm");
		Title.setLocation(10,430);
		Title.setSize(300,40);
		Title.setFont(main);
		Title.setForeground(Color.WHITE);
		
		logo.setSize(100,100);
		logo.setLocation(385, 280);
		add(logo);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//		c.add(Title);
		// setResizable(false);
	}
	
	public static class status extends Panel{
		Main ASTAR = new Main();
		public JFileChooser jfc = new JFileChooser();
		
		static Button buttons[][] = new Button[rowdata][columndata];
		static char[][] Map = new char[rowdata][columndata];
		
		static File text;
		Main main = new Main();
		
		public void MapDisable(){
			for (int r = 0; r < buttons.length; r++) {
				for (int j = 0; j < buttons[0].length; j++) {
					buttons[r][j].setEnabled(false);
				}
			}
		}
		
		public void MapEnable(){
			for (int r = 0; r < buttons.length; r++) {
				for (int j = 0; j < buttons[0].length; j++) {
					buttons[r][j].setEnabled(true);
				}
			}
		}
		

		
	}

	public class BGR extends status {
		Main ASTAR;
		public BGR() {
			
			Font sub = new Font("맑은 고딕",Font.BOLD,12);
			setBackground(null);		
			Container M = getContentPane();			
			setSize((int) ((M.getSize().height) * (1.0)), (int) ((M.getSize().width) * (0.08))+15);
			setLocation(15,(int) ((M.getSize().height) * (0.8))+35);
			setLayout(new GridLayout(2,2, 15, 5));
			JButton buttons_BUI[] = new JButton[4];
			for (int i = 0; i < buttons_BUI.length; i++) {
				buttons_BUI[i] = new JButton("매뉴" + (i + 1));
				buttons_BUI[i].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent a) {
						for (int i = 0; i < buttons_BUI.length; i++) {
							if (a.getSource() == buttons_BUI[i]) {
								switch(buttons_BUI[i].getText()){
								case "출발점 지정" :{
									buttons_BUI[0].setText("출발점 지정 종료");
									buttons_BUI[1].setEnabled(false);
									buttons_BUI[2].setEnabled(false);
									Mode = 1;
									break;
								}
								case "출발점 지정 종료" :{
									buttons_BUI[0].setText("출발점 지정");
									buttons_BUI[1].setEnabled(true);
									buttons_BUI[2].setEnabled(true);
									Mode = 0;
									break;
								}
								case "도착점 지정" :{
									buttons_BUI[0].setEnabled(false);
									buttons_BUI[1].setText("도착점 지정 종료");
									buttons_BUI[2].setEnabled(false);
									Mode = 2;
									break;
								}
								case "도착점 지정 종료" :{
									buttons_BUI[0].setEnabled(true);
									buttons_BUI[1].setText("도착점 지정");
									buttons_BUI[2].setEnabled(true);
									Mode = 0;
									break;
								}
								case "장애물 지정" :{
									buttons_BUI[0].setEnabled(false);
									buttons_BUI[1].setEnabled(false);
									buttons_BUI[2].setText("장애물 지정 종료");
									buttons_BUI[1].setText("도착점 지정");
									Mode = 3;
									break;
								}
								case "장애물 지정 종료" :{
									buttons_BUI[0].setEnabled(true);
									buttons_BUI[1].setEnabled(true);
									buttons_BUI[2].setText("장애물 지정");
									Mode = 0;
									break;
								}
								case "편집기 시작" :{
									for(int x=0; x<3; x++){
									buttons_BUI[x].setEnabled(true);}
									buttons_BUI[3].setText("편집기 종료");
									MapEnable();
									Mode = 0;
									break;
								}
								case "편집기 종료" :{
									for(int x=0; x<3; x++){
									buttons_BUI[x].setEnabled(false);}
									buttons_BUI[3].setText("편집기 시작");
									buttons_BUI[0].setText("출발점 지정");
									buttons_BUI[1].setText("도착점 지정");
									buttons_BUI[2].setText("장애물 지정");
									MapDisable();
									Mode = 0;
									break;
								}
								}
							}
						}
					}
				});

				buttons_BUI[i].setBackground(Color.GRAY);
				add(buttons_BUI[i]);
			}
			buttons_BUI[0].setText("출발점 지정");
			buttons_BUI[1].setText("도착점 지정");
			buttons_BUI[2].setText("장애물 지정");
			buttons_BUI[3].setText("편집기 시작");
			for(int x=0; x<3; x++){
				buttons_BUI[x].setEnabled(false);}
			for(int i=0; i<=3; i++){
				buttons_BUI[i].setBorderPainted(false);
				buttons_BUI[i].setFocusPainted(false);
				buttons_BUI[i].setContentAreaFilled(false);
				buttons_BUI[i].setFont(sub);
				buttons_BUI[i].setForeground(Color.ORANGE);
			}
		}
	}

	public class RUI extends status {
		
		public char[][] Map = new char[rowdata][columndata];
			public void search(){
				while(main.end == false){
				main.genMap(Map);
				main.search();
				}
			}
	
		public void MapClean(){
			text = null;
			for (int r = 0; r < buttons.length; r++) {
				for (int j = 0; j < buttons[0].length; j++) {
					buttons[r][j].status = 'b';
					buttons[r][j].block = false;
					buttons[r][j].setBackground(Color.DARK_GRAY);
				}
			}
		}
		
		public void RoadClean(){
			for (int r = 0; r < buttons.length; r++) {
				for (int j = 0; j < buttons[0].length; j++) {
					// 아직 미구현 기능, 지나간 경로를 지우는 기능
					buttons[r][j].status = 'b';
					buttons[r][j].block = false;
					buttons[r][j].setBackground(Color.DARK_GRAY);
				}
			}
		}
		
		Main ASTAR;
		public RUI()  {
			setBackground(Color.DARK_GRAY);
			Font sub = new Font("맑은 고딕",Font.BOLD,12);
			Container M = getContentPane();
			setResizable(false);
			setSize((int) ((M.getSize().height) * (0.2))+6, (int) ((M.getSize().width) * (0.6)-30));
			setLocation((int) ((M.getSize().height) * (0.84)), 10);
			setLayout(new GridLayout(6, 1, 0, 15));
			JButton buttons_BUI[] = new JButton[4];
			for (int i = 0; i < buttons_BUI.length; i++) {
				buttons_BUI[i] = new JButton("매뉴" + (i + 1));
				buttons_BUI[i].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent a) {
						for (int i = 0; i < buttons_BUI.length; i++) {
							if (a.getSource() == buttons_BUI[i]) {
								Mode = 0;
								switch(i){
								case 0:{
									FileReader fr = null;
									BufferedReader br = null;						
									boolean start = false;
									boolean end = false;
									String line = "";
									int pointerx = 0;
									int pointery = 0;
									char pointer;
									try {
										fr = new FileReader(textfile);
										br = new BufferedReader(fr);
										while((line = br.readLine()) != null) {
											for(int y=0; y<line.length(); y++){
					                        pointer = line.charAt(y);
					                        Map[pointerx][pointery] = pointer;
					                        if(pointer == '.' ||pointer == 'E' ||pointer == 'S'||pointer == 'W'){
					                        	if(pointer == '\n'){
					                        		pointerx = 0;
					                        		pointery++;
					                        		continue;
					                        	}
					                        	if(pointer == '.'){
					                        		buttons[pointerx][pointery].setBackground(Color.DARK_GRAY);
					                        		buttons[pointerx][pointery].block=false;
					                        		buttons[pointerx][pointery].status='b';
					                        	}
					                        	else if(pointer == 'S'){
					                        		if(start){
					                        			JOptionPane.showMessageDialog(M, "출발점이 두 개 이상 포착됨","에러",JOptionPane.ERROR_MESSAGE);
					                        			br.close();
					                        			MapClean();
					                        			buttons_BUI[0].setEnabled(false);
					                        			buttons_BUI[1].setEnabled(true);
					                        			break;
					                        		}else{
					                        		buttons[pointerx][pointery].setBackground(Color.GREEN);
					                        		buttons[pointerx][pointery].block=true;
					                        		buttons[pointerx][pointery].status='s';
					                        		start = true;}
					                        	}
					                        	else if(pointer == 'E'){
					                        		if(end){
					                        			JOptionPane.showMessageDialog(M, "도착점이 두 개 이상 포착됨","에러",JOptionPane.ERROR_MESSAGE);
					                        			br.close();
					                        			MapClean();
					                        			buttons_BUI[0].setEnabled(false);
					                        			buttons_BUI[1].setEnabled(true);
					                        			break;
					                        		}else{
					                        		buttons[pointerx][pointery].setBackground(Color.RED);
					                        		buttons[pointerx][pointery].block=true;
					                        		buttons[pointerx][pointery].status='e';
					                        		end = true;}
					                        	}
					                        	else if(pointer == 'W'){
					                        		buttons[pointerx][pointery].setBackground(Color.WHITE);
					                        		buttons[pointerx][pointery].block=true;
					                        		buttons[pointerx][pointery].status='w';
					                        	}
					                        	pointerx++;
					                        }else{
					                        	continue;
					                        }
										} pointery++;
										pointerx = 0;
										}
									} catch (IOException e) {
										e.printStackTrace();
									}
									main.genMap(Map);
									buttons_BUI[0].setEnabled(false);
                        			buttons_BUI[1].setEnabled(true);
									break;
								}
								case 1:{
									boolean start=false;
									boolean end=false;
									for (int r = 0; r < buttons.length; r++) {
										for (int j = 0; j < buttons[0].length; j++) {
											if(buttons[r][j].status=='s'){
												start = true;
											}
											if(buttons[r][j].status=='e'){
												end = true;
											}
										}
									}
									if(start&&end){
										search();
									}else{
										JOptionPane.showMessageDialog(M, "출발점이나 도착점이 설정되어 있지 않습니다,","에러",JOptionPane.ERROR_MESSAGE);
										break;
										// 출발점이나 도착점 둘중 하나라도 없으면 작동 불가능
									}
									break;
								}
								case 2:{
									JOptionPane.showMessageDialog(M, "경상대학교 자료구조 및 알고리즘 TA \n\n 알고리즘 구현 : 이영섭 \n GUI 제작 : 지평강 ",
											"제작자 정보",JOptionPane.INFORMATION_MESSAGE);
									break;
								}
								case 3:{
									int result = 0;
									result = JOptionPane.showConfirmDialog(M, "프로그램을 종료하시겠습니까?","종료",JOptionPane.INFORMATION_MESSAGE);
									if(result == JOptionPane.CANCEL_OPTION||result == JOptionPane.CLOSED_OPTION){
										break;
									}if(result == JOptionPane.OK_OPTION){
									System.exit(1);}
								}
								}
							}
						}
					}
				});
				buttons_BUI[i].setBackground(Color.GRAY);
				add(buttons_BUI[i]);
			}
			buttons_BUI[0].setText("맵 분석");
			buttons_BUI[1].setText("탐색 시작");
			buttons_BUI[2].setText("정보");
			buttons_BUI[3].setText("종료");
			buttons_BUI[1].setEnabled(false);
			for(int i=0; i<=3; i++){
				buttons_BUI[i].setBorderPainted(false);
				buttons_BUI[i].setFocusPainted(false);
				buttons_BUI[i].setContentAreaFilled(false);
				buttons_BUI[i].setFont(sub);
				buttons_BUI[i].setForeground(Color.ORANGE);
			}
		}
	}

	public class showmap extends status {
		Main ASTAR;
		public showmap() {
			
			Container M = getContentPane();
			setLayout(null);
			final int sizeX = (int) ((M.getSize().height) * (0.8));
			final int sizeY = (int) ((M.getSize().width) * (0.8));
			int buttons_sizeX = (sizeX / buttons.length);
			int buttons_sizeY = (sizeY / buttons[0].length);
			setSize(sizeX+5, sizeY+5);
			setBackground(Color.DARK_GRAY);
			Color c = Color.DARK_GRAY;
			int l = 0;
			int a = 10;
			int b = 10;
			for (int i = 0; i < buttons.length; i++) {
				for (int j = 0; j < buttons[0].length; j++) {
					buttons[i][j] = new Button('b');
					buttons[i][j].setSize(buttons_sizeX, buttons_sizeY);
					buttons[i][j].setLocation(a, b);
					buttons[i][j].setEnabled(false);
					buttons[i][j].setBorder(new LineBorder(Color.ORANGE, 1));
					buttons[i][j].addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							for (int i = 0; i < buttons.length; i++) {
								for (int j = 0; j < buttons[0].length; j++) {
									if (e.getSource() == buttons[i][j]) {
										int blocked = 0;
										switch(Mode){
										case 1:{
											for (int a = 0; a < buttons.length; a++) {
												for (int b = 0; b < buttons[0].length; b++) {
													if(buttons[a][b].status == 's'){
														if(a!=i||b!=j){
														System.out.println("출발점 2개이상 불가능");
														blocked = 1;}
														else if(a==i&&b==j){
															System.out.println("출발점 해제됨");
															blocked = 1;
															buttons[i][j].status = 'b';
															buttons[i][j].block = false;
															buttons[i][j].setBackground(Color.DARK_GRAY);
														}else if(buttons[i][j].block){
															System.out.println("중복 설정 불가능. 해제후 재시도");
															break;
														}
														break;
													}
												}
											}if(blocked == 0){
											buttons[i][j].status = 's';
											buttons[i][j].setBackground(Color.green);
											buttons[i][j].block = true;}
											break;
										}
										case 2:{
											for (int a = 0; a < buttons.length; a++) {
												for (int b = 0; b < buttons[0].length; b++) {
													if(buttons[a][b].status == 'e'){
														if(a!=i||b!=j){
															System.out.println("도착점 2개이상 불가능");
															blocked = 1;}
															else if(a==i&&b==j){
																System.out.println("도착점 해제됨");
																blocked = 1;
																buttons[i][j].status = 'b';
																buttons[i][j].setBackground(Color.DARK_GRAY);
																buttons[i][j].block = false;
															}else if(buttons[i][j].block){
																System.out.println("중복 설정 불가능. 해제후 재시도");
																break;
															}
															break;
													}
												}
											}
											if(blocked == 0){
												buttons[i][j].status = 'e';
												buttons[i][j].setBackground(Color.red);
												buttons[i][j].block = true;}
											break;
										}
										case 3:{
											for (int a = 0; a < buttons.length; a++) {
												for (int b = 0; b < buttons[0].length; b++) {
													if(buttons[a][b].status == 'w'){
														 if(a==i&&b==j){
																blocked = 1;
																buttons[i][j].status = 'b';
																buttons[i][j].setBackground(Color.DARK_GRAY);
																break;															
														}
													}
												}
											}
											if(blocked == 0){
												buttons[i][j].status = 'w';
												buttons[i][j].setBackground(Color.white);
												buttons[i][j].block = true;}
											break;
										}
										case 0:{}
										}
									}
								}
							}
						}
					});
					add(buttons[i][j]);
					buttons[i][j].setBackground(null);
					l++;
					a += buttons_sizeX;
					if (j == buttons[0].length - 1) {
						a = 10;
					}
				}
				b += buttons_sizeY;
			}
		}
	}

	@Override
	
}