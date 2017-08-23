package de.clearit.kindergarten.appliance;

import java.awt.Color;

import javax.swing.ActionMap;
import javax.swing.JComponent;
import javax.swing.JScrollPane;

import com.jgoodies.forms.builder.ButtonBarBuilder2;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.jsdl.core.component.factory.JSDLFactory;

/**
 * Builds Home views from a set of given mandatory and optional components:
 * title, search, list (table), list buttons, preview.
 *
 */
public class HomeViewBuilder {

  private JComponent titleLabel;
  private JComponent searchView;

  private JComponent listView;
  private JComponent listBar;
  private JComponent listExtras;
  private JComponent preview;

  /**
   * Holds the panel that has been lazily built in {@code #buildPanel}.
   */
  private JComponent panel;

  // API ********************************************************************

  public void setTitle(String markedTitle) {
    setTitleLabel(JSDLFactory.createHeaderLabel(markedTitle));
  }

  public void setTitleLabel(JComponent titleLabel) {
    this.titleLabel = titleLabel;
  }

  public void setSearchView(JComponent searchView) {
    this.searchView = searchView;
  }

  public void setListView(JComponent listView) {
    this.listView = listView;
  }

  public void setListBar(ActionMap actionMap, String... actionNames) {
    if ((actionNames == null) || (actionNames.length == 0)) {
      throw new IllegalArgumentException("You must provide at least one action name.");
    }
    ButtonBarBuilder2 builder = new ButtonBarBuilder2();
    boolean needsGap = false;
    for (String actionName : actionNames) {
      if (actionName.startsWith("---")) {
        builder.addUnrelatedGap();
        needsGap = false;
        continue;
      }
      if (needsGap) {
        builder.addRelatedGap();
      }
      builder.addButton(actionMap.get(actionName));
      needsGap = true;
    }
    setListBar(builder.getPanel());
  }

  public void setListBar(JComponent listBar) {
    this.listBar = listBar;
  }

  public void setListExtras(JComponent listExtras) {
    this.listExtras = listExtras;
  }

  public void setPreview(JComponent preview) {
    this.preview = preview;
  }

  public JComponent getPanel() {
    if (panel == null) {
      panel = buildPanel();
    }
    return panel;
  }

  // Implementation *********************************************************

  private JComponent buildPanel() {
    FormLayout layout = new FormLayout("default:grow, 9dlu, [100dlu,p]", "[14dlu,p], $lg, fill:100dlu:grow, p, p");
    PanelBuilder builder = new PanelBuilder(layout);
    builder.setBackground(Color.WHITE);
    builder.setBorder(Borders.createEmptyBorder("12dlu, 18dlu, 14dlu, 18dlu"));
    builder.add(titleLabel, CC.xy(1, 1));
    builder.add(new JScrollPane(listView), CC.xyw(1, 3, 3));
    if (searchView != null) {
      builder.add(searchView, CC.xy(3, 1));
    }
    if ((listBar != null) || (listExtras != null)) {
      builder.add(buildDecoratedListBarAndExtras(), CC.xyw(1, 4, 3));
    }
    if (preview != null) {
      builder.add(buildDecoratedPreview(), CC.xyw(1, 5, 3));
    }

    return builder.getPanel();
  }

  private JComponent buildDecoratedListBarAndExtras() {
    FormLayout layout = new FormLayout("left:default, 9dlu:grow, right:pref", "$rgap, p");
    PanelBuilder builder = new PanelBuilder(layout);
    builder.setOpaque(false);
    if (listBar != null) {
      builder.add(listBar, CC.xy(1, 2));
    }
    if (listExtras != null) {
      builder.add(listExtras, CC.xy(3, 2));
    }
    return builder.getPanel();
  }

  private JComponent buildDecoratedPreview() {
    FormLayout layout = new FormLayout("fill:default:grow", "14, p");
    PanelBuilder builder = new PanelBuilder(layout);
    builder.setOpaque(false);
    builder.add(preview, CC.xy(1, 2));
    return builder.getPanel();
  }

}
