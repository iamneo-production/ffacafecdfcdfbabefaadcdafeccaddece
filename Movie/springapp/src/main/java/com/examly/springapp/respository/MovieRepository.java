package com.examly.springapp.respository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.examly.springapp.model.Movie;

// MovieRepository.java
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByUser_Id(Long userId);

    List<Movie> findByUser_IdAndMovieNameContaining(Long userId, String searchValue);

    List<Movie> findAll(Sort sort);

    List<Movie> findByMovieNameContainingIgnoreCase(String searchValue, Sort sort);
    // List<Movie> findByMovieNameContainingIgnoreCase(String sort);

    List<Movie> findByUser_IdAndMovieNameContainingIgnoreCase(Long userId, String searchValue);

}
