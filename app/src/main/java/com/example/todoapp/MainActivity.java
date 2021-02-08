package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private List<Type> TypeList = new ArrayList<Type>();
    RecyclerView recyclerView;
    TypeAdapter adapter;
    SharedPreferences.Editor editor;
    private int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTypes();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TypeAdapter(TypeList);
        recyclerView.setAdapter(adapter);

        SharedPreferences type =getSharedPreferences("Type",MODE_PRIVATE);
        editor = type.edit();
        count = type.getInt("Count",0);
        if(count==0){
            editor.putInt("Count",0).apply();
            Toast.makeText(this,"first open !",Toast.LENGTH_SHORT).show();
        }
        else {
            for (int i = 1; i <= count; i++) {
                Type diyType = new Type(type.getString(String.valueOf(i), ""), R.drawable.apple_pic);
                TypeList.add(diyType);
            }
            adapter.notifyItemInserted(TypeList.size());
            recyclerView.setAdapter(adapter);
        }

        Button add = (Button)findViewById(R.id.main_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,edit_type.class);
                startActivityForResult(intent,1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String input = data.getStringExtra("input");
                count+=1;
                editor.putInt("Count",count).apply();
                Toast.makeText(this,String.valueOf(getSharedPreferences("Type",MODE_PRIVATE).getInt("Count",0)),Toast.LENGTH_SHORT).show();
                Toast.makeText(this,String.valueOf(count),Toast.LENGTH_SHORT).show();
                editor.putString(String.valueOf(count),input).apply();
                Type diy = new Type(input,R.drawable.apple_pic);
                TypeList.add(diy);
                adapter.notifyItemInserted(TypeList.size());
                recyclerView.setAdapter(adapter);
            }
        }
    }

    private void initTypes() {
        Type myDay=new Type("我的一天",R.drawable.apple_pic);
        TypeList.add(myDay);
        Type matter=new Type("重要",R.drawable.banana_pic);
        TypeList.add(matter);
        Type planned=new Type("已计划日常",R.drawable.cherry_pic);
        TypeList.add(planned);
        Type Tasks=new Type("Tasks",R.drawable.grape_pic);
        TypeList.add(Tasks);
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
}