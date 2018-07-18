package com.example.ayase.imagephoto;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.LayoutInflater;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by alva on 2016/11/1
 */
public class ImageDialog extends Dialog implements ViewPager.OnPageChangeListener {

    private LayoutInflater factory;
    private Context context;

    private ViewPager viewpager;
    private ArrayList<PhotoView> guideList;
    // 静态点容器
    private LinearLayout point_container;
    // 动态点
    private View point_focus;
    // 两个静态点之间的距离
    int point_space;
    private AutoSwitchPic autoSwitchPic;


    private List<String> list;
    private int temp;

    public ImageDialog(Context context,List<String> list,int temp) {
        super(context);
        this.context = context;
        this.list = list;
        this.temp = temp;
        factory = LayoutInflater.from(context);
    }

    public ImageDialog(Context context, int themeResId,List<String> list,int temp) {
        super(context, themeResId);
        this.context = context;
        this.list = list;
        this.temp = temp;
        factory = LayoutInflater.from(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(factory.inflate(R.layout.dialog_image, null));
        WindowManager m = ((Activity) context).getWindowManager();
        Display d = m.getDefaultDisplay();
        Window window = this.getWindow();
        WindowManager.LayoutParams dialogParams = window.getAttributes();
        window.setWindowAnimations(R.style.mydialogstyle);
        window.setAttributes(dialogParams);
        dialogParams.width = d.getWidth();
        dialogParams.height = d.getHeight();
        window.setBackgroundDrawableResource(R.color.black);

        // 初始化wiew
        initView();
        // 初始化数据
        initData();
    }

    private void initView() {
        viewpager = (ViewPager) findViewById(R.id.guide_viewpager);
        point_container = (LinearLayout) findViewById(R.id.guide_point_cont);
        point_focus = findViewById(R.id.point_focus);

        point_container.getViewTreeObserver().// 监听布局完成
                addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                if(list.size()>1){
                    point_container.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    // 测量两个子类组件之间的距离
                    point_space = point_container.getChildAt(1).getLeft() - point_container.getChildAt(0).getLeft();
                }
            }
        });
    }

    private void initData() {
        guideList = new ArrayList<PhotoView>();
        PhotoView img;
        View point;
        for (int i = 0; i < list.size(); i++) {
            img = new PhotoView(context);

            img.setAdjustViewBounds(true);
            int d = viewpager.getWidth();
            img.setMaxWidth(d);
            // 设置图片资源
            Glide.with(context)
                    .load(list.get(i))
                    .placeholder(R.mipmap.icon_default_small)
                    .error(R.mipmap.icon_default_small)
                    .into(img);
            // 设置填充
            img.enable();
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            guideList.add(img);

            // 设置底部静态点
            point = new View(context);
            point.setBackgroundResource(R.drawable.dot_normal);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
            if (i != 0) {
                // 这里的数值单位是px
                params.leftMargin = 20;
            }
            point_container.addView(point, params);
        }

        int marginLeft2 = (int) ( 50 * temp + 0.5f);
        RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) point_focus.getLayoutParams();
        params2.leftMargin = marginLeft2;
        point_focus.setLayoutParams(params2);

        // 给viewpager设置数据
        viewpager.setAdapter(new ImageAdapter(context, guideList));
        //设置默认页
        viewpager.setCurrentItem(temp);
        // 给viewpager设置监听
        viewpager.setOnPageChangeListener(this);
//        // 开启轮播
//        if (autoSwitchPic == null) {
//            autoSwitchPic = new AutoSwitchPic(context, viewpager, guideList);
//            autoSwitchPic.start();
//        }
        // 对于轮播过程中viewpager触摸事件的处理：
//        viewpager.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        autoSwitchPic.stop();
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        break;
//                    case MotionEvent.ACTION_CANCEL:
//                    case MotionEvent.ACTION_UP:
//                        autoSwitchPic.start();
//                        break;
//                    default:
//                        break;
//                }
//                return false;
//            }
//        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {// positionOffset:滑动的百分比，滑动的距离/父容器的宽度，positionOffsetPixels:滑动的像素点

        // viewpager正在滚动时调用的方法
        if (temp == 0){
            int marginLeft = (int) (positionOffset * point_space + point_space * position + 0.5f);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) point_focus.getLayoutParams();
            params.leftMargin = marginLeft;
            point_focus.setLayoutParams(params);
        }else {
            temp = 0;
        }

    }

    @Override
    public void onPageSelected(int position) {
        // viewpager被选中的时候调用的方法
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // 滚动状态改变的时候调用的方法

    }

}
