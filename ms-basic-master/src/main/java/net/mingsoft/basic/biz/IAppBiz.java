package net.mingsoft.basic.biz;

import net.mingsoft.basic.entity.AppEntity;

public interface IAppBiz extends IBasicBiz {
  int countByUrl(String paramString);
  
  AppEntity getByManagerId(int paramInt);
  
  AppEntity getByUrl(String paramString);
}


