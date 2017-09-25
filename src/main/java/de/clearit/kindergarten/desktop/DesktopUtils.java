package de.clearit.kindergarten.desktop;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;

/**
 * A utility class for the desktop.
 */
public final class DesktopUtils {

  public static JComponent buildValidationFeedbackPanel(String message1, String message2, String message3,
      String message4) {
    FormLayout layout = new FormLayout("left:p:grow", "3*([8dlu,p], 3dlu), [8dlu,p]");
    PanelBuilder builder = new PanelBuilder(layout, new ValidationFeedbackPanel());
    builder.setBorder(new CompoundBorder(new LineBorder(Color.GRAY), Borders.createEmptyBorder(
        "4dlu, 4dlu, 4dlu, 4dlu")));

    if (message1 != null) {
      builder.addLabel(message1, CC.xy(1, 1));
    }
    if (message2 != null) {
      builder.addLabel(message2, CC.xy(1, 3));
    }
    if (message3 != null) {
      builder.addLabel(message3, CC.xy(1, 5));
    }
    if (message4 != null) {
      builder.addLabel(message4, CC.xy(1, 7));
    }

    return builder.getPanel();
  }

  private static final class ValidationFeedbackPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    @Override
    protected void paintComponent(Graphics g) {
      Graphics2D g2 = (Graphics2D) g;
      int width = getWidth();
      int height = getHeight();
      int x, y, w, h;
      Rectangle clip = g2.getClipBounds();
      if (clip != null) {
        x = clip.x;
        y = clip.y;
        w = clip.width;
        h = clip.height;
      } else {
        x = 0;
        y = 0;
        w = width;
        h = height;
      }
      paintGradient(g2, x, y, w, h, width, height);
    }

    private void paintGradient(Graphics2D g2, int x, int y, int w, int h, int width, int height) {
      Color sideColor = new Color(255, 255, 210);
      Color midColor = new Color(255, 255, 230);
      int mid = width / 2;
      g2.setPaint(new GradientPaint(0, height, sideColor, mid, height, midColor));
      g2.fillRect(x, y, mid, h);
      g2.setPaint(new GradientPaint(mid, height, midColor, width, height, sideColor));
      g2.fillRect(x + mid, y, width, h);
    }
  }

}
