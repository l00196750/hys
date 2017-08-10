package com.hys.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.common.collect.Table;

public class ExcelWriter {

    public String writer(Table<Integer, Integer, String> table) throws IOException {
        Workbook workbook = new XSSFWorkbook();

        // 得到一个POI的工具类
        CreationHelper createHelper = workbook.getCreationHelper();

        // 在Excel工作簿中建一工作表，其名为缺省值, 也可以指定Sheet名称
        Sheet sheet = workbook.createSheet();

        // 单元格样式
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 9);
        font.setColor(Font.COLOR_NORMAL);

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        cellStyle.setVerticalAlignment(VerticalAlignment.TOP);
        cellStyle.setWrapText(true);

        for (Integer rownum : table.rowKeySet()) {
            // 创建行
            Row row = sheet.createRow(rownum);

            Map<Integer, String> rowData = table.row(rownum);
            Iterator<Entry<Integer, String>> iterator = rowData.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<Integer, String> next = iterator.next();

                Cell cell = row.createCell(next.getKey());
                cell.setCellStyle(cellStyle);
                cell.setCellValue(createHelper.createRichTextString(next.getValue()));
            }
        }

        // 设置列宽
        for (Integer column : table.columnKeySet()) {
            sheet.autoSizeColumn(column);
        }

        // 保存
        String filename = IdWorker.nextIdStr() + ".xlsx";
        FileOutputStream out = new FileOutputStream(filename);
        workbook.write(out);
        out.close();
        workbook.close();

        return new File(filename).getAbsolutePath();
    }
}
