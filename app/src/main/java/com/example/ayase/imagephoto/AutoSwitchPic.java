package com.example.ayase.imagephoto;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;

import com.bm.library.PhotoView;

import java.util.ArrayList;

/**
 * Created by Ayase on 2018/7/18.
 */

public class AutoSwitchPic extends Handler implements Runnable {

    private ArrayList<PhotoView> list;
    Context context;
    ViewPager viewPager;

    public AutoSwitchPic(Context applicationContext, ViewPager viewpager, ArrayList<PhotoView> guideList) {
        this.context = applicationContext;
        this.viewPager = viewpager;
        this.list = guideList;
    }

    public void start() {// 控制轮播的开始
        stop();
        // 发送一个延时执行的消息
        postDelayed(this, 2000);
    }

    public void stop() {// 控制轮播的结束
        // 从消息队列中移除消息
        removeCallbacks(this);
    }

    @Override
    public void run() {
        // 获取当前的轮播位置
        int position = viewPager.getCurrentItem();
        if (position != list.size() - 1) {
            // 设置接下来的轮播位置
            viewPager.setCurrentItem(++position);
        } else {
            viewPager.setCurrentItem(0);
        }
        postDelayed(this, 2000);
    }

}

