package com.example.bookodyssey.model;

import java.util.List;

public class Book {
    private int id;
    private String title;
    private String author;
    private String publication_year; // Ubah dari int ke String
    private List<String> genre;
    private String description;
    private String cover_image;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublicationYear() {
        return publication_year;
    }

    public void setPublicationYear(String publicationYear) {
        this.publication_year = publicationYear;
    }

    public List<String> getGenre() {
        return genre;
    }

    public void setGenre(List<String> genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverImage() {
        return cover_image;
    }

    public void setCoverImage(String coverImage) {
        this.cover_image = coverImage;
    }
}
