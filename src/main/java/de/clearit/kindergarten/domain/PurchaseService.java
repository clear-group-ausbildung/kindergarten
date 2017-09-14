package de.clearit.kindergarten.domain;

import java.util.List;

import javax.swing.ListModel;

import de.clearit.kindergarten.domain.entity.Purchase;

/**
 * The service for the Purchase resource.
 */
public class PurchaseService extends AbstractResourceService<PurchaseBean, Purchase> {

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
    bean.setItemQuantity(entity.getInteger(toSnakeCase(PurchaseBean.PROPERTY_ITEM_QUANTITY)));
    bean.setItemNumber(entity.getInteger(toSnakeCase(PurchaseBean.PROPERTY_ITEM_NUMBER)));
    bean.setItemPrice(entity.getDouble(toSnakeCase(PurchaseBean.PROPERTY_ITEM_PRICE)));
    bean.setTotalPrice(entity.getDouble(toSnakeCase(PurchaseBean.PROPERTY_TOTAL_PRICE)));
    bean.setProfit(entity.getDouble(PurchaseBean.PROPERTY_PROFIT));
    bean.setPayment(entity.getDouble(PurchaseBean.PROPERTY_PAYMENT));
    bean.setVendorId(entity.getInteger(toSnakeCase(PurchaseBean.PROPERTY_VENDOR_ID)));
    return bean;
  }

  @Override
  public Purchase toEntity(PurchaseBean bean) {
    Purchase entity = Purchase.findById(bean.getId());
    if (entity == null) {
      entity = new Purchase();
    }
    entity.setInteger(toSnakeCase(PurchaseBean.PROPERTY_ITEM_QUANTITY), bean.getItemQuantity());
    entity.setInteger(toSnakeCase(PurchaseBean.PROPERTY_ITEM_NUMBER), bean.getItemNumber());
    entity.setDouble(toSnakeCase(PurchaseBean.PROPERTY_ITEM_PRICE), bean.getItemPrice());
    entity.setDouble(toSnakeCase(PurchaseBean.PROPERTY_TOTAL_PRICE), bean.getTotalPrice());
    entity.setDouble(PurchaseBean.PROPERTY_PROFIT, bean.getProfit());
    entity.setDouble(PurchaseBean.PROPERTY_PAYMENT, bean.getPayment());
    entity.setInteger(toSnakeCase(PurchaseBean.PROPERTY_VENDOR_ID), bean.getVendorId());
    return entity;
  }

  @Override
  protected List<Purchase> getEntities() {
    return Purchase.<Purchase>findAll();
  }

}
