package com.example.gott;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {
    // global variables to store the book details
    public String id;
    public String title;
    public String subTitle;
    public String[] authors;
    public String publisher;
    public String published_date;

    // CONSTRUCTOR
    public Book(String id,
                String title,
                String subTitle,
                String[] authors,
                String publisher,
                String published_date){

        // INSTANTIATE THE GLOBAL VARIABLES.
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.authors = authors;
        this.publisher = publisher;
        this.published_date = published_date;
    }

    protected Book(Parcel in) {
        id = in.readString();
        title = in.readString();
        subTitle = in.readString();
        authors = in.createStringArray();
        publisher = in.readString();
        published_date = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(subTitle);
        dest.writeStringArray(authors);
        dest.writeString(publisher);
        dest.writeString(published_date);
    }
}
