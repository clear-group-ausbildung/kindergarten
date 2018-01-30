package de.clearit.kindergarten.domain;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;

import org.javalite.activejdbc.Model;

import de.clearit.kindergarten.domain.entity.Vendor;
import de.clearit.kindergarten.domain.entity.VendorNumber;

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
  @SuppressWarnings("unchecked")
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
    	 vendor.setVendorNumbers(VendorNumberService.getInstance().findByVendorID(vendor.getId()));
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
    bean.setVendorNumbers(VendorNumberService.getInstance().findByVendorID(bean.getId()));
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
   
//    for(VendorNumber vnr : VendorNumberService.getInstance().getEntities()) {
//    	if(bean.getId().equals(vnr.getInteger(VendorNumberBean.PROPERTY_VENDOR_ID))) {
//    		entity.add(vnr);
//    	}else {
//    		//test
//    		entity.add(vnr);
//    	}
//    }
    return entity;
  }
  
  

  @Override
public void create(VendorBean bean) {
	// TODO Auto-generated method stub
	super.create(bean);
	
    Vendor vendorentity =  this.toEntity(this.findByName(bean.getFirstName(), bean.getLastName()));
    VendorNumberBean vnrbean1 = new VendorNumberBean();
    vnrbean1.setVendorId(this.findByName(bean.getFirstName(), bean.getLastName()).getId());
    vnrbean1.setVendorNumber(558);
    VendorNumberBean vnrbean2 = new VendorNumberBean();
    vnrbean2.setVendorId(this.findByName(bean.getFirstName(), bean.getLastName()).getId());
    vnrbean2.setVendorNumber(559);
    List<VendorNumberBean> list = new ArrayList<>();
    list.add(vnrbean1);
    list.add(vnrbean2);
    
    bean.setVendorNumbers(list);
    
	 for(VendorNumberBean vnrbean : bean.getVendorNumbers()) {
	    	VendorNumberService.getInstance().create(vnrbean);
	    }
	
}

@Override
  public List<Vendor> getEntities() {

//	  List<Vendor> result = new ArrayList<>();
//	  for(Model test : Vendor.findAll()) {
//		  int id = (int) ((Vendor)test).getId();
//		  for(VendorNumberBean vnr : VendorNumberService.getInstance().findByVendorID(id)) {
//			  ((Vendor)test).add(VendorNumberService.getInstance().toEntity(vnr));
//		  }
//		  result.add((Vendor) test);
//	  }
	  
    return Vendor.findAll();
//    return result;
  }

}
