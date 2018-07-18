package de.clearit.kindergarten.domain.entity;

import de.clearit.kindergarten.domain.PurchaseBean;

public class PurchaseVendorEntity extends PurchaseBean
{
	private String vendorFullName;
	private Integer vendorNumber;
	
	public Integer getVendorNumber()
	{
		return vendorNumber;
	}
	public void setVendorNumber(Integer vendorNumber)
	{
		this.vendorNumber = vendorNumber;
	}

	public String getVendorFullName()
	{
		return vendorFullName;
	}

	public void setVendorFullName(String vendorFullName)
	{
		this.vendorFullName = vendorFullName;
	}
	

	
}