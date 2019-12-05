package net.mingsoft.mdiy.dao;

import java.util.List;
import java.util.Map;
import net.mingsoft.base.dao.IBaseDao;
import org.apache.ibatis.annotations.Param;

public interface IFormDao extends IBaseDao {
  void createDiyFormTable(@Param("table") String paramString, @Param("fileds") Map<Object, List> paramMap);
}


