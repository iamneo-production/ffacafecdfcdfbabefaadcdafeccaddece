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
import com.examly.springapp.model.Movie;
import com.examly.springapp.model.User;
import com.examly.springapp.service.MovieService;
import com.examly.springapp.service.UserService;

@RestController
// questMapping("/admin")
@CrossOrigin(origins = "http://localhost:8081")

public class MovieController {
    @Autowired
    MovieService movieService;

    @Autowired
    UserService userService;

    @PostMapping("/api/movie")
    @CrossOrigin(origins = "http://localhost:8081")
    public ApiResponse addMovie(@RequestBody Movie movie, @RequestParam Long userId) {
        User user = userService.getUserById(userId);
        movie.setUser(user);

        Movie addedMovie = movieService.addMovie(movie);

        if (addedMovie != null) {
            return new ApiResponse("Movie added successfully");
        } else {
            return new ApiResponse("Failed to add movie");
        }
    }

    @GetMapping("/api/movie/{id}")
    @CrossOrigin(origins = "http://localhost:8081")
    public Movie getMovieById(@PathVariable Long id) {
        return movieService.getMovieById(id);
    }

    @DeleteMapping("/api/movie/{id}")
    @CrossOrigin(origins = "http://localhost:8081")
    public ApiResponse deleteMovieById(@PathVariable Long id) {
        boolean result = movieService.deleteMovieById(id);

        if (result) {
            return new ApiResponse("Movie deleted successfully");
        } else {
            return new ApiResponse("Movie not found or could not be deleted");
        }
    }

    @PutMapping("/api/movie/{id}")
    @CrossOrigin(origins = "http://localhost:8081")
    public ApiResponse updateMovieById(@PathVariable Long id, @RequestBody Movie updatedMovie) {
        Movie result = movieService.updateMovieById(id, updatedMovie);

        if (result != null) {
            return new ApiResponse("Movie updated successfully");
        } else {
            return new ApiResponse("Movie not found or could not be updated");
        }
    }

    @GetMapping("/api/movie/user/{userId}")
    @CrossOrigin(origins = "http://localhost:8081")
    public List<Movie> getMoviesByUserIdAndSearchValue(
            @PathVariable Long userId,
            @RequestParam(required = false) String searchValue) {
        return movieService.getMoviesByUserIdAndSearchValue(userId, searchValue);
    }

    @GetMapping("/api/movies")
    @CrossOrigin(origins = "http://localhost:8081")
    public List<Movie> getAllMovies(
            @RequestParam(name = "sortOrder", defaultValue = "asc") String sortOrder,
            @RequestParam(name = "searchValue", required = false) String searchValue) {

        return movieService.getAllMovies(sortOrder, searchValue);
    }
}
