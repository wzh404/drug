package com.wannengyongyao.drug.vo;

import com.wannengyongyao.drug.model.DrugUserAddress;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserAddressVo {
    private Long id;
    private String name;
    private String address;
    private String district;
    private String mobile;

    private Long userId;

    public DrugUserAddress asAddress(){
        DrugUserAddress address = new DrugUserAddress();
        if (id != null){
            address.setId(id);
        }
        address.setAddress(this.address);
        address.setDistrict(this.district);
        address.setCreateTime(LocalDateTime.now());
        address.setStatus(0);
        address.setName(name);
        address.setMobile(this.mobile);
        address.setUserId(this.userId);

        return address;
    }
}
