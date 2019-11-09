package com.ngyb.mobicop.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/11/9 17:29
 */
public class VirusDao {
    public boolean isVirus(Context ctx, String md5) {
        String path = ctx.getFilesDir().getAbsolutePath() + File.separator + "antivirus.db";
        SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        String sql = "select count(*) from datable where md5 = ?;";
        Cursor cursor = db.rawQuery(sql, new String[]{md5});
        int count = 0;
        if (cursor.moveToNext()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return count > 0;
    }
}
