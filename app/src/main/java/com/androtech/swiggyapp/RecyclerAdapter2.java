package com.androtech.swiggyapp;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerAdapter2 extends RecyclerView.Adapter<RecyclerAdapter2.ViewHolder> {
    ArrayList<String> nameList2=new ArrayList<String>();
    ArrayList<String> categoryList2=new ArrayList<String>();
    ArrayList<String> imageList2=new ArrayList<String>();
    ArrayList<String> priceList2=new ArrayList<String>();

    private Context mContext;
  int i=0,n1=0,n2=0;
 long ln;


    public RecyclerAdapter2(ArrayList<String> nameList2, ArrayList<String> categoryList2, ArrayList<String> imageList2, ArrayList<String> priceList2, Context mContext) {
        this.nameList2 = nameList2;
        this.categoryList2 = categoryList2;
        this.imageList2 = imageList2;
        this.priceList2 = priceList2;
        this.mContext = mContext;
    }

    @Override
    public RecyclerAdapter2.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.rowrecycler2,parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public long getItemId(int position) {
        ln=getItemId(position);
        return super.getItemId(position);

    }

    @Override
    public void onBindViewHolder(final RecyclerAdapter2.ViewHolder holder, final int position) {

        holder.itemName.setText(nameList2.get(position));
        holder.itemCategory.setText(categoryList2.get(position));
        holder.itemPrice.setText(priceList2.get(position));
        Glide.with(mContext).load(imageList2.get(position)).into(holder.itemImage);


        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               i++;
               // holder.itemCount.setText(""+i);

                n1=Integer.valueOf(priceList2.get(position));
                n2=n1*i;

                Snackbar.make(v, i+"items "+"|"+"  ₹"+n2,Snackbar.LENGTH_SHORT).show();
            }
        });



        }

    @Override
    public int getItemCount() {
        return nameList2.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView itemName,itemCategory,itemPrice,itemCount;
        ImageView itemImage;
        Button removeButton,addButton;
        public ViewHolder(View itemView) {
            super(itemView);
            itemName=itemView.findViewById(R.id.nameRecycler);
            itemCategory=itemView.findViewById(R.id.categoryRecycler);
            itemPrice=itemView.findViewById(R.id.recyclerPrice);
            itemImage=itemView.findViewById(R.id.imageRecycler);
            removeButton=itemView.findViewById(R.id.removeButton);
            addButton=itemView.findViewById(R.id.addButton);
            itemCount=itemView.findViewById(R.id.countItem);
           /* itemImage.setOnClickListener(this);
           removeButton.setOnClickListener(this);
            addButton.setOnClickListener(this);*/
        }

     /*   @Override
        public void onClick(View v) {

            if(v==addButton){
               i=i+1;
                itemCount.setText(i);
                n1=Integer.valueOf(priceList2.get(getAdapterPosition()));
                n2=n1*i;

                Snackbar.make(itemImage, i+"items "+"|"+"  ₹"+n2,Snackbar.LENGTH_SHORT).show();
                }

            if(v==removeButton){

                if(i==0)
                {
                    itemCount.setText("");
                }
                else {
                    // subb.setEnabled(true);
                   i=i-1;
                    itemCount.setText(String.valueOf(i));
                     n1=Integer.valueOf(priceList2.get(getAdapterPosition()));
                     n2=n1*i;
                    Snackbar.make(itemView,  i +"items " + "|" + "  ₹" +n2, Snackbar.LENGTH_SHORT).show();
                }
            }

}*/
    }

    public void filterLst(ArrayList<String> filterdNames1) {
        this.nameList2 = filterdNames1;
        notifyDataSetChanged();
    }

}
