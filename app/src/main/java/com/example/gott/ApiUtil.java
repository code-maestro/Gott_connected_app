package com.example.gott;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class ApiUtil {
    private ApiUtil(){}

    public static final String BASE_API_URL = "https://www.googleapis.com/books/v1/volumes";

    public static final String QUERY_PARAMETER_KEY = "q";

    // API KEY CONSTANTS
    public static final String KEY = "key";
    public static final String API_KEY = "AIzaSyCzIlbD2_P8ijiOawIzKt2qN-OMecjE15I";

    public static URL buildUrl(String title){

        URL url = null;

        // BUILD THE URL WITH THE URI
        Uri uri = Uri.parse(BASE_API_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAMETER_KEY, title)
                .appendQueryParameter(KEY, API_KEY)
                .build();

        try {
            url = new URL(uri.toString());
        }catch (Exception e){
            e.printStackTrace();
        }

        return url;
    }

    public static String getJson(URL url) throws IOException{
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // CHECKING IF THE URL HAS DATA
            try {
                InputStream stream = connection.getInputStream();

                Scanner scanner = new Scanner(stream);
                scanner.useDelimiter("\\A");

                boolean hasData = scanner.hasNext();
                if (hasData){
                    return scanner.next();
                }else {
                    return null;
                }

            }catch (Exception e){
                Log.d("Error", e.toString());
                return null;
            }finally {
                connection.disconnect();
            }
            
    }

    // ARRAY LIST FUNCTION/ METHOD TO GET THE BOOKS FROM JSON
    public static ArrayList<Book> getBooksFromJson(String json){

        // constants for the json data
        final String ID = "id";
        final String TITLE = "title";
        final String SUBTITLE = "subtitle";
        final String AUTHORS = "authors";
        final String PUBLISHER = "publisher";
        final String PUBLISHED_DATE = "published_date";
        final String ITEMS = "items";
        final String VOLUMEINFO = "volumeInfo";

        ArrayList<Book> books = new ArrayList<Book>();
        try {
            JSONObject jsonBooks = new JSONObject(json);
            JSONArray arrayBooks = jsonBooks.getJSONArray(ITEMS);

            // retrieving the number of books in the json file retrieved from WEB SERVICE API URL
            int numberOfBooks = arrayBooks.length();

            // for loop to iterate through the books retrieved from webservice
            for (int i = 0; i<numberOfBooks; i++){
                JSONObject bookJSON = arrayBooks.getJSONObject(i);
                JSONObject volumeInfoJSON = bookJSON.getJSONObject(VOLUMEINFO);

                // RETRIEVING NUMBER OF THE AUTHORS
                int authorNo = volumeInfoJSON.getJSONArray(AUTHORS).length();

                String[] authors = new String[authorNo];

                //FOR LOOP LOOPING THROUGH EM AUTHORS.
                for (int j=0; j<authorNo; j++){
                    authors[j] = volumeInfoJSON.getJSONArray(AUTHORS).get(j).toString();
                }

                // book with all necessary data
                Book buch = new Book(
                        bookJSON.getString(ID),
                        volumeInfoJSON.getString(TITLE),
                        (volumeInfoJSON.isNull(SUBTITLE) ? " " : volumeInfoJSON.getString(SUBTITLE)),
                        authors,
                        volumeInfoJSON.getString(PUBLISHER),
                        volumeInfoJSON.getString(PUBLISHED_DATE));

                // ADD THE NW BOOK TO THE BOOKS
                books.add(buch);
            }


        }catch (JSONException e){
            e.printStackTrace();
        }
        return books;

    }
}
