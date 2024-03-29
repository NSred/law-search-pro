package com.udd.lawsearch.elastic.search.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResult {
    private List<ResultData> results;
    private Long numberOfResults;
}
