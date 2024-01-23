package com.examly.springapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Movie;
import com.examly.springapp.respository.MovieRepository;

@Service

public class MovieService {
    @Autowired
    MovieRepository movierepo;

    public Movie addMovie(Movie movie) {
        return movierepo.save(movie);
    }

    public Movie getMovieById(Long id) {
        return movierepo.findById(id).orElse(null);
    }

    public boolean deleteMovieById(Long id) {
        if (movierepo.existsById(id)) {
            movierepo.deleteById(id);
            return true;
        }
        return false;
    }

    public Movie updateMovieById(Long id, Movie updatedMovie) {
        if (movierepo.existsById(id)) {
            updatedMovie.setMovieId(id);
            return movierepo.save(updatedMovie);
        }
        return null;
    }

    // public List<Movie> getMoviesByUserIdAndSearchValue(Long userId, String
    // searchValue) {
    // return movierepo.findByUser_IdAndMovieNameContaining(userId, searchValue);
    // }

    public List<Movie> getMoviesByUserId(Long userId) {
        return movierepo.findByUser_Id(userId);
    }

    // search and sort
    public List<Movie> getAllMovies(String sortOrder, String searchValue) {
        Sort sort = getSortObject(sortOrder, "movieName");

        if (searchValue != null && !searchValue.isEmpty()) {
            return movierepo.findByMovieNameContainingIgnoreCase(searchValue, sort);
        } else {
            return movierepo.findAll(sort);
        }
    }

    private Sort getSortObject(String sortOrder, String sortBy) {
        if ("asc".equalsIgnoreCase(sortOrder)) {
            return Sort.by(Sort.Direction.ASC, sortBy);
        } else {
            return Sort.by(Sort.Direction.DESC, sortBy);
        }
    }

    public List<Movie> getMoviesByUserIdAndSearchValue(Long userId, String searchValue) {
        System.out.println("userId: " + userId + ", searchValue: " + searchValue);
        return movierepo.findByUser_IdAndMovieNameContainingIgnoreCase(userId, searchValue);
    }

}
