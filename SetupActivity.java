package com.li.tritonia.wildlife;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


public class SetupActivity extends ActionBarActivity {

    SharedPreferences prefs;

    private ListView listView;
    private ArrayAdapter arrayAdapter;
    private Button addTaskBtn;
    private ArrayList<Task> taskList;
    private int idCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        prefs = this.getSharedPreferences("com.li.tritonia.wildlife", Context.MODE_PRIVATE);

        idCount = prefs.getInt("idCount", 0);
        addTaskBtn = (Button)findViewById(R.id.addTaskButton);

        listView = (ListView)findViewById(R.id.task_list);

        addTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent addTask = new Intent(SetupActivity.this, AddTaskActivity.class);
                addTask.putParcelableArrayListExtra("list", taskList);

                startActivity(addTask);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        taskList = this.getIntent().getParcelableArrayListExtra("list");

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, taskList);
        listView.setAdapter(arrayAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        prefs.edit().putInt("idCount", idCount).apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_setup, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
