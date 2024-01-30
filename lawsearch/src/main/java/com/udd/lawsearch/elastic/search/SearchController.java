package com.udd.lawsearch.elastic.search;

import com.udd.lawsearch.elastic.search.dtos.BasicSearchDto;
import com.udd.lawsearch.elastic.search.dtos.LocationSearchDto;
import com.udd.lawsearch.elastic.search.dtos.SearchResult;
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

    @PostMapping("/basic")
    public ResponseEntity<SearchResult> basicSearch(@RequestBody BasicSearchDto dto) throws IOException {
        SearchResult response = searchService.basicSearch(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/location")
    public ResponseEntity<SearchResult> basicSearch(@RequestBody LocationSearchDto dto) throws IOException {
        SearchResult response = searchService.locationSearch(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
