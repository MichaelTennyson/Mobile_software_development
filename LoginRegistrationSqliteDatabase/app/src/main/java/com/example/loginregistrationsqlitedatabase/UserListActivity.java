package com.example.loginregistrationsqlitedatabase;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UserListActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        textView = findViewById(R.id.textview);
        String emailfromIntent = getIntent().getStringExtra("EMAIL");
        textView.setText(emailfromIntent);
    }
}
