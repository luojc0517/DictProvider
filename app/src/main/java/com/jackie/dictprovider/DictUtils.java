package com.jackie.dictprovider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Law on 2015/11/18.
 */
public class DictUtils {
    public final static String AUTHORITY = "com.jackie.dictprovider.provider";
    public final static String TABLE_NAME = "word";

    public static final class Word implements BaseColumns {
        public final static String _ID = "_id";
        public final static String WORD = "word";
        public final static String DETAIL = "detail";
        public final static Uri WORD_DIR = Uri.parse("content://" + AUTHORITY + "/word");
        public final static Uri WORD_ITEM = Uri.parse("content://" + AUTHORITY + "/word/#");

    }
}
