package net.mingsoft.mdiy.biz;

import java.util.List;
import net.mingsoft.base.biz.IBaseBiz;
import net.mingsoft.mdiy.entity.FormFieldEntity;

public interface IFormFieldBiz extends IBaseBiz {
  List<FormFieldEntity> queryByDiyFormId(int paramInt);
  
  FormFieldEntity getByFieldName(Integer paramInteger, String paramString);
}


