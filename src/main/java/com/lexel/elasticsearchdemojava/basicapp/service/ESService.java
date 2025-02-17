package com.lexel.elasticsearchdemojava.basicapp.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.DeleteResponse;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.indices.DeleteIndexResponse;
import com.lexel.elasticsearchdemojava.basicapp.model.ProductRequest;
import com.lexel.elasticsearchdemojava.basicapp.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ESService {

    private final ElasticsearchClient esClient;

    public IndexResponse createDocument(ProductRequest productRequest) throws IOException {
        String indexName = productRequest.getIndex();
        Product product = productRequest.getProduct();
        String documentId = product.getId();

        return esClient.index(i -> i
                .index(indexName)
                .id(documentId)
                .document(product)
        );
    }

    public GetResponse<Product> getDocument(String indexName, String documentId) throws IOException {
        return esClient.get(g -> g
                        .index(indexName)
                        .id(documentId),
                Product.class
        );
    }

    public SearchResponse<Product> searchDocuments(String indexName, String searchText) throws IOException {
        return esClient.search(s -> s
                        .index(indexName)
                        .query(q -> q
                                .match(t -> t
                                        .field("name")
                                        .query(searchText)
                                )
                        ),
                Product.class
        );
    }

    public DeleteResponse deleteDocument(String indexName, String documentId) throws IOException {
        return esClient.delete(d -> d
                .index(indexName)
                .id(documentId)
        );
    }

    public DeleteIndexResponse deleteIndex(String indexName) throws IOException {
        return esClient.indices().delete(d -> d
                .index(indexName)
        );
    }
}