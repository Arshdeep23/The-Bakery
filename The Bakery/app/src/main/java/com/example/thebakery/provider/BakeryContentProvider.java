package com.example.thebakery.provider;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class BakeryContentProvider extends android.content.ContentProvider{

    private BakeryOpenHelperClass myOpenHelerInstance;
    public static final UriMatcher myUriMatcher = my_build_bakery_uri_matcher();
    public static final int BAKERY_INGREDIENTS_WITH_ID = 301;
    public static final int BAKERY_INGREDIENTS = 300;


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase readableDatabase= myOpenHelerInstance.getReadableDatabase();
        int uri_match= myUriMatcher.match(uri);
        Cursor bakery_return_cursor;
        switch (uri_match){
            case BAKERY_INGREDIENTS:
                bakery_return_cursor=readableDatabase.query(BakeryContractClass.nameClass._TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unsupported Operation");
        }
        bakery_return_cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return bakery_return_cursor;
    }


    @Override
    public boolean onCreate() {
        Context my_context=getContext();
        myOpenHelerInstance =new BakeryOpenHelperClass(my_context);
        return true;
    }

    public static UriMatcher my_build_bakery_uri_matcher(){
        UriMatcher my_uri_matcher=new UriMatcher(UriMatcher.NO_MATCH);
        my_uri_matcher.addURI(BakeryContractClass.CONTENT_AUTHORITY, BakeryContractClass.PATH_RECIPE, BAKERY_INGREDIENTS);
        my_uri_matcher.addURI(BakeryContractClass.CONTENT_AUTHORITY, BakeryContractClass.PATH_RECIPE +"/#", BAKERY_INGREDIENTS_WITH_ID);
        return my_uri_matcher;
    }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }


    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int up_no;
        int uri_match = myUriMatcher.match(uri);
        switch (uri_match) {
            case BAKERY_INGREDIENTS_WITH_ID:
                String bakery_ingred_id = uri.getPathSegments().get(1);
                up_no = myOpenHelerInstance.getWritableDatabase().update(BakeryContractClass.nameClass._TABLE_NAME, values, "_id=?", new String[]{bakery_ingred_id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (up_no != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return up_no;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase writableDatabase= myOpenHelerInstance.getWritableDatabase();
        int uri_match= myUriMatcher.match(uri);
        int del_no;
        switch (uri_match){
            case BAKERY_INGREDIENTS:
                del_no=writableDatabase.delete(BakeryContractClass.nameClass._TABLE_NAME,null,null);
                break;
            case BAKERY_INGREDIENTS_WITH_ID:
                del_no=writableDatabase.delete(BakeryContractClass.nameClass._TABLE_NAME,selection,selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unkonwn uri:"+uri);
        }
        return del_no;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase writableDatabase= myOpenHelerInstance.getWritableDatabase();
        int uri_match= myUriMatcher.match(uri);
        Uri bakery_return_uri;
        switch (uri_match){
            case BAKERY_INGREDIENTS:
                long return_id=writableDatabase.insert(BakeryContractClass.nameClass._TABLE_NAME,null,values);
                if(return_id>0){
                    bakery_return_uri = ContentUris.withAppendedId(BakeryContractClass.nameClass.CONTENT_URI,return_id);
                }else{
                    throw new SQLException("Failed to insert row: "+uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unkonwn uri:"+uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return bakery_return_uri;
    }

}
