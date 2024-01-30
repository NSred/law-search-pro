package com.udd.lawsearch.elastic.contract;

import com.udd.lawsearch.government.dto.GovernmentDto;

public interface ContractIndexService {
    void create(GovernmentDto govDto, String govTypeName, double lat, double lon) throws Exception;
}
