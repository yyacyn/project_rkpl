package com.tokoteratai.project111.controller;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

// import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.tokoteratai.project111.repository.AccountRepository;
import com.tokoteratai.project111.repository.AmountRepository;
import com.tokoteratai.project111.repository.CategoryRepository;
import com.tokoteratai.project111.repository.ExpenseRepository;
import com.tokoteratai.project111.repository.IncomeRepository;
import com.tokoteratai.project111.repository.InvoiceRepository;
import com.tokoteratai.project111.repository.ProductRepository;
import com.tokoteratai.project111.model.Account;
import com.tokoteratai.project111.model.Amount;
import com.tokoteratai.project111.model.AmountDto;
import com.tokoteratai.project111.model.Category;
import com.tokoteratai.project111.model.CategoryDto;
import com.tokoteratai.project111.model.Expense;
import com.tokoteratai.project111.model.ExpenseDto;
import com.tokoteratai.project111.model.Income;
import com.tokoteratai.project111.model.IncomeDto;
import com.tokoteratai.project111.model.Invoice;
import com.tokoteratai.project111.model.InvoiceDto;

import jakarta.servlet.http.HttpSession;
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

    @Autowired
    private AccountRepository acrepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/print_latest_income")
    public String printLatestIncome(Model model) {
        List<Income> incomes = inrepo.findAll();
        model.addAttribute("incomes", incomes);

        List<Income> allIncomes = inrepo.findAll();
        model.addAttribute("allIncomes", allIncomes);

        // Get today's incomes.
        List<Income> todaysIncomes = inrepo.findAllByDate();
        model.addAttribute("todaysIncomes", todaysIncomes);
        model.addAttribute("allIncomes", allIncomes);
        return "printlatestincome";
    }

    @GetMapping("/product_page")
    public String showProductList(Model model, @RequestParam(defaultValue = "1") int page) {
        int pageSize = 6;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<Product> productPage = repo.findAll(pageable);

        model.addAttribute("categories", crepo.findAll());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("products", productPage.getContent());

        return "productpage";
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        List<Account> accounts = acrepo.findAll();
        model.addAttribute("accounts", accounts);
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String username, @RequestParam String password, HttpSession session,
            Model model) {
        Optional<Account> account = acrepo.findByUsername(username);

        if (account.isPresent() && passwordEncoder.matches(password, account.get().getPassword())) {
            session.setAttribute("loggedInUser", account.get());

            return "redirect:/";
        } else {
            model.addAttribute("loginError", "Invalid username or password.");
            return "login";
        }
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

    @GetMapping("/daily-income")
    public String yourMethod(Model model) {
        LocalDate today = LocalDate.now();

        List<Income> allIncomes = inrepo.findAll();

        List<Income> todaysIncomes = inrepo.findAllByDate();

        model.addAttribute("todaysIncomes", todaysIncomes);
        model.addAttribute("allIncomes", allIncomes);

        return "/";
    }

    @GetMapping("/api/daily-income")
    public ResponseEntity<List<Income>> getDailyIncome() {
        List<Income> incomes = inrepo.findAll();
        return new ResponseEntity<>(incomes, HttpStatus.OK);
    }

    @GetMapping("/")
    public String showIndex(Model model) {

        List<Invoice> invoices = irepo.findAll();
        model.addAttribute("invoices", invoices);

        List<Product> product = repo.findAll();
        model.addAttribute("products", product);

        Integer total = invoices.stream().mapToInt(invoice -> invoice.getPrice() * invoice.getQty()).sum();
        model.addAttribute("total", total);

        List<Amount> amounts = arepo.findAll();
        model.addAttribute("amounts", amounts);

        List<Income> allIncomes = inrepo.findAll();
        model.addAttribute("allIncomes", allIncomes);

        List<Income> todaysIncomes = inrepo.findAllByDate();
        model.addAttribute("todaysIncomes", todaysIncomes);
        model.addAttribute("allIncomes", allIncomes);

        Integer totalAmount = amounts.stream().mapToInt(Amount::getValue).sum();
        model.addAttribute("totalAmount", totalAmount);

        Integer totalProductPrice = product.stream()
                .mapToInt(products -> products.getP_price() * products.getStock())
                .sum();
        model.addAttribute("totalProductPrice", totalProductPrice);

        List<Income> incomes = inrepo.findAll();
        model.addAttribute("incomes", incomes);

        List<Expense> expenses = exrepo.findAll();
        model.addAttribute("expenses", expenses);

        Integer totalIncome = incomes.stream().mapToInt(Income::getTotal).sum();
        model.addAttribute("totalIncome", totalIncome);

        Integer totalExpense = expenses.stream().mapToInt(Expense::getAmount).sum();
        model.addAttribute("totalExpense", totalExpense);

        Integer sumQris = inrepo.sumTotalByPaymethod("qris");
        Integer sumCash = inrepo.sumTotalByPaymethod("cash");
        Integer sumKredit = inrepo.sumTotalByPaymethod("kredit/debit");

        model.addAttribute("sumQris", sumQris != null ? sumQris : 0);
        model.addAttribute("sumCash", sumCash != null ? sumCash : 0);
        model.addAttribute("sumKredit", sumKredit != null ? sumKredit : 0);

        Integer sumBelumLunas = inrepo.sumTotalByStatus("belum lunas");
        model.addAttribute("sumBelumLunas", sumBelumLunas != null ? sumBelumLunas : 0);

        Integer sumLunas = inrepo.sumTotalByStatus("lunas");
        model.addAttribute("sumLunas", sumLunas != null ? sumLunas : 0);

        Map<Product, Integer> productQuantities = new HashMap<>();
        Integer topQuantity = 0;
        Product topSellingProduct = null;
        Integer secondTopQuantity = 0;
        Product secondTopSellingProduct = null;

        for (Income income : incomes) {
            Product incomeProduct = income.getP_code();
            Integer quantity = income.getQty();
            productQuantities.put(incomeProduct, productQuantities.getOrDefault(incomeProduct, 0) + quantity);

            if (productQuantities.get(incomeProduct) > topQuantity) {
                secondTopSellingProduct = topSellingProduct;
                secondTopQuantity = topQuantity;

                topSellingProduct = incomeProduct;
                topQuantity = productQuantities.get(incomeProduct);
            } else if (productQuantities.get(incomeProduct) > secondTopQuantity
                    && !incomeProduct.equals(topSellingProduct)) {
                secondTopSellingProduct = incomeProduct;
                secondTopQuantity = productQuantities.get(incomeProduct);
            }

            System.out.println("Product: " + incomeProduct + ", Quantity: " + quantity);
            System.out.println("Top Selling Product: " + topSellingProduct + ", Top Quantity: " + topQuantity);
            System.out.println("Second Top Selling Product: " + secondTopSellingProduct + ", Second Top Quantity: "
                    + secondTopQuantity);
        }


        if (topSellingProduct != null) {
            model.addAttribute("topSellingProduct", topSellingProduct);
            model.addAttribute("topQuantity", topQuantity);
            model.addAttribute("productImage", topSellingProduct.getImgurl());
        }

        if (secondTopSellingProduct != null) {
            model.addAttribute("secondTopSellingProduct", secondTopSellingProduct); // corrected typo here
            model.addAttribute("secondTopQuantity", secondTopQuantity);
            model.addAttribute("secondProductImage", secondTopSellingProduct.getImgurl());
        }

        return "index";
    }

    @PostMapping("/updateStatus")
    public ResponseEntity<String> updateStatus(@RequestParam Integer id, @RequestParam String status) {
        Optional<Invoice> invoOptional = irepo.findById(id);
        if (!invoOptional.isPresent()) {
            return new ResponseEntity<>("Order not found", HttpStatus.NOT_FOUND);
        }

        Invoice invoice = invoOptional.get();
        invoice.setStatus(status);
        irepo.save(invoice);

        Optional<Income> incomeOptional = inrepo.findById(id);
        if (!incomeOptional.isPresent()) {
            return new ResponseEntity<>("Income not found", HttpStatus.NOT_FOUND);
        }

        Income income = incomeOptional.get();
        income.setStatus(status);
        inrepo.save(income);

        return new ResponseEntity<>("Status updated successfully", HttpStatus.OK);
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

        List<String> paymentMethod = Arrays.asList("Cash", "QRIS", "Kredit/Debit");
        model.addAttribute("paymentMethod", paymentMethod);

        List<Income> incomes = inrepo.findAll();
        model.addAttribute("incomes", incomes);

        Integer sumQris = inrepo.sumTotalByPaymethod("qris");
        Integer sumCash = inrepo.sumTotalByPaymethod("cash");
        Integer sumKredit = inrepo.sumTotalByPaymethod("kredit/debit");

        model.addAttribute("sumQris", sumQris != null ? sumQris : 0);
        model.addAttribute("sumCash", sumCash != null ? sumCash : 0);
        model.addAttribute("sumKredit", sumKredit != null ? sumKredit : 0);

        Integer sumBelumLunas = inrepo.sumTotalByStatus("belum lunas");
        model.addAttribute("sumBelumLunas", sumBelumLunas != null ? sumBelumLunas : 0);

        Integer sumLunas = inrepo.sumTotalByStatus("lunas");
        model.addAttribute("sumLunas", sumLunas != null ? sumLunas : 0);

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

        List<Income> incomes = inrepo.findAll();
        model.addAttribute("incomes", incomes);

        List<Amount> amounts = arepo.findAll();
        model.addAttribute("amounts", amounts);

        if (!incomes.isEmpty() && !amounts.isEmpty()) {
            Integer oid = amounts.get(0).getId();
            String cusName = incomes.get(incomes.size() - 1).getCus_name();
            model.addAttribute("oid", oid);
            model.addAttribute("cusName", cusName);
        }

        List<Product> product = repo.findAll();
        model.addAttribute("products", product);

        Integer total = invoices.stream().mapToInt(invoice -> invoice.getPrice() * invoice.getQty()).sum();
        model.addAttribute("total", total);

        Integer totalAmount = amounts.stream().mapToInt(Amount::getValue).sum();
        model.addAttribute("totalAmount", totalAmount);
        return "printarea";
    }

    @GetMapping("/print_area/{id}")
    public String showPrintArea(@PathVariable("id") Integer id, Model model) {
        Optional<Invoice> invoice = irepo.findById(id);
        if (invoice.isPresent()) {
            model.addAttribute("invoice", invoice.get());
        } else {
            
        }
        return "printarea";
    }

    @PostMapping("/delete_all_invoices")
    public String deleteAllInvoices() {
        irepo.deleteAll();
        return "redirect:/order_form";
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

    @GetMapping("/edit_income")
    public String showIncomeEdit(Model model, @RequestParam Integer id) {
        List<Invoice> invoices = irepo.findAll();
        model.addAttribute("invoices", invoices);

        List<Income> incomes = inrepo.findAll();
        model.addAttribute("incomes", incomes);

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

        List<String> paymentMethod = Arrays.asList("Cash", "QRIS", "Kredit/Debit");
        model.addAttribute("paymentMethod", paymentMethod);

        Invoice invoice = new Invoice();
        model.addAttribute("invoice", invoice);

        // Income income = new Income();
        // // model.addAttribute("income", income);

        Income income = inrepo.findById(id).get();
        model.addAttribute("income", income);

        Integer sumQris = inrepo.sumTotalByPaymethod("qris");
        Integer sumCash = inrepo.sumTotalByPaymethod("cash");
        Integer sumKredit = inrepo.sumTotalByPaymethod("kredit/debit");

        model.addAttribute("sumQris", sumQris != null ? sumQris : 0);
        model.addAttribute("sumCash", sumCash != null ? sumCash : 0);
        model.addAttribute("sumKredit", sumKredit != null ? sumKredit : 0);

        Integer sumBelumLunas = inrepo.sumTotalByStatus("belum lunas");
        model.addAttribute("sumBelumLunas", sumBelumLunas != null ? sumBelumLunas : 0);

        Integer sumLunas = inrepo.sumTotalByStatus("lunas");
        model.addAttribute("sumLunas", sumLunas != null ? sumLunas : 0);

        return "editIncome";
    }

    @PostMapping("/order_form")
    public String CreateInvoice(Model model, @jakarta.validation.Valid @ModelAttribute InvoiceDto invoiceDto,
            BindingResult result) {

        Product product = repo.findById(invoiceDto.getP_code()).orElse(null);
        if (product == null) {
            String errorMessage = "Invalid product code";
            result.rejectValue("p_code", "error.p_code", errorMessage);
            model.addAttribute("errorMessage", errorMessage);

            List<Invoice> invoices = irepo.findAll();
            model.addAttribute("invoices", invoices);

            List<Product> products = repo.findAll();
            model.addAttribute("products", products);

            List<Amount> amounts = arepo.findAll();
            model.addAttribute("amounts", amounts);

            InvoiceDto invoicesDto = new InvoiceDto();
            model.addAttribute("invoicesDto", invoicesDto);

            AmountDto amountDto = new AmountDto();
            model.addAttribute("amountDto", amountDto);

            Integer total = invoices.stream().mapToInt(invoice -> invoice.getPrice() * invoice.getQty()).sum();
            model.addAttribute("total", total);

            Integer totalAmount = amounts.stream().mapToInt(Amount::getValue).sum();
            model.addAttribute("totalAmount", totalAmount);

            List<String> statusValues = Arrays.asList("Lunas", "Belum Lunas");
            model.addAttribute("statusValues", statusValues);

            List<String> paymentMethod = Arrays.asList("Cash", "QRIS", "Kredit/Debit");
            model.addAttribute("paymentMethod", paymentMethod);

            Integer sumQris = inrepo.sumTotalByPaymethod("qris");
            Integer sumCash = inrepo.sumTotalByPaymethod("cash");
            Integer sumKredit = inrepo.sumTotalByPaymethod("kredit/debit");

            model.addAttribute("sumQris", sumQris != null ? sumQris : 0);
            model.addAttribute("sumCash", sumCash != null ? sumCash : 0);
            model.addAttribute("sumKredit", sumKredit != null ? sumKredit : 0);

            Integer sumBelumLunas = inrepo.sumTotalByStatus("belum lunas");
            model.addAttribute("sumBelumLunas", sumBelumLunas != null ? sumBelumLunas : 0);

            Integer sumLunas = inrepo.sumTotalByStatus("lunas");
            model.addAttribute("sumLunas", sumLunas != null ? sumLunas : 0);

            return "orderform";
        }

        if (product.getStock() < invoiceDto.getQty()) {
            String errorMessage = "Not enough product in stock";
            result.rejectValue("qty", "error.qty", errorMessage);
            model.addAttribute("errorMessage", errorMessage);

            List<Invoice> invoices = irepo.findAll();
            model.addAttribute("invoices", invoices);

            List<Product> products = repo.findAll();
            model.addAttribute("products", products);

            List<Amount> amounts = arepo.findAll();
            model.addAttribute("amounts", amounts);

            InvoiceDto invoicesDto = new InvoiceDto();
            model.addAttribute("invoicesDto", invoicesDto);

            AmountDto amountDto = new AmountDto();
            model.addAttribute("amountDto", amountDto);

            Integer total = invoices.stream().mapToInt(invoice -> invoice.getPrice() * invoice.getQty()).sum();
            model.addAttribute("total", total);

            Integer totalAmount = amounts.stream().mapToInt(Amount::getValue).sum();
            model.addAttribute("totalAmount", totalAmount);

            List<String> statusValues = Arrays.asList("Lunas", "Belum Lunas");
            model.addAttribute("statusValues", statusValues);

            List<String> paymentMethod = Arrays.asList("Cash", "QRIS", "Kredit/Debit");
            model.addAttribute("paymentMethod", paymentMethod);

            Integer sumQris = inrepo.sumTotalByPaymethod("qris");
            Integer sumCash = inrepo.sumTotalByPaymethod("cash");
            Integer sumKredit = inrepo.sumTotalByPaymethod("kredit/debit");

            model.addAttribute("sumQris", sumQris != null ? sumQris : 0);
            model.addAttribute("sumCash", sumCash != null ? sumCash : 0);
            model.addAttribute("sumKredit", sumKredit != null ? sumKredit : 0);

            Integer sumBelumLunas = inrepo.sumTotalByStatus("belum lunas");
            model.addAttribute("sumBelumLunas", sumBelumLunas != null ? sumBelumLunas : 0);

            Integer sumLunas = inrepo.sumTotalByStatus("lunas");
            model.addAttribute("sumLunas", sumLunas != null ? sumLunas : 0);

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
        // income.setOid(invoice.getId());
        income.setTotal(invoice.getPrice() * invoice.getQty());
        income.setPaymethod(invoice.getPaymethod());
        income.setStatus(invoice.getStatus());
        income.setP_code(product);
        inrepo.save(income);

        product.setStock(product.getStock() - invoiceDto.getQty());
        repo.save(product);

        return "redirect:/order_form";
    }

    @PostMapping("/create_amount")
    public String createAmount(@ModelAttribute AmountDto amountDto, BindingResult result) {
        if (amountDto.getValue() == null) {
            result.rejectValue("value", "error.value", "Amount value must not be null");
            return "amountform";
        }
        arepo.deleteAll();

        
        Amount amount = new Amount();
        amount.setValue(amountDto.getValue());

        
        arepo.save(amount);

        
        Integer oid = amount.getId();

        List<Invoice> invoices = irepo.findAll();


        for (Invoice invoice : invoices) {
            Income income = new Income();
            income.setCus_name(invoice.getCus_name());
            income.setDate(invoice.getDate());
            income.setQty(invoice.getQty());
            income.setTotal(invoice.getPrice() * invoice.getQty());
            income.setPaymethod(invoice.getPaymethod());
            income.setOid(invoice.getId() / 2);
            income.setStatus(invoice.getStatus());
            income.setP_code(invoice.getP_code());

            
            if (income.getP_code().equals(invoice.getP_code()) &&
                    income.getCus_name().equals(invoice.getCus_name())) {
                income.setOid(oid);
            } else {
                income.setOid(null);
            }

            inrepo.save(income);
        }

        inrepo.deleteByOidIsNull();

        return "redirect:/amountform";

    }

    @PostMapping("/delete_invoice/{id}")
    public String deleteInvoice(@PathVariable Integer id) {
        try {
            irepo.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
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
        product.setStock(productDto.getStock());
        product.setS_price(productDto.getS_price());
        product.setP_price(productDto.getP_price());
        product.setImgurl(storageFileName);
        product.setCreatedAt(createdAt);

        repo.save(product);
        return "redirect:/create";
    }

    @PostMapping("/edit_income/{id}")
    public String editIncome(@PathVariable Integer id, @Valid @ModelAttribute IncomeDto incomeDto,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "money_page";
        }
    
        try {
            Income income = inrepo.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid income Id:" + id));
            model.addAttribute("income", income);
    
            income.setPaymethod(incomeDto.getPaymethod());
            income.setStatus(incomeDto.getStatus());
            inrepo.save(income);
    
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/money_page";
        }
    
        return "redirect:/money_page";
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