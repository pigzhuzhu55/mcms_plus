package net.mingsoft.basic.biz;

import net.mingsoft.base.biz.IBaseBiz;
import net.mingsoft.basic.entity.FileEntity;

public interface IFileBiz extends IBaseBiz {
  int saveFile(FileEntity paramFileEntity);
  
  void updateFile(FileEntity paramFileEntity);
}


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\biz\IFileBiz.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */