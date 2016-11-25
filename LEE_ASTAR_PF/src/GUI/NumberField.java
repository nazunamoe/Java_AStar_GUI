package GUI;

import java.awt.event.*;

import javax.swing.*;

public class NumberField extends JTextField implements KeyListener {
 private static final long serialVersionUID = 1;
 
 public NumberField() {
  addKeyListener(this);
 }

 public void keyPressed(KeyEvent e) {
 }

 public void keyReleased(KeyEvent e) {
 }

 public void keyTyped(KeyEvent e) {
  // Get the current character you typed...
  char c = e.getKeyChar();
  
  if (!Character.isDigit(c)) {
   e.consume();
   return;
  }
 }
}