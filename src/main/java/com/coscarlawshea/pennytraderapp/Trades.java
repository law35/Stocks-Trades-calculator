package com.coscarlawshea.pennytraderapp;

public class Trades {
    private int trade_id;
    private String _company;
    private String _rrRatio;
    private String _atr;
    private String _riskUnit;
    private String _absStop;
    private String _positionSize;
    private String _positionValue;

    public Trades(String company, String rrRatio, String atr,
                  String riskUnit, String absStop, String positionSize,
                  String positionValue){
        this._company = company;
        this._absStop = absStop;
        this._rrRatio = rrRatio;
        this._atr = atr;
        this._riskUnit = riskUnit;
        this._positionSize = positionSize;
        this._positionValue = positionValue;
    }

    public void set_id(int trade_id){ this.trade_id = trade_id; }

    public void set_company(String _company){ this._company = _company; }

    public void set_rrRatio(String _rrRatio){ this._rrRatio = _rrRatio; }

    public void set_atr(String _atr){ this._atr = _atr; }

    public void set_riskUnit(String _riskUnit){ this._riskUnit = _riskUnit; }

    public void set_absStop(String _absStop){ this._absStop = _absStop; }

    public void set_positionSize(String _positionSize){ this._positionSize = _positionSize; }

    public void set_positionValue(String _positionValue){ this._positionValue = _positionValue; }


    public int get_id(){ return trade_id; }

    public String get_company(){ return _company; }

    public String get_rrRatio(){ return _rrRatio; }

    public String get_atr(){ return _atr; }

    public String get_riskUnit(){ return _riskUnit; }

    public String get_absStop(){ return _absStop; }

    public String get_positionSize(){ return _positionSize; }

    public String get_positionValue(){ return _positionValue; }
}
