// package com.tokoteratai.project111.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.validation.BindingResult;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// import com.tokoteratai.project111.model.Account;
// import com.tokoteratai.project111.service.AccountService;

// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.validation.Valid;
// import lombok.extern.slf4j.Slf4j;

// @Slf4j
// @Controller
// @RequestMapping("/auth/login")
// public class LoginController {
//     @Autowired
//     private AccountService accountService;

//     @GetMapping
//     public String view(@ModelAttribute("user") Account username, Model model, BindingResult bindingResult) {
//         return "pages/auth/login";
//     }

//     @PostMapping
//     public String login(@Valid @ModelAttribute("user") Account username, BindingResult bindingResult, Model model, HttpServletRequest request, RedirectAttributes redirectAttrs) {
//         if (bindingResult.hasErrors()) {
//             return "pages/auth/login";
//         }
    
//         Account account = accountService.authenticate(username);
    
//         if (account != null) {
//             log.info("Authentication successful");
//             request.getSession().setAttribute("user", account);
//             return "redirect:/";
//         } else {
//             redirectAttrs.addFlashAttribute("error_message", "Password or username is incorrect");
//             return "redirect:/auth/login";
//         }
//     }
// }