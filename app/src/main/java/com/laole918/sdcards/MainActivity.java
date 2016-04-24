package com.laole918.sdcards;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.laole918.sdcards.databinding.ActivityMainBinding;
import com.laole918.sdcards.utils.DeviceUtils;
import com.laole918.sdcards.utils.SDCardsUtils;
import com.laole918.sdcards.utils.Utils;
import com.laole918.sdcards.viewmodel.MainViewModel;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        MainViewModel mainViewModel = new MainViewModel();
        binding.setMainViewModel(mainViewModel);
        mainViewModel.deviceInfo.set(DeviceUtils.getDeviceInfo());
        mainViewModel.mountValue.set(Utils.exec("mount"));

        File sdcard0 = new File(SDCardsUtils.getSDCard0());
        String s1 = SDCardsUtils.getSDCard1(this);
        File sdcard1 = new File(s1 == null ? "" : s1);

        File[] files = new File[] {sdcard0, sdcard1};
        for (File file : files) {
            System.out.println("=====================================");
            System.out.println("isDirectory:" + file.isDirectory());
            System.out.println("exists:" + file.exists());
            System.out.println("canRead:" + file.canRead());
            System.out.println("canWrite:" + file.canWrite());
            System.out.println("getTotalSpace:" + file.getTotalSpace());
            System.out.println("getFreeSpace:" + file.getFreeSpace());
            System.out.println("getUsableSpace:" + file.getUsableSpace());
            System.out.println("-------------------------------------");
        }
        System.out.println("getSDCard0State:" + SDCardsUtils.getSDCard0State());
        System.out.println("getSDCard1State:" + SDCardsUtils.getSDCard1State(this));
    }
}
