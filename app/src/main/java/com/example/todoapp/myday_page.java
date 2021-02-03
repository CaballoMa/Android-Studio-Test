package com.example.todoapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class myday_page extends AppCompatActivity {

    private MyDatabaseHelper dbHelper= new MyDatabaseHelper(this,"TaskStore.db",null,1);;
    private   List<Task> TaskList = new ArrayList<Task>();
    int LAUNCH_SECOND_ACTIVITY = 1;
    taskAdapter adapter;
    RecyclerView recyclerView;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mydaypage);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query("TaskData", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String tasks = cursor.getString(cursor.getColumnIndex("Task"));
                Task task = new Task(tasks);
                Boolean myDay=cursor.getInt(cursor.getColumnIndex("myDay"))>0;
                Boolean imp = cursor.getInt(cursor.getColumnIndex("Important"))>0;
                if (!TaskList.contains(task)&&myDay) {
                    TaskList.add(task);
//                    Button important = (Button) findViewById(R.id.imp);
//                    if(imp) {
//                        important.setBackgroundColor(R.color.purple_200);
//                    }
                }
            } while (cursor.moveToNext());
        }
        cursor.close();

        db.close();

        recyclerView= (RecyclerView) findViewById(R.id.recycler_view);;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new taskAdapter(TaskList);
        recyclerView.setAdapter(adapter);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }

        Button back_button=(Button) findViewById(R.id.title_back);
        Button edit_button=(Button) findViewById(R.id.title_edit);
//        Button dele_button=(Button) findViewById(R.id.dele);
//        Button imp_button =(Button) findViewById(R.id.imp);
//
//        dele_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                View view = LayoutInflater.from(mydaypage.this).inflate(R.layout.task_layout, recyclerView, false);
//                taskAdapter.ViewHolder holder = new taskAdapter.ViewHolder(view);
//                int position = holder.getAdapterPosition();
//                Task task = TaskList.get(position);
//                TaskList.remove(task);
//                adapter.notifyItemInserted(TaskList.size());
//                adapter.notifyDataSetChanged();
//                recyclerView.setAdapter(adapter);
//                planB:
//                SQLiteDatabase db = dbHelper.getReadableDatabase();
//                List<Task> compare = new ArrayList<Task>();
//                Cursor cursor = db.query("TaskData", null, null, null, null, null, null);
//                if (cursor.moveToFirst()) {
//                    do {
//                        String tasks = cursor.getString(cursor.getColumnIndex("Task"));
//                        Task task = new Task(tasks);
//                        if (!TaskList.contains(task) && !tasks.equals("")) {
//                            compare.add(task);
//                        }
//                    } while (cursor.moveToNext());
//                }
//                cursor.close();
//
//                db.close();
//
//                int size=TaskList.size();
//                for(int i=0;i<size;i++){
//                    Task task=TaskList.get(i);
//                    if(!compare.contains(task)){
//                        TaskList.remove(task);
//                        size-=1;
//                    }
//                }
//
//                adapter.notifyItemInserted(TaskList.size());
//                adapter.notifyDataSetChanged();
//                recyclerView.setAdapter(adapter);
//            }
//        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((Activity)getBaseContext()).finish();
                Intent intent=new Intent();
                intent.setClass(myday_page.this,MainActivity.class);
                startActivity(intent);

            }

        });

        edit_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(myday_page.this,edit_msg.class);
                startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY);
            }


        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //dbHelper = new MyDatabaseHelper(this,"TaskStore.db",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (requestCode == LAUNCH_SECOND_ACTIVITY) {
            if(resultCode == Activity.RESULT_OK){
                String input=data.getStringExtra("input");
                ContentValues values = new ContentValues();
                values.put("myDay",Boolean.TRUE);
                values.put("Important",Boolean.FALSE);
                values.put("Dated",Boolean.TRUE);
                values.put("Task",input);
                db.insert("TaskData",null,values);
                Task task = new Task(input);
                TaskList.add(task);

                db.close();

                Toast.makeText(this,"input done!",Toast.LENGTH_SHORT).show();
                adapter.notifyItemInserted(TaskList.size());
                //adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
            }
        }

        db.close();
    }//onActivityResult



    @Override
    protected void onStart() {
        super.onStart();

    }
}