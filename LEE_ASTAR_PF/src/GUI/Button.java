package GUI;

import javax.swing.JButton;

public class Button extends JButton {
	char status;
	Button(char b){
		status = b;
	}
	
	public void setstatus(char a){
		this.status = a;
	}
	
}
