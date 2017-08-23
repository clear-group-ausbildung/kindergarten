package de.clearit.kindergarten;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class MenuComponent extends AbstractComponent {

  private ToolbarComponent toolbarComponent;

  // Instance Creation ******************************************************

  public MenuComponent() {
    super();
  }

  @Override
  protected JComponent buildPanel() {
    initComponents();

    JPanel contentPanel = new JPanel();
    contentPanel.setLayout(new GridBagLayout());
    GridBagConstraints gc = new GridBagConstraints();

    gc.weightx = 1;
    gc.weighty = 0.1;
    gc.anchor = GridBagConstraints.FIRST_LINE_START;
    gc.insets = new Insets(5, 5, 5, 5);
    gc.gridx = 0;
    gc.gridy = 0;
    contentPanel.add(toolbarComponent.getPanel(), gc);

    return contentPanel;
  }

  // Initialization *********************************************************

  private void initComponents() {
    toolbarComponent = new ToolbarComponent();
  }

}
