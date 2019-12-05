package net.mingsoft.basic.biz;

import net.mingsoft.base.biz.IBaseBiz;
import net.mingsoft.basic.entity.FileEntity;

public interface IFileBiz extends IBaseBiz {
  int saveFile(FileEntity paramFileEntity);
  
  void updateFile(FileEntity paramFileEntity);
}


