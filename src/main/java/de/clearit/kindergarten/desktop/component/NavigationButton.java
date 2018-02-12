package de.clearit.kindergarten.desktop.component;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.Action;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicGraphicsUtils;

import com.jgoodies.application.Application;
import com.jgoodies.application.ResourceMap;

/**
 *
 */
public final class NavigationButton extends JButton {

  private static final long serialVersionUID = 1L;

  private static final ResourceMap RESOURCES = Application.getResourceMap(NavigationButton.class);

  // Instance Creation ******************************************************

  public NavigationButton(Action action) {
    super(action);
    setFocusPainted(false);
    setFocusable(false);
    setContentAreaFilled(false);
    setRolloverEnabled(true);
    setHorizontalAlignment(SwingConstants.LEADING);
    setBorder(new CompoundBorder(new NavigationButtonBorder(), new EmptyBorder(2, 4, 2, 4)));
    setForeground(getCurrentColor());
    getModel().addChangeListener(e -> setForeground(getCurrentColor()));
  }

  @Override
  protected void paintComponent(Graphics g) {
    // ButtonModel model = getModel();
    boolean isActive = model.isRollover() || model.isPressed();

    // Paint background
    if (model.isArmed() && model.isPressed()) {
      paintPressed(g);
    } else if (isActive) {
      paintRollover(g);
    }

    if (isFocusPainted() && hasFocus()) {
      paintFocus(g);
    }
    super.paintComponent(g);
  }

  private void paintPressed(Graphics g) {
    Color startColor = RESOURCES.getColor("NavigationButton.pressed.gradient.start");
    Color endColor = RESOURCES.getColor("NavigationButton.pressed.gradient.stop");
    paintGradient(g, startColor, endColor);
  }

  private void paintRollover(Graphics g) {
    Color startColor = RESOURCES.getColor("NavigationButton.rollover.gradient.start");
    Color endColor = RESOURCES.getColor("NavigationButton.rollover.gradient.stop");
    paintGradient(g, startColor, endColor);
  }

  private void paintGradient(Graphics g, Color startColor, Color endColor) {
    int width = getWidth();
    int height = getHeight();
    Insets insets = NavigationButtonBorder.INSETS;
    int x, y, w, h;
    Graphics2D g2 = (Graphics2D) g;
    Rectangle clip = g2.getClipBounds();
    if (clip != null) {
      x = clip.x;
      y = clip.y;
      w = clip.width;
      h = clip.height;
      x = Math.max(x, insets.left);
      y = Math.max(y, insets.top);
      int right = width - insets.right;
      if (x + w > right) {
        w = right - x;
      }
      int bottom = height - insets.bottom;
      if (y + h > bottom) {
        h = bottom - y;
      }
    } else {
      x = insets.left;
      y = insets.top;
      w = width - (insets.left + insets.right);
      h = height - (insets.top + insets.bottom);
    }
    paintGradient(g2, x, y, w, h, height, startColor, endColor);
  }

  private void paintGradient(Graphics2D g2, int x, int y, int w, int h, int height, Color startColor, Color endColor) {
    g2.setPaint(new GradientPaint(0, 0, startColor, 0, height, endColor, true));
    g2.fillRect(x, y, w, h);
  }

  private void paintFocus(Graphics g) {
    int width = getWidth();
    int height = getHeight();
    Insets insets = NavigationButtonBorder.INSETS;
    int x = insets.left;
    int y = insets.top;
    int w = width - (insets.left + insets.right);
    int h = height - (insets.top + insets.bottom);
    Color focusColor = UIManager.getColor("Button.focus");
    g.setColor(focusColor);
    BasicGraphicsUtils.drawDashedRect(g, x, y, w, h);
  }

  private Color getCurrentColor() {
    ButtonModel m = getModel();
    String foregroundKey = m.isEnabled() ? "NavigationButton.foreground" : "NavigationButton.disabledForeground";
    return RESOURCES.getColor(foregroundKey);
  }

}