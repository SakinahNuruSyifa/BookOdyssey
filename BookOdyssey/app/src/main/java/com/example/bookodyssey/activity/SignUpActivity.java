package com.example.bookodyssey.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookodyssey.R;

public class SignUpActivity extends AppCompatActivity {

    EditText et_uname, et_pw;
    Button btn_signUp;
    TextView tv_signin;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        sharedPreferences = getSharedPreferences("pref", MODE_PRIVATE);
        et_uname = findViewById(R.id.et_uname_signup);
        et_pw = findViewById(R.id.et_pw_signup);
        btn_signUp = findViewById(R.id.btn_signup);
        tv_signin = findViewById(R.id.haveaccount);

        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_uname.getText().toString();
                String password = et_pw.getText().toString();

                if (username.isEmpty()) {
                    et_uname.setError("Masukkan username");
                    return;
                }
                if (password.isEmpty()) {
                    et_pw.setError("Masukkan Password");
                    return;
                }

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Username", username);
                editor.putString("Password", password);
                // editor.putBoolean("isLoggedIn", false);
                editor.apply();

                Toast.makeText(SignUpActivity.this, "Berhasil Sign Up", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                finish();
            }
        });

        tv_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
            }
        });
    }
}
