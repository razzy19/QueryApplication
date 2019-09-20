package com.example.queryapplication.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.queryapplication.R;


import androidx.appcompat.app.AppCompatActivity;

public class LoginMode extends AppCompatActivity {

    TextView txtstudent,txtteacher;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_mode);

      //  sp = getSharedPreferences("login",MODE_PRIVATE);
     //   if(sp.getBoolean("logged",false)){
     //       LoadHome();
    //    }

        sp = getSharedPreferences("login",MODE_PRIVATE);
        if(sp.getBoolean("logged",false)){
         //   Intent intent = new Intent(LoginMode.this, HomePage.class);
           // startActivity(intent);
        }

        txtstudent=findViewById(R.id.txt_student_signin);
        txtteacher=findViewById(R.id.txt_teacher_signin);



        txtstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Intent intent = new Intent(LoginMode.this, StudentSignIn.class);
               startActivity(intent);

            }
        });

        txtteacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginMode.this, TeacherSignIn.class);
                startActivity(intent);

            }
        });



    }
    public void LoadHome()
    {
        Intent intent = new Intent(LoginMode.this, StudentHome.class);
        startActivity(intent);
    }
}
