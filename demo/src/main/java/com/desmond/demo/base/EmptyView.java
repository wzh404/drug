package com.desmond.demo.base;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.desmond.demo.R;
import com.desmond.demo.common.AbstractView;


/**
 * Created by WIN10 on 2016/2/1.
 */
public class EmptyView extends AbstractView {
    public EmptyView(Context context, ViewGroup container){
        super.init(context, container, R.layout.item_empty);

//        WindowManager windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
//        Point point = new Point();
//        windowManager.getDefaultDisplay().getSize(point);
//        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)getView().getLayoutParams();
//        layoutParams.height = point.y;
//        getView().setLayoutParams(layoutParams);
    }
}
