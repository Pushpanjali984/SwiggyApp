package com.androtech.swiggyapp;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

public class RestaurantMenu extends AppCompatActivity {

    ImageView restImage;
    TextView restName,restCategory,rating1,time1;
    DatabaseReference db;
   GridLayoutManager layoutManager;
    StorageReference reference;
    RecyclerView recyclerView2;
    EditText searchItem;
    String resName,resCategory,ratingSet,timeSet,imageSet;
    ArrayList<String> nameList1=new ArrayList<String>();
    ArrayList<String> categoryList1=new ArrayList<String>();
    ArrayList<String> imageList1=new ArrayList<String>();
    ArrayList<String> priceList1=new ArrayList<String>();
    String itemName,itemCategory,itemPrice,itemImage;
ScrollView scrollView1;
    String name,key;
    RecyclerAdapter2 adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_menu);
        db= FirebaseDatabase.getInstance().getReference();
        reference= FirebaseStorage.getInstance().getReference();

        recyclerView2=findViewById(R.id.recyclerView2);
       name=getIntent().getStringExtra("namer");
       restImage=findViewById(R.id.restImage);
       restName=findViewById(R.id.restaurantName);
       restCategory=findViewById(R.id.restaurantCategory);
       rating1=findViewById(R.id.rating1);
       time1=findViewById(R.id.time1);
       searchItem=findViewById(R.id.searchItem);
       scrollView1=(ScrollView) findViewById(R.id.scrollView);
       layoutManager= new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView2.setLayoutManager(layoutManager);
        recyclerView2.setHasFixedSize(true);

        searchItem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchItem(s.toString());

            }
        });
      //  recyclerView2.setNestedScrollingEnabled(false);


       // Snackbar.make(scrollView1,"scroll view",Snackbar.LENGTH_INDEFINITE).show();

getData();
getRecyclerData();

    }

    public void  getData(){

        db.child("Restaurants").child(name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snap) {

                   resCategory=snap.child("category").getValue(String.class);
                      ratingSet=snap.child("ratings").getValue(String.class);
                     timeSet=snap.child("time").getValue(String.class);
                     imageSet=snap.child("image").getValue(String.class);


                restName.setText(name);
                restCategory.setText(resCategory);
                rating1.setText(ratingSet);
                time1.setText(timeSet);
                Glide.with(getApplicationContext()).load(imageSet).into(restImage);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }

    public void getRecyclerData(){


        db.child("Restaurants").child(name).child("menu").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               nameList1.clear();
                categoryList1.clear();
                priceList1.clear();
                imageList1.clear();

                for(DataSnapshot snap:dataSnapshot.getChildren()){
                     //key=snap.getKey().toString();
                    itemName=snap.child("itemname").getValue(String.class);
                    itemCategory=snap.child("itemcategory").getValue(String.class);
                    itemPrice=snap.child("itemprice").getValue(String.class);
                    itemImage=snap.child("itemimage").getValue(String.class);

                    nameList1.add(itemName);
                    categoryList1.add(itemCategory);
                    priceList1.add(itemPrice);
                    imageList1.add(itemImage);

                  //  Toast.makeText(getApplicationContext(),nameList1+""+categoryList1,Toast.LENGTH_SHORT).show();

                }
              adapter=new RecyclerAdapter2(nameList1,categoryList1,imageList1,priceList1,getApplicationContext());
                recyclerView2.setAdapter(adapter);
               // adapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });




    }


    public void searchItem(String str){

        ArrayList<String> restName=new ArrayList<>();
        for(String s:nameList1){
            if(s.toLowerCase().contains(str.toLowerCase())){

                restName.add(s);
            }


        }

        adapter.filterLst(restName);



    }


}
