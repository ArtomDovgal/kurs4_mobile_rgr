package com.example.lab4;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.lab4.services.BooksService;

import java.lang.reflect.Constructor;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private BooksService booksService;

    public ViewModelFactory(BooksService booksService) {
        this.booksService = booksService;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        try {
            Constructor<T> constructor =  modelClass.getConstructor(BooksService.class);
            return constructor.newInstance(booksService);
        }catch (ReflectiveOperationException e){
            Log.e(this.getClass().getSimpleName(),"Error",e);
            RuntimeException exception = new RuntimeException();
            exception.initCause(e);
            throw exception;
        }

    }

}
