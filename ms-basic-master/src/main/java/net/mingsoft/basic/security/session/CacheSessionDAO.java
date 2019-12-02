 package net.mingsoft.basic.security.session;

 import java.io.Serializable;
 import java.util.Collection;
 import java.util.HashSet;
 import java.util.Set;
 import javax.servlet.http.HttpServletRequest;
 import net.mingsoft.basic.util.SpringUtil;
 import org.apache.shiro.session.Session;
 import org.apache.shiro.session.UnknownSessionException;
 import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
 import org.apache.shiro.subject.PrincipalCollection;
 import org.apache.shiro.subject.support.DefaultSubjectContext;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;







 @Deprecated
 public class CacheSessionDAO
   extends EnterpriseCacheSessionDAO
   implements SessionDAO
 {
   private Logger logger = LoggerFactory.getLogger(getClass());






   protected Serializable doCreate(Session session) {
     HttpServletRequest request = SpringUtil.getRequest();
     if (request != null) {
       String str = request.getServletPath();
     }




     super.doCreate(session);
     this.logger.debug("doCreate {} {}", session, (request != null) ? request.getRequestURI() : "");
     return session.getId();
   }


   protected void doDelete(Session session) {
     if (session == null || session.getId() == null) {
       return;
     }

     super.doDelete(session);
     this.logger.debug("delete {} ", session.getId());
   }


   protected Session doReadSession(Serializable sessionId) {
     this.logger.debug("doReadSession {} ", sessionId);
     return super.doReadSession(sessionId);
   }


   protected void doUpdate(Session session) {
     this.logger.debug("doUpdate {} ", session.getId());

     if (session == null || session.getId() == null) {
       return;
     }

     HttpServletRequest request = SpringUtil.getRequest();
     if (request != null) {
       String str = request.getServletPath();
     }














     super.doUpdate(session);
   }



   public Session readSession(Serializable sessionId) throws UnknownSessionException {
     try {
       Session s = null;
       HttpServletRequest request = SpringUtil.getRequest();
       if (request != null) {
         String uri = request.getServletPath();





         s = (Session)request.getAttribute("session_" + sessionId);
       }
       if (s != null) {
         return s;
       }

       Session session = super.readSession(sessionId);


       if (request != null && session != null) {
         request.setAttribute("session_" + sessionId, session);
       }

       return session;
     } catch (UnknownSessionException e) {
       return null;
     }
   }







   public Collection<Session> getActiveSessions(boolean includeLeave) {
     this.logger.debug("getActiveSessions 获取活动会话");
     return getActiveSessions(includeLeave, null, null);
   }









   public Collection<Session> getActiveSessions(boolean includeLeave, Object principal, Session filterSession) {
     this.logger.debug("getActiveSessions");

     if (includeLeave && principal == null) {
       return getActiveSessions();
     }
     Set<Session> sessions = new HashSet<>();
     for (Session session : getActiveSessions()) {
       boolean isActiveSession = false;



       if (principal != null) {
         PrincipalCollection pc = (PrincipalCollection)session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
         if (principal.toString().equals((pc != null) ? pc.getPrimaryPrincipal().toString() : "")) {
           isActiveSession = true;
         }
       }

       if (filterSession != null && filterSession.getId().equals(session.getId())) {
         isActiveSession = false;
       }
       if (isActiveSession) {
         sessions.add(session);
       }
     }
     return sessions;
   }
 }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\security\session\CacheSessionDAO.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */