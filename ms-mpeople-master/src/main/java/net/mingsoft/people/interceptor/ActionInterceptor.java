 package net.mingsoft.people.interceptor;

 import java.net.URLDecoder;
 import java.net.URLEncoder;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import net.mingsoft.base.constant.e.BaseCookieEnum;
 import net.mingsoft.base.constant.e.BaseSessionEnum;
 import net.mingsoft.basic.constant.ErrorCodeEnum;
 import net.mingsoft.basic.exception.BusinessException;
 import net.mingsoft.basic.interceptor.BaseInterceptor;
 import net.mingsoft.basic.util.BasicUtil;
 import net.mingsoft.people.constant.e.CookieConstEnum;
 import net.mingsoft.people.constant.e.SessionConstEnum;
 import org.apache.commons.lang3.StringUtils;






















































 public class ActionInterceptor
   extends BaseInterceptor
 {
   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
     if (BasicUtil.getSession((BaseSessionEnum)SessionConstEnum.PEOPLE_SESSION) == null) {
       String loginUrl = BasicUtil.getCookie((BaseCookieEnum)CookieConstEnum.PEOPLE_LOGIN_URL);
       if (StringUtils.isBlank(loginUrl)) {
         throw new BusinessException(ErrorCodeEnum.CLIENT_UNAUTHORIZED.toString(), "登录失效");
       }
       String login = URLDecoder.decode(loginUrl, "utf-8");
       String backUrl = BasicUtil.getUrl() + request.getServletPath();
       if (request.getQueryString() != null) {
         backUrl = backUrl + "?" + request.getQueryString();
       }
       if (login.indexOf("?") > 0) {
         login = login + "&url=" + URLEncoder.encode(backUrl, "utf-8");
       } else {
         login = login + "?url=" + URLEncoder.encode(backUrl, "utf-8");
       }
       response.sendRedirect(login);
       return false;
     }


     return true;
   }
 }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-mpeople\1.0.11\ms-mpeople-1.0.11.jar!\net\mingsoft\people\interceptor\ActionInterceptor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */