package de.clearit.kindergarten.domain;

import org.javalite.activejdbc.Model;

import com.google.common.base.CaseFormat;

import de.clearit.kindergarten.domain.entity.Purchase;

public class PurchaseBean extends Model {
	
	// Names of the Bound Bean Properties *************************************	
	public static final String PROPERTY_VENDOR_ID = "sellerId";
	public static final String PROPERTY_ITEM_QUANTITY = "itemQuantity";
	public static final String PROPERTY_ITEM_NUMBER = "itemNumber";
	public static final String PROPERTY_ITEM_PRICE = "price";
	public static final String PROPERTY_PAYMENT = "payment";
	public static final String PROPERTY_PROFIT = "profit";
	public static final String PROPERTY_SUM = "sum";

	
	// Fields *****************************************************************

	private Integer sellerId;
	private Integer itemQuantity;
	private Integer itemNumber;
	private Double price;
	private Double payment;
	private Double profit;
	private Double sum;

	public static PurchaseBean fromEntity(Purchase entity) {
		PurchaseBean bean = new PurchaseBean();
		bean.setSellerId(entity.getInteger(PROPERTY_VENDOR_ID));
		bean.setItemQuantity(entity.getInteger(PROPERTY_ITEM_QUANTITY));
		bean.setItemNumber(entity.getInteger(PROPERTY_ITEM_NUMBER));
		bean.setPrice(entity.getDouble(toSnakeCase(PROPERTY_ITEM_PRICE)));
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
		 entity.setInteger(toSnakeCase(PROPERTY_VENDOR_ID), bean.getSellerId());
		 entity.setInteger(toSnakeCase(PROPERTY_ITEM_QUANTITY), bean.getItemQuantity());
		 entity.setInteger(toSnakeCase(PROPERTY_ITEM_NUMBER), bean.getItemNumber());
		 entity.setDouble(toSnakeCase(PROPERTY_ITEM_PRICE), bean.getPrice());
		 entity.setDouble(toSnakeCase(PROPERTY_PAYMENT), bean.getPayment());
		 entity.setDouble(toSnakeCase(PROPERTY_PROFIT), bean.getProfit());
		 entity.setDouble(toSnakeCase(PROPERTY_SUM), bean.getSum());

		 return entity;
	}
	
	private static String toSnakeCase(String camelCase) {
		return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, camelCase);
	}

	
	public Integer getSellerId() {
		return sellerId;
	}
	
	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
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
