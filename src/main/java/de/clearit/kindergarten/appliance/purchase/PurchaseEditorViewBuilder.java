package de.clearit.kindergarten.appliance.purchase;

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
import com.jgoodies.uif2.AbstractView;

public class PurchaseEditorViewBuilder extends AbstractView {

  private JComponent editor;
  private JComponent tableTitleLabel;
  private JComponent listView;
  private JComponent listBar;
  private JComponent analysis;

  // API ********************************************************************

  public void setEditor(final JComponent editor) {
    this.editor = editor;
  }

  public void setTableTitle(final String markedTitle) {
    setTableTitleLabel(JSDLFactory.createHeaderLabel(markedTitle));
  }

  public void setTableTitleLabel(final JComponent tableTitleLabel) {
    this.tableTitleLabel = tableTitleLabel;
  }

  public void setListView(final JComponent listView) {
    this.listView = listView;
  }

  public void setListBar(final ActionMap actionMap, final String... actionNames) {
    if ((actionNames == null) || (actionNames.length == 0)) {
      throw new IllegalArgumentException("You must provide at least one action name.");
    }
    final ButtonBarBuilder2 builder = new ButtonBarBuilder2();
    boolean needsGap = false;
    for (final String actionName : actionNames) {
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

  public void setListBar(final JComponent listBar) {
    this.listBar = listBar;
  }

  public void setAnalysis(final JComponent analysis) {
    this.analysis = analysis;
  }

  @Override
  protected JComponent buildPanel() {
    final FormLayout layout = new FormLayout("default:grow, 9dlu, [100dlu,p]",
        "p, 20dlu, [14dlu,p], $lg, fill:100dlu:grow, p, p");
    final PanelBuilder builder = new PanelBuilder(layout);
    builder.setBackground(Color.WHITE);
    builder.setBorder(Borders.createEmptyBorder("12dlu, 18dlu, 14dlu, 18dlu"));
    builder.add(editor, CC.xyw(1, 1, 3));
    builder.add(tableTitleLabel, CC.xy(1, 3));
    builder.add(new JScrollPane(listView), CC.xyw(1, 5, 3));
    if (listBar != null) {
      builder.add(buildDecoratedListBarAndExtras(), CC.xyw(1, 6, 3));
    }
    if (analysis != null) {
      builder.add(buildDecoratedAnalysis(), CC.xyw(1, 7, 3));
    }

    return builder.getPanel();
  }

  private JComponent buildDecoratedListBarAndExtras() {
    final FormLayout layout = new FormLayout("left:default, 9dlu:grow, right:pref", "$rgap, p");
    final PanelBuilder builder = new PanelBuilder(layout);
    builder.setOpaque(false);
    if (listBar != null) {
      builder.add(listBar, CC.xy(1, 2));
    }
    return builder.getPanel();
  }

  private JComponent buildDecoratedAnalysis() {
    final FormLayout layout = new FormLayout("fill:default:grow", "14, p");
    final PanelBuilder builder = new PanelBuilder(layout);
    builder.setOpaque(false);
    builder.add(analysis, CC.xy(1, 2));
    return builder.getPanel();
  }

}
