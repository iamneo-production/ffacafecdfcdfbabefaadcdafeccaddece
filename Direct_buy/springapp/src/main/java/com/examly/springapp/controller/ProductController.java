package com.examly.springapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.model.ApiResponse;
import com.examly.springapp.model.Product;
import com.examly.springapp.model.ProductDTO;
import com.examly.springapp.model.User;
import com.examly.springapp.service.ProductService;
import com.examly.springapp.service.UserService;

@RestController
// questMapping("/admin")
// @CrossOrigin(origins = "http://localhost:8081")

public class ProductController {
    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    // @PostMapping("/api/product")
    // @CrossOrigin(origins = "http://localhost:8081")
    // public ApiResponse addProduct(@RequestBody Product product, @RequestParam
    // Long userId) {
    // User user = userService.getUserById(userId);
    // product.setUser(user);

    // Product addedProduct = productService.addProduct(product);

    // if (addedProduct != null) {
    // return new ApiResponse("Product added successfully");
    // } else {
    // return new ApiResponse("Failed to add Product");
    // }
    // }
    @PostMapping("/api/product")
    // @CrossOrigin(origins = "http://localhost:8081")
    public ApiResponse addProduct(@RequestBody Product product, @RequestParam Long userId) {
        User user = userService.getUserById(userId);

        if (user == null) {
            return new ApiResponse("Failed to add product. User not found.");
        }

        product.setUser(user);

        Product addedProduct = productService.addProduct(product, userId);

        if (addedProduct != null) {
            return new ApiResponse("Product added successfully");
        } else {
            return new ApiResponse("Failed to add product");
        }
    }

    @GetMapping("/api/product/{id}")
    // @CrossOrigin(origins = "http://localhost:8081")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @DeleteMapping("/api/product/{id}")
    // @CrossOrigin(origins = "http://localhost:8081")
    public ApiResponse deleteProductById(@PathVariable Long id) {
        boolean result = productService.deleteProductById(id);

        if (result) {
            return new ApiResponse("Product deleted successfully");
        } else {
            return new ApiResponse(" not found or could not be deleted");
        }
    }

    @PutMapping("/api/product/{id}")
    // @CrossOrigin(origins = "http://localhost:8081")
    public ApiResponse updateProductById(@PathVariable Long id, @RequestBody Product updatedProduct) {
        Product result = productService.updateProductById(id, updatedProduct);

        if (result != null) {
            return new ApiResponse("Product updated successfully");
        } else {
            return new ApiResponse("Product not found or could not be updated");
        }
    }

    @GetMapping("/api/product/user/{userId}")
    // @CrossOrigin(origins = "http://localhost:8081")
    public List<Product> getProductsByUserIdAndSearchValue(
            @PathVariable Long userId,
            @RequestParam(required = false) String searchValue) {
        return productService.getProductsByUserIdAndSearchValue(userId, searchValue);
    }

    @GetMapping("/api/products")
    // @CrossOrigin(origins = "http://localhost:8081")
    public List<ProductDTO> getAllproducts(
            @RequestParam(name = "sortOrder", defaultValue = "asc") String sortOrder,
            @RequestParam(name = "searchValue", required = false) String searchValue) {

        return productService.getAllProducts(sortOrder, searchValue);
    }
}
