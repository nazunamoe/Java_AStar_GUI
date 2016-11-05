package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import Astar.*;

public class GUI extends JFrame {

	static Main ASTAR;

	GUI(Main ASTAR) {
		int Mode;
		Font main = new Font("맑은 고딕",0,30);
		Font sub = new Font("맑은 고딕",0,15);
		
		setTitle("A-STAR Algorithm");
		setSize(500, 500);
		setVisible(true);
		Container c = getContentPane();

		c.setBackground(Color.DARK_GRAY);
		setLayout(null);
		add(new BGR());
		add(new showmap());

		add(new RUI());
		
		JLabel Title = new JLabel("A Star Algorithm");
		Title.setLocation(10,430);
		Title.setSize(300,40);
		Title.setFont(main);
		Title.setForeground(Color.WHITE);
		c.add(Title);
		

		// setResizable(false);
	}
	
	public static class status extends Panel{
		static int Mode;
	}

	public class BGR extends status {
		Main ASTAR;
		public BGR() {
			Font sub = new Font("맑은 고딕",Font.BOLD,12);
			setBackground(null);		
			Container M = getContentPane();			
			setSize((int) ((M.getSize().height) * (1.0)), (int) ((M.getSize().width) * (0.08))-15);
			setLocation(10,(int) ((M.getSize().height) * (0.8))+30);
			setLayout(new GridLayout(1, 3, 30, 0));
			JButton buttons_BUI[] = new JButton[3];
			for (int i = 0; i < buttons_BUI.length; i++) {
				buttons_BUI[i] = new JButton("매뉴" + (i + 1));
				buttons_BUI[i].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent a) {
						for (int i = 0; i < buttons_BUI.length; i++) {
							if (a.getSource() == buttons_BUI[i]) {
								switch(buttons_BUI[i].getText()){
								case "출발점 지정" :{
									System.out.println("출발점 지정");
									Mode = 1;
									break;
								}
								case "도착점 지정" :{
									System.out.println("도착점 지정");
									Mode = 2;
									break;
								}
								case "출발! 드림팀" :{
									System.out.println("출발! 드림팀");
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
			buttons_BUI[2].setText("출발! 드림팀");
			for(int i=0; i<=2; i++){
				buttons_BUI[i].setFont(sub);
				buttons_BUI[i].setForeground(Color.WHITE);
			}
		}
	}

	public class RUI extends status {
		Main ASTAR;
		public RUI() {
			setBackground(Color.DARK_GRAY);
			Font sub = new Font("맑은 고딕",Font.BOLD,12);
			Container M = getContentPane();
			setResizable(false);
			setSize((int) ((M.getSize().height) * (0.2))+6, (int) ((M.getSize().width) * (0.9)));
			setLocation((int) ((M.getSize().height) * (0.84)), 10);
			setLayout(new GridLayout(6, 1, 0, 20));
			JButton buttons_BUI[] = new JButton[5];
			for (int i = 0; i < buttons_BUI.length; i++) {
				buttons_BUI[i] = new JButton("매뉴" + (i + 1));
				buttons_BUI[i].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent a) {
						for (int i = 0; i < buttons_BUI.length; i++) {
							if (a.getSource() == buttons_BUI[i]) {
								Mode = 0;
							}
						}
					}
				});
				buttons_BUI[i].setBackground(Color.GRAY);
				add(buttons_BUI[i]);
			}
			buttons_BUI[0].setText("맵 가져오기");
			buttons_BUI[1].setText("맵 에디터");
			buttons_BUI[2].setText("맵 저장");
			buttons_BUI[3].setText("경로 초기화");
			buttons_BUI[4].setText("경로저장");
			for(int i=0; i<=4; i++){
				buttons_BUI[i].setFont(sub);
				buttons_BUI[i].setForeground(Color.WHITE);
			}
		}
	}

	public class showmap extends status {
		Main ASTAR;
		public void name() {
		}
		/**
		 * Create the panel.
		 */
		public showmap() {
			Container M = getContentPane();
			// 맵 크기 받아서 그리드 레이 아웃으로 추가 해야함
			Button buttons[][] = new Button[20][20];
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
					buttons[i][j].addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							for (int i = 0; i < buttons.length; i++) {
								for (int j = 0; j < buttons[0].length; j++) {
									if (e.getSource() == buttons[i][j]) {
										System.out.println(Mode);
										int blocked = 0;
										switch(Mode){
										case 1:{
											for (int a = 0; a < buttons.length; a++) {
												for (int b = 0; b < buttons[0].length; b++) {
													if(buttons[a][b].status == 's'){
														if(a!=i||b!=j){
														System.out.println("출발점 2개이상 불가능");
														blocked = 1;
														buttons[i][j].status = 'b';
														buttons[i][j].setBackground(Color.DARK_GRAY);}
														else if(a==i&&b==j){
															System.out.println("출발점 해제됨");
															blocked = 1;
															buttons[i][j].status = 'b';
															buttons[i][j].setBackground(Color.DARK_GRAY);
														}
														break;
													}
												}
											}if(blocked == 0){
											buttons[i][j].status = 's';
											buttons[i][j].setBackground(Color.green);}
											break;
										}
										case 2:{
											for (int a = 0; a < buttons.length; a++) {
												for (int b = 0; b < buttons[0].length; b++) {
													if(buttons[a][b].status == 'e'){
														if(a!=i||b!=j){
															System.out.println("도착점 2개이상 불가능");
															blocked = 1;
															buttons[i][j].status = 'b';
															buttons[i][j].setBackground(Color.DARK_GRAY);}
															else if(a==i&&b==j){
																System.out.println("도착점 해제됨");
																blocked = 1;
																buttons[i][j].status = 'b';
																buttons[i][j].setBackground(Color.DARK_GRAY);
															}
															break;
													}
												}
											}
											if(blocked == 0){
												buttons[i][j].status = 'e';
												buttons[i][j].setBackground(Color.red);}
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
}