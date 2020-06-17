package com.example.gott;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder> implements View.OnClickListener{

    ArrayList<Book> books;

    public BooksAdapter(ArrayList<Book> books){
        this.books = books;
    }


    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.book_list, parent,
                false);

        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = books.get(position);
        holder.Bind(book);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    @Override
    public void onClick(View v) {

    }

    public class BookViewHolder extends RecyclerView.ViewHolder{

        // VARIABLES FOR THE RECYCLER VIEW LAYOUT
        TextView title;
        TextView subtitle;
        TextView publisher;
        TextView publishedDate;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            subtitle = itemView.findViewById(R.id.subtitle);
            publisher = itemView.findViewById(R.id.publisher);
            publishedDate = itemView.findViewById(R.id.publishedDate);
        }

        public void Bind(Book book){
            title.setText(book.title);
            String authors = "";
            int i = 0;

            for (String author: book.authors) {
                authors+=author;
                i++;

                if (i < book.authors.length) {
                    authors+=", ";
                    
                }
            }

            subtitle.setText(authors);
            publisher.setText(book.publisher);
            publishedDate.setText(book.published_date);

        }
    }
}
