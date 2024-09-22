package com.tag.backend.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SqlToExcel {

    public static void main(String[] args) {
        String url = "jdbc:mysql://backup-tags.b.aivencloud.com:19982/tags";
        String user = "avnadmin";
        String password = "AVNS_B_4-CPt4gIRMBH_LxmW";
        String[] queries = {
                "SELECT * FROM user"

        };

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Workbook workbook = new XSSFWorkbook()) {

            for (int i = 0; i < queries.length; i++) {
                String query = queries[i];
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery(query)) {

                    Sheet sheet = workbook.createSheet("Sheet" + (i + 1));
                    int rowNum = 0;

                    // Write header
                    Row headerRow = sheet.createRow(rowNum++);
                    int columnCount = rs.getMetaData().getColumnCount();
                    for (int col = 1; col <= columnCount; col++) {
                        headerRow.createCell(col - 1).setCellValue(rs.getMetaData().getColumnName(col));
                    }

                    // Write data
                    while (rs.next()) {
                        Row row = sheet.createRow(rowNum++);
                        for (int col = 1; col <= columnCount; col++) {
                            row.createCell(col - 1).setCellValue(rs.getString(col));
                        }
                    }
                }
            }

            try (FileOutputStream fileOut = new FileOutputStream("output.xlsx")) {
                workbook.write(fileOut);
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
