package com.example.architectureexample;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {
    private CategoryRepository repository;
    private LiveData<List<Category>> allCategories;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        repository = new CategoryRepository(application);
        allCategories = repository.getAllCategories();
    }

    public void insert(Category category) {
        repository.insert(category);
    }

    public void delete(Category category) {
        repository.delete(category);
    }

    public LiveData<List<Category>> getAllCategories() {
        return allCategories;
    }
}
