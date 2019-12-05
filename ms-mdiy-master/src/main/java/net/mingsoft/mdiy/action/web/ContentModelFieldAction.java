 package net.mingsoft.mdiy.action.web;

 import com.alibaba.fastjson.serializer.SerializeFilter;
 import io.swagger.annotations.Api;
 import io.swagger.annotations.ApiImplicitParam;
 import io.swagger.annotations.ApiOperation;
 import java.util.List;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import net.mingsoft.base.filter.DateValueFilter;
 import net.mingsoft.base.filter.DoubleValueFilter;
 import net.mingsoft.base.util.JSONArray;
 import net.mingsoft.basic.action.BaseAction;
 import net.mingsoft.basic.bean.EUListBean;
 import net.mingsoft.basic.biz.IColumnBiz;
 import net.mingsoft.basic.util.BasicUtil;
 import net.mingsoft.mdiy.biz.IContentModelBiz;
 import net.mingsoft.mdiy.biz.IContentModelFieldBiz;
 import net.mingsoft.mdiy.constant.e.ContentModelFieldEnum;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.ModelMap;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.PathVariable;
 import org.springframework.web.bind.annotation.RequestMapping;
 import springfox.documentation.annotations.ApiIgnore;
































 @Api("自定义模型字段接口")
 @Controller("webContentModelField")
 @RequestMapping({"/mdiy/contentModel/contentModelField"})
 public class ContentModelFieldAction
   extends BaseAction
 {
   @Autowired
   private IContentModelFieldBiz contentModelFieldBiz;
   @Autowired
   private IContentModelBiz contentModelBiz;
   @Autowired
   private IColumnBiz columnBiz;

   @ApiOperation("表单列表")
   @ApiImplicitParam(name = "contentModelId", value = "绑定内容模型表ID", required = true, paramType = "path")
   @GetMapping({"/{contentModelId}/list"})
   public void list(@PathVariable @ApiIgnore int contentModelId, HttpServletRequest request, @ApiIgnore ModelMap model, HttpServletResponse response) {
     BasicUtil.startPage();
     List contentModelFieldList = this.contentModelFieldBiz.queryListByCmid(contentModelId);

     model.put("fieldTypes", ContentModelFieldEnum.toMap());
     model.put("contentModelId", Integer.valueOf(contentModelId));
     model.addAttribute("contentModelFieldList", contentModelFieldList);
     outJson(response, JSONArray.toJSONString(new EUListBean(contentModelFieldList, (int)BasicUtil.endPage(contentModelFieldList).getTotal()), new SerializeFilter[] { (SerializeFilter)new DoubleValueFilter(), (SerializeFilter)new DateValueFilter() }));
   }
 }


