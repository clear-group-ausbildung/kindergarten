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
    bean.setVendorNumber(entity.getInteger(toSnakeCase(VendorBean.PROPERTY_VENDOR_NUMBER)));
    bean.setFirstName(entity.getString(toSnakeCase(VendorBean.PROPERTY_FIRST_NAME)));
    bean.setLastName(entity.getString(toSnakeCase(VendorBean.PROPERTY_LAST_NAME)));
    bean.setPhoneNumber(entity.getString(toSnakeCase(VendorBean.PROPERTY_PHONE_NUMBER)));
    return bean;
  }

  @Override
  public Vendor toEntity(VendorBean bean) {
    Vendor entity = Vendor.findById(bean.getId());
    if (entity == null) {
      entity = new Vendor();
    }
    entity.setInteger(toSnakeCase(VendorBean.PROPERTY_VENDOR_NUMBER), bean.getVendorNumber());
    entity.setString(toSnakeCase(VendorBean.PROPERTY_FIRST_NAME), bean.getFirstName());
    entity.setString(toSnakeCase(VendorBean.PROPERTY_LAST_NAME), bean.getLastName());
    entity.setString(toSnakeCase(VendorBean.PROPERTY_PHONE_NUMBER), bean.getPhoneNumber());
    return entity;
  }

  @Override
  public List<Vendor> getEntities() {
    return Vendor.<Vendor>findAll();
  }

}
