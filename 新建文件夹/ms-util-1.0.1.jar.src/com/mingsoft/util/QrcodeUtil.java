/*     */ package com.mingsoft.util;
/*     */ 
/*     */ import com.google.zxing.BarcodeFormat;
/*     */ import com.google.zxing.Binarizer;
/*     */ import com.google.zxing.BinaryBitmap;
/*     */ import com.google.zxing.DecodeHintType;
/*     */ import com.google.zxing.EncodeHintType;
/*     */ import com.google.zxing.LuminanceSource;
/*     */ import com.google.zxing.MultiFormatReader;
/*     */ import com.google.zxing.MultiFormatWriter;
/*     */ import com.google.zxing.ReaderException;
/*     */ import com.google.zxing.Result;
/*     */ import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
/*     */ import com.google.zxing.common.BitMatrix;
/*     */ import com.google.zxing.common.HybridBinarizer;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Map;
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
/*     */ public class QrcodeUtil
/*     */ {
/*     */   private static final int BLACK = -16777216;
/*     */   private static final int WHITE = -1;
/*     */   
/*     */   public static void encode(String contents, File file, BarcodeFormat format, int width, int height, Map<EncodeHintType, ?> hints) {
/*     */     try {
/*  69 */       BitMatrix bitMatrix = (new MultiFormatWriter()).encode(contents, format, width, height);
/*  70 */       BufferedImage image = new BufferedImage(width, height, 2);
/*  71 */       for (int x = 0; x < width; x++) {
/*  72 */         for (int y = 0; y < height; y++) {
/*  73 */           image.setRGB(x, y, bitMatrix.get(x, y) ? -16777216 : -1);
/*     */         }
/*     */       } 
/*  76 */       ImageIO.write(image, "png", file);
/*  77 */     } catch (Exception e) {
/*  78 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void decode(File file) {
/*     */     try {
/*     */       try {
/*  91 */         BufferedImage image = ImageIO.read(file);
/*  92 */         if (image == null) {
/*  93 */           System.out.println("Could not decode image");
/*     */         }
/*  95 */         BufferedImageLuminanceSource bufferedImageLuminanceSource = new BufferedImageLuminanceSource(image);
/*  96 */         BinaryBitmap bitmap = new BinaryBitmap((Binarizer)new HybridBinarizer((LuminanceSource)bufferedImageLuminanceSource));
/*     */ 
/*     */         
/*  99 */         Hashtable<Object, Object> hints = new Hashtable<>();
/*     */         
/* 101 */         hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
/* 102 */         Result result = (new MultiFormatReader()).decode(bitmap, hints);
/* 103 */         String resultStr = result.getText();
/* 104 */         System.out.println("解析后内容：" + resultStr);
/* 105 */       } catch (IOException ioe) {
/* 106 */         System.out.println(ioe.toString());
/* 107 */       } catch (ReaderException re) {
/* 108 */         System.out.println(re.toString());
/*     */       } 
/* 110 */     } catch (Exception ex) {
/* 111 */       System.out.println(ex.toString());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-util\1.0.1\ms-util-1.0.1.jar!\com\mingsof\\util\QrcodeUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.2
 */