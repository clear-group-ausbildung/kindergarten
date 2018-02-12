package de.clearit.kindergarten.domain;

import java.math.BigDecimal;
import java.util.List;

import javax.swing.ListModel;

import de.clearit.kindergarten.domain.entity.Purchase;

/**
 * The service for the Purchase resource.
 */
public class PurchaseService extends AbstractResourceService<PurchaseBean, Purchase> {

  private static final BigDecimal ZERO = new BigDecimal(0);
  private static final BigDecimal KINDERGARTEN_PROFIT_RATIO = new BigDecimal(0.2);
  private static final BigDecimal VENDOR_PAYOUT_RATIO = new BigDecimal(0.8);

  private static final PurchaseService INSTANCE = new PurchaseService();

  /**
   * Private constructor to prevent instantiation.
   */
  private PurchaseService() {
    super();
  }

  /**
   * Returns the singleton instance of the service.
   * 
   * @return the singleton instance
   */
  public static PurchaseService getInstance() {
    return INSTANCE;
  }

  // Public API *************************************************************

  /**
   * Returns the list of Vendors as a ListModel.
   * 
   * @return the ListModel
   */
  @SuppressWarnings("unchecked")
  public ListModel<PurchaseBean> getListModel() {
    return (ListModel<PurchaseBean>) getAll();
  }

  @Override
  public PurchaseBean fromEntity(Purchase entity) {
    PurchaseBean bean = new PurchaseBean();
    bean.setId(entity.getInteger(PurchaseBean.PROPERTY_ID));
    bean.setItemNumber(entity.getInteger(toSnakeCase(PurchaseBean.PROPERTY_ITEM_NUMBER)));
    bean.setItemPrice(entity.getBigDecimal(toSnakeCase(PurchaseBean.PROPERTY_ITEM_PRICE)));
    bean.setVendorNumber(entity.getInteger(toSnakeCase(PurchaseBean.PROPERTY_VENDOR_NUMBER)));
    return bean;
  }

  @Override
  public Purchase toEntity(PurchaseBean bean) {
    Purchase entity = Purchase.findById(bean.getId());
    if (entity == null) {
      entity = new Purchase();
    }
    entity.setInteger(toSnakeCase(PurchaseBean.PROPERTY_ITEM_NUMBER), bean.getItemNumber());
    entity.setBigDecimal(toSnakeCase(PurchaseBean.PROPERTY_ITEM_PRICE), bean.getItemPrice());
    entity.setInteger(toSnakeCase(PurchaseBean.PROPERTY_VENDOR_NUMBER), bean.getVendorNumber());
    return entity;
  }

  @Override
  public List<Purchase> getEntities() {
    return Purchase.findAll();
  }

  /**
   * Calculates the profit for the kindergarten for the given list of purchases.
   * 
   * @param listPurchases
   *          The list of purchases to summarise the kindergarten profit for
   * @return The summarised profit for the kindergarten
   */
  public BigDecimal getKindergartenProfitByPurchases(List<PurchaseBean> listPurchases) {
    return listPurchases != null && !listPurchases.isEmpty() ? getItemSumByPurchases(listPurchases).multiply(
        KINDERGARTEN_PROFIT_RATIO) : ZERO;
  }

  /**
   * Calculates the payout amount for the vendors for the given list of purchases.
   * 
   * @param listPurchases
   *          The list of purchases to summarise the vendor payout amount for
   * @return The summarized vendor payout amount
   */
  public BigDecimal getVendorPayoutByPurchases(List<PurchaseBean> listPurchases) {
    return listPurchases != null && !listPurchases.isEmpty() ? getItemSumByPurchases(listPurchases).multiply(
        VENDOR_PAYOUT_RATIO) : ZERO;
  }

  /**
   * Calculates the sum of item prices for the given list of purchases.
   * 
   * @param listPurchases
   *          The list of purchases to summarise the item prices for
   * @return The sum of item prices
   */
  public BigDecimal getItemSumByPurchases(List<PurchaseBean> listPurchases) {
    return listPurchases != null && !listPurchases.isEmpty() ? listPurchases.stream().map(PurchaseBean::getItemPrice)
        .reduce(BigDecimal::add).get() : ZERO;
  }

  /**
   * Returns the number of items in the given list of purchases. (Which
   * effectively is simply the list size, since 1 purchase equals 1 item in a
   * "shopping cart")
   * 
   * @param listPurchases
   *          The list of purchases to count the items
   * @return The count of items
   */
  public Integer getItemCountByPurchases(List<PurchaseBean> listPurchases) {
    return listPurchases != null && listPurchases.size() > 0 ? listPurchases.size() : 0;
  }

}
