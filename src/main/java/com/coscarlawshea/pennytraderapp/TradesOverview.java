package com.coscarlawshea.pennytraderapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class TradesOverview extends AppCompatActivity {

    private TextView companyTicker,
            forAccount,
            tradeID,
            accountID,
            riskID,
            riskUnit,
            positionSize,
            positionVal,
            absStop,
            rrRatio,
            atr;

    private Button nextBtn, previousBtn;
    private Cursor riskDisplay;

    MyDBManager dbManager = new MyDBManager(this, null, null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trades_overview);

        Spinner spinner = findViewById(R.id.spinner);

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.TradesOverviewMenu,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        nextBtn = findViewById(R.id.nextBtn);
        previousBtn = findViewById(R.id.previousBtn);
        //deleteAcctBtn = findViewById(R.id.deleteAcctBtn);
        companyTicker = findViewById(R.id.companyTicker);
        forAccount = findViewById(R.id.forAccount);
        tradeID = findViewById(R.id.tradeID);
        accountID = findViewById(R.id.accountID);
        riskID = findViewById(R.id.riskID);

        riskUnit = findViewById(R.id.riskUnit);
        positionSize = findViewById(R.id.positionSize);
        positionVal = findViewById(R.id.positionVal);
        absStop = findViewById(R.id.absStop);
        rrRatio = findViewById(R.id.rrRatio);
        atr = findViewById(R.id.atr);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).toString().equals("Home")){
                    startActivity(new Intent(TradesOverview.this, MainActivity.class));
                }

                if(parent.getItemAtPosition(position).toString().equals("Add Account")){
                    startActivity(new Intent(TradesOverview.this, AddAccount.class));
                }

                if(parent.getItemAtPosition(position).toString().equals("Accounts")){
                    String acctId = accountID.getText().toString();
                    Intent myIntent = new Intent(TradesOverview.this, AccountOverview.class);
                    myIntent.putExtra("AccountID", acctId);
                    startActivity(myIntent);
                }

                if(parent.getItemAtPosition(position).toString().equals("Edit Account")){
                    String idDisplay = accountID.getText().toString();
                    Intent myIntent = new Intent(TradesOverview.this, EditAccount.class);
                    myIntent.putExtra("DisplayID", idDisplay);
                    startActivity(myIntent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        riskDisplay = dbManager.displayRisk();
        populate();
        display_Trade_for_Risk();
        read_Next();
        read_Previous();
    }

    private String populate(){
        if (riskDisplay != null && riskDisplay.moveToFirst()) {
            riskID.setText(riskDisplay.getString(0));
            accountID.setText(riskDisplay.getString(1));
            tradeID.setText(riskDisplay.getString(2));
            companyTicker.setText(riskDisplay.getString(3));
            forAccount.setText(riskDisplay.getString(4));

            String ticker = companyTicker.getText().toString();
            return ticker;
        }
        return null;
    }


    public void display_Trade_for_Risk(){
        String ticker = populate();
        Cursor check = dbManager.getRecordByCompanyFromTrades(ticker);

        if(check.moveToFirst() && check != null){
            //check.getString(0);
            //check.getString(1);
            absStop.setText(check.getString(2));
            rrRatio.setText(check.getString(3));
            atr.setText(check.getString(4));
            riskUnit.setText(check.getString(5));
            positionSize.setText(check.getString(6));
            positionVal.setText(check.getString(7));
        }
    }

    public void read_Next(){
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (riskDisplay != null && riskDisplay.moveToNext()) {
                    riskID.setText(riskDisplay.getString(0));
                    accountID.setText(riskDisplay.getString(1));
                    tradeID.setText(riskDisplay.getString(2));
                    companyTicker.setText(riskDisplay.getString(3));
                    forAccount.setText(riskDisplay.getString(4));

                    String ticker = companyTicker.getText().toString();
                    Cursor check = dbManager.getRecordByCompanyFromTrades(ticker);

                    if(check.moveToFirst() && check != null){
                        //check.getString(0);
                        //check.getString(1);
                        absStop.setText(check.getString(2));
                        rrRatio.setText(check.getString(3));
                        atr.setText(check.getString(4));
                        riskUnit.setText(check.getString(5));
                        positionSize.setText(check.getString(6));
                        positionVal.setText(check.getString(7));
                    }
                }
            }
        });
    }

    public void read_Previous(){
        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (riskDisplay != null && riskDisplay.moveToPrevious()) {
                    riskID.setText(riskDisplay.getString(0));
                    accountID.setText(riskDisplay.getString(1));
                    tradeID.setText(riskDisplay.getString(2));
                    companyTicker.setText(riskDisplay.getString(3));
                    forAccount.setText(riskDisplay.getString(4));

                    String ticker = companyTicker.getText().toString();
                    Cursor check = dbManager.getRecordByCompanyFromTrades(ticker);

                    if(check.moveToFirst() && check != null){
                        //check.getString(0);
                        //check.getString(1);
                        absStop.setText(check.getString(2));
                        rrRatio.setText(check.getString(3));
                        atr.setText(check.getString(4));
                        riskUnit.setText(check.getString(5));
                        positionSize.setText(check.getString(6));
                        positionVal.setText(check.getString(7));
                    }
                }
            }
        });
    }
}
