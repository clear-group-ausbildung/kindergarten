package de.clearit.kindergarten.domain;

import java.util.List;
import java.util.stream.Collectors;

import javax.swing.ListModel;

import com.google.common.base.Preconditions;

import de.clearit.kindergarten.domain.entity.Purchase;

/**
 * The service for the Purchase resource.
 */
public class PurchaseService extends AbstractResourceService<PurchaseBean, Purchase> {

  private static final Double KINDERGARTEN_PROFIT_RATIO = 0.2d;
  private static final Double VENDOR_PAYOUT_RATIO = 0.8d;

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
  public ListModel<PurchaseBean> getListModel() {
    return (ListModel<PurchaseBean>) getAll();
  }

  @Override
  public PurchaseBean fromEntity(Purchase entity) {
    PurchaseBean bean = new PurchaseBean();
    bean.setId(entity.getInteger(PurchaseBean.PROPERTY_ID));
    bean.setItemNumber(entity.getInteger(toSnakeCase(PurchaseBean.PROPERTY_ITEM_NUMBER)));
    bean.setItemPrice(entity.getDouble(toSnakeCase(PurchaseBean.PROPERTY_ITEM_PRICE)));
    bean.setVendorId(entity.getInteger(toSnakeCase(PurchaseBean.PROPERTY_VENDOR_ID)));
    return bean;
  }

  @Override
  public Purchase toEntity(PurchaseBean bean) {
    Purchase entity = Purchase.findById(bean.getId());
    if (entity == null) {
      entity = new Purchase();
    }
    entity.setInteger(toSnakeCase(PurchaseBean.PROPERTY_ITEM_NUMBER), bean.getItemNumber());
    entity.setDouble(toSnakeCase(PurchaseBean.PROPERTY_ITEM_PRICE), bean.getItemPrice());
    entity.setInteger(toSnakeCase(PurchaseBean.PROPERTY_VENDOR_ID), bean.getVendorId());
    return entity;
  }

  @Override
  public List<Purchase> getEntities() {
    return Purchase.<Purchase>findAll();
  }

  /**
   * Returns a list of all purchases for the vendor with the given
   * {@code vendorId}.
   * 
   * @param vendorId
   *          The id of the vendor
   * @return The list of all purchases for the vendor
   */
  public List<PurchaseBean> getPurchasesByVendor(Integer vendorId) {
    Preconditions.checkNotNull(vendorId);
    return getAll().stream().filter(purchaseBean -> purchaseBean.getVendorId().equals(vendorId)).collect(Collectors
        .toList());
  }

  /**
   * Calculates the profit for the kindergarten for the given list of purchases.
   * 
   * @param listPurchases
   *          The list of purchases to summarise the kindergarten profit for
   * @return The summarised profit for the kindergarten
   */
  public Double getKindergartenProfitByPurchases(List<PurchaseBean> listPurchases) {
    Preconditions.checkNotNull(listPurchases);
    Preconditions.checkArgument(!listPurchases.isEmpty());
    // TODO: Gibts hier eine Java 8 Stream Moeglichkeit? Der untere Code
    // funktioniert so nicht, da in eine enclosing variable geschrieben werden soll
    // "Local variable result defined in an enclosing scope must be final or
    // effectively final"

    // listPurchases.forEach(purchase -> result += getProfitByPurchase(purchase));
    Double result = 0.0d;
    for (PurchaseBean purchase : listPurchases) {
      result += getKindergartenProfitByPurchase(purchase);
    }
    return result;
  }

  /**
   * Calculates the payout amount for the vendors for the given list of purchases.
   * 
   * @param listPurchases
   *          The list of purchases to summarise the vendor payout amount for
   * @return The summarized vendor payout amount
   */
  public Double getVendorPayoutByPurchases(List<PurchaseBean> listPurchases) {
    Preconditions.checkNotNull(listPurchases);
    Preconditions.checkArgument(!listPurchases.isEmpty());
    // TODO: Gibts hier eine Java 8 Stream Moeglichkeit? Der untere Code
    // funktioniert so nicht, da in eine enclosing variable geschrieben werden soll
    // "Local variable result defined in an enclosing scope must be final or
    // effectively final"

    // listPurchases.forEach(purchase -> result +=
    // getVendorPayoutByPurchase(purchase));
    Double result = 0.0d;
    for (PurchaseBean purchase : listPurchases) {
      result += getVendorPayoutByPurchase(purchase);
    }
    return result;
  }

  /**
   * Calculates the sum of item prices for the given list of purchases.
   * 
   * @param listPurchases
   *          The list of purchases to summarise the item prices for
   * @return The sum of item prices
   */
  public Double getItemSum(List<PurchaseBean> listPurchases) {
    Preconditions.checkNotNull(listPurchases);
    Preconditions.checkArgument(!listPurchases.isEmpty());
    // TODO: Gibts hier eine Java 8 Stream Moeglichkeit? Der untere Code
    // funktioniert so nicht, da in eine enclosing variable geschrieben werden soll
    // "Local variable result defined in an enclosing scope must be final or
    // effectively final"

    // listPurchases.forEach(purchase -> result += purchase.getItemPrice();
    Double result = 0.0d;
    for (PurchaseBean purchase : listPurchases) {
      Preconditions.checkNotNull(purchase.getItemPrice());
      result += purchase.getItemPrice();
    }
    return result;
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
  public Integer getItemCount(List<PurchaseBean> listPurchases) {
    Preconditions.checkNotNull(listPurchases);
    Preconditions.checkArgument(!listPurchases.isEmpty());
    return listPurchases.size();
  }

  /**
   * Calculates the profit for the kindergarten for the given {@code purchase}.
   * 
   * @param purchase
   *          The purchase to calculate the kindergarten profit for
   * @return The profit for the kindergarten
   */
  public Double getKindergartenProfitByPurchase(PurchaseBean purchase) {
    Preconditions.checkNotNull(purchase);
    Preconditions.checkNotNull(purchase.getItemPrice());
    return purchase.getItemPrice() * KINDERGARTEN_PROFIT_RATIO;
  }

  /**
   * Calculates the vendor payout amount for the given {@code purchase}.
   * 
   * @param purchase
   *          The purchase to calculate the vendor payout amount for
   * @return The vendor payout amount
   */
  public Double getVendorPayoutByPurchase(PurchaseBean purchase) {
    Preconditions.checkNotNull(purchase);
    Preconditions.checkNotNull(purchase.getItemPrice());
    return purchase.getItemPrice() * VENDOR_PAYOUT_RATIO;
  }

}
