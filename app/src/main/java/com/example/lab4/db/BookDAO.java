package com.example.lab4.db;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao()
public interface BookDAO {

    @Query("SELECT * FROM books WHERE title LIKE '%' || :title || '%' COLLATE NOCASE")
    List<BookDBEntity> getBooks(String title);
    @Query("SELECT * FROM books where key = :key")
    BookDBEntity getBookByKey(String key);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBookList(List<BookDBEntity> books);

    @Query("DELETE FROM books WHERE title LIKE '%' || :title || '%' COLLATE NOCASE")
    void deleteBooksByTitle(String title);
    @Query("DELETE FROM books WHERE key =  :key ")
    void deleteBookByKey(String key);


    default void updateBooks(String title, List<BookDBEntity> books){
        deleteBooksByTitle(title);
        insertBookList(books);
    }

}
