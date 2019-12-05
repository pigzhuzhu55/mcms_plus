 package net.mingsoft.mdiy.parser;

 import com.alibaba.druid.util.StringUtils;
 import freemarker.cache.StringTemplateLoader;
 import freemarker.cache.TemplateLoader;
 import freemarker.template.Configuration;
 import freemarker.template.Template;
 import freemarker.template.TemplateException;
 import java.io.IOException;
 import java.io.StringWriter;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;
 import java.util.regex.Matcher;
 import java.util.regex.Pattern;
 import net.mingsoft.base.entity.BaseEntity;
 import net.mingsoft.basic.util.SpringUtil;
 import net.mingsoft.mdiy.biz.ITagBiz;
 import net.mingsoft.mdiy.biz.ITagSqlBiz;
 import net.mingsoft.mdiy.entity.TagEntity;
 import net.mingsoft.mdiy.entity.TagSqlEntity;
 import net.mingsoft.mdiy.parser.bean.TagBean;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;


















 public class TagParser
 {
   private String content;
   private Map data = new HashMap<>();



   private NamedParameterJdbcTemplate jdbc;


   protected final Logger LOG = LoggerFactory.getLogger(getClass());


   private ITagBiz tagBiz;


   private ITagSqlBiz tagSqlBiz;


   private List<String> tagKeys = new ArrayList<>();




   private int pageSize;




   private Map<String, TagBean> tags = new HashMap<>();


   public TagParser(String content) { this(content, null); }






   public TagParser(String content, Map map) {
     this.content = content;

     this.tagBiz = (ITagBiz)SpringUtil.getBean(ITagBiz.class);
     this.tagSqlBiz = (ITagSqlBiz)SpringUtil.getBean(ITagSqlBiz.class);
     this.jdbc = (NamedParameterJdbcTemplate)SpringUtil.getBean(NamedParameterJdbcTemplate.class);

     if (map != null) {
       this.data.putAll(map);
     }
     parser();
   }


   public String getContent() { return this.content; }






   public TagParser parser() {
     parserSingle().parserData();
     parserDoublue().parserData();
     this.content = parserFreemarker(this.content);
     rendering();
     return this;
   }


   private TagParser parserData() { return parserData(this.data); }










   private TagParser parserData(Map<String, List> root) {
     Map<Object, Object> map = new HashMap<>();

     for (String tagName : this.tagKeys) {
       if (root.get(tagName) != null) {
         continue;
       }
       TagBean tagBean = this.tags.get(tagName);

       TagEntity tag = new TagEntity();

       tag.setTagName(tagName.split("_")[0].replace("ms:", ""));
       tag = (TagEntity)this.tagBiz.getEntity((BaseEntity)tag);
       if (tag == null) {
         continue;
       }


       if (tagBean != null) {
         Map<Object, Object> tagParams = new HashMap<>();

         tagParams.putAll(this.data);
         tagParams.putAll(tagBean.getParams());
         Map<String, Object> refs = new HashMap<>();

         Object tagRefs = tagBean.getParams().get("refs");
         if (tagRefs != null) {

           Object obj = root.get(tagRefs.toString().trim());
           Map mapData = (Map)obj;
           Iterator it = mapData.keySet().iterator();

           while (it.hasNext()) {
             List<Map> list = (List)mapData.get(it.next());


             for (int i = 0; i < list.size(); i++)
             {
               Map<?, ?> row = list.get(i);

               tagParams.putAll(row);


               List<TagSqlEntity> sqlList = this.tagSqlBiz.query(Integer.parseInt(tag.getId()));
               String sql = rendering(tagParams, ((TagSqlEntity)sqlList.get(0)).getTagSql());


               Map<String, Object> whereParams = new HashMap<>();
               List _list = this.jdbc.queryForList(sql, whereParams);


               root.put(tagName + row.get("id"), _list);


               if (tagBean.getParams().get("ref") != null) {
                 refs.put(tagBean.getParams().get("ref").toString() + row.get("id"), _list);
                 root.put((String)tagBean.getParams().get("ref"), (List) refs);
                 TagBean child = tagBean.getChild();
                 String ftl = "";
                 if (child != null) {
                   String temp = tagBean.getContent().replace(child.getContent(), child.getBeginTag().split(":")[1].trim() + "${item.id}");
                   ftl = parserFreemarker(temp);
                 } else {
                   ftl = parserFreemarker(tagBean.getContent());
                 }
                 String cont = rendering(root, ftl.replace(tagName, tagName + row.get("id")));
                 this.content = this.content.replace(tagName + row.get("id"), cont);
               }
               else if (_list != null) {
                 String ftl = parserFreemarker(tagBean.getContent());
                 String cont = rendering(root, ftl.replace(tagName, tagName + row.get("id")));
                 this.content = this.content.replace(tagName + row.get("id"), cont);
               }

             }

           }

         } else {

           List<TagSqlEntity> sqlList = this.tagSqlBiz.query(Integer.parseInt(tag.getId()));

           String sql = rendering(tagParams, ((TagSqlEntity)sqlList.get(0)).getTagSql());

           Map<String, Object> whereParams = new HashMap<>();
           List list = this.jdbc.queryForList(sql, whereParams);
           root.put(tagName, list);


           if (tagBean.getParams().get("ref") != null) {
             refs.put(tagBean.getParams().get("ref").toString(), list);
             root.put((String)tagBean.getParams().get("ref"), (List) refs);

             TagBean child = tagBean.getChild();

             String temp = tagBean.getContent().replace(child.getContent(), child.getBeginTag().split(":")[1].trim() + "${item.id}");

             String ftl = parserFreemarker(temp);

             String cont = rendering(root, ftl);

             this.content = this.content.replace(tagBean.getContent(), cont);
           }
         }


         if (tagBean.getParams().get("ispaging") != null)
           this.data.remove("ispaging");
         continue;
       }
       List<TagSqlEntity> sqlList = this.tagSqlBiz.query(Integer.parseInt(tag.getId()));
       String sql = rendering(this.data, ((TagSqlEntity)sqlList.get(0)).getTagSql());
       Map<String, Object> whereParams = new HashMap<>();
       List<Map<String, Object>> list = this.jdbc.queryForList(sql, whereParams);

       if (list.size() == 0) {

         Map<String, Object> article = new HashMap<>();
         list.add(article);
       }
       root.put(tagName, (List)list.get(0));
     }


     return this;
   }






   public static int getPageSize(String content) {
     Pattern r = Pattern.compile("\\{ms.*?}");
     Matcher m = r.matcher(content);
     int index = 0;

     TagBean pageTag = null;


     while (m.find()) {
       TagBean tagBean = new TagBean();
       String line = m.group(0);
       String tag = line.split(" ")[0].replace("{", "").replace("}", "");
       String newTag = tag + "_" + index;
       content = content.replace(line, line.replace(tag, newTag));
       tagBean.setBeginTag(line.replace(tag, newTag));
       tagBean.setEndTag("{/" + newTag.split(":")[1] + "}");

       String[] temp = line.replace("}", "").replace(newTag, "").split(" ");

       for (String p : temp) {
         if (!StringUtils.isEmpty(p)) {
           String[] _p = p.split("=");

           if (_p.length == 2) {
             tagBean.getParams().put(p.split("=")[0], p.split("=")[1]);

             if (p.split("=")[0].equalsIgnoreCase("ispaging") && p.split("=")[1].equals("true")) {
               pageTag = tagBean;
             }
           }
         }
       }
       tagBean.setContent(line.replace(tag, newTag));
       if (pageTag != null) {
         break;
       }
     }


     if (pageTag == null) {
       return 0;
     }
     if (pageTag.getParams().get("size") != null) {
       return Integer.parseInt((new StringBuilder()).append(pageTag.getParams().get("size")).append("").toString());
     }
     return 20;
   }















   private TagParser parserDoublue() {
     Pattern r = Pattern.compile("\\{ms.*?}");
     Matcher m = r.matcher(this.content);
     int index = 0;



     while (m.find()) {
       TagBean tagBean = new TagBean();
       String line = m.group(0);

       String tag = line.split(" ")[0].replace("{", "").replace("}", "");

       String newTag = tag + "_" + index;
       this.content = this.content.replace(line, line.replace(tag, newTag));
       tagBean.setBeginTag(line.replace(tag, newTag));
       tagBean.setEndTag("{/" + newTag.split(":")[1] + "}");
       String[] temp = line.replace("}", "").replace(newTag, "").split(" ");
       for (String p : temp) {
         if (!StringUtils.isEmpty(p)) {
           String[] _p = p.split("=");
           if (_p.length == 2) {
             tagBean.getParams().put(p.split("=")[0], p.split("=")[1]);
           }
         }
       }
       tagBean.setContent(line.replace(tag, newTag));

       this.tags.put(newTag.split(":")[1], tagBean);
       this.tagKeys.add(newTag.split(":")[1]);
       index++;
     }




     for (int i = this.tagKeys.size() - 1; i >= 0; i--) {
       String endTag = ((String)this.tagKeys.get(i)).split("_")[0];
       TagBean tag = this.tags.get(this.tagKeys.get(i));
       if (tag != null) {


         String p = tag.getContent().replace("{", "\\{") + "([\\w\\W]*?)\\{/ms:" + endTag + "}";
         Pattern pt = Pattern.compile(p);
         Matcher mt = pt.matcher(this.content);
         while (mt.find()) {
           String temp = mt.group(0).replace("/ms:" + endTag + "}", "/" + (String)this.tagKeys.get(i) + "}");
           TagBean tagBean = this.tags.get(this.tagKeys.get(i));
           tagBean.setContent(temp);
           this.content = this.content.replace(mt.group(0), temp);
         }
       }
     }  return this;
   }







   private String parserFreemarker(String content) {
     for (String str : this.tagKeys) {
       if (str.indexOf("if") > -1) {
         String _if = ((TagBean)this.tags.get(str)).getBeginTag().replace("{ms:" + str, "<#if ").replace("}", ">");
         content = content.replace(((TagBean)this.tags.get(str)).getBeginTag(), _if);
         content = content.replace(((TagBean)this.tags.get(str)).getEndTag(), "</#if>");
         content = content.replace("{/ms:if}", "</#if>");
         continue;
       }
       if (this.tags.get(str) == null) {
         continue;
       }
       content = content.replace(((TagBean)this.tags.get(str)).getBeginTag(), "<#list " + str
           .replace("ms:", "") + " as item>");
       content = content.replace(((TagBean)this.tags.get(str)).getEndTag(), "</#list>");
     }


     content = content.replace("{ms:else}", "<#else>");
     Pattern p = Pattern.compile("\\[.*?/]");
     Matcher mt = p.matcher(content);
     while (mt.find()) {
       String field = mt.group(0);
       if (field.indexOf("field.") > 0) {
         field = mt.group(0).replace("[field.", "${item.").replace("/]", "!''}").replace("]", "!''}");
       }
       else if (field.indexOf("_root:") > 0) {
         field = mt.group(0).replace("[_root:", "${").replace("/]", "}").replace("]", "}");
       }
       content = content.replace(mt.group(0), field);
     }
     return content;
   }










   private TagParser parserSingle() {
     Pattern pattern = Pattern.compile("\\{ms:+[\\S].[^\\{}]+?/}");
     Matcher matcher = pattern.matcher(this.content);
     while (matcher.find()) {
       String text = matcher.group(0);
       this.content = this.content.replace(text, matcher.group(0).replace("ms:", "").replace("{", "${").replace("/}", "!''}"));
       String key = text.split(":")[1].split("\\.")[0];

       this.tagKeys.add(key);
       this.tags.put(key, null);
     }
     return this;
   }






   public String rendering(Map map) {
     if (map != null) {
       this.data.putAll(map);
     }
     return rendering(this.data, this.content);
   }







   public String rendering() { return rendering(this.data, this.content); }










   private String rendering(Map root, String content) {
     Configuration cfg = new Configuration();
     StringTemplateLoader stringLoader = new StringTemplateLoader();
     stringLoader.putTemplate("template", content);
     cfg.setNumberFormat("#");
     cfg.setTemplateLoader((TemplateLoader)stringLoader);
     try {
       Template template = cfg.getTemplate("template", "utf-8");
       StringWriter writer = new StringWriter();
       try {
         template.process(root, writer);
         content = writer.toString();
       } catch (TemplateException e) {
         e.printStackTrace();
         this.LOG.debug(content);
       }
     } catch (IOException e) {
       e.printStackTrace();
       this.LOG.debug(content);
     }
     return content;
   }


   public int getPageSize() { return this.pageSize; }
 }


