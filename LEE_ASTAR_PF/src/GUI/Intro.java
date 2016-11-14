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
		Font main = new Font("���� ����",0,30);
		Font sub = new Font("���� ����",0,15);
		setSize(300, 200);
		setVisible(true);
		Container c = getContentPane();
		c.setBackground(Color.DARK_GRAY);
		c.setLayout(null);
		setTitle("�� �ҷ�����");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JButton Open = new JButton("�� ����");
		JButton Exit = new JButton("����");
		Open.setFont(sub);
		Exit.setFont(sub);
		
		Open.setSize(100,50);
		Open.setLocation(30,60);
		
		Exit.setSize(100,50);
		Exit.setLocation(150,60);
		
		c.add(Open);
		c.add(Exit);
		
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
						c.setVisible(false);
						c.setVisible(false);
						c.setVisible(false);
						
						new GUI(temprow/10,tempcolumn, f);
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
				result = JOptionPane.showConfirmDialog(c, "���α׷��� �����Ͻðڽ��ϱ�?","����",JOptionPane.INFORMATION_MESSAGE);
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