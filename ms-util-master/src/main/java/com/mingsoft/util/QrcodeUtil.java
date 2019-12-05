 package com.mingsoft.util;

 import com.google.zxing.BarcodeFormat;
 import com.google.zxing.Binarizer;
 import com.google.zxing.BinaryBitmap;
 import com.google.zxing.DecodeHintType;
 import com.google.zxing.EncodeHintType;
 import com.google.zxing.LuminanceSource;
 import com.google.zxing.MultiFormatReader;
 import com.google.zxing.MultiFormatWriter;
 import com.google.zxing.ReaderException;
 import com.google.zxing.Result;
 import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
 import com.google.zxing.common.BitMatrix;
 import com.google.zxing.common.HybridBinarizer;
 import java.awt.image.BufferedImage;
 import java.io.File;
 import java.io.IOException;
 import java.util.Hashtable;
 import java.util.Map;
 import javax.imageio.ImageIO;








































 public class QrcodeUtil
 {
   private static final int BLACK = -16777216;
   private static final int WHITE = -1;

   public static void encode(String contents, File file, BarcodeFormat format, int width, int height, Map<EncodeHintType, ?> hints) {
     try {
       BitMatrix bitMatrix = (new MultiFormatWriter()).encode(contents, format, width, height);
       BufferedImage image = new BufferedImage(width, height, 2);
       for (int x = 0; x < width; x++) {
         for (int y = 0; y < height; y++) {
           image.setRGB(x, y, bitMatrix.get(x, y) ? -16777216 : -1);
         }
       }
       ImageIO.write(image, "png", file);
     } catch (Exception e) {
       e.printStackTrace();
     }
   }







   public void decode(File file) {
     try {
       try {
         BufferedImage image = ImageIO.read(file);
         if (image == null) {
           System.out.println("Could not decode image");
         }
         BufferedImageLuminanceSource bufferedImageLuminanceSource = new BufferedImageLuminanceSource(image);
         BinaryBitmap bitmap = new BinaryBitmap((Binarizer)new HybridBinarizer((LuminanceSource)bufferedImageLuminanceSource));


         Hashtable<DecodeHintType, Object> hints = new Hashtable<>();

         hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
         Result result = (new MultiFormatReader()).decode(bitmap, hints);
         String resultStr = result.getText();
         System.out.println("解析后内容：" + resultStr);
       } catch (IOException ioe) {
         System.out.println(ioe.toString());
       } catch (ReaderException re) {
         System.out.println(re.toString());
       }
     } catch (Exception ex) {
       System.out.println(ex.toString());
     }
   }
 }


