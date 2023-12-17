package com.example.lab4;

import androidx.lifecycle.ViewModel;

import com.example.lab4.services.BooksService;

public class BaseViewModel extends ViewModel {

    private BooksService booksService;

    public BaseViewModel(BooksService booksService) {
        super();
        this.booksService = booksService;
    }

    public BooksService getTolkienBooksService(){
        return this.booksService;
    }
}
