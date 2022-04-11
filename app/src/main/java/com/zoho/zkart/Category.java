package com.zoho.zkart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.zoho.zkart.MainActivity.usernameglobal;
import static java.security.AccessController.getContext;

public class Category extends AppCompatActivity {
    private RecyclerView meRecyclerView;
    DatabaseReference databaseReference;
    String category;
    public Category(){

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
         category=getIntent().getStringExtra("Data");
        if(usernameglobal==null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
        FloatingActionButton additem=findViewById(R.id.fab);
        if(usernameglobal.equals("Admin")){
            additem.setVisibility(View.VISIBLE);
        }
        additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Category.this,Additem.class));
            }
        });
        TextView tbar=findViewById(R.id.categorytext);
        tbar.setText(category);
        meRecyclerView=findViewById(R.id.rvforitems);
        meRecyclerView.setLayoutManager(new LinearLayoutManager(Category.this));
        databaseReference= FirebaseDatabase.getInstance().getReference("Stocks").child(category);
        database();




    }
    public void  database(){ FirebaseRecyclerOptions<modelforcategory> options=new FirebaseRecyclerOptions.Builder<modelforcategory>()
            .setQuery(databaseReference,modelforcategory.class).build();
        FirebaseRecyclerAdapter<modelforcategory,CategoryVh> adapter=new FirebaseRecyclerAdapter<modelforcategory, CategoryVh>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CategoryVh CategoryVh, int i, @NonNull modelforcategory modeforcat) {
                CategoryVh.setDetails(getApplication(),modeforcat.getBrand(), modeforcat.getModel(),modeforcat.getPrice(),modeforcat.getStock());



            }

            @NonNull
            @Override
            public CategoryVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.stockrow,parent,false);

                CategoryVh cvh= new CategoryVh(view);
                return cvh;
            }
        };
        meRecyclerView.setAdapter(adapter);
        adapter.startListening();










    }

    public  class CategoryVh extends RecyclerView.ViewHolder{

        TextView brandtext,modeltext,pricetext,stocksavailable;
        TextInputEditText count;
        Button addtocart;
        Button addstocks;
        CardView cardView;
        LinearLayout user,admin;



        public CategoryVh(@NonNull View itemView) {
            super(itemView);

            user=itemView.findViewById(R.id.userLayout);
            admin=itemView.findViewById(R.id.adminLayout);

            cardView =itemView.findViewById(R.id.cardvw);

            brandtext=itemView.findViewById(R.id.Brand);
            modeltext=itemView.findViewById(R.id.Model);
            pricetext=itemView.findViewById(R.id.Price);
            count=itemView.findViewById(R.id.count);
            addtocart=itemView.findViewById(R.id.addtocart);
            stocksavailable=itemView.findViewById(R.id.stocksavailable);
            addstocks=itemView.findViewById(R.id.Increase_Stock);








        }
        public void setDetails(final Context ctx, final String brand, final String model, final String price, final String stock)  {

            brandtext.setText(brand.toUpperCase());
            modeltext.setText(model);
            pricetext.setText(price);
            stocksavailable.setText("Stocks available "+stock);

            if(usernameglobal.equals("Admin")){
                user.setVisibility(View.GONE);
                admin.setVisibility(View.VISIBLE);
                if(Integer.parseInt(stock)<10){
                    cardView.setCardBackgroundColor(Color.RED);
                }
            }
            else{
                admin.setVisibility(View.GONE);
                user.setVisibility(View.VISIBLE);
            }
            addstocks.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    increasestock(category,brand,model);
                }
            });

            addtocart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String quantity=count.getText().toString();
                    int available=Integer.parseInt(stock);
                    int need=Integer.parseInt(quantity);
                    if(need<available) {

                        DatabaseReference addtocartref = FirebaseDatabase.getInstance().getReference("Users").child(usernameglobal).child("cart").child(model);
                        addtocartref.child("model").setValue(model);
                        addtocartref.child("brand").setValue(brand);
                        addtocartref.child("price").setValue(price);
                        addtocartref.child("quantity").setValue(quantity);
                        addtocartref.child("category").setValue(category);
                        Toast.makeText(ctx, "Added to cart", Toast.LENGTH_SHORT).show();
                        count.setText("");

                    }
                    else{
                    Toast.makeText(ctx, "Insufficient stock!", Toast.LENGTH_SHORT).show();}






                }
            });

        }

    }
    private void increasestock(String category,String brand,String model){
        DatabaseReference increasestockref=FirebaseDatabase.getInstance().getReference("Stocks").child(category).child(model);
        increasestockref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int actualstock=Integer.parseInt(snapshot.child("stock").getValue().toString());
                increasestockref.child("stock").setValue(String.valueOf(actualstock+10));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}