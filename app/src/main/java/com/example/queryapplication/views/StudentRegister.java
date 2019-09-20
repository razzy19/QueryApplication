package com.example.queryapplication.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.queryapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class StudentRegister extends AppCompatActivity {



    private ConstraintLayout layout;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private ProgressBar progressBar;
    StringBuilder UID;
    private EditText email,pass,name,contact,stream;
    private Button btn;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    SharedPreferences sp;
    String student_id;
    private TextView uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);

        student_id = FirebaseAuth.getInstance().getUid();

        progressBar=findViewById(R.id.progressBar);

        email=findViewById(R.id.editstudid);
        pass=findViewById(R.id.editstudpass);
        name=findViewById(R.id.editstudname);
        stream=findViewById(R.id.editstudstream);
        contact=findViewById(R.id.editstudcontact);




        btn=findViewById(R.id.btn_stud_register);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore =FirebaseFirestore.getInstance();


        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkinputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                checkinputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkinputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                checkmobilenumberandpass();
            }
        });




    }


    private void checkmobilenumberandpass() {


        if(email.getText().toString().matches(emailPattern)) {

                progressBar.setVisibility(View.VISIBLE);
                btn.setTextColor(Color.argb(50, 255, 255, 255));
                btn.setEnabled(false);
                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {


                                if (task.isSuccessful()) {
                                    Map<String,Object> userdata =new HashMap<>();
                                    userdata.put("email",email.getText().toString());
                                    userdata.put("pass",pass.getText().toString());
                                    userdata.put("name",name.getText().toString());
                                    userdata.put("contact",contact.getText().toString());
                                    userdata.put("stream",stream.getText().toString());


                                    firebaseFirestore.collection("Student")
                                            .add(userdata)
                                            .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {

                                                @Override
                                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                                    if(task.isSuccessful())
                                                    {
                                                        Intent intent = new Intent(StudentRegister.this, StudentSignIn.class);
                                                        startActivity(intent);
                                                        StudentRegister.this.finish();
                                                    }
                                                    else{

                                                        progressBar.setVisibility(View.INVISIBLE);
                                                        btn.setEnabled(true);
                                                        btn.setTextColor(Color.rgb(255, 255, 255));
                                                        String error = task.getException().getMessage();


                                                    }
                                                }
                                            });



                                } else {
                                    btn.setEnabled(false);
                                    btn.setTextColor(Color.argb(50, 255, 255, 255));
                                    progressBar.setVisibility(View.INVISIBLE);


                                    String error = task.getException().getMessage();


                                    Toast.makeText(getApplicationContext(),error, Toast.LENGTH_SHORT).show();


                                }
                            }
                        });


        } else
        {

            email.setError("Enter a Valid email address");
        }





    }

    private void checkinputs() {

        if (!TextUtils.isEmpty(email.getText())) {
            if (!TextUtils.isEmpty(name.getText())) {
                if (!TextUtils.isEmpty(pass.getText()) && pass.length() >= 8) {
                    btn.setEnabled(true);
                    btn.setTextColor(Color.rgb(255, 255, 255));
                }else{
                    btn.setEnabled(false);
                    btn.setTextColor(Color.argb(50, 255, 255, 255));
                }
            } else {
                btn.setEnabled(false);
                btn.setTextColor(Color.argb(50, 255, 255, 255));
            }
        } else {
            btn.setEnabled(false);
            btn.setTextColor(Color.argb(50, 255, 255, 255));
        }

    }



}



