 package net.mingsoft.basic.servlet;

 import java.io.File;
 import java.io.IOException;
 import javax.servlet.ServletException;
 import javax.servlet.ServletRequest;
 import javax.servlet.ServletResponse;
 import javax.servlet.annotation.WebServlet;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import net.mingsoft.basic.biz.IAppBiz;
 import net.mingsoft.basic.entity.AppEntity;
 import net.mingsoft.basic.util.SpringUtil;
 import org.apache.commons.lang3.StringUtils;































 @WebServlet({"/index"})
 public class IndexServlet
   extends BaseServlet
 {
   private static final long serialVersionUID = -7580260477467138079L;
   private static String INDEX = "index.html"; private static String DEFAULT = "default.html";




   private IAppBiz appBiz;




   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     this.appBiz = (IAppBiz)SpringUtil.getBean(IAppBiz.class);

     int websiteId = 0;
     AppEntity website = this.appBiz.getByUrl(getDomain(request));
     if (website != null) {
       websiteId = website.getAppId();
     } else {
       return;
     }
     String path = "";

     if (!StringUtils.isEmpty(website.getAppMobileStyle())) {
       path = isMobileDevice(request) ? "m" : "";
     }

     String defaultHtmlPath = getRealPath(request, "html" + File.separator + websiteId + File.separator + path + File.separator + "default.html");
     File file = new File(defaultHtmlPath);
     String url = "html/" + websiteId + "/" + path;
     String indexPosition = url + "/" + INDEX;
     if (file.exists()) {
       indexPosition = url + "/" + DEFAULT;
     }

     request.getRequestDispatcher(indexPosition).forward((ServletRequest)request, (ServletResponse)response);
   }
 }


