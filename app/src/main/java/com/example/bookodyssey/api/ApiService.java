package com.example.bookodyssey.api;

import com.example.bookodyssey.model.BookResponse;
import com.example.bookodyssey.model.Book;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("books")
    Call<List<Book>> getBooks();

    @GET("books/{id}")
    Call<BookResponse> getDetail(@Path("id") String id);
}