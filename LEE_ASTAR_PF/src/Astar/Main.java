package Astar;

import java.awt.Color;
import java.io.*;
import java.nio.channels.InterruptedByTimeoutException;
import java.util.ArrayList;
import GUI.Button;
import GUI.GUI;

public class Main extends Thread {

	File F;
	BufferedWriter buffWrite;

	Point startp;

	final char START = 'S';
	final char END = 'E';
	final char SPACE = '.';
	final char WALL = 'W';
	final char VISITED = '-';
	final char ON_PATH = '@';
	final char NAW_NODE = '@';
	public int speed = 80; // 기본 탐색스피드
	public boolean end;
	public boolean refresh;
	

	Button buttons[][]; // GUI에서 넘겨받을 데이터 미리 선언

	public char[][] MAP;

	public boolean doing;

	String[] MAP_temp; // TXT 파일 받는 놈

	double h(Point pnt) { // 휴리스틱
		
		int x = (END_PNT.x- pnt.x )*10;
		int y = (END_PNT.y-pnt.y )*10;
		
		
		double heuristic = Math.abs(x) + Math.abs(y);			

//		double heuristic = Math.pow(x, 2) + Math.pow(y, 2);	
		
//		double heuristic = (x*x) + (y*y);	
		
//		heuristic = Math.sqrt(heuristic);
						
		// heuristic = Math.abs(pnt.x - END_PNT.x) + Math.abs(pnt.y -
		// END_PNT.y);
		// heuristic = Math.pow(pnt.x - END_PNT.x, 2) + Math.pow(pnt.y -
		// END_PNT.y, 2);		
		

		
//		System.out.println(heuristic);
		return heuristic;
	}

	Point MAX_PNT = null;
	Point START_PNT = null;
	Point END_PNT = null;
	Point Realtime_NODE = START_PNT;

	public Main(Button[][] inputmap) {
		doing = false;
		this.buttons = inputmap;
	}

	public void run() {
		doing = true;
		
		
		search();
		
		System.out.println(currentThread().getState());
	

	}



	public void setEND(int x, int y) {
		int Ex = x;
		int Ey = y;
		MAP[Ex][Ey] = END;
		END_PNT = new Point(Ex, Ey);
	}

	public void setSTART(int x, int y) {
		int Sx = x;
		int Sy = y;
		MAP[Sx][Sy] = START;
		START_PNT = new Point(Sx, Sy);
	}

	/*
	 * 맵 생성과 출발지 도착지 설정
	 * 
	 */

	public char[][] genMap() {
		int idx = 0;
		MAP = new char[MAP_temp[0].length()][MAP_temp.length];
		// Result = MAP;
		for (String s : MAP_temp) {
			char[] cs = s.replace(" ", "").toCharArray();
			for (int i = 0; i < cs.length; i++) {
				MAP[i][idx] = cs[i];
			}
			idx++;
		}

		MAX_PNT = new Point(MAP.length, MAP[0].length); // 맵크기 인식 시켜 줘야함
		this.MAP = MAP;

		return MAP;

	}

	public char[][] genMap(char[][] MAP) {
		this.MAP = MAP;
		int idx = 0;
		MAX_PNT = new Point(MAP.length, MAP[0].length); // 맵크기 인식 시켜 줘야함
		for (int j = 0; j < MAX_PNT.y; j++) {
			for (int i = 0; i < MAX_PNT.x; i++) {
				if (MAP[i][j] == 'S') {
					setSTART(i, j);
				}
				if (MAP[i][j] == 'E') {
					setEND(i, j);
				}
			}
		}
		return MAP;
	}

	
	public void refreshMap() {
		for (int i = 0; i < MAP.length; i++) {
			for (int j = 0; j < MAP[0].length; j++) {
				char pointer = MAP[i][j];
				switch (pointer) {
				case 'S': {
					buttons[i][j].setForeground(Color.GREEN);
					buttons[i][j].setBackground(Color.GREEN);
					break;
				}
				case 'E': {
					buttons[i][j].setForeground(Color.RED);
					buttons[i][j].setBackground(Color.RED);
					break;
				}
				case '.': {
					buttons[i][j].setForeground(Color.DARK_GRAY);
					buttons[i][j].setBackground(Color.DARK_GRAY);
					break;
				}
				case '-': {
					buttons[i][j].setForeground(Color.GRAY);
					buttons[i][j].setBackground(Color.GRAY);
					break;
				}
				case '@': {
					buttons[i][j].setForeground(Color.CYAN);
					buttons[i][j].setBackground(Color.CYAN);
					break;
				}
				case 'W': {
					buttons[i][j].setForeground(Color.WHITE);
					buttons[i][j].setBackground(Color.WHITE);
					break;
				}
				}
			}
		}
	}
	
	
	void Realtimdisplay(Data D) {
			Data Data =D;

		
		while (Data != null) {
		
			Point pnt = Data.point;
					
			int i = pnt.x;
			int j = pnt.y;			
			buttons[i][j].setBackground(Color.CYAN);			
			Data = Data.parent; 
		}
		
		
	}
	
	
	void Realtimdisplay() {

		int x = Realtime_NODE.x;
		int y = Realtime_NODE.y;
		for (int i = 0; i < MAP.length; i++) {
			for (int j = 0; j < MAP[0].length; j++) {
				char pointer = MAP[i][j];

			

				if (i == Realtime_NODE.x && j == Realtime_NODE.y)
					buttons[i][j].setBackground(Color.CYAN);
				else if (pointer == 'E')
					buttons[i][j].setBackground(Color.RED);
				else if (pointer == 'S')
					buttons[i][j].setBackground(Color.GREEN);
				else if (pointer == '.')
					buttons[i][j].setBackground(Color.DARK_GRAY);
				else if (pointer == '-')
					buttons[i][j].setBackground(Color.GRAY);
				else if (pointer == 'W')
					buttons[i][j].setBackground(Color.WHITE);
			}
		}
	}

