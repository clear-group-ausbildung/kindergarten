package de.clearit.kindergarten.domain.export;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellBorder.BorderSide;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.clearit.kindergarten.domain.export.entity.PayoffDataInternal;
import de.clearit.kindergarten.domain.export.entity.PayoffDataInternalVendor;
import de.clearit.kindergarten.domain.export.service.ExportDataService;

/**
 * The class ExportInternalPayoff
 *
 * @author Marco Jaeger, GfK External
 */
public class ExportInternalPayoff {

  private static final Logger LOGGER = LoggerFactory.getLogger(ExportExcel.class);
  private static final ExportInternalPayoff INSTANCE = new ExportInternalPayoff();

  private ExportInternalPayoff() {
  }

  public static ExportInternalPayoff getInstance() {
    return INSTANCE;
  }

  private XSSFWorkbook wb;
  private XSSFSheet sheet;

  private XSSFCellStyle priceStyle;
  private XSSFCellStyle labelStyle;
  private XSSFCellStyle textStyle;

  /**
   * Creates an receipt in excel for the given vendor.
   *
   */
  public void createInternalPayoff() {
    try {
      wb = new XSSFWorkbook(new FileInputStream("./abrechnung_intern_template.xlsx"));
      sheet = wb.getSheetAt(0);
      createStyles();

      PayoffDataInternal payoffDataInternal = ExportDataService.getPayoffDataInternal();
      fillInPlaceholders(payoffDataInternal);

      FileOutputStream fileOut = new FileOutputStream(getDateiname());
      wb.write(fileOut);
      fileOut.close();
      wb.close();
    } catch (FileNotFoundException e) {
      LOGGER.debug("Error - Excel Template not found");
      LOGGER.error(e.getMessage());
    } catch (IOException e) {
      LOGGER.debug("Error Exel Export");
      LOGGER.error(e.getMessage());
    }
  }

  private void fillInPlaceholders(PayoffDataInternal pPayoffDataInternal) {
    Cell totalSoldItemsCell = getCellForPlaceholder("$totalSoldItems");
    if (totalSoldItemsCell != null) {
      totalSoldItemsCell.setCellValue(pPayoffDataInternal.getTotalSoldItems());
    }

    Cell turnoverCell = getCellForPlaceholder("$turnover");
    if (turnoverCell != null) {
      turnoverCell.setCellValue(pPayoffDataInternal.getTurnover());
    }

    Cell profitCell = getCellForPlaceholder("$profit");
    if (profitCell != null) {
      profitCell.setCellValue(pPayoffDataInternal.getProfit());
    }

    Cell paymentCell = getCellForPlaceholder("$payment");
    if (paymentCell != null) {
      paymentCell.setCellValue(pPayoffDataInternal.getPayment());
    }

    Cell dateCell = getCellForPlaceholder("$date");
    if (dateCell != null) {
      dateCell.setCellValue(new Date());
    }

    Cell vendorListStartCell = getCellForPlaceholder("$vendorListStart");
    if (vendorListStartCell != null) {
      createVendorList(pPayoffDataInternal, vendorListStartCell);
    }
  }

  private void createVendorList(PayoffDataInternal pPayoffDataInternal, Cell pStartCell) {
    int rowCountGlobal = pStartCell.getRowIndex();
    int labelColumnIndex = pStartCell.getColumnIndex();
    int valueColumnIndex = labelColumnIndex + 1;

    for (PayoffDataInternalVendor payoffDataInternalVendor : pPayoffDataInternal.getPayoffDataInternalVendor()) {
      rowCountGlobal = createPlaceholderRow(rowCountGlobal, labelColumnIndex, "");

      XSSFRow nameRow = sheet.createRow(rowCountGlobal);
      XSSFCell nameLabelCell = nameRow.createCell(labelColumnIndex);
      nameLabelCell.setCellValue("Name");
      nameLabelCell.setCellStyle(labelStyle);
      XSSFCell nameValueCell = nameRow.createCell(valueColumnIndex);
      nameValueCell.setCellValue(payoffDataInternalVendor.getVendor().getLastName());
      nameValueCell.setCellStyle(textStyle);
      rowCountGlobal++;

      XSSFRow firstnameRow = sheet.createRow(rowCountGlobal);
      XSSFCell firstnameLabelCell = firstnameRow.createCell(labelColumnIndex);
      firstnameLabelCell.setCellValue("Vorname");
      firstnameLabelCell.setCellStyle(labelStyle);
      XSSFCell firstnameValueCell = firstnameRow.createCell(valueColumnIndex);
      firstnameValueCell.setCellValue(payoffDataInternalVendor.getVendor().getFirstName());
      firstnameValueCell.setCellStyle(textStyle);
      rowCountGlobal++;

      XSSFRow vendorNumberRow = sheet.createRow(rowCountGlobal);
      XSSFCell vendorNumberLabelCell = vendorNumberRow.createCell(labelColumnIndex);
      vendorNumberLabelCell.setCellValue("Nummer(n)");
      vendorNumberLabelCell.setCellStyle(labelStyle);
      XSSFCell vendorNumberValueCell = vendorNumberRow.createCell(valueColumnIndex);
      vendorNumberValueCell.setCellValue(payoffDataInternalVendor.getVendor().getVendorNumbers().stream().map(
          vendorNumber -> String.valueOf(vendorNumber.getVendorNumber())).collect(Collectors.joining(", ")));
      vendorNumberValueCell.setCellStyle(textStyle);
      rowCountGlobal++;

      XSSFRow paymentRow = sheet.createRow(rowCountGlobal);
      XSSFCell paymentLabelCell = paymentRow.createCell(labelColumnIndex);
      paymentLabelCell.setCellValue("Auszahlungsbetrag");
      paymentLabelCell.setCellStyle(labelStyle);
      XSSFCell paymentValueCell = paymentRow.createCell(valueColumnIndex);
      paymentValueCell.setCellValue(payoffDataInternalVendor.getVendorPayment());
      paymentValueCell.setCellStyle(priceStyle);
      rowCountGlobal++;
    }
  }

