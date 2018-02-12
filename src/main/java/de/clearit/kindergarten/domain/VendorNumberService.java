package de.clearit.kindergarten.domain;

import java.util.List;
import java.util.stream.Collectors;

import javax.swing.ListModel;

import de.clearit.kindergarten.domain.entity.VendorNumber;
import de.clearit.kindergarten.utils.CollectorUtils;

/**
 * The service for the Vendor resource.
 */
public final class VendorNumberService extends AbstractResourceService<VendorNumberBean, VendorNumber> {

  private static final VendorNumberService INSTANCE = new VendorNumberService();

  /**
   * Private constructor to prevent instantiation.
   */
  private VendorNumberService() {
    super();
  }

  /**
   * Returns the singleton instance of the service.
   * 
   * @return the singleton instance
   */
  public static VendorNumberService getInstance() {
    return INSTANCE;
  }

  // Public API *************************************************************

  /**
   * Returns the list of Vendors as a ListModel.
   * 
   * @return the ListModel
   */
  @SuppressWarnings("unchecked")
  public ListModel<VendorNumberBean> getListModel() {
    return (ListModel<VendorNumberBean>) getAll();
  }

  @Override
  public VendorNumberBean fromEntity(VendorNumber entity) {
    VendorNumberBean bean = new VendorNumberBean();
    bean.setId(entity.getInteger(toSnakeCase(VendorNumberBean.PROPERTY_ID)));
    bean.setVendorNumber(entity.getInteger(toSnakeCase(VendorNumberBean.PROPERTY_VENDOR_NUMBER)));
    bean.setVendorId(entity.getInteger(toSnakeCase(VendorNumberBean.PROPERTY_VENDOR_ID)));
    return bean;
  }

  @Override
  public VendorNumber toEntity(VendorNumberBean bean) {
    VendorNumber entity = VendorNumber.findById(bean.getId());
    if (entity == null) {
      entity = new VendorNumber();
    }
    entity.setInteger(toSnakeCase(VendorNumberBean.PROPERTY_VENDOR_NUMBER), bean.getVendorNumber());
    entity.setInteger(toSnakeCase(VendorNumberBean.PROPERTY_VENDOR_ID), bean.getVendorId());
    return entity;
  }

  @Override
  public List<VendorNumber> getEntities() {
    return VendorNumber.findAll();
  }

  /**
   * Looks up all {@link VendorNumberBean}s by the given (technical)
   * {@code vendorId}.
   * 
   * @param vendorId
   *          the (technical) vendor id
   * @return the list of found {@link VendorNumberBean}s
   */
  public List<VendorNumberBean> findByVendorId(int vendorId) {
    return getAll().stream().filter(element -> element.getVendorId() == vendorId).collect(Collectors.toList());
  }

  /**
   * Looks up a {@link VendorNumberBean} by the given {@code vendorNumber}. Since
   * a vendor number can only be give to a single vendor, the result will be a
   * single {@link VendorNumberBean}.
   * 
   * @param vendorNumber
   *          the vendor number
   * @return the found {@link VendorNumberBean}
   */
  public VendorNumberBean findByVendorNumber(int vendorNumber) {
    return getAll().stream().filter(element -> element.getVendorNumber() == vendorNumber).collect(CollectorUtils
        .singletonCollector());
  }

}
