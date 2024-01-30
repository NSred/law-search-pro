package com.udd.lawsearch.elastic.contract;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "contracts")
public class ContractIndex {
    @Id
    private String id;
    @Field(type = FieldType.Text, searchAnalyzer = "serbian", analyzer = "serbian", store = true)
    private String signatoryPersonName;
    @Field(type = FieldType.Text, searchAnalyzer = "serbian", analyzer = "serbian", store = true)
    private String signatoryPersonSurname;
    @Field(type = FieldType.Text, searchAnalyzer = "serbian", analyzer = "serbian", store = true)
    private String governmentName;
    @Field(type = FieldType.Text, searchAnalyzer = "serbian", analyzer = "serbian", store = true)
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
