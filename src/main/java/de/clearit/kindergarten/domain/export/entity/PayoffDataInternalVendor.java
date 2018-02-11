package de.clearit.kindergarten.domain.export.entity;

import de.clearit.kindergarten.domain.VendorBean;

/**
 * The class PayoffDataInternalVendor
 *
 * @author Marco Jaeger
 */
public class PayoffDataInternalVendor {

	private final VendorBean vendor;
	private final Double vendorPayment;

	public PayoffDataInternalVendor(VendorBean pVendor, Double pVendorPayment) {
		vendor = pVendor;
		vendorPayment = pVendorPayment;
	}

	public VendorBean getVendor() {
		return vendor;
	}

	public Double getVendorPayment() {
		return vendorPayment;
	}
}