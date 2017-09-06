package de.clearit.kindergarten.desktop;

import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;

import com.jgoodies.application.Application;
import com.jgoodies.application.ResourceMap;
import com.jgoodies.desktop.spec.MenuSpec;
import com.jgoodies.desktop.spec.NavigationBarSpec;
import com.jgoodies.desktop.spec.NavigationBarSpec.NavigationBarBuilder;
import com.jgoodies.desktop.spec.SplitButtonSpec;
import com.jgoodies.desktop.spec.ToolBarSpec;
import com.jgoodies.desktop.spec.ToolBarSpec.ToolBarBuilder;
import com.jgoodies.desktop.view.DefaultMenuBuilder;
import com.jgoodies.forms.builder.ButtonBarBuilder2;
import com.jgoodies.forms.builder.ButtonStackBuilder;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.Sizes;
import com.jgoodies.uif2.component.GradientSeparator;
import com.jgoodies.uif2.util.ComponentUtils;

import de.clearit.kindergarten.desktop.component.ImageBackgroundPanel;
import de.clearit.kindergarten.desktop.component.NavigationButton;
import de.clearit.kindergarten.desktop.component.ToolBarButton;
import de.clearit.kindergarten.desktop.component.ToolBarMenuButton;

/**
 * The view factory for the desktop.
 */
public final class DesktopViewFactory {

  private static final ResourceMap RESOURCES = Application.getResourceMap(DesktopViewFactory.class);

  private DesktopViewFactory() {
    // Override default constructor; prevents instantiation.
  }

  // Panels *****************************************************************

  static JPanel createStatusPanel() {
    Color sideColor = RESOURCES.getColor("status.sideColor");
    Color midColor = RESOURCES.getColor("status.midColor");
    return new HorizontalGradientPanel(sideColor, midColor);
  }

  // Components *************************************************************

  static JComponent createActionSeparator() {
    GridLayout layout = new GridLayout(2, 1);
    JPanel panel = new JPanel(layout);
    panel.setOpaque(false);
    panel.add(GradientSeparator.createHorizontalGradient(RESOURCES.getColor(
        "navigation.actionSeparator.top.midColor")));
    panel.add(GradientSeparator.createHorizontalGradient(RESOURCES.getColor(
        "navigation.actionSeparator.bottom.midColor")));
    return panel;
  }

  private static JComponent createHeaderLabel(String text) {
    JLabel label = new JLabel(text);
    label.setForeground(RESOURCES.getColor("actionGroup.foreground"));
    label.setBorder(new EmptyBorder(2, 6, 2, 0));
    return label;
  }

  static JComponent createToolBar(ToolBarSpec toolBarSpec) {
    return toolBarSpec.buildToolBar(new ContextToolBarBuilder());
  }

  static JComponent createSideBar(NavigationBarSpec sideBarSpec) {
    return sideBarSpec.buildNavigationBar(new SideBarBuilder());
  }

  static JButton createToolBarButton(Action action) {
    JButton button = new ToolBarButton(action);
    Object accelerator = action.getValue(Action.ACCELERATOR_KEY);
    if (accelerator != null) {
      ComponentUtils.registerKeyboardAction(button, action, JComponent.WHEN_IN_FOCUSED_WINDOW);
    }
    return button;
  }

  static JButton createToolBarMenuButton(JMenu menu) {
    JButton button = new ToolBarMenuButton(menu.getAction(), menu.getPopupMenu());
    Object accelerator = menu.getAction().getValue(Action.ACCELERATOR_KEY);
    if (accelerator != null) {
      ComponentUtils.registerKeyboardAction(button, menu.getAction(), JComponent.WHEN_IN_FOCUSED_WINDOW);
    }
    return button;
  }

  private static JButton createActionButton(Action action) {
    JButton button = new NavigationButton(action);
    Object accelerator = action.getValue(Action.ACCELERATOR_KEY);
    if (accelerator != null) {
      ComponentUtils.registerKeyboardAction(button, action, JComponent.WHEN_IN_FOCUSED_WINDOW);
    }
    return button;
  }

  // Builders ***************************************************************

  private static final class ContextToolBarBuilder implements ToolBarBuilder {

    private final ButtonBarBuilder2 builder;

    private boolean hasComponent = false;

