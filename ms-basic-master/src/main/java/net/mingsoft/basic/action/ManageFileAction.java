 package net.mingsoft.basic.action;

 import io.swagger.annotations.Api;
 import io.swagger.annotations.ApiOperation;
 import java.io.IOException;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.springframework.beans.factory.annotation.Value;
 import org.springframework.stereotype.Controller;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.ResponseBody;




















 @Api("上传文件接口")
 @Controller("ManageFileAction")
 @RequestMapping({"${ms.manager.path}/file"})
 public class ManageFileAction
   extends BaseFileAction
 {
   @Value("${ms.upload.denied}")
   private String uploadFileDenied;

   @ApiOperation("处理post请求上传文件")
   @PostMapping({"/upload"})
   @ResponseBody
   public void upload(BaseFileAction.Bean bean, boolean uploadFloderPath, HttpServletRequest req, HttpServletResponse res) throws IOException {
     BaseFileAction.Config config = new BaseFileAction.Config(bean.getUploadPath(), bean.getFile(), null, uploadFloderPath);
     outString(res, upload(config));
   }
 }


