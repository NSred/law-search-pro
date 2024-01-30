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
import com.udd.lawsearch.elastic.search.dtos.BasicSearchDto;
import com.udd.lawsearch.elastic.search.dtos.LocationSearchDto;
import com.udd.lawsearch.elastic.search.dtos.ResultData;
import com.udd.lawsearch.elastic.search.dtos.SearchResult;
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
