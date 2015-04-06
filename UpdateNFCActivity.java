package com.li.tritonia.wildlife;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;


public class UpdateNFCActivity extends ActionBarActivity {

    NfcAdapter mNfcAdapter;
    Tag mytag;
    Intent intent;
    int taskIdVal, huntIdVal;
    String taskName, taskDesc;
    double gpsLat, gpsLon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_nfc);

        // Initializing variable with data from addTaskActivity
        intent = getIntent();
        huntIdVal = intent.getIntExtra("huntIdValue", 99);
        taskIdVal = intent.getIntExtra("taskIdValue", 0);
        taskName = intent.getStringExtra("passTaskName");
        taskDesc = intent.getStringExtra("passTaskDesc");
        gpsLat = intent.getDoubleExtra("passGPSLat", 0.0);
        gpsLon = intent.getDoubleExtra("passGPSLat", 0.0);

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        Log.d("NFC NAME", taskName);

    }

    //NFC write implementation
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        mytag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

        Log.d("Fore", "Discovered tag");
        try {
            write(taskName, mytag);
            Toast.makeText(this, "Writing to tag successful", Toast.LENGTH_LONG).show();

            // write info into DB
            MainActivity.dataBase.updateTaskData(taskIdVal, huntIdVal, taskName, taskDesc, gpsLat, gpsLon);

            Intent returnActivity = new Intent(UpdateNFCActivity.this, TaskListActivity.class);
            returnActivity.putExtra("huntIdValue", huntIdVal);
            startActivity(returnActivity);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        }

    }

    private NdefRecord createRecord(String text) throws UnsupportedEncodingException {

        //create the message in according with the standard
        String lang = "en";
        byte[] textBytes = text.getBytes();
        byte[] langBytes = lang.getBytes("US-ASCII");
        int langLength = langBytes.length;
        int textLength = textBytes.length;

        byte[] payload = new byte[1 + langLength + textLength];
        payload[0] = (byte) langLength;

        // copy langbytes and textbytes into payload
        System.arraycopy(langBytes, 0, payload, 1, langLength);
        System.arraycopy(textBytes, 0, payload, 1 + langLength, textLength);

        NdefRecord recordNFC = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], payload);
        return recordNFC;
    }

    private void write(String text, Tag tag) throws IOException, FormatException {

        NdefRecord[] records = { createRecord(text) };
        NdefMessage message = new NdefMessage(records);
        Ndef ndef = Ndef.get(tag);
        ndef.connect();
        ndef.writeNdefMessage(message);
        ndef.close();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = new Intent(this, UpdateNFCActivity.class);
        intent.addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        IntentFilter[] intentFilter = new IntentFilter[]{};
        mNfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilter, null);
        Log.d("Fore", "Foreground enabled");
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(mNfcAdapter != null){
            mNfcAdapter.disableForegroundDispatch(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_update_nfc, menu);
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
