package com.lexel.elasticsearchdemojava.basicapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductRequest {
    private String index;
    private Product product;
}
