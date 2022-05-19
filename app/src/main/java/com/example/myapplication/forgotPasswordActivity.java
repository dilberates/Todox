package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotPasswordActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        EditText minputEmail2 = findViewById(R.id.inputEmail2);
        Button mbtnBaglanti = findViewById(R.id.btnBaglanti);
        TextView mbackLogin = findViewById(R.id.backLogin);

        firebaseAuth = FirebaseAuth.getInstance();

        mbackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(forgotPasswordActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });

        mbtnBaglanti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = minputEmail2.getText().toString().trim();
                if (mail.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Mailinizi giriniz.", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Kurtarma e-poastası gönderildi.", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(forgotPasswordActivity.this, loginActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), "E-posta yanlış veya böyle bir hesap yok!", Toast.LENGTH_SHORT).show();
                            }
                        }


                    });
                }
            }
        });
    }
}