package com.ngyb.mobicop.utils;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/11/3 14:57
 */
public class CopyDBUtils {

    public boolean copyDB(Context context, String assetsFileName, String copyFilePath) {
        File file = null;
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            if (copyFilePath != null && !copyFilePath.equals("")) {
                file = new File(copyFilePath);
            } else {
                file = new File(context.getFilesDir(), assetsFileName);
            }
            is = context.getAssets().open(assetsFileName);
            fos = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
