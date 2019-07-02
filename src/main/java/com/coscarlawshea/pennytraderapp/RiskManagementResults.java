package com.coscarlawshea.pennytraderapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RiskManagementResults extends AppCompatActivity {
    private TextView companyTicker, riskRewardRatioResults,
            atrResults, riskUnitResults, riskRewardLbl,
            abStopResults, riskPosSizeResults, atrResultsLbl,
            posValueResults, acctName, riskMResultsBalanceTxtView,
            riskUnitLbl, absStopLbl, riskPosSizeLbl, posValueLbl;

    private MyDBManager dbManager;

    private Button resultsHomeBtn, resultsSaveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risk_management_results);
        acctName = findViewById(R.id.acctName);
        riskMResultsBalanceTxtView = findViewById(R.id.riskMResultsBalanceTxtView);
        companyTicker = findViewById(R.id.companyTicker);
        riskRewardRatioResults = findViewById(R.id.riskRewardRatioResults);
        riskRewardLbl = findViewById(R.id.riskRewardLbl);
        atrResults = findViewById(R.id.atrResults);
        atrResultsLbl = findViewById(R.id.atrResultsLbl);
        riskUnitResults = findViewById(R.id.riskUnitResults);
        riskUnitLbl = findViewById(R.id.riskUnitLbl);
        abStopResults = findViewById(R.id.abStopResults);
        absStopLbl = findViewById(R.id.abStopLbl);
        riskPosSizeResults = findViewById(R.id.riskPosSizeResults);
        riskPosSizeLbl=findViewById(R.id.riskPosSizeLbl);
        posValueResults = findViewById(R.id.posValueResults);
        posValueLbl = findViewById(R.id.posValueLbl);
        resultsHomeBtn = findViewById(R.id.resultsHomeBtn);
        resultsSaveBtn = findViewById(R.id.resultsSaveBtn);
        dbManager = new MyDBManager(this, null, null, 1);

        display();
        save_Button_Clicked();
        rr_tool_tip();
        atr_tool_tip();
        ru_tool_tip();
        rps_tool_tip();
        abs_tool_tip();
        pv_tool_tip();
    }

    public void display(){
        acctName.setText(getIntent().getStringExtra("Account Name"));
        riskMResultsBalanceTxtView.setText(getIntent().getStringExtra("Account Balance"));
        companyTicker.setText(getIntent().getStringExtra("Ticker"));
        abStopResults.setText(getIntent().getStringExtra("Absolute Stop"));
        riskRewardRatioResults.setText(getIntent().getStringExtra("R3"));
        atrResults.setText(getIntent().getStringExtra("ATR"));
        riskUnitResults.setText(getIntent().getStringExtra("RU"));
        riskPosSizeResults.setText(getIntent().getStringExtra("Position Size"));
        posValueResults.setText(getIntent().getStringExtra("Position Value"));
    }

    public void store_calculations(){
        Trades trade = new Trades(
                companyTicker.getText().toString(),
                abStopResults.getText().toString(),
                riskRewardRatioResults.getText().toString(),
                atrResults.getText().toString(),
                riskUnitResults.getText().toString(),
                riskPosSizeResults.getText().toString(),
                posValueResults.getText().toString());

        dbManager.addTrade(trade);
    }

    public int find_Acct_Id(){
       String accountName = acctName.getText().toString();
       Cursor result = dbManager.getRecordByNameFromAccts(accountName);

       return Integer.parseInt(result.getString(0));
    }

    public int find_Trade_Id(){
        String ticker = companyTicker.getText().toString();
        Cursor result = dbManager.getRecordByCompanyFromTrades(ticker);

        return Integer.parseInt(result.getString(0));
    }

    public void add_Risk_Record(){
        AccountTrade risk = new AccountTrade(
                companyTicker.getText().toString(),
                acctName.getText().toString(),
                find_Acct_Id(),
                find_Trade_Id()
        );

        dbManager.addRisk(risk);
    }

    public void save_Button_Clicked(){
        resultsSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                store_calculations();
                add_Risk_Record();

                Toast.makeText(RiskManagementResults.this,
                        "Account added successfully.",
                        Toast.LENGTH_LONG).show();
                startActivity(new Intent(RiskManagementResults.this,
                        MainActivity.class));

            }
        });
    }

    public void rr_tool_tip(){
        riskRewardLbl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(RiskManagementResults.this,
                        getString(R.string.RiskRewardDef),
                        Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }

    public void atr_tool_tip(){
        atrResultsLbl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(RiskManagementResults.this,
                        getString(R.string.ATRDef),
                        Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }

    public void ru_tool_tip(){
        riskUnitLbl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(RiskManagementResults.this,
                        getString(R.string.RiskUnitDef),
                        Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }

    public void abs_tool_tip(){
        absStopLbl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(RiskManagementResults.this,
                        getString(R.string.AbsoluteStopDef),
                        Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }

    public void rps_tool_tip(){
        riskPosSizeLbl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(RiskManagementResults.this,
                        getString(R.string.RiskPositionSizeDef),
                        Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }

    public void pv_tool_tip(){
        posValueLbl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(RiskManagementResults.this,
                        getString(R.string.PositionValueDef),
                        Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }
}
