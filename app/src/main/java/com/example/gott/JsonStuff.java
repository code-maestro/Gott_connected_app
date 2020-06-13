package com.example.gott;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class JsonStuff extends Activity {

    private ProgressBar mLoadingProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_stuff);

        mLoadingProgress = findViewById(R.id.pb_loading);

        try {
            URL bookUrl = ApiUtil.buildUrl("Cooking");
            new BookQueryTask().execute(bookUrl);
        }catch (Exception e){
           Log.d("onCreate: ", Objects.requireNonNull(e.getMessage()));
        }
    }

    public class BookQueryTask extends AsyncTask<URL, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            TextView error_msg = (TextView) findViewById(R.id.error_txt);

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
                    Log.d("Error", Objects.requireNonNull(e.getMessage()));
                }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            TextView txt = findViewById(R.id.display);
            TextView error_msg = (TextView) findViewById(R.id.error_txt);

            mLoadingProgress.setVisibility(View.INVISIBLE);

            if (result == null){
                txt.setVisibility(View.INVISIBLE);
                error_msg.setVisibility(View.VISIBLE);
            }else{
                txt.setVisibility(View.VISIBLE);
                error_msg.setVisibility(View.INVISIBLE);
            }

            ArrayList<Book> books = ApiUtil.getBooksFromJson(result);
            String resultString = "";

            for (Book book : books){
                resultString = resultString + book.title + "\n"
                        + book.publisher +"\n"
                + book.published_date + "\n\n\n";
            }

            txt.setText(resultString);
        }
    }
}