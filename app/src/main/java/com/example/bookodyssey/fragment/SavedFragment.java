package com.example.bookodyssey.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookodyssey.helper.DatabaseHelper;
import com.example.bookodyssey.R;
import com.example.bookodyssey.activity.DetailActivity;
import com.example.bookodyssey.adapter.CollectionAdapter;
import com.example.bookodyssey.model.Book;

import java.util.ArrayList;
import java.util.List;

public class SavedFragment extends Fragment {

    private RecyclerView recyclerView;
    private CollectionAdapter adapter;
    private DatabaseHelper databaseHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved, container, false);

        recyclerView = view.findViewById(R.id.rvCollectionBooks);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        adapter = new CollectionAdapter(new ArrayList<>(), getContext());
        recyclerView.setAdapter(adapter);

        databaseHelper = new DatabaseHelper(getContext());
        loadSavedBooks();

        // Handle item click listener
        adapter.setOnItemClickListener(book -> {
            Intent intent = new Intent(getContext(), DetailActivity.class);
            intent.putExtra("title", book.getTitle());
            intent.putExtra("author", book.getAuthor());
            intent.putExtra("genre", TextUtils.join(", ", book.getGenre()));
            intent.putExtra("publication_year", book.getPublicationYear());
            intent.putExtra("description", book.getDescription());
            intent.putExtra("cover_image", book.getCoverImage());
            startActivity(intent);
        });

        ImageView search = view.findViewById(R.id.IV_Search);
        ImageView home = view.findViewById(R.id.IV_Home);
        ImageView profile = view.findViewById(R.id.IV_Profile);

        search.setOnClickListener(v -> {
            SearchFragment searchFragment = new SearchFragment();
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.nav_container, searchFragment)
                    .addToBackStack(null)
                    .commit();
        });

        home.setOnClickListener(v -> {
            HomeFragment homeFragment = new HomeFragment();
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.nav_container, homeFragment)
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

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadSavedBooks();
    }

    private void loadSavedBooks() {
        List<Book> savedBooks = databaseHelper.getAllBooks();
        adapter.setBooksList(savedBooks);
    }
}

