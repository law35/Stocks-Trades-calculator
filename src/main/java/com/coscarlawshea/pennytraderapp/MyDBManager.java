package com.coscarlawshea.pennytraderapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;


public class MyDBManager extends SQLiteOpenHelper {
	private static final double DATABASE_VERSION = 1.0;
	private static final String DATABASE_NAME = "accounts.db";

	private static final String TABLE_NAME = "accounts";
	//Column Names Accounts:
	private static final String COLUMN_ID = "_id";
	private static final String COLUMN_ACCNAME = "accountname";
	private static final String COLUMN_BALANCE = "balance";
	private static final String COLUMN_GAINES = "gaines";
	private static final String COLUMN_LOSSES = "losses";

	private static final String TABLE_NAME1 = "trades";
	//Column Names Trades:
	private static final String COLUMN_TRADES_ID = "trade_id";
	private static final String COLUMN_COMPANY = "company";
    private static final String COLUMN_ABSOLUTE_STOP = "absStop";
	private static final String COLUMN_RISK_REWARD = "rrRatio";
	private static final String COLUMN_ATR = "atr";
	private static final String COLUMN_RISK_UNIT = "riskUnit";
	private static final String COLUMN_POSITION_SIZE = "positionSize";
	private static final String COLUMN_POSITION_VALUE = "positionValue";

