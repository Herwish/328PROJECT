package com.example.a328project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.common.internal.constants.ListAppsActivityContract;

import java.util.ArrayList;
import java.util.List;

public class list extends ListActivity {

    List<String> data = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ListView listData = (ListView)findViewById(R.id.list);
        adapter = new ArrayAdapter<String>(list.this, android.R.layout.simple_list_item_1, data);

        listData.setAdapter(adapter);
        listData.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        String[] message =  new String[1];
        Cursor cur = db.ViewList();
        while(cur.moveToNext())
        {
            message[0] = cur.getString(0) + "\n" + cur.getString(1);
        }
        setListAdapter(new ArrayAdapter<String>(this, R.layout.activity_list, R.id.textView6, message));
    }

}