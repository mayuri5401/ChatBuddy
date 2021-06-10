package com.example.chatbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.Loader;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginacitivity extends AppCompatActivity {
    TextView txtsignup;
    EditText loginemail, loginpass;
    TextView loginbtn;
    FirebaseAuth auth;
    ProgressDialog progressDialog;

    String expression = "^[\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginacitivity);


        auth = FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("please wait!");
        progressDialog.setCancelable(false);


        txtsignup = (TextView) findViewById(R.id.txtsignup);
        loginemail = (EditText) findViewById(R.id.loginemail);
        loginpass = (EditText) findViewById(R.id.loginpwd);
        loginbtn = findViewById(R.id.loginbtn);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                String email = loginemail.getText().toString();
                String pass = loginpass.getText().toString();

                if (TextUtils.isEmpty(email) | TextUtils.isEmpty(pass)) {
                    progressDialog.dismiss();

                    Toast.makeText(loginacitivity.this, "enter valid data", Toast.LENGTH_SHORT).show();
                } else if (email.matches(expression)) {
                    progressDialog.dismiss();

                    loginemail.setError("invalid email adress");
                    Toast.makeText(loginacitivity.this, "invalid emalil address", Toast.LENGTH_SHORT).show();
                } else if (pass.length() < 4) {


                    loginemail.setError("invalid Password");
                    Toast.makeText(loginacitivity.this, "please enter valid password", Toast.LENGTH_SHORT).show();
                } else {


                    auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                startActivity(new Intent(loginacitivity.this, homeacivity.class));
                            } else {
                                progressDialog.dismiss();

                                Toast.makeText(loginacitivity.this, "please check your username or password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }


            }
        });


        txtsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(loginacitivity.this, registartion.class));
            }
        });
    }
}