package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.reactivex.rxjava3.annotations.NonNull;

public class todos_details extends AppCompatActivity {

    private TextView gorevdetail,aciklamadetail,tarihdetail;
    FloatingActionButton meditbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todos_details);
        gorevdetail=findViewById(R.id.gorevdetails);
        aciklamadetail=findViewById(R.id.aciklamadetails);
        tarihdetail=findViewById(R.id.dateTodo);
        meditbtn=findViewById(R.id.gotoedittodo);
        Toolbar toolbar=findViewById(R.id.toolbarofnotedetail);
        setSupportActionBar(toolbar);

        Intent data=getIntent();
        meditbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),editnoteactivity.class);
                intent.putExtra("tarih",data.getStringExtra("tarih"));
                intent.putExtra("gorev",data.getStringExtra("gorev"));
                intent.putExtra("aciklama",data.getStringExtra("aciklama"));
                intent.putExtra("todoId",data.getStringExtra("todoId"));
                v.getContext().startActivity(intent);
            }
        });

        tarihdetail.setText(data.getStringExtra("tarih"));
        gorevdetail.setText(data.getStringExtra("gorev"));
        aciklamadetail.setText(data.getStringExtra("aciklama"));

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home)
        {
            startActivity(new Intent(this, ToDoActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
    }
