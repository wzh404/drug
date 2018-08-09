package com.wannengyongyao.drug.vo;

import com.wannengyongyao.drug.model.DrugOrderGoods;

import java.util.ArrayList;
import java.util.List;

public class DrugOrderVo extends OrderVo {
    // 药品信息
    List<DrugVo> drugs;

    // 订单药品信息
    public List<DrugOrderGoods> asGoods(){
        List<DrugOrderGoods> goods = new ArrayList<>();
        for (DrugVo d : drugs){
            DrugOrderGoods g = new DrugOrderGoods();
            g.setDrugId(d.getId());
            g.setQuantity(d.getQuantity());

            goods.add(g);
        }

        return goods;
    }
}