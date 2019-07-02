package com.coscarlawshea.pennytraderapp;

import android.database.Cursor;

public class AccountTrade {
    private int _riskId;
    private String _tradeName;
    private String _forAccount;
    private int _accountId;
    private int _tradeId;


    public AccountTrade(String tradeName, String forAccount, int accountId, int tradeId){
        this._tradeName = tradeName;
        this._forAccount = forAccount;
        this._accountId = accountId;
        this._tradeId = tradeId;
    }

    public void set_id(int _riskId){this._riskId = _riskId;}

    public void set_tradeName(String _tradeName){this._tradeName = _tradeName;}

    public void set_forAccount(String _forAccount){this._forAccount = _forAccount;}

    public void set_accountId(int _accountId){this._accountId = _accountId;}

    public void set_tradeId(int _tradeId){this._tradeId = _tradeId;}



    public int get_riskId(){return _riskId;}

    public String get_tradeName(){return _tradeName;}

    public String get_forAccount(){return _forAccount;}

    public int get_accountId(){return _accountId;}

    public int get_tradeId(){return _tradeId;}

}
