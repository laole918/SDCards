package com.laole918.sdcards.viewmodel;

import android.databinding.ObservableField;
import android.view.View;

import com.laole918.sdcards.R;
import com.laole918.sdcards.utils.Utils;

/**
 * Created by laole918 on 2016/4/23.
 */
public class MainViewModel {

    public final ObservableField<String> deviceInfo = new ObservableField<>();
    public final ObservableField<String> mountValue = new ObservableField<>();

    public void onClickShareDeviceInfo(View view) {
        StringBuilder sb = new StringBuilder();
        sb.append(deviceInfo.get());
        sb.append("\n");
        sb.append(mountValue.get());
        Utils.startShareActivity(view.getContext(), R.string.txt_share_device_info, sb.toString());
    }
}
