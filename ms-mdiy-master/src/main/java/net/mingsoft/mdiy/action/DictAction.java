 package net.mingsoft.mdiy.action;

 import com.alibaba.fastjson.serializer.SerializeFilter;
 import io.swagger.annotations.Api;
 import io.swagger.annotations.ApiImplicitParam;
 import io.swagger.annotations.ApiImplicitParams;
 import io.swagger.annotations.ApiOperation;
 import java.util.List;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import net.mingsoft.base.entity.BaseEntity;
 import net.mingsoft.base.filter.DateValueFilter;
 import net.mingsoft.base.filter.DoubleValueFilter;
 import net.mingsoft.base.util.JSONArray;
 import net.mingsoft.basic.bean.EUListBean;
 import net.mingsoft.basic.util.BasicUtil;
 import net.mingsoft.basic.util.StringUtil;
 import net.mingsoft.mdiy.biz.IDictBiz;
 import net.mingsoft.mdiy.entity.DictEntity;
 import org.apache.shiro.authz.annotation.RequiresPermissions;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.ModelMap;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.ModelAttribute;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestBody;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.ResponseBody;
 import springfox.documentation.annotations.ApiIgnore;


















 @Api("字典表管理接口")
 @Controller
 @RequestMapping({"/${ms.manager.path}/mdiy/dict"})
 public class DictAction
   extends BaseAction
 {
   @Autowired
   private IDictBiz dictBiz;

   @GetMapping({"/index"})
   public String index(HttpServletResponse response, HttpServletRequest request) { return "/mdiy/dict/index"; }
















































   @ApiOperation("查询字典表列表接口")
   @ApiImplicitParams({@ApiImplicitParam(name = "dictValue", value = "数据值", required = false, paramType = "query"), @ApiImplicitParam(name = "dictLabel", value = "标签名", required = false, paramType = "query"), @ApiImplicitParam(name = "dictType", value = "类型", required = false, paramType = "query"), @ApiImplicitParam(name = "dictDescription", value = "描述", required = false, paramType = "query"), @ApiImplicitParam(name = "dictSort", value = "排序（升序）", required = false, paramType = "query"), @ApiImplicitParam(name = "dictParentId", value = "父级编号", required = false, paramType = "query"), @ApiImplicitParam(name = "isChild", value = "子业务关联", required = false, paramType = "query")})
   @GetMapping({"/list"})
   @ResponseBody
   public void list(@ModelAttribute @ApiIgnore DictEntity dict, HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model) {
     dict.setAppId(BasicUtil.getAppId());
     BasicUtil.startPage();
     List dictList = this.dictBiz.query((BaseEntity)dict);
     outJson(response, JSONArray.toJSONString(new EUListBean(dictList, (int)BasicUtil.endPage(dictList).getTotal()), new SerializeFilter[] { (SerializeFilter)new DoubleValueFilter(), (SerializeFilter)new DateValueFilter() }));
   }




   @GetMapping({"/form"})
   public String form(@ModelAttribute DictEntity dict, HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model) {
     if (dict.getDictId() != null) {
       BaseEntity dictEntity = this.dictBiz.getEntity(dict.getDictId().intValue());
       model.addAttribute("dictEntity", dictEntity);
     }

     return "/mdiy/dict/form";
   }





































   @ApiOperation("获取字典详情接口")
   @ApiImplicitParam(name = "dictId", value = "字典编号", required = true, paramType = "query")
   @GetMapping({"/get"})
   @ResponseBody
   public void get(@ModelAttribute @ApiIgnore DictEntity dict, HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model) {
     if (dict.getDictId().intValue() <= 0) {
       outJson(response, null, false, getResString("err.error", new String[] { getResString("dict.id") }));
       return;
     }
     DictEntity _dict = (DictEntity)this.dictBiz.getEntity(dict.getDictId().intValue());
     outJson(response, (BaseEntity)_dict);
   }














































   @ApiOperation("保存字典接口")
   @ApiImplicitParams({@ApiImplicitParam(name = "dictLabel", value = "标签名", required = true, paramType = "query"), @ApiImplicitParam(name = "dictType", value = "类型", required = true, paramType = "query"), @ApiImplicitParam(name = "dictValue", value = "数据值", required = false, paramType = "query"), @ApiImplicitParam(name = "dictDescription", value = "描述", required = false, paramType = "query"), @ApiImplicitParam(name = "dictSort", value = "排序（升序）", required = false, paramType = "query"), @ApiImplicitParam(name = "dictParentId", value = "父级编号", required = false, paramType = "query"), @ApiImplicitParam(name = "isChild", value = "子业务关联", required = false, paramType = "query"), @ApiImplicitParam(name = "dictRemarks", value = "备注信息", required = false, paramType = "query")})
   @PostMapping({"/save"})
   @ResponseBody
   @RequiresPermissions({"mdiy:dict:save"})
   public void save(@ModelAttribute @ApiIgnore DictEntity dict, HttpServletResponse response, HttpServletRequest request) {
     dict.setAppId(BasicUtil.getAppId());
     if (this.dictBiz.getByTypeAndLabel(dict.getDictType(), dict.getDictLabel()) != null) {
       outJson(response, null, false, getResString("diy.dict.type.and.label.repeat"));
       return;
     }
     this.dictBiz.saveEntity((BaseEntity)dict);
     if (StringUtil.isBlank(dict.getDictValue())) {
       dict.setDictValue(dict.getDictId() + "");
       this.dictBiz.updateEntity((BaseEntity)dict);
     }
     outJson(response, true);
   }












   @ApiOperation("批量删除字典")
   @PostMapping({"/delete"})
   @ResponseBody
   @RequiresPermissions({"mdiy:dict:del"})
   public void delete(@RequestBody List<DictEntity> dicts, HttpServletResponse response, HttpServletRequest request) {
     int[] ids = new int[dicts.size()];
     for (int i = 0; i < dicts.size(); i++) {
       ids[i] = ((DictEntity)dicts.get(i)).getDictId().intValue();
     }
     this.dictBiz.delete(ids);
     outJson(response, true);
   }
















































   @ApiOperation("更新字典信息接口")
   @ApiImplicitParams({@ApiImplicitParam(name = "dictId", value = "字典编号", required = true, paramType = "query"), @ApiImplicitParam(name = "dictLabel", value = "标签名", required = true, paramType = "query"), @ApiImplicitParam(name = "dictType", value = "类型", required = true, paramType = "query"), @ApiImplicitParam(name = "dictValue", value = "数据值", required = false, paramType = "query"), @ApiImplicitParam(name = "dictDescription", value = "描述", required = false, paramType = "query"), @ApiImplicitParam(name = "dictSort", value = "排序（升序）", required = false, paramType = "query"), @ApiImplicitParam(name = "dictParentId", value = "父级编号", required = false, paramType = "query"), @ApiImplicitParam(name = "isChild", value = "子业务关联", required = false, paramType = "query"), @ApiImplicitParam(name = "dictRemarks", value = "备注信息", required = false, paramType = "query")})
   @PostMapping({"/update"})
   @ResponseBody
   @RequiresPermissions({"mdiy:dict:update"})
   public void update(@ModelAttribute @ApiIgnore DictEntity dict, HttpServletResponse response, HttpServletRequest request) {
     DictEntity _dict = this.dictBiz.getByTypeAndLabel(dict.getDictType(), dict.getDictLabel());
     if (_dict != null &&
       !_dict.getDictId().equals(dict.getDictId())) {
       outJson(response, null, false, getResString("diy.dict.type.and.label.repeat"));

       return;
     }
     if (StringUtil.isBlank(dict.getDictValue())) {
       dict.setDictValue(null);
     }
     this.dictBiz.updateEntity((BaseEntity)dict);
     outJson(response, true);
   }
 }


