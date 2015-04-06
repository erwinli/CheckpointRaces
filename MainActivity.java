package com.li.tritonia.wildlife;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

    private Button setupBtn;
    private Button playBtn;
    public static Database dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO refresh database for testing
        this.deleteDatabase("tasks.db");

        //Var initialization
        dataBase = new Database(this);
        setupBtn = (Button)findViewById(R.id.setupButton);
        playBtn = (Button)findViewById(R.id.playButton);

        // TODO generate test data
        gen();

        final Intent playGame = new Intent(MainActivity.this, GameSelectActivity.class);
        final Intent setupGame = new Intent(MainActivity.this, SetupActivity.class);

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

    //test data
    public void gen(){

        dataBase.insertTaskData(0, 0, "Owl", "Snow", 1.2, 1.5);
        dataBase.insertTaskData(1, 0, "Bear", "Brown", 1.2, 1.5);
        dataBase.insertTaskData(2, 0, "Bird", "Blue", 1.2, 1.3);

        dataBase.insertTaskData(0, 1, "Chicken", "Snow", 1.2, 1.5);
        dataBase.insertTaskData(1, 1, "Worm", "Brown", 1.2, 1.5);
        dataBase.insertTaskData(2, 1, "Bull", "Blue", 1.2, 1.3);

        dataBase.insertTaskData(0, 2, "Tree", "Snow", 1.2, 1.5);
        dataBase.insertTaskData(1, 2, "Toy", "Brown", 1.2, 1.5);
        dataBase.insertTaskData(2, 2, "No", "Blue", 1.2, 1.3);

        dataBase.insertTaskData(0, 3, "Pen", "Snow", 1.2, 1.5);
        dataBase.insertTaskData(1, 3, "Lu", "Brown", 1.2, 1.5);
        dataBase.insertTaskData(2, 3, "ho", "Blue", 1.2, 1.3);

        dataBase.insertHuntData(0, "Greenman", "Green pasture hunt");
        dataBase.insertHuntData(1, "Overhill", "Big hill hunt");
        dataBase.insertHuntData(2, "Checkpoint", "Quick fire hunt");
        dataBase.insertHuntData(3, "Summit", "Up hill hunt");

    }


}
