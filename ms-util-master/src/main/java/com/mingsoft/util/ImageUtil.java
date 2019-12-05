 package com.mingsoft.util;

 import com.mortennobel.imagescaling.ResampleOp;
 import com.sun.image.codec.jpeg.JPEGCodec;
 import com.sun.image.codec.jpeg.JPEGImageEncoder;
 import java.awt.Graphics;
 import java.awt.Image;
 import java.awt.Rectangle;
 import java.awt.Toolkit;
 import java.awt.color.ColorSpace;
 import java.awt.geom.AffineTransform;
 import java.awt.image.AffineTransformOp;
 import java.awt.image.BufferedImage;
 import java.awt.image.ColorConvertOp;
 import java.awt.image.CropImageFilter;
 import java.awt.image.FilteredImageSource;
 import java.awt.image.ImageFilter;
 import java.io.ByteArrayInputStream;
 import java.io.ByteArrayOutputStream;
 import java.io.File;
 import java.io.FileInputStream;
 import java.io.FileNotFoundException;
 import java.io.FileOutputStream;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.Iterator;
 import javax.imageio.ImageIO;
 import javax.imageio.ImageReadParam;
 import javax.imageio.ImageReader;
 import javax.imageio.stream.ImageInputStream;
 import javax.imageio.stream.MemoryCacheImageInputStream;
 import org.apache.log4j.Logger;


































 public class ImageUtil
 {
   protected static Logger LOGGER = Logger.getLogger(ImageUtil.class);










   public static void batchImageWidthHeight(String directoryPath, int sSize, int tSize) {
     File dir = new File(directoryPath);

     File[] files = dir.listFiles();


     if (files == null) {
       return;
     }


     for (int i = 0; i < files.length; i++) {

       if (files[i].isDirectory()) {
         batchImageWidthHeight(files[i].getAbsolutePath(), sSize, tSize);
       } else {


         try {
           files[i].getPath();
           if (isPic(files[i].getPath()))
           {
             BufferedImage srcImage = ImageIO.read(files[i]);
             getImageWidthHeight(srcImage, sSize, tSize);


           }


         }
         catch (IOException e) {
           e.printStackTrace();
         }
       }
     }
   }











   public static void ChangeImage(String root, double scale) throws IOException {
     File file = new File(root);
     File[] subFile = file.listFiles();
     for (int i = 0; i < subFile.length; i++) {

       String name = subFile[i].getName();

       if (subFile[i].isDirectory()) {
         ChangeImage(String.valueOf(subFile[i].getAbsolutePath()) + "\\", scale);
       }
       String[] names = name.split("//.");
       if (StringUtil.isBlank(names[0]))
         break;
       scaleHyaline(String.valueOf(root) + subFile[i].getName(), String.valueOf(root) + subFile[i].getName(), scale, true);
     }
   }







   public static void convert(String source, String result) {
     try {
       File f = new File(source);
       f.canRead();
       f.canWrite();
       BufferedImage src = ImageIO.read(f);
       ImageIO.write(src, "JPG", new File(result));
     } catch (Exception e) {
       LOGGER.error(e);
     }
   }







   public static void createImage(String path, BufferedImage bi) {
     try {
       ImageIO.write(bi, path.substring(path.lastIndexOf("."), path.length()).replace(".", ""), new File(path));
     } catch (IOException e) {
       LOGGER.error(e);
     }
   }











   public static void createImage(String path, byte[] bt) {}










   public static void cut(String sourceImagePath, String descDir, int width, int height) {
     try {
       BufferedImage bi = ImageIO.read(new File(sourceImagePath));
       int srcWidth = bi.getHeight();
       int srcHeight = bi.getWidth();
       if (srcWidth > width && srcHeight > height) {
         Image image = bi.getScaledInstance(srcWidth, srcHeight, 1);
         width = 200;
         height = 150;
         int cols = 0;
         int rows = 0;

         if (srcWidth % width == 0) {
           cols = srcWidth / width;
         } else {
           cols = (int)Math.floor((srcWidth / width)) + 1;
         }
         if (srcHeight % height == 0) {
           rows = srcHeight / height;
         } else {
           rows = (int)Math.floor((srcHeight / height)) + 1;
         }


         for (int i = 0; i < rows; i++) {
           for (int j = 0; j < cols; j++) {


             ImageFilter cropFilter = new CropImageFilter(j * 200, i * 150, width, height);
             Image img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
             BufferedImage tag = new BufferedImage(width, height, 1);
             Graphics g = tag.getGraphics();
             g.drawImage(img, 0, 0, null);
             g.dispose();

             ImageIO.write(tag, "JPEG", new File(String.valueOf(descDir) + "pre_map_" + i + "_" + j + ".jpg"));
           }
         }
       }
     } catch (Exception e) {
       e.printStackTrace();
     }
   }





   public static void decodeImage(String sourceImagePath) {
     File file = new File(sourceImagePath);
     if (file.exists()) {
       ByteArrayOutputStream baos = new ByteArrayOutputStream();
       byte[] buffer = new byte[1024];
       int length = -1;

       try {
         InputStream is = new FileInputStream(file);
         try {
           while ((length = is.read(buffer)) != -1) {
             baos.write(buffer, 0, length);
           }
           baos.flush();
         } catch (IOException e) {
           e.printStackTrace();
         }
         byte[] data = baos.toByteArray();
         data[0] = -1;

         OutputStream os = new FileOutputStream(file);
         try {
           os.write(data);
           os.flush();
           os.close();
         } catch (IOException e1) {

           e1.printStackTrace();
         }
         try {
           is.close();
           baos.close();
         } catch (IOException e) {
           e.printStackTrace();
         }
       } catch (FileNotFoundException e1) {

         e1.printStackTrace();
       }
     }
   }





   public static void encodeImage(String sourceImagePath) {
     File file = new File(sourceImagePath);
     if (file.exists()) {
       ByteArrayOutputStream baos = new ByteArrayOutputStream();
       byte[] buffer = new byte[1024];
       int length = -1;

       try {
         InputStream is = new FileInputStream(file);
         try {
           while ((length = is.read(buffer)) != -1) {
             baos.write(buffer, 0, length);
           }
           baos.flush();
         } catch (IOException e) {
           e.printStackTrace();
         }
         byte[] data = baos.toByteArray();
         data[0] = 0;

         OutputStream os = new FileOutputStream(file);
         try {
           os.write(data);
           os.flush();
           os.close();
         } catch (IOException e1) {

           e1.printStackTrace();
         }
         try {
           is.close();
           baos.close();
         } catch (IOException e) {
           e.printStackTrace();
         }
       } catch (FileNotFoundException e1) {

         e1.printStackTrace();
       }
     }
   }







   public static String getImageType(byte[] imageBytes) {
     ByteArrayInputStream bais = null;
     MemoryCacheImageInputStream mcis = null;
     try {
       bais = new ByteArrayInputStream(imageBytes);
       mcis = new MemoryCacheImageInputStream(bais);
       Iterator<ImageReader> itr = ImageIO.getImageReaders(mcis);
       while (itr.hasNext()) {
         ImageReader reader = itr.next();
         String imageName = reader.getClass().getSimpleName();
         if (imageName != null && "JPEGImageReader".equalsIgnoreCase(imageName))
           return "jpeg";
         if (imageName != null && "JPGImageReader".equalsIgnoreCase(imageName))
           return "jpg";
         if (imageName != null && "pngImageReader".equalsIgnoreCase(imageName)) {
           return "png";
         }
       }
     } catch (Exception e) {
       LOGGER.error(e);
     }
     return null;
   }












   private static int[] getImageWidthHeight(BufferedImage source, int sourceWidth, int targetWidth) {
     double ts = targetWidth / sourceWidth;
     int newWidth = (int)(source.getWidth() * ts);
     int newHeight = (int)(source.getHeight() * ts);
     if (newWidth < 3)
       newWidth = 3;
     if (newHeight < 3)
       newHeight = 3;
     int[] wh = new int[2];
     wh[0] = newWidth;
     wh[1] = newHeight;
     return wh;
   }








   public static int[] getImageWidthHeight(String sourceImagePath) {
     try {
       BufferedImage bi = ImageIO.read(new File(sourceImagePath));

       int[] wh = new int[2];
       wh[0] = bi.getWidth();
       wh[1] = bi.getHeight();
       return wh;
     } catch (IOException e) {
       LOGGER.error(e);

       return null;
     }
   }






   public static void gray(String sourceImagePath, String savePath) {
     try {
       BufferedImage src = ImageIO.read(new File(sourceImagePath));
       ColorSpace cs = ColorSpace.getInstance(1003);
       ColorConvertOp op = new ColorConvertOp(cs, null);
       src = op.filter(src, null);
       ImageIO.write(src, "JPEG", new File(savePath));
     } catch (IOException e) {
       e.printStackTrace();
     }
   }









   private static boolean isPic(String sourceImagePath) {
     String picSfix = "jpg|png|gif";
     String[] temp = picSfix.split("\\|");
     if (sourceImagePath.indexOf(".") > 0) {
       String fileSfix = sourceImagePath.substring(sourceImagePath.indexOf("."), sourceImagePath.length()).replace(".", "");
       for (int i = 0; i < temp.length; i++) {
         if (fileSfix.equals(temp[i]))
           return true;
       }
     }
     return false;
   }














   public static void main(String[] args) throws IOException {}













   public static BufferedImage resize(String sourceImagePath, int width, int height) {
     try {
       BufferedImage inputBufImage = ImageIO.read(new File(sourceImagePath));
       ResampleOp resampleOp = new ResampleOp(Math.min(width, inputBufImage.getWidth()), Math.min(height, inputBufImage.getHeight()));
       BufferedImage rescaledTomato = resampleOp.filter(inputBufImage, null);
       return rescaledTomato;
     } catch (IOException e1) {
       e1.printStackTrace();


       return null;
     }
   }















   public static boolean resizeImage(File sourceImagePath, String savePath, int width, int height, String sufix) {
     try {
       BufferedImage inputBufImage = ImageIO.read(sourceImagePath);
       ResampleOp resampleOp = new ResampleOp(Math.min(width, inputBufImage.getWidth()), Math.min(height, inputBufImage.getHeight()));
       BufferedImage rescaledTomato = resampleOp.filter(inputBufImage, null);
       ImageIO.write(rescaledTomato, sufix, new File(savePath));
       return true;
     } catch (Exception e) {
       LOGGER.error(e);
       return false;
     }
   }











   public static byte[] resizeImageForBytes(String sourceImagePath, int targetW, int targetH, String type) {
     try {
       BufferedImage image = resize(sourceImagePath, targetW, targetH);
       ByteArrayOutputStream outStream = new ByteArrayOutputStream();




       ImageIO.write(image, type, outStream);
       outStream.flush();
       byte[] result = outStream.toByteArray();
       outStream.close();
       return result;
     } catch (Exception ex) {
       LOGGER.error(ex);
       return null;
     }
   }

















   public static boolean resizeImageForEncode(File sourceImageFile, String savePath, Integer width, Integer height, String sufix) {
     try {
       BufferedImage inputBufImage = ImageIO.read(sourceImageFile);
       ResampleOp resampleOp = new ResampleOp(Math.min(width.intValue(), inputBufImage.getWidth()), Math.min(height.intValue(), inputBufImage.getHeight()));
       BufferedImage rescaledTomato = resampleOp.filter(inputBufImage, null);
       ImageIO.write(rescaledTomato, sufix, new File(savePath));
       return true;
     } catch (Exception e) {
       LOGGER.error(e);
       return false;
     }
   }











   public static void rotate(String sourceImagePath, int direction) {
     File file = null;
     BufferedImage original = null;
     BufferedImage bufOut = null;


     file = new File(sourceImagePath);
     try {
       original = ImageIO.read(file);
     } catch (Exception exception) {
       return;
     }

     int width = original.getWidth();
     int height = original.getHeight();
     bufOut = new BufferedImage(width, height, original.getType());

     AffineTransform atx = new AffineTransform();
     switch (direction) {
       case 0:
         atx.rotate(-1.5707963267948966D, (width / 2), (height / 2));
         break;
       case 1:
         atx.rotate(1.5707963267948966D, (width / 2), (height / 2));
         break;
     }
     AffineTransformOp atop = new AffineTransformOp(atx, 3);
     atop.filter(original, bufOut);
     bufOut = bufOut.getSubimage(0, 0, width, height);
     try {
       ImageIO.write(bufOut, "JPG", new File(sourceImagePath));
     } catch (IOException e) {
       LOGGER.debug(e);
     }
   }









   public static void saveImageAsJpg(String sourceImagePath, String savePath, int width, int hight) {
     BufferedImage srcImage = null;
     String imgType = "JPEG";
     if (sourceImagePath.toLowerCase().endsWith(".png")) {
       imgType = "PNG";
     }
     File saveFile = new File(savePath);
     File fromFile = new File(sourceImagePath);
     try {
       srcImage = ImageIO.read(fromFile);
     } catch (IOException e) {
       LOGGER.error(e);
     }
     if (width > 0 || hight > 0) {
       srcImage = resize(sourceImagePath, width, hight);
     }
     try {
       ImageIO.write(srcImage, imgType, saveFile);
     } catch (IOException e) {
       LOGGER.error(e);
     }
   }













   public static void scale(String sourceImagePath, String savePath, double scale, boolean flag) {
     try {
       BufferedImage src = ImageIO.read(new File(sourceImagePath));
       int width = src.getWidth();
       int height = src.getHeight();
       if (flag) {

         width = (int)(width * scale);
         height = (int)(height * scale);
       } else {

         width = (int)(width / scale);
         height = (int)(height / scale);
       }
       Image image = src.getScaledInstance(width, height, 1);
       BufferedImage tag = new BufferedImage(width, height, 1);
       Graphics g = tag.getGraphics();
       g.drawImage(image, 0, 0, null);
       g.dispose();
       ImageIO.write(tag, "JPEG", new File(savePath));
     } catch (IOException e) {
       LOGGER.equals(e);
     }
   }













   public static void scaleHyaline(String sourceImagePath, String savePath, double scale, boolean flag) {
     if (isPic(sourceImagePath)) {

       try {
         BufferedImage src = ImageIO.read(new File(sourceImagePath));
         BufferedImage dstImage = null;
         AffineTransform transform = AffineTransform.getScaleInstance(scale, scale);
         AffineTransformOp op = new AffineTransformOp(transform, 2);
         dstImage = op.filter(src, null);


         try {
           ImageIO.write(dstImage, "png", new File(savePath));
         } catch (IOException e) {
           e.printStackTrace();
         }

       }
       catch (IOException e) {
         LOGGER.equals(e);
       }
     }
   }















   public static void cut(int x, int y, int width, int height, String sourceImagePath, String savePath) {
     FileInputStream is = null;
     ImageInputStream iis = null;


     try {
       try {
         is = new FileInputStream(sourceImagePath);
       } catch (FileNotFoundException e) {
         LOGGER.error("图片未找到:" + sourceImagePath);
         LOGGER.error(e);
       }





       Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName("jpg");
       ImageReader reader = it.next();


       try {
         iis = ImageIO.createImageInputStream(is);
       } catch (IOException e) {
         e.printStackTrace();
         LOGGER.error("图片未找到:" + sourceImagePath);
         LOGGER.error(e);
       }





       reader.setInput(iis, true);











       ImageReadParam param = reader.getDefaultReadParam();

       try {
         reader.read(0);
       } catch (IOException e) {
         LOGGER.error("图片未找到:" + sourceImagePath);
         LOGGER.error(e);
       }





       Rectangle rect = new Rectangle(x, y, width, height);




















       param.setSourceRegion(rect);





       BufferedImage bi = null;
       try {
         bi = reader.read(0, param);
       } catch (IOException e) {
         LOGGER.error("图片未找到:" + sourceImagePath);
         LOGGER.error(e);
       }


       try {
         ImageIO.write(bi, "jpg", new File(savePath));
       } catch (IOException e) {
         LOGGER.error("输出路径不正确:" + savePath);
         LOGGER.error(e);

       }

     }
     finally {

       if (is != null)
         try {
           is.close();
         } catch (IOException e) {
           LOGGER.error(e);
         }
       if (iis != null) {
         try {
           iis.close();
         } catch (IOException e) {
           LOGGER.error(e);
         }
       }
     }
   }









   public static void formatImage(String path, String fix) {
     String _path = path;
     try {
       File file = new File(_path);
       InputStream is = new FileInputStream(file);
       BufferedImage image = ImageIO.read(is);
       BufferedImage tag = new BufferedImage(image.getWidth(), image.getHeight(), 1);
       tag.getGraphics().drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
       FileOutputStream newimage = new FileOutputStream(String.valueOf(path.substring(0, path.lastIndexOf("."))) + fix);
       JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage);
       encoder.encode(tag);
       newimage.close();
     } catch (Exception ex) {
       LOGGER.equals(ex);
     }
   }













   public static void zoom(String sourceImagePath, String savePath, int targetWidth, int targetHeight, boolean more, String exp) {
     File file = null;
     BufferedImage original = null;
     BufferedImage bufOut = null;

     if (more) {
       file = new File(sourceImagePath);
       try {
         original = ImageIO.read(file);
       } catch (IOException e) {
         LOGGER.debug(e);
       }
       int width = original.getWidth();
       int height = original.getHeight();
       if (targetWidth >= width && targetHeight >= height) {
         targetWidth = width;
         targetHeight = height;
       }
       bufOut = new BufferedImage(width, height, original.getType());
       double xScale = (new Integer(targetWidth)).doubleValue() / width;
       double yScale = (new Integer(targetHeight)).doubleValue() / height;
       double scale = Math.min(xScale, yScale);
       int newWidth = (new Double(original.getWidth() * scale)).intValue();
       int newHeight = (new Double(original.getHeight() * scale)).intValue();
       AffineTransform atx = AffineTransform.getScaleInstance(scale, scale);
       AffineTransformOp atop = new AffineTransformOp(atx, 2);
       atop.filter(original, bufOut);
       bufOut = bufOut.getSubimage(0, 0, newWidth, newHeight);
       try {
         ImageIO.write(bufOut, "JPG", file);
       } catch (IOException e) {
         LOGGER.debug(e);
       }

     } else {

       if (sourceImagePath == null && sourceImagePath.length() > 0)
         return;
       String[] filePath = sourceImagePath.split(exp);
       if (filePath.length > 0)
         for (int i = 0; i < filePath.length; i++) {

           file = new File(savePath);
           try {
             original = ImageIO.read(file);
           } catch (Exception exception) {
             return;
           }

           int width = original.getWidth();
           int height = original.getHeight();
           if (targetWidth >= width && targetHeight >= height) {
             targetWidth = width;
             targetHeight = height;
           }
           bufOut = new BufferedImage(width, height, original.getType());
           double xScale = (new Integer(targetWidth)).doubleValue() / width;
           double yScale = (new Integer(targetHeight)).doubleValue() / height;
           double scale = Math.min(xScale, yScale);
           int newWidth = (new Double(original.getWidth() * scale)).intValue();
           int newHeight = (new Double(original.getHeight() * scale)).intValue();
           AffineTransform atx = AffineTransform.getScaleInstance(scale, scale);
           AffineTransformOp atop = new AffineTransformOp(atx, 2);
           atop.filter(original, bufOut);
           bufOut = bufOut.getSubimage(0, 0, newWidth, newHeight);
           try {
             ImageIO.write(bufOut, "JPG", new File(savePath));
           } catch (IOException e) {
             LOGGER.debug(e);
           }
         }
     }
   }
 }