	void displaymap() {
		for (int i = 0; i < MAX_PNT.y; i++) {
			for (int j = 0; j < MAX_PNT.x; j++) {
				 System.out.printf("%c ", MAP[i][j]);
			}
			System.out.printf("\n");
		}
		System.out.printf("\n");
		refreshMap();
	}
	
	
	String printD(int i) {

		String D = "";

		switch (i) {
		case 0:
			D = "↓";
			break;

		case 1:
			D = "↙";
			break;

		case 2:
			D = "←";
			break;

		case 3:
			D = "↖";
			break;

		case 4:
			D = "↑";
			break;

		case 5:
			D = "↗";
			break;

		case 6:
			D = "→";
			break;

		case 7:
			D = "↘";
			break;
		default:
			D = "";
		}

		return D;

	}

	public void search() {	
		for (int i = 0; i < MAX_PNT.y; i++)
			for (int j = 0; j < MAX_PNT.x; j++)		
				buttons[i][j].setText("");
				
		
	
		
		// final int[][] directs = { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };
//		final int[][] directs = { { 1, 0 }, { 1, 1 }, { 0, 1 }, { -1, 1 }, { -1, 0 }, { -1, -1 }, { 1, -1 },{ 0, -1 } }; // 8 ???? ?̵?
		final int[][] directs = // 8 9 6 3 2 1 4 7 순으로 검색 (넘키 방향,시계 )
				{ { -1, 0 }, { -1, 1 }, { 0, 1 }, { 1, 1 }, { 1, 0 }, { 1, -1 }, { 0, -1 }, { -1, -1 } };

		final MinHeap heap = new MinHeap();
		heap.add(new Data(START_PNT, 0, 0, null));
		Data lastData = null;

		end = false;
		while (!end && !heap.isEmpty()) {

			final Data data = heap.getAndRemoveMin();
			final Point point = data.point;

			for (int i = 0; i < directs.length; i++) { // loop 2 ???? ?˻?
				final Point nextPoint = new Point(point.x + directs[i][0], point.y + directs[i][1]);
				// System.out.println(nextPoint.x+" , "+nextPoint.y);

				int poz = directs[i][0] + directs[i][1];

				int g;

				if (poz == 2 || poz == 0 || poz == -2)
					g = 14;
				else
					g = 10;

				
//				System.out.println(g);

				if (nextPoint.x >= 0 && nextPoint.x < MAX_PNT.x && nextPoint.y >= 0 && nextPoint.y < MAX_PNT.y) {
					char state = MAP[nextPoint.x][nextPoint.y];
					if (state == END) {
						lastData = data;
						end = true;
						break;
					}
					// if (state == WALL) {break;}
					if (state != SPACE)
						continue;

					if (MAP[nextPoint.x][nextPoint.y] == SPACE) { // ?湮??? ?ν?
						MAP[nextPoint.x][nextPoint.y] = VISITED;
						String D = printD(i);
						buttons[nextPoint.x][nextPoint.y].setText(D);

					}
					final Data heephaveData = heap.find(nextPoint);
					if (heephaveData != null) { // ???? ??Ͽ? ?ִٸ?

						if (heephaveData.g > data.g + g) {
							heephaveData.g = data.g + g;
							heephaveData.parent = data;
						}

					} else {
						double h = h(nextPoint);
						Data newData;

						newData = new Data(nextPoint, data.g + g, h, data);

						heap.add(newData);
					}

					Realtime_NODE = data.point;
					Realtimdisplay(data);
					System.out.println(data.h);

					try {
						Thread.sleep(speed);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block

						

					}
					refreshMap();

					// clearScreen();
				} // if ;
			} // for ;
		} // main loop ;
		Data pathData = lastData;
		while (pathData != null) {
			Point pnt = pathData.point;
			if (MAP[pnt.x][pnt.y] == VISITED) {
				MAP[pnt.x][pnt.y] = ON_PATH;
				Realtime_NODE = pnt;
			}
			pathData = pathData.parent; // ?Ф? ?θ???
			// ?ڿ??? ???? ?ٽ? ???
		}

		displaymap();
		
	

	}

	public static void clearScreen() {
		for (int i = 0; i < 120; i++)
			System.out.println("");
	}
}