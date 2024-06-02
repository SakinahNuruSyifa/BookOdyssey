package com.example.bookodyssey.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.widget.SearchView;

import com.example.bookodyssey.R;
import com.example.bookodyssey.adapter.SearchAdapter;
import com.example.bookodyssey.api.ApiConfig;
import com.example.bookodyssey.api.ApiService;
import com.example.bookodyssey.model.Book;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    private RecyclerView rv_search;
    private SearchView searchView;
    private SearchAdapter searchAdapter;
    private ProgressBar progressBar;
    private Handler handler;

    public SearchFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        rv_search = view.findViewById(R.id.rv_search);
        searchView = view.findViewById(R.id.searchView);
        progressBar = view.findViewById(R.id.progressBar);
        handler = new Handler(Looper.getMainLooper());

        rv_search.setLayoutManager(new LinearLayoutManager(getContext()));

        ImageView home = view.findViewById(R.id.IV_Home);
        ImageView saved = view.findViewById(R.id.IV_Saved);
        ImageView profile = view.findViewById(R.id.IV_Profile);

        home.setOnClickListener(v -> {
            HomeFragment homeFragment = new HomeFragment();
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.nav_container, homeFragment)
                    .addToBackStack(null)
                    .commit();
        });
        saved.setOnClickListener(v -> {
            SavedFragment savedFragment = new SavedFragment();
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.nav_container, savedFragment)
                    .addToBackStack(null)
                    .commit();
        });
        profile.setOnClickListener(v -> {
            ProfileFragment profileFragment = new ProfileFragment();
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.nav_container, profileFragment)
                    .addToBackStack(null)
                    .commit();
        });

        loadBooks();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (searchAdapter != null) {
                    searchAdapter.filter(newText);
                }
                return true;
            }
        });

        return view;
    }

    private void loadBooks() {
        handler.post(() -> progressBar.setVisibility(View.VISIBLE));
        ApiService apiService = ApiConfig.getApiService();
        Call<List<Book>> call = apiService.getBooks();

        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Book> books = response.body();
                    searchAdapter = new SearchAdapter(books, getContext());
                    rv_search.setAdapter(searchAdapter);
                    handler.post(() -> progressBar.setVisibility(View.GONE));
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                // Handle failure
            }
        });
    }
}
