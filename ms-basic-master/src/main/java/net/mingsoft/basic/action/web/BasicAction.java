 package net.mingsoft.basic.action.web;
 
 import io.swagger.annotations.Api;
 import io.swagger.annotations.ApiImplicitParam;
 import io.swagger.annotations.ApiOperation;
 import java.util.Date;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import net.mingsoft.base.entity.BaseEntity;
 import net.mingsoft.basic.action.BaseAction;
 import net.mingsoft.basic.biz.IBasicBiz;
 import net.mingsoft.basic.biz.IBasicLogBiz;
 import net.mingsoft.basic.constant.e.LogEnum;
 import net.mingsoft.basic.entity.BasicEntity;
 import net.mingsoft.basic.entity.BasicLogEntity;
 import net.mingsoft.basic.util.BasicUtil;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Controller;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.PathVariable;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.ResponseBody;
 import springfox.documentation.annotations.ApiIgnore;
 

 @Api("文章解析器接口")
 @Controller("webBasicAction")
 @RequestMapping({"/basic/"})
 public class BasicAction
         extends BaseAction
 {
   @Autowired
   private IBasicBiz basicBiz;
   @Autowired
   private IBasicLogBiz basicLogBiz;
   
   @ApiOperation("更新文章点击数")
   @ApiImplicitParam(name = "basicId", value = "文章编号", required = true, paramType = "path")
   @GetMapping({"/{basicId}/hit"})
   @ResponseBody
   public void hit(@PathVariable @ApiIgnore int basicId, HttpServletRequest request, HttpServletResponse response) {
     if (basicId <= 0) {
       outString(response, "document.write(0)");
       
       return;
     } 
     BasicEntity basic = (BasicEntity)this.basicBiz.getEntity(basicId);
     
     if (basic == null) {
       outString(response, "document.write(0)");
       return;
     } 
     int appId = BasicUtil.getAppId();
     
     if (basic.getBasicAppId() != appId) {
       outString(response, "document.write(0)");
       return;
     } 
     boolean isShow = BasicUtil.getBoolean("isShow").booleanValue();
     if (isShow) {
       outString(response, "document.write(" + basic.getBasicHit() + ")");
       return;
     } 
     BasicLogEntity basicLog = new BasicLogEntity();
     basicLog.setBasicLogAppId(appId);
     basicLog.setBasicLogDatetime(new Date());
     basicLog.setBasicLogIp(BasicUtil.getIp());
     basicLog.setBasicLogBasicId(basicId);
     if (isMobileDevice(request)) {
       basicLog.setBasicLogIsMobile(LogEnum.MOBILE.toInt());
     }
     int isHit = this.basicLogBiz.count(basicLog);
     this.basicBiz.updateHit(basicId, Integer.valueOf(basic.getBasicHit() + 1));
     this.basicLogBiz.saveEntity((BaseEntity)basicLog);
     outString(response, "document.write(" + (basic.getBasicHit() + 1) + ")");
   }
 }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\action\web\BasicAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */