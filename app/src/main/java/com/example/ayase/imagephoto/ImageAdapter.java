package com.example.ayase.imagephoto;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bm.library.PhotoView;

import java.util.ArrayList;

/**
 * Created by alva on 2016/11/3 0003.
 */

public class ImageAdapter extends PagerAdapter {
    private ArrayList<PhotoView> list;
    Context context;

    public ImageAdapter(Context applicationContext, ArrayList<PhotoView> guideList) {
        this.context = applicationContext;
        this.list = guideList;
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // 1.获取imageview
        ImageView iv = list.get(position);
        // 2.把imageview添加到viewpager
        container.addView(iv);
        // 3.返回imageview
        return iv;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
