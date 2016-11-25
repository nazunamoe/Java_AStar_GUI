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
	public int speed = 80; // 탐색스피드
	public boolean end;
	public boolean refresh;

	Button buttons[][]; // GUI에서 넘겨받을 데이터 미리 선언

	public char[][] MAP;

	public boolean doing;

	String[] MAP_temp; // TXT 파일 받는 놈

	double h(Point pnt) { // 휴리스틱 
		double heuristic = Math.abs(pnt.x - END_PNT.x) + Math.abs(pnt.y - END_PNT.y);
	//	heuristic = Math.abs(pnt.x - END_PNT.x) + Math.abs(pnt.y - END_PNT.y);
//		heuristic = Math.pow(pnt.x - END_PNT.x, 2) + Math.pow(pnt.y - END_PNT.y, 2);
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
			try {
				while(true){
					sleep(1);
				}
			} catch (InterruptedException e) {
				return;
			}
		
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

	public void getMap() {
		try {
			F = new File("C:\\astarmap\\MAP.txt");
			FileReader TXT = new FileReader(F);
			int c;
			String temp = "";
			ArrayList<String> S = new ArrayList<>();
			while ((c = TXT.read()) != -1) {
				char C = (char) c;
				if (C == '\r') {
					S.add(temp);
					temp = "";
				} else if (C != '\r' && C != '\n') {
					temp += C;
				}
			}
			MAP_temp = S.toArray(new String[S.size()]);
			for (String s : MAP_temp) {
				System.out.println(s);
			}
			// TXT.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	void Realtimdisplay() {

		int x = Realtime_NODE.x;
		int y = Realtime_NODE.y;
		for (int i = 0; i < MAP.length; i++) {
			for (int j = 0; j < MAP[0].length; j++) {
				char pointer = MAP[i][j];

				boolean empti = (i == Realtime_NODE.x && j == Realtime_NODE.y);

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
		for (int j = 0; j < MAX_PNT.y; j++) {
			for (int i = 0; i < MAX_PNT.x; i++) {
//				System.out.printf("%c ", MAP[i][j]);
			}
			System.out.printf("\n");
		}
		System.out.printf("\n");
		refreshMap();
	}

	public void search() {
//		 final int[][] directs = { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };
	final int[][] directs = { { 1, 0 }, { 1, 1 }, { 0, 1 }, { -1, 1 }, { -1, 0 }, { -1, -1 }, { 1, -1 },		{ 0, -1 } }; // 8 ???? ?̵?
		final MinHeap heap = new MinHeap();
		heap.add(new Data(START_PNT, 0, 0, null));
		Data lastData = null;
		end = false;
		while (!end && !heap.isEmpty()) {

			final Data data = heap.getAndRemoveMin();
			final Point point = data.point;
			Realtime_NODE = data.point;
			for (int i = 0; i < directs.length; i++) { // loop 2 ???? ?˻?
				final Point nextPoint = new Point(point.x + directs[i][0], point.y + directs[i][1]);
				// System.out.println(nextPoint.x+" , "+nextPoint.y);

				int poz = directs[i][0] + directs[i][1];

				if (nextPoint.x >= 0 && nextPoint.x < MAX_PNT.x && nextPoint.y >= 0 && nextPoint.y < MAX_PNT.y) {
					char state = MAP[nextPoint.x][nextPoint.y];
					if (state == END) {
						lastData = data;
						end = true;
						break;
					}
					// if (state == WALL) {break;}
					if (state != SPACE) {
						continue;
					}
					if (MAP[nextPoint.x][nextPoint.y] == SPACE) { // ?湮??? ?ν?
						MAP[nextPoint.x][nextPoint.y] = VISITED;
					}
					final Data heephaveData = heap.find(nextPoint);
					if (heephaveData != null) { // ???? ??Ͽ? ?ִٸ?

						if (poz == 2 || poz == 0 || poz == -2)

						{
							if (heephaveData.g > data.g + 1) {
								heephaveData.g = data.g + 14;
								heephaveData.parent = data;
							}
						} else

						{
							if (heephaveData.g > data.g + 1) {
								heephaveData.g = data.g + 10;
								heephaveData.parent = data;
							}

						}

					} else {
						double h = h(nextPoint);
						Data newData;
						if (poz == 2 || poz == 0 || poz == -2)
							newData = new Data(nextPoint, data.g + 14, h, data);
						else
							newData = new Data(nextPoint, data.g + 10, h, data);

						heap.add(newData);

					}
						try {
							Thread.sleep(speed);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					Realtimdisplay();
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