package com.verizon.mvyas.verizonproject;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.verizon.mvyas.tags_asc_dsc_libs.data.TagCounts;


/**
 * Created by manisha on 7/22/15.
 */


public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName1.db";
    public static final String TABLE_NAME = "tb_tags";

    public static final String COLUMN_TAG = "tag";

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(" + COLUMN_TAG + " text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);

        onCreate(db);
    }

    public boolean insertTAG (String tag)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TAG, tag);
        db.insert(TABLE_NAME, null, contentValues);
        Log.d("Test", "insert Success");
        return true;
    }

    public boolean duplicateTag1000Time(String tag)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TAG, tag);

        for(int i =0 ; i<1000;i++)
            db.insert(TABLE_NAME, null, contentValues);
        Log.d("Test", "insert Success");
        return true;
    }

    public Cursor getData(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TABLE_NAME+" where "+COLUMN_TAG+"="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }

    public boolean updateTag (String oldTag,String newTag)    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TAG, newTag);
        db.update(TABLE_NAME, contentValues, COLUMN_TAG+"= ? ", new String[] {oldTag} );
        return true;
    }

    public ArrayList<TagCounts> getTagsGroupByTagCount(){
        ArrayList<TagCounts> tag_map = new ArrayList<TagCounts> ();

        SQLiteDatabase db = this.getReadableDatabase();
        String queryStr = "select COUNT("+COLUMN_TAG+") ,"+COLUMN_TAG +
                          " from " +TABLE_NAME +
                          " group by " +COLUMN_TAG;
        Cursor res =  db.rawQuery( queryStr, null );
        res.moveToFirst();

        int i=0;
        while(res.isAfterLast() == false){
            TagCounts tag_with_count = new TagCounts(res.getString(res.getColumnIndex(COLUMN_TAG)),
                               res.getInt(res.getColumnIndex("COUNT(" + COLUMN_TAG + ")")));
            tag_map.add(tag_with_count);
            Log.d("While reading = " + (i++), "" + tag_with_count.getTag());
            res.moveToNext();
        }
        return tag_map;
    }
    public ArrayList<String> getAllTAGs()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TABLE_NAME, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(COLUMN_TAG)));
            res.moveToNext();
        }
        return array_list;
    }
}