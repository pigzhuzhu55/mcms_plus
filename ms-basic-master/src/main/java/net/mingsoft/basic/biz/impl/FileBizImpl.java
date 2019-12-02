 package net.mingsoft.basic.biz.impl;

 import net.mingsoft.base.biz.impl.BaseBizImpl;
 import net.mingsoft.base.dao.IBaseDao;
 import net.mingsoft.base.entity.BaseEntity;
 import net.mingsoft.basic.biz.IFileBiz;
 import net.mingsoft.basic.dao.IFileDao;
 import net.mingsoft.basic.entity.FileEntity;
 import net.mingsoft.basic.util.BasicUtil;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;































 @Service("fileBizImpl")
 public class FileBizImpl
   extends BaseBizImpl
   implements IFileBiz
 {
   @Autowired
   private IFileDao fileDao;

   protected IBaseDao getDao() { return (IBaseDao)this.fileDao; }


   public int saveFile(FileEntity file) {
     file.setAppId(Integer.valueOf(BasicUtil.getAppId()));
     this.fileDao.saveEntity((BaseEntity)file);
     return saveEntity((BaseEntity)file);
   }



   public void updateFile(FileEntity file) {
     file.setAppId(Integer.valueOf(BasicUtil.getAppId()));
     this.fileDao.updateEntity((BaseEntity)file);
     updateEntity((BaseEntity)file);
   }
 }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\biz\impl\FileBizImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */