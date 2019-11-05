package com.ngyb.mobicop.dao;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.ngyb.mobicop.db.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/11/5 17:58
 */
public class AppLockDao {
    private Context context;
    private DBHelper dbHelper;

    public AppLockDao(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context);
    }

    public void insert(String packageName) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("packagename", packageName);
        db.insert("applock", null, values);
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = Uri.parse("content://ngyb.mobicop.change");
        contentResolver.notifyChange(uri, null);
        db.close();
    }

    public void delete(String packageName) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("applock", "packagename = ?", new String[]{packageName});
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = Uri.parse("content://ngyb.mobicop.change");
        contentResolver.notifyChange(uri, null);
        db.close();
    }

    public List<String> queryAll() {
        List<String> packageNameList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select packagename from applock;", new String[]{});
        while (cursor.moveToNext()) {
            String packagename = cursor.getString(0);
            packageNameList.add(packagename);
        }
        cursor.close();
        db.close();
        return packageNameList;
    }
}
