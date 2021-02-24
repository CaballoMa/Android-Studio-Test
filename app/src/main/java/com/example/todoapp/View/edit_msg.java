package com.example.todoapp.View;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.todoapp.Model.ChooseDateDialog;
import com.example.todoapp.Model.MyDatabaseHelper;
import com.example.todoapp.R;

public class edit_msg extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;
    private String[] output={""};
    ChooseDateDialog dialog = new ChooseDateDialog();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        final String data = intent.getStringExtra("extra data");

        setContentView(R.layout.activity_edit_msg);
        Button ensure=(Button)findViewById(R.id.ensure);
        Button date = (Button)findViewById(R.id.Date);

        ensure.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //((Activity)getBaseContext()).finish();
                EditText editText=(EditText)findViewById(R.id.edit_text);
                String inputText = editText.getText().toString();
                Intent returnIntent=new Intent();
                String inputDate =output[0];

                if(data.equals("add")){
                returnIntent.putExtra("input",inputText);
                returnIntent.putExtra("Date", inputDate);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
                }
                else {
                    MyDatabaseHelper dbHelper= new MyDatabaseHelper(edit_msg.this,"TaskStore.db",null,1);;
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    Cursor cursor = db.query("TaskData", new String[]{"Type , Important, Dated, Task"}, "Task=?", new String[]{data}, null, null, null);
                    cursor.moveToFirst();
                    ContentValues values = new ContentValues();
                    values.put("Task",inputText);
                    if(!inputDate.equals("")){
                        values.put("Dated",inputDate);
                    }
                    db.update("TaskData",values,"Task=?",new String[] {data});
                    db.close();
                    finish();
                }
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.showDialog(v.getContext(),(EditText)findViewById(R.id.date_text),output);
            }
        });
    }

}
