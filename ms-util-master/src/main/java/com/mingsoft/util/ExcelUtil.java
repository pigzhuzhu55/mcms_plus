 package com.mingsoft.util;

 import java.io.OutputStream;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.ServletOutputStream;
 import javax.servlet.http.HttpServletResponse;
 import jxl.SheetSettings;
 import jxl.Workbook;
 import jxl.format.Alignment;
 import jxl.format.Border;
 import jxl.format.BorderLineStyle;
 import jxl.format.CellFormat;
 import jxl.format.VerticalAlignment;
 import jxl.write.Label;
 import jxl.write.WritableCell;
 import jxl.write.WritableCellFormat;
 import jxl.write.WritableFont;
 import jxl.write.WritableSheet;
 import jxl.write.WritableWorkbook;






































 public class ExcelUtil
 {
   public static final String exportExcel(String fileName, String[] titles, List<Object> listContent, HttpServletResponse response) {
     String result = "系统提示：Excel文件导出成功！";


     try {
       ServletOutputStream servletOutputStream = response.getOutputStream();
       response.reset();
       response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("GB2312"), "ISO8859-1"));

       response.setContentType("application/msexcel");



       WritableWorkbook workbook = Workbook.createWorkbook((OutputStream)servletOutputStream);



       WritableSheet sheet = workbook.createSheet("Sheet1", 0);


       SheetSettings sheetset = sheet.getSettings();
       sheetset.setProtected(false);


       WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 10);
       WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10,
           WritableFont.BOLD);



       WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
       wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN);
       wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE);
       wcf_center.setAlignment(Alignment.CENTRE);
       wcf_center.setWrap(false);


       WritableCellFormat wcf_left = new WritableCellFormat(NormalFont);
       wcf_left.setBorder(Border.NONE, BorderLineStyle.THIN);
       wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE);
       wcf_left.setAlignment(Alignment.LEFT);
       wcf_left.setWrap(false);





       for (int i = 0; i < titles.length; i++) {
         sheet.addCell((WritableCell)new Label(i, 0, titles[i], (CellFormat)wcf_center));
       }

       int i = 1;
       for (Object obj : listContent) {
         int j = 0; byte b; int k; Object[] arrayOfObject;
         for (k = (arrayOfObject = (Object[])obj).length, b = 0; b < k; ) { Object field = arrayOfObject[b];
           if (field instanceof Map) {
             if (field != null) {
               Map temp = (Map)field;
               if (temp.get("format") != null && temp.get("format") instanceof WritableCellFormat) {
                 sheet.addCell((WritableCell)new Label(j, i, (field != null) ? String.valueOf(temp.get("value")) : "", (CellFormat)temp.get("format")));
               } else {
                 sheet.addCell((WritableCell)new Label(j, i, (field != null) ? String.valueOf(field) : "", (CellFormat)wcf_left));
               }
             }
           } else {
             sheet.addCell((WritableCell)new Label(j, i, (field != null) ? String.valueOf(field) : "", (CellFormat)wcf_left));
           }

           j++; b++; }

         i++;
       }

       workbook.write();

       workbook.close();
     }
     catch (Exception e) {
       result = "系统提示：Excel文件导出失败，原因：" + e.toString();
       System.out.println(result);
       e.printStackTrace();
     }
     return result;
   }
 }


