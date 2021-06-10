package com.example.chatbuddy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class registartion extends AppCompatActivity {
    TextView loginacti;
    EditText name, mail, pass, cnfrmpass;
    TextView btnsignup;
    CircleImageView profileimg;
    Uri imageuri;
    FirebaseDatabase database;
    FirebaseStorage storage;
    String expression = "^[\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
    FirebaseAuth mAuth;
    String imageURI;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registartion);


        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("please wait!");
        progressDialog.setCancelable(false);


        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();


        profileimg = findViewById(R.id.imgsingup);
        loginacti = findViewById(R.id.txtsignup);
        name = (EditText) findViewById(R.id.name);
        mail = (EditText) findViewById(R.id.mail);
        pass = (EditText) findViewById(R.id.password);
        cnfrmpass = (EditText) findViewById(R.id.confrmpass);
        btnsignup = findViewById(R.id.signupbtn);
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();

                String regname = name.getText().toString();
                String email = mail.getText().toString();
                String regpass = pass.getText().toString();
                String cnfpass = cnfrmpass.getText().toString();
                String status="Hey there i'm using chat buddy";


                if (TextUtils.isEmpty(regname) || TextUtils.isEmpty(regpass) || TextUtils.isEmpty(cnfpass)) {

                    progressDialog.dismiss();
                    Toast.makeText(registartion.this, "please enter valid Data", Toast.LENGTH_SHORT).show();
                } else if (email.matches(expression)) {
                    progressDialog.dismiss();
                    mail.setError("invalid email adress");
                    Toast.makeText(registartion.this, "invalid emalil address", Toast.LENGTH_SHORT).show();
                } else if (!regpass.equals(cnfpass)) {
                    progressDialog.dismiss();
                    Toast.makeText(registartion.this, "password does not match", Toast.LENGTH_SHORT).show();
                }
                else if (regpass.length() < 4) {


                    Toast.makeText(registartion.this, "please enter valid password", Toast.LENGTH_SHORT).show();}
                else {
                    mAuth.createUserWithEmailAndPassword(email, regpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {


                                DatabaseReference reference = database.getReference().child("user").child(mAuth.getUid());
                                StorageReference storageReference = storage.getReference().child("upload").child(mAuth.getUid());

                                if (imageuri != null) {
                                    storageReference.putFile(imageuri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    imageURI = uri.toString();
                                                    users users = new users(mAuth.getUid(), regname, email, imageURI,status);
                                                    reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                progressDialog.dismiss();
                                                                startActivity(new Intent(registartion.this, homeacivity.class));

                                                            } else {
                                                                Toast.makeText(registartion.this, "error in creating a new users", Toast.LENGTH_SHORT).show();
                                                            }

                                                        }
                                                    });

                                                }
                                            });
                                        }
                                    });
                                } else {
                                    String status="Hey there i'm using chat buddy";

                                    imageURI = " https://firebasestorage.googleapis.com/v0/b/chatbuddy-ee514.appspot.com/o/prof.png?alt=media&token=3bde11ed-f8ac-49b4-a740-2a3f9b049390";
                                    users users = new users(mAuth.getUid(), regname, email, imageURI,status);
                                    reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {

                                                startActivity(new Intent(registartion.this, homeacivity.class));

                                            } else {
                                                Toast.makeText(registartion.this, "error in creating a new users", Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });
                                }

                            }
                            else
                            {
                                progressDialog.dismiss();
                                Toast.makeText(registartion.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }


            }
        });
        profileimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Tack Image"), 10);
            }
        });


        loginacti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(registartion.this, loginacitivity.class));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            if (data != null) {
                imageuri = data.getData();
                profileimg.setImageURI(imageuri);
            }
        }
    }
}