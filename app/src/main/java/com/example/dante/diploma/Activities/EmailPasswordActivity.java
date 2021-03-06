package com.example.dante.diploma.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dante.diploma.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmailPasswordActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private EditText etEmail, etPassword;
    private Button btnSignIn, btnSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_password);

        mAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                CheckUser();
            }
        };

        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnSignIn = findViewById(R.id.btn_sign_in);
        btnSignUp = findViewById(R.id.btn_sign_up);

        btnSignIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        CheckUser();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_sign_in:
                SignIn(etEmail.getText().toString(),
                        etPassword.getText().toString());
                break;
            case R.id.btn_sign_up:
                Intent intent =
                        new Intent(EmailPasswordActivity.this,
                                SignUpActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void SignIn(String email, String password){
        try {
            mAuth.signInWithEmailAndPassword(email, password).
                    addOnCompleteListener(this,
                            new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Intent intent =
                                new Intent(EmailPasswordActivity.this,
                                        CoursesActivity.class);
                        startActivity(intent);
                        Toast.makeText(EmailPasswordActivity.this,
                                "Авторизация прошла успешно", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(EmailPasswordActivity.this,
                                "Ошибка авторизации", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }catch (Exception ex){Toast.makeText(EmailPasswordActivity.this,
                "Пожалуйта, заполните пустые поля", Toast.LENGTH_LONG).show();
        }
    }

    public void CheckUser(){
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null) {
            Intent intent =
                    new Intent(EmailPasswordActivity.this,
                            CoursesActivity.class);
            startActivity(intent);
        }
    }
}
