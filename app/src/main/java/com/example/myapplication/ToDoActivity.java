package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;

import javax.xml.transform.sax.SAXResult;

public class ToDoActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;

    private DatabaseReference reference;
    private FirebaseAuth myAuth;
    private FirebaseUser mUser;
    private String onlineUserID;

    private ProgressDialog loader;

    private String key="";
    private String gorev;
    private  String aciklama;

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
        loader=new ProgressDialog(this);

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

        final AlertDialog dialog=myDialog.create();
        dialog.setCancelable(false);
        final EditText gorevtxt=myView.findViewById(R.id.gorev);
        final EditText aciklamatxt=myView.findViewById(R.id.aciklama);
        Button savebtn=myView.findViewById(R.id.ekleBtn);
        Button cancelbtn=myView.findViewById(R.id.iptalBtn);

        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mgorev=gorevtxt.getText().toString().trim();
                String maciklama=aciklamatxt.getText().toString().trim();
                String id=reference.push().getKey();
                String date= DateFormat.getDateInstance().format(new Date());

                if(TextUtils.isEmpty(mgorev)){
                    gorevtxt.setError("Lütfen Görev Giriniz !!");
                    return;
                }
                if(TextUtils.isEmpty(maciklama)){
                    gorevtxt.setError("Lütfen Açıklama Giriniz !!");
                    return;
                }else{
                 loader.setMessage("Verileriniz ekleniyor..");
                 loader.setCanceledOnTouchOutside(false);
                 loader.show();

                 Model_todo model=new Model_todo(mgorev,maciklama,id,date);
                 reference.child(id).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                     @Override
                     public void onComplete(@NonNull Task<Void> task) {
                         if(task.isSuccessful())
                         {
                             Toast.makeText(ToDoActivity.this,"Görev başarı ile kaydedildi.",Toast.LENGTH_SHORT).show();
                             loader.dismiss();
                         }else{
                             String error=task.getException().toString();
                             Toast.makeText(ToDoActivity.this,"Hata",Toast.LENGTH_SHORT).show();
                             loader.dismiss();
                         }
                     }
                 });
                }
            }
        });
        dialog.show();

    }

    private void initCompenents() {
        toolbar=findViewById(R.id.homeToolBar);
        recyclerView=findViewById(R.id.recyclerView);
        floatingActionButton=findViewById(R.id.flo);
        myAuth=FirebaseAuth.getInstance();
        mUser=myAuth.getCurrentUser();
        onlineUserID=mUser.getUid();
        reference= FirebaseDatabase.getInstance().getReference().child("gorevler").child(onlineUserID);

    }
}