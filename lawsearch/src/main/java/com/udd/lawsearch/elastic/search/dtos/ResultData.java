package com.udd.lawsearch.elastic.search.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResultData {
    private String governmentName;
    private String governmentType;
    private String signatoryPersonName;
    private String signatoryPersonSurname;
    private String highlight;
    private String contractId;
    private String fileName;
}
