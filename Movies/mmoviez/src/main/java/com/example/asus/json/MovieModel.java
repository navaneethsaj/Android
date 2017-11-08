package com.example.asus.json;

/**
 * Created by ASUS on 11/3/2017.
 */

class MovieModel {
    String movie;
    String year;
    String director;
    String story;
    String screenplay;
    String cast;

    public MovieModel(String movie, String year, String director, String story, String screenplay, String cast) {
        this.movie = movie;
        this.year = year;
        this.director = director;
        this.story = story;
        this.screenplay = screenplay;
        this.cast = cast;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getScreenplay() {
        return screenplay;
    }

    public void setScreenplay(String screenplay) {
        this.screenplay = screenplay;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }
}
