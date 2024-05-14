package com.tokoteratai.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tokoteratai.project.models.Product;
import com.tokoteratai.project.services.ProductRepository;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository repo;

    @GetMapping({"","/"})
    public String showProductList (Model model) {
        List<Product> products = repo.findAll();
        model.addAttribute("products", products);
        return "products/productpage";
    }
}
