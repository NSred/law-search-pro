package com.udd.lawsearch.elastic.search;

import com.udd.lawsearch.elastic.search.dtos.BasicSearchDto;
import com.udd.lawsearch.elastic.search.dtos.LocationSearchDto;
import com.udd.lawsearch.elastic.search.dtos.SearchResult;

import java.io.IOException;

public interface SearchService {
    SearchResult basicSearch(BasicSearchDto dto) throws IOException, RuntimeException;
    SearchResult locationSearch(LocationSearchDto dto) throws IOException;
}
