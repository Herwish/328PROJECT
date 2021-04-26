package com.example.a328project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class databaseEditor extends AppCompatActivity {

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_editor);

        db = new DatabaseHelper(this);
        EditText id = (EditText)findViewById(R.id.idSQL);
        EditText email = (EditText)findViewById(R.id.emailSQL);
        EditText name = (EditText)findViewById(R.id.nameSQL);

        Button insert = (Button)findViewById(R.id.insertSQL);
        Button edit = (Button)findViewById(R.id.editSQL);
        Button delete = (Button)findViewById(R.id.deleteSQL);
        Button goBack = (Button)findViewById(R.id.goToAct1);
        Button bttnView = (Button)findViewById(R.id.viewSQL);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                db.addData(Integer.parseInt(id.getText().toString()),name.getText().toString(),Integer.parseInt(email.getText().toString()));
            }
            catch(Error err){
                Toast.makeText(databaseEditor.this, "data error", Toast.LENGTH_SHORT).show();
                }
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
            db.update(Integer.parseInt(id.getText().toString()),Integer.parseInt(name.getText().toString()),Integer.parseInt(email.getText().toString()));
            }
            catch(Error err){
                Toast.makeText(databaseEditor.this, "data error", Toast.LENGTH_SHORT).show();
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    db.dltRow(id.getText().toString());
                }
                catch(Error err){
                    Toast.makeText(databaseEditor.this, "data error", Toast.LENGTH_SHORT).show();
                }
            }

        });
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(databaseEditor.this, MainActivity.class));
            }
        });
        bttnView.setOnClickListener(new View.OnClickListener() {
            // if nothing is entered in the employee NAME it will view all enteries in the table
            // if a string was written in the employee NAME it will show the record
            //an error will appear if no results were found

            Cursor cur;
            StringBuffer buffer=new StringBuffer();

            @Override
            public void onClick(View v) {
                if (name.getText().toString().equals("")) {
                    cur = db.getListContents();
                } else if (!(name.getText().toString().equals(""))) {
                    cur = db.getSpecificResult(name.getText().toString());
                }

                if (cur.getCount()==0)
                    Toast.makeText(databaseEditor.this, "No results found !", Toast.LENGTH_LONG).show();
                else {

                    while (cur.moveToNext()) {
                        for (int i = 0; i < cur.getColumnCount(); i++) {
                            buffer.append(cur.getColumnName(i) + ": " + cur.getString(i) + "\n");
                        }
                        buffer.append("\n");

                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(databaseEditor.this);
                    builder.setCancelable(true);
                    builder.setTitle("Results");
                    builder.setMessage(buffer.toString());
                    builder.show();

                    buffer.delete(0, buffer.capacity());

                }
            }
        });
    }
}