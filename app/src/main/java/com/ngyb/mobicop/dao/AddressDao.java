package com.ngyb.mobicop.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.io.File;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/11/4 17:02
 */
public class AddressDao {
    public String queryPhoneAddress(Context context, String phone) {
        String path = context.getFilesDir().getAbsolutePath() + File.separator + "address.db";
        SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        String rex = "^1[3,5,7,8]\\d{9}";
        String address = "未知号码";
        boolean matches = phone.matches(rex);
        if (matches) {
            String subPhone = phone.substring(0, 7);
            String sql = "select cardtype from info where mobileprefix = ?;";
            Cursor cursor = db.rawQuery(sql, new String[]{subPhone});
            if (cursor.moveToNext()) {
                address = cursor.getString(0);
            } else {
                address = "未知号码";
            }
            cursor.close();
        } else {
            switch (phone.length()) {
                case 3:
                    address = "报警电话";
                    break;
                case 5:
                    address = "运营商服务";
                    break;
                case 7:
                case 8:
                    address = "本地座机";
                    break;
                case 10://3+7
                    String subArea0 = phone.substring(0, 3);
                    String sql0 = "select city from info where area = ?;";
                    Cursor cursor0 = db.rawQuery(sql0, new String[]{subArea0});
                    if (cursor0.moveToNext()) {
                        address = cursor0.getString(0);
                    } else {
                        address = "未知号码";
                    }
                    cursor0.close();
                    break;
                case 11://3+8 4+8
                    String subArea1 = phone.substring(0, 3);//3+8
                    String subAreaF1 = phone.substring(0, 4);//4+7
                    String sql1 = "select city from info where area = ?;";
                    Cursor cursor1 = db.rawQuery(sql1, new String[]{subArea1, subAreaF1});
                    if (cursor1.moveToNext()) {
                        address = cursor1.getString(0);
                    } else {
                        address = "未知号码";
                    }
                    cursor1.close();
                    break;
                case 12://4+8
                    String subArea2 = phone.substring(0, 4);
                    String sql2 = "select city from info where area = ?;";
                    Cursor cursor2 = db.rawQuery(sql2, new String[]{subArea2});
                    if (cursor2.moveToNext()) {
                        address = cursor2.getString(0);
                    } else {
                        address = "未知号码";
                    }
                    cursor2.close();
                    break;
                default:
                    address = "未知号码";
                    break;
            }
        }
        return address;
    }

}