	private static final String TABLE_NAME2 = "risk_management";
	//Column Names trades for account:
	private static final String COLUMN_RISK_ID = "risk_id";
	private static final String COLUMN_TRADE_NAME = "tradeName";
	private static final String COLUMN_FOR_ACCOUNT = "forAccount";
	private static final String COLUMN_ID_FOR_ACCT = "accountId";
	private static final String COLUMN_ID_FOR_TRADE = "tradeId";

	
	public MyDBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
		super(context, DATABASE_NAME, factory, (int) DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String query = "CREATE TABLE " + TABLE_NAME + "("
				+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ COLUMN_ACCNAME + " TEXT NOT NULL, " +
				COLUMN_BALANCE + " DOUBLE NOT NULL, " +
				COLUMN_GAINES + " DOUBLE NOT NULL, " +
				COLUMN_LOSSES + " DOUBLE NOT NULL " +
				");";
		db.execSQL(query);

		String query1 = "CREATE TABLE " + TABLE_NAME1 + "("
				+ COLUMN_TRADES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ COLUMN_COMPANY + " TEXT NOT NULL, " +
                COLUMN_ABSOLUTE_STOP + " DOUBLE NOT NULL, " +
				COLUMN_RISK_REWARD + " DOUBLE NOT NULL, " +
				COLUMN_ATR + " DOUBLE NOT NULL, " +
				COLUMN_RISK_UNIT + " DOUBLE NOT NULL, " +
				COLUMN_POSITION_SIZE + " DOUBLE NOT NULL, " +
				COLUMN_POSITION_VALUE + " DOUBLE NOT NULL " +
				");";
		db.execSQL(query1);

		String query2 = "CREATE TABLE " + TABLE_NAME2 + "("
				+ COLUMN_RISK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ COLUMN_ID_FOR_ACCT + " INTEGER REFERENCES TABLE_NAME(_id), "
				+ COLUMN_ID_FOR_TRADE + " INTEGER REFERENCES TABLE_NAME1(trade_id), " +
				COLUMN_TRADE_NAME + " TEXT NOT NULL, " +
				COLUMN_FOR_ACCOUNT + " TEXT NOT NULL " +
				");";
		db.execSQL(query2);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + TABLE_NAME1 + TABLE_NAME2);
		onCreate(db);
	}

	//Add new row to database
	public void addAccount(Accounts account){
		ContentValues values = new ContentValues();
		values.put(COLUMN_ACCNAME, account.get_acctName());
		values.put(String.valueOf(COLUMN_BALANCE), account.get_balance());
		values.put(String.valueOf(COLUMN_GAINES), account.get_gaines());
		values.put(String.valueOf(COLUMN_LOSSES), account.get_losses());
		SQLiteDatabase db = getWritableDatabase();
		db.insert(TABLE_NAME, null, values);
		db.close();
	}

	public void addTrade(Trades trade){
		ContentValues values = new ContentValues();
		values.put(COLUMN_COMPANY, trade.get_company());
        values.put(String.valueOf(COLUMN_ABSOLUTE_STOP), trade.get_absStop());
        values.put(String.valueOf(COLUMN_RISK_REWARD), trade.get_rrRatio());
        values.put(String.valueOf(COLUMN_ATR), trade.get_atr());
		values.put(String.valueOf(COLUMN_RISK_UNIT), trade.get_riskUnit());
		values.put(String.valueOf(COLUMN_POSITION_SIZE), trade.get_positionSize());
		values.put(String.valueOf(COLUMN_POSITION_VALUE), trade.get_positionValue());
		SQLiteDatabase db = getWritableDatabase();
		db.insert(TABLE_NAME1, null, values);
		db.close();
	}

	public void addRisk(AccountTrade risk){
		ContentValues values = new ContentValues();
		values.put(COLUMN_TRADE_NAME, risk.get_tradeName());
		values.put(COLUMN_FOR_ACCOUNT, risk.get_forAccount());
		values.put(String.valueOf(COLUMN_ID_FOR_ACCT), String.valueOf(risk.get_accountId()));
		values.put(String.valueOf(COLUMN_ID_FOR_TRADE), String.valueOf(risk.get_tradeId()));
		SQLiteDatabase db = getWritableDatabase();
		db.insert(TABLE_NAME2, null, values);
		db.close();
	}

	public void deleteAccount(String _id){
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE _id = " + _id);
	}

	public void deleteTrade(String trade_id){
		SQLiteDatabase db = getReadableDatabase();
		db.execSQL("DELETE FROM " + TABLE_NAME1 + " WHERE trade_id = " + trade_id);
	}

	public void deleteRiskMan(String risk_id){
		SQLiteDatabase db = getReadableDatabase();
		db.execSQL("DELETE FROM " + TABLE_NAME2 + " WHERE risk_id = " + risk_id);
	}

	//Display data
	public Cursor displayData(){
		SQLiteDatabase db = this.getWritableDatabase();
		String query = "SELECT * FROM " + TABLE_NAME;
		
		//Cursor point to a location in the data
		Cursor result = db.rawQuery(query, null);

		//Move to first row in the data
		result.moveToFirst();

		db.close();
		return result;
	}

	public Cursor displayTrade(){
		SQLiteDatabase db = this.getReadableDatabase();
		String query = "SELECT * FROM " + TABLE_NAME1;

		Cursor result = db.rawQuery(query, null);

		result.moveToFirst();

		db.close();
		return result;
	}

	public Cursor displayRisk(){
		SQLiteDatabase db = this.getReadableDatabase();
		String query = "SELECT * FROM " + TABLE_NAME2;

		Cursor result = db.rawQuery(query, null);

		result.moveToFirst();

		db.close();
		return result;
	}

	//Update row
	public boolean updateAccount(String _id, String accountname, String balance,
                              String gaines, String losses){
	    SQLiteDatabase db = getWritableDatabase();

		ContentValues newValues = new ContentValues();
		newValues.put(COLUMN_ID, _id);
		newValues.put(COLUMN_ACCNAME, accountname);
		newValues.put(COLUMN_BALANCE, balance);
		newValues.put(COLUMN_GAINES, gaines);
		newValues.put(COLUMN_LOSSES, losses);

		//Insert values into database record
		db.update(TABLE_NAME, newValues, "_id = ?", new String[]{ _id });
		db.close();
		return true;
	}

    //Find Account record by ID
    public Cursor getRecordByID(String _id){
		SQLiteDatabase db = this.getWritableDatabase();
		String query = " SELECT * FROM " + TABLE_NAME + " WHERE _id = " + _id;

		//Cursor point to a location in the data
		Cursor result = db.rawQuery(query, null);

		//Move to first row in the data
		result.moveToFirst();

		db.close();
		return result;
    }

    //Find record by Name from Accounts table
	public Cursor getRecordByNameFromAccts(String accountname){
		SQLiteDatabase db = this.getWritableDatabase();
		String query = "SELECT _id FROM " + TABLE_NAME + " WHERE accountname = '"+accountname+"'";

		Cursor result = db.rawQuery(query, null);

		result.moveToFirst();

		db.close();
		return result;
	}

	//Find record by Name from trades table
	public Cursor getRecordByCompanyFromTrades(String company){
		SQLiteDatabase db = this.getWritableDatabase();
		String query = "SELECT * FROM " + TABLE_NAME1 + " WHERE company = '"+company+"'" ;

		Cursor result = db.rawQuery(query, null);

		result.moveToFirst();
		db.close();
		return result;
	}

	public Cursor getRecordByIDFromTrades(String trade_id){
	    SQLiteDatabase db = this.getWritableDatabase();
	    String query = "SELECT * FROM " + TABLE_NAME1 + " WHERE trade_id = '"+trade_id+"'";

	    Cursor result = db.rawQuery(query,null);

	    result.moveToFirst();
	    db.close();
	    return result;
    }

    public Cursor getRecordByTradeFromRisk(String tradeName){
	    SQLiteDatabase db = this.getWritableDatabase();
	    String query = "SELECT * FROM " + TABLE_NAME2 + " WHERE tradeName = '"+tradeName+"'";

	    Cursor result = db.rawQuery(query, null);

	    result.moveToFirst();
	    db.close();
	    return result;
    }
}
