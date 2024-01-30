package com.udd.lawsearch.elastic.search.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoolQueryField {
    private String field;
    private String value;
    private String operator;
    private boolean isPhrase;
}
