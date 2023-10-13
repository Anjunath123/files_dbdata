package com.dbdata.Service;

import com.dbdata.Entity.PaytmData;
import com.dbdata.Repository.PaymentDataRepository;
import com.dbdata.Util.ExcelUtil;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PaymentService {
    @Autowired
    public PaymentDataRepository paymentDataRepository;

    public void save(MultipartFile file) throws EncryptedDocumentException, IOException {
        List<PaytmData> excelDataList = new ArrayList<>();

        Workbook workbook = WorkbookFactory.create(file.getInputStream());

        int sheetIndex = 0; // Use the desired sheet index, change if needed

        if (workbook.getNumberOfSheets() > sheetIndex) {
            Sheet sheet = workbook.getSheetAt(sheetIndex);

            List<List<String>> rows = StreamSupport.stream(sheet.spliterator(), false)
                    .map(row -> {
                        List<String> rowData = StreamSupport
                                .stream(row.spliterator(), false)
                                .map(this::getCellStringValue)
                                .collect(Collectors.toList());

                        // Ensure there are at least 8 columns
                        while (rowData.size() < 8) {
                            rowData.add(null);
                        }

                        // Truncate 'debit' values to a specific size (e.g., 10 characters)
                        int debitIndex = 5; // Assuming 'debit' is at index 5
                        if (rowData.get(debitIndex) != null && rowData.get(debitIndex).length() > 10) {
                            rowData.set(debitIndex, rowData.get(debitIndex).substring(0, 10));
                        }

                        return rowData;
                    })
                    .collect(Collectors.toList());

            for (List<String> row : rows) {
                PaytmData excelData = new PaytmData();
                excelData.setActivity(row.get(0));
                excelData.setSource(row.get(1));
                excelData.setWalletTxnID(row.get(2));
                excelData.setComment(row.get(3));
                excelData.setTransactionBreakup(row.get(4));
                excelData.setDebit(row.get(5));
                excelData.setCredit(row.get(6));
                excelData.setSource(row.get(7));
                excelDataList.add(excelData);
            }

            paymentDataRepository.saveAll(excelDataList);
        } else {
            // Handle the case where the specified sheet index does not exist
        }
    }

    private String getCellStringValue(Cell cell) {
        CellType cellType = cell.getCellType();

        if (cellType == CellType.STRING) {
            return cell.getStringCellValue();
        } else if (cellType == CellType.NUMERIC) {
            return String.valueOf(cell.getNumericCellValue());
        } else if (cellType == CellType.BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        }

        return null;
    }
    public ByteArrayInputStream getDataDownloaded() throws IOException {
        List<PaytmData> paymentData = paymentDataRepository.findAll();
        ByteArrayInputStream data = ExcelUtil.dataToExcel(paymentData);
        return data;

    }
}
