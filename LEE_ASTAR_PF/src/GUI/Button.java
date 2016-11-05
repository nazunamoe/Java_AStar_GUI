package GUI;

import javax.swing.JButton;

public class Button extends JButton {
	char status;
	boolean block;
	
	Button(char b){
		status = 'b';
	}
	
	public void setstatus(char a){
		this.status = a;
	}
	
}
