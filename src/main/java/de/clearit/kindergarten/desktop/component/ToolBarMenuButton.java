package de.clearit.kindergarten.desktop.component;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JPopupMenu;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import com.jgoodies.application.Application;
import com.jgoodies.application.ResourceMap;

/**
 *
 */
public final class ToolBarMenuButton extends AbstractToolBarButton {

  private static final long serialVersionUID = 1L;

  private static final ResourceMap RESOURCES = Application.getResourceMap(ToolBarMenuButton.class);

  private static ActionListener showMenuListener;

  private final Icon downIcon;
  private final int downIconTextGap;

  private JPopupMenu popupMenu;

  // Instance Creation ******************************************************

  public ToolBarMenuButton(Action action, JPopupMenu popupMenu) {
    setAction(action);
    downIcon = RESOURCES.getIcon("ToolBarMenuButton.downIcon");
    downIconTextGap = RESOURCES.getInt("ToolBarMenuButton.downIconTextGap");
    setMenu(popupMenu);
    if (showMenuListener == null) {
      showMenuListener = new ShowMenuActionListener();
    }
    addActionListener(showMenuListener);
  }

  // Accessors **************************************************************

  private JPopupMenu getMenu() {
    return popupMenu;
  }

  private void setMenu(JPopupMenu popupMenu) {
    if (popupMenu == null) {
      throw new IllegalArgumentException("The popup menu must not be null.");
    }
    this.popupMenu = popupMenu;
  }

  // Overriding Superclass Behavior *****************************************

  @Override
  protected Border getInsetsBorder() {
    return new EmptyBorder(2, 0, 2, 8);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Insets insets = getInsets();
    int width = getWidth();
    int height = getHeight();
    int x = width - (insets.right - 3) - downIcon.getIconWidth();
    int y = insets.top + 1 + (height - insets.top - insets.bottom - downIcon.getIconHeight()) / 2;
    downIcon.paintIcon(this, g, x, y);
  }

  @Override
  public Dimension getPreferredSize() {
    Dimension prefSize = super.getPreferredSize();
    int downIconWidth = downIcon.getIconWidth();
    return new Dimension(prefSize.width + downIconWidth + 2 * downIconTextGap, prefSize.height);
  }

  private static final class ShowMenuActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      ToolBarMenuButton b = (ToolBarMenuButton) e.getSource();
      b.getMenu().show(b, 0, b.getHeight() + 1);
    }
  }

}
