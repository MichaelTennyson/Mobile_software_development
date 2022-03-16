package com.example.loginregistrationsqlitedatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.NestedScrollView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = LoginActivity.this;

    private NestedScrollView nestedScrollView;

    private  TextInputLayout lay_Name;
    private TextInputLayout lay_Email;
    private TextInputLayout lay_Password;

    private EditText et_Name;
    private EditText et_Email;
    private EditText et_Password;

    private AppCompatButton appCompatButtonLogin;
    private AppCompatButton appCompatButtonDelete;
    private AppCompatButton appCompatButtonEdit;

    private AppCompatTextView textViewLinkRegister;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();
    }

    private void initViews() {

        nestedScrollView = findViewById(R.id.nestedScrollView);
        lay_Email = findViewById(R.id.lay_Email);
        lay_Password = findViewById(R.id.lay_Password);
        et_Name = findViewById(R.id.et_Name);
        et_Email = findViewById(R.id.et_Email);
        et_Password = findViewById(R.id.et_Password);
        appCompatButtonLogin = findViewById(R.id.appCompatButtonLogin);
        appCompatButtonDelete = findViewById(R.id.appCompatButtonDelete);
        appCompatButtonEdit = findViewById(R.id.appCompatButtonEdit);
        textViewLinkRegister = findViewById(R.id.textViewLinkRegister);

    }


    private void initListeners() {
        appCompatButtonLogin.setOnClickListener(this);
        appCompatButtonDelete.setOnClickListener(this);
        appCompatButtonEdit.setOnClickListener(this);
        textViewLinkRegister.setOnClickListener(this);
    }

    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);

    }

    //Switch statement
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompatButtonLogin:
                verifyFromSQLite();
                break;
            case R.id.appCompatButtonDelete:
                removeFromSQLite();
                break;
            case R.id.appCompatButtonEdit:
                Intent intentEdit = new Intent(getApplicationContext(), EditActivity.class);
                startActivity(intentEdit);
                break;
            case R.id.textViewLinkRegister:
                Intent intentRegister = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(intentRegister);
                break;
        }
    }

    private void verifyFromSQLite() {
        if (!inputValidation.isInputEditTextFilled(et_Email, lay_Email, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(et_Email, lay_Email, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(et_Password, lay_Password, getString(R.string.error_message_email))) {
            return;
        }

        if (databaseHelper.checkUser(et_Email.getText().toString().trim(), et_Password.getText().toString().trim())) {
            Intent accountsIntent = new Intent(activity, UserListActivity.class);
            accountsIntent.putExtra("EMAIL", et_Email.getText().toString().trim());
            emptyInputEditText();
            startActivity(accountsIntent);


        } else {
            // Snack Bar to show success message that record is wrong
            Snackbar.make(nestedScrollView, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
        }
    }

    private void removeFromSQLite() {
        if (!inputValidation.isInputEditTextFilled(et_Name, lay_Name, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(et_Email, lay_Email, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(et_Email, lay_Email, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(et_Password, lay_Password, getString(R.string.error_message_email))) {
            return;
        }

        if (databaseHelper.checkUser(et_Email.getText().toString().trim(), et_Password.getText().toString().trim())) {
            databaseHelper.deleteUser(user);
        } else {
            // Snack Bar to show success message that record is wrong
            Snackbar.make(nestedScrollView, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
        }

    }


    private void emptyInputEditText() {
        et_Email.setText(null);
        et_Password.setText(null);
    }
}
