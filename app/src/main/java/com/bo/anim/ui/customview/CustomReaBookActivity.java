package com.bo.anim.ui.customview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bo.anim.R;
import com.bo.anim.custom.ReadBook1;

public class CustomReaBookActivity extends AppCompatActivity {
    private ReadBook1 book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_rea_book);
        book = (ReadBook1) findViewById(R.id.book);

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                book.pathAnimStartOrStop();
            }
        });
    }
}
