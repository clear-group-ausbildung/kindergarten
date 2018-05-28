package de.clearit.kindergarten.domain.print;


import de.clearit.kindergarten.domain.VendorBean;
import de.clearit.kindergarten.domain.VendorService;
import de.clearit.kindergarten.domain.export.ExportReceipt;
import de.clearit.kindergarten.domain.export.entity.PayoffDataReceipt;
import de.clearit.kindergarten.domain.export.service.ExportDataService;

import java.awt.*;
import java.io.File;
import java.util.List;

public class PrintService {

  private PrintService() {
    super();
  }

  public static void printAllExportedFiles() {
    List<VendorBean> vendorList = VendorService.getInstance().getAll();
    vendorList.forEach(PrintService::printFile);
  }

  private static void printFile(VendorBean vendor) {
    if (Desktop.isDesktopSupported()) {
      Desktop desktop = Desktop.getDesktop();
      if (desktop.isSupported(Desktop.Action.PRINT)) {
        try {
          PayoffDataReceipt payoffData = ExportDataService.getPayoffDataForVendor(vendor);
          File fileToPrint = new File(ExportReceipt.getInstance().getDateiname(payoffData));
          desktop.print(fileToPrint);
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    }
  }

}
