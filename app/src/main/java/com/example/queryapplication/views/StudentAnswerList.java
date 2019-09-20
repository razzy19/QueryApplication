package com.example.queryapplication.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.queryapplication.R;
import com.example.queryapplication.controller.StudentAdapter;
import com.example.queryapplication.controller.TeacherAdapter;
import com.example.queryapplication.model.TeacherModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class StudentAnswerList extends AppCompatActivity {
    private List<TeacherModel> list;
    private RecyclerView recyclerView;
    private FirebaseFirestore firebaseFirestore;
    private TextView add,txtid,txtid2;
    private StudentAdapter studentAdapter;
    Button signout;
    String emailtemp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_answer_list);

        firebaseFirestore=FirebaseFirestore.getInstance();





        signout = findViewById(R.id.txt_signout);

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                StudentAnswerList.this.finish();
                Intent intent = new Intent(StudentAnswerList.this, LoginMode.class);
                startActivity(intent);

            }
        });





        recyclerView=findViewById(R.id.answer_recycle);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        list = new ArrayList<TeacherModel>();
        studentAdapter=new StudentAdapter(list,this);
        recyclerView.setAdapter(studentAdapter);

        emailtemp=getIntent().getStringExtra("email");



        firebaseFirestore.collection("TeacherChat")
                .whereEqualTo("name",emailtemp)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
                            for(QueryDocumentSnapshot documentSnapshot:task.getResult())
                            {
                                list.add(new TeacherModel(documentSnapshot.get("query").toString(),documentSnapshot.get("reply").toString()));


                            }
                            studentAdapter.notifyDataSetChanged();

                        }
                        else
                        {
                            String error=task.getException().getMessage();
                            Toast.makeText(StudentAnswerList.this,error,Toast.LENGTH_SHORT).show();
                        }

                    }
                });



    }
}
