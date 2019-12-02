 package net.mingsoft.basic.security;

 import java.util.List;
 import net.mingsoft.base.entity.BaseEntity;
 import net.mingsoft.basic.biz.IManagerBiz;
 import net.mingsoft.basic.biz.IModelBiz;
 import net.mingsoft.basic.entity.ManagerEntity;
 import net.mingsoft.basic.entity.ModelEntity;
 import org.apache.commons.lang3.StringUtils;
 import org.apache.shiro.authc.AuthenticationException;
 import org.apache.shiro.authc.AuthenticationInfo;
 import org.apache.shiro.authc.AuthenticationToken;
 import org.apache.shiro.authc.SimpleAuthenticationInfo;
 import org.apache.shiro.authc.UsernamePasswordToken;
 import org.apache.shiro.authc.credential.CredentialsMatcher;
 import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
 import org.apache.shiro.authz.AuthorizationInfo;
 import org.apache.shiro.authz.SimpleAuthorizationInfo;
 import org.apache.shiro.cache.Cache;
 import org.apache.shiro.realm.AuthorizingRealm;
 import org.apache.shiro.subject.PrincipalCollection;
 import org.apache.shiro.subject.SimplePrincipalCollection;
 import org.springframework.beans.factory.annotation.Autowired;


 public class BaseAuthRealm
   extends AuthorizingRealm
 {
   @Autowired
   private IManagerBiz managerBiz;
   @Autowired
   private IModelBiz modelBiz;

   public BaseAuthRealm() {
     setAuthenticationTokenClass(UsernamePasswordToken.class);

     setCredentialsMatcher((CredentialsMatcher)new SimpleCredentialsMatcher());
   }




   protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
     String loginName = principalCollection.fromRealm(getName()).iterator().next().toString();
     ManagerEntity newManager = new ManagerEntity();
     newManager.setManagerName(loginName);
     ManagerEntity manager = (ManagerEntity)this.managerBiz.getEntity((BaseEntity)newManager);
     if (null == manager) {
       return null;
     }
     SimpleAuthorizationInfo result = new SimpleAuthorizationInfo();

     List<BaseEntity> models = this.modelBiz.queryModelByRoleId(manager.getManagerRoleID());
     for (BaseEntity e : models) {
       ModelEntity me = (ModelEntity)e;
       if (!StringUtils.isEmpty(me.getModelUrl())) {
         result.addStringPermission(me.getModelUrl());
       }
     }
     return (AuthorizationInfo)result;
   }






   protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
     UsernamePasswordToken upToken = (UsernamePasswordToken)token;
     ManagerEntity newManager = new ManagerEntity();
     newManager.setManagerName(upToken.getUsername());
     ManagerEntity manager = (ManagerEntity)this.managerBiz.getEntity((BaseEntity)newManager);
     if (manager != null) {
       return (AuthenticationInfo)new SimpleAuthenticationInfo(manager.getManagerName(), manager.getManagerPassword(), getName());
     }
     return null;
   }




   public void clearCachedAuthorizationInfo(String principal) {
     SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
     clearCachedAuthorizationInfo((PrincipalCollection)principals);
   }




   public void clearAllCachedAuthorizationInfo() {
     Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
     if (cache != null)
       for (Object key : cache.keys())
         cache.remove(key);
   }
 }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\security\BaseAuthRealm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */