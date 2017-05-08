package com.jiithelp;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.lang.reflect.Field;
import java.util.ArrayList;


/**
 * Created by yash on 8/4/17.
 */

public class JIITListAdapter extends RecyclerView.Adapter<JIITListAdapter.IssueBookViewHolder>{

    private ArrayList<JIITListModel> dataSet;
    Context context;


    public JIITListAdapter(ArrayList<JIITListModel> data, Context context) {
        this.dataSet = data;
        this.context = context;

    }

    @Override
    public JIITListAdapter.IssueBookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.jiit_list_layout,parent,false);
        IssueBookViewHolder viewHolder=new IssueBookViewHolder(v);
        return viewHolder;
    }



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(JIITListAdapter.IssueBookViewHolder holder, int position) {

        final JIITListModel jiitListModel = dataSet.get(position);

        String name = jiitListModel.getImage().toString();
        int id = context.getResources().getIdentifier(name, "drawable", context.getPackageName());
        Drawable drawable = context.getResources().getDrawable(id);

        holder.title.setText(jiitListModel.getTitle().toString());
        holder.img.setBackground(drawable);

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Next.class);
                intent.putExtra("title", jiitListModel.getTitle().toString());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class IssueBookViewHolder extends RecyclerView.ViewHolder{

        protected TextView title;
        protected ImageView img;

        public IssueBookViewHolder(View itemView) {
            super(itemView);
            title= (TextView) itemView.findViewById(R.id.title);
            img= (ImageView) itemView.findViewById(R.id.img);
        }
    }
}
