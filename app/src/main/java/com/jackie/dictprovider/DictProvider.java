package com.jackie.dictprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by Law on 2015/11/18.
 */
public class DictProvider extends ContentProvider {
    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private DictDatabaseHelper dictDatabaseHelper;
    private static final int WORD_DIR = 0;
    private static final int WORD_ITEM = 1;

    static {
        uriMatcher.addURI(DictUtils.AUTHORITY, DictUtils.TABLE_NAME, WORD_DIR);
        uriMatcher.addURI(DictUtils.AUTHORITY, DictUtils.TABLE_NAME + "/#", WORD_ITEM);
    }

    @Override
    public boolean onCreate() {
        dictDatabaseHelper = new DictDatabaseHelper(getContext(), "dict.db", null, 1);
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;
        SQLiteDatabase db = dictDatabaseHelper.getReadableDatabase();
        switch (uriMatcher.match(uri)) {
            case WORD_DIR:
                cursor = db.query(DictUtils.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case WORD_ITEM:
                long _id = ContentUris.parseId(uri);
                String whereClause = DictUtils.Word._ID + "=" + _id;
                if (selection != null && !selection.equals("")) {
                    whereClause = whereClause + "and" + selection;
                }
                cursor = db.query(DictUtils.TABLE_NAME, projection, whereClause, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("未知Uri" + uri);

        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case WORD_DIR:
                return "vnd.android.cursor.dir/vnd.com.jackie.dictprovider.word";
            case WORD_ITEM:
                return "vnd.android.cursor.item/vnd.com.jackie.dictprovider.word";
            default:
                throw new IllegalArgumentException("未知Uri" + uri);

        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dictDatabaseHelper.getWritableDatabase();
        long rowId = 0;
        Uri newUri = null;
        switch (uriMatcher.match(uri)) {
            case WORD_DIR:
                rowId = db.insert(DictUtils.TABLE_NAME, null, values);
                if (rowId > 0) {
                    newUri = ContentUris.withAppendedId(uri, rowId);
                }
        }

        return newUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int rowId = 0;
        SQLiteDatabase db = dictDatabaseHelper.getReadableDatabase();
        switch (uriMatcher.match(uri)) {
            case WORD_DIR:
                rowId = db.delete(DictUtils.TABLE_NAME, selection, selectionArgs);
                break;
            case WORD_ITEM:
                long _id = ContentUris.parseId(uri);
                String whereClause = DictUtils.Word._ID + "=" + _id;
                if (selection != null && !selection.equals("")) {
                    whereClause = whereClause + "and" + selection;
                }
                rowId = db.delete(DictUtils.TABLE_NAME, whereClause, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("未知Uri" + uri);
        }
        return rowId;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int rowId = 0;
        SQLiteDatabase db = dictDatabaseHelper.getReadableDatabase();
        switch (uriMatcher.match(uri)) {
            case WORD_DIR:
                rowId = db.update(DictUtils.TABLE_NAME, values, selection, selectionArgs);
                break;
            case WORD_ITEM:
                long _id = ContentUris.parseId(uri);
                String whereClause = DictUtils.Word._ID + "=" + _id;
                if (selection != null && !selection.equals("")) {
                    whereClause = whereClause + "and" + selection;
                }
                rowId = db.update(DictUtils.TABLE_NAME, values, whereClause, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("未知Uri" + uri);
        }
        return rowId;
    }
}
