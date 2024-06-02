package com.example.bookodyssey.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.bookodyssey.model.Book;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "saved_db";
    private static final int DATABASE_VERSION = 3;

    private static final String TABLE_BOOKS = "saved";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_AUTHOR = "author";
    private static final String COLUMN_PUBLICATION_YEAR = "publication_year";
    private static final String COLUMN_GENRE = "genre";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_COVER_IMAGE = "cover_image";

    private static final String TAG = "DatabaseHelper";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BOOKS_TABLE = "CREATE TABLE " + TABLE_BOOKS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_AUTHOR + " TEXT,"
                + COLUMN_PUBLICATION_YEAR + " TEXT,"
                + COLUMN_GENRE + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_COVER_IMAGE + " TEXT" + ")";
        db.execSQL(CREATE_BOOKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS);
        onCreate(db);
    }

    public void addBook(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_TITLE, book.getTitle());
            values.put(COLUMN_AUTHOR, book.getAuthor());
            values.put(COLUMN_PUBLICATION_YEAR, book.getPublicationYear());
            values.put(COLUMN_GENRE, String.join(", ", book.getGenre()));
            values.put(COLUMN_DESCRIPTION, book.getDescription());
            values.put(COLUMN_COVER_IMAGE, book.getCoverImage());

            long result = db.insert(TABLE_BOOKS, null, values);

            if (result == -1) {
                Log.e(TAG, "Failed to insert book");
            } else {
                Log.i(TAG, "Book inserted successfully");
            }
        } catch (SQLException e) {
            Log.e(TAG, "SQLException: " + e.getMessage());
        } finally {
            db.close();
        }
    }

    public void removeBook(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            int rowsDeleted = db.delete(TABLE_BOOKS, COLUMN_TITLE + "=? AND " + COLUMN_AUTHOR + "=?", new String[]{book.getTitle(), book.getAuthor()});

            if (rowsDeleted == 0) {
                Log.e(TAG, "Failed to delete book");
            } else {
                Log.i(TAG, "Book deleted successfully");
            }
        } catch (SQLException e) {
            Log.e(TAG, "SQLException: " + e.getMessage());
        } finally {
            db.close();
        }
    }

    public boolean isBookSaved(Book book) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        boolean exists = false;

        try {
            cursor = db.query(TABLE_BOOKS, null, COLUMN_TITLE + "=? AND " + COLUMN_AUTHOR + "=?", new String[]{book.getTitle(), book.getAuthor()}, null, null, null);
            exists = cursor != null && cursor.getCount() > 0;
        } catch (SQLException e) {
            Log.e(TAG, "SQLException: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return exists;
    }

    public List<Book> getAllBooks() {
        List<Book> bookList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_BOOKS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    Book book = new Book();
                    book.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                    book.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)));
                    book.setAuthor(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR)));
                    book.setPublicationYear(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PUBLICATION_YEAR)));
                    book.setGenre(Arrays.asList(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENRE)).split(", ")));
                    book.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)));
                    book.setCoverImage(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COVER_IMAGE)));
                    bookList.add(book);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            Log.e(TAG, "SQLException: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return bookList;
    }
}
