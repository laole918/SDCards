# SDCards说明
方便android中获取内置和外置SDCard <br>
注意6.0以上权限申请，[详见](http://blog.csdn.net/lmj623565791/article/details/50709663)
## 原理
通过反射得到SDCard列表，其中第0个就是内置SD卡，第1个就是外置SD卡
``` java
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
```
## 使用
``` java
    //SD卡目录
    SDCardsUtils.getSDCard0();//内置，或者SDCardsUtils.getSDCard0(context)
    SDCardsUtils.getSDCard1(context);//外置
    //SD卡状态
    SDCardsUtils.getSDCard0State();//或者SDCardsUtils.getSDCard0State(context)
    SDCardsUtils.getSDCard1State(context);
```
## 参考
[http://www.cnblogs.com/littlepanpc/p/3868369.html](http://www.cnblogs.com/littlepanpc/p/3868369.html) <br>
[http://blog.fidroid.com/post/android/ru-he-zheng-que-huo-de-androidnei-wai-sdqia-lu-jing](http://blog.fidroid.com/post/android/ru-he-zheng-que-huo-de-androidnei-wai-sdqia-lu-jing)

