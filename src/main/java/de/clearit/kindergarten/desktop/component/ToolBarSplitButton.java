package de.clearit.kindergarten.desktop.component;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JPopupMenu;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import com.jgoodies.application.Application;
import com.jgoodies.application.ResourceMap;

public final class ToolBarSplitButton extends AbstractToolBarButton {

  private static final long serialVersionUID = 1L;

  private static final ResourceMap RESOURCES = Application.getResourceMap(ToolBarSplitButton.class);

  private static ActionListener showMenuListener;

  private final Icon downIcon;
  private final int downIconTextGap;

  private JPopupMenu popupMenu;

  // Instance Creation ******************************************************

  public ToolBarSplitButton(Action action, JPopupMenu popupMenu) {
    if (action == null) {
      throw new IllegalArgumentException("The action must not be null.");
    }
    setAction(new ActivatableAction(action));
    downIcon = RESOURCES.getIcon("ToolBarSplitButton.downIcon");
    downIconTextGap = RESOURCES.getInt("ToolBarSplitButton.downIconTextGap");
    setMenu(popupMenu);
    if (showMenuListener == null) {
      showMenuListener = new ShowMenuActionListener();
    }
    addActionListener(showMenuListener);
    addMouseMotionListener(new ActionActivator());
  }

  // Accessors **************************************************************

  public JPopupMenu getMenu() {
    return popupMenu;
  }

  public void setMenu(JPopupMenu popupMenu) {
    if (popupMenu == null) {
      throw new IllegalArgumentException("The popup menu must not be null.");
    }
    this.popupMenu = popupMenu;
  }

  // Overriding Superclass Behavior *****************************************

  /**
   * In addition to the superclass behavior, this methods turns off the rollover
   * state if the component is not showing.
   */
  @Override
  protected void fireActionPerformed(ActionEvent event) {
    super.fireActionPerformed(event);
    if (!isShowing()) {
      getModel().setRollover(false);
    }
  }

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
    int x = width - (insets.right - 5) - downIcon.getIconWidth();
    int y = insets.top + 1 + (height - insets.top - insets.bottom - downIcon.getIconHeight()) / 2;
    // System.out.println("width=" + width + "; insets.right=" + insets.right + ";
    // iconWidth=" + downIcon.getIconWidth() + "; x=" + x);
    downIcon.paintIcon(this, g, x, y);
  }

  @Override
  protected void paintBorder(Graphics g) {
    super.paintBorder(g);
    if ((getModel().isPressed() && getModel().isArmed()) || getModel().isRollover()) {
      paintSeparator(g);
    }
  }

  protected void paintSeparator(Graphics g) {
    Insets insets = getInsets();
    int width = getWidth();
    int height = getHeight();
    int x = width - (insets.right - 4) - downIcon.getIconWidth();
    int sx = x - 5;
    g.setColor(RESOURCES.getColor("ToolBarSplitButton.separatorShadow"));
    g.fillRect(sx, 2, 1, height - 4);
    g.setColor(RESOURCES.getColor("ToolBarSplitButton.separatorHighlight"));
    g.fillRect(sx + 1, 2, 1, height - 4);
  }

  @Override
  public Dimension getPreferredSize() {
    Dimension prefSize = super.getPreferredSize();
    int downIconWidth = downIcon.getIconWidth();
    return new Dimension(prefSize.width + downIconWidth + 2 * downIconTextGap, prefSize.height);
  }

  // Helper Code ************************************************************

  private ActivatableAction getActivatableAction() {
    return (ActivatableAction) getAction();
  }

  private final class ActionActivator implements MouseMotionListener {
    @Override
    public void mouseMoved(MouseEvent e) {
      boolean isInButtonArea = e.getX() < getWidth() - 20;
      getActivatableAction().setActive(isInButtonArea);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
      // Do nothing
    }
  }

  private static final class ShowMenuActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      ToolBarSplitButton b = (ToolBarSplitButton) e.getSource();
      b.getMenu().show(b, 0, b.getHeight() + 1);
    }
  }

  private static final class ActivatableAction extends AbstractAction {

    private static final long serialVersionUID = 1L;

    private final Action delegate;

    private boolean active;

    ActivatableAction(Action delegate) {
      this.delegate = delegate;
      delegate.addPropertyChangeListener(new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
          firePropertyChange(evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
        }
      });
    }

    void setActive(boolean b) {
      active = b;
    }

    // Action Interface ----------------------------------------------

    @Override
    public Object getValue(String key) {
      return delegate.getValue(key);
    }

    @Override
    public void putValue(String key, Object value) {
      delegate.putValue(key, value);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      if (active) {
        delegate.actionPerformed(e);
      }
    }

  }

}