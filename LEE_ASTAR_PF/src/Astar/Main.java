package Astar;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	Point startp;

	final char START = 'S';
	final char END = 'E';
	final char SPACE = '.';
	final char WALL = 'W';
	final char VISITED = '-';
	final char ON_PATH = '@';
	final char NAW_NODE = '@';

	final int speed = 40; // 탐색스피드

	String[] map = { "....................", // 1
			"...WWWW.............", // 2
			"......W.............", // 3
			"......W.............", // 4
			"......W.............", // 5
			"......W.............", // 6
			"......W.............", // 7
			"......W.............", // 8
			"...WWWW.............", // 9
			".................E.."// 10
	};

	String[] map1 = { "....................", // 1
			"...WWWW.............", // 2
			"......W.............", // 3
			"......W.............", // 4
			"......W.............", // 5
			"......W.............", // 6
			"......W.............", // 7
			"......W.............", // 8
			"...WWWW.............", // 9
			"...W.............E.."// 10
	};

	char[][] MAP;

	String[] MAP_temp; // TXT 파일 받는 놈

	double h(Point pnt) {

		double heuristic = Math.abs(pnt.x - END_PNT.x) + Math.abs(pnt.y - END_PNT.y);
		heuristic = Math.abs(pnt.x - END_PNT.x) + Math.abs(pnt.y - END_PNT.y);
		heuristic = Math.pow(pnt.x - END_PNT.x, 2) + Math.pow(pnt.y - END_PNT.y, 2);
		return heuristic;
	}

	Point MAX_PNT = null;

	Point START_PNT = null;

	Point END_PNT = null;

	Point Realtime_NODE = START_PNT;

	public Main() {

		getMap();

	}

	public void getMap() { // txt 파일로 맵 받는거다

		try {

			File F = new File("C:\\astarmap\\MAP.txt"); /// 저장경로 표시 저번에 니가 루트
														/// 보이게 해서 하던거 처럼

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

			TXT.close();

		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	public void setEND(int x, int y) {

		System.out.println("도착지 입력");
		int Ex = x;
		int Ey = y;
		MAP[Ex][Ey] = END;
		END_PNT = new Point(Ex, Ey);
	}

	public void setSTART(int x, int y) {

		System.out.println("출발지 입력: ");
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
		char[][] MAP = new char[MAP_temp[0].length()][MAP_temp.length];

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

	void Realtimdisplay() {
		for (int j = 0; j < MAX_PNT.y; j++) {

			for (int i = 0; i < MAX_PNT.x; i++) {

				if (i == Realtime_NODE.x && j == Realtime_NODE.y) {

					System.out.printf("%c ", NAW_NODE);
				} else {
					System.out.printf("%c ", MAP[i][j]);
				}

			}
			System.out.printf("\n");
		}
		System.out.printf("\n");
	}

	void displaymap() {
		for (int j = 0; j < MAX_PNT.y; j++) {

			for (int i = 0; i < MAX_PNT.x; i++) {

				System.out.printf("%c ", MAP[i][j]);
			}
			System.out.printf("\n");
		}
		System.out.printf("\n");
	}

	void search() {

		// final int[][] directs = { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };

		final int[][] directs = { { 1, 0 }, { 1, 1 }, { 0, 1 }, { -1, 1 }, { -1, 0 }, { -1, -1 }, { 1, -1 },
				{ 0, -1 } }; // 8 방향 이동

		final MinHeap heap = new MinHeap();

		heap.add(new Data(START_PNT, 0, 0, null)); // 시작점 제일 중요 시작점!! 이여!!!

		Data lastData = null;

		boolean end = false;

		while (!end && !heap.isEmpty()) { // 도착 x 힙메모리 비어 있지 않으면 계속 ㄱㄱ // main
											// loop

			final Data data = heap.getAndRemoveMin();

			final Point point = data.point;

			Realtime_NODE = data.point;

			for (int i = 0; i < directs.length; i++) { // loop 2 발향 검색

				final Point nextPoint = new Point(point.x + directs[i][0], point.y + directs[i][1]);

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

					if (MAP[nextPoint.x][nextPoint.y] == SPACE) { // 방문노드 인식
						MAP[nextPoint.x][nextPoint.y] = VISITED;
					}

					final Data heephaveData = heap.find(nextPoint);

					if (heephaveData != null) { // 열린 목록에 있다면

						if (heephaveData.g > data.g + 1) { // 그 G 값이 현제 G 갑 보다
															// 크다면
							heephaveData.g = data.g + 1; // 바꿈
							heephaveData.parent = data;
						}

					}

					else {
						double h = h(nextPoint);

						Data newData = new Data(nextPoint, data.g + 1, h, data);

						heap.add(newData);
					}

					clearScreen();
					Realtimdisplay();

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

			pathData = pathData.parent; // ㅠㅠ 부모노드

			// 뒤에서 부터 다시 출력

		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////

	public static void clearScreen() {
		for (int i = 0; i < 120; i++)
			System.out.println("");
	}
}