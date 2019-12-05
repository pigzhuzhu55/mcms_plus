package net.mingsoft.mdiy.biz;

import java.util.List;
import java.util.Map;
import net.mingsoft.base.biz.IBaseBiz;

public interface IFormBiz extends IBaseBiz {
  void saveDiyFormData(int paramInt, Map paramMap);
  
  Map queryDiyFormData(int paramInt1, int paramInt2, Map paramMap);
  
  void deleteQueryDiyFormData(int paramInt1, int paramInt2);
  
  int countDiyFormData(int paramInt1, int paramInt2);
  
  void createDiyFormTable(String paramString, Map<Object, List> paramMap);
}


