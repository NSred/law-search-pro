package com.udd.lawsearch.elastic.law;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface LawIndexRepository extends ElasticsearchRepository<LawIndex, String> {
}
