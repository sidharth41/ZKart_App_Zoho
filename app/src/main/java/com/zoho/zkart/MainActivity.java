package com.zoho.zkart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    TextInputEditText username,password;
    Boolean state=false;
    public static String usernameglobal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView registration=findViewById(R.id.register_page);
        username =findViewById(R.id.usernamevalue);
        password=findViewById(R.id.passwordvalue);

        Button home=findViewById(R.id.loginbutton);
        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Registration.class));
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username1=username.getText().toString();
                String password1=password.getText().toString();
                //Toast.makeText(MainActivity.this, password1, Toast.LENGTH_SHORT).show();
                if(userisadmin(username1,password1)){
                    usernameglobal="Admin";
                    startActivity(new Intent(MainActivity.this,Homescreen.class));
                }
                else {
                    password1=encrypt(password.getText().toString()).toLowerCase();
                    userexists(username1, password1);
                }



            }
        });



    }
    private boolean userisadmin(String name,String password){
        if(name.equals("admin@zoho.com")&&password.equals("admin")){
            //Toast.makeText(this, "Admin", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;

    }

    private void userexists(String name,final String password){
        DatabaseReference login= FirebaseDatabase.getInstance().getReference("Users");

        login.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(name)){
                   String passwordactual=snapshot.child(name).child("password").getValue().toString();

                     if(passwordactual.equals(password)){
                            usernameglobal=name;
                         startActivity(new Intent(MainActivity.this,Homescreen.class));
                     }
                     else{
                         Toast.makeText(MainActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                     }
                }
                else {
                 Toast.makeText(MainActivity.this, "User name doesn't Exist", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    private String encrypt(String password){

        return "123"+password+"@";
    }
}