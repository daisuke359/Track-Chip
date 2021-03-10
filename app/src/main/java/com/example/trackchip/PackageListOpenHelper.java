package com.example.trackchip;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PackageListOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = PackageListOpenHelper.class.getSimpleName();
    // has to be 1 first time or app will crash
    private static final int DATABASE_VERSION = 1;
    private static final String PACKAGE_LIST_TABLE = "PACKAGES";
    private static final String DATABASE_NAME = "PROJECT";
    // Column names...
    public static final String KEY_ID = "TRACKING_ID";
    public static final String KEY_ESTDATE = "EST_DATE";
    public static final String KEY_ORIGIN = "ORIGIN";
    public static final String KEY_DCLOCATION = "DC_LOCATION";
    public static final String KEY_DESTINATION = "DESTINATION";
    public static final String KEY_STATUS = "STATUS";
    // ... and a string array of columns.
    private static final String[] COLUMNS = {KEY_ID, KEY_ESTDATE, KEY_ORIGIN, KEY_DCLOCATION, KEY_DESTINATION, KEY_STATUS};

    // Build the SQL query that creates the table.
    private static final String PACKAGE_LIST_TABLE_CREATE =
            "CREATE TABLE " + PACKAGE_LIST_TABLE + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY, " +
                    KEY_ESTDATE + " DATE, " +
                    KEY_ORIGIN + " TEXT, " +
                    KEY_DCLOCATION + " TEXT, " +
                    KEY_DESTINATION + " TEXT, " +
                    KEY_STATUS + " TEXT);";

    private SQLiteDatabase mWritableDB;
    private SQLiteDatabase mReadableDB;

    private void fillDatabaseWithData(SQLiteDatabase db) {
        int[] ids = {1234, 2341, 3412, 4123};
        String[] estdates = {"11/21/2020",
                "12/20/2020",
                "1/20/2021",
                "1/25/2021"
        };
        String origin = "123 Main Street, Vancouver";
        String dcCenter = "Vancouver Sort Center";
        String destination = "1118 Hardy Street, Kelowna";
        String[] status = {"DELIVERED",
                "IN_TRANSIT",
                "IN_TRANSIT",
                "IN_TRANSIT"
        };

        // Create a container for the data.
        ContentValues values = new ContentValues();
        for (int i = 0; i < ids.length; i++) {
            // Put column/value pairs into the container.
            // put() overrides existing values.
            values.put(KEY_ID, ids[i]);
            values.put(KEY_ESTDATE, estdates[i]);
            values.put(KEY_ORIGIN, origin);
            values.put(KEY_DCLOCATION, dcCenter);
            values.put(KEY_DESTINATION, destination);
            values.put(KEY_STATUS, status[i]);

            db.insert(PACKAGE_LIST_TABLE, null, values);
        }
    }

    public Package getbyId(int id) {
        String query = "SELECT * FROM " + PACKAGE_LIST_TABLE +
                " WHERE " + KEY_ID + " = " + id;

        Cursor cursor = null;
        Package entry = new Package();

        if (mReadableDB == null) {
            mReadableDB = getReadableDatabase();
        }
        cursor = mReadableDB.rawQuery(query, null);

        if(cursor != null && cursor.getCount() >0) {
            try {
                cursor.moveToFirst();
                entry.setID(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                //SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                //Date date = format.parse(cursor.getString(cursor.getColumnIndex(KEY_ESTDATE)));
                entry.setEst_date(cursor.getString(cursor.getColumnIndex(KEY_ESTDATE)));
                entry.setOrigin(cursor.getString(cursor.getColumnIndex(KEY_ORIGIN)));
                entry.setDc_location(cursor.getString(cursor.getColumnIndex(KEY_DCLOCATION)));
                entry.setDestination(cursor.getString(cursor.getColumnIndex(KEY_DESTINATION)));
                entry.setStatus(cursor.getString(cursor.getColumnIndex(KEY_STATUS)));

            } catch (Exception e) {
                Log.d(TAG, "EXCEPTION! " + e);
            } finally {
                cursor.close();
                return entry;
            }
        }
        else {
            return null;
        }
    }


    public PackageListOpenHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(PACKAGE_LIST_TABLE_CREATE);
            fillDatabaseWithData(sqLiteDatabase);
        } catch (Exception e) {

        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.w(PackageListOpenHelper.class.getName(),
                "Upgrading database from version " + i + " to "
                        + i1 + ", which will destroy all old data");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PACKAGE_LIST_TABLE);
        onCreate(sqLiteDatabase);
    }

    public long insert(Package pack) {
        long newId = 0;
        ContentValues values = new ContentValues();
        values.put(KEY_ID, pack.getID());
        values.put(KEY_ESTDATE, pack.getEst_date().toString());
        values.put(KEY_ORIGIN,pack.getOrigin());
        values.put(KEY_DCLOCATION, pack.getDc_location());
        values.put(KEY_DESTINATION, pack.getDestination());
        values.put(KEY_STATUS, pack.getStatus());
        try {
            if (mWritableDB == null) {
                mWritableDB = getWritableDatabase();
            }
            newId = mWritableDB.insert(PACKAGE_LIST_TABLE, null, values);
        } catch (Exception e) {
            Log.d(TAG, "INSERT EXCEPTION! " + e.getMessage());
        }
        return newId;
    }


}

