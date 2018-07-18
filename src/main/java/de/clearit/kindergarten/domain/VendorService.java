package de.clearit.kindergarten.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.ListModel;

import de.clearit.kindergarten.domain.entity.Vendor;
import de.clearit.kindergarten.domain.entity.VendorNumber;
import de.clearit.kindergarten.utils.CollectorUtils;

/**
 * The service for the Vendor resource.
 */
public final class VendorService extends AbstractResourceService<VendorBean, Vendor> {

	private static final Logger LOGGER = Logger.getLogger(VendorService.class.getName());
	private List<VendorBean> existingVendorBeans = null;
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

	@Override
	public void postCreate(VendorBean bean, Vendor entity) {
		super.postCreate(bean, entity);
		Integer createdVendorId = entity.getInteger(toSnakeCase(VendorBean.PROPERTY_ID));
		bean.getVendorNumbers().forEach(number -> number.setVendorId(createdVendorId));
		bean.getVendorNumbers().forEach(number -> entity.add(VendorNumberService.getInstance().toEntity(number)));
		entity.saveIt();
		LOGGER.exiting(VendorService.class.getSimpleName(), "postCreate(VendorBean bean, Vendor entity)");
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
		LOGGER.entering(VendorService.class.getSimpleName(), "fromEntity(Vendor entity)", new Object[] { entity });
		VendorBean bean = new VendorBean();
		bean.setId(entity.getInteger(VendorBean.PROPERTY_ID));
		bean.setFirstName(entity.getString(toSnakeCase(VendorBean.PROPERTY_FIRST_NAME)));
		bean.setLastName(entity.getString(toSnakeCase(VendorBean.PROPERTY_LAST_NAME)));
		bean.setPhoneNumber(entity.getString(toSnakeCase(VendorBean.PROPERTY_PHONE_NUMBER)));
		// Load Vendor Numbers
		List<VendorNumber> listVendorNumberEntities = entity.getAll(VendorNumber.class);
		List<VendorNumberBean> listVendorNumberBeans = new ArrayList<>();
		for (VendorNumber vendorNumberEntity : listVendorNumberEntities) {
			VendorNumberBean vendorNumberBean = VendorNumberService.getInstance().fromEntity(vendorNumberEntity);
			listVendorNumberBeans.add(vendorNumberBean);
		}
		bean.setVendorNumbers(listVendorNumberBeans);
		LOGGER.exiting(PurchaseService.class.getSimpleName(), "fromEntity(Vendor entity)", new Object[] { bean });
		return bean;
	}

	@Override
	public Vendor toEntity(VendorBean bean) {
		LOGGER.entering(VendorService.class.getSimpleName(), "toEntity(VendorBean bean)", new Object[] { bean });
		Vendor entity = Vendor.findById(bean.getId());
		if (entity == null) {
			LOGGER.fine("New Vendor entity!");
			entity = new Vendor();
		}
		if (bean.getId() != null && bean.getId() != 0) {
			entity.setInteger(toSnakeCase(VendorBean.PROPERTY_ID), bean.getId());
		}
		entity.setString(toSnakeCase(VendorBean.PROPERTY_FIRST_NAME), bean.getFirstName());
		entity.setString(toSnakeCase(VendorBean.PROPERTY_LAST_NAME), bean.getLastName());
		entity.setString(toSnakeCase(VendorBean.PROPERTY_PHONE_NUMBER), bean.getPhoneNumber());

		LOGGER.exiting(VendorService.class.getSimpleName(), "toEntity(VendorBean bean)", new Object[] { entity });
		return entity;
	}

	@Override
	public void delete(VendorBean bean) {
		LOGGER.entering(VendorService.class.getSimpleName(), "delete(VendorBean bean)", new Object[] { bean });
		Vendor entity = toEntity(bean);
		entity.deleteCascade();
		postDelete(bean, entity);
		flush();
		LOGGER.exiting(VendorService.class.getSimpleName(), "delete(VendorBean bean)");
	}

	@Override
	public void update(VendorBean bean) {
		LOGGER.entering(VendorService.class.getSimpleName(), "update(VendorBean bean)", new Object[] { bean });
		delete(bean);
		createAsNew(bean);
		LOGGER.exiting(VendorService.class.getSimpleName(), "update(VendorBean bean)");
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
	 *            the first name of the vendor
	 * @param lastName
	 *            the last name of the vendor
	 * @return the found {@link VendorBean} or {@code null}
	 */
	public VendorBean findByName(String firstName, String lastName) {
		LOGGER.entering(VendorService.class.getName(), "findByName(String firstName, String lastName)",
				new Object[] { firstName, lastName });
		VendorBean result = getAll().stream()
				.filter(element -> element.getFirstName().equals(firstName) && element.getLastName().equals(lastName))
				.collect(CollectorUtils.singletonCollector());
		result.setVendorNumbers(VendorNumberService.getInstance().findByVendorId(result.getId()));
		LOGGER.exiting(VendorService.class.getName(), "findByName(String firstName, String lastName)",
				new Object[] { result });
		return result;
	}
	
	public VendorBean findByVendorNumber(Integer vendorNumber) {
        LOGGER.entering(VendorService.class.getName(), "findByVendorNumber(Integer vendorNumber)", new Object[] {
                vendorNumber});
        VendorBean result = null;
        // 1. Alle Vendors ermitteln
        List<VendorBean> allVendors = getAll();
        // 2. Ueber alle Vendors iterieren
        for(VendorBean oneVendor : allVendors) {
            // 2.1 Ueber alle VendorNumbers von oneVendor iterieren
            for(VendorNumberBean oneVendorNumber : oneVendor.getVendorNumbers()) {
                // 2.1.1 -> entspricht die aktuelle VendorNumber dem uebergebenen vendorNumber Parameter?
                if(Integer.valueOf(oneVendorNumber.getVendorNumber()).equals(vendorNumber)) {
                    // Huzzah, Ergebnis gefunden -> zurueckgeben
                    return oneVendor;
                }
            }
        }
        LOGGER.exiting(VendorService.class.getName(), "findByVendorNumber(Integer vendorNumber)", new Object[] {
                result });
      return result;
 }

	private void createAsNew(VendorBean bean) {
		LOGGER.entering(VendorService.class.getSimpleName(), "createAsNew(VendorBean bean)", new Object[] { bean });
		bean.setId(null);
		super.create(bean);
		LOGGER.exiting(VendorService.class.getSimpleName(), "createAsNew(VendorBean bean)");
	}
	
	
	
	/**
	 * import Vendors from a JSON File
	 * @param bean
	 */
	public void importVendors(VendorBean bean) {
		List<VendorBean> allVendors = getAll();
		boolean check = true;
		for(int i = 0; i < allVendors.size(); i++) {
			
			if(bean.getLastName().equals(allVendors.get(i).getLastName()) || bean.getFirstName().equals(allVendors.get(i).getFirstName())) {
				check = false;
			}
		}
		if(check) {
			createAsNew(bean);
		}
	}

	/**
	 * Call this Method for -> clear the existing VendorBeansList
	 */
	public void clearExistingVendorBeansList() {
		getExistingVendorBeans().clear();
	}
	
	/**
	 * List for the MessageDialog
	 * Returns a List -> VendorBeans
	 * @return
	 */
	
	public List<VendorBean> getExistingVendorBeans(){
		return existingVendorBeans;
	}
	
}