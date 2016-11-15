package ru.arcsinus.salesblast;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ru.arcsinus.salesblast.model.Item;

/**
 * Created by Andrei on 13.11.2016.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static String TAG = "log";
    private SQLiteDatabase db;
    private Cursor cursor;

    public DBHelper(Context context) {
        super(context, "SalesBlastDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table items ("
                        + "id integer primary key,"
                        + "header text,"
                        + "short_text text,"
                        + "full_text text,"
                        + "publish_datetime text,"
                        + "change_datetime text,"
                        + "content_type_id integer,"
                        + "type text,"
                        + "link text,"
                        + "start_datetime text,"
                        + "end_datetime text,"
                        + "img_preview_url text,"
                        + "img_url text"
                        + ");");

        Log.d(TAG, "Local database created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public void insertItems(final List<Item> itemList){
                db = getWritableDatabase();
                db.beginTransaction();
                try{
                    final String sql = "insert into " + "items" + "("
                            + " id,"
                            + " header,"
                            + " short_text,"
                            + " full_text,"
                            + " publish_datetime,"
                            + " change_datetime,"
                            + " content_type_id,"
                            + " type,"
                            + " link,"
                            + " start_datetime,"
                            + " end_datetime,"
                            + " img_preview_url,"
                            + " img_url"
                            + " ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? );";


                    SQLiteStatement statement = db.compileStatement(sql);

                    long rowId = 0;

                    for (Item item : itemList){

                        statement.bindLong(1, item.getId());
                        statement.bindString(2, item.getHeader());
                        statement.bindString(3, item.getShortText());
                        statement.bindString(4, item.getFullText());
                        statement.bindString(5, item.getPublishTime());
                        statement.bindString(6, item.getChangeDateTime());
                        statement.bindLong(7, item.getContentTypeId());
                        statement.bindString(8, item.getType());
                        statement.bindString(9, item.getLink());
                        statement.bindString(10, item.getStartDateTime());
                        statement.bindString(11, item.getEndDateTime());
                        statement.bindString(12, (item.getImagePreviewUrl() != null && !item.getImagePreviewUrl().isEmpty() ? item.getImagePreviewUrl() : ""));
                        statement.bindString(13, (item.getImageUrl() != null && !item.getImageUrl().isEmpty() ? item.getImageUrl() : ""));

                        rowId = statement.executeInsert();
                        statement.clearBindings();
                    }
                    //Log.d(TAG, "DBHelper: number of items inserted: " + rowId);
                    db.setTransactionSuccessful();

                } finally {
                    db.endTransaction();
                }
        this.close();
    }

    public void insertItem(Item item){
        db = getWritableDatabase();
        db.beginTransaction();
        try{
            final String sql = "insert into " + "items" + "("
                    + " id,"
                    + " header,"
                    + " short_text,"
                    + " full_text,"
                    + " publish_datetime,"
                    + " change_datetime,"
                    + " content_type_id,"
                    + " type,"
                    + " link,"
                    + " start_datetime,"
                    + " end_datetime,"
                    + " img_preview_url,"
                    + " img_url"
                    + " ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? );";


            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindLong(1, item.getId());
            statement.bindString(2, item.getHeader());
            statement.bindString(3, item.getShortText());
            statement.bindString(4, item.getFullText());
            statement.bindString(5, item.getPublishTime());
            statement.bindString(6, item.getChangeDateTime());
            statement.bindLong(7, item.getContentTypeId());
            statement.bindString(8, item.getType());
            statement.bindString(9, item.getLink());
            statement.bindString(10, item.getStartDateTime());
            statement.bindString(11, item.getEndDateTime());
            statement.bindString(12, item.getImagePreviewUrl());
            statement.bindString(13, item.getImageUrl());
            statement.executeInsert();
            statement.clearBindings();

            db.setTransactionSuccessful();

        } finally {
            db.endTransaction();
        }
        this.close();
    }

    public ArrayList<Item> getAllItems(){
        //Log.d(TAG, "DBHelper.getAllItems");
        ArrayList<Item> result = new ArrayList<>();
        db = getWritableDatabase();
        db.beginTransaction();

        try{
            Cursor cursor = db.query("items", null, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                int idColIndex = cursor.getColumnIndex("id");
                int headerColIndex = cursor.getColumnIndex("header");
                int shortTextColIndex = cursor.getColumnIndex("short_text");
                int fullTextColIndex = cursor.getColumnIndex("full_text");
                int publishDatetimeColIndex = cursor.getColumnIndex("publish_datetime");
                int changeDatetimeColIndex = cursor.getColumnIndex("change_datetime");
                int contentTypeIdColIndex = cursor.getColumnIndex("content_type_id");
                int typeColIndex = cursor.getColumnIndex("type");
                int linkColIndex = cursor.getColumnIndex("link");
                int startDatetimeColIndex = cursor.getColumnIndex("start_datetime");
                int endDatetimeColIndex = cursor.getColumnIndex("end_datetime");
                int imgPreviewUrlColIndex = cursor.getColumnIndex("img_preview_url");
                int imgUrlColIndex = cursor.getColumnIndex("img_url");

                do {

                    result.add(new Item(
                                    cursor.getLong(idColIndex),
                                    cursor.getString(headerColIndex),
                                    cursor.getString(shortTextColIndex),
                                    cursor.getString(fullTextColIndex),
                                    cursor.getString(publishDatetimeColIndex),
                                    cursor.getString(changeDatetimeColIndex),
                                    (byte)cursor.getInt(contentTypeIdColIndex),
                                    cursor.getString(typeColIndex),
                                    cursor.getString(linkColIndex),
                                    cursor.getString(startDatetimeColIndex),
                                    cursor.getString(endDatetimeColIndex),
                                    cursor.getString(imgPreviewUrlColIndex),
                                    cursor.getString(imgUrlColIndex)
                            )
                    );


                } while (cursor.moveToNext());
            }
            cursor.close();
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        this.close();
        return result;
    }

    public Item getById(long id){
        Item item = null;
        db = getWritableDatabase();
        db.beginTransaction();

        try {
            Cursor cursor = db.query("items", null, "id=?", new String[]{""+id}, null, null, null);
            if (cursor.moveToFirst()) {

                int idColIndex = cursor.getColumnIndex("id");
                int headerColIndex = cursor.getColumnIndex("header");
                int shortTextColIndex = cursor.getColumnIndex("short_text");
                int fullTextColIndex = cursor.getColumnIndex("full_text");
                int publishDatetimeColIndex = cursor.getColumnIndex("publish_datetime");
                int changeDatetimeColIndex = cursor.getColumnIndex("change_datetime");
                int contentTypeIdColIndex = cursor.getColumnIndex("content_type_id");
                int typeColIndex = cursor.getColumnIndex("type");
                int linkColIndex = cursor.getColumnIndex("link");
                int startDatetimeColIndex = cursor.getColumnIndex("start_datetime");
                int endDatetimeColIndex = cursor.getColumnIndex("end_datetime");
                int imgPreviewUrlColIndex = cursor.getColumnIndex("img_preview_url");
                int imgUrlColIndex = cursor.getColumnIndex("img_url");

                item = new Item(
                        cursor.getLong(idColIndex),
                        cursor.getString(headerColIndex),
                        cursor.getString(shortTextColIndex),
                        cursor.getString(fullTextColIndex),
                        cursor.getString(publishDatetimeColIndex),
                        cursor.getString(changeDatetimeColIndex),
                        (byte)cursor.getInt(contentTypeIdColIndex),
                        cursor.getString(typeColIndex),
                        cursor.getString(linkColIndex),
                        cursor.getString(startDatetimeColIndex),
                        cursor.getString(endDatetimeColIndex),
                        cursor.getString(imgPreviewUrlColIndex),
                        cursor.getString(imgUrlColIndex)
                );
            }
            cursor.close();
            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
        this.close();
        return item;
    }

    public void updateItem(Item item){
        db = getWritableDatabase();
        db.beginTransaction();
        try{
            final String sql = "update " + "items" + " set"
                    + " header=?,"
                    + " full_text=?,"
                    + " short_text=?,"
                    + " publish_datetim=?,"
                    + " change_datetime=?,"
                    + " content_type_id=?,"
                    + " type=?,"
                    + " link=?,"
                    + " start_datetim=?,"
                    + " end_datetime=?,"
                    + " img_preview_url=?,"
                    + " img_url=?"
                    + " where id=?;";

            SQLiteStatement statement = db.compileStatement(sql);

            statement.bindLong(13, item.getId());
            statement.bindString(1, item.getHeader());
            statement.bindString(2, item.getShortText());
            statement.bindString(3, item.getFullText());
            statement.bindString(4, item.getPublishTime());
            statement.bindString(5, item.getChangeDateTime());
            statement.bindLong(6, item.getContentTypeId());
            statement.bindString(7, item.getType());
            statement.bindString(8, item.getLink());
            statement.bindString(9, item.getStartDateTime());
            statement.bindString(10, item.getEndDateTime());
            statement.bindString(11, item.getImagePreviewUrl());
            statement.bindString(12, item.getImageUrl());

            statement.executeUpdateDelete();
            statement.clearBindings();
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            this.close();
        }
    }



    public void deleteItem(long itemId){
        db = getWritableDatabase();
        db.beginTransaction();
        try{
            db.delete("items", "id=?", new String[]{"" + itemId});
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
    }

    public boolean isDatabaseEmpty(){
        boolean isDatabaseEmpty = true;
        db = getWritableDatabase();
        db.beginTransaction();

        try {
            cursor = db.query("items", null, null, null, null, null, null);

            if(cursor != null) {
                //Log.d(TAG, "Number of places in db = " + cursor.getCount());
                if (cursor.moveToFirst()) isDatabaseEmpty = !isDatabaseEmpty;
            }

            db.setTransactionSuccessful();

        } finally {
            db.endTransaction();
            cursor.close();
            this.close();
        }

        return isDatabaseEmpty;
    }

    public void clearDatabase(){
        db = getWritableDatabase();
        db.beginTransaction();
        try{
            db.delete("items", null, null);
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
    }
}
