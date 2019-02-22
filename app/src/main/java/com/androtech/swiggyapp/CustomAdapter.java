package com.androtech.swiggyapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.data.DataHolder;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
ArrayList<String> nameList=new ArrayList<String>();
ArrayList<String> categoryList=new ArrayList<String>();
ArrayList<String> imageList=new ArrayList<String>();
ArrayList<String> ratingList=new ArrayList<String>();
ArrayList<String> timeList=new ArrayList<String>();
    private Context mContext;




    public CustomAdapter(ArrayList<String> nameList, ArrayList<String> categoryList,ArrayList<String> imageList,ArrayList<String> ratingList,ArrayList<String> timeList, Context mContext) {
        this.nameList = nameList;
        this.categoryList = categoryList;
       this.imageList=  imageList;
        this.mContext = mContext;
        this.ratingList=ratingList;
        this.timeList=timeList;


    }




    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(CustomAdapter.ViewHolder holder, int position) {
        holder.restaurantName.setText(nameList.get(position));
        holder.categ.setText(categoryList.get(position));
        holder.rat.setText(ratingList.get(position));
        holder.tim.setText(timeList.get(position));
       Glide.with(mContext).load(imageList.get(position)).into(holder.image);
/*holder.setItemListener(new ItemClickListener() {
    @Override
    public void onClick(View v, int position, boolean isLongClick) {

        if(isLongClick) {
                Toast.makeText(mContext, "long click" + nameList.get(position), Toast.LENGTH_SHORT).show();


        }else{ Toast.makeText(mContext,  nameList.get(position), Toast.LENGTH_SHORT).show();}
    }
});*/

    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
        TextView restaurantName,categ,rat,tim;
       // private ItemClickListener itemListener;
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);

            restaurantName=itemView.findViewById(R.id.tvName);
            categ=itemView.findViewById(R.id.tvCategory);
            image=itemView.findViewById(R.id.imageView);
            tim=itemView.findViewById(R.id.time);
            rat=itemView.findViewById(R.id.ratings);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);


        }


        @Override
        public void onClick(View v) {
//itemListener.onClick(v,getAdapterPosition(),false);

            Intent in =new Intent(mContext,RestaurantMenu.class);
            in.putExtra("namer",nameList.get(getAdapterPosition()));
            mContext.startActivity(in);
        }

        @Override
        public boolean onLongClick(View v) {
           // itemListener.onClick(v,getAdapterPosition(),true);
            return  true;
        }


        public void setItemListener(ItemClickListener itemListener){

           // this.itemListener=itemListener;
        }


        }


    public void filterList(ArrayList<String> filterdNames) {
        this.nameList = filterdNames;
        notifyDataSetChanged();
    }


    }









