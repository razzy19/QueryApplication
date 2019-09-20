package com.example.queryapplication.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Movie;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.queryapplication.R;
import com.example.queryapplication.controller.TeacherAdapter;
import com.example.queryapplication.model.TeacherModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TeacherHome extends AppCompatActivity {

    private List<TeacherModel> list;
    private RecyclerView recyclerView;
    private FirebaseFirestore firebaseFirestore;
    private TextView add,txtid,txtid2;
    private TeacherAdapter teachersAdapter;
    String teacherid,doc_id;
    Button signout;
    SharedPreferences sp;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_home);

       teacherid= FirebaseAuth.getInstance().getUid();
        firebaseFirestore=FirebaseFirestore.getInstance();





        signout = findViewById(R.id.txt_signout);

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                TeacherHome.this.finish();
                sp = getSharedPreferences("login", MODE_PRIVATE);
                sp.edit().putBoolean("logged", false).apply();
                Intent intent = new Intent(TeacherHome.this, LoginMode.class);
                startActivity(intent);

            }
        });





        recyclerView=findViewById(R.id.recycler_techer);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        list = new ArrayList<TeacherModel>();
        teachersAdapter=new TeacherAdapter(list,this);
        recyclerView.setAdapter(teachersAdapter);


        String email=getIntent().getStringExtra("email");



        firebaseFirestore.collection("StudentChat")
                .whereEqualTo("name",email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
                            for(QueryDocumentSnapshot documentSnapshot:task.getResult())
                            {
                                list.add(new TeacherModel(documentSnapshot.get("query").toString(),documentSnapshot.get("studid").toString()));


                            }
                            teachersAdapter.notifyDataSetChanged();

                        }
                        else
                        {
                            String error=task.getException().getMessage();
                            Toast.makeText(TeacherHome.this,error,Toast.LENGTH_SHORT).show();
                        }

                    }
                });





    }
}
