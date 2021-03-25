package cn.yjh.spring_3.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.*;

/**
 * ---------------------POI所需jar包：---------------------
 * <dependency>
 * <groupId>org.apache.poi</groupId>
 * <artifactId>poi</artifactId>
 * <version>4.0.0</version>
 * </dependency>
 * <dependency>
 * <groupId>org.apache.poi</groupId>
 * <artifactId>poi-ooxml</artifactId>
 * <version>4.0.0</version>
 * </dependency>
 * ------------------------------------------------------
 *
 * @description: Excel工具类
 * @author: You Jinhua
 * @create: 2021-01-19 13:27
 */
public class ExcelUtil {

    /**
     * 读取 EXCEL
     *
     * @param filePath excel存放的路径
     * @return 返回 ExcelSheet 结果集
     * @throws Exception
     */
    public static List<ExcelSheet> readExcel(String filePath) throws Exception {
        File file = new File(filePath);
        Workbook workbook = WorkbookFactory.create(file);
        int sheetSize = workbook.getNumberOfSheets();
        List<ExcelSheet> resultList = new ArrayList<>();
        for (int i = 0; i < sheetSize; i++) {
            ExcelSheet excelSheet = new ExcelSheet(null);
            excelSheet.setSheetName(workbook.getSheetName(i));
            Sheet sheet = workbook.getSheetAt(i);
            int rowSize = sheet.getLastRowNum();
            for (int j = 0; j < rowSize; j++) {
                Row row = sheet.getRow(j);
                short cellSize = row.getLastCellNum();
                List<String> cellList = new ArrayList<>();
                for (int k = 0; k < cellSize; k++) {
                    Cell cell = row.getCell(k);
                    if(cell!=null){
                        String cellValue = cell.getStringCellValue();
                        cellList.add(cellValue);
                    }
                }
                excelSheet.getData().add(cellList);
            }
            resultList.add(excelSheet);
        }
        workbook.close();
        return resultList;
    }

    /**
     * 创建 EXCEL
     *
     * @param filePath 生成的excel存放路径
     * @param dataList excel的数据
     * @throws Exception
     */
    public static void createExcel(String filePath, List<List<String>> dataList) throws Exception {
        Workbook wb = new XSSFWorkbook();
        createSheet(wb, null, dataList);
        FileOutputStream fileOut = new FileOutputStream(filePath);
        wb.write(fileOut);
        fileOut.close();
    }

    /**
     * 创建 EXCEL(带多个sheet的)
     *
     * @param filePath    生成的excel存放路径
     * @param excelSheets Excel每个sheet的数据及映射关系
     * @throws Exception
     */
    public static void createExcel(String filePath, ExcelSheet... excelSheets) throws Exception {
        Workbook wb = new XSSFWorkbook();
        for (ExcelSheet es : excelSheets) {
            createSheet(wb, es);
        }
        FileOutputStream fileOut = new FileOutputStream(filePath);
        wb.write(fileOut);
        fileOut.close();
    }

    /**
     * 创建 EXCEL(带多个sheet的)
     *
     * @param filePath   生成的excel存放路径
     * @param dataList   excel的数据
     * @param sheetNames 多个sheet的名称
     * @throws Exception
     */
    public static void createExcel(String filePath, List<List<String>> dataList, String... sheetNames) throws Exception {
        Workbook wb = new XSSFWorkbook();
        if (sheetNames != null && sheetNames.length > 0) {
            for (int i = 0; i < sheetNames.length; i++) {
                List<String> title = null;
                createSheet(wb, sheetNames[i], dataList);
            }
        } else {
            createSheet(wb, null, dataList);
        }
        FileOutputStream fileOut = new FileOutputStream(filePath);
        wb.write(fileOut);
        fileOut.close();
    }

    /**
     * 创建 SHEET
     *
     * @param workbook  sheet所属的workbook
     * @param sheetName sheet的名称
     * @param dataList  sheet的数据
     */
    private static void createSheet(Workbook workbook, String sheetName, List<List<String>> dataList) {
        Sheet sheet = null;
        if (sheetName != null && !"".equals(sheetName)) {
            sheet = workbook.createSheet(sheetName);
        } else {
            sheet = workbook.createSheet();
        }
        int rowSize = dataList.size();

        for (int i = 0; i < rowSize; i++) {
            createRow(sheet, i, dataList.get(i));
        }
    }

