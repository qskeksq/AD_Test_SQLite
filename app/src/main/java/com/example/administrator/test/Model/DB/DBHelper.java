package com.example.administrator.test.Model.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {

    // 아하!! 사실 이렇게 한 곳에서 테이블을 관리할 수 있었군!
    // 처음 만들 때 테이블을 한꺼번에 만들어 놨어야 한 것이야.
    static DBHelper helper;

    // 헬퍼는 싱글턴으로 만드는 것이 조금이라도 리소스를 절약할 수 있다.
    public static DBHelper getInstance(Context context){
        if(helper == null) {
            helper = new DBHelper(context);
        }
        return helper;
    }

    private DBHelper(Context context) {
        super(context, DbSchema.DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String qt_sql = "CREATE TABLE "+ DbSchema.QT_Table.QT_TABLE_NAME
                + "( _id integer primary key autoincrement, "
                + DbSchema.QT_Table.QT_Cols.QT_UUID + ", "
                + DbSchema.QT_Table.QT_Cols.QT_DATE + ", "
                + DbSchema.QT_Table.QT_Cols.WEEK + ", "
                + DbSchema.QT_Table.QT_Cols.QT + ", "
                + DbSchema.QT_Table.QT_Cols.THANKS + ", "
                + DbSchema.QT_Table.QT_Cols.PRAYER + ", "
                + DbSchema.QT_Table.QT_Cols.JOURNAL + ")";
        String words_sql = "CREATE TABLE "+ DbSchema.Words_Table.WORDS_TABLE_NAME
                + "( _id integer primary key autoincrement, "
                + DbSchema.Words_Table.Words_Cols.WORDS_UUID + ", "
                + DbSchema.Words_Table.Words_Cols.WORDS_DATE + ", "
                + DbSchema.Words_Table.Words_Cols.TITLE + ", "
                + DbSchema.Words_Table.Words_Cols.BOOK + ", "
//                안 지워두고 놔둔 이유는 만들 때 신중하기 위해서. 서비스가 출시된 이후에는 아주 복잡한 상황이 발생한다.
//                + DbSchema.Words_Table.Words_Cols.CHAP + ", "
//                + DbSchema.Words_Table.Words_Cols.PHRASE + ", "
                + DbSchema.Words_Table.Words_Cols.WORDS + ", "
                + DbSchema.Words_Table.Words_Cols.SUMMARY + ")";
        db.execSQL(qt_sql);
        db.execSQL(words_sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