  private int createPlaceholderRow(int pRowCount, int pColIndex, String pValue) {
    XSSFRow tempRow = sheet.createRow(pRowCount);
    if (pValue != null && !pValue.equals("")) {
      tempRow.createCell(pColIndex).setCellValue(pValue);
    }

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

  private String getDateiname() {
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
    dateiName.append("000_Abrechnung_intern");
    dateiName.append(".xlsx");
    return dateiName.toString();
  }

  private void createStyles() {
    XSSFFont headerFont = wb.createFont();
    headerFont.setColor(new XSSFColor(Color.decode("#103FA6")));

    priceStyle = wb.createCellStyle();
    priceStyle.setDataFormat((short) 7);
    priceStyle.setAlignment(HorizontalAlignment.LEFT);
    priceStyle.setBorderBottom(BorderStyle.THIN);
    priceStyle.setBorderTop(BorderStyle.THIN);
    priceStyle.setBorderRight(BorderStyle.THIN);
    priceStyle.setBorderLeft(BorderStyle.THIN);
    priceStyle.setBorderColor(BorderSide.BOTTOM, new XSSFColor(Color.decode("#CFDDFB")));
    priceStyle.setBorderColor(BorderSide.TOP, new XSSFColor(Color.decode("#CFDDFB")));
    priceStyle.setBorderColor(BorderSide.RIGHT, new XSSFColor(Color.decode("#CFDDFB")));
    priceStyle.setBorderColor(BorderSide.LEFT, new XSSFColor(Color.decode("#CFDDFB")));

    labelStyle = wb.createCellStyle();
    labelStyle.setBorderBottom(BorderStyle.THIN);
    labelStyle.setBorderTop(BorderStyle.THIN);
    labelStyle.setBorderRight(BorderStyle.THIN);
    labelStyle.setBorderLeft(BorderStyle.THIN);
    labelStyle.setBorderColor(BorderSide.BOTTOM, new XSSFColor(Color.decode("#CFDDFB")));
    labelStyle.setBorderColor(BorderSide.TOP, new XSSFColor(Color.decode("#CFDDFB")));
    labelStyle.setBorderColor(BorderSide.RIGHT, new XSSFColor(Color.decode("#CFDDFB")));
    labelStyle.setBorderColor(BorderSide.LEFT, new XSSFColor(Color.decode("#CFDDFB")));
    labelStyle.setFont(headerFont);

    textStyle = wb.createCellStyle();
    textStyle.setBorderBottom(BorderStyle.THIN);
    textStyle.setBorderTop(BorderStyle.THIN);
    textStyle.setBorderRight(BorderStyle.THIN);
    textStyle.setBorderLeft(BorderStyle.THIN);
    textStyle.setBorderColor(BorderSide.BOTTOM, new XSSFColor(Color.decode("#CFDDFB")));
    textStyle.setBorderColor(BorderSide.TOP, new XSSFColor(Color.decode("#CFDDFB")));
    textStyle.setBorderColor(BorderSide.RIGHT, new XSSFColor(Color.decode("#CFDDFB")));
    textStyle.setBorderColor(BorderSide.LEFT, new XSSFColor(Color.decode("#CFDDFB")));
  }

}
