package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.ColorSpace;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
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
import com.google.firebase.firestore.Query;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.xml.transform.sax.SAXResult;

public class ToDoActivity extends AppCompatActivity {

    private Toolbar toolbar=null;
    private RecyclerView recyclerView=null;
    private FloatingActionButton floatingActionButton=null;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;

    FirestoreRecyclerAdapter<Model_todo, TodoViewHolder> todoAdapter;


    private ProgressDialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        initCompenents();

        Toolbar toolbar = findViewById(R.id.homeToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);


        loader = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask();
            }

        });

        Query query = firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("myTodo").orderBy("tarih",Query.Direction.ASCENDING);;
        FirestoreRecyclerOptions<Model_todo> allusertodos = new FirestoreRecyclerOptions.Builder<Model_todo>().setQuery(query, Model_todo.class).build();
        todoAdapter=new FirestoreRecyclerAdapter<Model_todo, TodoViewHolder>(allusertodos) {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            protected void onBindViewHolder(@NonNull TodoViewHolder holder, int i, @NonNull Model_todo model) {

                holder.tarih.setText(model.getDate());
                holder.gorev.setText(model.getGorev());
                holder.aciklama.setText(model.getAciklama());//görüntüleme

                ImageView popupbutton=holder.itemView.findViewById(R.id.menupopbutton);

                String docId=todoAdapter.getSnapshots().getSnapshot(i).getId();
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(v.getContext(),todos_details.class);
                        intent.putExtra("gorev", model.getGorev());
                        intent.putExtra("tarih", model.getDate());
                        intent.putExtra("aciklama",model.getAciklama());
                        intent.putExtra("todoId",docId);
                        v.getContext().startActivity(intent);
                    }
                });

              /* popupbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                        popupMenu.setGravity(Gravity.END);
                        popupMenu.getMenu().add("Düzenle").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {

                                Intent intent = new Intent(v.getContext(), editnoteactivity.class);
                                intent.putExtra("title", firebasemodel.getTitle());
                                intent.putExtra("content", firebasemodel.getContent());
                                intent.putExtra("noteId", docId);
                                v.getContext().startActivity(intent);
                                return false;
                            }
                        });
                    }});*/


                       /* popupMenu.getMenu().add("Sil").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                DocumentReference documentReference=firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("myNotes").document(docId);
                                documentReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(v.getContext(),"Not silindi",Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(v.getContext(),"Not silinemedi",Toast.LENGTH_SHORT).show();
                                    }
                                });


                                return false;
                            }
                        });

                        popupMenu.show();
                    }
                });*/


            }

            @NonNull
            @Override
            public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.retrieved_layout,parent,false);
                return new TodoViewHolder(view);
            }
        };

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(todoAdapter);

    }
    @Override
    protected void onStart() {
        super.onStart();
        todoAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(todoAdapter!=null)
        {
            todoAdapter.stopListening();
        }
    }

    private void addTask () {
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
                String date = DateFormat.getDateInstance().format(new Date()).trim();

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
                    note.put("tarih", date);

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

    public class TodoViewHolder extends RecyclerView.ViewHolder {

        private TextView gorev;
        private TextView aciklama;
        private TextView tarih;
        LinearLayout mtodo;

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            gorev = itemView.findViewById(R.id.gorevTv);
            aciklama = itemView.findViewById(R.id.aciklamaTv);
            tarih = itemView.findViewById(R.id.dateTv);
            mtodo = itemView.findViewById(R.id.todo);
        }
    }

    private void initCompenents () {
            toolbar = findViewById(R.id.homeToolBar);
            recyclerView = findViewById(R.id.recyclerView);
            floatingActionButton = findViewById(R.id.flo);
        }

        }


