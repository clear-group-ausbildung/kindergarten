package de.clearit.kindergarten.domain;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
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

/**
 * The class ExportExcel
 *
 * @author Marco Jaeger, GfK External
 */
public class ExportExcel {

	private static final Logger LOGGER = Logger.getLogger(ExportExcel.class.getName());
	private static final ExportExcel INSTANCE = new ExportExcel();

	private ExportExcel() {
	}

	public static ExportExcel getInstance() {
		return INSTANCE;
	}

	private XSSFWorkbook wb;
	private XSSFSheet sheet;

	private XSSFCellStyle currencyStyle;
	private XSSFCellStyle currencyResultStyle;
	private XSSFCellStyle dateStyle;
	private XSSFCellStyle numberStyle;
	private XSSFCellStyle priceStyle;
	private XSSFCellStyle vendorHeaderStyle;

	/**
	 * Creates an receipt in excel for each existing vendor.
	 */
	public void createExcelForAllVendors() {
		List<VendorBean> vendorList = VendorService.getInstance().getAll();
		vendorList.forEach(this::createExcelForOneVendor);
	}

	/**
	 * Creates an receipt in excel for the given vendor.
	 * 
	 * @param pVendor
	 *            {@link VendorBean}
	 */
	public void createExcelForOneVendor(VendorBean pVendor) {
		try {
			wb = new XSSFWorkbook(new FileInputStream("./src/main/resources/abrechnung_template.xlsx"));
			sheet = wb.getSheetAt(0);
			createStyles();

			PayoffData payoffData = ExportDataService.getPayoffDataForVendor(pVendor);

			fillInPlaceholdersForOne(payoffData);

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

	/**
	 * Creates ONE receipt in excel for all given vendors.
	 * 
	 * @param pVendorList
	 *            list with {@link VendorBean} to create the receipt.
	 */
	public void createExcelForOneVendorWithMultipleVendorNumbers(List<VendorBean> pVendorList) {
		try {
			wb = new XSSFWorkbook(new FileInputStream("./src/main/resources/abrechnung_template.xlsx"));
			sheet = wb.getSheetAt(0);
			createStyles();

			List<PayoffData> payoffDataList = ExportDataService.getPayoffDataForVendors(pVendorList);

			fillInPlaceholdersForMultipleVendorNumbers(payoffDataList);

			FileOutputStream fileOut = new FileOutputStream(getDateiname(payoffDataList.get(0)));
			wb.write(fileOut);
			fileOut.close();
			wb.close();
		} catch (Exception e) {
			LOGGER.fine("Error creating Excel Export");
			e.printStackTrace();
		}
	}

	private void fillInPlaceholdersForOne(PayoffData pPayoffData) {
		Cell vendorCell = getCellForPlaceholder("$vendor");
		if (vendorCell != null) {
			vendorCell.setCellValue(pPayoffData.getVendorNumber());
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
			createSoldItemListInExcelForOne(pPayoffData.getSoldItemNumbersPricesMap(), soldItemListStartCell);
		}
	}
	
	private void createSoldItemListInExcelForOne(HashMap<Integer, Double> pSoldItemMap, Cell startCell) {
		if (pSoldItemMap.isEmpty()) {
			startCell.setCellValue("Keine Artikel verkauft");
		} else {
			createItemRows(startCell.getRowIndex(), startCell.getColumnIndex(), pSoldItemMap);
		}
	}

	private void fillInPlaceholdersForMultipleVendorNumbers(List<PayoffData> pPayoffDataList) {
		StringBuilder vendorNumbers = new StringBuilder();
		String firstName = "";
		String lastName = "";
		Integer totalSoldItems = 0;
		Double turnover = 0.0;
		Double profit = 0.0;
		Double payment = 0.0;
		HashMap<Integer, HashMap<Integer, Double>> soldItemMaps = new HashMap<>();

		Iterator<PayoffData> iter = pPayoffDataList.iterator();
		while (iter.hasNext()) {
			PayoffData payoffData = iter.next();
			totalSoldItems += payoffData.getTotalSoldItems();
			turnover += payoffData.getTurnover();
			profit += payoffData.getProfit();
			payment += payoffData.getPayment();
			soldItemMaps.put(payoffData.getVendorNumber(), payoffData.getSoldItemNumbersPricesMap());
			vendorNumbers.append(payoffData.getVendorNumber());
			if (iter.hasNext()) {
				vendorNumbers.append(" & ");
			} else {
				firstName = payoffData.getFirstName();
				lastName = payoffData.getLastName();
			}
		}

		Cell vendorCell = getCellForPlaceholder("$vendor");
		if (vendorCell != null) {
			vendorCell.setCellValue(vendorNumbers.toString());
		}

		Cell lastNameCell = getCellForPlaceholder("$lastName");
		if (lastNameCell != null) {
			lastNameCell.setCellValue(lastName);
		}

		Cell firstNameCell = getCellForPlaceholder("$firstName");
		if (firstNameCell != null) {
			firstNameCell.setCellValue(firstName);
		}

		Cell totalSoldItemsCell = getCellForPlaceholder("$totalSoldItems");
		if (totalSoldItemsCell != null) {
			totalSoldItemsCell.setCellValue(totalSoldItems);
		}

		Cell turnoverCell = getCellForPlaceholder("$turnover");
		if (turnoverCell != null) {
			turnoverCell.setCellValue(turnover);
		}

		Cell profitCell = getCellForPlaceholder("$profit");
		if (profitCell != null) {
			profitCell.setCellValue(profit);
		}

		Cell paymentCell = getCellForPlaceholder("$payment");
		if (paymentCell != null) {
			paymentCell.setCellValue(payment);
		}

		Cell dateCell = getCellForPlaceholder("$date");
		if (dateCell != null) {
			dateCell.setCellValue(new Date());
		}

		Cell soldItemListStartCell = getCellForPlaceholder("$soldItemListStart");
		if (soldItemListStartCell != null) {
			createSoldItemListInExcelForMultipleVendorNumbers(soldItemMaps, soldItemListStartCell);
		}
	}

	private void createSoldItemListInExcelForMultipleVendorNumbers(HashMap<Integer, HashMap<Integer, Double>> pSoldItemMaps,
			Cell startCell) {

		int rowCountGlobal = startCell.getRowIndex();
		int columnIndex = startCell.getColumnIndex();

		for (Integer mapsKey : pSoldItemMaps.keySet()) {
			HashMap<Integer, Double> soldItemMap = pSoldItemMaps.get(mapsKey);

			rowCountGlobal = createPlaceholderRow(rowCountGlobal, columnIndex, "");

			rowCountGlobal = createVendorHeaderRow(rowCountGlobal, columnIndex, mapsKey);

			if (soldItemMap.isEmpty()) {
				rowCountGlobal = createPlaceholderRow(rowCountGlobal, columnIndex, "Keine Artikel verkauft");
			} else {
				rowCountGlobal = createItemRows(rowCountGlobal, columnIndex, soldItemMap);
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
		vendorNumberCell.setCellValue("Verkäufer Nummer: " + pVendorNumber);
		vendorNumberCell.setCellStyle(vendorHeaderStyle);

		sheet.addMergedRegion(new CellRangeAddress(pRowCount, pRowCount, vendorNumberCell.getColumnIndex(),
				vendorNumberCell.getColumnIndex() + 1));

		return ++pRowCount;
	}

	private int createItemRows(int pRowCount, int pColIndex, HashMap<Integer, Double> pSoldItemMap) {
		int colIndexNumber = pColIndex;
		int colIndexPrice = colIndexNumber + 1;
		for (Integer key : pSoldItemMap.keySet()) {
			XSSFRow tempRow = sheet.createRow(pRowCount);

			XSSFCell numberCell = tempRow.createCell(colIndexNumber);
			numberCell.setCellValue(key);
			numberCell.setCellStyle(numberStyle);

			XSSFCell priceCell = tempRow.createCell(colIndexPrice);
			priceCell.setCellValue(pSoldItemMap.get(key));
			priceCell.setCellStyle(priceStyle);

			pRowCount++;
		}
		return ++pRowCount;
	}

	private Cell getCellForPlaceholder(String pPlaceholder) {
		for (Row row : sheet) {
			for (Cell cell : row) {
				if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
					if (cell.getRichStringCellValue().getString().trim().startsWith(pPlaceholder)) {
						return cell;
					}
				}
			}
		}
		return null;
	}

	private String getDateiname(PayoffData pPayoffData) {
		String folder = System.getProperty("user.home") + "/Basar Abrechnungen";
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
		dateiName.append(pPayoffData.getVendorNumber());
		dateiName.append("_");
		dateiName.append(pPayoffData.getLastName());
		dateiName.append(".xlsx");
		return dateiName.toString();
	}

	private void createStyles() {
		currencyStyle = wb.createCellStyle();
		currencyStyle.setDataFormat((short) 7);

		currencyResultStyle = wb.createCellStyle();
		currencyResultStyle.setDataFormat((short) 7);
		currencyResultStyle.setBorderBottom(BorderStyle.DOUBLE);

		dateStyle = wb.createCellStyle();
		CreationHelper createHelper = wb.getCreationHelper();
		dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd.MM.YYYY"));

		numberStyle = wb.createCellStyle();
		numberStyle.setAlignment(HorizontalAlignment.CENTER);
		numberStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		numberStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
		numberStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		numberStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		numberStyle.setBorderColor(BorderSide.BOTTOM, new XSSFColor(Color.decode("#CFDDFB")));
		numberStyle.setBorderColor(BorderSide.TOP, new XSSFColor(Color.decode("#CFDDFB")));
		numberStyle.setBorderColor(BorderSide.RIGHT, new XSSFColor(Color.decode("#CFDDFB")));
		numberStyle.setBorderColor(BorderSide.LEFT, new XSSFColor(Color.decode("#CFDDFB")));

		priceStyle = wb.createCellStyle();
		priceStyle.setDataFormat((short) 7);
		priceStyle.setAlignment(HorizontalAlignment.LEFT);
		priceStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		priceStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
		priceStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		priceStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		priceStyle.setBorderColor(BorderSide.BOTTOM, new XSSFColor(Color.decode("#CFDDFB")));
		priceStyle.setBorderColor(BorderSide.TOP, new XSSFColor(Color.decode("#CFDDFB")));
		priceStyle.setBorderColor(BorderSide.RIGHT, new XSSFColor(Color.decode("#CFDDFB")));
		priceStyle.setBorderColor(BorderSide.LEFT, new XSSFColor(Color.decode("#CFDDFB")));

		XSSFFont headerFont = wb.createFont();
		headerFont.setColor(new XSSFColor(Color.decode("#103FA6")));
		vendorHeaderStyle = wb.createCellStyle();
		vendorHeaderStyle.setFillForegroundColor(new XSSFColor(Color.decode("#CFDDFB")));
		vendorHeaderStyle.setFont(headerFont);

	}

}
