package com.example.bpp.util;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class ExcelUtil {

    //字母转数字  A-Z ：1-26
    public static int letterToNumber(String letter) {
        int length = letter.length();
        int num = 0;
        int number = 0;
        for (int i = 0; i < length; i++) {
            char ch = letter.charAt(length - i - 1);
            num = (int) (ch - 'A' + 1);
            num *= Math.pow(26, i);
            number += num;
        }
        return number;
    }

    public static String getExcelColumnLabel(int num) {
        String temp = "";
        double i = Math.floor(Math.log(25.0 * (num) / 26.0 + 1) / Math.log(26)) + 1;
        if (i > 1) {
            double sub = num - 26 * (Math.pow(26, i - 1) - 1) / 25;
            for (double j = i; j > 0; j--) {
                temp = temp + (char) (sub / Math.pow(26, j - 1) + 65);
                sub = sub % Math.pow(26, j - 1);
            }
        } else {
            temp = temp + (char) (num + 65);
        }
        return temp;
    }

    /**
     * 根据定位获取sheet对应的单元格
     * @param cellCoordinates 定位（“”F19）
     * @param sheet sheet页
     * @return
     */
    public static XSSFCell getCellByCellCoordinates(String cellCoordinates, XSSFSheet sheet) {
        StringBuilder rowNum = new StringBuilder();
        StringBuilder stringBuffer = new StringBuilder();
        //  \d 为正则表达式表示[0-9]数9字
        String[] eng = cellCoordinates.split("\\d");
        // \D 为正则表达式表示非数字
        String[] num = cellCoordinates.split("\\D");
        for (String s : eng) {
            stringBuffer.append(s);
        }
        for (String s : num) {
            rowNum.append(s);
        }
        int cellNum = letterToNumber(stringBuffer.toString());
        XSSFRow row = sheet.getRow(Integer.parseInt(rowNum.toString()) - 1);
        return row.getCell(cellNum - 1);
    }

    /**
     * 获取单元格数据 double类型
     *
     * @param cell 单元格
     * @return 单元格值
     */
    public static Double getDoubleCellVal(XSSFCell cell) {
        if (cell == null) {
            return null;
        }
        double cellVal = Double.parseDouble("0");
        if (CellType.NUMERIC.equals(cell.getCellType())) {
            cellVal = cell.getNumericCellValue();
        }
        if (CellType.STRING.equals(cell.getCellType())) {
            cellVal = Double.parseDouble(cell.getStringCellValue());
        }
        return cellVal;
    }

    public static void main(String[] args) {
        exportTask();
    }
    /**
     * 导出质检任务详情
     */
    public static void exportTask() {
        XSSFWorkbook workbook = null;
        FileInputStream fileInputStream = null;
        try {
//            ZipSecureFile.setMinInflateRatio(-1.0d);
            File file = new File("E://新建文件夹//FILEC04001-经销店月结表_2024-01_65432_经销商名称-样例.xlsx");
//            ZipSecureFile.setMinInflateRatio(-1.0d);
            fileInputStream = new FileInputStream(file);
            workbook = new XSSFWorkbook(fileInputStream);
            XSSFSheet sheet = workbook.getSheetAt(2);
            String coordinate = "F48";
            StringBuffer rowNum = new StringBuffer();
            StringBuffer stringBuffer = new StringBuffer();
            //  \d 为正则表达式表示[0-9]数字
            String[] str = coordinate.split("\\d");
            // \D 为正则表达式表示非数字
            String[] num = coordinate.split("\\D");
            for (String s : str) {
                stringBuffer.append(s);
            }
            for (String s : num) {
                rowNum.append(s);
            }
            int cellNum = letterToNumber(stringBuffer.toString());
            XSSFRow row = sheet.getRow(Integer.parseInt(rowNum.toString()) - 1);
            XSSFCell cell = row.getCell(cellNum - 1);
//            cell.setCellFormula("IFERROR(SUM(INDIRECT(\"F7:F10\"))/INDIRECT(\"F11\"),0)");  // 设置公式为求和公式，范围为A2到A5
            cell.setCellFormula("'2-2_业务明细表-服务_已更新'!G7");  // 设置公式为求和公式，范围为A2到A5
            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
            evaluator.evaluateFormulaCell(cell);
            File output = new File("E://FILEC04001-经销店月结表_2024-01_65433_经销商名称-样例.xlsx");
            FileOutputStream fileOutputStream = new FileOutputStream(output);
            workbook.write(fileOutputStream);
            workbook.close();
            fileInputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
