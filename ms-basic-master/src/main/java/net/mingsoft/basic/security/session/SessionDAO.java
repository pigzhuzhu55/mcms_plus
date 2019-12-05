package net.mingsoft.basic.security.session;

import java.util.Collection;
import org.apache.shiro.session.Session;

@Deprecated
public interface SessionDAO extends org.apache.shiro.session.mgt.eis.SessionDAO {
  Collection<Session> getActiveSessions(boolean paramBoolean);
  
  Collection<Session> getActiveSessions(boolean paramBoolean, Object paramObject, Session paramSession);
}


