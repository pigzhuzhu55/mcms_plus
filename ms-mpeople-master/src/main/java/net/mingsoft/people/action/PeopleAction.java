 package net.mingsoft.people.action;

 import io.swagger.annotations.Api;
 import io.swagger.annotations.ApiOperation;
 import java.util.List;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import net.mingsoft.base.constant.e.BaseEnum;
 import net.mingsoft.base.entity.BaseEntity;
 import net.mingsoft.people.biz.IPeopleBiz;
 import net.mingsoft.people.constant.ModelCode;
 import net.mingsoft.people.constant.e.PeopleEnum;
 import net.mingsoft.people.entity.PeopleUserEntity;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Controller;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestBody;
 import org.springframework.web.bind.annotation.RequestMapping;










































 @Api("用户基础接口")
 @Controller
 @RequestMapping({"/${ms.manager.path}/people"})
 public class PeopleAction
   extends BaseAction
 {
   @Autowired
   private IPeopleBiz peopleBiz;

   @ApiOperation("更新用户状态接口")
   @PostMapping({"/updateState"})
   public void updateState(@RequestBody List<PeopleUserEntity> peoples, HttpServletRequest request, HttpServletResponse response) {
     if (peoples.size() <= 0) {
       outJson(response, (BaseEnum)ModelCode.PEOPLE, false);
       return;
     }
     for (int i = 0; i < peoples.size(); i++) {
       if (((PeopleUserEntity)peoples.get(i)).getPeopleState().intValue() == PeopleEnum.STATE_CHECK.toInt()) {
         ((PeopleUserEntity)peoples.get(i)).setPeopleStateEnum((BaseEnum)PeopleEnum.STATE_NOT_CHECK);
       } else {
         ((PeopleUserEntity)peoples.get(i)).setPeopleStateEnum((BaseEnum)PeopleEnum.STATE_CHECK);
       }
       this.peopleBiz.updateEntity((BaseEntity)peoples.get(i));
     }
     outJson(response, (BaseEnum)ModelCode.PEOPLE, true);
   }
 }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-mpeople\1.0.11\ms-mpeople-1.0.11.jar!\net\mingsoft\people\action\PeopleAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */