/*     */ package com.mingsoft.util;
/*     */ 
/*     */ import com.google.zxing.BarcodeFormat;
/*     */ import com.google.zxing.Binarizer;
/*     */ import com.google.zxing.BinaryBitmap;
/*     */ import com.google.zxing.DecodeHintType;
/*     */ import com.google.zxing.LuminanceSource;
/*     */ import com.google.zxing.MultiFormatReader;
/*     */ import com.google.zxing.MultiFormatWriter;
/*     */ import com.google.zxing.ReaderException;
/*     */ import com.google.zxing.Result;
/*     */ import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
/*     */ import com.google.zxing.client.j2se.MatrixToImageWriter;
/*     */ import com.google.zxing.common.BitMatrix;
/*     */ import com.google.zxing.common.HybridBinarizer;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.Hashtable;
/*     */ import javax.imageio.ImageIO;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MatrixImageUtil
/*     */ {
/*     */   public static void encode(String content, String imgPath, int width, int height) {
/*     */     try {
/*  69 */       if (StringUtil.isBlank(content) || StringUtil.isBlank(imgPath)) {
/*     */         return;
/*     */       }
/*     */       
/*  73 */       BitMatrix byteMatrix = (new MultiFormatWriter()).encode(new String(content
/*  74 */             .getBytes("utf-8"), "iso-8859-1"), BarcodeFormat.QR_CODE, 
/*  75 */           width, height);
/*  76 */       File file = new File(imgPath);
/*  77 */       MatrixToImageWriter.writeToFile(byteMatrix, "png", file);
/*  78 */     } catch (Exception e) {
/*  79 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String decode(String imgPath) {
/*     */     try {
/*  93 */       File file = new File(imgPath);
/*     */       
/*     */       try {
/*  96 */         BufferedImage image = ImageIO.read(file);
/*  97 */         if (image == null) {
/*  98 */           System.out.println("Could not decode image");
/*     */         }
/* 100 */         BufferedImageLuminanceSource bufferedImageLuminanceSource = new BufferedImageLuminanceSource(image);
/* 101 */         BinaryBitmap bitmap = new BinaryBitmap((Binarizer)new HybridBinarizer(
/* 102 */               (LuminanceSource)bufferedImageLuminanceSource));
/*     */ 
/*     */         
/* 105 */         Hashtable<Object, Object> hints = new Hashtable<>();
/* 106 */         hints.put(DecodeHintType.CHARACTER_SET, "GBK");
/* 107 */         Result result = (new MultiFormatReader()).decode(bitmap, hints);
/* 108 */         String resultStr = result.getText();
/* 109 */         return resultStr;
/* 110 */       } catch (IOException ioe) {
/* 111 */         System.out.println(ioe.toString());
/* 112 */       } catch (ReaderException re) {
/* 113 */         System.out.println(re.toString());
/*     */       }
/*     */     
/* 116 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/* 119 */     return "";
/*     */   }
/*     */ }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-util\1.0.1\ms-util-1.0.1.jar!\com\mingsof\\util\MatrixImageUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.2
 */