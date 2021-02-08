package com.example.todoapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class diy_page extends AppCompatActivity {

    private MyDatabaseHelper dbHelper= new MyDatabaseHelper(this,"TaskStore.db",null,1);;
    private List<Task> TaskList = new ArrayList<Task>();
    private String[] title={""};
    int LAUNCH_SECOND_ACTIVITY = 1;
    taskAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diy_page);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Intent getItent = getIntent();
        title[0] = getItent.getStringExtra("diy");

        TextView textView = findViewById(R.id.title_text);
        textView.setText(title[0]);

        Cursor cursor = db.query("TaskData", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String tasks = cursor.getString(cursor.getColumnIndex("Task"));
                Task task = new Task(tasks);
                String type=cursor.getString(cursor.getColumnIndex("Type"));
                if (type.equals(title[0])) {
                    TaskList.add(task);
                    Toast.makeText(diy_page.this,tasks,Toast.LENGTH_SHORT).show();
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
        Button edit_button=(Button) findViewById(R.id.main_add);
        Button share_button=(Button) findViewById(R.id.title_share);

        share_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                String shareText=title[0];
                for(int i=0;i<TaskList.size();i++){
                    shareText+="\n"+TaskList.get(i).getText();
                }
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
                shareIntent.setType("text/plain");
                startActivity(shareIntent);
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((Activity)getBaseContext()).finish();
                Intent intent=new Intent();
                intent.setClass(diy_page.this,MainActivity.class);
                startActivity(intent);

            }

        });
        edit_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(diy_page.this,edit_msg.class);
                intent.putExtra("extra data","add");
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
                String date = data.getStringExtra("Date");
                ContentValues values = new ContentValues();
                values.put("Type",title[0]);
                values.put("Important",Boolean.FALSE);
                values.put("Dated",date);
                Task task;
                if(date.equals("")){
                    task = new Task(input);
                    values.put("Task",input);
                }
                else {
                    task = new Task(input + "(" + date + ")");
                    values.put("Task",input+"("+date+")");
                }
                db.insert("TaskData",null,values);
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
    protected void onResume() {
        super.onResume();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<String> list = new ArrayList<String >();
        for(int i=0;i<TaskList.size();i++){
            list.add(TaskList.get(i).getText());
        }

        Cursor cursor = db.query("TaskData", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String tasks = cursor.getString(cursor.getColumnIndex("Task"));
                Task task = new Task(tasks);
                String type=cursor.getString(cursor.getColumnIndex("Type"));
                if (!list.contains(tasks)&&type.equals(title[0])) {
                    TaskList.add(task);
                }

            } while (cursor.moveToNext());
        }
        cursor.close();
        adapter.notifyItemInserted(TaskList.size());
        recyclerView.setAdapter(adapter);
        db.close();
    }
}