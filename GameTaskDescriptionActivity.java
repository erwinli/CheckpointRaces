package com.li.tritonia.wildlife;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class GameTaskDescriptionActivity extends ActionBarActivity {

    int taskIdVal, huntIdVal;
    String taskDescription;

    Intent intent;

    TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_description);

        output = (TextView)findViewById(R.id.taskDescriptionTextView);
    }

    @Override
    protected void onResume() {
        super.onResume();

        intent = getIntent();
        taskIdVal = intent.getIntExtra("taskIdValue", 0);
        huntIdVal = intent.getIntExtra("huntIdValue", 0);

        taskDescription = MainActivity.dataBase.getTaskDesc(taskIdVal, huntIdVal);

        output.setText(taskDescription);
     }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_task_description, menu);
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
