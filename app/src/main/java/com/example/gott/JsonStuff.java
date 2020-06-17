package com.example.gott;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class JsonStuff extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private ProgressBar mLoadingProgress;

    private RecyclerView rv_books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_stuff);

        mLoadingProgress = findViewById(R.id.pb_loading);

//        RECYCLER VIEW AND ITS LAYOUT
        rv_books = findViewById(R.id.rv_books);
        LinearLayoutManager books_layout = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);

        rv_books.setLayoutManager(books_layout);

        try {
            URL bookUrl = ApiUtil.buildUrl("Cooking");
            new BookQueryTask().execute(bookUrl);
        }catch (Exception e){
           Log.d("onCreate: ", Objects.requireNonNull(e.getMessage()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.book_list_menu, menu);
        final MenuItem searchitem = findViewById(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchitem);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        try {
            URL bookURL = ApiUtil.buildUrl(query);
            new BookQueryTask().execute(bookURL);
        }catch (Exception e){
            Log.d("error", e.getMessage());
        }

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    public class BookQueryTask extends AsyncTask<URL, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            TextView error_msg = findViewById(R.id.error_txt);

            error_msg.setVisibility(View.INVISIBLE);
            mLoadingProgress.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(URL... urls) {
                URL search = urls[0];
                String result = null;

                try{
                    result = ApiUtil.getJson(search);
                }catch (IOException e){
                    Log.d("Error", e.getMessage());
                }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            TextView error_msg = findViewById(R.id.error_txt);

            mLoadingProgress.setVisibility(View.INVISIBLE);

            if (result == null){
                rv_books.setVisibility(View.INVISIBLE);
                error_msg.setVisibility(View.VISIBLE);
            }else{
                rv_books.setVisibility(View.VISIBLE);
                error_msg.setVisibility(View.INVISIBLE);
            }

            ArrayList<Book> books = ApiUtil.getBooksFromJson(result);

            BooksAdapter adapter = new BooksAdapter(books);
            rv_books.setAdapter(adapter);
        }
    }
}