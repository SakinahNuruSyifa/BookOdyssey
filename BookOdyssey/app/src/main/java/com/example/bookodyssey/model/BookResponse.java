package com.example.bookodyssey.model;

import com.example.bookodyssey.model.Book;

import java.util.List;

public class BookResponse {
    private List<Book> novels;

    public List<Book> getNovels() {
        return novels;
    }

    public void setNovels(List<Book> novels) {
        this.novels = novels;
    }
}
