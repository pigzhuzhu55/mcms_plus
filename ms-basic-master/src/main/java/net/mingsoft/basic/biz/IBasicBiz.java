package net.mingsoft.basic.biz;

import java.util.List;
import net.mingsoft.base.biz.IBaseBiz;
import net.mingsoft.basic.entity.BasicEntity;

public interface IBasicBiz extends IBaseBiz {
  void deleteBasic(int paramInt);
  
  void deleteBasic(int[] paramArrayOfint);
  
  BasicEntity getBasic(int paramInt);
  
  List<BasicEntity> query(int paramInt);
  
  int saveBasic(BasicEntity paramBasicEntity);
  
  void updateBasic(BasicEntity paramBasicEntity);
  
  void updateHit(int paramInt, Integer paramInteger);
  
  void update(int paramInt, Integer paramInteger, String paramString);
}


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\biz\IBasicBiz.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */