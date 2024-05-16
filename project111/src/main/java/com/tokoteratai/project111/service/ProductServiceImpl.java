// package com.tokoteratai.project111.service;

// import java.util.List;

// import com.tokoteratai.project111.model.Product;

// public class ProductServiceImpl {
    
//     @Override
//     public List<Product> searchProducts(String query) {
//         List<Product> products;
//         if (query.contains(" ")) {
//             String[] queryParts = query.split(" ");
//             String nameQuery = queryParts[0];
//             String categoryQuery = queryParts[1];
//             products = productRepository.findByNameContainingAndCategoryContaining(nameQuery, categoryQuery);
//         } else {
//             products = productRepository.findByNameContainingOrCategoryContaining(query, query);
//         }
//         return products;
//     }
// }
