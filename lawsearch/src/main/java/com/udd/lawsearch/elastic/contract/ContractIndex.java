package com.udd.lawsearch.elastic.contract;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "contracts")
public class ContractIndex {
    @Id
    private String id;
    @MultiField(
            mainField = @Field(type = FieldType.Text, searchAnalyzer = "serbian", analyzer = "serbian"),
            otherFields = {
                    @InnerField(suffix = "keyword", type = FieldType.Keyword, ignoreAbove = 256)
            }
    )
    private String signatoryPersonName;
    @MultiField(
            mainField = @Field(type = FieldType.Text, searchAnalyzer = "serbian", analyzer = "serbian"),
            otherFields = {
                    @InnerField(suffix = "keyword", type = FieldType.Keyword, ignoreAbove = 256)
            }
    )
    private String signatoryPersonSurname;
    @MultiField(
            mainField = @Field(type = FieldType.Text, searchAnalyzer = "serbian", analyzer = "serbian"),
            otherFields = {
                    @InnerField(suffix = "keyword", type = FieldType.Keyword, ignoreAbove = 256)
            }
    )
    private String governmentName;
    @MultiField(
            mainField = @Field(type = FieldType.Text, searchAnalyzer = "serbian", analyzer = "serbian"),
            otherFields = {
                    @InnerField(suffix = "keyword", type = FieldType.Keyword, ignoreAbove = 256)
            }
    )
    private String governmentType;
    @GeoPointField
    private GeoPoint governmentLocation;
    @Field(type = FieldType.Text, searchAnalyzer = "serbian", analyzer = "serbian")
    private String content;

    public ContractIndex(String signatoryPersonName, String signatoryPersonSurname, String governmentName, String governmentType, String content, double lat, double lon) {
        this.signatoryPersonName = signatoryPersonName;
        this.signatoryPersonSurname = signatoryPersonSurname;
        this.governmentName = governmentName;
        this.governmentType = governmentType;
        this.governmentLocation = new GeoPoint(lat, lon);
        this.content = content;
    }
}
