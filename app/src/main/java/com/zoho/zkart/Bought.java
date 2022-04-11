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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.zoho.zkart.MainActivity.usernameglobal;

public class Bought extends AppCompatActivity {
    private RecyclerView boughtRecyclerView;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bought);
        if(usernameglobal==null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
        boughtRecyclerView=findViewById(R.id.rvforbought);
        boughtRecyclerView.setLayoutManager(new LinearLayoutManager(Bought.this));
        databaseReference= FirebaseDatabase.getInstance().getReference("Users").child(usernameglobal).child("Bought");
        database();
    }
    public void  database(){ FirebaseRecyclerOptions<modelforbought> options=new FirebaseRecyclerOptions.Builder<modelforbought>()
            .setQuery(databaseReference,modelforbought.class).build();
        FirebaseRecyclerAdapter<modelforbought, Boughtvh> adapter=new FirebaseRecyclerAdapter<modelforbought, Boughtvh>(options) {
            @Override
            protected void onBindViewHolder(@NonNull Boughtvh bvh, int i, @NonNull modelforbought modeforbought) {
                bvh.setDetails(getApplication(),modeforbought.getBrand(), modeforbought.getModel(),modeforbought.getTotal(),modeforbought.getQuantity(),modeforbought.getCategory(),modeforbought.getTime(),modeforbought.getDate(),modeforbought.getInvoice());



            }

            @NonNull
            @Override
            public Boughtvh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.boughtrow,parent,false);

                Boughtvh bviewholder= new Boughtvh(view);
                return bviewholder;
            }
        };
        boughtRecyclerView.setAdapter(adapter);
        adapter.startListening();










    }

    public  class Boughtvh extends RecyclerView.ViewHolder{

        TextView brandtext,modeltext,pricetext,quantity,dateview,timeview,categoryview,idview;






        public Boughtvh(@NonNull View itemView) {
            super(itemView);



            brandtext=itemView.findViewById(R.id.Brand);
            modeltext=itemView.findViewById(R.id.Model);
            pricetext=itemView.findViewById(R.id.Price);
            quantity=itemView.findViewById(R.id.Quantitybought);
            dateview=itemView.findViewById(R.id.Date);
            timeview=itemView.findViewById(R.id.Time);
            categoryview=itemView.findViewById(R.id.categorytextview);
            idview=itemView.findViewById(R.id.Invoice);






        }
        public void setDetails(final Context ctx, final String brand, final String model, final String price, final String stock, final String category,final String time,final String date,final String id)  {

            brandtext.setText(brand);
            modeltext.setText(model);
            pricetext.setText("Rs "+price);
            quantity.setText("Quantity: "+stock);
            dateview.setText(date);
            timeview.setText(time);
            categoryview.setText(category);

            idview.setText(id);





        }

    }
}