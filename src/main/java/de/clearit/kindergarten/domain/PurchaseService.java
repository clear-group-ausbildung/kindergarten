package de.clearit.kindergarten.domain;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ListModel;

import org.apache.commons.collections4.CollectionUtils;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

import de.clearit.kindergarten.domain.entity.Purchase;
import de.clearit.kindergarten.utils.JacksonUtils;
import io.reactivex.Emitter;

/**
 * The service for the Purchase resource.
 */
public class PurchaseService extends AbstractResourceService<PurchaseBean, Purchase> {

  private static final Logger LOGGER = Logger.getLogger(PurchaseService.class.getName());

  private static final BigDecimal ZERO = BigDecimal.valueOf(0.0d);
  private static final BigDecimal KINDERGARTEN_PROFIT_RATIO = BigDecimal.valueOf(0.2d);
  private static final BigDecimal VENDOR_PAYOUT_RATIO = BigDecimal.valueOf(0.8d);

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
    LOGGER.entering(PurchaseService.class.getSimpleName(), "fromEntity(Purchase entity)", new Object[] { entity });
    PurchaseBean bean = new PurchaseBean();
    bean.setId(entity.getInteger(PurchaseBean.PROPERTY_ID));
    bean.setItemNumber(entity.getInteger(toSnakeCase(PurchaseBean.PROPERTY_ITEM_NUMBER)));
    bean.setItemPrice(entity.getBigDecimal(toSnakeCase(PurchaseBean.PROPERTY_ITEM_PRICE)));
    bean.setVendorNumber(entity.getInteger(toSnakeCase(PurchaseBean.PROPERTY_VENDOR_NUMBER)));
    LOGGER.exiting(PurchaseService.class.getSimpleName(), "fromEntity(Purchase entity)", new Object[] { bean });
    return bean;
  }

  @Override
  public Purchase toEntity(PurchaseBean bean) {
    LOGGER.entering(PurchaseService.class.getSimpleName(), "toEntity(PurchaseBean bean)", new Object[] { bean });
    Purchase entity = Purchase.findById(bean.getId());
    if (entity == null) {
      LOGGER.fine("New Purchase entity!");
      entity = new Purchase();
    }
    entity.setInteger(toSnakeCase(PurchaseBean.PROPERTY_ITEM_NUMBER), bean.getItemNumber());
    entity.setBigDecimal(toSnakeCase(PurchaseBean.PROPERTY_ITEM_PRICE), bean.getItemPrice());
    entity.setInteger(toSnakeCase(PurchaseBean.PROPERTY_VENDOR_NUMBER), bean.getVendorNumber());
    LOGGER.exiting(PurchaseService.class.getSimpleName(), "toEntity(PurchaseBean bean)", new Object[] { entity });
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
    return CollectionUtils.isNotEmpty(listPurchases) ? getItemSumByPurchases(listPurchases).multiply(
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
    return CollectionUtils.isNotEmpty(listPurchases) ? getItemSumByPurchases(listPurchases).multiply(
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
    Optional<BigDecimal> itemSum = listPurchases.stream().map(PurchaseBean::getItemPrice).reduce(BigDecimal::add);
    BigDecimal result = ZERO;
    if (itemSum.isPresent()) {
      result = itemSum.get();
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
  public Integer getItemCountByPurchases(List<PurchaseBean> listPurchases) {
    return CollectionUtils.isNotEmpty(listPurchases) ? listPurchases.size() : 0;
  }

  public PurchaseBean readPurchase(JsonParser parser) throws IOException {
    LOGGER.entering(PurchaseService.class.getSimpleName(), "readPurchase(JsonParser parser)", new Object[] { parser });
    JacksonUtils.debugJsonParser(parser);
    parser.nextToken(); //
    LOGGER.fine("PurchaseService.readPurchase(JsonParser parser) after .nextToken()");
    JacksonUtils.debugJsonParser(parser);
    final PurchaseBean purchaseBean = new PurchaseBean();
    purchaseBean.setItemNumber(JacksonUtils.readInt(parser));
    purchaseBean.setItemPrice(BigDecimal.valueOf(JacksonUtils.readDouble(parser)));
    purchaseBean.setVendorNumber(JacksonUtils.readInt(parser));
    LOGGER.log(Level.FINE, () -> "Created PurchaseBean: " + purchaseBean);
    parser.nextToken(); //
    LOGGER.fine("PurchaseService.readPurchase(JsonParser parser) after PurchaseBean creation");
    JacksonUtils.debugJsonParser(parser);
    LOGGER.exiting(PurchaseService.class.getSimpleName(), "readPurchase(JsonParser parser)", new Object[] {
        purchaseBean });
    return purchaseBean;
  }

  public void pullOrComplete(JsonParser parser, Emitter<PurchaseBean> emitter) throws IOException {
    LOGGER.entering(PurchaseService.class.getSimpleName(),
        "pullOrComplete(JsonParser parser, Emitter<PurchaseBean> emitter)", new Object[] { parser, emitter });
    JacksonUtils.debugJsonParser(parser);
    if (parser.nextToken() != JsonToken.END_ARRAY) {
      final PurchaseBean purchaseBean = readPurchase(parser);
      LOGGER.log(Level.FINE, () -> "emitter.onNext: " + purchaseBean);
      emitter.onNext(purchaseBean);
    } else {
      LOGGER.fine("parser.nextToken == JsonToken.END_OBJECT");
      LOGGER.fine("emitter.onComplete()");
      emitter.onComplete();
    }
    LOGGER.exiting(PurchaseService.class.getSimpleName(),
        "pullOrComplete(JsonParser parser, Emitter<PurchaseBean> emitter)");
  }

}
