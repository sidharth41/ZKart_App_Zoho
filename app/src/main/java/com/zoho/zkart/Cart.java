package com.zoho.zkart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.zoho.zkart.MainActivity.usernameglobal;

public class Cart extends AppCompatActivity {
    private RecyclerView cartRecyclerView;
    DatabaseReference databaseReference;
    public static int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        if(usernameglobal==null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
        cartRecyclerView=findViewById(R.id.rvforcart);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(Cart.this));
        databaseReference= FirebaseDatabase.getInstance().getReference("Users").child(usernameglobal).child("cart");
        database();

    }
    public void  database(){ FirebaseRecyclerOptions<modelforcart> options=new FirebaseRecyclerOptions.Builder<modelforcart>()
            .setQuery(databaseReference,modelforcart.class).build();
        FirebaseRecyclerAdapter<modelforcart,Cartvh> adapter=new FirebaseRecyclerAdapter<modelforcart, Cartvh>(options) {
            @Override
            protected void onBindViewHolder(@NonNull Cartvh cartvh, int i, @NonNull modelforcart modeforcat) {
                cartvh.setDetails(getApplication(),modeforcat.getBrand(), modeforcat.getModel(),modeforcat.getPrice(),modeforcat.getQuantity(),modeforcat.getCategory());



            }

            @NonNull
            @Override
            public Cartvh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cartrow,parent,false);

                Cartvh cartviewholder= new Cartvh(view);
                return cartviewholder;
            }
        };
        cartRecyclerView.setAdapter(adapter);
        adapter.startListening();










    }

    public  class Cartvh extends RecyclerView.ViewHolder{

        TextView brandtext,modeltext,pricetext,quantity;
        Button buy;





        public Cartvh(@NonNull View itemView) {
            super(itemView);



            brandtext=itemView.findViewById(R.id.Brand);
            modeltext=itemView.findViewById(R.id.Model);
            pricetext=itemView.findViewById(R.id.Price);
            quantity=itemView.findViewById(R.id.Quantitytext);
            buy=itemView.findViewById(R.id.buy);





        }
        public void setDetails(final Context ctx, final String brand, final String model, final String price, final String stock,final String category)  {

            brandtext.setText(brand.toUpperCase());
            modeltext.setText(model);
            pricetext.setText(price);
            quantity.setText("Quantity "+stock);



            buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addtobought(brand,model,price,stock,category);
                    reducefromstock(stock,model,category);
                }
            });
        }

    }
    private void addtobought(String brand,String model,String price,String stock,String category){

        DatabaseReference buyref = FirebaseDatabase.getInstance().getReference("Users").child(usernameglobal).child("Bought").child(model);
        int pricevalue=Integer.parseInt(price)*Integer.parseInt(stock);

        DatabaseReference invoiceid=FirebaseDatabase.getInstance().getReference("Invoice");
        invoiceid.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                id =Integer.parseInt(snapshot.child("id").getValue().toString());
                invoiceid.child("id").setValue(id +1);
                buyref.child("invoice").setValue(String.valueOf(id+1));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        buyref.child("model").setValue(model);
        buyref.child("brand").setValue(brand);
        buyref.child("Total").setValue(String.valueOf(pricevalue));
        buyref.child("quantity").setValue(stock);
        buyref.child("category").setValue(category);
        buyref.child("Time").setValue(currentTime);
        buyref.child("Date").setValue(currentDate);

        Toast.makeText(this, "Items at cart bought successfully!", Toast.LENGTH_SHORT).show();
        DatabaseReference removefromcart=FirebaseDatabase.getInstance().getReference("Users").child(usernameglobal).child("cart");
        removefromcart.child(model).removeValue();

    }






    private void reducefromstock(String stock,String model,String category){
        int stockvalue=Integer.parseInt(stock);

        DatabaseReference reduceref=FirebaseDatabase.getInstance().getReference("Stocks").child(category).child(model);
        reduceref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int actual=Integer.parseInt(snapshot.child("stock").getValue().toString());
                reduceref.child("stock").setValue(String.valueOf(actual-stockvalue));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}