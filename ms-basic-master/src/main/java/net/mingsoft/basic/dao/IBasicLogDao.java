package net.mingsoft.basic.dao;

import java.util.List;
import net.mingsoft.base.dao.IBaseDao;
import net.mingsoft.base.entity.BaseEntity;
import net.mingsoft.basic.entity.BasicLogEntity;
import org.apache.ibatis.annotations.Param;

public interface IBasicLogDao extends IBaseDao {
  List<BaseEntity> query(@Param("basicLog") BasicLogEntity paramBasicLogEntity, @Param("pageNo") int paramInt1, @Param("pageSize") int paramInt2, @Param("orderBy") String paramString, @Param("order") boolean paramBoolean);
  
  int count(@Param("basicLog") BasicLogEntity paramBasicLogEntity);
}


