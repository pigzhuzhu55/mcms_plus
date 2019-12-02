 package net.mingsoft.basic.action;

 import cn.hutool.core.io.FileUtil;
 import com.alibaba.fastjson.JSONArray;
 import io.swagger.annotations.Api;
 import io.swagger.annotations.ApiImplicitParam;
 import io.swagger.annotations.ApiImplicitParams;
 import io.swagger.annotations.ApiOperation;
 import java.io.File;
 import java.util.ArrayList;
 import java.util.List;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import net.mingsoft.base.constant.e.BaseEnum;
 import net.mingsoft.basic.bean.EUListBean;
 import net.mingsoft.basic.biz.IColumnBiz;
 import net.mingsoft.basic.constant.ModelCode;
 import net.mingsoft.basic.entity.ColumnEntity;
 import net.mingsoft.basic.util.BasicUtil;
 import net.mingsoft.basic.util.StringUtil;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.ModelMap;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.ModelAttribute;
 import org.springframework.web.bind.annotation.PathVariable;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.ResponseBody;
 import springfox.documentation.annotations.ApiIgnore;
























 @Api("通用栏目分类")
 @Controller("basicColumnAction")
 @RequestMapping({"/${ms.manager.path}/column"})
 public class ColumnAction
   extends BaseAction
 {
   @Autowired
   private IColumnBiz columnBiz;

   @ApiOperation("返回主界面index")
   @GetMapping({"/index"})
   public String index(HttpServletResponse response, HttpServletRequest request, ModelMap model) { return "/basic/column/index"; }







   @ApiOperation("栏目添加跳转页面")
   @GetMapping({"/add"})
   public String add(HttpServletRequest request, ModelMap model) {
     int appId = BasicUtil.getAppId();
     List<ColumnEntity> list = this.columnBiz.queryAll(appId, getModelCodeId(request));
     ColumnEntity columnSuper = new ColumnEntity();
     model.addAttribute("appId", Integer.valueOf(appId));
     model.addAttribute("columnSuper", columnSuper);
     model.addAttribute("column", new ColumnEntity());
     model.addAttribute("listColumn", JSONArray.toJSONString(list));
     return "/basic/column/form";
   }








   protected boolean checkForm(ColumnEntity column, HttpServletResponse response) {
     if (StringUtil.isBlank(column.getCategoryTitle())) {
       outJson(response, (BaseEnum)ModelCode.COLUMN, false, getResString("err.empty", new String[] { getResString("categoryTitle") }));
       return false;
     }

     if (!StringUtil.checkLength(column.getCategoryTitle(), 1, 31)) {
       outJson(response, (BaseEnum)ModelCode.COLUMN, false, getResString("err.length", new String[] { getResString("categoryTitle"), "1", "30" }));
       return false;
     }

     if (StringUtil.isBlank(column.getColumnType() + "")) {
       outJson(response, (BaseEnum)ModelCode.COLUMN, false, getResString("err.empty", new String[] { getResString("columnType") }));
       return false;
     }

     if (column.getCategoryId() == column.getCategoryCategoryId()) {
       outJson(response, (BaseEnum)ModelCode.COLUMN, false, getResString("err.same", new String[] { getResString("columnCategoryIdCompared") }));
       return false;
     }
     return true;
   }














   @ApiOperation("批量删除栏目表")
   @ApiImplicitParam(name = "ids", value = "栏目编号", required = true, paramType = "query")
   @PostMapping({"/delete"})
   @ResponseBody
   public void delete(HttpServletResponse response, HttpServletRequest request) {
     int[] ids = BasicUtil.getInts("ids", ",");
     for (int i = 0; i < ids.length; i++) {
       if (this.columnBiz.getEntity(ids[i]) != null) {
         this.columnBiz.deleteCategory(ids[i]);
       }
     }
     outJson(response, true);
   }










   @ApiOperation("栏目更新页面跳转")
   @ApiImplicitParam(name = "columnId", value = "栏目ID", required = true, paramType = "path")
   @GetMapping({"/{columnId}/edit"})
   public String edit(@PathVariable @ApiIgnore int columnId, HttpServletRequest request, @ApiIgnore ModelMap model) {
     int appId = BasicUtil.getAppId();
     List<ColumnEntity> list = new ArrayList<>();

     list = this.columnBiz.queryAll(appId, getModelCodeId(request));

     ColumnEntity column = (ColumnEntity)this.columnBiz.getEntity(columnId);
     model.addAttribute("appId", Integer.valueOf(appId));
     model.addAttribute("column", column);
     model.addAttribute("columnc", Integer.valueOf(column.getCategoryId()));
     ColumnEntity columnSuper = new ColumnEntity();

     if (column.getCategoryCategoryId() != 0) {
       columnSuper = (ColumnEntity)this.columnBiz.getEntity(column.getCategoryCategoryId());
     }
     model.addAttribute("columnSuper", columnSuper);
     model.addAttribute("listColumn", JSONArray.toJSONString(list));
     return "/basic/column/form";
   }







   @ApiOperation("栏目首页面列表显示")
   @GetMapping({"/list"})
   public void list(@ModelAttribute @ApiIgnore ColumnEntity column, HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model) {
     int websiteId = BasicUtil.getAppId();

     List list = this.columnBiz.queryAll(websiteId, getModelCodeId(request));
     EUListBean _list = new EUListBean(list, list.size());
     outJson(response, JSONArray.toJSONString(_list, new com.alibaba.fastjson.serializer.SerializeFilter[0]));
   }

















   @ApiOperation("栏目添加")
   @ApiImplicitParams({@ApiImplicitParam(name = "categoryTitle", value = "栏目标题", required = true, paramType = "query"), @ApiImplicitParam(name = "columnType", value = "栏目属性 1，COLUMN_TYPE_LIST 代表最终栏目列表 2， COLUMN_TYPE_COVER代表频道封面", required = true, paramType = "query"), @ApiImplicitParam(name = "columnKeyword", value = "栏目简介", required = false, paramType = "query"), @ApiImplicitParam(name = "columnDescrip", value = "栏目关键字的扩展", required = false, paramType = "query"), @ApiImplicitParam(name = "columnUrl", value = "如果为最终栏目列表，则保持栏目列表的地址,如果为外部链接，则保存外部链接的地址", required = false, paramType = "query"), @ApiImplicitParam(name = "columnListUrl", value = "最终列表栏目的列表模板地址", required = false, paramType = "query"), @ApiImplicitParam(name = "columnContentModelId", value = "栏目类型，直接影响栏目发布的表单样式", required = false, paramType = "query"), @ApiImplicitParam(name = "columnPath", value = "栏目保存路径", required = false, paramType = "query")})
   @PostMapping({"/save"})
   public void save(@ModelAttribute @ApiIgnore ColumnEntity column, HttpServletRequest request, HttpServletResponse response) {
     if (!checkForm(column, response)) {
       return;
     }
     this.columnBiz.save(column, getModelCodeId(request), getManagerId(request));
     outJson(response, (BaseEnum)ModelCode.COLUMN, true, null, JSONArray.toJSONString(Integer.valueOf(column.getCategoryId())));
   }

















   @ApiOperation("更新栏目")
   @ApiImplicitParams({@ApiImplicitParam(name = "categoryId", value = "类别的编号", required = false, paramType = "query"), @ApiImplicitParam(name = "columnKeyword", value = "栏目简介", required = false, paramType = "query"), @ApiImplicitParam(name = "columnDescrip", value = "栏目关键字的扩展", required = false, paramType = "query"), @ApiImplicitParam(name = "columnType", value = "栏目属性", required = false, paramType = "query"), @ApiImplicitParam(name = "columnUrl", value = "如果为最终栏目列表，则保持栏目列表的地址,如果为外部链接，则保存外部链接的地址", required = false, paramType = "query"), @ApiImplicitParam(name = "columnListUrl", value = "最终列表栏目的列表模板地址", required = false, paramType = "query"), @ApiImplicitParam(name = "columnContentModelId", value = "栏目类型，直接影响栏目发布的表单样式", required = false, paramType = "query"), @ApiImplicitParam(name = "columnPath", value = "栏目保存路径", required = false, paramType = "query"), @ApiImplicitParam(name = "categorySmallImg", value = "缩略图", required = false, paramType = "query")})
   @PostMapping({"/update"})
   @ResponseBody
   public void update(@ModelAttribute @ApiIgnore ColumnEntity column, HttpServletRequest request, HttpServletResponse response) {
     ColumnEntity _column = (ColumnEntity)this.columnBiz.getEntity(column.getCategoryId());
     String categorySmallImg = column.getCategorySmallImg();
     String _categorySmallImg = _column.getCategorySmallImg();

     if (!StringUtil.isBlank(_categorySmallImg) && (
       !_categorySmallImg.equals(categorySmallImg) || StringUtil.isBlank(categorySmallImg))) {

       String categorySmallImgs = _categorySmallImg.replace("/", File.separator);

       String path = BasicUtil.getRealPath(categorySmallImgs);
       FileUtil.del(path);
     }

     this.columnBiz.update(column, getModelCodeId(request), getManagerId(request));
     outJson(response, (BaseEnum)ModelCode.COLUMN, true, null, JSONArray.toJSONString(Integer.valueOf(column.getCategoryId())));
   }
 }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\action\ColumnAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */