package com.example.bookodyssey.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bookodyssey.R;
import com.example.bookodyssey.activity.DetailActivity;
import com.example.bookodyssey.adapter.BooksAdapter;
import com.example.bookodyssey.api.ApiConfig;
import com.example.bookodyssey.api.ApiService;
import com.example.bookodyssey.model.Book;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private RecyclerView rv_book_Home;
    private BooksAdapter booksAdapter;
    private List<Book> book;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ImageView search = view.findViewById(R.id.IV_Search);
        ImageView saved = view.findViewById(R.id.IV_Saved);
        ImageView profile = view.findViewById(R.id.IV_Profile);

        search.setOnClickListener(v -> navigateToFragment(new SearchFragment()));
        saved.setOnClickListener(v -> navigateToFragment(new SavedFragment()));
        profile.setOnClickListener(v -> navigateToFragment(new ProfileFragment()));

        rv_book_Home = view.findViewById(R.id.rvBooksHome);
        rv_book_Home.setLayoutManager(new GridLayoutManager(getContext(), 3));
        book = new ArrayList<>();
        booksAdapter = new BooksAdapter(book, getContext());
        rv_book_Home.setAdapter(booksAdapter);

        booksAdapter.setOnItemClickListener(book -> {
            Intent intent = new Intent(getContext(), DetailActivity.class);
            intent.putExtra("id", book.getId());
            intent.putExtra("title", book.getTitle());
            intent.putExtra("author", book.getAuthor());
            intent.putExtra("genre", String.join(", ", book.getGenre()));
            intent.putExtra("publication_year", book.getPublicationYear());
            intent.putExtra("description", book.getDescription());
            intent.putExtra("cover_image", book.getCoverImage());
            startActivity(intent);
        });

        loadBooks();

        return view;
    }

    private void navigateToFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.nav_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void loadBooks() {
        ApiService apiService = ApiConfig.getApiService();
        Call<List<Book>> call = apiService.getBooks();

        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Book> books = response.body();
                    Log.d("HomeFragment", "Books data received: " + books.toString());
                    booksAdapter.setBooksList(books);
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("HomeFragment", "API response error: " + errorBody);
                        Toast.makeText(getContext(), "Failed to retrieve data: " + errorBody, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Log.e("HomeFragment", "Error parsing error response", e);
                        Toast.makeText(getContext(), "Failed to retrieve data", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Toast.makeText(getContext(), "An error occurred: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("HomeFragment", "API call failed", t);
            }
        });
    }
}
