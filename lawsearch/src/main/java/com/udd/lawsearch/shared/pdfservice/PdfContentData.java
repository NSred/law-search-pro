package com.udd.lawsearch.shared.pdfservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PdfContentData {
    private String name;
    private String surname;
    private String content;
    private String governmentName;
    private String governmentType;
    private String governmentAddress;
}
