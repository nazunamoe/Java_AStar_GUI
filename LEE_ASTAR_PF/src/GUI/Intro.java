package GUI;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Intro extends JFrame {
	Intro(){
		Font main = new Font("맑은 고딕",0,30);
		Font sub = new Font("맑은 고딕",0,15);
		setSize(300, 100);
		setVisible(true);
		Container c = getContentPane();
		c.setBackground(Color.DARK_GRAY);
		c.setLayout(null);
		setTitle("맵 불러오기");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JButton Open = new JButton("맵 열기");
		JButton Exit = new JButton("종료");
		Open.setFont(sub);
		Exit.setFont(sub);
		
		Open.setSize(100,50);
		Open.setLocation(30,10);
		
		Exit.setSize(100,50);
		Exit.setLocation(160,10);
		
		Exit.setBorderPainted(false);
		Exit.setFocusPainted(false);
		Exit.setContentAreaFilled(false);
		Exit.setForeground(Color.ORANGE);
		
		Open.setBorderPainted(false);
		Open.setFocusPainted(false);
		Open.setContentAreaFilled(false);
		Open.setForeground(Color.ORANGE);
		
		c.add(Open);
		c.add(Exit);
		
		setResizable(false);
		
		Open.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				File f = null;
				FileReader fr;
				try {
					JFileChooser jfc = new JFileChooser();
					jfc.setFileFilter(new FileNameExtensionFilter("txt", "txt"));
					if(jfc.showOpenDialog(c) == JFileChooser.APPROVE_OPTION){
						f=jfc.getSelectedFile();
						fr = new FileReader(f);
						BufferedReader br = new BufferedReader(fr);
						String line = "";
						char pointer;
						int temprow = 0;
						int tempcolumn = 0;
						try {
							while((line = br.readLine()) != null) {
								for(int y=0; y<line.length(); y++){
									 pointer = line.charAt(y);
									 if(pointer == '.' ||pointer == 'E' ||pointer == 'S'||pointer == 'W'){
									temprow++;}
								}tempcolumn++;
							}
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						dispose();
						c.setVisible(false);
						new GUI(temprow/tempcolumn,tempcolumn, f);
						}
					else{
						
					}
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		Exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int result = 0;
				result = JOptionPane.showConfirmDialog(c, "프로그램을 종료하시겠습니까?","종료",JOptionPane.INFORMATION_MESSAGE);
				if(result == JOptionPane.CANCEL_OPTION||result == JOptionPane.CLOSED_OPTION){
				}if(result == JOptionPane.OK_OPTION){
				System.exit(1);}
			}
		});
	}
	
	public static void main(String[] args){
		new Intro();
	}
}
