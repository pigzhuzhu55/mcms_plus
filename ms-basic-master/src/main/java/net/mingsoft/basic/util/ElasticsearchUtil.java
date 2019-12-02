 package net.mingsoft.basic.util;

 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import net.mingsoft.basic.elasticsearch.bean.BaseMapping;
 import net.mingsoft.basic.elasticsearch.bean.SearchBean;
 import net.mingsoft.basic.elasticsearch.search.IBaseSearch;
 import org.elasticsearch.index.query.MatchQueryBuilder;
 import org.elasticsearch.index.query.QueryBuilder;
 import org.elasticsearch.index.query.QueryBuilders;
 import org.elasticsearch.search.sort.SortBuilders;
 import org.elasticsearch.search.sort.SortOrder;
 import org.springframework.data.domain.Page;
 import org.springframework.data.domain.PageRequest;
 import org.springframework.data.domain.Pageable;
 import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
 import org.springframework.data.elasticsearch.core.query.IndexQuery;
 import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
 import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
 import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
 import org.springframework.data.elasticsearch.core.query.SearchQuery;












































 public class ElasticsearchUtil
 {
   public static void saveOrUpdate(String id, BaseMapping base) {
     ElasticsearchTemplate elasticsearchTemplate = SpringUtil.getBean(ElasticsearchTemplate.class);
     IndexQuery indexQuery = (new IndexQueryBuilder()).withId(id).withObject(base).build();
     elasticsearchTemplate.index(indexQuery);
   }










   public static void saveOrUpdate(List<BaseMapping> bases) {
     ElasticsearchTemplate elasticsearchTemplate = SpringUtil.getBean(ElasticsearchTemplate.class);
     List<IndexQuery> indexQueries = new ArrayList<>();
     for (int i = 0; i < bases.size(); i++) {

       IndexQuery indexQuery = (new IndexQueryBuilder()).withId(((BaseMapping)bases.get(i)).getId()).withObject(bases.get(i)).build();
       indexQueries.add(indexQuery);
     }
     if (indexQueries.size() > 0) {
       elasticsearchTemplate.bulkIndex(indexQueries);
     }
   }













   public static Map search(IBaseSearch baseSearch, String field, SearchBean search) {
     MatchQueryBuilder mqb = QueryBuilders.matchQuery(field, search.getKeyword());
     PageRequest pageRequest = new PageRequest(search.getPageNo() - 1, search.getPageSize());
     NativeSearchQuery nativeSearchQuery = (new NativeSearchQueryBuilder()).withPageable((Pageable)pageRequest).withSort(SortBuilders.fieldSort(search.getOrderBy()).order(search.getOrder().equalsIgnoreCase("asc") ? SortOrder.ASC : SortOrder.DESC)).withQuery((QueryBuilder)mqb).build();
     Page p = baseSearch.search((SearchQuery)nativeSearchQuery);
     Pager pager = new Pager();
     pager.setCurrentPage(p.getNumber());
     pager.setPageSize(p.getSize());
     pager.setTotalCount(p.getTotalElements());
     pager.setTotalPage(p.getTotalPages());
     Map<Object, Object> map = new HashMap<>();
     map.put("data", p.getContent());
     map.put("page", pager);

     return map;
   }





































   public static SearchQuery buildSearchQuery(String keyword, Map<String, Float> field, String orderBy, SortOrder order, Integer pageNumber, Integer pageSize) { return null; }


   public static class Pager
   {
     private int currentPage = 1;

     private int pageSize;

     private int totalPage;

     private long totalCount;


     public int getCurrentPage() { return this.currentPage; }



     public void setCurrentPage(int currentPage) { this.currentPage = currentPage; }



     public int getPageSize() { return this.pageSize; }



     public void setPageSize(int pageSize) { this.pageSize = pageSize; }



     public int getTotalPage() { return this.totalPage; }



     public void setTotalPage(int totalPage) { this.totalPage = totalPage; }



     public long getTotalCount() { return this.totalCount; }



     public void setTotalCount(long totalCount) { this.totalCount = totalCount; }
   }
 }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basi\\util\ElasticsearchUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */