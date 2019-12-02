package net.mingsoft.basic.elasticsearch.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface IBaseSearch<T, ID extends java.io.Serializable> extends ElasticsearchRepository<T, ID>, PagingAndSortingRepository<T, ID> {}


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\elasticsearch\search\IBaseSearch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */