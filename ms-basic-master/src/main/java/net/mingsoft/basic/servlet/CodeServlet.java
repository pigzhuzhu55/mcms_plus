 package net.mingsoft.basic.servlet;

 import java.awt.Color;
 import java.awt.Font;
 import java.awt.Graphics2D;
 import java.awt.image.BufferedImage;
 import java.io.IOException;
 import java.io.OutputStream;
 import java.util.Random;
 import javax.imageio.ImageIO;
 import javax.servlet.ServletException;
 import javax.servlet.ServletOutputStream;
 import javax.servlet.annotation.WebServlet;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import javax.servlet.http.HttpSession;
 import net.mingsoft.basic.constant.e.SessionConstEnum;

































 @WebServlet(urlPatterns = {"/code"})
 public class CodeServlet
   extends BaseServlet
 {
   private int imgWidth = 100;




   private int imgHeight = 50;




   private int codeCount = 4;




   private int x = 0;





   private int fontHeight;




   private int codeY;




   private String fontStyle;




   private static final long serialVersionUID = 128554012633034503L;





   public void init() throws ServletException {
     String strWidth = getInitParameter("imgWidth");

     String strHeight = getInitParameter("imgHeight");

     String strCodeCount = getInitParameter("codeCount");

     this.fontStyle = getInitParameter("fontStyle");


     try {
       if (strWidth != null && strWidth.length() != 0) {
         this.imgWidth = Integer.parseInt(strWidth);
       }
       if (strHeight != null && strHeight.length() != 0) {
         this.imgHeight = Integer.parseInt(strHeight);
       }
       if (strCodeCount != null && strCodeCount.length() != 0) {
         this.codeCount = Integer.parseInt(strCodeCount);
       }
     } catch (NumberFormatException e) {
       e.printStackTrace();
     }

     this.x = this.imgWidth / (this.codeCount + 1);
     this.fontHeight = this.imgHeight - 2;
     this.codeY = this.imgHeight - 12;
   }








   protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     response.setContentType("image/jpeg");
     response.setHeader("Pragma", "No-cache");
     response.setHeader("Cache-Control", "no-cache");
     response.setDateHeader("Expires", 0L);
     HttpSession session = request.getSession();


     BufferedImage image = new BufferedImage(this.imgWidth, this.imgHeight, 1);


     Graphics2D g = image.createGraphics();


     Random random = new Random();


     g.setColor(Color.WHITE);
     g.fillRect(0, 0, this.imgWidth, this.imgHeight);


     g.setFont(new Font(this.fontStyle, 2, this.fontHeight));


     g.setColor(new Color(55, 55, 12));



     g.setColor(getRandColor(160, 200));
     for (int i = 0; i < 60; i++) {
       int x = random.nextInt(this.imgWidth);
       int y = random.nextInt(this.imgHeight);
       int xl = random.nextInt(12);
       int yl = random.nextInt(12);
       g.drawLine(x, y, x + xl, y + yl);
     }


     String sRand = "";
     int red = 0, green = 0, blue = 0;
     for (int i = 0; i < this.codeCount; i++) {
       red = random.nextInt(255);
       green = random.nextInt(255);
       blue = random.nextInt(255);
       int wordType = random.nextInt(3);
       char retWord = Character.MIN_VALUE;
       switch (wordType) {
         case 0:
           retWord = getSingleNumberChar();
           break;
         case 1:
           retWord = getLowerOrUpperChar(0);
           break;
         case 2:
           retWord = getLowerOrUpperChar(1);
           break;
       }
       sRand = sRand + String.valueOf(retWord);
       g.setColor(new Color(red, green, blue));
       g.drawString(String.valueOf(retWord), i * this.x, this.codeY);
     }


     session.setAttribute(SessionConstEnum.CODE_SESSION.toString(), sRand);

     g.dispose();
     ServletOutputStream responseOutputStream = response.getOutputStream();

     ImageIO.write(image, "JPEG", (OutputStream)responseOutputStream);


     responseOutputStream.flush();
     responseOutputStream.close();
   }







   Color getRandColor(int fc, int bc) {
     Random random = new Random();
     if (fc > 255)
       fc = 255;
     if (bc > 255)
       bc = 255;
     int r = fc + random.nextInt(bc - fc);
     int g = fc + random.nextInt(bc - fc);
     int b = fc + random.nextInt(bc - fc);
     return new Color(r, g, b);
   }









   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { processRequest(request, response); }










   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { processRequest(request, response); }






   private char getSingleNumberChar() {
     Random random = new Random();
     int numberResult = random.nextInt(10);
     int ret = numberResult + 48;
     return (char)ret;
   }






   private char getLowerOrUpperChar(int upper) {
     Random random = new Random();
     int numberResult = random.nextInt(26);
     int ret = 0;
     if (upper == 0) {
       ret = numberResult + 97;
     } else if (upper == 1) {
       ret = numberResult + 65;
     }
     return (char)ret;
   }
 }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\servlet\CodeServlet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */