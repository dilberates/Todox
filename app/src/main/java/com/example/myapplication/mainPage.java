package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class mainPage extends AppCompatActivity {

    ImageView toDo,notes,pomodoro,week;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        initCompanenet();

        week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mainPage.this,weekActivity.class));
            }
        });


        pomodoro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mainPage.this,pomodoroActivity.class));
            }
        });

        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mainPage.this,notesActivitiy.class));
            }
        });

        toDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mainPage.this,toDoActivity.class));
            }
        });
    }

    private void initCompanenet() {
        toDo=findViewById(R.id.toDoBtn);
        notes=findViewById(R.id.notesBtn);
        pomodoro=findViewById(R.id.pomodoroBtn);
        week=findViewById(R.id.weekBtn);
    }
}