package com.android.zpp.viewbnidtest.utils;

import android.content.Context;
import android.os.Environment;
import android.view.MotionEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: ViewBnidTest
 * @Package: com.android.zpp.viewbnidtest.utils
 * @ClassName: GetPhoto
 * @Description:
 * @Author: zpp
 * @CreateDate: 2022/5/19 9:43
 * @UpdateUser:
 * @UpdateDate: 2022/5/19 9:43
 * @UpdateRemark:
 */
public class GetPhoto {
    public static ArrayList<String> getPhoto(Context context) {
        ArrayList<String> arrayList = new ArrayList<>();
        File scanner5Directory = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath());
        if (scanner5Directory.isDirectory()) {
            for (File file : scanner5Directory.listFiles()) {
                String path = file.getAbsolutePath();
                if (path.endsWith(".jpg") || path.endsWith(".jpeg") || path.endsWith(".png")) {
                    arrayList.add(path);
                }
            }
        }
        return arrayList;
    }

    /**
     * 计算手指间距
     */
   public static float calculateFingerSpacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }
}
