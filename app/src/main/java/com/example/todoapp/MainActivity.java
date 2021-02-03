package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private List<Type> TypeList = new ArrayList<Type>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTypes();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        TypeAdapter adapter = new TypeAdapter(TypeList);
        recyclerView.setAdapter(adapter);
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

}