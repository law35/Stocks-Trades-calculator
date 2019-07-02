package com.coscarlawshea.pennytraderapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class RiskManagement extends AppCompatActivity {
    private TextView riskMAcctNameView, riskManageBalView, tickerSymbolLbl,
            riskUnitLbl, avgTrueRangeLbl, riskRatioLbl, posValueLbl, abStopLbl;
    private TextInputEditText tickerSymbolTxtInput, potentialProfitTxtInput,
            potentialLossTxtInput, totalAcctValTxtInput,
            riskPercentageTxtInput, atrTxtInput, lossThresTxtInput,
            entryPriceTxtInput, riskUnitTxtInput,
            lossThresTxtInput1,
            numOfSharesTxtInput, pricePerShareTxtInput;

    private RadioButton shortPosRadioBtn, longPosRadioBtn;
    private Button riskMBackBtn, calculateBtn;
    private String acctID;

    //Call the read database method
    MyDBManager dbManager = new MyDBManager(this, null, null, 1);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risk_management);

        riskMAcctNameView = findViewById(R.id.riskMAcctNameView);
        riskManageBalView = findViewById(R.id.riskManageBalView);
        tickerSymbolTxtInput = findViewById(R.id.tickerSymbolTxtInput);
        potentialProfitTxtInput = findViewById(R.id.potentialProfitTxtInput);
        potentialLossTxtInput = findViewById(R.id.potentialLossTxtInput);
        totalAcctValTxtInput = findViewById(R.id.totalAcctValTxtInput);
        riskPercentageTxtInput = findViewById(R.id.riskPercentageTxtInput);
        riskMBackBtn = findViewById(R.id.riskMBackBtn);

        tickerSymbolLbl = findViewById(R.id.tickerSymbolLbl);
        avgTrueRangeLbl = findViewById(R.id.avgTrueRangeLbl);
        riskUnitLbl = findViewById(R.id.riskUnitLbl);
        riskRatioLbl = findViewById(R.id.riskRatioLbl);
        posValueLbl = findViewById(R.id.posValueLbl);
        abStopLbl = findViewById(R.id.abStopLbl);

        atrTxtInput = findViewById(R.id.atrTxtInput);
        calculateBtn = findViewById(R.id.calculateBtn);
        lossThresTxtInput = findViewById(R.id.lossThresTxtInput);
        entryPriceTxtInput = findViewById(R.id.entryPriceTxtInput);
        numOfSharesTxtInput = findViewById(R.id.numOfSharesTxtInput);
        pricePerShareTxtInput = findViewById(R.id.pricePerShareTxtInput);
        shortPosRadioBtn = findViewById(R.id.shortPosRadioBtn);
        longPosRadioBtn = findViewById(R.id.longPosRadioBtn);

        acctID = getIntent().getStringExtra("AccountIDManage");

        display_Acct_Data();
        calculate_Results();

        ticker_tool_tip();
        abs_tool_tip();
        rr_tool_tip();
        pv_tool_tip();
        ru_tool_tip();
        atr_tool_tip();
    }

    public void display_Acct_Data(){
        Cursor doesIDExist = dbManager.getRecordByID(acctID);

        if(doesIDExist != null && doesIDExist.moveToFirst()){
            riskMAcctNameView.setText(doesIDExist.getString(1));
            riskManageBalView.setText(doesIDExist.getString(2));
        }
    }

    public Double loss_Threshold(){
        String lossThreshold = lossThresTxtInput.getText().toString();
        Double lossThresholdFinal = Double.parseDouble(lossThreshold);

        return lossThresholdFinal;
    }

    public Double atr(){
        String atr = atrTxtInput.getText().toString();
        Double atrFinal = Double.parseDouble(atr);

        return  atrFinal;
    }

    public Double risk_Reward_Ratio(){
        String proValue = potentialProfitTxtInput.getText().toString();
        Double profitFinal = Double.parseDouble(proValue);

        String lossValue = potentialLossTxtInput.getText().toString();
        Double lossFinal = Double.parseDouble(lossValue);

        Double constant = Constants.RRConstant;

        if(profitFinal == 0 || lossFinal == 0){
            Toast.makeText(RiskManagement.this,
                    "Please enter values higher than zero for the required fields.",
                    Toast.LENGTH_LONG).show();
        }
        Double R3 = (profitFinal * constant) / lossFinal;
        return R3;
    }

    public Double risk_Unit(){
        String balance = riskManageBalView.getText().toString();
        Double balanceFinal = Double.parseDouble(balance);

        String risk = riskPercentageTxtInput.getText().toString();
        Double riskFinal = Double.parseDouble(risk);
        Double riskPercentage = riskFinal / Constants.PerDivisor;

        Double riskUnit = (balanceFinal * riskPercentage);

        return riskUnit;
    }

    public Double long_Position(){
        String entryPrice = entryPriceTxtInput.getText().toString();
        Double entryPriceFinal = Double.parseDouble(entryPrice);

        Double absoluteStop = (entryPriceFinal + (loss_Threshold() * atr()));
        return (double)Math.round(absoluteStop * 10000d) / 10000d;
    }

    public Double short_Position(){
        String entryPrice = entryPriceTxtInput.getText().toString();
        Double entryPriceFinal = Double.parseDouble(entryPrice);

        Double absoluteStop = (entryPriceFinal - (loss_Threshold() * atr()));
        return (double)Math.round(absoluteStop * 10000d) / 10000d;
    }

    public Double absolute_Stop() {
        boolean longPosition = longPosRadioBtn.isChecked();
        boolean shortPosition = shortPosRadioBtn.isChecked();

        if(longPosition == true){
            Double longPos = long_Position();
            return longPos;
        }

        if(shortPosition == true){
            Double shortPos = short_Position();
            return shortPos;
        }
        return null;
    }

    public Double position_Size(){
        //String riskUnit = riskUnitTxtInput.getText().toString();
        //Double riskUnitFinal = Double.parseDouble(riskUnit);

        Double positionSize = risk_Unit() / (loss_Threshold() * atr());

        return positionSize;
    }

    public Double position_Value(){
        String pricePerShare = pricePerShareTxtInput.getText().toString();
        Double pricePerShareFinal = Double.parseDouble(pricePerShare);

        String numberOfShares = numOfSharesTxtInput.getText().toString();
        Double numberOfSharesFinal = Double.parseDouble(numberOfShares);

        Double positionValue = (pricePerShareFinal * numberOfSharesFinal);

        return positionValue;
    }

    public void calculate_Results(){
        calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String abStop = String.valueOf(absolute_Stop());
                String posSize = String.valueOf(position_Size());
                String posValue = String.valueOf(position_Value());

                String atr = atrTxtInput.getText().toString();
                String ticker = tickerSymbolTxtInput.getText().toString();
                String account_name = riskMAcctNameView.getText().toString();
                String account_bal = riskManageBalView.getText().toString();
                String ru = String.valueOf(risk_Unit());
                String r3 = String.valueOf(risk_Reward_Ratio());

                Intent myIntent = new Intent(v.getContext(), RiskManagementResults.class);
                myIntent.putExtra("Account Name", account_name);
                myIntent.putExtra("Account Balance", account_bal);
                myIntent.putExtra("Absolute Stop", abStop);
                myIntent.putExtra("Position Size", posSize);
                myIntent.putExtra("Position Value", posValue);

                myIntent.putExtra("R3", r3);
                myIntent.putExtra("ATR", atr);
                myIntent.putExtra("RU", ru);
                myIntent.putExtra("Ticker", ticker);
                startActivity(myIntent);
            }
        });
    }

    public void ticker_tool_tip(){
        tickerSymbolLbl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(RiskManagement.this,
                        getString(R.string.CompanyTickerDef),
                        Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }

    public void atr_tool_tip(){
        avgTrueRangeLbl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(RiskManagement.this,
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
                Toast.makeText(RiskManagement.this,
                        getString(R.string.RiskUnitDef),
                        Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }

    public void rr_tool_tip(){
        riskRatioLbl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(RiskManagement.this,
                        getString(R.string.RiskRewardDef),
                        Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }

    public void pv_tool_tip(){
        posValueLbl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(RiskManagement.this,
                        getString(R.string.PositionValueDef),
                        Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }

    public void abs_tool_tip(){
        abStopLbl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(RiskManagement.this,
                        getString(R.string.AbsoluteStopDef),
                        Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }
}

