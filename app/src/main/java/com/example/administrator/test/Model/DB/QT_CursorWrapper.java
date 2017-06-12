package com.example.administrator.test.Model.DB;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.administrator.test.Model.QT;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Administrator on 2017-06-03.
 */

public class QT_CursorWrapper extends CursorWrapper {

    public QT_CursorWrapper(Cursor cursor){
        super(cursor);
    }

    public QT getQtFromCursor(){

        String uuid = getString(getColumnIndex( DbSchema.QT_Table.QT_Cols.QT_UUID));
        long date = getLong(getColumnIndex(DbSchema.QT_Table.QT_Cols.QT_DATE));
        String week = getString(getColumnIndex(DbSchema.QT_Table.QT_Cols.WEEK));
        String quietTime = getString(getColumnIndex(DbSchema.QT_Table.QT_Cols.QT));
        String thanks = getString(getColumnIndex(DbSchema.QT_Table.QT_Cols.THANKS));
        String prayer = getString(getColumnIndex(DbSchema.QT_Table.QT_Cols.PRAYER));
        String journal = getString(getColumnIndex(DbSchema.QT_Table.QT_Cols.JOURNAL));

        QT qt = new QT();
        qt.setUuid(UUID.fromString(uuid));
        qt.setDate(new Date(date));
        qt.setWeek(week);
        qt.setQt(quietTime);
        qt.setThanks(thanks);
        qt.setPrayer(prayer);
        qt.setJournal(journal);

        return qt;
    }


}
