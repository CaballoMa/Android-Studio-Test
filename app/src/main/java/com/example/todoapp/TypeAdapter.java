package com.example.todoapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.ViewHolder>{

    private List<Type> mtypeList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView typeImage;
        TextView typeName;
        View typeView;
        public ViewHolder(View view) {
            super(view);
            typeView=view;
            typeImage = (ImageView) view.findViewById(R.id.type_image);
            typeName = (TextView) view.findViewById(R.id.task_info);
        }
    }

    public TypeAdapter(List<Type> typeList) {
        mtypeList = typeList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.type_layout, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.typeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Type type = mtypeList.get(position);
                if(type.getName()=="我的一天"){
                    Intent intent=new Intent();
                    intent.setClass(view.getContext(), myday_page.class);
                    startActivity(view.getContext(),intent,null);
                }
                else if(type.getName()=="重要"){
                    Intent intent=new Intent();
                    intent.setClass(view.getContext(), imp_page.class);
                    startActivity(view.getContext(),intent,null);
                }
                else if(type.getName()=="已计划日常"){
                    Intent intent=new Intent();
                    intent.setClass(view.getContext(), planned_page.class);
                    startActivity(view.getContext(),intent,null);
                }
                else if(type.getName()=="Tasks"){
                    Intent intent=new Intent();
                    intent.setClass(view.getContext(), task_page.class);
                    startActivity(view.getContext(),intent,null);
                }
                else {
                    Intent intent=new Intent();
                    intent.putExtra("diy",type.getName());
                    intent.setClass(view.getContext(),diy_page.class);
                    startActivity(view.getContext(),intent,null);
                }
            }
        });
        holder.typeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Type type = mtypeList.get(position);
                if(type.getName()=="我的一天"){
                    Intent intent=new Intent();
                    intent.setClass(view.getContext(), myday_page.class);
                    startActivity(view.getContext(),intent,null);
                }
                else if(type.getName()=="重要"){
                    Intent intent=new Intent();
                    intent.setClass(view.getContext(), imp_page.class);
                    startActivity(view.getContext(),intent,null);
                }
                else if(type.getName()=="已计划日常"){
                    Intent intent=new Intent();
                    intent.setClass(view.getContext(), planned_page.class);
                    startActivity(view.getContext(),intent,null);
                }
                else if(type.getName()=="Tasks"){
                    Intent intent=new Intent();
                    intent.setClass(view.getContext(), task_page.class);
                    startActivity(view.getContext(),intent,null);
                }
                else {
                    Intent intent=new Intent();
                    intent.putExtra("diy",type.getName());
                    intent.setClass(view.getContext(),diy_page.class);
                    startActivity(view.getContext(),intent,null);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Type type = mtypeList.get(position);
        holder.typeImage.setImageResource(type.getImageId());
        holder.typeName.setText(type.getName());
    }

    @Override
    public int getItemCount() {
        return mtypeList.size();
    }

}
