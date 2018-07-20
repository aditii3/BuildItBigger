package com.example.android.mylibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokesActivity extends AppCompatActivity {
    public static final String INTENT_EXTRA_JOKE = "extrajoke";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jokes);

        String joke = getIntent().getStringExtra(INTENT_EXTRA_JOKE);
        TextView jokeView = findViewById(R.id.joke);
        if(jokeView!=null){
            jokeView.setText(joke);
        }
    }
}
