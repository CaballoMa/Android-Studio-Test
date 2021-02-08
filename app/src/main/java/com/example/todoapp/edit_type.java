package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class edit_type extends AppCompatActivity {
    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_type);
        Button ensure=(Button)findViewById(R.id.main_ensure);

        ensure.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //((Activity)getBaseContext()).finish();
                EditText editText=(EditText)findViewById(R.id.main_edit_text);
                String inputText = editText.getText().toString();
                Intent returnIntent=new Intent();
                returnIntent.putExtra("input",inputText);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });

    }

}