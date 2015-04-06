package com.li.tritonia.wildlife;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


public class SetupActivity extends ActionBarActivity {

    private ListView listView;
    private ArrayAdapter arrayAdapter;
    private Button newGameBtn;
    private Button doneBtn;
    private ArrayList<String> gameNameList;
    private int gameIdValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Variable initialization
        gameIdValue = MainActivity.dataBase.numberOfHunts();
        newGameBtn = (Button)findViewById(R.id.newGameButton);
        doneBtn = (Button)findViewById(R.id.doneButton);
        listView = (ListView)findViewById(R.id.task_list);
        gameNameList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, gameNameList);

    }

    public void initializeHuntList(){
        gameNameList.clear();

        for(int i = 0; i <= gameIdValue; i++){
            String name;
            // Pull each existing hunts name from DB
            name = MainActivity.dataBase.getHuntName(i);
            gameNameList.add(name);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent taskListView = new Intent(SetupActivity.this, TaskListActivity.class);
                taskListView.putExtra("huntIdValue", position);
                startActivity(taskListView);
            }
        });

        // Display all existing hunts
        initializeHuntList();
        listView.setAdapter(arrayAdapter);

        // Move to Activity for new hunt information
        newGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent taskList = new Intent(SetupActivity.this, AddHuntActivity.class);
                taskList.putExtra("huntIdValue", listView.getCount() + 1);
                startActivity(taskList);
            }
        });

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToMenu = new Intent(SetupActivity.this, MainActivity.class);
                startActivity(backToMenu);
            }
        });
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
