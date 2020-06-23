package com.digi.analytics.utility;

import com.digi.analytics.model.Role;
import com.digi.analytics.model.User;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ExcelHelper {

  public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
  static String[] HEADERs = {"userId", "name", "role", "emailId", "Error"};
  static String SHEET = "error";

  public static boolean hasExcelFormat(MultipartFile file) {

    if (!TYPE.equals(file.getContentType())) {
      return false;
    }

    return true;
  }

  public static List<User> excelToUsers(InputStream is) {
    try {
      Workbook workbook = new XSSFWorkbook(is);

      Sheet sheet = workbook.getSheet(SHEET);
      Iterator<Row> rows = sheet.iterator();
      List<User> users = new ArrayList<>();

      int rowNumber = 0;
      while (rows.hasNext()) {
        Row currentRow = rows.next();

        // skip header
        if (rowNumber == 0) {
          rowNumber++;
          continue;
        }

        Iterator<Cell> cellsInRow = currentRow.iterator();

        User user = new User();

        int cellIdx = 0;
        while (cellsInRow.hasNext()) {
          Cell currentCell = cellsInRow.next();

          switch (cellIdx) {
            case 0:
              user.setUserId(currentCell.getStringCellValue());
              break;

            case 1:
              user.setName(currentCell.getStringCellValue());
              break;

            case 2:
              Role role = new Role();
              role.setRoleName(currentCell.getStringCellValue());
              user.setRole(role);
              break;

            case 3:
              user.setEmailId(currentCell.getStringCellValue());
              break;

            default:
              break;
          }

          cellIdx++;
        }

        users.add(user);
      }

      workbook.close();

      return users;
    } catch (IOException e) {
      throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
    }
  }

  private static final String path = "/tmp/";
  public static ByteArrayInputStream genErrorExcelFile(List<User> users, String filePath) throws IOException {
    String[] COLUMNs = {"EmailId", "Name", "Roles", "Error"};

    File errorFile = new File(filePath);
    if(!errorFile.exists()){
      errorFile.mkdir();
    }

    try(
            Workbook workbook = new XSSFWorkbook("errors");

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            FileOutputStream outputStream = new FileOutputStream(errorFile);
    ){
      CreationHelper createHelper = workbook.getCreationHelper();

      Sheet sheet = workbook.createSheet("Error");

      Font headerFont = workbook.createFont();
      headerFont.setBold(true);
      headerFont.setColor(IndexedColors.BLUE.getIndex());

      CellStyle headerCellStyle = workbook.createCellStyle();
      headerCellStyle.setFont(headerFont);

      // Row for Header
      Row headerRow = sheet.createRow(0);

      // Header
      for (int col = 0; col < COLUMNs.length; col++) {
        Cell cell = headerRow.createCell(col);
        cell.setCellValue(COLUMNs[col]);
        cell.setCellStyle(headerCellStyle);
      }

      // CellStyle for Age
      CellStyle ageCellStyle = workbook.createCellStyle();
      ageCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#"));

      AtomicInteger rowIdex = new AtomicInteger(1);
      users.stream().forEach(user->createRow(sheet, sheet.createRow(rowIdex.getAndIncrement()),user));
      workbook.write(outputStream);
      workbook.write(out);
      return new ByteArrayInputStream(out.toByteArray());
    }
  }

  private static void createRow(Sheet sheet, Row row, User user) {

    row.createCell(0).setCellValue(user.getEmailId());
    row.createCell(1).setCellValue(user.getName());
    row.createCell(2).setCellValue(user.getRole().getRoleName());
    row.createCell(3).setCellValue(user.getError());
  }
}

