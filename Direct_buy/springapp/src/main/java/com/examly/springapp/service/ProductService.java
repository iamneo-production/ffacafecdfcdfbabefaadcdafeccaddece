package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.examly.springapp.model.ApiResponse;
import com.examly.springapp.model.Product;
import com.examly.springapp.model.ProductDTO;
import com.examly.springapp.model.User;
import com.examly.springapp.respository.ProductRepository;
import com.examly.springapp.respository.UserRepository;

@Service

public class ProductService {
    @Autowired
    ProductRepository productrepo;
    @Autowired
    UserRepository userRepo;
    @Autowired
    UserService userService;

    // public Product addProduct(Product product) {
    // return productrepo.save(product);
    // }
    public Product addProduct(Product product, Long userId) {
        User user = userService.getUserById(userId);

        if (user != null) {
            product.setUser(user);
            return productrepo.save(product);
        } else {
            // Handle the case when the user is not found
            // You can throw an exception, return null, or handle it according to your
            // requirements.
            return null;
        }
    }

    public Product getProductById(Long id) {
        return productrepo.findById(id).orElse(null);
    }

    public boolean deleteProductById(Long id) {
        Optional<Product> optionalProduct = productrepo.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();

            // If the movie has a user, delete the user
            User user = product.getUser();
            if (user != null) {
                userRepo.deleteById(user.getUserId());
            }

            // Now delete the movie
            productrepo.deleteById(id);
            return true;
        }
        return false;
    }

    // public Product updateProductById(Long id, Product updatedProduct) {
    // if (productrepo.existsById(id)) {
    // updatedProduct.setProductId(id);
    // return productrepo.save(updatedProduct);
    // }
    // return null;
    // }
    public Product updateProductById(Long id, Product updatedProduct) {
        if (productrepo.existsById(id)) {
            // Retrieve the existing product
            Product existingProduct = productrepo.findById(id).orElse(null);

            if (existingProduct != null) {
                // Set the user association to the updated product
                updatedProduct.setUser(existingProduct.getUser());

                // Set the productId to ensure it's the same as the existing one
                updatedProduct.setProductId(id);

                // Save the updated product
                return productrepo.save(updatedProduct);
            }
        }
        return null;
    }

    // public List<Movie> getMoviesByUserIdAndSearchValue(Long userId, String
    // searchValue) {
    // return movierepo.findByUser_IdAndMovieNameContaining(userId, searchValue);
    // }

    public List<Product> getProductsByUserId(Long userId) {
        return productrepo.findByUser_UserId(userId);
    }

    // search and sort
    public List<ProductDTO> getAllProducts(String sortOrder, String searchValue) {
        Sort sort = getSortObject(sortOrder, "productName");
        List<Product> products;

        if (searchValue != null && !searchValue.isEmpty()) {
            products = productrepo.findByProductNameContainingIgnoreCase(searchValue, sort);
        } else {
            products = productrepo.findAll(sort);
        }

        return products.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setProductId(product.getProductId());
        dto.setProductName(product.getProductName());
        dto.setPrice(product.getPrice());
        dto.setDescription(product.getDescription());
        dto.setCoverImage(product.getCoverImage());

        // Assuming category, origin, and quantity are attributes of Product
        dto.setCategory(product.getCategory());
        dto.setOrigin(product.getOrigin());
        dto.setQuantity(product.getQuantity());

        // Assuming User is associated with Product
        if (product.getUser() != null) {
            dto.setUserId(product.getUser().getUserId());
        }

        return dto;
    }

    private Sort getSortObject(String sortOrder, String sortBy) {
        if ("asc".equalsIgnoreCase(sortOrder)) {
            return Sort.by(Sort.Direction.ASC, sortBy);
        } else {
            return Sort.by(Sort.Direction.DESC, sortBy);
        }
    }

    public List<Product> getProductsByUserIdAndSearchValue(Long userId, String searchValue) {
        System.out.println("userId: " + userId + ", searchValue: " + searchValue);
        return productrepo.findByUser_UserIdAndProductNameContainingIgnoreCase(userId, searchValue);
    }

}
