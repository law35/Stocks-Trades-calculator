package com.coscarlawshea.pennytraderapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class EditAccount extends AppCompatActivity {
    private Button editAcctCancelBtn,
            nextBtn,
            previousBtn,
            updateAcctBtn;

    private Spinner spinner2;

    private EditText editAcctNameInput,
            editAcctValueInput,
            editGainesInput,
            editLossesInput,
            accountID;

    private Cursor results;
    private MyDBManager dbManager = new MyDBManager(this, null, null, 1);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        //Display data
        results = dbManager.displayData();

        updateAcctBtn = findViewById(R.id.updateAcctBtn);
        editAcctNameInput = findViewById(R.id.editAcctNameInput);
        editAcctValueInput = findViewById(R.id.editAcctValueInput);
        editGainesInput = findViewById(R.id.editGainesInput);
        editLossesInput = findViewById(R.id.editLossesInput);
        accountID = findViewById(R.id.accountID);

        accountID.setText(getIntent().getStringExtra("DisplayID"));

        spinner2 = findViewById(R.id.spinner2);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.EditAcctMenu,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).toString().equals("Add Account")){
                    startActivity(new Intent(EditAccount.this, AddAccount.class));
                }

                if(parent.getItemAtPosition(position).toString().equals("Home")){
                    startActivity(new Intent(EditAccount.this, MainActivity.class));
                }

                if(parent.getItemAtPosition(position).toString().equals("Trades")){
                    startActivity(new Intent(EditAccount.this, TradesOverview.class));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        switch_to_MainActivity();
        read_Next();
        read_Previous();
        update_Account_Entry();
    }

    private void switch_to_MainActivity(){
        editAcctCancelBtn = findViewById(R.id.editAcctCancelBtn);
        editAcctCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditAccount.this, MainActivity.class));
            }
        });
    }

    public void read_Next(){
        nextBtn = findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(results.moveToNext()){
                    accountID.setText(results.getString(0));
                    editAcctNameInput.setText(results.getString(1));
                    editAcctValueInput.setText(results.getString(2));
                    editGainesInput.setText(results.getString(3));
                    editLossesInput.setText(results.getString(4));
                }
            }
        });
    }

    public void read_Previous(){
        previousBtn = findViewById(R.id.previousBtn);
        previousBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(results.moveToPrevious()){
                    accountID.setText(results.getString(0));
                    editAcctNameInput.setText(results.getString(1));
                    editAcctValueInput.setText(results.getString(2));
                    editGainesInput.setText(results.getString(3));
                    editLossesInput.setText(results.getString(4));
                }

            }
        });
    }

    //Update account
    public void update_Account_Entry() {

        updateAcctBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isUpdated = dbManager.updateAccount(
                        accountID.getText().toString(),
                        editAcctNameInput.getText().toString(),
                        editAcctValueInput.getText().toString(),
                        editGainesInput.getText().toString(),
                        editLossesInput.getText().toString()
                );

                //Log.d("STATE1:",accountID.getText().toString());
                //Log.d("STATE2:",editAcctNameInput.getText().toString());
                //Log.d("STATE3:",editAcctValueInput.getText().toString());
                //Log.d("STATE4:",editGainesInput.getText().toString());
                //Log.d("STATE5:",editLossesInput.getText().toString());

                if( isUpdated == true){
                    Toast.makeText(EditAccount.this,
                            "Data updated successfully.",
                            Toast.LENGTH_LONG).show();
                    startActivity(new Intent(EditAccount.this, MainActivity.class));
                }else{
                    Toast.makeText(EditAccount.this,
                            "Data not updated successfully.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