    /**
     * 创建 ROW
     *
     * @param sheet    该行所属的sheet
     * @param rowIndex 在第几行创建
     * @param data     该行的数据
     */
    private static void createRow(Sheet sheet, int rowIndex, List<String> data) {
        Row row = sheet.createRow(rowIndex);
        for (int i = 0; i < data.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(data.get(i));
        }
    }

    /**
     * 创建SHEET
     *
     * @param wb sheet所属的workbook
     * @param es sheet所需的数据
     */
    private static <T> void createSheet(Workbook wb, ExcelSheet es) {
        String sheetName = es.getSheetName();
        Sheet sheet = null;
        if (sheetName != null && !"".equals(sheetName)) {
            sheet = wb.createSheet(sheetName);
        } else {
            sheet = wb.createSheet();
        }
        Map<String,Integer> title_column_mapping = es.getTitle_column_mapping();
        int rownum = 0;
        if(title_column_mapping != null){
            Iterator<Map.Entry<String, Integer>> iterator = title_column_mapping.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String, Integer> entry = iterator.next();
                setCellValue(sheet,entry.getValue(),rownum,entry.getKey());
            }
            rownum++;
        }
        if (es.getClazz() != null) {
            List<T> beanList = es.getBeanList();
            Map<String,String> title_field_mapping = es.getTitle_field_mapping();
            int rowSize = beanList.size();
            for(int i=0;i<rowSize;i++){
                T t = beanList.get(i);
                Class<?> clazz = t.getClass();
                Field[] fields = clazz.getDeclaredFields();
                for(Field field : fields){
                    String fieldName = field.getName();
                    String titleName = title_field_mapping.get(fieldName);
                    Integer y = title_column_mapping.get(titleName);
                    String data = getFieldValue(t,fieldName);
                    if(data!=null){
                        setCellValue(sheet,y,rownum,data);
                    }
                }
                rownum++;
            }
        } else {
            List<List<String>> dataList = es.getData();
            int rowSize = dataList.size();
            for (int i = 0; i < rowSize; i++) {
                createRow(sheet, rownum++, dataList.get(i));
            }
        }
    }

    /**
     * 给指定单元格设置值
     *
     * @param sheet 要操作的sheet
     * @param x     横坐标（列）
     * @param y     纵坐标（行）
     * @param data  要设置的值
     */
    private static void setCellValue(Sheet sheet, int x, int y, String data) {
        Row row = sheet.getRow(y);
        if (row == null) {
            row = sheet.createRow(y);
        }
        Cell cell = row.getCell(x);
        if (cell == null) {
            cell = row.createCell(x);
        }
        cell.setCellValue(data);
    }

    /**
     * 获取指定单元格的值
     *
     * @param sheet 要操作的sheet
     * @param x     横坐标（列）
     * @param y     纵坐标（行）
     */
    private static String getCellValue(Sheet sheet, int x, int y) {
        Row row = sheet.getRow(y);
        if (row == null) {
            return null;
        }
        Cell cell = row.getCell(x);
        if (cell == null) {
            return null;
        }
        return cell.getStringCellValue();
    }

    private static <T> void setFieldValue(T t,String fieldName,String value){
        Class<T> clazz = null;
        Field field = null;
        try {
            clazz = (Class<T>) t.getClass();
            field = clazz.getDeclaredField(fieldName);
            if(field == null){
                return ;
            }
            field.setAccessible(true);
            field.set(t,value);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }


    private static <T> String getFieldValue(T t,String fieldName){
        Class<T> clazz = null;
        Field field = null;
        try {
            clazz = (Class<T>) t.getClass();
            field = clazz.getDeclaredField(fieldName);
            if(field == null){
                return null;
            }
            field.setAccessible(true);
            return (String) field.get(t);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static class ExcelSheet<T> {
        private String sheetName;
        private Class<T> clazz;
        private List<List<String>> data;
        private List<T> beanList;
        private Map<String, Integer> title_column_mapping;
        private Map<String, String> title_field_mapping;

        public Class<T> getClazz() {
            return clazz;
        }

        public void setClazz(Class<T> clazz) {
            this.clazz = clazz;
        }

        public ExcelSheet(Class<T> clazz) {
            this.clazz = clazz;
            if (clazz != null) {
                this.beanList = new ArrayList<>();
            } else {
                this.data = new ArrayList<>();
            }
        }

        public String getSheetName() {
            return sheetName;
        }

        public void setSheetName(String sheetName) {
            this.sheetName = sheetName;
        }

        public List<List<String>> getData() {
            return data;
        }

        public void setData(List<List<String>> data) {
            this.data = data;
        }

        public List<T> getBeanList() {
            return beanList;
        }

        public void setBeanList(List<T> beanList) {
            this.beanList = beanList;
        }

        public Map<String, Integer> getTitle_column_mapping() {
            return title_column_mapping;
        }

        public void setTitle_column_mapping(Map<String, Integer> title_column_mapping) {
            this.title_column_mapping = title_column_mapping;
        }

        public Map<String, String> getTitle_field_mapping() {
            return title_field_mapping;
        }

        public void setTitle_field_mapping(Map<String, String> title_field_mapping) {
            this.title_field_mapping = title_field_mapping;
        }
    }

    public static void createExcel(String filePath, Map<Integer,String> map1,Map<String, Integer> map2,ExcelSheet... excelSheets) throws Exception {
        Workbook wb = new XSSFWorkbook();
        for (ExcelSheet es : excelSheets) {
            createSheet(wb, es,map1,map2);
        }
        FileOutputStream fileOut = new FileOutputStream(filePath);
        wb.write(fileOut);
        fileOut.close();
    }


    private static <T> void createSheet(Workbook wb,ExcelSheet es,Map<Integer,String> map1,Map<String, Integer> map2) {
        String sheetName = es.getSheetName();
        Sheet sheet = null;
        if (sheetName != null && !"".equals(sheetName)) {
            sheet = wb.createSheet(sheetName);
        } else {
            sheet = wb.createSheet();
        }
        Map<String,Integer> title_column_mapping = es.getTitle_column_mapping();
        int rownum = 0;
        if(title_column_mapping != null){
            Iterator<Map.Entry<String, Integer>> iterator = title_column_mapping.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String, Integer> entry = iterator.next();
                setCellValue(sheet,entry.getValue(),rownum,entry.getKey());
            }
            rownum++;
        }

        Iterator<Map.Entry<Integer,String>> iterator = map1.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<Integer,String> entry = iterator.next();
            setCellValue(sheet,entry.getKey(),rownum,entry.getValue());
        }
        rownum++;

        Iterator<Map.Entry<String, Integer>> iterator1 = map2.entrySet().iterator();
        while (iterator1.hasNext()){
            Map.Entry<String, Integer> entry = iterator1.next();
            setCellValue(sheet,entry.getValue(),rownum,entry.getKey());
        }
        rownum++;

        if (es.getClazz() != null) {
            List<T> beanList = es.getBeanList();
            Map<String,String> title_field_mapping = es.getTitle_field_mapping();
            int rowSize = beanList.size();
            for(int i=0;i<rowSize;i++){
                T t = beanList.get(i);
                Class<?> clazz = t.getClass();
                Field[] fields = clazz.getDeclaredFields();
                for(Field field : fields){
                    String fieldName = field.getName();
                    String titleName = title_field_mapping.get(fieldName);
                    Integer y = title_column_mapping.get(titleName);
                    String data = getFieldValue(t,fieldName);
                    if(data!=null){
                        setCellValue(sheet,y,rownum,data);
                    }
                }
                rownum++;
            }
        } else {
            List<List<String>> dataList = es.getData();
            int rowSize = dataList.size();
            for (int i = 0; i < rowSize; i++) {
                createRow(sheet, rownum++, dataList.get(i));
            }
        }
    }

    public static void main(String[] args) throws Exception {
        List<ExcelSheet> excelSheets = readExcel("C:\\Users\\youjinhua\\Desktop\\test\\影像系统表结构-（全部）.xlsx");
        List<List<String>> dataList = new ArrayList<>();
        ExcelSheet excelSheet = new ExcelSheet(null);
        for(ExcelSheet es:excelSheets){
            List<String> rowData = new ArrayList<>();
            String sheetName = es.getSheetName();
            String substring = sheetName.substring(4, sheetName.length());
            rowData.add(substring);
            rowData.add(sheetName);
            dataList.add(rowData);

        }
        excelSheet.setData(dataList);
        createExcel("C:\\Users\\youjinhua\\Desktop\\test\\temp.xlsx",excelSheet);
        //System.out.println(excelSheets);
    }
}
