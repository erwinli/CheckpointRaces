package com.li.tritonia.wildlife;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    SharedPreferences prefs;

    Button setupBtn;
    Button playBtn;
    ArrayList<Task> taskList;
    private static final String STATE_TASK = "state_task";
    int idCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = this.getSharedPreferences("com.li.tritonia.wildlife", Context.MODE_PRIVATE);

        if(savedInstanceState != null){
            taskList = savedInstanceState.getParcelableArrayList(STATE_TASK);
        }

        taskList = new ArrayList<>();
        gen();

        setupBtn = (Button)findViewById(R.id.setupButton);
        playBtn = (Button)findViewById(R.id.playButton);

        final Intent playGame = new Intent(MainActivity.this, GameStartActivity.class);
        final Intent setupGame = new Intent(MainActivity.this, SetupActivity.class);

        setupGame.putParcelableArrayListExtra("list", taskList);

        setupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(setupGame);
            }
        });
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(playGame);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        prefs.edit().putInt("idCount", idCount).apply();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(STATE_TASK, taskList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void gen(){
        Task task = new Task();
        Task task1 = new Task();
        Task task2 = new Task();

        task.setTaskID(idCount);
        task.setTaskname("Task " + Integer.toString(1));
        task.setTaskdescription("Description " + Integer.toString(1));
        task.setGpsLat(1.576);
        task.setGpsLong(1.555);

        idCount++;
        taskList.add(task);

        task1.setTaskID(idCount);
        task1.setTaskname("Task " + Integer.toString(2));
        task1.setTaskdescription("Description " + Integer.toString(2));
        task1.setGpsLat(1.576);
        task1.setGpsLong(1.555);

        idCount++;
        taskList.add(task1);

        task2.setTaskID(idCount);
        task2.setTaskname("Task " + Integer.toString(3));
        task2.setTaskdescription("Description " + Integer.toString(3));
        task2.setGpsLat(1.576);
        task2.setGpsLong(1.555);

        idCount++;
        taskList.add(task2);
    }


}
