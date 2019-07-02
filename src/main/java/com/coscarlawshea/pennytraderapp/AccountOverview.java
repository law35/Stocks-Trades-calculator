package com.coscarlawshea.pennytraderapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class AccountOverview extends AppCompatActivity {
    private TextView acctNameTxtView,
            balanceTxtView,
            gainesTxtView,
            lossesTxtView;
    private Spinner spinner3;

    private TextInputEditText idInput;
    private String id;
    private Button submitBtn, manageAcctBtn;

    //Call the read database method
    MyDBManager dbManager = new MyDBManager(this, null, null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_overview);



        acctNameTxtView = findViewById(R.id.acctNameTxtView);
        balanceTxtView = findViewById(R.id.balanceTxtView);
        gainesTxtView = findViewById(R.id.gainesTxtView);
        lossesTxtView = findViewById(R.id.lossesTxtView);
        idInput = findViewById(R.id.idInput);
        manageAcctBtn = findViewById(R.id.manageAcctBtn);
        submitBtn = findViewById(R.id.submitBtn);
        spinner3 = findViewById(R.id.spinner3);

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.EditAcctMenu,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter);


        idInput.setText(getIntent().getStringExtra("AccountID"));

        return_Home();
        transfer_Data();
        find_ID();
    }

    private void return_Home(){
        Button acctViewHomeBtn = findViewById(R.id.acctViewHomeBtn);
        acctViewHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void find_ID(){
        Cursor doesIdExist = dbManager.getRecordByID(idInput.getText().toString());

        if (doesIdExist != null && doesIdExist.moveToFirst()){
            acctNameTxtView.setText(doesIdExist.getString(1));
            balanceTxtView.setText(doesIdExist.getString(2));
            gainesTxtView.setText(doesIdExist.getString(3));
            lossesTxtView.setText(doesIdExist.getString(4));
        }
    }

    public void transfer_Data(){
        manageAcctBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = idInput.getText().toString();
                Log.d("IdInput:", id);

                Intent myIntent = new Intent(v.getContext(), RiskManagement.class);
                myIntent.putExtra("AccountIDManage", id);
                startActivity(myIntent);
            }
        });
    }

}