    ContextToolBarBuilder() {
      builder = new ButtonBarBuilder2();
      builder.getLayout().setRowSpec(1, RowSpec.decode("fill:pref:grow"));
      builder.setOpaque(false);
    }

    @Override
    public void add(Action action) {
      addDefaultGap();
      builder.addFixed(createToolBarButton(action));
      hasComponent = true;
    }

    @Override
    public void add(MenuSpec menuSpec) {
      JMenu menu = menuSpec.buildMenu(new DefaultMenuBuilder());
      addDefaultGap();
      builder.addFixed(createToolBarMenuButton(menu));
      hasComponent = true;
    }

    @Override
    public void add(SplitButtonSpec splitButtonSpec) {
      throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void addGap() {
      addDefaultGap();
      builder.addStrut(Sizes.DLUX11);
    }

    @Override
    public void addGlue() {
      builder.addGlue();
    }

    @Override
    public JComponent getToolBar() {
      return builder.getPanel();
    }

    private void addDefaultGap() {
      if (hasComponent) {
        builder.addStrut(Sizes.DLUX4);
      }
    }

  }

  private static final class SideBarBuilder implements NavigationBarBuilder {

    private final ButtonStackBuilder builder;

    SideBarBuilder() {
      builder = new ButtonStackBuilder();
      builder.setOpaque(false);
    }

    @Override
    public void add(Action action) {
      builder.addFixed(createActionButton(action));
    }

    @Override
    public void add(String title) {
      builder.addFixed(createHeaderLabel(title + ':'));
    }

    @Override
    public void addRelatedGap() {
      builder.addStrut(Sizes.DLUY5);
    }

    @Override
    public void addUnrelatedGap() {
      builder.addStrut(Sizes.DLUY14);
    }

    @Override
    public void addSeparator() {
      builder.addFixed(createActionSeparator());
    }

    @Override
    public void addGlue() {
      builder.addGlue();
    }

    @Override
    public JComponent getNavigationBar() {
      return builder.getPanel();
    }

  }

  // Stylish Panels *********************************************************

  /*
   * private static final class VerticalGradientPanel extends JPanel {
   * 
   * private final Color startColor; private final Color midColor;
   * 
   * public VerticalGradientPanel(Color startColor, Color midColor) {
   * this.startColor = startColor; this.midColor = midColor; }
   * 
   * protected void paintComponent(Graphics g) { Graphics2D g2 = (Graphics2D) g;
   * int width = getWidth(); int height = getHeight(); int x, y, w, h; Rectangle
   * clip = g2.getClipBounds(); if (clip != null) { x = clip.x; y = clip.y; w =
   * clip.width; h = clip.height; } else { x = 0; y = 0; w = width; h = height; }
   * paintGradient(g2, x, y, w, h, width, height); }
   * 
   * // private void paintGradient(Graphics2D g2, // int x, int y, int w, int h,
   * int width, int height) { // Color stopColor = new Color( 30, 22, 63); //
   * Color startColor = new Color(34, 78, 153); // g2.setPaint(new
   * GradientPaint(0, 0, startColor, 0, height-20, stopColor)); // g2.fillRect(x,
   * y, w, h); // }
   * 
   * private void paintGradient(Graphics2D g2, int x, int y, int w, int h, int
   * width, int height) { int mid = height / 2; g2.setPaint(new GradientPaint(0,
   * 0, startColor, 0, mid, midColor, true)); g2.fillRect(x, y, w, mid);
   * g2.setPaint(new GradientPaint(0, mid+1, midColor, 0, height-20, startColor,
   * true)); g2.fillRect(x, mid, w, height); }
   * 
   * }
   */

  private static final class HorizontalGradientPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private final Color sideColor;
    private final Color midColor;

    public HorizontalGradientPanel(Color sideColor, Color midColor) {
      this.sideColor = sideColor;
      this.midColor = midColor;
    }

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
      int end = x + w;
      int mid = width / 2;
      if (x < mid) {
        g2.setPaint(new GradientPaint(0, height, sideColor, mid, height, midColor, true));
        g2.fillRect(x, y, Math.min(mid - x, w), h);
      }
      if (end >= mid) {
        int start = Math.max(mid, x);
        int myW = end - start;
        g2.setPaint(new GradientPaint(mid, height, midColor, width, height, sideColor, true));
        g2.fillRect(start, y, myW, h);
      }
    }
  }

}
