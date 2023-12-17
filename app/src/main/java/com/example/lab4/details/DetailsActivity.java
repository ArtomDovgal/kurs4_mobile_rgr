package com.example.lab4.details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.lab4.App;
import com.example.lab4.R;
import com.example.lab4.model.Book;

public class DetailsActivity extends AppCompatActivity {

    public static final String EXTRA_BOOK_ID = "BOOK_ID";
    public static final String AUTHORS = "authors";
    private TextView titleTextView,authorTextView,ratingTextView,
            countWantToReadTextView,numberOfPagesMedianTextView,firstPublishYearTextView;
    private ProgressBar progressBar;
    private DetailsViewModel viewModel;
    private ImageView imageView;

    //private RatingBar ratingBar;

    private Button toMainPageButton;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        titleTextView = findViewById(R.id.titleTextView);
        authorTextView = findViewById(R.id.authorTextView);
        ratingTextView = findViewById(R.id.ratingTextView);
        countWantToReadTextView = findViewById(R.id.countWantReadTextView);
        numberOfPagesMedianTextView = findViewById(R.id.numberOfPagesMedianTextView);
        firstPublishYearTextView = findViewById(R.id.firstPublishYearTextView);
        progressBar = findViewById(R.id.progressBarDetails);
        imageView = findViewById(R.id.imageView);
        toMainPageButton = findViewById(R.id.button_to_main_page);
        //ratingBar = findViewById(R.id.ratingBar);
        String bookKey = getIntent().getStringExtra(EXTRA_BOOK_ID);
        String bookAuthors = getIntent().getStringExtra(AUTHORS);

        if(bookKey == null){
            throw new RuntimeException("There is no book with such key");
        }

        App app = (App) getApplication();
        ViewModelProvider viewModelProvider = new ViewModelProvider(this,app.getViewModelFactory());
        viewModel =viewModelProvider.get(DetailsViewModel.class);

        viewModel.loadBookByKey(bookKey);
        viewModel.getResults().observe(this,result->{
            titleTextView.setText("");
            authorTextView.setText("");
            ratingTextView.setText("");
            countWantToReadTextView.setText("");
            numberOfPagesMedianTextView.setText("");
            firstPublishYearTextView.setText("");
            //imageView.setVisibility(View.INVISIBLE);

            switch (result.getStatus()){

                case SUCCESS:
                    Book book = result.getData();
                    titleTextView.setText(book.getTitle());
                    authorTextView.setText(bookAuthors);
                    ratingTextView.setText(R.string.rating);
                    ratingTextView.append(book.getRating());
                    countWantToReadTextView.setText(R.string.count_want_to_read);
                    numberOfPagesMedianTextView.setText(R.string.number_of_pages_median);
                    firstPublishYearTextView.setText(R.string.first_publish_year);
                    imageView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    //ratingBar.setRating(Float.parseFloat(book.getRating()));

                    if(book.getImage() != null) {
                        imageView.setImageBitmap(book.getImage());

                    }else{
                        progressBar.setVisibility(View.INVISIBLE);
                        imageView.setImageResource(R.drawable.image_not_found);
                    }

                    if(book.getCountWantToRead()==0){
                        countWantToReadTextView.append("Information is missing");
                    }else{
                        countWantToReadTextView.append(book.getCountWantToRead().toString());
                    }

                    if(book.getNumberOfPagesMedian()==0){
                        numberOfPagesMedianTextView.append("Information is missing");
                    }else{
                        numberOfPagesMedianTextView.append(book.getNumberOfPagesMedian().toString());
                    }

                    if(book.getFirstPublishYear()==0){
                        firstPublishYearTextView.append("Information is missing");
                    }else{
                        firstPublishYearTextView.append(book.getFirstPublishYear().toString());
                    }

                    progressBar.setVisibility(View.GONE);
                    break;

                case EMPTY:
                    progressBar.setVisibility(View.GONE);
                    break;

                case LOADING:
                    progressBar.setVisibility(View.VISIBLE);

            }
        });

        toMainPageButton.setOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed();
        });
    }
}