package com.examly.springapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.model.Movie;
import com.examly.springapp.model.User;
import com.examly.springapp.service.MovieService;
import com.examly.springapp.service.UserService;

@RestController
@RequestMapping("/admin")

public class MovieController {
    @Autowired
    MovieService movieService;

    @Autowired
    UserService userService;

    @PostMapping("/api/movie")
    public Movie addMovie(@RequestBody Movie movie, @RequestParam Long userId) {
        User user = userService.getUserById(userId);
        movie.setUser(user);
        return movieService.addMovie(movie);
    }

    @GetMapping("/api/movie/{id}")
    public Movie getMovieById(@PathVariable Long id) {
        return movieService.getMovieById(id);
    }

    @DeleteMapping("/api/movie/{id}")
    public boolean deleteMovieById(@PathVariable Long id) {
        return movieService.deleteMovieById(id);
    }

    @PutMapping("/api/movie/{id}")
    public Movie updateMovieById(@PathVariable Long id, @RequestBody Movie updatedMovie) {
        return movieService.updateMovieById(id, updatedMovie);
    }

    @GetMapping("/api/movie/user/{userId}")
    public List<Movie> getMoviesByUserIdAndSearchValue(
            @PathVariable Long userId,
            @RequestParam(required = false) String searchValue) {
        return movieService.getMoviesByUserIdAndSearchValue(userId, searchValue);
    }

    @GetMapping("/api/movies")
    public List<Movie> getAllMovies(
            @RequestParam(name = "sortOrder", defaultValue = "asc") String sortOrder,
            @RequestParam(name = "searchValue", required = false) String searchValue) {

        return movieService.getAllMovies(sortOrder, searchValue);
    }
}
