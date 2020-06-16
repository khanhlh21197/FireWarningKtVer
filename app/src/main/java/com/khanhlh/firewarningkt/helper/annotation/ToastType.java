package com.khanhlh.firewarningkt.helper.annotation;

import androidx.annotation.IntDef;

import static com.khanhlh.firewarningkt.helper.annotation.ToastType.ERROR;
import static com.khanhlh.firewarningkt.helper.annotation.ToastType.NORMAL;
import static com.khanhlh.firewarningkt.helper.annotation.ToastType.SUCCESS;
import static com.khanhlh.firewarningkt.helper.annotation.ToastType.WARNING;

/**
 * 页面描述：ToastType
 * <p>
 * Created by ditclear on 2017/10/11.
 */
@IntDef({ERROR, NORMAL, SUCCESS, WARNING})
public @interface ToastType {
    int ERROR = -2;
    int WARNING = -1;
    int NORMAL = 0;
    int SUCCESS = 1;
}
