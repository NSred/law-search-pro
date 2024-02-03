package com.udd.lawsearch.elastic.search.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SearchConditionWrapper {
    private BasicSearchDto basicSearchDto;
    private BoolQueryDto booleanQueryDto;

    public SearchCondition getCondition() {
        if (basicSearchDto != null) {
            return basicSearchDto;
        } else if (booleanQueryDto != null) {
            return booleanQueryDto;
        }
        throw new IllegalStateException("No search condition is set");
    }
}
