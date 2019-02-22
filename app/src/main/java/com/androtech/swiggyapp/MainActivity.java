package com.androtech.swiggyapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
TextView currentAddress;
LocationManager manager;
LocationListener listener;
RecyclerView recyclerView;
LinearLayoutManager mgr;
DatabaseReference db;
StorageReference reference;
EditText searchData;
    ArrayList<String> nameList1=new ArrayList<String>();
    ArrayList<String> categoryList1=new ArrayList<String>();
    ArrayList<String> imageList1=new ArrayList<String>();
    ArrayList<String> ratingList1=new ArrayList<String>();
    ArrayList<String> timeList1=new ArrayList<String>();
    CustomAdapter cus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentAddress=findViewById(R.id.currentAddress);
        manager= (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        recyclerView=findViewById(R.id.recyclerView);
        db=FirebaseDatabase.getInstance().getReference();
        reference= FirebaseStorage.getInstance().getReference();

        searchData=findViewById(R.id.searchData);
        LinearLayoutManager layoutManager = new LinearLayoutManager( this, LinearLayoutManager.VERTICAL, false );
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        searchData.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                filter(s.toString());

            }
        });

        getData();


        }


    @Override
    protected void onStart() {
        super.onStart();
        getCurrentAddress();

    }

    public void getCurrentAddress(){

   listener=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double latitude=location.getLatitude();
                double longitude=location.getLongitude();
                Geocoder geocoder=new Geocoder(getApplicationContext());
                StringBuilder address=new StringBuilder();

                if(geocoder.isPresent()){

                  //  Toast.makeText(getApplicationContext(),"getting street address",Toast.LENGTH_SHORT).show();

                    try{

                        List<Address> streetaddress=geocoder.getFromLocation(latitude,longitude,10);
                        Address firstaddress=streetaddress.get(0);
                        String locality = firstaddress.getLocality();
                        String sublocality = firstaddress.getSubLocality();
                        address.append(locality+","+sublocality);
                        currentAddress.setText(address.toString());
                        //manager.removeUpdates(listener);

                    }
                    catch (IOException e){
                        e.printStackTrace();

                        Toast.makeText(getApplicationContext(),"failed to get address",Toast.LENGTH_SHORT).show();
                    }

                    }else{
                    Toast.makeText(getApplicationContext(), "GEO CODER NOT AVLBL", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "CANT GET STREET ADDRESS..TRY AGAIN", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Toast.makeText(getApplicationContext(), provider + " IS OFF..TRY AGAIN", Toast.LENGTH_SHORT).show();

            }
        };






        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                Toast.makeText(getApplicationContext(), "PERMISSION NOT GRANTED", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(getApplicationContext(), "PERMISSION GRANTED", Toast.LENGTH_SHORT).show();
            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    10000, 100, listener);



        }

        public void  getData(){

db.child("Restaurants").addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

        nameList1.clear();
        categoryList1.clear();
        ratingList1.clear();
        timeList1.clear();
        imageList1.clear();

        for(DataSnapshot snap:dataSnapshot.getChildren()){
         String resName=snap.getKey();
         String resCategory=snap.child("category").getValue(String.class);
         String ratings=snap.child("ratings").getValue(String.class);
         String time=snap.child("time").getValue(String.class);
         String imageGet=snap.child("image").getValue(String.class);
         nameList1.add(resName);
         categoryList1.add(resCategory);
         ratingList1.add(ratings);
         timeList1.add(time);
         imageList1.add(imageGet);

//Toast.makeText(getApplicationContext(),nameList1+""+categoryList1,Toast.LENGTH_SHORT).show();

        }
        cus=new CustomAdapter(nameList1,categoryList1,imageList1,ratingList1,timeList1,getApplicationContext());
        recyclerView.setAdapter(cus);
        cus.notifyDataSetChanged();


    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});

    }

    public void filter(String str){
        ArrayList<String> restaurantName=new ArrayList<>();
        for(String s:nameList1){
            if(s.toLowerCase().contains(str.toLowerCase())){
                restaurantName.add(s);
            }
        }

    cus.filterList(restaurantName);
    }
}
