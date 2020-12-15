package com.example.shopping.repository.local.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.example.shopping.model.User;
import com.example.shopping.repository.local.DatabaseHandler;
import com.example.shopping.repository.local.dao.UserDao;
import com.example.shopping.utility.AsyncResponse;

import java.util.List;

public class UserRepository {
    private static final String TAG = "UserRepository";

    private final UserDao userDao;

    public UserRepository(Application application) {
        DatabaseHandler handler = DatabaseHandler.getInstance(application);
        userDao = handler.userDao();
    }

    public void get(String username, AsyncResponse asyncResponse) {
        new GetUserAsyncTask(userDao, asyncResponse).execute(username);
    }

    public void insert(User user) {
        new InsertUserAsyncTask(userDao).execute(user);
    }

    public void getAll(AsyncResponse asyncResponse) {
        new GetAllAsyncTask(userDao, asyncResponse).execute();
    }

    private static class GetUserAsyncTask extends AsyncTask<String, Void, User> {

        public AsyncResponse asyncResponse;
        private final UserDao userDao;

        public GetUserAsyncTask(UserDao userDao, AsyncResponse asyncResponse) {
            this.userDao = userDao;
            this.asyncResponse = asyncResponse;
        }

        @Override
        protected User doInBackground(String... strings) {
            return userDao.get(strings[0]);
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);

            asyncResponse.onAsyncProcessFinish(user);
        }
    }

    private static class InsertUserAsyncTask extends AsyncTask<User, Void, Void> {
        private final UserDao userDao;

        public InsertUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.insert(users[0]);
            return null;
        }
    }

    private static class GetAllAsyncTask extends AsyncTask<Void, Void, List<User>> {
        public AsyncResponse asyncResponse;
        private final UserDao userDao;

        public GetAllAsyncTask(UserDao userDao, AsyncResponse asyncResponse) {
            this.asyncResponse = asyncResponse;
            this.userDao = userDao;
        }

        @Override
        protected List<User> doInBackground(Void... voids) {
            return userDao.getAll();
        }

        @Override
        protected void onPostExecute(List<User> users) {
            super.onPostExecute(users);

            asyncResponse.onAsyncProcessFinish(users);
        }
    }
}
