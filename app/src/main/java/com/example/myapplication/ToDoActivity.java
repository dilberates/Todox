package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.sax.SAXResult;

public class ToDoActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    DatabaseReference reference;

    private ProgressDialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        initCompenents();

        Toolbar toolbar=findViewById(R.id.homeToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        loader=new ProgressDialog(this);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        reference=FirebaseDatabase.getInstance().getReference().child("myTodo");


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask();
            }

        });

    }


    private void addTask() {
        AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);

        View myView = inflater.inflate(R.layout.input_file_todo, null);
        myDialog.setView(myView);

        final AlertDialog dialog = myDialog.create();
        dialog.setCancelable(false);
        final EditText gorevtxt = myView.findViewById(R.id.gorev);
        final EditText aciklamatxt = myView.findViewById(R.id.aciklama);
        Button savebtn = myView.findViewById(R.id.ekleBtn);
        Button cancelbtn = myView.findViewById(R.id.iptalBtn);

        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mgorev = gorevtxt.getText().toString().trim();
                String maciklama = aciklamatxt.getText().toString().trim();
                String date =DateFormat.getDateInstance().format(new Date());

                if (mgorev.isEmpty() || maciklama.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Lütfen her iki alanı da doldurunuz!", Toast.LENGTH_SHORT).show();
                } else {
                    loader.setMessage("Verileriniz ekleniyor..");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();
                    DocumentReference documentReference = firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("myTodo").document();
                    Map<String, Object> note = new HashMap<>();
                    note.put("gorev", mgorev);
                    note.put("aciklama", maciklama);
                    note.put("tarih",date);

                    documentReference.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(), "Gorev kaydedildi", Toast.LENGTH_SHORT).show();
                            loader.dismiss();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Gorev kaydedilemedi", Toast.LENGTH_SHORT).show();
                            loader.dismiss();
                        }
                    });
                }
            }
        });

        dialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Model_todo> options = new FirebaseRecyclerOptions.Builder<Model_todo>()
                .setQuery(reference, Model_todo.class).build();

        FirebaseRecyclerAdapter<Model_todo, MyView> adapter = new FirebaseRecyclerAdapter<Model_todo, MyView>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyView holder, int position, @NonNull Model_todo model) {
            holder.setDate(model.getDate());
            holder.setTask(model.getGorev());
            holder.setDescription(model.getAciklama());
            }

            @NonNull
            @Override
            public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.retrieved_layout, parent, false);
                return new MyView(view);
            }
    };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
}
    public static class MyView extends RecyclerView.ViewHolder{
        View mView;

        public MyView(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
        }
        public void setTask(String task){
            TextView taskTextView= (TextView) mView.findViewById(R.id.gorevTv);
            taskTextView.setText(task);
        }
        public void setDate(String date){
            TextView taskTextView= (TextView) mView.findViewById(R.id.dateTv);
            taskTextView.setText(date);
        }
        public void setDescription(String description){
            TextView taskTextView= (TextView) mView.findViewById(R.id.aciklamaTv);
            taskTextView.setText(description);
        }
    }


                private void initCompenents () {
                    toolbar = findViewById(R.id.homeToolBar);
                    recyclerView = findViewById(R.id.recyclerView);
                    floatingActionButton = findViewById(R.id.flo);


                }
            }