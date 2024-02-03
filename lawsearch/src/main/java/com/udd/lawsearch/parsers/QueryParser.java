package com.udd.lawsearch.parsers;

import com.udd.lawsearch.elastic.search.dtos.BasicSearchDto;
import com.udd.lawsearch.elastic.search.dtos.BoolQueryDto;
import com.udd.lawsearch.elastic.search.dtos.SearchConditionWrapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QueryParser {
    // Parses the input query and returns the root SearchConditionWrapper
    public SearchConditionWrapper parse(String query) {
        // Normalize the query to ensure consistent formatting
        query = normalizeQuery(query);

        // Parse the query recursively starting from the highest level
        return parseCondition(query);
    }

    private SearchConditionWrapper parseCondition(String query) {
        // Check for the existence of top-level AND/OR/NOT operators
        if (query.contains(" OR ")) {
            return parseBooleanQuery("OR", query.split(" OR "));
        } else if (query.contains(" AND ")) {
            return parseBooleanQuery("AND", query.split(" AND "));
        } else if (query.startsWith(" NOT ")) {
            return parseNotQuery(query.substring(4));
        } else {
            // Base case: no logical operators, so it must be a basic search condition
            return parseBasicSearch(query);
        }
    }

    private SearchConditionWrapper parseNotQuery(String query) {
        BoolQueryDto booleanQueryDto = new BoolQueryDto();
        booleanQueryDto.setOperator("NOT");
        List<SearchConditionWrapper> conditions = new ArrayList<>();

        // The NOT operator should only have a single condition following it
        conditions.add(parseCondition(query.trim()));

        booleanQueryDto.setBooleanQueryFields(conditions);
        return new SearchConditionWrapper(null, booleanQueryDto);
    }

    private SearchConditionWrapper parseBooleanQuery(String operator, String[] parts) {
        BoolQueryDto booleanQueryDto = new BoolQueryDto();
        booleanQueryDto.setOperator(operator);
        List<SearchConditionWrapper> conditions = new ArrayList<>();

        for (String part : parts) {
            conditions.add(parseCondition(part.trim()));
        }

        booleanQueryDto.setBooleanQueryFields(conditions);
        return new SearchConditionWrapper(null, booleanQueryDto);
    }

    private SearchConditionWrapper parseBasicSearch(String query) {
        // Assuming the basic search is in the format "field:value"
        String[] parts = query.split(":");
        String field = parts[0].trim();
        String value = parts[1].trim().replaceAll("^\"|\"$", ""); // Remove surrounding quotes

        BasicSearchDto basicSearchDto = new BasicSearchDto(field, value);
        return new SearchConditionWrapper(basicSearchDto, null);
    }

    private String normalizeQuery(String query) {
        // This method should implement any normalization logic you need, such as removing unnecessary spaces, handling special characters, etc.
        return query.replaceAll("\\s{2,}", " ").trim();
    }
}
