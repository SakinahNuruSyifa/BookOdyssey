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
import androidx.appcompat.app.AppCompatDelegate;

import com.example.bookodyssey.R;

public class SignInActivity extends AppCompatActivity {

    EditText et_uname, et_pw;
    Button btn_signIn;
    TextView tv_signup;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);

        sharedPreferences = getSharedPreferences("pref", MODE_PRIVATE);

        int theme = sharedPreferences.getInt("theme", 0);
        if (theme == 1) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        if (isLoggedIn){
            startActivity(new Intent(SignInActivity.this, HomeActivity.class));
            finish();
        }

        et_uname = findViewById(R.id.et_uname_signin);
        et_pw = findViewById(R.id.et_pw_signin);
        btn_signIn = findViewById(R.id.btn_signin);
        tv_signup = findViewById(R.id.donthaveaccount);

        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_uname.getText().toString();
                String password = et_pw.getText().toString();

                if (username.isEmpty()) {
                    et_uname.setError("Masukkan username");
                    return;
                }
                if (password.isEmpty()) {
                    et_pw.setError("Masukkan password");
                    return;
                }

                String saveUname = sharedPreferences.getString("Username", "");
                String savePw = sharedPreferences.getString("Password", "");

                if (username.equals(saveUname) && password.equals(savePw)) {

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isLoggedIn", true);
                    editor.apply();

                    startActivity(new Intent(SignInActivity.this, HomeActivity.class));
                    finish();
                } else {
                    Toast.makeText(SignInActivity.this, "Username atau Password salah", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });
    }
}
