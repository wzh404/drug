package com.desmond.demo.box.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.Theme;
import com.desmond.demo.R;
import com.desmond.demo.common.AbstractView;
import com.desmond.demo.common.IView;

/**
 * Created by wangzunhui on 2016/5/30.
 */
public class DrugItemView extends AbstractView {
    private Context context;
    public DrugItemView(Context context, ViewGroup container){
        super.init(context, container, R.layout.item_drug_box);
        this.context = context;
    }

    public void setDrugOtc(String otc){
        if (otc == null) otc = "OTHER";

        TextView textView = get(R.id.drug_otc);
        if ("RX".equalsIgnoreCase(otc)){
            textView.setText("处方");
            textView.setTextColor(ContextCompat.getColor(context, R.color.accent));
            textView.setBackgroundResource(R.drawable.text_view_rx_border);
        }
        else if ("OTHER".equalsIgnoreCase(otc)){
            textView.setText("其它");
            textView.setTextColor(ContextCompat.getColor(context, R.color.dark_gray));
            textView.setBackgroundResource(R.drawable.text_view_other_border);
        }
    }

    public void setDrugForm(String form){
        ImageView imageView = get(R.id.item_drug_form);
        if (form == null){
            imageView.setImageResource(R.mipmap.ic_jiaolang);
        }

        if (form.contains("胶囊")){
            imageView.setImageResource(R.mipmap.ic_jiaolang);
        }
        else if (form.contains("片剂")){
            imageView.setImageResource(R.mipmap.ic_yaopian);
        }
        else if (form.contains("口服液")){
            imageView.setImageResource(R.mipmap.ic_koufuye);
        }
    }

    public void setOnClick(){
        RelativeLayout relativeLayout = get(R.id.item_drug_root);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Drug", "----click-----");
            }
        });

        relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.e("Drug", "----long click-----");
                return true;
            }
        });
    }
}
