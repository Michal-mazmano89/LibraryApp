package pl.mazmano89.library;

import java.io.Serializable;

public class Book implements Serializable {
    private String title;
    private Author author;

    private Genre genre;

    public Book(String title, Author author, Genre genre){
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public String getInfo(){
        return "Title: " + title + " | " + "Author: " + author.getInfo() + " | " + "Genre: " + genre;
    }
}
