package com.example.a328project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("users");

        EditText id = (EditText)findViewById(R.id.id);
        EditText email = (EditText)findViewById(R.id.email);
        EditText name = (EditText)findViewById(R.id.name);

        Button insert = (Button)findViewById(R.id.insert);
        Button editName = (Button)findViewById(R.id.editName);
        Button editEmail = (Button)findViewById(R.id.editEmail);
        Button delete = (Button)findViewById(R.id.delete);
        Button goList = (Button)findViewById(R.id.listbttn);
        Button goWeather = (Button)findViewById(R.id.weatherbttn);
        Button goSQL = (Button)findViewById(R.id.goSQL);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    createUser(id.getText().toString(), name.getText().toString(), email.getText().toString());
                    Toast.makeText(MainActivity.this, "data successfully added", Toast.LENGTH_SHORT).show();
                }
                catch(Error err){
                    Toast.makeText(MainActivity.this, "Error occured: " + err + " couldn't add data", Toast.LENGTH_SHORT).show();
                }
            }
        });
        editName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    editUserName(id.getText().toString(), name.getText().toString());
                    Toast.makeText(MainActivity.this, "data successfully modified", Toast.LENGTH_SHORT).show();
                }
                catch(Error err){
                    Toast.makeText(MainActivity.this, "Error occured: " + err + " couldn't edit data", Toast.LENGTH_SHORT).show();
                }
            }
        });
        editEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    editUserEmail(id.getText().toString(), email.getText().toString());
                    Toast.makeText(MainActivity.this, "data successfully modified", Toast.LENGTH_SHORT).show();
                }
                catch(Error err){
                    Toast.makeText(MainActivity.this, "Error occured: " + err + " couldn't edit data", Toast.LENGTH_SHORT).show();
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                        deleteUser(id.getText().toString());
                        Toast.makeText(MainActivity.this, "data deleted", Toast.LENGTH_SHORT).show();
                }
                catch(Error err){
                    Toast.makeText(MainActivity.this, "Error occured: " + err + " couldn't delete data", Toast.LENGTH_SHORT).show();
                }
            }
        });
        goList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, list.class));
            }
        });
        goWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, weather.class));
            }
        });
        goSQL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, databaseEditor.class));
            }
        });

    }
    @IgnoreExtraProperties
    public class User{
        public String username;
        public String email;

        public User(){

        }
        public User(String username, String email){
            this.username = username;
            this.email = email;
        }
    }
    private void createUser(String id, String name, String email){
        User user = new User(name, email);
        ref.child(id).setValue(user);
    }
    private void editUserName(String id, String name){
        ref.child(id).child("username").setValue(name);
    }
    private void editUserEmail(String id, String email){
        ref.child(id).child("email").setValue(email);
    }
    private void deleteUser(String id){
        ref.child(id).removeValue();
    }
}
