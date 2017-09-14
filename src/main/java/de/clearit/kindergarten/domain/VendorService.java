package de.clearit.kindergarten.domain;

import java.util.List;

import javax.swing.ListModel;

import de.clearit.kindergarten.domain.entity.Vendor;

/**
 * The service for the Vendor resource.
 */
public final class VendorService extends AbstractResourceService<VendorBean, Vendor> {

  private static final VendorService INSTANCE = new VendorService();

  /**
   * Private constructor to prevent instantiation.
   */
  private VendorService() {
    super();
  }

  /**
   * Returns the singleton instance of the service.
   * 
   * @return the singleton instance
   */
  public static VendorService getInstance() {
    return INSTANCE;
  }

  // Public API *************************************************************

  /**
   * Returns the list of Vendors as a ListModel.
   * 
   * @return the ListModel
   */
  public ListModel<VendorBean> getListModel() {
    return (ListModel<VendorBean>) getAll();
  }

  /**
   * Finds a VendorBean by the given {@code firstName} and {@code lastName}.
   * 
   * @param firstName
   *          the first name of the vendor
   * @param lastName
   *          the last name of the vendor
   * @return the found {@link VendorBean} or {@code null}
   */
  public VendorBean findByName(String firstName, String lastName) {
    for (VendorBean vendor : getAll()) {
      if (vendor.getFirstName().equals(firstName) && vendor.getLastName().equals(lastName)) {
        return vendor;
      }
    }
    return null;
  }

  @Override
  public VendorBean fromEntity(Vendor entity) {
    VendorBean bean = new VendorBean();
    bean.setId(entity.getInteger(VendorBean.PROPERTY_ID));
    bean.setFirstName(entity.getString(toSnakeCase(VendorBean.PROPERTY_FIRST_NAME)));
    bean.setLastName(entity.getString(toSnakeCase(VendorBean.PROPERTY_LAST_NAME)));
    bean.setPhoneNumber(entity.getString(toSnakeCase(VendorBean.PROPERTY_PHONE_NUMBER)));
    bean.setDelivered(entity.getInteger(VendorBean.PROPERTY_DELIVERED) != 0);
    bean.setDirty(entity.getInteger(VendorBean.PROPERTY_DIRTY) != 0);
    bean.setFetched(entity.getInteger(VendorBean.PROPERTY_FETCHED) != 0);
    bean.setReceivedMoney(entity.getInteger(toSnakeCase(VendorBean.PROPERTY_RECEIVED_MONEY)) != 0);
    return bean;
  }

  @Override
  public Vendor toEntity(VendorBean bean) {
    Vendor entity = Vendor.findById(bean.getId());
    if (entity == null) {
      entity = new Vendor();
    }
    entity.setString(toSnakeCase(VendorBean.PROPERTY_FIRST_NAME), bean.getFirstName());
    entity.setString(toSnakeCase(VendorBean.PROPERTY_LAST_NAME), bean.getLastName());
    entity.setString(toSnakeCase(VendorBean.PROPERTY_PHONE_NUMBER), bean.getPhoneNumber());
    entity.setInteger(VendorBean.PROPERTY_DELIVERED, bean.getDelivered() ? 1 : 0);
    entity.setInteger(VendorBean.PROPERTY_DIRTY, bean.getDirty() ? 1 : 0);
    entity.setInteger(VendorBean.PROPERTY_FETCHED, bean.getFetched() ? 1 : 0);
    entity.setInteger(toSnakeCase(VendorBean.PROPERTY_RECEIVED_MONEY), bean.getReceivedMoney() ? 1 : 0);
    return entity;
  }

  @Override
  protected List<Vendor> getEntities() {
    return Vendor.<Vendor>findAll();
  }

}
