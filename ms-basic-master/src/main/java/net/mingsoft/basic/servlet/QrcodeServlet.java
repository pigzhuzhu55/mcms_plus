 package net.mingsoft.basic.servlet;

 import com.google.zxing.BarcodeFormat;
 import com.google.zxing.MultiFormatWriter;
 import com.google.zxing.WriterException;
 import com.google.zxing.common.BitMatrix;
 import java.awt.image.BufferedImage;
 import java.io.IOException;
 import java.io.OutputStream;
 import javax.imageio.ImageIO;
 import javax.servlet.ServletException;
 import javax.servlet.ServletOutputStream;
 import javax.servlet.annotation.WebServlet;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;



































 @WebServlet(urlPatterns = {"/qrcode"})
 public class QrcodeServlet
   extends BaseServlet
 {
   private static final int BLACK = -16777216;
   private static final int WHITE = -1;

   protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     response.setContentType("image/jpeg");
     response.setHeader("Pragma", "No-cache");
     response.setHeader("Cache-Control", "no-cache");
     response.setDateHeader("Expires", 0L);
     String contents = request.getParameter("contents");
     int width = getInt(request, "width", 100);
     int height = getInt(request, "height", 100);

     try {
       BitMatrix bitMatrix = (new MultiFormatWriter()).encode(contents, BarcodeFormat.QR_CODE, width, height);

       BufferedImage image = new BufferedImage(width, height, 1);
       for (int x = 0; x < width; x++) {
         for (int y = 0; y < height; y++) {
           image.setRGB(x, y, (bitMatrix.get(x, y) == true) ? -16777216 : -1);
         }
       }
       ServletOutputStream responseOutputStream = response.getOutputStream();

       ImageIO.write(image, "png", (OutputStream)responseOutputStream);

       responseOutputStream.flush();
       responseOutputStream.close();
     } catch (WriterException e) {

       e.printStackTrace();
     }
   }










   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { processRequest(request, response); }










   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { processRequest(request, response); }
 }


