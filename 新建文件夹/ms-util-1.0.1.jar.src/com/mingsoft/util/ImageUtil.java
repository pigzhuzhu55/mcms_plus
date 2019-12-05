/*     */ package com.mingsoft.util;
/*     */ 
/*     */ import com.mortennobel.imagescaling.ResampleOp;
/*     */ import com.sun.image.codec.jpeg.JPEGCodec;
/*     */ import com.sun.image.codec.jpeg.JPEGImageEncoder;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Image;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.color.ColorSpace;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.image.AffineTransformOp;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ColorConvertOp;
/*     */ import java.awt.image.CropImageFilter;
/*     */ import java.awt.image.FilteredImageSource;
/*     */ import java.awt.image.ImageFilter;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Iterator;
/*     */ import javax.imageio.ImageIO;
/*     */ import javax.imageio.ImageReadParam;
/*     */ import javax.imageio.ImageReader;
/*     */ import javax.imageio.stream.ImageInputStream;
/*     */ import javax.imageio.stream.MemoryCacheImageInputStream;
/*     */ import org.apache.log4j.Logger;
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
/*     */ public class ImageUtil
/*     */ {
/*  70 */   protected static Logger LOGGER = Logger.getLogger(ImageUtil.class);
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
/*     */   public static void batchImageWidthHeight(String directoryPath, int sSize, int tSize) {
/*  82 */     File dir = new File(directoryPath);
/*     */     
/*  84 */     File[] files = dir.listFiles();
/*     */ 
/*     */     
/*  87 */     if (files == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  92 */     for (int i = 0; i < files.length; i++) {
/*     */       
/*  94 */       if (files[i].isDirectory()) {
/*  95 */         batchImageWidthHeight(files[i].getAbsolutePath(), sSize, tSize);
/*     */       } else {
/*     */ 
/*     */         
/*     */         try {
/* 100 */           files[i].getPath();
/* 101 */           if (isPic(files[i].getPath()))
/*     */           {
/* 103 */             BufferedImage srcImage = ImageIO.read(files[i]);
/* 104 */             getImageWidthHeight(srcImage, sSize, tSize);
/*     */ 
/*     */           
/*     */           }
/*     */ 
/*     */         
/*     */         }
/* 111 */         catch (IOException e) {
/* 112 */           e.printStackTrace();
/*     */         } 
/*     */       } 
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
/*     */ 
/*     */   
/*     */   public static void ChangeImage(String root, double scale) throws IOException {
/* 129 */     File file = new File(root);
/* 130 */     File[] subFile = file.listFiles();
/* 131 */     for (int i = 0; i < subFile.length; i++) {
/*     */       
/* 133 */       String name = subFile[i].getName();
/*     */       
/* 135 */       if (subFile[i].isDirectory()) {
/* 136 */         ChangeImage(String.valueOf(subFile[i].getAbsolutePath()) + "\\", scale);
/*     */       }
/* 138 */       String[] names = name.split("//.");
/* 139 */       if (StringUtil.isBlank(names[0]))
/*     */         break; 
/* 141 */       scaleHyaline(String.valueOf(root) + subFile[i].getName(), String.valueOf(root) + subFile[i].getName(), scale, true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void convert(String source, String result) {
/*     */     try {
/* 153 */       File f = new File(source);
/* 154 */       f.canRead();
/* 155 */       f.canWrite();
/* 156 */       BufferedImage src = ImageIO.read(f);
/* 157 */       ImageIO.write(src, "JPG", new File(result));
/* 158 */     } catch (Exception e) {
/* 159 */       LOGGER.error(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void createImage(String path, BufferedImage bi) {
/*     */     try {
/* 171 */       ImageIO.write(bi, path.substring(path.lastIndexOf("."), path.length()).replace(".", ""), new File(path));
/* 172 */     } catch (IOException e) {
/* 173 */       LOGGER.error(e);
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
/*     */ 
/*     */   
/*     */   public static void createImage(String path, byte[] bt) {}
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
/*     */   public static void cut(String sourceImagePath, String descDir, int width, int height) {
/*     */     try {
/* 200 */       BufferedImage bi = ImageIO.read(new File(sourceImagePath));
/* 201 */       int srcWidth = bi.getHeight();
/* 202 */       int srcHeight = bi.getWidth();
/* 203 */       if (srcWidth > width && srcHeight > height) {
/* 204 */         Image image = bi.getScaledInstance(srcWidth, srcHeight, 1);
/* 205 */         width = 200;
/* 206 */         height = 150;
/* 207 */         int cols = 0;
/* 208 */         int rows = 0;
/*     */         
/* 210 */         if (srcWidth % width == 0) {
/* 211 */           cols = srcWidth / width;
/*     */         } else {
/* 213 */           cols = (int)Math.floor((srcWidth / width)) + 1;
/*     */         } 
/* 215 */         if (srcHeight % height == 0) {
/* 216 */           rows = srcHeight / height;
/*     */         } else {
/* 218 */           rows = (int)Math.floor((srcHeight / height)) + 1;
/*     */         } 
/*     */ 
/*     */         
/* 222 */         for (int i = 0; i < rows; i++) {
/* 223 */           for (int j = 0; j < cols; j++) {
/*     */ 
/*     */             
/* 226 */             ImageFilter cropFilter = new CropImageFilter(j * 200, i * 150, width, height);
/* 227 */             Image img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
/* 228 */             BufferedImage tag = new BufferedImage(width, height, 1);
/* 229 */             Graphics g = tag.getGraphics();
/* 230 */             g.drawImage(img, 0, 0, null);
/* 231 */             g.dispose();
/*     */             
/* 233 */             ImageIO.write(tag, "JPEG", new File(String.valueOf(descDir) + "pre_map_" + i + "_" + j + ".jpg"));
/*     */           } 
/*     */         } 
/*     */       } 
/* 237 */     } catch (Exception e) {
/* 238 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void decodeImage(String sourceImagePath) {
/* 247 */     File file = new File(sourceImagePath);
/* 248 */     if (file.exists()) {
/* 249 */       ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 250 */       byte[] buffer = new byte[1024];
/* 251 */       int length = -1;
/*     */       
/*     */       try {
/* 254 */         InputStream is = new FileInputStream(file);
/*     */         try {
/* 256 */           while ((length = is.read(buffer)) != -1) {
/* 257 */             baos.write(buffer, 0, length);
/*     */           }
/* 259 */           baos.flush();
/* 260 */         } catch (IOException e) {
/* 261 */           e.printStackTrace();
/*     */         } 
/* 263 */         byte[] data = baos.toByteArray();
/* 264 */         data[0] = -1;
/*     */         
/* 266 */         OutputStream os = new FileOutputStream(file);
/*     */         try {
/* 268 */           os.write(data);
/* 269 */           os.flush();
/* 270 */           os.close();
/* 271 */         } catch (IOException e1) {
/*     */           
/* 273 */           e1.printStackTrace();
/*     */         } 
/*     */         try {
/* 276 */           is.close();
/* 277 */           baos.close();
/* 278 */         } catch (IOException e) {
/* 279 */           e.printStackTrace();
/*     */         } 
/* 281 */       } catch (FileNotFoundException e1) {
/*     */         
/* 283 */         e1.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void encodeImage(String sourceImagePath) {
/* 293 */     File file = new File(sourceImagePath);
/* 294 */     if (file.exists()) {
/* 295 */       ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 296 */       byte[] buffer = new byte[1024];
/* 297 */       int length = -1;
/*     */       
/*     */       try {
/* 300 */         InputStream is = new FileInputStream(file);
/*     */         try {
/* 302 */           while ((length = is.read(buffer)) != -1) {
/* 303 */             baos.write(buffer, 0, length);
/*     */           }
/* 305 */           baos.flush();
/* 306 */         } catch (IOException e) {
/* 307 */           e.printStackTrace();
/*     */         } 
/* 309 */         byte[] data = baos.toByteArray();
/* 310 */         data[0] = 0;
/*     */         
/* 312 */         OutputStream os = new FileOutputStream(file);
/*     */         try {
/* 314 */           os.write(data);
/* 315 */           os.flush();
/* 316 */           os.close();
/* 317 */         } catch (IOException e1) {
/*     */           
/* 319 */           e1.printStackTrace();
/*     */         } 
/*     */         try {
/* 322 */           is.close();
/* 323 */           baos.close();
/* 324 */         } catch (IOException e) {
/* 325 */           e.printStackTrace();
/*     */         } 
/* 327 */       } catch (FileNotFoundException e1) {
/*     */         
/* 329 */         e1.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getImageType(byte[] imageBytes) {
/* 341 */     ByteArrayInputStream bais = null;
/* 342 */     MemoryCacheImageInputStream mcis = null;
/*     */     try {
/* 344 */       bais = new ByteArrayInputStream(imageBytes);
/* 345 */       mcis = new MemoryCacheImageInputStream(bais);
/* 346 */       Iterator<ImageReader> itr = ImageIO.getImageReaders(mcis);
/* 347 */       while (itr.hasNext()) {
/* 348 */         ImageReader reader = itr.next();
/* 349 */         String imageName = reader.getClass().getSimpleName();
/* 350 */         if (imageName != null && "JPEGImageReader".equalsIgnoreCase(imageName))
/* 351 */           return "jpeg"; 
/* 352 */         if (imageName != null && "JPGImageReader".equalsIgnoreCase(imageName))
/* 353 */           return "jpg"; 
/* 354 */         if (imageName != null && "pngImageReader".equalsIgnoreCase(imageName)) {
/* 355 */           return "png";
/*     */         }
/*     */       } 
/* 358 */     } catch (Exception e) {
/* 359 */       LOGGER.error(e);
/*     */     } 
/* 361 */     return null;
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
/*     */ 
/*     */ 
/*     */   
/*     */   private static int[] getImageWidthHeight(BufferedImage source, int sourceWidth, int targetWidth) {
/* 376 */     double ts = targetWidth / sourceWidth;
/* 377 */     int newWidth = (int)(source.getWidth() * ts);
/* 378 */     int newHeight = (int)(source.getHeight() * ts);
/* 379 */     if (newWidth < 3)
/* 380 */       newWidth = 3; 
/* 381 */     if (newHeight < 3)
/* 382 */       newHeight = 3; 
/* 383 */     int[] wh = new int[2];
/* 384 */     wh[0] = newWidth;
/* 385 */     wh[1] = newHeight;
/* 386 */     return wh;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] getImageWidthHeight(String sourceImagePath) {
/*     */     try {
/* 398 */       BufferedImage bi = ImageIO.read(new File(sourceImagePath));
/*     */       
/* 400 */       int[] wh = new int[2];
/* 401 */       wh[0] = bi.getWidth();
/* 402 */       wh[1] = bi.getHeight();
/* 403 */       return wh;
/* 404 */     } catch (IOException e) {
/* 405 */       LOGGER.error(e);
/*     */       
/* 407 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void gray(String sourceImagePath, String savePath) {
/*     */     try {
/* 418 */       BufferedImage src = ImageIO.read(new File(sourceImagePath));
/* 419 */       ColorSpace cs = ColorSpace.getInstance(1003);
/* 420 */       ColorConvertOp op = new ColorConvertOp(cs, null);
/* 421 */       src = op.filter(src, null);
/* 422 */       ImageIO.write(src, "JPEG", new File(savePath));
/* 423 */     } catch (IOException e) {
/* 424 */       e.printStackTrace();
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
/*     */   private static boolean isPic(String sourceImagePath) {
/* 437 */     String picSfix = "jpg|png|gif";
/* 438 */     String[] temp = picSfix.split("\\|");
/* 439 */     if (sourceImagePath.indexOf(".") > 0) {
/* 440 */       String fileSfix = sourceImagePath.substring(sourceImagePath.indexOf("."), sourceImagePath.length()).replace(".", "");
/* 441 */       for (int i = 0; i < temp.length; i++) {
/* 442 */         if (fileSfix.equals(temp[i]))
/* 443 */           return true; 
/*     */       } 
/*     */     } 
/* 446 */     return false;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] args) throws IOException {}
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
/*     */   public static BufferedImage resize(String sourceImagePath, int width, int height) {
/*     */     try {
/* 478 */       BufferedImage inputBufImage = ImageIO.read(new File(sourceImagePath));
/* 479 */       ResampleOp resampleOp = new ResampleOp(Math.min(width, inputBufImage.getWidth()), Math.min(height, inputBufImage.getHeight()));
/* 480 */       BufferedImage rescaledTomato = resampleOp.filter(inputBufImage, null);
/* 481 */       return rescaledTomato;
/* 482 */     } catch (IOException e1) {
/* 483 */       e1.printStackTrace();
/*     */ 
/*     */       
/* 486 */       return null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean resizeImage(File sourceImagePath, String savePath, int width, int height, String sufix) {
/*     */     try {
/* 506 */       BufferedImage inputBufImage = ImageIO.read(sourceImagePath);
/* 507 */       ResampleOp resampleOp = new ResampleOp(Math.min(width, inputBufImage.getWidth()), Math.min(height, inputBufImage.getHeight()));
/* 508 */       BufferedImage rescaledTomato = resampleOp.filter(inputBufImage, null);
/* 509 */       ImageIO.write(rescaledTomato, sufix, new File(savePath));
/* 510 */       return true;
/* 511 */     } catch (Exception e) {
/* 512 */       LOGGER.error(e);
/* 513 */       return false;
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
/*     */ 
/*     */   
/*     */   public static byte[] resizeImageForBytes(String sourceImagePath, int targetW, int targetH, String type) {
/*     */     try {
/* 529 */       BufferedImage image = resize(sourceImagePath, targetW, targetH);
/* 530 */       ByteArrayOutputStream outStream = new ByteArrayOutputStream();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 535 */       ImageIO.write(image, type, outStream);
/* 536 */       outStream.flush();
/* 537 */       byte[] result = outStream.toByteArray();
/* 538 */       outStream.close();
/* 539 */       return result;
/* 540 */     } catch (Exception ex) {
/* 541 */       LOGGER.error(ex);
/* 542 */       return null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean resizeImageForEncode(File sourceImageFile, String savePath, Integer width, Integer height, String sufix) {
/*     */     try {
/* 564 */       BufferedImage inputBufImage = ImageIO.read(sourceImageFile);
/* 565 */       ResampleOp resampleOp = new ResampleOp(Math.min(width.intValue(), inputBufImage.getWidth()), Math.min(height.intValue(), inputBufImage.getHeight()));
/* 566 */       BufferedImage rescaledTomato = resampleOp.filter(inputBufImage, null);
/* 567 */       ImageIO.write(rescaledTomato, sufix, new File(savePath));
/* 568 */       return true;
/* 569 */     } catch (Exception e) {
/* 570 */       LOGGER.error(e);
/* 571 */       return false;
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
/*     */ 
/*     */   
/*     */   public static void rotate(String sourceImagePath, int direction) {
/* 586 */     File file = null;
/* 587 */     BufferedImage original = null;
/* 588 */     BufferedImage bufOut = null;
/*     */ 
/*     */     
/* 591 */     file = new File(sourceImagePath);
/*     */     try {
/* 593 */       original = ImageIO.read(file);
/* 594 */     } catch (Exception exception) {
/*     */       return;
/*     */     } 
/*     */     
/* 598 */     int width = original.getWidth();
/* 599 */     int height = original.getHeight();
/* 600 */     bufOut = new BufferedImage(width, height, original.getType());
/*     */     
/* 602 */     AffineTransform atx = new AffineTransform();
/* 603 */     switch (direction) {
/*     */       case 0:
/* 605 */         atx.rotate(-1.5707963267948966D, (width / 2), (height / 2));
/*     */         break;
/*     */       case 1:
/* 608 */         atx.rotate(1.5707963267948966D, (width / 2), (height / 2));
/*     */         break;
/*     */     } 
/* 611 */     AffineTransformOp atop = new AffineTransformOp(atx, 3);
/* 612 */     atop.filter(original, bufOut);
/* 613 */     bufOut = bufOut.getSubimage(0, 0, width, height);
/*     */     try {
/* 615 */       ImageIO.write(bufOut, "JPG", new File(sourceImagePath));
/* 616 */     } catch (IOException e) {
/* 617 */       LOGGER.debug(e);
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
/*     */   public static void saveImageAsJpg(String sourceImagePath, String savePath, int width, int hight) {
/* 630 */     BufferedImage srcImage = null;
/* 631 */     String imgType = "JPEG";
/* 632 */     if (sourceImagePath.toLowerCase().endsWith(".png")) {
/* 633 */       imgType = "PNG";
/*     */     }
/* 635 */     File saveFile = new File(savePath);
/* 636 */     File fromFile = new File(sourceImagePath);
/*     */     try {
/* 638 */       srcImage = ImageIO.read(fromFile);
/* 639 */     } catch (IOException e) {
/* 640 */       LOGGER.error(e);
/*     */     } 
/* 642 */     if (width > 0 || hight > 0) {
/* 643 */       srcImage = resize(sourceImagePath, width, hight);
/*     */     }
/*     */     try {
/* 646 */       ImageIO.write(srcImage, imgType, saveFile);
/* 647 */     } catch (IOException e) {
/* 648 */       LOGGER.error(e);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void scale(String sourceImagePath, String savePath, double scale, boolean flag) {
/*     */     try {
/* 666 */       BufferedImage src = ImageIO.read(new File(sourceImagePath));
/* 667 */       int width = src.getWidth();
/* 668 */       int height = src.getHeight();
/* 669 */       if (flag) {
/*     */         
/* 671 */         width = (int)(width * scale);
/* 672 */         height = (int)(height * scale);
/*     */       } else {
/*     */         
/* 675 */         width = (int)(width / scale);
/* 676 */         height = (int)(height / scale);
/*     */       } 
/* 678 */       Image image = src.getScaledInstance(width, height, 1);
/* 679 */       BufferedImage tag = new BufferedImage(width, height, 1);
/* 680 */       Graphics g = tag.getGraphics();
/* 681 */       g.drawImage(image, 0, 0, null);
/* 682 */       g.dispose();
/* 683 */       ImageIO.write(tag, "JPEG", new File(savePath));
/* 684 */     } catch (IOException e) {
/* 685 */       LOGGER.equals(e);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void scaleHyaline(String sourceImagePath, String savePath, double scale, boolean flag) {
/* 702 */     if (isPic(sourceImagePath)) {
/*     */       
/*     */       try {
/* 705 */         BufferedImage src = ImageIO.read(new File(sourceImagePath));
/* 706 */         BufferedImage dstImage = null;
/* 707 */         AffineTransform transform = AffineTransform.getScaleInstance(scale, scale);
/* 708 */         AffineTransformOp op = new AffineTransformOp(transform, 2);
/* 709 */         dstImage = op.filter(src, null);
/*     */ 
/*     */         
/*     */         try {
/* 713 */           ImageIO.write(dstImage, "png", new File(savePath));
/* 714 */         } catch (IOException e) {
/* 715 */           e.printStackTrace();
/*     */         }
/*     */       
/*     */       }
/* 719 */       catch (IOException e) {
/* 720 */         LOGGER.equals(e);
/*     */       } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void cut(int x, int y, int width, int height, String sourceImagePath, String savePath) {
/* 740 */     FileInputStream is = null;
/* 741 */     ImageInputStream iis = null;
/*     */ 
/*     */     
/*     */     try {
/*     */       try {
/* 746 */         is = new FileInputStream(sourceImagePath);
/* 747 */       } catch (FileNotFoundException e) {
/* 748 */         LOGGER.error("图片未找到:" + sourceImagePath);
/* 749 */         LOGGER.error(e);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 756 */       Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName("jpg");
/* 757 */       ImageReader reader = it.next();
/*     */ 
/*     */       
/*     */       try {
/* 761 */         iis = ImageIO.createImageInputStream(is);
/* 762 */       } catch (IOException e) {
/* 763 */         e.printStackTrace();
/* 764 */         LOGGER.error("图片未找到:" + sourceImagePath);
/* 765 */         LOGGER.error(e);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 772 */       reader.setInput(iis, true);
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
/* 784 */       ImageReadParam param = reader.getDefaultReadParam();
/*     */       
/*     */       try {
/* 787 */         reader.read(0);
/* 788 */       } catch (IOException e) {
/* 789 */         LOGGER.error("图片未找到:" + sourceImagePath);
/* 790 */         LOGGER.error(e);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 797 */       Rectangle rect = new Rectangle(x, y, width, height);
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
/* 818 */       param.setSourceRegion(rect);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 824 */       BufferedImage bi = null;
/*     */       try {
/* 826 */         bi = reader.read(0, param);
/* 827 */       } catch (IOException e) {
/* 828 */         LOGGER.error("图片未找到:" + sourceImagePath);
/* 829 */         LOGGER.error(e);
/*     */       } 
/*     */ 
/*     */       
/*     */       try {
/* 834 */         ImageIO.write(bi, "jpg", new File(savePath));
/* 835 */       } catch (IOException e) {
/* 836 */         LOGGER.error("输出路径不正确:" + savePath);
/* 837 */         LOGGER.error(e);
/*     */       
/*     */       }
/*     */     
/*     */     }
/*     */     finally {
/*     */       
/* 844 */       if (is != null)
/*     */         try {
/* 846 */           is.close();
/* 847 */         } catch (IOException e) {
/* 848 */           LOGGER.error(e);
/*     */         }  
/* 850 */       if (iis != null) {
/*     */         try {
/* 852 */           iis.close();
/* 853 */         } catch (IOException e) {
/* 854 */           LOGGER.error(e);
/*     */         } 
/*     */       }
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
/*     */   public static void formatImage(String path, String fix) {
/* 869 */     String _path = path;
/*     */     try {
/* 871 */       File file = new File(_path);
/* 872 */       InputStream is = new FileInputStream(file);
/* 873 */       BufferedImage image = ImageIO.read(is);
/* 874 */       BufferedImage tag = new BufferedImage(image.getWidth(), image.getHeight(), 1);
/* 875 */       tag.getGraphics().drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
/* 876 */       FileOutputStream newimage = new FileOutputStream(String.valueOf(path.substring(0, path.lastIndexOf("."))) + fix);
/* 877 */       JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage);
/* 878 */       encoder.encode(tag);
/* 879 */       newimage.close();
/* 880 */     } catch (Exception ex) {
/* 881 */       LOGGER.equals(ex);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void zoom(String sourceImagePath, String savePath, int targetWidth, int targetHeight, boolean more, String exp) {
/* 898 */     File file = null;
/* 899 */     BufferedImage original = null;
/* 900 */     BufferedImage bufOut = null;
/*     */     
/* 902 */     if (more) {
/* 903 */       file = new File(sourceImagePath);
/*     */       try {
/* 905 */         original = ImageIO.read(file);
/* 906 */       } catch (IOException e) {
/* 907 */         LOGGER.debug(e);
/*     */       } 
/* 909 */       int width = original.getWidth();
/* 910 */       int height = original.getHeight();
/* 911 */       if (targetWidth >= width && targetHeight >= height) {
/* 912 */         targetWidth = width;
/* 913 */         targetHeight = height;
/*     */       } 
/* 915 */       bufOut = new BufferedImage(width, height, original.getType());
/* 916 */       double xScale = (new Integer(targetWidth)).doubleValue() / width;
/* 917 */       double yScale = (new Integer(targetHeight)).doubleValue() / height;
/* 918 */       double scale = Math.min(xScale, yScale);
/* 919 */       int newWidth = (new Double(original.getWidth() * scale)).intValue();
/* 920 */       int newHeight = (new Double(original.getHeight() * scale)).intValue();
/* 921 */       AffineTransform atx = AffineTransform.getScaleInstance(scale, scale);
/* 922 */       AffineTransformOp atop = new AffineTransformOp(atx, 2);
/* 923 */       atop.filter(original, bufOut);
/* 924 */       bufOut = bufOut.getSubimage(0, 0, newWidth, newHeight);
/*     */       try {
/* 926 */         ImageIO.write(bufOut, "JPG", file);
/* 927 */       } catch (IOException e) {
/* 928 */         LOGGER.debug(e);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 933 */       if (sourceImagePath == null && sourceImagePath.length() > 0)
/*     */         return; 
/* 935 */       String[] filePath = sourceImagePath.split(exp);
/* 936 */       if (filePath.length > 0)
/* 937 */         for (int i = 0; i < filePath.length; i++) {
/*     */           
/* 939 */           file = new File(savePath);
/*     */           try {
/* 941 */             original = ImageIO.read(file);
/* 942 */           } catch (Exception exception) {
/*     */             return;
/*     */           } 
/*     */           
/* 946 */           int width = original.getWidth();
/* 947 */           int height = original.getHeight();
/* 948 */           if (targetWidth >= width && targetHeight >= height) {
/* 949 */             targetWidth = width;
/* 950 */             targetHeight = height;
/*     */           } 
/* 952 */           bufOut = new BufferedImage(width, height, original.getType());
/* 953 */           double xScale = (new Integer(targetWidth)).doubleValue() / width;
/* 954 */           double yScale = (new Integer(targetHeight)).doubleValue() / height;
/* 955 */           double scale = Math.min(xScale, yScale);
/* 956 */           int newWidth = (new Double(original.getWidth() * scale)).intValue();
/* 957 */           int newHeight = (new Double(original.getHeight() * scale)).intValue();
/* 958 */           AffineTransform atx = AffineTransform.getScaleInstance(scale, scale);
/* 959 */           AffineTransformOp atop = new AffineTransformOp(atx, 2);
/* 960 */           atop.filter(original, bufOut);
/* 961 */           bufOut = bufOut.getSubimage(0, 0, newWidth, newHeight);
/*     */           try {
/* 963 */             ImageIO.write(bufOut, "JPG", new File(savePath));
/* 964 */           } catch (IOException e) {
/* 965 */             LOGGER.debug(e);
/*     */           } 
/*     */         }  
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-util\1.0.1\ms-util-1.0.1.jar!\com\mingsof\\util\ImageUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.2
 */