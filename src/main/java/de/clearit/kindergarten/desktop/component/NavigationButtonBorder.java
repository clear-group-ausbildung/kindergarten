package de.clearit.kindergarten.desktop.component;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.border.AbstractBorder;

public final class NavigationButtonBorder extends AbstractBorder {

  private static final long serialVersionUID = 1L;

  static final Insets INSETS = new Insets(2, 2, 2, 2);

  @Override
  public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
    AbstractButton b = (AbstractButton) c;
    ButtonModel m = b.getModel();
    Color lightest;
    Color veryLight;
    Color light;
    Color outerLine;
    Color innerLine = Color.WHITE;
    if (m.isArmed()) {
      outerLine = new Color(165, 165, 165);
      light = new Color(178, 178, 178);
      veryLight = new Color(198, 198, 198);
      lightest = new Color(204, 204, 204);
    } else if (m.isRollover() || m.isPressed()) {
      outerLine = new Color(198, 198, 198);
      light = new Color(210, 210, 210);
      veryLight = new Color(234, 234, 234);
      lightest = new Color(245, 245, 245);
    } else {
      return;
    }
    g.translate(x, y);
    g.setColor(veryLight); // Outer very light corner
    // Upper left
    g.fillRect(1, 0, 1, 1);
    g.fillRect(0, 1, 1, 1);
    // Upper right
    g.fillRect(w - 2, 0, 1, 1);
    g.fillRect(w - 1, 1, 1, 1);
    // Lower left
    g.fillRect(1, h - 1, 1, 1);
    g.fillRect(0, h - 2, 1, 1);
    // Lower right
    g.fillRect(w - 2, h - 1, 1, 1);
    g.fillRect(w - 1, h - 2, 1, 1);
    g.setColor(light);
    // Upper left
    g.fillRect(2, 0, 1, 1);
    g.fillRect(1, 1, 1, 1);
    g.fillRect(0, 2, 1, 1);
    // Upper right
    g.fillRect(w - 3, 0, 1, 1);
    g.fillRect(w - 2, 1, 1, 1);
    g.fillRect(w - 1, 2, 1, 1);
    // Lower left
    g.fillRect(2, h - 1, 1, 1);
    g.fillRect(1, h - 2, 1, 1);
    g.fillRect(0, h - 3, 1, 1);
    // Lower right
    g.fillRect(w - 3, h - 1, 1, 1);
    g.fillRect(w - 2, h - 2, 1, 1);
    g.fillRect(w - 1, h - 3, 1, 1);
    g.setColor(outerLine);
    g.fillRect(3, 0, w - 6, 1);
    g.fillRect(3, h - 1, w - 6, 1);
    g.fillRect(0, 3, 1, h - 6);
    g.fillRect(w - 1, 3, 1, h - 6);
    g.setColor(lightest); // Inner corner
    // Upper left
    g.fillRect(2, 1, 1, 1);
    g.fillRect(1, 2, 1, 1);
    // Upper right
    g.fillRect(w - 3, 1, 1, 1);
    g.fillRect(w - 2, 2, 1, 1);
    // Lower left
    g.fillRect(2, h - 2, 1, 1);
    g.fillRect(1, h - 3, 1, 1);
    // Lower right
    g.fillRect(w - 3, h - 2, 1, 1);
    g.fillRect(w - 2, h - 3, 1, 1);
    g.setColor(innerLine);
    g.fillRect(3, 1, w - 6, 1);
    g.fillRect(3, h - 2, w - 6, 1);
    g.fillRect(1, 3, 1, h - 6);
    g.fillRect(w - 2, 3, 1, h - 6);
    g.translate(-x, -y);
  }

  @Override
  public Insets getBorderInsets(Component c) {
    return INSETS;
  }

  @Override
  public Insets getBorderInsets(Component c, Insets newInsets) {
    newInsets.top = INSETS.top;
    newInsets.left = INSETS.left;
    newInsets.bottom = INSETS.bottom;
    newInsets.right = INSETS.right;
    return newInsets;
  }
}