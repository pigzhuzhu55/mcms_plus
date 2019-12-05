/*     */ package com.mingsoft.util;
/*     */ 
/*     */ import java.io.OutputStream;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.ServletOutputStream;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import jxl.SheetSettings;
/*     */ import jxl.Workbook;
/*     */ import jxl.format.Alignment;
/*     */ import jxl.format.Border;
/*     */ import jxl.format.BorderLineStyle;
/*     */ import jxl.format.CellFormat;
/*     */ import jxl.format.VerticalAlignment;
/*     */ import jxl.write.Label;
/*     */ import jxl.write.WritableCell;
/*     */ import jxl.write.WritableCellFormat;
/*     */ import jxl.write.WritableFont;
/*     */ import jxl.write.WritableSheet;
/*     */ import jxl.write.WritableWorkbook;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExcelUtil
/*     */ {
/*     */   public static final String exportExcel(String fileName, String[] titles, List<Object> listContent, HttpServletResponse response) {
/*  62 */     String result = "系统提示：Excel文件导出成功！";
/*     */ 
/*     */     
/*     */     try {
/*  66 */       ServletOutputStream servletOutputStream = response.getOutputStream();
/*  67 */       response.reset();
/*  68 */       response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("GB2312"), "ISO8859-1"));
/*     */       
/*  70 */       response.setContentType("application/msexcel");
/*     */ 
/*     */ 
/*     */       
/*  74 */       WritableWorkbook workbook = Workbook.createWorkbook((OutputStream)servletOutputStream);
/*     */ 
/*     */ 
/*     */       
/*  78 */       WritableSheet sheet = workbook.createSheet("Sheet1", 0);
/*     */ 
/*     */       
/*  81 */       SheetSettings sheetset = sheet.getSettings();
/*  82 */       sheetset.setProtected(false);
/*     */ 
/*     */       
/*  85 */       WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 10);
/*  86 */       WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10, 
/*  87 */           WritableFont.BOLD);
/*     */ 
/*     */ 
/*     */       
/*  91 */       WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
/*  92 */       wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN);
/*  93 */       wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE);
/*  94 */       wcf_center.setAlignment(Alignment.CENTRE);
/*  95 */       wcf_center.setWrap(false);
/*     */ 
/*     */       
/*  98 */       WritableCellFormat wcf_left = new WritableCellFormat(NormalFont);
/*  99 */       wcf_left.setBorder(Border.NONE, BorderLineStyle.THIN);
/* 100 */       wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE);
/* 101 */       wcf_left.setAlignment(Alignment.LEFT);
/* 102 */       wcf_left.setWrap(false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 108 */       for (int i = 0; i < titles.length; i++) {
/* 109 */         sheet.addCell((WritableCell)new Label(i, 0, titles[i], (CellFormat)wcf_center));
/*     */       }
/*     */       
/* 112 */       int i = 1;
/* 113 */       for (Object obj : listContent) {
/* 114 */         int j = 0; byte b; int k; Object[] arrayOfObject;
/* 115 */         for (k = (arrayOfObject = (Object[])obj).length, b = 0; b < k; ) { Object field = arrayOfObject[b];
/* 116 */           if (field instanceof Map) {
/* 117 */             if (field != null) {
/* 118 */               Map temp = (Map)field;
/* 119 */               if (temp.get("format") != null && temp.get("format") instanceof WritableCellFormat) {
/* 120 */                 sheet.addCell((WritableCell)new Label(j, i, (field != null) ? String.valueOf(temp.get("value")) : "", (CellFormat)temp.get("format")));
/*     */               } else {
/* 122 */                 sheet.addCell((WritableCell)new Label(j, i, (field != null) ? String.valueOf(field) : "", (CellFormat)wcf_left));
/*     */               } 
/*     */             } 
/*     */           } else {
/* 126 */             sheet.addCell((WritableCell)new Label(j, i, (field != null) ? String.valueOf(field) : "", (CellFormat)wcf_left));
/*     */           } 
/*     */           
/* 129 */           j++; b++; }
/*     */         
/* 131 */         i++;
/*     */       } 
/*     */       
/* 134 */       workbook.write();
/*     */       
/* 136 */       workbook.close();
/*     */     }
/* 138 */     catch (Exception e) {
/* 139 */       result = "系统提示：Excel文件导出失败，原因：" + e.toString();
/* 140 */       System.out.println(result);
/* 141 */       e.printStackTrace();
/*     */     } 
/* 143 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-util\1.0.1\ms-util-1.0.1.jar!\com\mingsof\\util\ExcelUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.2
 */