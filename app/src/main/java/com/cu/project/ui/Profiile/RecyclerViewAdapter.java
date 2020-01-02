package com.cu.project.ui.Profiile;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cu.project.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {


    Context mcontext;
    List<Achievements> mData;


    public RecyclerViewAdapter(Context mcontext, List<Achievements> mData) {
        this.mcontext = mcontext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        v = LayoutInflater.from(mcontext).inflate(R.layout.row, parent , false);
        final MyViewHolder myViewHolder = new MyViewHolder(v);

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mcontext , "Clicked! " + String.valueOf(myViewHolder.getAdapterPosition()) , Toast.LENGTH_LONG).show();
            }
        });




        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.main.setText(mData.get(position).getMainTitle());
        holder.auth.setText(mData.get(position).getAuth_name());
        holder.date.setText(mData.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView main,auth,date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            main = itemView.findViewById(R.id.maintextview_id);
            auth = itemView.findViewById(R.id.authortextview_id);
            date = itemView.findViewById(R.id.datetextview_id);


        }
    }

}
