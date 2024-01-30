package com.udd.lawsearch.elastic.search.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationSearchDto {
    private String address;
    private Double radius;
}
