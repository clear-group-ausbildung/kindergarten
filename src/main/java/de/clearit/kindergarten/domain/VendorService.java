package de.clearit.kindergarten.domain;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;

import org.apache.poi.ss.formula.functions.Address;

import de.clearit.kindergarten.domain.entity.Vendor;
import de.clearit.kindergarten.domain.entity.VendorNumber;
import de.clearit.kindergarten.utils.CollectorUtils;

/**
 * The service for the Vendor resource.
 */
public final class VendorService extends AbstractResourceService<VendorBean, Vendor> {

  private static final VendorService INSTANCE = new VendorService();
  private static final VendorNumberService NUMBER_SERVICE = VendorNumberService.getInstance();

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

  @Override
  protected void postCreate(VendorBean bean, Vendor entity) {
    Integer createdVendorId = entity.getInteger(toSnakeCase(VendorBean.PROPERTY_ID));
    bean.getVendorNumbers().stream().forEach(number -> number.setVendorId(createdVendorId));
    bean.getVendorNumbers().stream().forEach(number -> entity.add(NUMBER_SERVICE.toEntity(number)));
    entity.saveIt();
  }

  // Public API *************************************************************

  /**
   * Returns the list of Vendors as a ListModel.
   * 
   * @return the ListModel
   */
  @SuppressWarnings("unchecked")
  public ListModel<VendorBean> getListModel() {
    return (ListModel<VendorBean>) getAll();
  }

  @Override
  public VendorBean fromEntity(Vendor entity) {
    VendorBean bean = new VendorBean();
    bean.setId(entity.getInteger(VendorBean.PROPERTY_ID));
    bean.setFirstName(entity.getString(toSnakeCase(VendorBean.PROPERTY_FIRST_NAME)));
    bean.setLastName(entity.getString(toSnakeCase(VendorBean.PROPERTY_LAST_NAME)));
    bean.setPhoneNumber(entity.getString(toSnakeCase(VendorBean.PROPERTY_PHONE_NUMBER)));
    // Load Vendor Numbers
    List<VendorNumber> vendorNumbers = entity.getAll(VendorNumber.class);
    List<VendorNumberBean> vendorNumberBeans = new ArrayList<>();
    vendorNumbers.stream().forEach(number -> vendorNumberBeans.addAll(NUMBER_SERVICE.findByVendorId(bean.getId())));
    bean.setVendorNumbers(vendorNumberBeans);
    return bean;
  }

  @Override
  public Vendor toEntity(VendorBean bean) {
    Vendor entity = Vendor.findById(bean.getId());
    if (entity == null) {
      entity = new Vendor();
    }
    if (bean.getId() != null && bean.getId() != 0) {
      entity.setInteger(toSnakeCase(VendorBean.PROPERTY_ID), bean.getId());
    }
    entity.setString(toSnakeCase(VendorBean.PROPERTY_FIRST_NAME), bean.getFirstName());
    entity.setString(toSnakeCase(VendorBean.PROPERTY_LAST_NAME), bean.getLastName());
    entity.setString(toSnakeCase(VendorBean.PROPERTY_PHONE_NUMBER), bean.getPhoneNumber());

    return entity;
  }

  @Override
  public List<Vendor> getEntities() {
    return Vendor.findAll();
  }

  /**
   * Finds a VendorBean by the given {@code firstName} and {@code lastName}.
   * Furthermore, the list of vendor numbers is populated with the found vendor
   * numbers for this vendor.
   * 
   * @param firstName
   *          the first name of the vendor
   * @param lastName
   *          the last name of the vendor
   * @return the found {@link VendorBean} or {@code null}
   */
  public VendorBean findByName(String firstName, String lastName) {
    VendorBean result = getAll().stream().filter(element -> element.getFirstName().equals(firstName) && element
        .getLastName().equals(lastName)).collect(CollectorUtils.singletonCollector());
    result.setVendorNumbers(VendorNumberService.getInstance().findByVendorId(result.getId()));
    return result;
  }

}
