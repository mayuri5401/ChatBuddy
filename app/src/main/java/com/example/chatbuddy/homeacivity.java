package com.example.chatbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class homeacivity extends AppCompatActivity {
    FirebaseAuth auth;
    RecyclerView mainuserrecycle;
    useradpter adpter;
    FirebaseDatabase database;
    ArrayList<users> usersArrayList;
    ImageView imglogout, imgsetting;
    private boolean doubleBackToExitPressedOnce = false;


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        doubleBackToExitPressedOnce = true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.homeactivity);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        usersArrayList = new ArrayList<>();
        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(homeacivity.this, registartion.class));
        }

        Toast.makeText(this, "Data is Fetching...", Toast.LENGTH_SHORT).show();

        DatabaseReference reference = database.getReference().child("user");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    users users = dataSnapshot.getValue(com.example.chatbuddy.users.class);
                    usersArrayList.add(users);
                }
                adpter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        imglogout = findViewById(R.id.img_logout);
        mainuserrecycle = findViewById(R.id.main_user_recycler);
        mainuserrecycle.setLayoutManager(new LinearLayoutManager(this));
        adpter = new useradpter(homeacivity.this, usersArrayList);
        mainuserrecycle.setAdapter(adpter);
        imgsetting = findViewById(R.id.img_settings);
        imgsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(homeacivity.this, SettingActivity.class));
            }
        });

        imglogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(homeacivity.this);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                dialog.setContentView(R.layout.homeactivity);
                TextView yesBtn, noBtn;

                yesBtn = dialog.findViewById(R.id.yesbtn);
                noBtn = dialog.findViewById(R.id.nobtn);

                yesBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(homeacivity.this, registartion.class));
                    }
                });

                noBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });





    }

}