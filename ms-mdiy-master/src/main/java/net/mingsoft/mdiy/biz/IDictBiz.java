package net.mingsoft.mdiy.biz;

import net.mingsoft.base.biz.IBaseBiz;
import net.mingsoft.mdiy.entity.DictEntity;

public interface IDictBiz extends IBaseBiz {
  DictEntity getByTypeAndLabel(String paramString1, String paramString2);
}


