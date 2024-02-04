package com.udd.lawsearch.elastic.search;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.GeoLocation;
import co.elastic.clients.elasticsearch._types.query_dsl.GeoDistanceQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.udd.lawsearch.elastic.search.dtos.*;
import com.udd.lawsearch.shared.location.GeoCodingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService{
    private final ElasticsearchClient esClient;
    private final GeoCodingService geoCodingService;

    @Override
    public SearchResult basicSearch(BasicSearchDto dto) throws IOException, RuntimeException {
        SearchResponse<ObjectNode> response = esClient.search(
                SearchRequest.of(s -> s
                        .index("contracts")
                        .query(q -> q
                                .match(t -> t
                                        .field(dto.getField())
                                        .query(dto.getValue())
                                )
                        )
                        .highlight(h -> h
                                .fields("content", f -> f
                                        .highlightQuery(hq -> hq
                                                .match(mq -> mq
                                                        .field("content")
                                                        .query(dto.getValue())
                                                )
                                        )
                                )
                        )
                ),
                ObjectNode.class
        );

        HitsMetadata<ObjectNode> hitsMetadata = response.hits();
        return createResponse(hitsMetadata);
    }

    public SearchResult locationSearch(LocationSearchDto dto) throws IOException {
        var coordinates = geoCodingService.getCoordinates(dto.getAddress());

        GeoDistanceQuery geoDistanceQuery = Query.of(q -> q
                .geoDistance(gd -> gd
                        .field("governmentLocation")
                        .distance(dto.getRadius().intValue() + "km")
                        .location(GeoLocation.of(gl -> gl
                                .latlon(ll -> ll
                                        .lat(coordinates[0])
                                        .lon(coordinates[1])
                                )
                        ))
                )
        ).geoDistance();

        SearchRequest searchRequest = SearchRequest.of(s -> s
                .index("contracts")
                .query(Query.of(q -> q
                        .bool(b -> b
                                .filter(f -> f
                                        .geoDistance(geoDistanceQuery)
                                )
                        )
                ))
        );

        SearchResponse<ObjectNode> searchResponse = esClient.search(searchRequest, ObjectNode.class);

        HitsMetadata<ObjectNode> hitsMetadata = searchResponse.hits();
        return createResponse(hitsMetadata);
    }

    @Override
    public SearchResult advancedSearch(SearchCondition condition) throws IOException {
        Query query = buildQuery(condition);

        SearchResponse<ObjectNode> response = esClient.search(
                SearchRequest.of(s -> s
                        .index("contracts")
                        .query(query)
                        .highlight(h -> h
                                .fields("content", f -> f)
                        )
                ),
                ObjectNode.class
        );

        HitsMetadata<ObjectNode> hitsMetadata = response.hits();
        return createResponse(hitsMetadata);
    }

    private Query buildQuery(SearchCondition condition) {
        if (condition instanceof BasicSearchDto simpleCondition) {
            if (((BasicSearchDto) condition).isPhrase())
                return Query.of(q -> q
                        .matchPhrase(m -> m
                                .field(simpleCondition.getField())
                                .query(simpleCondition.getValue())
                        )
                );
            return Query.of(q -> q
                    .match(m -> m
                            .field(simpleCondition.getField())
                            .query(simpleCondition.getValue())
                    )
            );
        } else if (condition instanceof BoolQueryDto booleanCondition) {
            List<Query> innerQueries = new ArrayList<>();
            for (SearchConditionWrapper innerConditionWrapper : booleanCondition.getBooleanQueryFields()) {
                innerQueries.add(buildQuery(innerConditionWrapper.getCondition()));
            }

            switch (booleanCondition.getOperator().toUpperCase()) {
                case "AND":
                    return Query.of(q -> q.bool(b -> b.must(innerQueries)));
                case "OR":
                    return Query.of(q -> q.bool(b -> b.should(innerQueries).minimumShouldMatch("1")));
                case "NOT":
                    if (innerQueries.size() == 1) {
                        return Query.of(q -> q.bool(b -> b.mustNot(innerQueries.get(0))));
                    } else {
                        throw new IllegalArgumentException("NOT operator should have only one condition");
                    }
                default:
                    throw new IllegalArgumentException("Unsupported operator: " + booleanCondition.getOperator());
            }
        } else {
            throw new IllegalArgumentException("Unknown condition type: " + condition.getClass().getName());
        }
    }

    @Override
    public LawSearchResult lawSearch(BasicSearchDto dto) throws IOException, RuntimeException {
        SearchResponse<ObjectNode> response = esClient.search(
                SearchRequest.of(s -> s
                        .index("laws")
                        .query(q -> q
                                .match(t -> t
                                        .field(dto.getField())
                                        .query(dto.getValue())
                                )
                        )
                        .highlight(h -> h
                                .fields("content", f -> f
                                        .highlightQuery(hq -> hq
                                                .match(mq -> mq
                                                        .field("content")
                                                        .query(dto.getValue())
                                                )
                                        )
                                )
                        )
                ),
                ObjectNode.class
        );

        HitsMetadata<ObjectNode> hitsMetadata = response.hits();
        return createLawsResponse(hitsMetadata);
    }

    private LawSearchResult createLawsResponse(HitsMetadata<ObjectNode> hitsMetadata) {
        List<LawResultData> responses = new ArrayList<>();
        List<Hit<ObjectNode>> searchHits = hitsMetadata.hits();

        for (Hit<ObjectNode> h: searchHits) {
            LawResultData searchResponse = new LawResultData();
            ObjectNode json = h.source();

            if (h.highlight().isEmpty()) {
                searchResponse.setHighlight(Objects.requireNonNull(json).get("content").asText().substring(0, 150) + "...");
            } else {
                searchResponse.setHighlight("..." + h.highlight().get("content").get(0) + "...");
            }
            searchResponse.setLawId(h.id());

            responses.add(searchResponse);
        }

        return new LawSearchResult(responses, Objects.requireNonNull(hitsMetadata.total()).value());
    }

    private SearchResult createResponse(HitsMetadata<ObjectNode> hitsMetadata) {
        List<ResultData> responses = new ArrayList<>();
        List<Hit<ObjectNode>> searchHits = hitsMetadata.hits();

        for (Hit<ObjectNode> h: searchHits) {
            ResultData searchResponse = new ResultData();
            ObjectNode json = h.source();
            String name = Objects.requireNonNull(json).get("governmentName").asText();
            searchResponse.setGovernmentName(name);
            searchResponse.setGovernmentType(json.get("governmentType").asText());
            searchResponse.setSignatoryPersonName(json.get("signatoryPersonName").asText());
            searchResponse.setSignatoryPersonSurname(json.get("signatoryPersonSurname").asText());
            searchResponse.setFileName(json.get("fileName").asText());

            if (h.highlight().isEmpty()) {
                searchResponse.setHighlight(json.get("content").asText().substring(0, 150) + "...");
            } else {
                searchResponse.setHighlight("..." + h.highlight().get("content").get(0) + "...");
            }
            searchResponse.setContractId(h.id());

            responses.add(searchResponse);
        }

        return new SearchResult(responses, Objects.requireNonNull(hitsMetadata.total()).value());
    }
}
