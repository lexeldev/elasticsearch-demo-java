package com.lexel.elasticsearchdemojava.basicapp.controller;

import co.elastic.clients.elasticsearch.core.DeleteResponse;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.indices.DeleteIndexResponse;
import com.lexel.elasticsearchdemojava.basicapp.model.ProductRequest;
import com.lexel.elasticsearchdemojava.basicapp.model.ProductResponse;
import com.lexel.elasticsearchdemojava.basicapp.model.Product;
import com.lexel.elasticsearchdemojava.basicapp.service.ESService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ESService esService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
        try {
            IndexResponse indexResponse = esService.createDocument(productRequest);
            ProductResponse response = new ProductResponse();
            response.setId(indexResponse.id());
            response.setName(productRequest.getProduct().getName());
            response.setPrice(productRequest.getProduct().getPrice());
            response.setMessage("Product created successfully");
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(
            @RequestParam("index") String indexName,
            @PathVariable("id") String documentId) {
        try {
            GetResponse<Product> response = esService.getDocument(indexName, documentId);
            if (response.found() && response.source() != null) {
                return ResponseEntity.ok(response.source());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProducts(
            @RequestParam("index") String indexName,
            @RequestParam("query") String searchText) {
        try {
            SearchResponse<Product> searchResponse = esService.searchDocuments(indexName, searchText);
            List<ProductResponse> responseList = searchResponse.hits().hits().stream()
                    .map(hit -> {
                        Product product = hit.source();
                        ProductResponse response = new ProductResponse();
                        response.setId(hit.id());
                        if (product != null) {
                            response.setName(product.getName());
                            response.setPrice(product.getPrice());
                        }
                        return response;
                    })
                    .toList();
            return ResponseEntity.ok(responseList);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponse> deleteProduct(
            @RequestParam("index") String indexName,
            @PathVariable("id") String documentId) {
        try {
            DeleteResponse deleteResponse = esService.deleteDocument(indexName, documentId);
            return ResponseEntity.ok(deleteResponse);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/index")
    public ResponseEntity<DeleteIndexResponse> deleteIndex(
            @RequestParam("index") String indexName) {
        try {
            DeleteIndexResponse deleteIndexResponse = esService.deleteIndex(indexName);
            return ResponseEntity.ok(deleteIndexResponse);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}