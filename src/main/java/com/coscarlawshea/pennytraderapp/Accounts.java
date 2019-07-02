package com.coscarlawshea.pennytraderapp;

public class Accounts {
	private int _id;
	private String _acctName;
	private String _balance;
	private String _gaines;
	private String _losses;

    public Accounts(String acctName, String balance, String gaines, String losses) {
        this._acctName = acctName;
        this._balance = balance;
        this._gaines = gaines;
        this._losses = losses;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_acctName(String _acctName) {
        this._acctName = _acctName;
    }

    public void set_balance(String _balance) {
        this._balance = _balance;
    }

    public void set_gaines(String _gaines) {
        this._gaines = _gaines;
    }

    public void set_losses(String _losses) {
        this._losses = _losses;
    }


    public int get_id() {
        return _id;
    }

    public String get_acctName() {
        return _acctName;
    }

    public String get_balance() {
        return _balance;
    }

    public String get_gaines() {
        return _gaines;
    }

    public String get_losses() {
        return _losses;
    }
}
