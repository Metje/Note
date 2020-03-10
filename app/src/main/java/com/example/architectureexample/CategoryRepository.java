package com.example.architectureexample;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CategoryRepository {
    private CategoryDao categoryDao;
    private LiveData<List<Category>> allCategories;

    public CategoryRepository(Application application) {
        MainDatabase database = MainDatabase.getInstance(application);
        categoryDao = database.categoryDao();
        allCategories = categoryDao.getAllCategories();
    }

    public void insert(Category category){
        new InsertCategoryAsyncTasc(categoryDao).execute(category);
    }

    public void delete(Category category){
        new DeleteCategoryAsyncTasc(categoryDao).execute(category);
    }

    public LiveData<List<Category>> getAllCategories() {
        return allCategories;
    }

    private static class InsertCategoryAsyncTasc extends AsyncTask <Category, Void, Void> {

        private CategoryDao categoryDao;

        private InsertCategoryAsyncTasc(CategoryDao categoryDao){
            this.categoryDao = categoryDao;
        }

        @Override
        protected Void doInBackground (Category... categories){
            categoryDao.insert(categories[0]);
            return null;
        }

    }


    private static class DeleteCategoryAsyncTasc extends AsyncTask <Category, Void, Void> {

        private CategoryDao categoryDao;

        private DeleteCategoryAsyncTasc (CategoryDao categoryDao){
            this.categoryDao = categoryDao;
        }

        @Override
        protected Void doInBackground (Category... categories){
            categoryDao.delete(categories[0]);
            return null;
        }

    }


}
