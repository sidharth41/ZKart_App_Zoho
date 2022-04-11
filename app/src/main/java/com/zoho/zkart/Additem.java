package com.zoho.zkart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.zoho.zkart.MainActivity.usernameglobal;

public class Additem extends AppCompatActivity {
    TextInputEditText brand,model,price,stock;
    AutoCompleteTextView category;
    String[] Categoryarray = new String[] {"Mobile","Laptop","Tablet","Camera"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);
        brand=findViewById(R.id.addbrand);
        if(usernameglobal==null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
        model=findViewById(R.id.addmodel);
        price=findViewById(R.id.addprice);
        stock=findViewById(R.id.addstock);
        category=findViewById(R.id.addcategory);

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        getApplicationContext(),
                        R.layout.dropdown_item,
                        Categoryarray);
        category.setAdapter(adapter);

        Button additem=findViewById(R.id.additembutton);
        additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String brandname=brand.getText().toString();
                String modelname=model.getText().toString();
                String pricename=price.getText().toString();
                String categoryname=category.getText().toString();
                String stockvalue=stock.getText().toString();
                DatabaseReference addtodbitems= FirebaseDatabase.getInstance().getReference("Stocks").child(categoryname).child(modelname);
                addtodbitems.child("brand").setValue(brandname);
                addtodbitems.child("model").setValue(modelname);
                addtodbitems.child("price").setValue(pricename);
                addtodbitems.child("stock").setValue(stockvalue);
                Toast.makeText(Additem.this, "Items added to database successfully!", Toast.LENGTH_SHORT).show();


            }
        });
    }
}