// package com.tokoteratai.project111.controller;

// import java.util.List;
// import java.util.Optional;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;

// import com.tokoteratai.project111.model.Category;
// import com.tokoteratai.project111.model.Product;
// import com.tokoteratai.project111.model.ProductDto;
// import com.tokoteratai.project111.repository.CategoryRepository;
// import com.tokoteratai.project111.repository.ProductRepository;

// @Controller
// @RequestMapping("/category")
// public class CategoryController {

//     @Autowired
//     private CategoryRepository repo;

//     @GetMapping("/product_page")
//     public String showCategoryList(Model model) {
//         List<Category> categories = repo.findAll();
//         model.addAttribute("categories", categories);
//         return "productpage";
//     }

//     @GetMapping("/{id}")
//     public String showCategory(@PathVariable("id") Integer id, Model model) {
//         Optional<Category> category = repo.findById(id);
//         List<Category> categories = repo.findAll();
//         model.addAttribute("category", category);
//         model.addAttribute("categories", categories);
//         return "inputpage";
//     }
// }