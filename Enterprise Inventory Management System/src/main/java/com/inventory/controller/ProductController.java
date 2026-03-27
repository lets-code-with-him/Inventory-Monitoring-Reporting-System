package com.inventory.controller;

import com.inventory.entity.Product;
import com.inventory.repository.ProductRepository;
import com.inventory.service.ProductService;
import com.inventory.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ReportService reportService;

    @GetMapping("/products")
    public String products(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "products";
    }

    @GetMapping("/products/add")
    public String addProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "add-product";
    }

    @PostMapping("/products/save")
    public String saveProduct(@ModelAttribute Product product) {
        productService.addProduct(product);
        return "redirect:/products";
    }


    @GetMapping("/low-stock")
    public String lowStock(Model model) {
        model.addAttribute("products", productService.getLowStockProducts());
        return "low-stock";
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }


    @Autowired
    private ProductRepository productRepository;
    @GetMapping("/products/search")
    public String searchProduct(@RequestParam String keyword, Model model) {

        List<Product> products =
                productRepository.findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(keyword, keyword);

        model.addAttribute("products", products);

        return "products";
    }

    // CSV EXPORT ENDPOINT
    @GetMapping("/reports/export")
    public void exportCSV(HttpServletResponse response) throws Exception {
        reportService.exportProductsToCSV(response);
    }

    @GetMapping("/reports/logs/export")
    public void exportLogsCSV(HttpServletResponse response) throws Exception {
        reportService.exportStockLogsToCSV(response);
    }

    @GetMapping("/products/update/{id}")
    public String updateStock(@PathVariable Long id,
                              @RequestParam int quantity,
                              @RequestParam String type) {

        productService.updateStock(id, quantity, type);
        return "redirect:/products";
    }

    // Show Edit Form
    @GetMapping("/products/edit/{id}")
    public String editProduct(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "edit-product";
    }

    // Save Updated Product
    @PostMapping("/products/update")
    public String updateProduct(@ModelAttribute Product product) {
        productService.updateProductDetails(product);
        return "redirect:/products";
    }

}