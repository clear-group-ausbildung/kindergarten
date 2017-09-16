package de.clearit.kindergarten.domain;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The service for the ExportData.
 *
 * @author Marco Jaeger
 */
public class ExportDataService {

	public static PayoffData getAbrechnungForVendor(VendorBean pVendor) {
		List<PurchaseBean> purchaseAllList = PurchaseService.getInstance().getAll();
		System.out.println(purchaseAllList.size());
		System.out.println(purchaseAllList.get(0).getVendorId());
		List<PurchaseBean> purchaseList = purchaseAllList.stream()
				.filter(purchase -> purchase.getVendorId() == pVendor.getId()).collect(Collectors.toList());
		System.out.println(purchaseList.size());

		HashMap<Integer, Double> soldItemNumbersPricesMap = new HashMap<>();
		Double turnover = 0.0;
		Double profit = 0.0;
		Double payment = 0.0;
		Integer totalSoldItems = 0;
		for (PurchaseBean purchase : purchaseList) {
			turnover += purchase.getTotalPrice();
			profit += purchase.getProfit();
			payment += purchase.getPayment();
			totalSoldItems += purchase.getItemQuantity();
			soldItemNumbersPricesMap.put(purchase.getItemNumber(), purchase.getTotalPrice());
		}

		PayoffData payoffData = new PayoffData(pVendor.getId(), pVendor.getFirstName(), pVendor.getLastName(),
				pVendor.getPhoneNumber(), turnover, profit, payment, totalSoldItems, soldItemNumbersPricesMap);

		return payoffData;
	}

}
