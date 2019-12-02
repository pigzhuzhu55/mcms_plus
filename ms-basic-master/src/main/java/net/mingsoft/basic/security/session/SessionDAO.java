package net.mingsoft.basic.security.session;

import java.util.Collection;
import org.apache.shiro.session.Session;

@Deprecated
public interface SessionDAO extends org.apache.shiro.session.mgt.eis.SessionDAO {
  Collection<Session> getActiveSessions(boolean paramBoolean);
  
  Collection<Session> getActiveSessions(boolean paramBoolean, Object paramObject, Session paramSession);
}


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\security\session\SessionDAO.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */