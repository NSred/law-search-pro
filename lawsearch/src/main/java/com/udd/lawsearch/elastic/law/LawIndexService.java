package com.udd.lawsearch.elastic.law;

import com.udd.lawsearch.government.dto.GovernmentDto;

public interface LawIndexService {
    void create(GovernmentDto govDto, String govTypeName) throws Exception;
}
