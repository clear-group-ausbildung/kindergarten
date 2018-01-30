package de.clearit.kindergarten.domain;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;

import de.clearit.kindergarten.domain.entity.Vendor;
import de.clearit.kindergarten.domain.entity.VendorNumber;

/**
 * The service for the Vendor resource.
 */
public final class VendorNumberService extends AbstractResourceService<VendorNumberBean, VendorNumber> {

  private static VendorNumberService INSTANCE = new VendorNumberService();

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

  /**
   * Finds a VendorBean by the given vendor_id
   * 
   * @param vendor_id
   * @return the found ArrayList<VendorNumberBean>
   */
  public ArrayList<VendorNumberBean> findByVendorID(int vendorID) {
	  ArrayList<VendorNumberBean> result = new ArrayList<VendorNumberBean>();
    for (VendorNumberBean vendorNumberbean : getAll()) {
      if (vendorNumberbean.getVendorId() == vendorID) {
    	  result.add(vendorNumberbean);
      }
    }
    return result;
  }
  
  public VendorNumberBean findByVendorNumber(int vendorNumber) {
	    for (VendorNumberBean vendorNumberbean : getAll()) {
	      if (vendorNumberbean.getVendorNumber() == vendorNumber) {
	        return vendorNumberbean;
	      }
	    }
	    return null;
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
    entity.setString(toSnakeCase(VendorNumberBean.PROPERTY_ID), bean.getId());
    entity.setString(toSnakeCase(VendorNumberBean.PROPERTY_VENDOR_NUMBER), bean.getVendorNumber());
    entity.setString(toSnakeCase(VendorNumberBean.PROPERTY_VENDOR_ID), bean.getVendorId());
    return entity;
  }

  @Override
  public List<VendorNumber> getEntities() {
    return VendorNumber.findAll();
  }


}
