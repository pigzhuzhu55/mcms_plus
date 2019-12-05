package net.mingsoft.basic.biz;

import net.mingsoft.base.biz.IBaseBiz;
import net.mingsoft.basic.entity.BasicLogEntity;

public interface IBasicLogBiz extends IBaseBiz {
  int count(BasicLogEntity paramBasicLogEntity);
}


