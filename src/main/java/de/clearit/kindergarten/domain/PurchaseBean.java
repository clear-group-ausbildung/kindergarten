package de.clearit.kindergarten.domain;

import org.javalite.activejdbc.Model;

import com.google.common.base.CaseFormat;

import de.clearit.kindergarten.domain.entity.Purchase;

public class PurchaseBean extends Model {
	
	// Names of the Bound Bean Properties *************************************	
	public static final String PROPERTY_VENDOR_ID = "vendorId";
	public static final String PROPERTY_ITEM_QUANTITY = "itemQuantity";
	public static final String PROPERTY_ITEM_NUMBER = "itemNumber";
	public static final String PROPERTY_ITEM_PRICE = "itemPrice";
	public static final String PROPERTY_PAYMENT = "payment";
	public static final String PROPERTY_PROFIT = "profit";
	public static final String PROPERTY_SUM = "sum";

	
	// Fields *****************************************************************

	private Integer vendorId;
	private Integer itemQuantity;
	private Integer itemNumber;
	private Double itemPrice;
	private Double payment;
	private Double profit;
	private Double sum;

	public static PurchaseBean fromEntity(Purchase entity) {
		PurchaseBean bean = new PurchaseBean();
		bean.setVendorId(entity.getInteger(PROPERTY_VENDOR_ID));
		bean.setItemQuantity(entity.getInteger(PROPERTY_ITEM_QUANTITY));
		bean.setItemNumber(entity.getInteger(PROPERTY_ITEM_NUMBER));
		bean.setItemPrice(entity.getDouble(toSnakeCase(PROPERTY_ITEM_PRICE)));
		bean.setPayment(entity.getDouble(toSnakeCase(PROPERTY_PAYMENT)));
		bean.setProfit(entity.getDouble(toSnakeCase(PROPERTY_PROFIT)));
		bean.setSum(entity.getDouble(PROPERTY_SUM));
		return bean;
	}
	
	 public static Purchase toEntity(PurchaseBean bean) {
		 Purchase entity = Purchase.findById(bean.getId());
		 if (entity == null) {
		   entity = new Purchase();
		 }
		 entity.setInteger(toSnakeCase(PROPERTY_VENDOR_ID), bean.getVendorId());
		 entity.setInteger(toSnakeCase(PROPERTY_ITEM_QUANTITY), bean.getItemQuantity());
		 entity.setInteger(toSnakeCase(PROPERTY_ITEM_NUMBER), bean.getItemNumber());
		 entity.setDouble(toSnakeCase(PROPERTY_ITEM_PRICE), bean.getItemPrice());
		 entity.setDouble(toSnakeCase(PROPERTY_PAYMENT), bean.getPayment());
		 entity.setDouble(toSnakeCase(PROPERTY_PROFIT), bean.getProfit());
		 entity.setDouble(toSnakeCase(PROPERTY_SUM), bean.getSum());

		 return entity;
	}
	
	public PurchaseBean(Integer vendorId, Integer itemNumber, Double itemPrice) {
		    super();
		    this.vendorId = vendorId;
		    this.itemNumber = itemNumber;
		    this.itemPrice = itemPrice;
	}
	
	public PurchaseBean() {
		this(0, 0, 0.00);
	}
	
	private static String toSnakeCase(String camelCase) {
		return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, camelCase);
	}

	
	public Integer getVendorId() {
		return vendorId;
	}
	
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public Integer getItemQuantity() {
		return itemQuantity;
	}
	
	public void setItemQuantity(Integer itemQuantity) {
		this.itemQuantity = itemQuantity;
	}
	
	public Integer getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(Integer itemNumber) {
		this.itemNumber = itemNumber;
	}

	public Double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(Double itemPrice) {
		this.itemPrice = itemPrice;
	}

	public Double getPayment() {
		return payment;
	}
	
	public void setPayment(Double payment) {
		this.payment = payment;
	}
	
	public Double getProfit() {
		return profit;
	}

	public void setProfit(Double profit) {
		this.profit = profit;
	}	 
	
	public Double getSum() {
		return sum;
	}
	
	public void setSum(Double sum) {
		this.sum = sum;
	}
}
