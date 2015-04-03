package com.li.tritonia.wildlife;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


public class TaskListActivity extends Activity {

    private ListView listView;
    private ArrayAdapter arrayAdapter;
    private Button addTaskBtn;
    private ArrayList<Task> taskList;
    private ArrayList<String> taskNameList;
    private int idCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        idCount = MainActivity.dataBase.numberOfTasks(1);

        addTaskBtn = (Button) findViewById(R.id.addNewTaskButton);
        listView = (ListView)findViewById(R.id.task_list);

        addTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addTaskActivity = new Intent(TaskListActivity.this, AddTaskActivity.class);

                startActivity(addTaskActivity);

            }
        });

    }

    public void initializeTaskList(){

        taskNameList = new ArrayList<String>();

        for(int i = 0; i <= idCount; i++){

            String name = new String();

            name = MainActivity.dataBase.getTaskName(i, 1);
            taskNameList.add(name);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        initializeTaskList();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, taskNameList);
        listView.setAdapter(arrayAdapter);
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
