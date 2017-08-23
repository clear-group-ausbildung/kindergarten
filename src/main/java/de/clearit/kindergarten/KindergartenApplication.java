package de.clearit.kindergarten;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class KindergartenApplication {

  public static void main(String[] args) {

    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        JFrame mainFrame = buildFrame();

        mainFrame.setVisible(true);
      }
    });
  }

  private static JFrame buildFrame() {
    JFrame frame = new JFrame("Kassenprogramm V. 1.0.0");
    frame.setSize(800, 600);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
        | UnsupportedLookAndFeelException e) {
      System.err.println(e.getMessage());
    }

    Container contentPane = frame.getContentPane();
    contentPane.setLayout(new BorderLayout());
    contentPane.add(new MenuComponent().getPanel(), BorderLayout.WEST);

    return frame;
  }

}
