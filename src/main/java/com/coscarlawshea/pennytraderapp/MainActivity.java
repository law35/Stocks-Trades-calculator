package com.coscarlawshea.pennytraderapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
//import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.util.Log;
//import android.widget.EditText;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Spinner;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView acctNameLink,
            acctBalanceDisplay,
            acctLossesDisplay,
            acctGainesDisplay,
            acctIDDisplay;

    private Button nextBtn, previousBtn, deleteAcctBtn;
    private Cursor results;
    private Spinner spinner;

    //Call the read database method
    MyDBManager dbManager = new MyDBManager(this, null, null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinner);

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.MainActivityMenu,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        nextBtn = findViewById(R.id.nextBtn);
        previousBtn = findViewById(R.id.previousBtn);
        deleteAcctBtn = findViewById(R.id.deleteAcctBtn);
        acctNameLink = findViewById(R.id.acctNameLink);
        acctIDDisplay = findViewById(R.id.acctIDDisplay);
        acctBalanceDisplay = findViewById(R.id.acctBalanceDisplay);
        acctLossesDisplay = findViewById(R.id.acctLossesDisplay);
        acctGainesDisplay = findViewById(R.id.acctGainesDisplay);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).toString().equals("Add Account")){
                    startActivity(new Intent(MainActivity.this, AddAccount.class));
                }

                if(parent.getItemAtPosition(position).toString().equals("Overview")){
                    String acctId = acctIDDisplay.getText().toString();
                    Intent myIntent = new Intent(MainActivity.this, AccountOverview.class);
                    myIntent.putExtra("AccountID", acctId);
                    startActivity(myIntent);
                }

                if(parent.getItemAtPosition(position).toString().equals("Edit Account")){
                    String idDisplay = acctIDDisplay.getText().toString();
                    Intent myIntent = new Intent(MainActivity.this, EditAccount.class);
                    myIntent.putExtra("DisplayID", idDisplay);
                    startActivity(myIntent);
                }

                if(parent.getItemAtPosition(position).toString().equals("Trades")){
                    startActivity(new Intent(MainActivity.this, TradesOverview.class));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Display data
        results = dbManager.displayData();

        populate();
        read_Next();
        read_Previous();
        delete_Account();
    }

    private void populate(){
        if (results != null && results.moveToFirst()) {
            acctIDDisplay.setText(results.getString(0));
            acctNameLink.setText(results.getString(1));
            acctBalanceDisplay.setText(results.getString(2));
            acctLossesDisplay.setText(results.getString(3));
            acctGainesDisplay.setText(results.getString(4));
        }
    }

    public void read_Next(){
        nextBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(results.moveToNext()){
                    acctIDDisplay.setText(results.getString(0));
                    acctNameLink.setText(results.getString(1));
                    acctBalanceDisplay.setText(results.getString(2));
                    acctLossesDisplay.setText(results.getString(3));
                    acctGainesDisplay.setText(results.getString(4));
                }
            }
        });
    }

    public void read_Previous(){
        previousBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                if(results.moveToPrevious()){
                    acctIDDisplay.setText(results.getString(0));
                    acctNameLink.setText(results.getString(1));
                    acctBalanceDisplay.setText(results.getString(2));
                    acctLossesDisplay.setText(results.getString(3));
                    acctGainesDisplay.setText(results.getString(4));
                }
            }
        });
    }

    public void delete_Account(){
        deleteAcctBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String acctId = acctIDDisplay.getText().toString();

                dbManager.deleteAccount(acctId);
                Toast.makeText(MainActivity.this, "Data deleted successfully.",
                        Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(MainActivity.this, MainActivity.class ));
            }
        });
    }
}
