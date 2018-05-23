package de.clearit.kindergarten.domain.export;

import java.awt.Color;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.renderer.DocumentRenderer;
import org.apache.poi.hssf.converter.ExcelToHtmlConverter;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellBorder.BorderSide;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.clearit.kindergarten.domain.VendorBean;
import de.clearit.kindergarten.domain.export.entity.PayoffDataReceipt;
import de.clearit.kindergarten.domain.export.entity.PayoffSoldItemsData;
import de.clearit.kindergarten.domain.export.service.ExportDataService;
import org.w3c.dom.Document;

/**
 * The class ExportReceipt
 *
 * @author Marco Jaeger, GfK External
 */
public class ExportReceipt {

  private static final String PALE_BLUE = "#CFDDFB";
  private static final Logger LOGGER = LoggerFactory.getLogger(ExportExcel.class);
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

  private PdfDocument pdfDocument;

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

      String fileOutName = getDateiname(payoffData);
      FileOutputStream fileOut = new FileOutputStream(fileOutName);
      wb.write(fileOut);
      fileOut.close();
      wb.close();
      createPDF(payoffData);
    } catch (FileNotFoundException e) {
      LOGGER.debug("Error - Excel Template not found");
      LOGGER.error(e.getMessage());
    } catch (IOException e) {
      LOGGER.debug("Error Excel Export");
      LOGGER.error(e.getMessage());
    }
  }

  private void createPDF(PayoffDataReceipt payoffData) throws IOException {
    PdfReader reader = new PdfReader("./abrechnung_template.pdf");
    PdfWriter writer = new PdfWriter(getDateiname(payoffData).replace("xlsx", "pdf"));
    pdfDocument = new PdfDocument(reader, writer);

    fillInPDFPlaceholders(payoffData);

    reader.close();
    writer.close();
    pdfDocument.close();
  }

  private void createPDF(String fileOutName) throws IOException, ParserConfigurationException, TransformerException {
    Document doc = ExcelToHtmlConverter.process(new File(fileOutName));

    DOMSource domSource = new DOMSource(doc);
    StreamResult streamResult = new StreamResult(new File("./abrechnung.html"));

    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer serializer = transformerFactory.newTransformer();
    serializer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
    serializer.setOutputProperty(OutputKeys.INDENT, "no");
    serializer.setOutputProperty(OutputKeys.METHOD, "html");
    serializer.transform(domSource, streamResult);
  }

  private void fillInPDFPlaceholders(PayoffDataReceipt pPayoffData) {
    com.itextpdf.layout.Document doc = new com.itextpdf.layout.Document(pdfDocument);

    PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDocument, true);
    Map<String, PdfFormField> fields = form.getFormFields();

    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    fields.get("date").setValue(now.format(formatter));
    fields.get("vendorID").setValue(pPayoffData.getVendorNumberList().stream().map(Object::toString).collect(Collectors
      .joining(", ")));
    fields.get("lastName").setValue(pPayoffData.getLastName());
    fields.get("firstName").setValue(pPayoffData.getFirstName());
    fields.get("totalSoldItems").setValue(String.valueOf(pPayoffData.getTotalSoldItems()));
    fields.get("turnover").setValue(formatCurrency(pPayoffData.getTurnover()));
    fields.get("profit").setValue(formatCurrency(pPayoffData.getProfit()));
    fields.get("payment").setValue(formatCurrency(pPayoffData.getPayment()));
    fields.get("soldItemListStart").setValue("");
    form.flattenFields();

    Table soldItemsTable = createPDFSoldItemList(pPayoffData.getPayoffSoldItemsData());

    doc.setRenderer(new DocumentRenderer(doc) {
      @Override
      protected LayoutArea updateCurrentArea(LayoutResult overflowResult) {
        LayoutArea area = super.updateCurrentArea(overflowResult);
        if (area.getPageNumber() == 1) {
          area.getBBox().decreaseHeight(316);
        }
        return area;
      }
    });

    doc.add(soldItemsTable);
    doc.close();
  }

  private String formatCurrency(Double amountOfMoney) {
    return String.format("%.2f", amountOfMoney) + " \u20AC";
  }

  private Table createPDFSoldItemList(List<PayoffSoldItemsData> pPayoffSoldItemDataList) {
    Table table = new Table(new float[]{1, 15});
    table.setBorder(Border.NO_BORDER);
    for (PayoffSoldItemsData payoffSoldItemData : pPayoffSoldItemDataList) {
      table.addCell(getFormattedCell(1, 2, ""));
      table.addCell(getFormattedCell(1, 2, "Verk" + "\u00E4" + "ufer Nummer: " + payoffSoldItemData.getVendorNumber()));

      if (payoffSoldItemData.getSoldItemNumbersPricesMap().isEmpty()) {
        table.addCell(getFormattedCell(1, 2, "Keine Artikel verkauft"));
      } else {
        createItemRows(payoffSoldItemData, table);
      }
    }
    return table;
  }

  private void createItemRows(PayoffSoldItemsData pPayoffSoldItemData, Table table) {
    Map<Integer, Double> soldItemMap = pPayoffSoldItemData.getSoldItemNumbersPricesMap();
    for (Entry<Integer, Double> entry : soldItemMap.entrySet()) {
      table.addCell(getFormattedCell(String.valueOf(entry.getKey())));
      table.addCell(getFormattedCell(formatCurrency(entry.getValue())));
      table.addCell(getFormattedCell("Summe:"));
      table.addCell(getFormattedCell(formatCurrency(pPayoffSoldItemData.getSoldItemSum())));
    }
  }

  private com.itextpdf.layout.element.Cell getFormattedCell(String content) {
    return getFormattedCell(1, 1, content);
  }

  private com.itextpdf.layout.element.Cell getFormattedCell(int rowspan, int colspan, String content) {
    com.itextpdf.layout.element.Cell cell = new com.itextpdf.layout.element.Cell(rowspan,colspan);
    cell.add(new Paragraph(content));
    cell.setBorder(Border.NO_BORDER);
    return cell;
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

  public String getDateiname(PayoffDataReceipt pPayoffData) {
    String folder = System.getProperty("user.home") + "/Desktop/Basar Abrechnungen";
    StringBuilder dateiName = new StringBuilder();
    if (!Files.isDirectory(Paths.get(folder))) {
      try {
        Files.createDirectory(Paths.get(folder));
        dateiName.append(folder);
        dateiName.append("/");
      } catch (IOException e) {
        LOGGER.error(e.getMessage());
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
    numberStyle.setBorderBottom(BorderStyle.THIN);
    numberStyle.setBorderTop(BorderStyle.THIN);
    numberStyle.setBorderRight(BorderStyle.THIN);
    numberStyle.setBorderLeft(BorderStyle.THIN);
    numberStyle.setBorderColor(BorderSide.BOTTOM, new XSSFColor(Color.decode(PALE_BLUE)));
    numberStyle.setBorderColor(BorderSide.TOP, new XSSFColor(Color.decode(PALE_BLUE)));
    numberStyle.setBorderColor(BorderSide.RIGHT, new XSSFColor(Color.decode(PALE_BLUE)));
    numberStyle.setBorderColor(BorderSide.LEFT, new XSSFColor(Color.decode(PALE_BLUE)));

    priceStyle = wb.createCellStyle();
    priceStyle.setDataFormat((short) 7);
    priceStyle.setAlignment(HorizontalAlignment.LEFT);
    priceStyle.setBorderBottom(BorderStyle.THIN);
    priceStyle.setBorderTop(BorderStyle.THIN);
    priceStyle.setBorderRight(BorderStyle.THIN);
    priceStyle.setBorderLeft(BorderStyle.THIN);
    priceStyle.setBorderColor(BorderSide.BOTTOM, new XSSFColor(Color.decode(PALE_BLUE)));
    priceStyle.setBorderColor(BorderSide.TOP, new XSSFColor(Color.decode(PALE_BLUE)));
    priceStyle.setBorderColor(BorderSide.RIGHT, new XSSFColor(Color.decode(PALE_BLUE)));
    priceStyle.setBorderColor(BorderSide.LEFT, new XSSFColor(Color.decode(PALE_BLUE)));

    sumStyle = wb.createCellStyle();
    sumStyle.setAlignment(HorizontalAlignment.RIGHT);
    sumStyle.setBorderBottom(BorderStyle.THIN);
    sumStyle.setBorderTop(BorderStyle.THIN);
    sumStyle.setBorderRight(BorderStyle.THIN);
    sumStyle.setBorderLeft(BorderStyle.THIN);
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
