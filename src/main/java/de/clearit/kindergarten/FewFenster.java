package de.clearit.kindergarten;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class FewFenster extends JPanel {

  private TextPanel textarea;
  private FewPanel fewPanel;

  public FewFenster() {
    JFrame frame = new JFrame("Kassenprogramm V. 1.0.0");

    Container c = frame.getContentPane();
        c.setLayout(new BorderLayout());
        textarea = new TextPanel();
        fewPanel = new FewPanel();

        c.add(fewPanel, BorderLayout.WEST);
    c.add(textarea, BorderLayout.CENTER);

    frame.setSize(800, 600);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }
  
}
