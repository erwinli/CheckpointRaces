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
    public static Database dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = this.getSharedPreferences("com.li.tritonia.wildlife", Context.MODE_PRIVATE);

        if(savedInstanceState != null){
            taskList = savedInstanceState.getParcelableArrayList(STATE_TASK);
        }

        this.deleteDatabase("tasks.db");

        dataBase = new Database(this);

        taskList = new ArrayList<>();
        gen();

        setupBtn = (Button)findViewById(R.id.setupButton);
        playBtn = (Button)findViewById(R.id.playButton);

        final Intent playGame = new Intent(MainActivity.this, MapActivity.class);
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

        dataBase.insertTaskData(0, 1, "Owl", "Snow", 1.2, 1.5);
        dataBase.insertTaskData(1, 1, "Bear", "Brown", 1.2, 1.5);
        dataBase.insertTaskData(2, 1, "Bird", "Blue", 1.2, 1.3);
        dataBase.insertHuntData(0, "first", "descr");
        dataBase.insertHuntData(1, "second", "test");
        dataBase.insertHuntData(2, "second", "test");
        dataBase.insertHuntData(3, "second", "test");

    }


}
