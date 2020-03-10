package com.example.architectureexample;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Note.class, Category.class}, version = 2, exportSchema = false)
public abstract class MainDatabase extends RoomDatabase {
    private static MainDatabase instance;

    public abstract NoteDao noteDao();
    public abstract CategoryDao categoryDao();

    public static synchronized MainDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    MainDatabase.class,
                    "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback =  new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDao noteDao;
        private CategoryDao categoryDao;

        public PopulateDbAsyncTask(MainDatabase db) {
            this.noteDao = db.noteDao();
            this.categoryDao = db.categoryDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("Milch","Die gute von Tuffi",2));
            noteDao.insert(new Note("Müsli","Honey Bees",5));
            noteDao.insert(new Note("Patties","Von der Fleischtheke",1));

            categoryDao.insert(new Category("Obst"));
            categoryDao.insert(new Category("Fleisch"));
            categoryDao.insert(new Category("Sonstiges"));
            return null;
        }
    }
}
