package com.example.administrator.test.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.test.Model.DB.DBHelper;
import com.example.administrator.test.Model.DB.DbSchema;
import com.example.administrator.test.Model.DB.QT_CursorWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2017-06-03.
 */

public class QT_Lab {

    static QT_Lab sQTLAb;
    Context context;

    SQLiteDatabase db;
    DBHelper helper;

    public QT_Lab(Context context){
        this.context = context;
        helper = DBHelper.getInstance(context);
        db = helper.getWritableDatabase();
    }

    public static QT_Lab getQTLAB(Context context){
        if(sQTLAb == null){
            sQTLAb = new QT_Lab(context);
        }
        return sQTLAb;
    }

    public void addQT(QT qt){
        ContentValues values = getContentValues(qt);
        db.insert(DbSchema.QT_Table.QT_TABLE_NAME, null, values);
    }

    public ContentValues getContentValues(QT qt){
        ContentValues values = new ContentValues();
        values.put(DbSchema.QT_Table.QT_Cols.QT_UUID , qt.getUuid().toString());
        values.put(DbSchema.QT_Table.QT_Cols.QT_DATE , qt.getDate().getTime());
        values.put(DbSchema.QT_Table.QT_Cols.WEEK , qt.getWeek());
        values.put(DbSchema.QT_Table.QT_Cols.QT , qt.getQt());
        values.put(DbSchema.QT_Table.QT_Cols.THANKS , qt.getThanks());
        values.put(DbSchema.QT_Table.QT_Cols.PRAYER , qt.getPrayer());
        values.put(DbSchema.QT_Table.QT_Cols.JOURNAL , qt.getJournal());
        return values;
    }

    public void QT_Update(QT qt){
        ContentValues values = getContentValues(qt);
        String whereClause = DbSchema.QT_Table.QT_Cols.QT_UUID + " = ?";
        String[] whereArgs = { qt.getUuid().toString() };
        db.update(DbSchema.QT_Table.QT_TABLE_NAME, values, whereClause, whereArgs);
    }

    public void QT_Delete(QT qt){
        String whereClause = DbSchema.QT_Table.QT_Cols.QT_UUID + " = ?";
        String[] whereArgs = { qt.getUuid().toString() };
        db.delete(DbSchema.QT_Table.QT_TABLE_NAME, whereClause, whereArgs);
    }

    public QT_CursorWrapper query(String whereClause, String[] whereArgs){
        Cursor cursor = db.query(DbSchema.QT_Table.QT_TABLE_NAME, null, whereClause, whereArgs, null, null, null);
        return new QT_CursorWrapper(cursor);
    }

    public QT getQT(UUID uuid){
        String whereClause = DbSchema.QT_Table.QT_Cols.QT_UUID + " = ?";
        String[] whereArgs = { uuid.toString() };
        QT_CursorWrapper cursor = query(whereClause, whereArgs);

        try {

            if (cursor == null) {
                return null;
            }

            cursor.moveToFirst();
            QT qt = cursor.getQtFromCursor();

            return qt;
        } finally {
            cursor.close();
        }
    }

    public List<QT> getQTs(){
        List<QT> datas = new ArrayList<>();

        QT_CursorWrapper cursor = query(null, null);

        try {
            while (cursor.moveToNext()) {
                datas.add(cursor.getQtFromCursor());
            }
        } finally {
            cursor.close();
        }
        return datas;
    }


}
