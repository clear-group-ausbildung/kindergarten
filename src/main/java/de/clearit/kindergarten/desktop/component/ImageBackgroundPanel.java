package de.clearit.kindergarten.desktop.component;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.JPanel;

/**
 * Paints a scaled image as panel background.
 *
 */
public final class ImageBackgroundPanel extends JPanel {

  private static final long serialVersionUID = 1L;

  private final Image image;
  private final int sx1;
  private final int sy1;
  private final int sx2;
  private final int sy2;

  // Instance Creation ******************************************************

  public ImageBackgroundPanel(Image image, int sx1, int sy1, int sx2, int sy2) {
    this.image = image;
    this.sx1 = sx1;
    this.sy1 = sy1;
    this.sx2 = sx2;
    this.sy2 = sy2;
  }

  public static ImageBackgroundPanel createFrom(Image image) {
    int width = image.getWidth(null);
    int height = image.getHeight(null);
    return new ImageBackgroundPanel(image, 0, 0, width, height);
  }

  // Painting ***************************************************************

  @Override
  protected void paintComponent(Graphics g) {
    Insets insets = getInsets();
    int x1 = insets.left;
    int y1 = insets.top;
    int x2 = getWidth() - insets.right;
    int y2 = getHeight() - insets.bottom;
    g.drawImage(image, x1, y1, x2, y2, sx1, sy1, sx2, sy2, null);
  }

}