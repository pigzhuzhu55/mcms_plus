package net.mingsoft.mdiy.biz;

import java.util.List;
import net.mingsoft.base.biz.IBaseBiz;
import net.mingsoft.mdiy.entity.TagSqlEntity;

public interface ITagSqlBiz extends IBaseBiz {
  List<TagSqlEntity> query(int paramInt);
}


