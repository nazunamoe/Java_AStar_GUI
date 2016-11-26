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
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import Astar.Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class GUI extends JFrame {

	static int rowdata;
	static int columndata;

	static File textfile;

	public GUI(int row, int column, File text) { // GUI 메인 클래스

		Font main = new Font("맑은 고딕", 0, 30);
		Font sub = new Font("맑은 고딕", 0, 15);

		Image img = null;
		try {
			File sourceimage = new File("src/overwatch.png");
			img = ImageIO.read(sourceimage);
		} catch (IOException e) {
			System.out.println("이미지파일이 없습니다.");
		} // 오버워치 로고 불러오는 부분
		setIconImage(img);

		JLabel logo = new JLabel(new ImageIcon(img));
		textfile = text;
		rowdata = row;
		columndata = column; // 인트로에서 받아온 파일과 사이즈를 GUI객체로 전송

		setTitle("A * Algorithm implemented by Java");
		setSize(500, 500);
		setVisible(true);
		Container c = getContentPane();

		c.setBackground(Color.DARK_GRAY);
		setLayout(null);
		add(new showmap());
		add(new BGR());
		add(new RUI());

		logo.setSize(100, 100);
		logo.setLocation(385, 280);
		add(logo);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static class status extends Panel { // 모든 패널이 상속하는 클래스, 공유해야 할 데이터를
												// 보존

		static Button buttons[][] = new Button[rowdata][columndata];
		static char[][] Map = new char[rowdata][columndata];

		static JButton buttons_BUI[] = new JButton[4];
		static JButton buttons_BUI2[] = new JButton[4];
		static NumberField speed = new NumberField();
		static int Mode;

		static File text;
		Main main = new Main(buttons);

		public status() {
		}

		public void convertMap() { // 버튼으로 된 맵을 char로 변환하여 Map을 갱신
			char[][] temp = new char[rowdata][columndata];
			for (int x = 0; x < buttons.length; x++) {
				for (int y = 0; y < buttons[0].length; y++) {
					switch (buttons[x][y].status) {
					case '.': {
						temp[x][y] = '.';
						break;
					}
					case 'W': {
						temp[x][y] = 'W';
						break;
					}
					case 'S': {
						temp[x][y] = 'S';
						break;
					}
					case 'E': {
						temp[x][y] = 'E';
						break;
					}
					default: {

					}
					}
				}
			}
			Map = temp;
		}

		public void removeRoad() {
			for (int x = 0; x < buttons.length; x++) {
				for (int y = 0; y < buttons[0].length; y++) {
					switch (Map[x][y]) {
					case '-': {
						System.out.println("1");
						Map[x][y] = '.';
						showmap.buttons[x][y].setBackground(Color.darkGray);
						break;
					}
					case '@': {
						System.out.println("2");
						Map[x][y] = '.';
						showmap.buttons[x][y].setBackground(Color.darkGray);
						break;
					}
					}
				}
			}
		}

		public void MapDisable() { // 버튼으로 된 맵을 사용하지 못하도록 제한
			for (int r = 0; r < buttons.length; r++) {
				for (int j = 0; j < buttons[0].length; j++) {
					buttons[r][j].setEnabled(false);
				}
			}
		}

		public void MapEnable() { // 버튼으로 된 맵을 사용할 수 있도록 설정
			for (int r = 0; r < buttons.length; r++) {
				for (int j = 0; j < buttons[0].length; j++) {
					buttons[r][j].setEnabled(true);
				}
			}
		}
	}

	public class BGR extends status { // 출발점, 도착점, 장애물 설정 가능한 편집기 기능을 정의
		public BGR() {
			Font sub = new Font("맑은 고딕", Font.BOLD, 12);
			setBackground(null);
			Container M = getContentPane();
			setSize((int) ((M.getSize().height) * (1.0)), (int) ((M.getSize().width) * (0.08)) + 15);
			setLocation(15, (int) ((M.getSize().height) * (0.8)) + 35);
			setLayout(new GridLayout(2, 2, 15, 5));
			for (int i = 0; i < buttons_BUI.length; i++) {
				buttons_BUI[i] = new JButton("매뉴" + (i + 1));
				buttons_BUI[i].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent a) {
						for (int i = 0; i < buttons_BUI.length; i++) {
							if (a.getSource() == buttons_BUI[i]) { // Mode변수를 이용하여 편집기의 모드를 조정하거나 빠져나갈 수 있음
								switch (buttons_BUI[i].getText()) {
								case "출발점 지정": {
									MapEnable();
									buttons_BUI[0].setText("출발점 지정 종료");
									buttons_BUI[1].setEnabled(false);
									buttons_BUI[2].setEnabled(false);
									buttons_BUI[3].setEnabled(false);
									Mode = 1;
									break;
								}
								case "출발점 지정 종료": {
									MapDisable();
									buttons_BUI[0].setText("출발점 지정");
									buttons_BUI[1].setEnabled(true);
									buttons_BUI[2].setEnabled(true);
									buttons_BUI[3].setEnabled(true);
									Mode = 0;
									break;
								}
								case "도착점 지정": {
									MapEnable();
									buttons_BUI[1].setText("도착점 지정 종료");
									buttons_BUI[0].setEnabled(false);
									buttons_BUI[2].setEnabled(false);
									buttons_BUI[3].setEnabled(false);
									Mode = 2;
									break;
								}
								case "도착점 지정 종료": {
									MapDisable();
									buttons_BUI[1].setText("도착점 지정");
									buttons_BUI[0].setEnabled(true);
									buttons_BUI[2].setEnabled(true);
									buttons_BUI[3].setEnabled(true);
									Mode = 0;
									break;
								}
								case "장애물 지정": {
									MapEnable();
									buttons_BUI[2].setText("장애물 지정 종료");
									buttons_BUI[0].setEnabled(false);
									buttons_BUI[1].setEnabled(false);
									buttons_BUI[3].setEnabled(false);
									Mode = 3;
									break;
								}
								case "장애물 지정 종료": {
									MapDisable();
									buttons_BUI[2].setText("장애물 지정");
									buttons_BUI[0].setEnabled(true);
									buttons_BUI[1].setEnabled(true);
									buttons_BUI[3].setEnabled(true);
									Mode = 0;
									break;
								}
								case "편집기 시작": {
									buttons_BUI2[1].setEnabled(false);
									for (int x = 0; x < 3; x++) {
										buttons_BUI[x].setEnabled(true);
									}
									buttons_BUI[3].setText("편집기 종료");
									Mode = 0;
									break;
								}
								case "편집기 종료": {
									convertMap();
									buttons_BUI2[1].setEnabled(true);
									for (int x = 0; x < 3; x++) {
										buttons_BUI[x].setEnabled(false);
									}
									buttons_BUI[3].setText("편집기 시작");
									buttons_BUI[0].setText("출발점 지정");
									buttons_BUI[1].setText("도착점 지정");
									buttons_BUI[2].setText("장애물 지정");
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
			for (int x = 0; x <= 3; x++) {
				buttons_BUI[x].setEnabled(false);
			}
			for (int i = 0; i <= 3; i++) {
				buttons_BUI[i].setBorderPainted(false);
				buttons_BUI[i].setFocusPainted(false);
				buttons_BUI[i].setContentAreaFilled(false);
				buttons_BUI[i].setFont(sub);
				buttons_BUI[i].setForeground(Color.ORANGE);
			}
		}
	}

	public class RUI extends status { // 맵 분석, 탐색 시작, 정보, 종료가 있는 컨트롤러

		public void MapClean() {
			text = null;
			for (int r = 0; r < buttons.length; r++) {
				for (int j = 0; j < buttons[0].length; j++) {
					buttons[r][j].status = 'b';
					buttons[r][j].block = false;
					buttons[r][j].setBackground(Color.DARK_GRAY);
				}
			}
		}

		public RUI() {
			setBackground(Color.DARK_GRAY);
			Font sub = new Font("맑은 고딕", Font.BOLD, 12);
			Container M = getContentPane();
			setResizable(false);
			JLabel speedtitle = new JLabel("속도");
			JButton speedok = new JButton("속도 적용");
			speedtitle.setFont(sub);
			speedok.setFont(sub);
			
			M.add(speed);
			M.add(speedtitle);
			M.add(speedok);
			speed.setSize(60, 20);
			speedtitle.setSize(60, 20);
			speedok.setSize(100, 20);
			speedtitle.setForeground(Color.orange);
			speedok.setForeground(Color.ORANGE);
			speedok.setBackground(Color.DARK_GRAY);
			speedok.setBorderPainted(false);
			speedok.setFocusPainted(false);
			speedok.setContentAreaFilled(false);
			speed.setLocation((int) ((M.getSize().height) * (0.84)) + 35, 210);
			speedtitle.setLocation((int) ((M.getSize().height) * (0.84)), 210);
			speedok.setLocation((int) ((M.getSize().height) * (0.84)) - 5, 235);
			speed.setText("80");
			speed.setForeground(Color.WHITE);
			main.speed = 80;
			speed.setBackground(Color.GRAY);
			speed.setDocument((new JTextFieldLimit(4)));
			speedok.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						main.speed = Integer.parseInt(speed.getText());
					} catch (NumberFormatException e) {

					}
				}
			});

			setSize((int) ((M.getSize().height) * (0.2)) + 6, (int) ((M.getSize().width) * (0.6) - 30));
			setLocation((int) ((M.getSize().height) * (0.84)), 10);
			setLayout(new GridLayout(6, 1, 0, 15));
			for (int i = 0; i < buttons_BUI2.length; i++) {
				buttons_BUI2[i] = new JButton("매뉴" + (i + 1));
				buttons_BUI2[i].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent a) {
						for (int i = 0; i < buttons_BUI2.length; i++) {
							if (a.getSource() == buttons_BUI2[i]) {
								Mode = 0;
								switch (i) {
								case 0: {
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
										while ((line = br.readLine()) != null) {
											for (int y = 0; y < line.length(); y++) {
												pointer = line.charAt(y);
												if (pointer == '.' || pointer == 'E' || pointer == 'S'
														|| pointer == 'W') {
													if (pointer == '.') {
														buttons[pointery][pointerx].setBackground(Color.DARK_GRAY);
														buttons[pointery][pointerx].block = false;
														buttons[pointery][pointerx].status = '.';
														Map[pointery][pointerx] = '.';
													} else if (pointer == 'S') {
														if (start) {
															JOptionPane.showMessageDialog(M, "출발점이 두 개 이상 포착됨", "에러",
																	JOptionPane.ERROR_MESSAGE);
															br.close();
															MapClean();
															buttons_BUI2[0].setEnabled(false);
															buttons_BUI2[1].setEnabled(true);
															break;
														} else {
															buttons[pointery][pointerx].setBackground(Color.GREEN);
															buttons[pointery][pointerx].block = true;
															buttons[pointery][pointerx].status = 'S';
															Map[pointery][pointerx] = 'S';
															start = true;
														}
													} else if (pointer == 'E') {
														if (end) {
															JOptionPane.showMessageDialog(M, "도착점이 두 개 이상 포착됨", "에러",
																	JOptionPane.ERROR_MESSAGE);
															br.close();
															MapClean();
															buttons_BUI2[0].setEnabled(false);
															buttons_BUI2[1].setEnabled(true);
															break;
														} else {
															buttons[pointery][pointerx].setBackground(Color.RED);
															buttons[pointery][pointerx].block = true;
															buttons[pointery][pointerx].status = 'E';
															Map[pointery][pointerx] = 'E';
															end = true;
														}
													} else if (pointer == 'W') {
														buttons[pointery][pointerx].setBackground(Color.WHITE);
														buttons[pointery][pointerx].block = true;
														buttons[pointery][pointerx].status = 'W';
														Map[pointery][pointerx] = 'W';
													}
													pointerx++;
												} else {
													continue;
												}
											}
											pointery++;
											pointerx = 0;
										}
									} catch (IOException e) {
										e.printStackTrace();
									}
									buttons_BUI[3].setEnabled(true);
									buttons_BUI2[0].setEnabled(false);
									buttons_BUI2[1].setEnabled(true);
									break;
								}
								case 1: {
									boolean start = false;
									boolean end = false;
									for (int r = 0; r < buttons.length; r++) {
										for (int j = 0; j < buttons[0].length; j++) {
											if (buttons[r][j].status == 'S') {
												start = true;
											}
											if (buttons[r][j].status == 'E') {
												end = true;
											}
										}
									}
									if (start && end) {
										main.genMap(Map);
		                                 try {                                 
		                                    main.start();         // 일단 스레드를 실행해 보고
		                                     } catch (Exception e) {                                                                                
		                                       main = null;                                                   
		                                       main = new Main(buttons);
		                                       main.genMap(Map);                              
		                                       System.out.println(speed.getText());	                                    
		                                       if(!speed.getText().equals(null))
		                                    	 try{
		                   						main.speed = Integer.parseInt(speed.getText());}
		                                       catch(NumberFormatException e2){
		                                    	   main.speed = 80; // 속도칸이 비워져있으면 기본값 설정
		                                       }
		                                       main.start();
		                                     }		                        
		                           } else {
										JOptionPane.showMessageDialog(M, "출발점이나 도착점이 설정되어 있지 않습니다,", "에러",
												JOptionPane.ERROR_MESSAGE);
										break;
										// 출발점이나 도착점 둘중 하나라도 없으면 작동 불가능
									}
									break;
								}
								case 2: {
									JOptionPane.showMessageDialog(M,
											"경상대학교 자료구조 및 알고리즘 TA \n\n 알고리즘 구현 : 이영섭 \n GUI 제작 : 지평강 \n 여담이지만 다들 정말 고생했다... ", "제작자 정보",
											JOptionPane.INFORMATION_MESSAGE);
									break;
								}
								case 3: {
									int result = 0;
									result = JOptionPane.showConfirmDialog(M, "프로그램을 종료하시겠습니까?", "종료",
											JOptionPane.INFORMATION_MESSAGE);
									if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION) {
										break;
									}
									if (result == JOptionPane.OK_OPTION) {
										System.exit(1);
									}
								}
								}
							}
						}
					}
				});
				buttons_BUI2[i].setBackground(Color.GRAY);
				add(buttons_BUI2[i]);
			}
			buttons_BUI2[0].setText("맵 분석");
			buttons_BUI2[1].setText("탐색 시작");
			buttons_BUI2[2].setText("정보");
			buttons_BUI2[3].setText("종료");
			buttons_BUI2[1].setEnabled(false);
			for (int i = 0; i <= 3; i++) {
				buttons_BUI2[i].setBorderPainted(false);
				buttons_BUI2[i].setFocusPainted(false);
				buttons_BUI2[i].setContentAreaFilled(false);
				buttons_BUI2[i].setFont(sub);
				buttons_BUI2[i].setForeground(Color.ORANGE);
			}
		}
	}

	public class showmap extends status { // 버튼 맵 객체, 편집기 기능을 구현
		public showmap() {

			Container M = getContentPane();
			setLayout(null);
			final int sizeX = (int) ((M.getSize().height) * (0.8));
			final int sizeY = (int) ((M.getSize().width) * (0.8));
			int buttons_sizeX = (sizeX / buttons.length);
			int buttons_sizeY = (sizeY / buttons[0].length);
			setSize(sizeX + 5, sizeY + 5);
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
										int blocked = 0; // 맵을 변경하였을 시 제한 사항이
															// 발생할 경우 1로 변경되며
															// if문을 통해 맵 변경이 제한됨
										switch (Mode) {
										case 1: {
											for (int a = 0; a < buttons.length; a++) {
												for (int b = 0; b < buttons[0].length; b++) {
													if (buttons[a][b].status == 'S') {

														/*
														 * 출발점이나 도착점이 2개가 될때 둘의
														 * 좌표가 같으면 기존의점을 지우는 것으로
														 * 인식
														 */
														if (a != i || b != j) {
															System.out.println("출발점 2개이상 불가능");
															blocked = 1;
														} else if (a == i && b == j) {
															System.out.println("출발점 해제됨");
															blocked = 1;
															buttons[i][j].status = '.';
															buttons[i][j].block = false;
															buttons[i][j].setBackground(Color.DARK_GRAY);
														} else if (buttons[i][j].block) {
															System.out.println("중복 설정 불가능. 해제후 재시도");
															break;
														}
														break;
													}
												}
											}
											if (blocked == 0) {
												buttons[i][j].status = 'S';
												buttons[i][j].setBackground(Color.green);
												buttons[i][j].block = true;
											}
											break;
										}
										case 2: {
											for (int a = 0; a < buttons.length; a++) {
												for (int b = 0; b < buttons[0].length; b++) {
													if (buttons[a][b].status == 'E') {
														if (a != i || b != j) {
															System.out.println("도착점 2개이상 불가능");
															blocked = 1;
														} else if (a == i && b == j) {
															System.out.println("도착점 해제됨");
															blocked = 1;
															buttons[i][j].status = '.';
															buttons[i][j].setBackground(Color.DARK_GRAY);
															buttons[i][j].block = false;
														} else if (buttons[i][j].block) {
															System.out.println("중복 설정 불가능. 해제후 재시도");
															break;
														}
														break;
													}
												}
											}
											if (blocked == 0) {
												buttons[i][j].status = 'E';
												buttons[i][j].setBackground(Color.red);
												buttons[i][j].block = true;
											}
											break;
										}
										case 3: {
											for (int a = 0; a < buttons.length; a++) {
												for (int b = 0; b < buttons[0].length; b++) {
													if (buttons[a][b].status == 'W') {
														if (a == i && b == j) {
															blocked = 1;
															buttons[i][j].status = '.';
															buttons[i][j].setBackground(Color.DARK_GRAY);
															break;
														}
													}
												}
											}
											if (blocked == 0) {
												buttons[i][j].status = 'W';
												buttons[i][j].setBackground(Color.white);
												buttons[i][j].block = true;
											}
											break;
										}
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
}