package net.mingsoft.mdiy.dao;

import java.util.List;
import net.mingsoft.base.dao.IBaseDao;
import net.mingsoft.mdiy.entity.FormFieldEntity;
import org.apache.ibatis.annotations.Param;

public interface IFormFieldDao extends IBaseDao {
  List<FormFieldEntity> queryByDiyFormId(@Param("diyFormFieldFormId") int paramInt);
  
  FormFieldEntity getByFieldName(@Param("diyFormFieldFormId") Integer paramInteger, @Param("diyFormFieldFieldName") String paramString);
}


