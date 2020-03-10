package com.example.architectureexample;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CategoryDao {

    @Insert
    void insert(Category cat);

    @Delete
    void delete(Category cat);

    @Query ("SELECT * FROM category_table ORDER BY category ASC")
    LiveData<List<Category>> getAllCategories();
}
