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


public class GameSelectActivity extends ActionBarActivity {

    Button okBtn;
    private ListView listView;
    private ArrayAdapter arrayAdapter;
    private ArrayList<String> huntNameList;
    private int huntIdValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_select);

        huntIdValue = MainActivity.dataBase.numberOfHunts();
        huntNameList = new ArrayList<String>();
        listView = (ListView)findViewById(R.id.hunt_list);
    }

    @Override
    protected void onResume() {
        super.onResume();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent moveToDesc = new Intent(GameSelectActivity.this, GameDescriptionActivity.class);
                moveToDesc.putExtra("huntIdValue", position);

                startActivity(moveToDesc);
            }
        });

        // Display all existing hunts
        initializeHuntList();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, huntNameList);
        listView.setAdapter(arrayAdapter);
    }

    public void initializeHuntList(){
        huntNameList.clear();

        for(int i = 0; i <= huntIdValue; i++){
            String name;

            // Pull each existing hunts name from DB
            name = MainActivity.dataBase.getHuntName(i);
            huntNameList.add(name);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_start, menu);
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
