package local.firespace.Pizzeria.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import local.firespace.Pizzeria.Order;

import java.util.ArrayList;


public class OrdersDatabase {
	public static final String KEY_ID = "id";
	public static final String KEY_PIZZERIA_NAME = "pizzeria_name";
	public static final String KEY_COURIER_NUMBER = "cour_numb";
	public static final String KEY_PIZZA_NAME = "name";
	public static final String KEY_ORDER_TIME = "time";
	public static final String KEY_TELEPHONE_NUMBER = "telephone";
	public static final String KEY_ORDER_SPEED = "speed";

	private static final String DATABASE_NAME = "pizzeriasdb";
	private static final Integer DATABASE_VERSION = 1;
	private static final String CREATE_TABLE = "CREATE TABLE " + DATABASE_NAME + " (" + KEY_ID +
			" INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_PIZZERIA_NAME + " STRING NOT NULL, " +
			KEY_COURIER_NUMBER + " INTEGER, " + KEY_PIZZA_NAME + " STRING, " + KEY_ORDER_TIME + " STRING, " +
			KEY_TELEPHONE_NUMBER + " STRING, " + KEY_ORDER_SPEED + " STRING); ";
	private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + DATABASE_NAME;

	DBHelper dbHelper;
	SQLiteDatabase database;

	private class DBHelper extends SQLiteOpenHelper {

		DBHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_TABLE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL(DROP_TABLE);
			onCreate(db);
		}
	}

	public OrdersDatabase(Context context) {
		dbHelper = new DBHelper(context);
	}

	public void close() {
		dbHelper.close();
	}

	public OrdersDatabase open() {
		database = dbHelper.getWritableDatabase();
		return this;
	}

	public void reset() {
		dbHelper.onUpgrade(database, 1, 1);
	}

	public ArrayList<Order> getOrders(String pizzeriaName) {
		Cursor cursor = database.query(DATABASE_NAME, null, KEY_PIZZERIA_NAME + " = ?", new String[] {pizzeriaName}, null, null, null);
		ArrayList<Order> response = new ArrayList<Order>();
		while (cursor.moveToNext()) {
			response.add(new Order(
					cursor.getString(cursor.getColumnIndex(KEY_PIZZA_NAME)),
					cursor.getInt(cursor.getColumnIndex(KEY_COURIER_NUMBER)),
					cursor.getString(cursor.getColumnIndex(KEY_ORDER_TIME)),
					cursor.getString(cursor.getColumnIndex(KEY_TELEPHONE_NUMBER)),
					cursor.getString(cursor.getColumnIndex(KEY_ORDER_SPEED)),
					cursor.getInt(cursor.getColumnIndex(KEY_ID))
			));
		}

		return response;
	}

	public Order getOrderByID(String pizzeriaName, Integer ID) { // bad code because I don't know, how to query by two parameters
		Cursor cursor = database.query(DATABASE_NAME, null, KEY_PIZZERIA_NAME + " = ?", new String[] {pizzeriaName}, null, null, null);
		while (cursor.moveToNext()) {
			if (cursor.getInt(cursor.getColumnIndex(KEY_ID)) == ID) {
				return new Order(
						cursor.getString(cursor.getColumnIndex(KEY_PIZZA_NAME)),
						cursor.getInt(cursor.getColumnIndex(KEY_COURIER_NUMBER)),
						cursor.getString(cursor.getColumnIndex(KEY_ORDER_TIME)),
						cursor.getString(cursor.getColumnIndex(KEY_TELEPHONE_NUMBER)),
						cursor.getString(cursor.getColumnIndex(KEY_ORDER_SPEED))
				);
			}
		}

		return null;
	}

	public void addOrder(Order order, String pizzeriaName) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(KEY_COURIER_NUMBER, order.getCourierNumb());
		contentValues.put(KEY_ORDER_SPEED, order.getOrderSpeed());
		contentValues.put(KEY_ORDER_TIME, order.getOrderTime());
		contentValues.put(KEY_PIZZA_NAME, order.getPizzaName());
		contentValues.put(KEY_PIZZERIA_NAME, pizzeriaName);
		contentValues.put(KEY_TELEPHONE_NUMBER, order.getTelephoneNumber());
		database.insert(DATABASE_NAME, null, contentValues);
	}
}
