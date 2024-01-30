package com.udd.lawsearch.elastic.contract;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ContractIndexRepository extends ElasticsearchRepository<ContractIndex, String> {
}
