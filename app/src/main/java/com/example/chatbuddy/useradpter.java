package com.example.chatbuddy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class useradpter extends RecyclerView.Adapter<useradpter.Viewholder>{
    Context homeacivity;
    ArrayList<users> usersArrayList;

    public useradpter(homeacivity homeacivity, ArrayList<users> usersArrayList) {
        this.homeacivity=homeacivity;
        this.usersArrayList=usersArrayList;
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(homeacivity).inflate(R.layout.item_user_row,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        users users =usersArrayList.get(position);
        holder.username.setText(users.name);
        holder.userstatus.setText(users.status);

        Picasso.get().load(users.imageUri).into(holder.userimage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(homeacivity,ChatActivity.class);
                intent.putExtra("name",users.getName());
                intent.putExtra("recevierimg",users.getImageUri());
                intent.putExtra("uid",users.getUid());

                homeacivity.startActivity(intent);
            }
        });{

        }


    }



    @Override
    public int getItemCount() {
        return usersArrayList.size();
    }
    class  Viewholder extends  RecyclerView.ViewHolder{


        CircleImageView userimage;
        TextView username;
        TextView userstatus;
        public Viewholder(@NonNull View itemView) {
            super(itemView);

            userimage=itemView.findViewById(R.id.userimg);
            username=itemView.findViewById(R.id.username);
            userstatus=itemView.findViewById(R.id.userstatus);
        }
    }
}
