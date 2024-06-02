package com.example.bookodyssey.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.bookodyssey.helper.DatabaseHelper;
import com.example.bookodyssey.R;
import com.example.bookodyssey.model.Book;

import java.util.Arrays;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = "DetailActivity";
    private ImageView coverImage, imgSave, back;
    private TextView title, author, genre, publicationYear, description;
    private Book book;
    private DatabaseHelper databaseHelper;
    private boolean isBookSaved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        coverImage = findViewById(R.id.img_cover);
        imgSave = findViewById(R.id.img_save);
        title = findViewById(R.id.detail_title);
        author = findViewById(R.id.detail_author);
        genre = findViewById(R.id.detail_genre);
        publicationYear = findViewById(R.id.detail_pubYear);
        description = findViewById(R.id.detail_desc);
        back = findViewById(R.id.iv_back);

        databaseHelper = new DatabaseHelper(this);

        // Get data from intent
        String bookTitle = getIntent().getStringExtra("title");
        String bookAuthor = getIntent().getStringExtra("author");
        String bookGenre = getIntent().getStringExtra("genre");
        String bookPublicationYear = getIntent().getStringExtra("publication_year");
        String bookDescription = getIntent().getStringExtra("description");
        String bookCoverImage = getIntent().getStringExtra("cover_image");

        // Set data to views
        title.setText("Title : " + bookTitle);
        author.setText("Author : " + bookAuthor);
        genre.setText("Genre : " + bookGenre);
        publicationYear.setText("Publication Year : " + bookPublicationYear);
        description.setText("Description : \n" + bookDescription);
        coverImage.setImageResource(R.drawable.cover);
        // Picasso.get().load(bookCoverImage).into(coverImage); // Use Picasso to load the cover image

        book = new Book();
        book.setTitle(bookTitle);
        book.setAuthor(bookAuthor);
        book.setGenre(Arrays.asList(bookGenre.split(", ")));
        book.setPublicationYear(bookPublicationYear);
        book.setDescription(bookDescription);
        book.setCoverImage(bookCoverImage);

        isBookSaved = databaseHelper.isBookSaved(book);
        updateSaveButtonImage();

        imgSave.setOnClickListener(v -> toggleSaveBook());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailActivity.this, HomeActivity.class));
            }
        });
    }

    private void updateSaveButtonImage() {
        if (isBookSaved) {
            imgSave.setImageResource(R.drawable.save);
        } else {
            imgSave.setImageResource(R.drawable.bookmark);
        }
    }


    private void toggleSaveBook() {
        if (isBookSaved) {
            databaseHelper.removeBook(book);
            Toast.makeText(this, "Book removed from saved collection", Toast.LENGTH_SHORT).show();
        } else {
            databaseHelper.addBook(book);
            Toast.makeText(this, "Book added to saved collection", Toast.LENGTH_SHORT).show();
        }
        isBookSaved = !isBookSaved;
        updateSaveButtonImage();
        Log.i(TAG, "Book details saved/removed: " + book.toString());
    }
}
