package com.examly.springapp.respository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.examly.springapp.model.Product;

// MovieRepository.java
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByUser_UserId(Long userId);
    // @Query("SELECT m FROM Movie m WHERE m.user.userId = :userId")
    // List<Movie> findByUser_Id(@Param("userId") Long userId);

    // List<Movie> findByUser_UserIdAndMovieNameContaining(Long userId, String
    // movieName);

    // List<Movie> findByUser_UserIdAndMovieNameContaining(Long userId, String
    // searchValue);

    List<Product> findAll(Sort sort);

    List<Product> findByProductNameContainingIgnoreCase(String searchValue, Sort sort);
    // List<Movie> findByMovieNameContainingIgnoreCase(String sort);

    List<Product> findByUser_UserIdAndProductNameContainingIgnoreCase(Long userId, String searchValue);

}
