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
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


public class TaskListActivity extends ActionBarActivity {

    private ListView listView;
    private ArrayAdapter arrayAdapter;
    private Button addTaskBtn;
    private Button doneBtn;
    private ArrayList<String> taskNameList;
    private int taskIdVal;
    private int huntIdVal;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        addTaskBtn = (Button) findViewById(R.id.newTaskButton);
        doneBtn = (Button)findViewById(R.id.doneButton);
        listView = (ListView)findViewById(R.id.task_list);
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
    protected void onResume() {
        super.onResume();

        intent = getIntent();
        huntIdVal = intent.getIntExtra("huntIdValue", 99);
        taskIdVal = MainActivity.dataBase.numberOfTasks(huntIdVal);
        Log.d("taskNum", String.valueOf(taskIdVal));

        addTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addTaskActivity = new Intent(TaskListActivity.this, AddTaskActivity.class);

                addTaskActivity.putExtra("huntIdValue", huntIdVal);
                addTaskActivity.putExtra("taskIdValue", taskIdVal + 1);
                startActivity(addTaskActivity);
            }
        });

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnToSettings = new Intent(TaskListActivity.this, SetupActivity.class);

                startActivity(returnToSettings);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent taskListView = new Intent(TaskListActivity.this, UpdateTaskActivity.class);
                taskListView.putExtra("huntIdValue", huntIdVal);
                taskListView.putExtra("taskIdValue", position);

                startActivity(taskListView);
            }
        });

        initializeTaskList();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, taskNameList);
        listView.setAdapter(arrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_task_list_main, menu);
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
