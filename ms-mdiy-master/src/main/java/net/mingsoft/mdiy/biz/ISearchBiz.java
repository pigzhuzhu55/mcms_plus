package net.mingsoft.mdiy.biz;

import net.mingsoft.basic.biz.IBasicBiz;
import net.mingsoft.mdiy.entity.SearchEntity;

public interface ISearchBiz extends IBasicBiz {
  SearchEntity getById(int paramInt);
}


