package com.example.bookodyssey.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookodyssey.R;
import com.example.bookodyssey.activity.DetailActivity;
import com.example.bookodyssey.model.Book;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private List<Book> books;
    private List<Book> booksFiltered;
    private Context context;

    public SearchAdapter(List<Book> books, Context context) {
        this.books = books;
        this.booksFiltered = new ArrayList<>(books);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book book = booksFiltered.get(position);
        holder.title.setText(book.getTitle());
        holder.author.setText(book.getAuthor());

        // Set OnClickListener to navigate to DetailActivity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("id", book.getId());
            intent.putExtra("title", book.getTitle());
            intent.putExtra("author", book.getAuthor());
            intent.putExtra("genre", String.join(", ", book.getGenre()));
            intent.putExtra("publication_year", book.getPublicationYear());
            intent.putExtra("description", book.getDescription());
            intent.putExtra("cover_image", book.getCoverImage());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return booksFiltered.size();
    }

    public void filter(String query) {
        booksFiltered.clear();
        if (query.isEmpty()) {
            booksFiltered.addAll(books);
        } else {
            String queryLower = query.toLowerCase();
            for (Book book : books) {
                if (book.getTitle().toLowerCase().contains(queryLower)) {
                    booksFiltered.add(book);
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView author;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.search_title);
            author = itemView.findViewById(R.id.search_author);
        }
    }
}
