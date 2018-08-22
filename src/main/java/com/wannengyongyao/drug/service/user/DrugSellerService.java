package com.wannengyongyao.drug.service.user;

import com.github.pagehelper.Page;
import com.wannengyongyao.drug.model.DrugSeller;

import java.util.Map;

public interface DrugSellerService {
    Page<DrugSeller> myPharmacists(int page, int pageSize, Map<String, Object> conditionMap);

    /**
     * 靠谱药师
     *
     * @return
     */
    Page<DrugSeller> reliableSeller(int page, int pageSize);
}