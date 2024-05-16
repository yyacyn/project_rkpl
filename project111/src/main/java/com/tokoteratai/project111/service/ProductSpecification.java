// package com.tokoteratai.project111.service;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.function.Predicate;

// import org.springframework.data.jpa.domain.Specification;

// import com.tokoteratai.project111.model.Product;

// public class ProductSpecification {

//     private static Specification<Product> getSpec(Integer code, String name, String category){
//         return ((root,query, criteriaBuilder) -> {
//             List<Predicate> predicate = new ArrayList<>();

//             if(code != null && !(code.isEmpty)){
//                 predicate.add(criteriaBuilder.equal(root.get("code"), code));
//             }



//         });
//     }
// }
