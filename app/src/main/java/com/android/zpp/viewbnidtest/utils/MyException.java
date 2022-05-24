package com.android.zpp.viewbnidtest.utils;

import androidx.annotation.Nullable;

/**
 * @ProjectName: ViewBnidTest
 * @Package: com.android.zpp.viewbnidtest.utils
 * @ClassName: MyException
 * @Description:
 * @Author: zpp
 * @CreateDate: 2022/5/24 10:50
 * @UpdateUser:
 * @UpdateDate: 2022/5/24 10:50
 * @UpdateRemark:
 */
public class MyException extends Exception {
    String message;

    public MyException(String message) {
        this.message = message;
    }

    @Nullable
    @Override
    public String getMessage() {
        return message;
    }
}
