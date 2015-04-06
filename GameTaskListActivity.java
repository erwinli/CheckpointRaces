package com.li.tritonia.wildlife;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class GameTaskListActivity extends ActionBarActivity {

    private ListView listView;
    private ArrayAdapter arrayAdapter;
    private ArrayList<String> taskNameList;
    private int taskIdVal;
    private int huntIdVal;
    private Intent intent;
    private static int taskComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_task_list);

        listView = (ListView)findViewById(R.id.task_list);


    }

    @Override
    protected void onResume() {
        super.onResume();

        intent = getIntent();
        huntIdVal = intent.getIntExtra("huntIdValue", 99);
        taskIdVal = MainActivity.dataBase.numberOfTasks(huntIdVal);
        Log.d("taskNum", String.valueOf(taskIdVal));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent taskListView = new Intent(GameTaskListActivity.this, MapActivity.class);
                taskListView.putExtra("huntIdValue", huntIdVal);
                taskListView.putExtra("taskIdValue", position);

                startActivity(taskListView);
            }
        });

        initializeTaskList();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, taskNameList);
        listView.setAdapter(arrayAdapter);
    }

    public void initializeTaskList(){
        taskNameList = new ArrayList<>();

        for(int i = 0; i <= taskIdVal; i++){
            String name = new String();
            name = MainActivity.dataBase.getTaskName(i, huntIdVal);
            taskNameList.add(name);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_task_list, menu);
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
