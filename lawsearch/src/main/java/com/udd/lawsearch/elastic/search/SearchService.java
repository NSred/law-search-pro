package com.udd.lawsearch.elastic.search;

import com.udd.lawsearch.elastic.search.dtos.*;

import java.io.IOException;

public interface SearchService {
    SearchResult basicSearch(BasicSearchDto dto) throws IOException, RuntimeException;
    SearchResult locationSearch(LocationSearchDto dto) throws IOException;
    SearchResult advancedSearch(SearchCondition condition) throws IOException;
    LawSearchResult lawSearch(BasicSearchDto dto) throws IOException, RuntimeException;
}
