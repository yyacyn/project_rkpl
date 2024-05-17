package com.tokoteratai.project111.controller;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tokoteratai.project111.model.Product;
import com.tokoteratai.project111.model.ProductDto;
import com.tokoteratai.project111.repository.AmountRepository;
import com.tokoteratai.project111.repository.CategoryRepository;
import com.tokoteratai.project111.repository.ExpenseRepository;
import com.tokoteratai.project111.repository.IncomeRepository;
import com.tokoteratai.project111.repository.InvoiceRepository;
import com.tokoteratai.project111.repository.ProductRepository;
import com.tokoteratai.project111.model.Amount;
import com.tokoteratai.project111.model.AmountDto;
import com.tokoteratai.project111.model.Category;
import com.tokoteratai.project111.model.CategoryDto;
import com.tokoteratai.project111.model.Expense;
import com.tokoteratai.project111.model.ExpenseDto;
import com.tokoteratai.project111.model.Income;
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

    @Autowired
    private AmountRepository arepo;

    @Autowired
    private IncomeRepository inrepo;

    @Autowired
    private ExpenseRepository exrepo;

    @GetMapping("/product_page")
    public String showProductList(Model model) {
        List<Product> product = repo.findAll();
        model.addAttribute("products", product);

        List<Category> category = crepo.findAll();
        model.addAttribute("categories", category);
        return "productpage";
    }

    @GetMapping("/search")
    public String searchProducts(@RequestParam(name = "query", required = false) String query,
            @RequestParam(name = "category", required = false) String category,
            Model model) {
        List<Product> products;
        if (category != null) {
            products = repo.findByCategoryContainingIgnoreCase(category);
        } else if (query != null) {
            products = repo.findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(query, query);
        } else {
            products = repo.findAll();
        }

        List<Category> categoryList = crepo.findAll();
        model.addAttribute("categories", categoryList);
        model.addAttribute("products", products);
        return "productpage";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/money_page")
    public String showMoneyPage(Model model) {
        List<Invoice> invoices = irepo.findAll();
        model.addAttribute("invoices", invoices);

        List<Product> product = repo.findAll();
        model.addAttribute("products", product);

        List<Amount> amounts = arepo.findAll();
        model.addAttribute("amounts", amounts);

        Integer total = invoices.stream().mapToInt(invoice -> invoice.getPrice() * invoice.getQty()).sum();
        model.addAttribute("total", total);

        Integer totalAmount = amounts.stream().mapToInt(Amount::getValue).sum();
        model.addAttribute("totalAmount", totalAmount);

        InvoiceDto invoiceDto = new InvoiceDto();
        model.addAttribute("invoiceDto", invoiceDto);

        AmountDto amountDto = new AmountDto();
        model.addAttribute("amountDto", amountDto);

        List<Expense> expenses = exrepo.findAll();
        model.addAttribute("expenses", expenses);

        ExpenseDto expenseDto = new ExpenseDto();
        model.addAttribute("expenseDto", expenseDto);

        List<String> statusValues = Arrays.asList("Lunas", "Belum Lunas");
        model.addAttribute("statusValues", statusValues);

        List<String> paymentMethod = Arrays.asList("Cash", "QRIS", "Kredit atau Debit");
        model.addAttribute("paymentMethod", paymentMethod);

        List<Income> incomes = inrepo.findAll();
        model.addAttribute("incomes", incomes);

        Integer sumQris = inrepo.sumTotalByPaymethod("qris");
        Integer sumCash = inrepo.sumTotalByPaymethod("cash");

        model.addAttribute("sumQris", sumQris != null ? sumQris : 0);
        model.addAttribute("sumCash", sumCash != null ? sumCash : 0);

        Integer sumBelumLunas = inrepo.sumTotalByStatus("belum lunas");
        model.addAttribute("sumBelumLunas", sumBelumLunas != null ? sumBelumLunas : 0);

        return "moneypage";
    }

    @RequestMapping("/delete_all_income")
    public String deleteAllIncome() {
        inrepo.deleteAll();
        return "redirect:/money_page";
    }

    @PostMapping("/money_page")
    public String CreateExpense(@jakarta.validation.Valid @ModelAttribute ExpenseDto expenseDto, BindingResult result) {
        Expense expense = new Expense();
        expense.setAmount(expenseDto.getAmount());
        expense.setInfo(expenseDto.getInfo());
        expense.setDate(expenseDto.getDate());
        exrepo.save(expense);
        return "redirect:/money_page";
    }

    @GetMapping("/print_area")
    public String showPrintArea(Model model) {
        List<Invoice> invoices = irepo.findAll();
        model.addAttribute("invoices", invoices);

        List<Product> product = repo.findAll();
        model.addAttribute("products", product);

        List<Amount> amounts = arepo.findAll();
        model.addAttribute("amounts", amounts);

        Integer total = invoices.stream().mapToInt(invoice -> invoice.getPrice() * invoice.getQty()).sum();
        model.addAttribute("total", total);

        Integer totalAmount = amounts.stream().mapToInt(Amount::getValue).sum();
        model.addAttribute("totalAmount", totalAmount);
        return "printarea";
    }

    // @GetMapping("/product_page")
    // public String getAllProduct() {
    // return "productpage";
    // }

    // @GetMapping("/input_form")
    // public String inputForm() {
    // return "inputform";
    // }

    @GetMapping("/order_form")
    public String orderForm(Model model) {
        List<Invoice> invoices = irepo.findAll();
        model.addAttribute("invoices", invoices);

        List<Product> product = repo.findAll();
        model.addAttribute("products", product);

        List<Amount> amounts = arepo.findAll();
        model.addAttribute("amounts", amounts);

        Integer total = invoices.stream().mapToInt(invoice -> invoice.getPrice() * invoice.getQty()).sum();
        model.addAttribute("total", total);

        Integer totalAmount = amounts.stream().mapToInt(Amount::getValue).sum();
        model.addAttribute("totalAmount", totalAmount);

        InvoiceDto invoiceDto = new InvoiceDto();
        model.addAttribute("invoiceDto", invoiceDto);

        AmountDto amountDto = new AmountDto();
        model.addAttribute("amountDto", amountDto);

        List<String> statusValues = Arrays.asList("Lunas", "Belum Lunas");
        model.addAttribute("statusValues", statusValues);

        List<String> paymentMethod = Arrays.asList("Cash", "QRIS", "Kredit atau Debit");
        model.addAttribute("paymentMethod", paymentMethod);

        return "orderform";
    }

    @PostMapping("/order_form")
    public String CreateInvoice(@jakarta.validation.Valid @ModelAttribute InvoiceDto invoiceDto, BindingResult result) {

        // Check if the product exists in the product table
        Product product = repo.findById(invoiceDto.getP_code()).orElse(null);
        if (product == null) {
            // If the product does not exist, add an error to the BindingResult object
            result.rejectValue("p_code", "error.p_code", "Invalid product code");
            // Return the form with the error message
            return "orderform";
        }

        // Check if the product stock is enough
        if (product.getStock() < invoiceDto.getQty()) {
            // If the product stock is not enough, add an error to the BindingResult object
            result.rejectValue("qty", "error.qty", "Not enough product in stock");
            // Return the form with the error message
            return "orderform";
        }

        Invoice invoice = new Invoice();
        invoice.setCus_name(invoiceDto.getCus_name());
        invoice.setDate(invoiceDto.getDate());
        invoice.setPrice(product.getS_price());
        invoice.setQty(invoiceDto.getQty());
        invoice.setPaymethod(invoiceDto.getPaymethod());
        invoice.setStatus(invoiceDto.getStatus());
        invoice.setP_code(product);
        irepo.save(invoice);

        Income income = new Income();
        income.setCus_name(invoice.getCus_name());
        income.setDate(invoice.getDate());
        income.setQty(invoice.getQty());
        income.setOid(invoice.getId());
        income.setTotal(invoice.getPrice() * invoice.getQty());
        income.setPaymethod(invoice.getPaymethod());
        income.setStatus(invoice.getStatus());
        inrepo.save(income);

        // Decrease the product stock by the quantity
        product.setStock(product.getStock() - invoiceDto.getQty());
        // Save the updated product
        repo.save(product);

        return "redirect:/order_form";
    }

    @PostMapping("/create_amount")
    public String createAmount(@ModelAttribute AmountDto amountDto, BindingResult result) {
        if (amountDto.getValue() == null) {
            // If the amount value is null, add an error to the BindingResult object
            result.rejectValue("value", "error.value", "Amount value must not be null");
            // Return the form with the error message
            return "amountform";
        }

        // Delete all existing Amount objects
        arepo.deleteAll();

        // Create a new Amount object
        Amount amount = new Amount();
        amount.setValue(amountDto.getValue());

        // Save the new Amount object to the database
        arepo.save(amount);

        return "redirect:/amountform";
    }

    @PostMapping("/delete_invoice/{id}")
    public String deleteInvoice(@PathVariable Integer id) {
        try {
            irepo.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            // Handle the case where there is no Invoice with the provided id
        }
        return "redirect:/order_form";
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
    public String CreateCategoryPage(@jakarta.validation.Valid @ModelAttribute CategoryDto categoryDto,
            BindingResult result) {

        MultipartFile image = categoryDto.getImgurl();
        Date createdAt = new Date();

        String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();

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

        String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();

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
    public String updateProduct(Model model, @RequestParam Integer code, @Valid @ModelAttribute ProductDto productDto,
            BindingResult result) {
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
                    Files.copy(inputStream, Paths.get(uploadDir + storageFileName),
                            StandardCopyOption.REPLACE_EXISTING);

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

        catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/create";
        }

        return "redirect:/create";
    }

    @GetMapping("/edit")
    public String showEditPage(
            Model model, @RequestParam Integer code) {
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

        catch (Exception ex) {
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

    @RequestMapping("/delete_category/{id}")
    public String deleteCategory(@PathVariable Integer id) {
        crepo.deleteByCode(id);
        return "redirect:/createc";
    }

    @RequestMapping("/delete_all_expense")
    public String deleteAllExpense() {
        exrepo.deleteAllExpenses();
        return "redirect:/money_page";
    }


    @RequestMapping("/delete_amount/{id}")
    public String deleteamount(@RequestParam Integer id) {
        arepo.deleteByCode(id);
        return "redirect:/order_form";
    }
}