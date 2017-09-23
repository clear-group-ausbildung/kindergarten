package de.clearit.kindergarten.desktop.component;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import com.jgoodies.application.Application;
import com.jgoodies.application.ResourceMap;

abstract class AbstractToolBarButton extends JButton {

  private static final long serialVersionUID = 1L;

  private static final ResourceMap RESOURCES = Application.getResourceMap(AbstractToolBarButton.class);

  // Instance Fields ********************************************************

  private final Image rolloverBackground;
  private final Image pressedBackground;

  // Instance Creation ******************************************************

  AbstractToolBarButton() {
    setFocusPainted(false);
    setFocusable(false);
    setContentAreaFilled(false);
    setRolloverEnabled(true);
    rolloverBackground = RESOURCES.getImage("ToolBarButton.rollover");
    pressedBackground = RESOURCES.getImage("ToolBarButton.pressed");
    setBorder(new CompoundBorder(new ToolBarButtonBorder(rolloverBackground, pressedBackground), getInsetsBorder()));
    setForeground(getCurrentColor());
    getModel().addChangeListener(e -> setForeground(getCurrentColor()));
  }

  // Misc *******************************************************************

  protected Border getInsetsBorder() {
    return new EmptyBorder(2, 4, 2, 4);
  }

  @Override
  protected void paintComponent(Graphics g) {
    int width = getWidth();
    int height = getHeight();
    if (getModel().isPressed() && getModel().isArmed()) {
      g.drawImage(pressedBackground, 4, 4, width - 4, height - 4, 4, 4, 22, 26, null);
    } else if (getModel().isRollover()) {
      g.drawImage(rolloverBackground, 4, 4, width - 4, height - 4, 4, 4, 32, 26, null);
    }
    super.paintComponent(g);
  }

  private Color getCurrentColor() {
    ButtonModel m = getModel();
    String foregroundKey = m.isEnabled() ? "ToolBarButton.foreground" : "ToolBarButton.disabledForeground";
    return RESOURCES.getColor(foregroundKey);
  }

}