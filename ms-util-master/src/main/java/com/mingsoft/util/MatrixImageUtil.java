 package com.mingsoft.util;

 import com.google.zxing.BarcodeFormat;
 import com.google.zxing.Binarizer;
 import com.google.zxing.BinaryBitmap;
 import com.google.zxing.DecodeHintType;
 import com.google.zxing.LuminanceSource;
 import com.google.zxing.MultiFormatReader;
 import com.google.zxing.MultiFormatWriter;
 import com.google.zxing.ReaderException;
 import com.google.zxing.Result;
 import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
 import com.google.zxing.client.j2se.MatrixToImageWriter;
 import com.google.zxing.common.BitMatrix;
 import com.google.zxing.common.HybridBinarizer;
 import java.awt.image.BufferedImage;
 import java.io.File;
 import java.io.IOException;
 import java.util.Hashtable;
 import javax.imageio.ImageIO;












































 public class MatrixImageUtil
 {
   public static void encode(String content, String imgPath, int width, int height) {
     try {
       if (StringUtil.isBlank(content) || StringUtil.isBlank(imgPath)) {
         return;
       }

       BitMatrix byteMatrix = (new MultiFormatWriter()).encode(new String(content
             .getBytes("utf-8"), "iso-8859-1"), BarcodeFormat.QR_CODE,
           width, height);
       File file = new File(imgPath);
       MatrixToImageWriter.writeToFile(byteMatrix, "png", file);
     } catch (Exception e) {
       e.printStackTrace();
     }
   }









   public static String decode(String imgPath) {
     try {
       File file = new File(imgPath);

       try {
         BufferedImage image = ImageIO.read(file);
         if (image == null) {
           System.out.println("Could not decode image");
         }
         BufferedImageLuminanceSource bufferedImageLuminanceSource = new BufferedImageLuminanceSource(image);
         BinaryBitmap bitmap = new BinaryBitmap((Binarizer)new HybridBinarizer(
               (LuminanceSource)bufferedImageLuminanceSource));


         Hashtable<DecodeHintType, Object> hints = new Hashtable<>();
         hints.put(DecodeHintType.CHARACTER_SET, "GBK");
         Result result = (new MultiFormatReader()).decode(bitmap, hints);
         String resultStr = result.getText();
         return resultStr;
       } catch (IOException ioe) {
         System.out.println(ioe.toString());
       } catch (ReaderException re) {
         System.out.println(re.toString());
       }

     } catch (Exception exception) {}


     return "";
   }
 }


