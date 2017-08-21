package de.clearit.kindergarten;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ShowFenster extends JPanel {

  private Menu menu;
  private JFrame frame;
  private JPanel Panel;

  public ShowFenster() {
    frame = new JFrame("Kassenprogramm V. 1.0.0");
    
    Container c = frame.getContentPane();
    c.setLayout(new BorderLayout());
    menu = new Menu();
    Panel = new JPanel();
    Panel.add(menu);
    
    c.add(Panel, BorderLayout.WEST);

    
    frame.setSize(800, 600);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }  
}
