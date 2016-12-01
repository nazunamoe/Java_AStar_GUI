package GUI;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Intro extends JFrame {
	int temprow = 0;
	int tempcolumn = 0;
	File f = null;
	Intro() {

		Image img = null;
		try {
			File sourceimage = new File("src/overwatch.png");
			img = ImageIO.read(sourceimage);
		} catch (IOException e) {
			System.out.println("이미지파일이 없습니다.");
		} // 오버워치 로고 불러오는 부분
		setIconImage(img);

		Font sub = new Font("맑은 고딕", Font.BOLD, 15);
		setSize(300, 120);
		setVisible(true);
		Container c = getContentPane();
		c.setBackground(Color.DARK_GRAY);
		c.setLayout(null);
		setTitle("A * Algorithm");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JButton Open = new JButton("맵 열기");
		JButton Exit = new JButton("종료");
		Open.setFont(sub);
		Exit.setFont(sub);

		Open.setSize(100, 50);
		Open.setLocation(30, 40);

		Exit.setSize(100, 50);
		Exit.setLocation(160, 40);

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
		Font f1 = new Font("돋움",Font.ITALIC,17);
		JLabel Title = new JLabel("A * Algorithm Implemented By JAVA");
		Title.setFont(f1);

		c.add(Title);
		Title.setSize(420, 25);
		Title.setLocation(10, 8);
		Title.setForeground(Color.orange);

		setResizable(false);

		Open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileReader fr;
				try {
					JFileChooser jfc = new JFileChooser();
					jfc.setFileFilter(new FileNameExtensionFilter("txt", "txt"));
					if (jfc.showOpenDialog(c) == JFileChooser.APPROVE_OPTION) {
						f = jfc.getSelectedFile();
						fr = new FileReader(f);
						BufferedReader br = new BufferedReader(fr);
						String line = "";
						char pointer;
						try {
							while ((line = br.readLine()) != null) {
								for (int y = 0; y < line.length(); y++) {
									pointer = line.charAt(y);
									if (pointer == '.' || pointer == 'E' || pointer == 'S' || pointer == 'W') {
										temprow++;
									}else{
										
									}
								}
								tempcolumn++;
							}
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					} else {
					}
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});

		Exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = 0;
				result = JOptionPane.showConfirmDialog(c, "Will you exit program?", "Exit",
						JOptionPane.INFORMATION_MESSAGE);
				if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION) {
				}
				if (result == JOptionPane.OK_OPTION) {
					System.exit(1);
				}
			}
		});
	}

	public static void main(String[] args) {
		new Intro();
	}
}
