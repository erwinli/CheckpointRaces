package com.li.tritonia.wildlife;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class GameDescriptionActivity extends ActionBarActivity {

    Intent intent;
    TextView gameDescriptionOutput;
    Button continueBtn;
    int huntIdVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_description);

        gameDescriptionOutput = (TextView)findViewById(R.id.gameDescTextView);
        continueBtn = (Button)findViewById(R.id.continueButton);

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startGame = new Intent(GameDescriptionActivity.this, GameTaskListActivity.class);
                startGame.putExtra("huntIdValue", huntIdVal);

                startActivity(startGame);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        intent = getIntent();
        huntIdVal = intent.getIntExtra("huntIdValue", 99);
        gameDescriptionOutput.setText(MainActivity.dataBase.getHuntDesc(huntIdVal));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_description, menu);
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
