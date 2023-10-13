package com.dbdata.Util;

import com.dbdata.Entity.PaytmData;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelUtil {
    public static String HEADER[]={"id","activity","source","WalletTxnID","comment","debit","credit","transactionBreakup","status"};
    public static String SHEET_NAME = "sheetForProductData";
    public static ByteArrayInputStream dataToExcel(List<PaytmData> paymentList) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            Sheet sheet = workbook.createSheet(SHEET_NAME);
            Row row = sheet.createRow(0);

            for (int i = 0; i < HEADER.length; i++) {

                Cell cell = row.createCell(i);
                cell.setCellValue(HEADER[i]);
            }
            int rowIndex = 1;
            for (PaytmData p : paymentList) {
                Row row1 = sheet.createRow(rowIndex);
                rowIndex++;
                row1.createCell(0).setCellValue(p.getId());
                row1.createCell(1).setCellValue(p.getActivity());
                row1.createCell(2).setCellValue(p.getSource());
                row1.createCell(3).setCellValue(p.getWalletTxnID());
                row1.createCell(4).setCellValue(p.getComment());
                row1.createCell(5).setCellValue(p.getDebit());
                row1.createCell(6).setCellValue(p.getCredit());
                row1.createCell(7).setCellValue(p.getTransactionBreakup());
                row1.createCell(8).setCellValue(p.getStatus());

            }
            workbook.write(byteArrayOutputStream);
            return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            workbook.close();
            byteArrayOutputStream.close();
        }

    }

}


