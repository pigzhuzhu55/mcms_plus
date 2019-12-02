 package net.mingsoft.basic.servlet;

 import java.io.File;
 import java.io.FileInputStream;
 import java.io.IOException;
 import javax.servlet.ServletException;
 import javax.servlet.ServletOutputStream;
 import javax.servlet.annotation.WebServlet;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;








































 @WebServlet(urlPatterns = {"/download"})
 public class DownloadServlet
   extends BaseServlet
 {
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     String type = request.getParameter("type");
     String filePath = request.getParameter("file");
     filePath = "/upload/" + filePath;

     response.setContentType("multipart/form-data");

     response.setHeader("Content-Disposition", "attachment;fileName=" + request.getParameter("file"));


     File file = new File(getRealPath(request, filePath));

     try {
       FileInputStream inputStream = new FileInputStream(file);


       ServletOutputStream out = response.getOutputStream();

       int b = 0;
       byte[] buffer = new byte[512];
       while (b != -1) {
         b = inputStream.read(buffer);

         out.write(buffer, 0, b);
       }
       inputStream.close();
       out.close();
       out.flush();
     }
     catch (IOException e) {
       e.printStackTrace();
     }
   }
 }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\servlet\DownloadServlet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */