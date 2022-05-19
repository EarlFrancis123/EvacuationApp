package com.evacuationapp.finalevacuationapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.evacuationapp.finalevacuationapp.Model.model2;
import com.evacuationapp.finalevacuationapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


public class myadapter2 extends FirebaseRecyclerAdapter<model2, myadapter2.myviewholder>
{
    public myadapter2(@NonNull FirebaseRecyclerOptions<model2> options)
    {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, final int position, @NonNull final model2 model) {
        holder.evacuationName.setText("Name: "+model.getAddReliefGoodsEvacuationName());
        holder.Sponsor.setText("Contact Number: "+model.getAddReliefGoodsSponsor());
        holder.Date.setText("Address: "+model.getAddReliefGoodsDate());
        holder.Food.setText("Gender: "+model.getAddReliefGoodsFood());
        holder.Water.setText("Evacuation: "+model.getAddReliefGoodsWater());

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new myviewholder(view);
    }


    class myviewholder extends RecyclerView.ViewHolder
    {
        TextView evacuationName,Sponsor,Date,Food, Water;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);

            evacuationName=(TextView)itemView.findViewById(R.id.nameTV);
            Sponsor=(TextView)itemView.findViewById(R.id.contactTV);
            Date=(TextView)itemView.findViewById(R.id.addressTV);
            Food=(TextView)itemView.findViewById(R.id.genderTV);
            Water=(TextView)itemView.findViewById(R.id.evacuationTV);


        }
    }
}
