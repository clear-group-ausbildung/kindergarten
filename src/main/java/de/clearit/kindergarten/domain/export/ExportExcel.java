package de.clearit.kindergarten.domain.export;

import java.util.List;

import de.clearit.kindergarten.domain.VendorBean;
import de.clearit.kindergarten.domain.VendorService;

/**
 * The class ExportExcel
 *
 * @author Marco Jaeger, GfK External
 */
public class ExportExcel {

	private static final ExportExcel INSTANCE = new ExportExcel();

	private ExportExcel() {
	}

	public static ExportExcel getInstance() {
		return INSTANCE;
	}

	/**
	 * Creates an receipt in excel for each existing vendor.
	 */
	public void createExcelForAllVendors() {
		List<VendorBean> vendorList = VendorService.getInstance().getAll();
		vendorList.forEach(this::createExcelForOneVendor);
	}

	/**
	 * Creates an receipt in excel for the given vendor.
	 * 
	 * @param pVendor
	 *            {@link VendorBean}
	 */
	public void createExcelForOneVendor(VendorBean pVendor) {
		ExportReceipt.getInstance().createReceipt(pVendor);
	}

	public void createExcelForInternalPayoff() {
		ExportInternalPayoff.getInstance().createInternalPayoff();
	}
}
