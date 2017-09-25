package de.clearit.kindergarten.domain;

import java.util.ArrayList;
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

    List<PurchaseBean> purchaseList = purchaseAllList.stream().filter(purchase -> purchase.getVendorNumber().equals(
        pVendor.getVendorNumber())).collect(Collectors.toList());

    HashMap<Integer, Double> soldItemNumbersPricesMap = new HashMap<>();
    for (PurchaseBean purchase : purchaseList) {
      soldItemNumbersPricesMap.put(purchase.getItemNumber(), purchase.getItemPrice());
    }

    return new PayoffData(pVendor.getId(), pVendor.getFirstName(), pVendor.getLastName(), purchaseService
        .getItemSumByPurchases(purchaseList), purchaseService.getKindergartenProfitByPurchases(purchaseList),
        purchaseService.getVendorPayoutByPurchases(purchaseList), purchaseService.getItemCountByPurchases(purchaseList),
        soldItemNumbersPricesMap);
  }

  /**
   * Returns a list with {@link PayoffData} for the given list of
   * {@link VendorBean}s.
   * 
   * @param pVendorList
   *          the vendors to get the data for.
   * @return list with {@link PayoffData} to create the receipts.
   */
  public static List<PayoffData> getPayoffDataForVendors(List<VendorBean> pVendorList) {
    List<PayoffData> payoffDataList = new ArrayList<>();

    pVendorList.forEach(pVendor -> payoffDataList.add(getPayoffDataForVendor(pVendor)));

    return payoffDataList;
  }

}
