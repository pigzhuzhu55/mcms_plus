 package net.mingsoft.mdiy.action.web;

 import com.alibaba.fastjson.JSONObject;
 import io.swagger.annotations.Api;
 import io.swagger.annotations.ApiImplicitParam;
 import io.swagger.annotations.ApiOperation;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import net.mingsoft.basic.action.BaseAction;
 import net.mingsoft.basic.bean.EUListBean;
 import net.mingsoft.basic.util.BasicUtil;
 import net.mingsoft.mdiy.biz.IFormBiz;
 import net.mingsoft.mdiy.dao.IFormFieldDao;
 import net.mingsoft.mdiy.entity.FormEntity;
 import org.apache.commons.lang3.math.NumberUtils;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Controller;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.PathVariable;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.ResponseBody;
 import springfox.documentation.annotations.ApiIgnore;



























 @Api("通用自定义表单")
 @Controller("webDiyForm")
 @RequestMapping({"/mdiy/diyForm", "/mdiy/form"})
 public class FormAction
   extends BaseAction
 {
   @Autowired
   IFormBiz diyFormBiz;
   @Autowired
   IFormBiz formBiz;
   private static final String ID = "ID";
   @Autowired
   IFormFieldDao formFieldDao;

   @ApiOperation("保存")
   @ApiImplicitParam(name = "idBase64", value = "Base64编码数据", required = true, paramType = "path")
   @PostMapping({"{idBase64}"})
   @ResponseBody
   public void save(@PathVariable("idBase64") @ApiIgnore String idBase64, HttpServletRequest request, HttpServletResponse response) {
     String temp = decryptByAES(request, idBase64);


     if (request.getParameter("rand_code") != null &&
       !checkRandCode(request)) {
       outJson(response, null, false);

       return;
     }

     if (!NumberUtils.isNumber(temp)) {
       outJson(response, null, false);

       return;
     }
     int formId = Integer.parseInt(temp);
     if (formId != 0) {
       this.LOG.debug("fromId:" + formId);
       this.diyFormBiz.saveDiyFormData(formId, BasicUtil.assemblyRequestMap());
       outJson(response, null, true);
     }
   }






   @ApiOperation("提供前端查询自定义表单提交数据")
   @ApiImplicitParam(name = "idBase64", value = "Base64编码数据", required = true, paramType = "path")
   @GetMapping({"{idBase64}/queryData"})
   @ResponseBody
   public void queryData(@PathVariable("idBase64") @ApiIgnore String idBase64, HttpServletRequest request, HttpServletResponse response) {
     String temp = decryptByAES(request, idBase64);

     String where = request.getParameter("where");
     Map<String, Object> whereMap = new HashMap<>();
     whereMap = BasicUtil.assemblyRequestMap();








     int formId = Integer.parseInt(temp);

     if (!NumberUtils.isNumber(temp)) {
       outJson(response, null, false);
       return;
     }
     if (formId != 0) {
       int appId = BasicUtil.getAppId();
       FormEntity dfe = (FormEntity)this.formBiz.getEntity(formId);
       Map map = null;
       if (dfe != null) {

         map = this.diyFormBiz.queryDiyFormData(formId, appId, whereMap);
         if (map != null) {
           map.remove("fields");
           BasicUtil.startPage();
           List list = this.formBiz.queryBySQL(dfe.getFormTableName(), null, map);
           outJson(response, JSONObject.toJSONString(new EUListBean(list, (int)BasicUtil.endPage(list).getTotal())));
           return;
         }
       }
     }
     outJson(response, null, false);
   }
 }


