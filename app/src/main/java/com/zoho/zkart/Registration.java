package com.zoho.zkart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {
    TextInputEditText mail,password,name,number;
    String mail1,password1,name1,number1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        TextView login=findViewById(R.id.Login_page);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Registration.this,MainActivity.class));
            }
        });
        Button register=findViewById(R.id.register);
        mail=findViewById(R.id.registermail);
        password=findViewById(R.id.registerpassword);
        name=findViewById(R.id.registername);
        number=findViewById(R.id.registernumber);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mail1=mail.getText().toString();

                password1=password.getText().toString();
                password1=encrypt(password1).toLowerCase();
                name1=name.getText().toString();
                number1=number.getText().toString();
                if(storedata(mail1,password1,name1,number1)){
                    Toast.makeText(Registration.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Registration.this,MainActivity.class));
                }
            }
        });


    }
    private Boolean storedata(String mail,String pass,String nam,String num){
        DatabaseReference reg= FirebaseDatabase.getInstance().getReference("Users").child(mail);
        reg.child("username").setValue(mail);
        reg.child("password").setValue(pass);
        reg.child("name").setValue(nam);
        reg.child("number").setValue(num);
        return true;

    }
    private String encrypt(String password){

        return "123"+password+"@";
    }
}