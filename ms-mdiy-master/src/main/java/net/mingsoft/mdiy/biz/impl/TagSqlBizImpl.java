 package net.mingsoft.mdiy.biz.impl;

 import java.util.List;
 import net.mingsoft.base.biz.impl.BaseBizImpl;
 import net.mingsoft.base.dao.IBaseDao;
 import net.mingsoft.base.entity.BaseEntity;
 import net.mingsoft.mdiy.biz.ITagSqlBiz;
 import net.mingsoft.mdiy.dao.ITagSqlDao;
 import net.mingsoft.mdiy.entity.TagSqlEntity;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;





























 @Service("tagSqlBizImpl")
 public class TagSqlBizImpl
   extends BaseBizImpl
   implements ITagSqlBiz
 {
   @Autowired
   private ITagSqlDao tagSqlDao;

   protected IBaseDao getDao() { return (IBaseDao)this.tagSqlDao; }




   public List<TagSqlEntity> query(int tagId) {
     TagSqlEntity sql = new TagSqlEntity();
     sql.setTagId(Integer.valueOf(tagId));
     return this.tagSqlDao.query((BaseEntity)sql);
   }
 }


