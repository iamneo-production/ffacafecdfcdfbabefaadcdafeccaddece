package com.examly.springapp.model;

import java.sql.Time;
import java.time.LocalDate;

import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MovieDTO {
    private Long movieId;
    private String movieName;
    private String movieType;
    private Double ticketRate;
    private Integer noOfTicketsAvailable;
    @NotBlank(message = "date is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate showDate;
    private Time showTime;
    private String coverImage;
    private Long userId;

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieType() {
        return movieType;
    }

    public void setMovieType(String movieType) {
        this.movieType = movieType;
    }

    public Double getTicketRate() {
        return ticketRate;
    }

    public void setTicketRate(Double ticketRate) {
        this.ticketRate = ticketRate;
    }

    public Integer getNoOfTicketsAvailable() {
        return noOfTicketsAvailable;
    }

    public void setNoOfTicketsAvailable(Integer noOfTicketsAvailable) {
        this.noOfTicketsAvailable = noOfTicketsAvailable;
    }

    public LocalDate getShowDate() {
        return showDate;
    }

    public void setShowDate(LocalDate showDate) {
        this.showDate = showDate;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Time getShowTime() {
        return showTime;
    }

    public void setShowTime(Time showTime) {
        this.showTime = showTime;
    }

}
