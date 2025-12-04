package com.example.productservice.service;

import com.example.productservice.model.Product;
import com.example.productservice.model.ProductDocument;
import com.example.productservice.repository.ProductDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductDocumentService {

    private final ProductDocumentRepository productDocumentRepository;
    private final ElasticsearchOperations elasticsearchOperations;


    public void createOrUpdateProductDocument(Product product) {
        if (product == null || product.getId() == null) {
            throw new IllegalArgumentException("Product or Product ID cannot be null");
        }

        String productDocumentId = product.getId().toString();
        ProductDocument productDocument = ProductDocument.builder()
                .id(productDocumentId)
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();

        productDocumentRepository.save(productDocument);
    }


    public void deleteProductDocument(String productDocumentId) {
        if (productDocumentId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        productDocumentRepository.deleteById(productDocumentId);
    }


    public List<ProductDocument> searchByMatch(String searchKeyword) {
        if (searchKeyword == null || searchKeyword.isBlank()) {
            throw new IllegalArgumentException("Search keyword cannot be null or empty");
        }

        NativeQuery nativeQuery = NativeQuery.builder()
                .withQuery(query -> query.bool(bool -> bool
                        .should(should -> should.multiMatch(multiMatch -> multiMatch
                                .fields("title^3", "description")
                                .query(searchKeyword)
                                .fuzziness("AUTO")
                        ))
                        .should(should -> should.term(term -> term
                                .field("title.keyword")
                                .value(searchKeyword)
                        ))
                        .should(s -> s.term(t -> t
                                .field("description.keyword")
                                .value(searchKeyword)
                        ))
                        .minimumShouldMatch("1")

                )).build();


        SearchHits<ProductDocument> searchHits = elasticsearchOperations.search(nativeQuery, ProductDocument.class);

        return searchHits.getSearchHits()
                .stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }
}
