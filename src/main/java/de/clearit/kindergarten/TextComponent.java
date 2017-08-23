package de.clearit.kindergarten;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TextComponent extends AbstractComponent {

  private JTextArea textArea;

  // Instance Creation ******************************************************

  public TextComponent() {
    super();
    initComponents();
  }

  // Initialization *********************************************************

  private void initComponents() {
    textArea = new JTextArea();
  }

  public void appendText(String text) {
    textArea.append(text);
  }

  @Override
  protected JComponent buildPanel() {
    JPanel contentPanel = new JPanel();
    contentPanel.setLayout(new BorderLayout());
    contentPanel.add(new JScrollPane(textArea), BorderLayout.CENTER);
    return contentPanel;
  }

}
