package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Movie;
import com.examly.springapp.model.MovieDTO;
import com.examly.springapp.respository.MovieRepository;
import com.examly.springapp.respository.UserRepository;

@Service

public class MovieService {
    @Autowired
    MovieRepository movierepo;
    @Autowired
    UserRepository userRepo;

    public Movie addMovie(Movie movie) {
        return movierepo.save(movie);
    }

    public Movie getMovieById(Long id) {
        return movierepo.findById(id).orElse(null);
    }

    // public boolean deleteMovieById(Long id) {
    // Optional<Movie> optionalMovie = movierepo.findById(id);
    // if (optionalMovie.isPresent()) {
    // Movie movie = optionalMovie.get();

    // // If the movie has a user, delete the user
    // User user = movie.getUser();
    // if (user != null) {
    // userRepo.deleteById(user.getUserId());
    // }

    // // Now delete the movie
    // movierepo.deleteById(id);
    // return true;
    // }
    // return false;
    // }

    public boolean deleteMovieById(Long id) {
        Optional<Movie> optionalMovie = movierepo.findById(id);
        if (optionalMovie.isPresent()) {
            Movie movie = optionalMovie.get();

            // Remove the association with the user
            movie.setUser(null);

            // Now delete the movie
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
        return movierepo.findByUser_UserId(userId);
    }

    // search and sort
    public List<MovieDTO> getAllMovies(String sortOrder, String searchValue) {
        Sort sort = getSortObject(sortOrder, "movieName");
        List<Movie> movies;

        if (searchValue != null && !searchValue.isEmpty()) {
            movies = movierepo.findByMovieNameContainingIgnoreCase(searchValue, sort);
        } else {
            movies = movierepo.findAll(sort);
        }

        return movies.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private MovieDTO convertToDTO(Movie movie) {
        MovieDTO dto = new MovieDTO();
        dto.setMovieId(movie.getMovieId());
        dto.setMovieName(movie.getMovieName());
        dto.setMovieType(movie.getMovieType());
        dto.setTicketRate(movie.getTicketRate());
        dto.setNoOfTicketsAvailable(movie.getNoOfTicketsAvailable());
        dto.setShowDate(movie.getShowDate());
        dto.setShowTime(movie.getShowTime());
        dto.setCoverImage(movie.getCoverImage());
        dto.setUserId(movie.getUser().getUserId()); // Set the userId here
        return dto;
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
        return movierepo.findByUser_UserIdAndMovieNameContainingIgnoreCase(userId, searchValue);
    }

}
