package com.zoho.zkart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import static com.zoho.zkart.MainActivity.usernameglobal;

public class Homescreen extends AppCompatActivity {

    CardView c1,c2,c3,c4;
    ImageView cartpage,historypage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
        Toast.makeText(this, "Hello "+usernameglobal+" !", Toast.LENGTH_SHORT).show();
        c1=findViewById(R.id.mobilecard);
        c2=findViewById(R.id.lapcard);
        c3=findViewById(R.id.tabletcard);
        if(usernameglobal==null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
        c4=findViewById(R.id.cameracard);
        cartpage=findViewById(R.id.cart);
        historypage=findViewById(R.id.bought);
        historypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Homescreen.this,Bought.class));
            }
        });
        cartpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Homescreen.this,Cart.class));
            }
        });

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Homescreen.this,Category.class);
                intent.putExtra("Data","Mobile");
                startActivity(intent);
            }
        });
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Homescreen.this,Category.class);
                intent.putExtra("Data","Laptop");
                startActivity(intent);
            }
        });
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Homescreen.this,Category.class);
                intent.putExtra("Data","Tablet");
                startActivity(intent);
            }
        });
        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Homescreen.this,Category.class);
                intent.putExtra("Data","Camera");
                startActivity(intent);
            }
        });




    }
}