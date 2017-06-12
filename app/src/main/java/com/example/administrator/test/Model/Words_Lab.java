package com.example.administrator.test.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.test.Model.DB.DBHelper;
import com.example.administrator.test.Model.DB.DbSchema;
import com.example.administrator.test.Model.DB.Words_CursorWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Words_Lab {

    static Words_Lab sWordsLAb;
    Context context;

    SQLiteDatabase db;
    DBHelper helper;

    public Words_Lab(Context context){
        this.context = context;
        helper = DBHelper.getInstance(context);
        db = helper.getWritableDatabase();
    }

    public static Words_Lab getWordsLAB(Context context){
        if(sWordsLAb == null){
            sWordsLAb = new Words_Lab(context);
        }
        return sWordsLAb;
    }

    public void addWords(Words words){
        ContentValues values = getContentValues(words);
        db.insert(DbSchema.Words_Table.WORDS_TABLE_NAME, null, values);
    }

    public ContentValues getContentValues(Words words){
        ContentValues values = new ContentValues();
        values.put(DbSchema.Words_Table.Words_Cols.WORDS_UUID , words.getUuid().toString());
        values.put(DbSchema.Words_Table.Words_Cols.WORDS_DATE , words.getDate().getTime());
        values.put(DbSchema.Words_Table.Words_Cols.TITLE , words.getTitle());
        values.put(DbSchema.Words_Table.Words_Cols.BOOK , words.getBook());
//        values.put(DbSchema.Words_Table.Words_Cols.CHAP , words.getChap());
//        values.put(DbSchema.Words_Table.Words_Cols.PHRASE , words.getPhrase());
        values.put(DbSchema.Words_Table.Words_Cols.WORDS , words.getWords());
        values.put(DbSchema.Words_Table.Words_Cols.SUMMARY , words.getSummary());
        return values;
    }

    public void Words_Update(Words words){
        ContentValues values = getContentValues(words);
        String whereClause = DbSchema.Words_Table.WORDS_TABLE_NAME + " = ?";
        String[] whereArgs = { words.getUuid().toString() };
        db.update(DbSchema.Words_Table.WORDS_TABLE_NAME, values, whereClause, whereArgs);
    }

    public void Words_Delete(Words words){
        String whereClause = DbSchema.Words_Table.WORDS_TABLE_NAME + " = ?";
        String[] whereArgs = { words.getUuid().toString() };
        db.delete(DbSchema.Words_Table.WORDS_TABLE_NAME, whereClause, whereArgs);
    }

    public Words_CursorWrapper query(String whereClause, String[] whereArgs){
        Cursor cursor = db.query(DbSchema.Words_Table.WORDS_TABLE_NAME, null, whereClause, whereArgs, null, null, null);
        return new Words_CursorWrapper(cursor);
    }

    public Words getQT(UUID uuid){
        String whereClause = DbSchema.Words_Table.WORDS_TABLE_NAME + " = ?";
        String[] whereArgs = { uuid.toString() };
        Words_CursorWrapper cursor = query(whereClause, whereArgs);

        try {

            if (cursor == null) {
                return null;
            }

            cursor.moveToFirst();
           Words words = cursor.getWordsFromCursor();

            return words;
        } finally {
            cursor.close();
        }
    }

    public List<Words> getWords(){
        List<Words> datas = new ArrayList<>();

        Words_CursorWrapper cursor = query(null, null);

        try {
            while (cursor.moveToNext()) {
                datas.add(cursor.getWordsFromCursor());
            }
        } finally {
            cursor.close();
        }
        return datas;
    }



}
