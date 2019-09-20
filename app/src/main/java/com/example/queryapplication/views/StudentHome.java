package com.example.queryapplication.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.queryapplication.R;
import com.example.queryapplication.controller.SpinnerAdapt;
import com.example.queryapplication.model.SpinnerModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentHome extends AppCompatActivity {
    TextView signout,txtcheckinbox;
    EditText studid,studquery;
   SpinnerAdapt spinnerAdapter;
    private ArrayList<SpinnerModel> spinnerModelArrayList;
    SharedPreferences sp;
    FirebaseFirestore firestore;
    ArrayAdapter<String> adapter;
    List<String> subjects;
    String name;
    Spinner spinner;
    String TAG="demo";
    String studentid,doc_id,studname;
    private Button btn;
    String emailtemp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        studid=findViewById(R.id.txt_studid);
        studquery=findViewById(R.id.txt_studquery);
        txtcheckinbox=findViewById(R.id.txt_inbox);

        btn=findViewById(R.id.btn_submit_student);
        firestore= FirebaseFirestore.getInstance();
         emailtemp=getIntent().getStringExtra("email");

        studentid = FirebaseAuth.getInstance().getUid();
        spinner = (Spinner) findViewById(R.id.spinnerstudent);


        name="Select Name";

        txtcheckinbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentHome.this, StudentAnswerList.class);
                intent.putExtra("email",emailtemp);
                startActivity(intent);
            }
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                name = (String) adapterView.getItemAtPosition(i);
                // Notify the selected item text
                Toast.makeText
                        (getApplicationContext(), "Selected : " + name, Toast.LENGTH_SHORT)
                        .show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });



        String emailtemp=getIntent().getStringExtra("email");




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> data = new HashMap<>();

                data.put("studid",studid.getText().toString());
                data.put("name",name);
                data.put("query",studquery.getText().toString());
                data.put("student_id",studentid);

                firestore.collection("StudentChat")
                        .add(data)
                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {

                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText
                                            (getApplicationContext(), "Query submitted successfully", Toast.LENGTH_SHORT)
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


        signout = findViewById(R.id.txt_signout);

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                StudentHome.this.finish();
                sp = getSharedPreferences("login", MODE_PRIVATE);
                sp.edit().putBoolean("logged", false).apply();
                Intent intent = new Intent(StudentHome.this, LoginMode.class);
                startActivity(intent);

            }
        });

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        CollectionReference subjectsRef = rootRef.collection("Teacher");
        subjects = new ArrayList<>();
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, subjects);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        subjectsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String subject = document.getString("email");
                        subjects.add(subject);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });


    }

}
