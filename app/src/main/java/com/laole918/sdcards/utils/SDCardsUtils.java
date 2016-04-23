package com.laole918.sdcards.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.text.TextUtils;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Method;

/**
 * Created by laole918 on 2016/4/23.
 */
public class SDCardsUtils {

    private static String sdcard0 = null;
    private static String sdcard1 = null;
    private static String usbOtg = null;
    private static boolean initialized = false;

    public static String getSDCard0State() {
        return Environment.getExternalStorageState();
    }

    public static String getSDCard0State(Context context) {
        return getSDCardState(context, sdcard0);
    }

    public static String getSDCard1State(Context context) {
        return getSDCardState(context, sdcard1);
    }

    private static String getUsbOTGState(Context context) {
        return getSDCardState(context, usbOtg);
    }

    @TargetApi(19)
    private static String getSDCardState(Context context, String sdcard) {
        initialize(context);
        if (!TextUtils.isEmpty(sdcard)) {
            File path = new File(sdcard);
            if (Build.VERSION.SDK_INT >= 19) {
                return Environment.getStorageState(path);
            } else {
                if (path.exists() && path.isDirectory() && path.canRead()) {
                    if (path.canWrite()) {
                        return Environment.MEDIA_MOUNTED;
                    } else {
                        return Environment.MEDIA_MOUNTED_READ_ONLY;
                    }
                } else {
                    return Environment.MEDIA_REMOVED;
                }
            }
        } else {
            return Environment.MEDIA_UNMOUNTED;
        }
    }

    public static String getSDCard0(Context context) {
        initialize(context);
        return sdcard0;
    }

    public static String getSDCard0() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    public static String getSDCard1(Context context) {
        initialize(context);
        return sdcard1;
    }

    private static String getUsbOTG(Context context) {
        initialize(context);
        return usbOtg;
    }

    private static void initialize(Context context) {
        if (!initialized) {
            synchronized (SDCardsUtils.class) {
                if (!initialized) {
                    String[] dirs = getExternalDirs(context);
                    if (dirs != null) {
                        int length = dirs.length;
                        String[] dirsPath = new String[3];
                        for (int i = 0; i < length && i < 3; i++) {
                            dirsPath[i] = dirs[i];
                        }
                        sdcard0 = dirsPath[0];
                        sdcard1 = dirsPath[1];
                        usbOtg = dirsPath[2];
                    }
                    initialized = true;
                }
            }
        }
    }

    private static String[] getExternalDirs(Context context) {
        Context mContext = context.getApplicationContext();
        StorageManager mStorageManager = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);
        try {
            Class<?> storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
            Method getVolumeList = mStorageManager.getClass().getMethod("getVolumeList");
            Method getPath = storageVolumeClazz.getMethod("getPath");
            Object result = getVolumeList.invoke(mStorageManager);
            final int length = Array.getLength(result);
            final String[] paths = new String[length];
            for (int i = 0; i < length; i++) {
                Object storageVolumeElement = Array.get(result, i);
                paths[i] = (String) getPath.invoke(storageVolumeElement);
            }
            return paths;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
