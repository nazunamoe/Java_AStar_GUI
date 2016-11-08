package GUI;

import javax.swing.JButton;

public class Button extends JButton {
	char status;
	boolean block;
	
	Button left;
	Button right;
	Button up;
	Button down;
	Button leftup;
	Button rightup;
	Button leftdown;
	Button rightdown;
	
	Button(char b){
		status = 'b';
	}
	
	public void setstatus(char a){
		this.status = a;
	}
	
}
