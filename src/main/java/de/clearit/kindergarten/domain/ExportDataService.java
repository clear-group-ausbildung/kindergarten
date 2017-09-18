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

  /**
   * Returns the {@link PayoffData} for the given {@link VendorBean}.
   * 
   * @param pVendor
   *          the vendor to get the data for.
   * 
   * @return {@link PayoffData} to create the receipt.
   */
  public static PayoffData getPayoffDataForVendor(VendorBean pVendor) {
    PurchaseService purchaseService = PurchaseService.getInstance();
    List<PurchaseBean> purchaseAllList = purchaseService.getAll();
    List<PurchaseBean> purchaseList = purchaseAllList.stream().filter(purchase -> purchase.getVendorNumber() == pVendor
        .getId()).collect(Collectors.toList());

    HashMap<Integer, Double> soldItemNumbersPricesMap = new HashMap<>();
    for (PurchaseBean purchase : purchaseList) {
      soldItemNumbersPricesMap.put(purchase.getItemNumber(), purchase.getItemPrice());
    }

    PayoffData payoffData = new PayoffData(pVendor.getId(), pVendor.getFirstName(), pVendor.getLastName(), pVendor
        .getPhoneNumber(), purchaseService.getVendorPayoutByPurchases(purchaseList), purchaseService
            .getKindergartenProfitByPurchases(purchaseList), purchaseService.getVendorPayoutByPurchases(purchaseList),
        purchaseService.getItemCountByPurchases(purchaseList), soldItemNumbersPricesMap);

    return payoffData;
  }

}
