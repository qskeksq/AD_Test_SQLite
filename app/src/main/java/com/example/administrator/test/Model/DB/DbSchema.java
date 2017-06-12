package com.example.administrator.test.Model.DB;

/**
 * Created by Administrator on 2017-06-03.
 */

public class DbSchema {

    public static final String DATABASE_NAME = "dataBase1.db";

    public class QT_Table{

        public static final String QT_TABLE_NAME = "qtTableName";

        public class QT_Cols {
            public static final String QT_UUID = "qt_uuid";
            public static final String QT_DATE = "qt_date";
            public static final String WEEK = "word_of_this_week";
            public static final String QT = "daily_qt";
            public static final String THANKS = "thanksGiving";
            public static final String PRAYER = "prayer";
            public static final String JOURNAL = "journal";
        }

    }


    public class Words_Table{

        public static final String WORDS_TABLE_NAME = "wordsTableName";

        public class Words_Cols {
            public static final String WORDS_UUID = "words_uuid";
            public static final String WORDS_DATE = "words_date";
            public static final String TITLE = "wordsTitle";
            public static final String BOOK = "wordsBook";
//            public static final String CHAP = "wordsChap";
//            public static final String PHRASE = "wordsPhrase";
            public static final String WORDS = "words";
            public static final String SUMMARY = "wordsSummary";
        }

    }





}
