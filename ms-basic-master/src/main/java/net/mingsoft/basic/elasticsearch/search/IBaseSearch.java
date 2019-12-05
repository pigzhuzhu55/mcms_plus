package net.mingsoft.basic.elasticsearch.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface IBaseSearch<T, ID extends java.io.Serializable> extends ElasticsearchRepository<T, ID>, PagingAndSortingRepository<T, ID> {}


