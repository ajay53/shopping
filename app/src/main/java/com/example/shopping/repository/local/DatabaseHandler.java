package com.example.shopping.repository.local;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.shopping.model.Product;
import com.example.shopping.repository.local.dao.ProductDao;

@Database(entities = {Product.class}, version = 1, exportSchema = false)
public abstract class DatabaseHandler extends RoomDatabase {

    private static DatabaseHandler databaseInstance;

    public abstract ProductDao productDao();

    public static synchronized DatabaseHandler getInstance(Context context) {
        if (databaseInstance == null) {
            databaseInstance = Room.databaseBuilder(context.getApplicationContext(),
                    DatabaseHandler.class, "shopping_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return databaseInstance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(databaseInstance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private ProductDao productDao;

        public PopulateDbAsyncTask(DatabaseHandler handler) {
            productDao = handler.productDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            productDao.insert(new Product(99, "test title", 99.99, "test Description", "test url"));
            return null;
        }
    }
}
