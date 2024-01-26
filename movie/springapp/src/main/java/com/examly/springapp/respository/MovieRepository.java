package com.examly.springapp.respository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.examly.springapp.model.Movie;

// MovieRepository.java
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByUser_UserId(Long userId);

    List<Movie> findAll(Sort sort);

    List<Movie> findByMovieNameContainingIgnoreCase(String searchValue, Sort sort);

    List<Movie> findByUser_UserIdAndMovieNameContainingIgnoreCase(Long userId, String searchValue);

    void deleteByUser_UserId(Long userId);

}
