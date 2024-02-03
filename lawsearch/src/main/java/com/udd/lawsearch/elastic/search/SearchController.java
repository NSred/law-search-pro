package com.udd.lawsearch.elastic.search;

import com.udd.lawsearch.elastic.search.dtos.*;
import com.udd.lawsearch.parsers.QueryParser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/search")
public class SearchController {
    private final SearchService searchService;
    private final QueryParser queryParser;

    @PostMapping("/laws")
    public ResponseEntity<LawSearchResult> lawsSearch(@RequestBody String content) throws IOException {
        BasicSearchDto dto = new BasicSearchDto();
        dto.setField("content");
        dto.setValue(content);
        LawSearchResult response = searchService.lawSearch(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/basic")
    public ResponseEntity<SearchResult> basicSearch(@RequestBody BasicSearchDto dto) throws IOException {
        SearchResult response = searchService.basicSearch(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/location")
    public ResponseEntity<SearchResult> locationSearch(@RequestBody LocationSearchDto dto) throws IOException {
        SearchResult response = searchService.locationSearch(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/advanced")
    public ResponseEntity<SearchResult> advancedSearch(@RequestBody String query) throws IOException {

        SearchConditionWrapper wrapper = queryParser.parse(query);
        SearchResult results = searchService.advancedSearch(wrapper.getCondition());
        return new ResponseEntity<>(results, HttpStatus.OK);
    }
}
