package de.clearit.kindergarten.domain.export.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import de.clearit.kindergarten.domain.PurchaseBean;
import de.clearit.kindergarten.domain.PurchaseService;
import de.clearit.kindergarten.domain.VendorBean;
import de.clearit.kindergarten.domain.VendorNumberBean;
import de.clearit.kindergarten.domain.VendorService;
import de.clearit.kindergarten.domain.export.entity.PayoffDataInternal;
import de.clearit.kindergarten.domain.export.entity.PayoffDataInternalVendor;
import de.clearit.kindergarten.domain.export.entity.PayoffDataReceipt;
import de.clearit.kindergarten.domain.export.entity.PayoffSoldItemsData;

/**
 * The service for the ExportData.
 *
 * @author Marco Jaeger
 */
public class ExportDataService {

  private ExportDataService() {
    super();
  }

  /**
   * Returns a {@link PayoffDataReceipt} for the given {@link VendorBean}.
   * 
   * @param pVendor
   *          the vendor to get the data for.
   * 
   * @return {@link PayoffDataReceipt} to create the receipts.
   */
  public static PayoffDataReceipt getPayoffDataForVendor(VendorBean pVendor) {
    PurchaseService purchaseService = PurchaseService.getInstance();
    List<PurchaseBean> purchaseAllList = purchaseService.getAll();

    ArrayList<Integer> vendorNumberList = new ArrayList<>();
    ArrayList<PayoffSoldItemsData> payoffSoldItemsDataList = new ArrayList<>();
    List<PurchaseBean> totalPurchaseList = new ArrayList<>();

    for (VendorNumberBean vendorNumberBean : pVendor.getVendorNumbers()) {
      int vendorNumber = vendorNumberBean.getVendorNumber();
      vendorNumberList.add(vendorNumber);

      List<PurchaseBean> purchaseList = purchaseAllList.stream().filter(purchase -> purchase.getVendorNumber().equals(
          vendorNumber)).collect(Collectors.toList());
      totalPurchaseList.addAll(purchaseList);

      HashMap<Integer, Double> soldItemNumbersPricesMap = new HashMap<>();
      for (PurchaseBean purchase : purchaseList) {
        soldItemNumbersPricesMap.put(purchase.getItemNumber(), purchase.getItemPrice().doubleValue());
      }

      payoffSoldItemsDataList.add(new PayoffSoldItemsData(vendorNumber, soldItemNumbersPricesMap, purchaseService
          .getItemSumByPurchases(purchaseList).doubleValue()));
    }

    return new PayoffDataReceipt(vendorNumberList, pVendor.getFirstName(), pVendor.getLastName(), purchaseService
        .getItemSumByPurchases(totalPurchaseList).doubleValue(), purchaseService.getKindergartenProfitByPurchases(
            totalPurchaseList).doubleValue(), purchaseService.getVendorPayoutByPurchases(totalPurchaseList)
                .doubleValue(), purchaseService.getItemCountByPurchases(totalPurchaseList), payoffSoldItemsDataList);
  }

  /**
   * Returns a {@link PayoffDataInternal} for the Internal Payoff.
   * 
   * @return {@link PayoffDataInternal} to create the internal payoff.
   */
  public static PayoffDataInternal getPayoffDataInternal() {
    PurchaseService purchaseService = PurchaseService.getInstance();
    List<PurchaseBean> purchaseAllList = purchaseService.getAll();

    Double totalSum = purchaseService.getItemSumByPurchases(purchaseAllList).doubleValue();
    Double totalProfit = purchaseService.getKindergartenProfitByPurchases(purchaseAllList).doubleValue();
    Double totalPayout = purchaseService.getVendorPayoutByPurchases(purchaseAllList).doubleValue();
    Integer totalItemCount = purchaseService.getItemCountByPurchases(purchaseAllList);

    ArrayList<PayoffDataInternalVendor> payoffDataInternalVendorList = new ArrayList<>();
    List<VendorBean> vendorList = VendorService.getInstance().getAll();
    for (VendorBean vendor : vendorList) {
      List<PurchaseBean> vendorPurchaseList = new ArrayList<>();
      for (VendorNumberBean vendorNumberBean : vendor.getVendorNumbers()) {
        int vendorNumber = vendorNumberBean.getVendorNumber();
        List<PurchaseBean> purchaseList = purchaseAllList.stream().filter(purchase -> purchase.getVendorNumber().equals(
            vendorNumber)).collect(Collectors.toList());
        vendorPurchaseList.addAll(purchaseList);
      }
      payoffDataInternalVendorList.add(new PayoffDataInternalVendor(vendor, purchaseService.getVendorPayoutByPurchases(
          vendorPurchaseList).doubleValue()));
    }

    return new PayoffDataInternal(totalSum, totalProfit, totalPayout, totalItemCount, payoffDataInternalVendorList);
  }
}
