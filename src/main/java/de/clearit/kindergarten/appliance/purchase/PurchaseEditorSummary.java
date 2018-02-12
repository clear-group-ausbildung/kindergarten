package de.clearit.kindergarten.appliance.purchase;

import java.awt.Color;
import java.awt.Font;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import com.jgoodies.application.Application;
import com.jgoodies.application.ResourceMap;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.uif2.AbstractView;

import de.clearit.kindergarten.domain.PurchaseService;

public class PurchaseEditorSummary extends AbstractView {

  private static final ResourceMap RESOURCES = Application.getResourceMap(PurchaseEditorSummary.class);

  private JLabel itemCountLabel;
  private JLabel itemCountField;
  private JComponent itemSumLabel;
  private JLabel itemSumField;

  private final PurchaseEditorModel model;

  // Instance Creation ******************************************************

  public PurchaseEditorSummary(final PurchaseEditorModel model) {
    this.model = model;
  }

  // Building ***************************************************************

  @Override
  protected JComponent buildPanel() {
    initComponents();
    initFonts();
    initEventHandling();

    final FormLayout layout = new FormLayout("left:pref, $lcgap, right:125dlu", "p, $lg, p");
    final PanelBuilder builder = new PanelBuilder(layout);
    builder.setBackground(Color.WHITE);
    builder.add(itemCountLabel, CC.xy(1, 1));
    builder.add(itemCountField, CC.xy(3, 1));
    builder.add(itemSumLabel, CC.xy(1, 3));
    builder.add(itemSumField, CC.xy(3, 3));
    return builder.getPanel();
  }

  private void initComponents() {
    itemCountLabel = new JLabel(RESOURCES.getString("purchase.itemCount"));
    itemCountField = new JLabel("0");
    itemCountField.setHorizontalAlignment(SwingConstants.RIGHT);
    itemSumLabel = new JLabel(RESOURCES.getString("purchase.itemSum"));
    itemSumField = new JLabel("0,00 \u20ac");
    itemSumField.setHorizontalAlignment(SwingConstants.RIGHT);
  }

  private void initFonts() {
    final Font bold18PtLabelFont = itemCountLabel.getFont().deriveFont(Font.BOLD, 18);
    itemCountLabel.setFont(bold18PtLabelFont);
    itemSumLabel.setFont(bold18PtLabelFont);
    final Font regurlar18PtFieldFont = itemCountLabel.getFont().deriveFont(18.0f);
    itemCountField.setFont(regurlar18PtFieldFont);
    itemSumField.setFont(regurlar18PtFieldFont);
  }

  private void initEventHandling() {
    model.getSelectionInList().addListDataListener(new ListDataListener() {

      private final PurchaseService service = PurchaseService.getInstance();

      @Override
      public void intervalRemoved(ListDataEvent e) {
        refreshLabelText();
      }

      @Override
      public void intervalAdded(ListDataEvent e) {
        refreshLabelText();
      }

      @Override
      public void contentsChanged(ListDataEvent e) {
        refreshLabelText();
      }

      private void refreshLabelText() {
        Integer itemCount = service.getItemCountByPurchases(model.getSelectionInList().getList());
        itemCountField.setText(itemCount.toString());
        BigDecimal itemSum = service.getItemSumByPurchases(model.getSelectionInList().getList());

        String itemSumWithDots = formatNumber(itemSum);
        String itemSumWithComma = itemSumWithDots.replace('.', ',');
        itemSumField.setText(itemSumWithComma + " \u20ac");
      }

      // Format the ItemPrice / Sum with 2 decimal places and return this as String
      private String formatNumber(BigDecimal itemSum) {
        BigDecimal sumToFormat = itemSum;

        NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        nf.setRoundingMode(RoundingMode.HALF_UP);
        return nf.format(sumToFormat);
      }

    });
  }
}
