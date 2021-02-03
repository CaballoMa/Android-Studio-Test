package com.example.todoapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

public class taskAdapter extends RecyclerView.Adapter<taskAdapter.ViewHolder>{

    private List<Task> mtaskList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView task;
        View taskView;
        public ViewHolder(View view) {
            super(view);
            taskView=view;
            task = (TextView) view.findViewById(R.id.task_info);
        }
    }

    public taskAdapter(List<Task> taskList) {
        mtaskList = taskList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        final ViewHolder holder = new ViewHolder(view);


        final Button imp_button = view.findViewById(R.id.imp);
        final Button dele_button = view.findViewById(R.id.dele);

        dele_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Task task = mtaskList.get(position);
                String text=task.getText();
                MyDatabaseHelper dbHelper= new MyDatabaseHelper(view.getContext(),"TaskStore.db",null,1);;
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("TaskData","Task=?",new String[] {text});
                db.close();
                notifyItemRemoved(position);
                mtaskList.remove(task);
            }
        });

        imp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper dbHelper= new MyDatabaseHelper(view.getContext(),"TaskStore.db",null,1);;
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                int position = holder.getAdapterPosition();
                Task task = mtaskList.get(position);

                Cursor cursor = db.query("TaskData", new String[]{"myDay, Important, Dated, Task"}, "Task=?", new String[]{task.getText()}, null, null, null);
                cursor.moveToFirst();
                Boolean imp = cursor.getInt(cursor.getColumnIndex("Important"))>0;
                ContentValues values = new ContentValues();

                if(!imp){
                imp_button.setBackgroundColor(view.getContext().getResources().getColor(R.color.purple_200));
                values.put("Important",Boolean.TRUE);
                }
                else {
                    values.put("Important",Boolean.FALSE);
                    imp_button.setBackgroundColor(view.getContext().getResources().getColor(R.color.teal_200));
                }
                String text=task.getText();
                db.update("TaskData",values,"Task=?",new String[] {text});
                db.close();
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Task task = mtaskList.get(position);
        holder.task.setText(task.getText());
    }

    @Override
    public int getItemCount() {
        return mtaskList.size();
    }

}
