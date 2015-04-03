package com.li.tritonia.wildlife;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;


public class CheckpointTapActivity extends ActionBarActivity {


    TextView txt;
    TextView test;
    NfcAdapter mNfcAdapter;
    private static final String TAG = "NFC";
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkpoint_tap);

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        txt = (TextView)findViewById(R.id.dataTextView);
        test = (TextView)findViewById(R.id.successTextView);


        if(mNfcAdapter == null){
            txt.setText("NFC is not available");
            finish();
            return;

        } else
            test.setText("NFC is supported");
            Log.d("test", "good");

        /*Button btn = (Button) findViewById(R.id.nfcButton);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = 0;
                trucount = 0;
                txt.setText(Integer.toString(count));
                test.setText("TRU count: " + Integer.toString(trucount));
            }
        });*/



    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        NdefMessage[] msgs = null;
        String text;

        count++;
        txt.setText(Integer.toString(count));

        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        if (rawMsgs != null) {
            msgs = new NdefMessage[rawMsgs.length];
            for (int i = 0; i < rawMsgs.length; i++) {

                msgs[i] = (NdefMessage) rawMsgs[i];
            }
        } else {
            // Unknown tag type
            byte[] empty = new byte[] {};
            NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN, empty, empty, empty);
            NdefMessage msg = new NdefMessage(new NdefRecord[] {
                    record
            });
            msgs = new NdefMessage[] {
                    msg
            };
        }

        byte[] payload = msgs[0].getRecords()[0].getPayload();
        try {

            String textEncoding = ((payload[0] & 0x80) == 0) ? "UTF-8" : "UTF-16";
            Log.d(TAG, "*********** NFC textEncoding = " + textEncoding);
            //Get the Language Code
            int languageCodeLength = payload[0] & 0x3F;
            Log.d(TAG, "*********** NFC languageCodeLength = " + languageCodeLength);
            String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");
            Log.d(TAG, "*********** NFC languageCode = " + languageCode);
            //Get the Text
            text = new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
            Log.d(TAG, "*********** NFC TAG = " + text);
            //check text
            if (text.equals("tru")){
                test.setText(text);

            }
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    NdefMessage[] getNdefMessages(Intent intent) {
        // Parse the intent
        NdefMessage[] msgs = null;
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action) || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {

                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
            } else {
                // Unknown tag type
                byte[] empty = new byte[] {};
                NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN, empty, empty, empty);
                NdefMessage msg = new NdefMessage(new NdefRecord[] {
                        record
                });
                msgs = new NdefMessage[] {
                        msg
                };
            }
        } else {
            Log.d(TAG, "Unknown intent.");
            finish();
        }
        return msgs;
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        Intent intent = new Intent(this, CheckpointTapActivity.class);
        intent.addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        IntentFilter[] intentFilter = new IntentFilter[]{};
        mNfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilter, null);

        if (mNfcAdapter.isEnabled()){
            Toast.makeText(this, "NFC is Enabled", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "NFC is Disabled", Toast.LENGTH_LONG).show();
            Intent i;
            if (Build.VERSION.SDK_INT >= 10) {
                i = new Intent("android.settings.NFC_SETTINGS");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                this.startActivity(i);
            } else {
                i = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                this.startActivity(i);
            }
        }


    }

    @Override
    protected void onPause() {
        super.onPause();

        mNfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_checkpoint_tap, menu);
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
