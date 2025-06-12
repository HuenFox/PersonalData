package tw.frzfox.personaldata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private final static int DBVersion = 1;
    private final static String DBName = "PersonalData.db";
    private final static String TableName = "PersonalData";

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQLTable = "CREATE TABLE IF NOT EXISTS " + TableName + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Account TEXT NOT NULL, " +
                "Password TEXT NOT NULL, " +
                "Zodiac TEXT NOT NULL, " +
                "BloodType TEXT NOT NULL," +
                "CreateTime TEXT NOT NULL," +
                "UpdateTime TEXT NOT NULL" +
                ");";
        sqLiteDatabase.execSQL(SQLTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addData(String Account, String Password, String Zodiac, String BloodType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Account", Account);
        values.put("Password", Password);
        values.put("Zodiac", Zodiac);
        values.put("BloodType", BloodType);
        values.put("CreateTime", System.currentTimeMillis());
        values.put("UpdateTime", System.currentTimeMillis());
        db.insert(TableName, null, values);
        db.close();
    }

    public ArrayList<Personal> getData(String Account) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        if (Account == null || Account.equals("") || Account.equals("0")) {
            c = db.rawQuery("SELECT * FROM " + TableName, null);
        } else {
            c = db.rawQuery("SELECT * FROM " + TableName + " WHERE Account = '" + Account + "'", null);
        }
        ArrayList<Personal> personalArray = new ArrayList<>();
        while (c.moveToNext()) {
            String text = c.getString(1);
            personalArray.add(new Personal(c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6)));
        }
        return personalArray;
    }

    public ArrayList<String> getPwd(String Account) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT Password FROM " + TableName + " WHERE Account = '" + Account + "'", null);

        ArrayList<String> textArray = new ArrayList<>();
        while (c.moveToNext()) {
            String text = c.getString(0);
            textArray.add(text);
        }
        return textArray;
    }

    public void updateData(String Account, String Password, String Zodiac, String BloodType) {
        SQLiteDatabase db = this.getWritableDatabase();
        //沒有密碼就是更新一般資料，有密碼就一起更新密碼
        Log.e("sql = ", "UPDATE " + TableName + " SET Zodiac = '" + Zodiac + "', BloodType = '" + BloodType + "', UpdateTime = '" + System.currentTimeMillis() + "' WHERE Account = '" + Account + "'");
        if (Password == null || Password.equals("")) {
            db.execSQL("UPDATE " + TableName + " SET Zodiac = '" + Zodiac + "', BloodType = '" + BloodType + "', UpdateTime = '" + System.currentTimeMillis() + "' WHERE Account = '" + Account + "'");
        } else {
            db.execSQL("UPDATE " + TableName + " SET Password = '" + Password + "', Zodiac = '" + Zodiac + "', BloodType = '" + BloodType + "', UpdateTime = '" + System.currentTimeMillis() + "' WHERE Account = '" + Account + "'");
        }
    }

    public void deleteData(String Account) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TableName + " WHERE Account = '" + Account + "'");
    }
}
