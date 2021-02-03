package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class edit_msg extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_msg);
        Button ensure=(Button)findViewById(R.id.OK);
        ensure.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //((Activity)getBaseContext()).finish();
                EditText editText=(EditText)findViewById(R.id.edit_text);
                String inputText = editText.getText().toString();
                Intent returnIntent=new Intent();
                returnIntent.putExtra("input",inputText);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });
    }
}
