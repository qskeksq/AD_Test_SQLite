package com.example.administrator.test.Model.DB;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.administrator.test.Model.Words;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Administrator on 2017-06-03.
 */

public class Words_CursorWrapper extends CursorWrapper {

    public Words_CursorWrapper(Cursor cursor){
        super(cursor);
    }

    public Words getWordsFromCursor(){


        String uuid = getString(getColumnIndex(DbSchema.Words_Table.Words_Cols.WORDS_UUID));
        long date = getLong(getColumnIndex(DbSchema.Words_Table.Words_Cols.WORDS_DATE));
        String title = getString(getColumnIndex(DbSchema.Words_Table.Words_Cols.TITLE));
        String book = getString(getColumnIndex(DbSchema.Words_Table.Words_Cols.BOOK));
//        int chap = getInt(getColumnIndex(DbSchema.Words_Table.Words_Cols.CHAP));
//        int phrase = getInt(getColumnIndex(DbSchema.Words_Table.Words_Cols.PHRASE));
        String word = getString(getColumnIndex(DbSchema.Words_Table.Words_Cols.WORDS));
        String summary = getString(getColumnIndex(DbSchema.Words_Table.Words_Cols.SUMMARY));

        Words words = new Words();
        words.setUuid(UUID.fromString(uuid));
        words.setDate(new Date(date));
        words.setTitle(title);
        words.setBook(book);
//        words.setChap(chap);
//        words.setPhrase(phrase);
        words.setWords(word);
        words.setSummary(summary);

        return words;
    }

}
