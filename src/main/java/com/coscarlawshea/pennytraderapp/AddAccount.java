package com.coscarlawshea.pennytraderapp;

import android.accounts.Account;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

public class AddAccount extends AppCompatActivity {

    private EditText acctNameTxtInput,
            acctValueTxtInput,
            acctGainesTxtInput,
            acctLossesTxtInput;
    private Button addAcctSaveBtn, addAcctCancelBtn;
    private MyDBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);

        acctNameTxtInput = findViewById(R.id.acctNameTxtInput);
        acctValueTxtInput = findViewById(R.id.acctValueTxtInput);
        acctGainesTxtInput = findViewById(R.id.acctGainesTxtInput);
        acctLossesTxtInput = findViewById(R.id.acctLossesTxtInput);

        addAcctSaveBtn = findViewById(R.id.addAcctSaveBtn);
        addAcctCancelBtn = findViewById(R.id.addAcctCancelBtn);
        dbManager = new MyDBManager(this, null, null, 1);

        save_AcctBtn_Clicked();
        cancel_AcctBtn_Clicked();
    }

    public void cancel_AcctBtn_Clicked() {
        addAcctCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //Add an account
    public void save_AcctBtn_Clicked() {
        addAcctSaveBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                 Accounts account = new Accounts(
                        acctNameTxtInput.getText().toString(),
                        acctValueTxtInput.getText().toString(),
                        acctGainesTxtInput.getText().toString(),
                        acctLossesTxtInput.getText().toString());

                 if(acctNameTxtInput.getText().toString().equals("")
                         && acctValueTxtInput.getText().toString().equals("")
                         && acctGainesTxtInput.getText().toString().equals("")
                         && acctLossesTxtInput.getText().toString().equals("")){
                     Toast.makeText(AddAccount.this, "Account not added, try again.",
                             Toast.LENGTH_LONG).show();
                 }else{
                     dbManager.addAccount(account);
                     Toast.makeText(AddAccount.this, "Account added successfully.",
                             Toast.LENGTH_LONG).show();
                     startActivity(new Intent(AddAccount.this, MainActivity.class));
                 }

            }
        });
    }
}
