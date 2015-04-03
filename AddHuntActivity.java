package com.li.tritonia.wildlife;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class AddHuntActivity extends Activity {

    Button doneBtn;
    EditText huntNameEdit;
    EditText huntDescEdit;
    int huntCount, currHuntId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hunt);

        //Var initialization
        doneBtn = (Button)findViewById(R.id.doneButton);
        huntNameEdit = (EditText)findViewById(R.id.huntNameEditText);
        huntDescEdit = (EditText)findViewById(R.id.huntDescEditText);
        huntCount = MainActivity.dataBase.numberOfHunts();
        currHuntId = huntCount + 1;

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Insert validation of text fields
                Log.d("test", "clicked");

                //Save user input into hunt table - db
                MainActivity.dataBase.insertHuntData(currHuntId, huntNameEdit.getText().toString(), huntDescEdit.getText().toString());

                Intent nextActivity = new Intent(AddHuntActivity.this, AddTaskActivity.class);
                nextActivity.putExtra("huntIdValue", currHuntId);

                startActivity(nextActivity);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_hunt, menu);
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
