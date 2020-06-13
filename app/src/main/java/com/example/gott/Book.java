package com.example.gott;

public class Book {
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
}
