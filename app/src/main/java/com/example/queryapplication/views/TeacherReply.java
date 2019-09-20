package com.example.queryapplication.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.queryapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class TeacherReply extends AppCompatActivity {
    TextView nametxt,querytxt;
    EditText answer;
    Button btnreply,signout;

    FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_reply);

        nametxt=findViewById(R.id.txt_name);
        querytxt=findViewById(R.id.txt_query);
        btnreply=findViewById(R.id.btn_reply);
        answer=findViewById(R.id.txt_reply);

        String email=getIntent().getStringExtra("name");
        String query=getIntent().getStringExtra("query");

        nametxt.setText(email);
        querytxt.setText(query);

        firestore= FirebaseFirestore.getInstance();


        signout = findViewById(R.id.txt_signout);

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                TeacherReply.this.finish();

                Intent intent = new Intent(TeacherReply.this, LoginMode.class);
                startActivity(intent);

            }
        });


        btnreply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String, Object> data = new HashMap<>();

                data.put("name",nametxt.getText().toString());
                data.put("reply",answer.getText().toString());
                data.put("query",querytxt.getText().toString());
                firestore.collection("TeacherChat")
                        .add(data)
                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {

                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText
                                            (getApplicationContext(), "Message sent", Toast.LENGTH_SHORT)
                                            .show();

                                }
                                else{

                                    Toast.makeText
                                            (getApplicationContext(), "Some error occured", Toast.LENGTH_SHORT)
                                            .show();


                                }
                            }
                        });




            }
        });





    }
}
