 package net.mingsoft.mdiy.action.web;

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
 import net.mingsoft.basic.action.BaseAction;
 import net.mingsoft.basic.bean.EUListBean;
 import net.mingsoft.basic.util.BasicUtil;
 import net.mingsoft.mdiy.biz.IDictBiz;
 import net.mingsoft.mdiy.entity.DictEntity;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Controller;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.ModelAttribute;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.ResponseBody;
 import springfox.documentation.annotations.ApiIgnore;

































































 @Api("通用自定义字典")
 @Controller("webDictAction")
 @RequestMapping({"/mdiy/dict"})
 public class DictAction
   extends BaseAction
 {
   @Autowired
   private IDictBiz dictBiz;

   @ApiOperation("查询字典表列表")
   @ApiImplicitParams({@ApiImplicitParam(name = "dictValue", value = "数据值", required = false, paramType = "query"), @ApiImplicitParam(name = "dictLabel", value = "标签名", required = false, paramType = "query"), @ApiImplicitParam(name = "dictType", value = "类型", required = false, paramType = "query"), @ApiImplicitParam(name = "dictDescription", value = "描述", required = false, paramType = "query"), @ApiImplicitParam(name = "dictSort", value = "排序（升序）", required = false, paramType = "query"), @ApiImplicitParam(name = "dictParentId", value = "父级编号", required = false, paramType = "query"), @ApiImplicitParam(name = "isChild", value = "子业务关联", required = false, paramType = "query")})
   @GetMapping({"/list"})
   @ResponseBody
   public void list(@ModelAttribute @ApiIgnore DictEntity dict, HttpServletResponse response, HttpServletRequest request) {
     dict.setAppId(BasicUtil.getAppId());
     BasicUtil.startPage(1, 100, true);
     List dictList = this.dictBiz.query((BaseEntity)dict);
     outJson(response, JSONArray.toJSONString(new EUListBean(dictList, (int)BasicUtil.endPage(dictList).getTotal()), new SerializeFilter[] { (SerializeFilter)new DoubleValueFilter(), (SerializeFilter)new DateValueFilter() }));
   }
 }


