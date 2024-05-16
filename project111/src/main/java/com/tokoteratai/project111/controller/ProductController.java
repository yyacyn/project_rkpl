package com.tokoteratai.project111.controller;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tokoteratai.project111.model.Product;
import com.tokoteratai.project111.model.ProductDto;
import com.tokoteratai.project111.repository.CategoryRepository;
import com.tokoteratai.project111.repository.InvoiceRepository;
import com.tokoteratai.project111.repository.ProductRepository;
import com.tokoteratai.project111.model.Category;
import com.tokoteratai.project111.model.CategoryDto;
import com.tokoteratai.project111.model.Invoice;
import com.tokoteratai.project111.model.InvoiceDto;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/")
public class ProductController {

    @Autowired
    private InvoiceRepository irepo;

    @Autowired
    private ProductRepository repo;

    @Autowired
    private CategoryRepository crepo;

    @GetMapping("/product_page")
    public String showProductList(Model model) {
        List<Product> product = repo.findAll();
        model.addAttribute("products", product);

        List<Category> category = crepo.findAll();
        model.addAttribute("categories", category);
        return "productpage";
    }

    @GetMapping("/search")
    public String searchProducts(@RequestParam(name = "query", required = false) String query, Model model) {
        List<Product> products;
        if (query == null) {
            products = repo.findAll();
        } else {
            products = repo.findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(query, query);
        }

        List<Category> category = crepo.findAll();
        model.addAttribute("categories", category);
        model.addAttribute("products", products);
        return "productpage";
    }
    
    @GetMapping("/")
    public String index() {
        return "index";
    }

    
    @GetMapping("/money_page")
    public String moneyPage() {
        return "moneypage";
    }

    // @GetMapping("/product_page")
    // public String getAllProduct() {
    //     return "productpage";
    // }

    // @GetMapping("/input_form")
    // public String inputForm() {
    //     return "inputform";
    // }

    @GetMapping("/order_form")
    public String orderForm(Model model) {
        List<Invoice> invoice = irepo.findAll();
        model.addAttribute("invoices", invoice);
        return "orderform";
    }

    @GetMapping("/create_invoice")
    public String showCreateInvoicePage(Model model) {
        InvoiceDto invoiceDto = new InvoiceDto();
        model.addAttribute("invoiceDto", invoiceDto);
        return "orderform";
    }

    @PostMapping("/create_invoice")
    public String CreateInvoice(@jakarta.validation.Valid @ModelAttribute InvoiceDto invoiceDto, BindingResult result) {

        Invoice invoice = new Invoice();
        invoice.setCus_name(invoiceDto.getCus_name());
        invoice.setDate(invoiceDto.getDate());
        invoice.setP_code(invoiceDto.getP_code());
        invoice.setPrice(invoiceDto.getPrice());
        invoice.setQty(invoiceDto.getQty());
        irepo.save(invoice);
        return "redirect:/orderform";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model) {
        ProductDto productDto = new ProductDto();
        model.addAttribute("productDto", productDto);
    
        List<Product> product = repo.findAll(Sort.by(Sort.Direction.DESC, "code"));
        model.addAttribute("products", product);

        List<Category> category = crepo.findAll();
        model.addAttribute("categories", category);
    
        return "create";
    }

    @GetMapping("/createc")
    public String showCreateCategoryPage(Model model) {
        CategoryDto categoryDto = new CategoryDto();
        model.addAttribute("categoryDto", categoryDto);

        List<Category> category = crepo.findAll();
        model.addAttribute("categories", category);
    
        // List<Product> product = repo.findAll(Sort.by(Sort.Direction.DESC, "code"));
        // model.addAttribute("products", product);
    
        return "createc";
    }

    @PostMapping("/createc")
    public String CreateCategoryPage(@jakarta.validation.Valid @ModelAttribute CategoryDto categoryDto, BindingResult result) {

        MultipartFile image = categoryDto.getImgurl();
        Date createdAt = new Date();

        String storageFileName = createdAt.getTime() + "_" +image.getOriginalFilename();

        try {
            String uploadDir = "public/images/";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
                
            }
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }

        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setImgurl(storageFileName);


        crepo.save(category);
        return "redirect:/createc";
    }



    @PostMapping("/create")
    public String CreatePage(@jakarta.validation.Valid @ModelAttribute ProductDto productDto, BindingResult result) {

        MultipartFile image = productDto.getImgurl();
        Date createdAt = new Date();

        String storageFileName = createdAt.getTime() + "_" +image.getOriginalFilename();

        try {
            String uploadDir = "public/images/";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
                
            }
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }

        Product product = new Product();
        product.setName(productDto.getName());
        product.setCategory(productDto.getCategory());
        product.setS_price(productDto.getS_price());
        product.setP_price(productDto.getP_price());
        product.setStock(productDto.getStock());
        product.setImgurl(storageFileName);
        product.setCreatedAt(createdAt);

        repo.save(product);
        return "redirect:/create";
    }

    @PostMapping("/edit")
    public String updateProduct(Model model, @RequestParam Integer code, @Valid @ModelAttribute ProductDto productDto, BindingResult result) {
    try {
        Product product = repo.findById(code).get();
        model.addAttribute("product", product);

        if (result.hasErrors()) {
            return "edit";
        }

        if (!productDto.getImgurl().isEmpty()) {
            String uploadDir = "public/images/";
            Path oldImagePath = Paths.get(uploadDir + product.getImgurl());
            try {
                Files.delete(oldImagePath);
            } catch (Exception ex) {
                System.out.println("Exception: " + ex.getMessage());
            }

            MultipartFile image = productDto.getImgurl();
            Date createdAt = new Date();
            String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();

            try (InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);

            }
            
            product.setImgurl(storageFileName);
        }

        product.setName(productDto.getName());
        product.setCategory(productDto.getCategory());
        product.setS_price(productDto.getS_price());
        product.setP_price(productDto.getP_price());
        product.setStock(productDto.getStock());

        repo.save(product);

    } 
    
    catch(Exception ex) {
        System.out.println("Exception: " + ex.getMessage());
        return "redirect:/create";  
    }

    return "redirect:/create";
    }

    @GetMapping("/edit")
    public String showEditPage (
        Model model, @RequestParam Integer code
    ) {
        try {
            Product product = repo.findById(code).get();
            model.addAttribute("product", product);

            ProductDto productDto = new ProductDto();
            productDto.setName(product.getName());
            productDto.setCategory(product.getCategory());
            productDto.setS_price(product.getS_price());
            productDto.setP_price(product.getP_price());
            productDto.setStock(product.getStock());
            
            model.addAttribute("productDto", productDto);
        }

        catch(Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/create";  
        }

        List<Category> category = crepo.findAll();
        model.addAttribute("categories", category);

        return "editProduct";
    }

    @RequestMapping("/delete")
    public String delete(@RequestParam Integer code) {
        repo.deleteByCode(code);
        return "redirect:/create";
    }
}