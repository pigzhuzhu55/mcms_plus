package net.mingsoft.basic.biz;

import net.mingsoft.basic.entity.AppEntity;

public interface IAppBiz extends IBasicBiz {
  int countByUrl(String paramString);
  
  AppEntity getByManagerId(int paramInt);
  
  AppEntity getByUrl(String paramString);
}


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\biz\IAppBiz.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */