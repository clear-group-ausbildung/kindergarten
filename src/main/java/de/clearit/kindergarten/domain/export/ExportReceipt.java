package de.clearit.kindergarten.domain.export;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellBorder.BorderSide;

import de.clearit.kindergarten.domain.VendorBean;
import de.clearit.kindergarten.domain.export.entity.PayoffDataReceipt;
import de.clearit.kindergarten.domain.export.entity.PayoffSoldItemsData;
import de.clearit.kindergarten.domain.export.service.ExportDataService;

/**
 * The class ExportReceipt
 *
 * @author Marco Jaeger, GfK External
 */
public class ExportReceipt {

  private static final String PALE_BLUE = "#CFDDFB";
  private static final Logger LOGGER = Logger.getLogger(ExportExcel.class.getName());
  private static final ExportReceipt INSTANCE = new ExportReceipt();

  private ExportReceipt() {
  }

  public static ExportReceipt getInstance() {
    return INSTANCE;
  }

  private XSSFWorkbook wb;
  private XSSFSheet sheet;

  private XSSFCellStyle numberStyle;
  private XSSFCellStyle priceStyle;
  private XSSFCellStyle sumStyle;
  private XSSFCellStyle vendorHeaderStyle;

  /**
   * Creates an receipt in excel for the given vendor.
   * 
   * @param pVendor
   *          {@link VendorBean}
   */
  public void createReceipt(VendorBean pVendor) {
    try {
      wb = new XSSFWorkbook(new FileInputStream("./abrechnung_template.xlsx"));
      sheet = wb.getSheetAt(0);
      createStyles();

      PayoffDataReceipt payoffData = ExportDataService.getPayoffDataForVendor(pVendor);
      fillInPlaceholders(payoffData);

      FileOutputStream fileOut = new FileOutputStream(getDateiname(payoffData));
      wb.write(fileOut);
      fileOut.close();
      wb.close();
    } catch (FileNotFoundException e) {
      LOGGER.fine("Error - Excel Template not found");
      e.printStackTrace();
    } catch (IOException e) {
      LOGGER.fine("Error Exel Export");
      e.printStackTrace();
    }
  }

  private void fillInPlaceholders(PayoffDataReceipt pPayoffData) {
    Cell vendorCell = getCellForPlaceholder("$vendor");
    if (vendorCell != null) {
      vendorCell.setCellValue(pPayoffData.getVendorNumberList().stream().map(Object::toString).collect(Collectors
          .joining(", ")));
    }

    Cell lastNameCell = getCellForPlaceholder("$lastName");
    if (lastNameCell != null) {
      lastNameCell.setCellValue(pPayoffData.getLastName());
    }

    Cell firstNameCell = getCellForPlaceholder("$firstName");
    if (firstNameCell != null) {
      firstNameCell.setCellValue(pPayoffData.getFirstName());
    }

    Cell totalSoldItemsCell = getCellForPlaceholder("$totalSoldItems");
    if (totalSoldItemsCell != null) {
      totalSoldItemsCell.setCellValue(pPayoffData.getTotalSoldItems());
    }

    Cell turnoverCell = getCellForPlaceholder("$turnover");
    if (turnoverCell != null) {
      turnoverCell.setCellValue(pPayoffData.getTurnover());
    }

    Cell profitCell = getCellForPlaceholder("$profit");
    if (profitCell != null) {
      profitCell.setCellValue(pPayoffData.getProfit());
    }

    Cell paymentCell = getCellForPlaceholder("$payment");
    if (paymentCell != null) {
      paymentCell.setCellValue(pPayoffData.getPayment());
    }

    Cell dateCell = getCellForPlaceholder("$date");
    if (dateCell != null) {
      dateCell.setCellValue(new Date());
    }

    Cell soldItemListStartCell = getCellForPlaceholder("$soldItemListStart");
    if (soldItemListStartCell != null) {
      createSoldItemList(pPayoffData.getPayoffSoldItemsData(), soldItemListStartCell);
    }
  }

  private void createSoldItemList(List<PayoffSoldItemsData> pPayoffSoldItemDataList, Cell pStartCell) {

    int rowCountGlobal = pStartCell.getRowIndex();
    int columnIndex = pStartCell.getColumnIndex();

    for (PayoffSoldItemsData payoffSoldItemData : pPayoffSoldItemDataList) {

      rowCountGlobal = createPlaceholderRow(rowCountGlobal, columnIndex, "");
      rowCountGlobal = createVendorHeaderRow(rowCountGlobal, columnIndex, payoffSoldItemData.getVendorNumber());
      if (payoffSoldItemData.getSoldItemNumbersPricesMap().isEmpty()) {
        rowCountGlobal = createPlaceholderRow(rowCountGlobal, columnIndex, "Keine Artikel verkauft");
      } else {
        rowCountGlobal = createItemRows(rowCountGlobal, columnIndex, payoffSoldItemData);
      }
    }
  }

  private int createPlaceholderRow(int pRowCount, int pColIndex, String pValue) {
    XSSFRow tempRow = sheet.createRow(pRowCount);
    if (pValue != null && !pValue.equals("")) {
      tempRow.createCell(pColIndex).setCellValue(pValue);
    }

    return ++pRowCount;
  }

  private int createVendorHeaderRow(int pRowCount, int pColIndex, Integer pVendorNumber) {
    XSSFRow vendorRow = sheet.createRow(pRowCount);
    XSSFCell vendorNumberCell = vendorRow.createCell(pColIndex);
    vendorNumberCell.setCellValue("Verk" + "\u00E4" + "ufer Nummer: " + pVendorNumber);
    vendorNumberCell.setCellStyle(vendorHeaderStyle);

    sheet.addMergedRegion(new CellRangeAddress(pRowCount, pRowCount, vendorNumberCell.getColumnIndex(), vendorNumberCell
        .getColumnIndex() + 1));

    return ++pRowCount;
  }

  private int createItemRows(int pRowCount, int pColIndex, PayoffSoldItemsData pPayoffSoldItemData) {
    int colIndexPrice = pColIndex + 1;
    Map<Integer, Double> soldItemMap = pPayoffSoldItemData.getSoldItemNumbersPricesMap();
    for (Entry<Integer, Double> entry : soldItemMap.entrySet()) {
      XSSFRow tempRow = sheet.createRow(pRowCount);

      XSSFCell numberCell = tempRow.createCell(pColIndex);
      numberCell.setCellValue(entry.getKey());
      numberCell.setCellStyle(numberStyle);

      XSSFCell priceCell = tempRow.createCell(colIndexPrice);
      priceCell.setCellValue(soldItemMap.get(entry.getKey()));
      priceCell.setCellStyle(priceStyle);

      pRowCount++;
    }

    XSSFRow sumRow = sheet.createRow(pRowCount);

    XSSFCell labelCell = sumRow.createCell(pColIndex);
    labelCell.setCellValue("Summe:");
    labelCell.setCellStyle(sumStyle);

    XSSFCell priceCell = sumRow.createCell(colIndexPrice);
    priceCell.setCellValue(pPayoffSoldItemData.getSoldItemSum());
    priceCell.setCellStyle(priceStyle);

    return ++pRowCount;
  }

  private Cell getCellForPlaceholder(String pPlaceholder) {
    for (Row row : sheet) {
      for (Cell cell : row) {
        if (cell.getCellType() == Cell.CELL_TYPE_STRING && cell.getRichStringCellValue().getString().trim().startsWith(
            pPlaceholder)) {
          return cell;
        }
      }
    }
    return null;
  }

  private String getDateiname(PayoffDataReceipt pPayoffData) {
    String folder = System.getProperty("user.home") + "/Desktop/Basar Abrechnungen";
    StringBuilder dateiName = new StringBuilder();
    if (!Files.isDirectory(Paths.get(folder))) {
      try {
        Files.createDirectory(Paths.get(folder));
        dateiName.append(folder);
        dateiName.append("/");
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      dateiName.append(folder);
      dateiName.append("/");
    }
    dateiName.append("Abrechnung_");
    dateiName.append(pPayoffData.getVendorNumberList().get(0));
    dateiName.append("_");
    dateiName.append(pPayoffData.getLastName());
    dateiName.append(".xlsx");
    return dateiName.toString();
  }

  private void createStyles() {
    XSSFFont headerFont = wb.createFont();
    headerFont.setColor(new XSSFColor(Color.decode("#103FA6")));

    numberStyle = wb.createCellStyle();
    numberStyle.setAlignment(HorizontalAlignment.CENTER);
    numberStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
    numberStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
    numberStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
    numberStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
    numberStyle.setBorderColor(BorderSide.BOTTOM, new XSSFColor(Color.decode(PALE_BLUE)));
    numberStyle.setBorderColor(BorderSide.TOP, new XSSFColor(Color.decode(PALE_BLUE)));
    numberStyle.setBorderColor(BorderSide.RIGHT, new XSSFColor(Color.decode(PALE_BLUE)));
    numberStyle.setBorderColor(BorderSide.LEFT, new XSSFColor(Color.decode(PALE_BLUE)));

    priceStyle = wb.createCellStyle();
    priceStyle.setDataFormat((short) 7);
    priceStyle.setAlignment(HorizontalAlignment.LEFT);
    priceStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
    priceStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
    priceStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
    priceStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
    priceStyle.setBorderColor(BorderSide.BOTTOM, new XSSFColor(Color.decode(PALE_BLUE)));
    priceStyle.setBorderColor(BorderSide.TOP, new XSSFColor(Color.decode(PALE_BLUE)));
    priceStyle.setBorderColor(BorderSide.RIGHT, new XSSFColor(Color.decode(PALE_BLUE)));
    priceStyle.setBorderColor(BorderSide.LEFT, new XSSFColor(Color.decode(PALE_BLUE)));

    sumStyle = wb.createCellStyle();
    sumStyle.setAlignment(HorizontalAlignment.RIGHT);
    sumStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
    sumStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
    sumStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
    sumStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
    sumStyle.setBorderColor(BorderSide.BOTTOM, new XSSFColor(Color.decode(PALE_BLUE)));
    sumStyle.setBorderColor(BorderSide.TOP, new XSSFColor(Color.decode(PALE_BLUE)));
    sumStyle.setBorderColor(BorderSide.RIGHT, new XSSFColor(Color.decode(PALE_BLUE)));
    sumStyle.setBorderColor(BorderSide.LEFT, new XSSFColor(Color.decode(PALE_BLUE)));
    sumStyle.setFont(headerFont);

    vendorHeaderStyle = wb.createCellStyle();
    vendorHeaderStyle.setFillForegroundColor(new XSSFColor(Color.decode(PALE_BLUE)));
    vendorHeaderStyle.setFont(headerFont);
  }

}
