package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ToDoActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        initCompenents();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Todo Uygulaması");
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask();
            }

        });
    }
    private void addTask() {
        AlertDialog.Builder myDialog=new AlertDialog.Builder(this);
        LayoutInflater inflater=LayoutInflater.from(this);
        View myView=inflater.inflate(R.layout.input_file_todo,null);
        myDialog.setView(myView);
        AlertDialog dialog=myDialog.create();
        dialog.setCancelable(false);
        dialog.show();

    }

    private void initCompenents() {
        toolbar=findViewById(R.id.homeToolBar);
        recyclerView=findViewById(R.id.recyclerView);
        floatingActionButton=findViewById(R.id.flo);

    }
}