package com.wannengyongyao.drug.service.user.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wannengyongyao.drug.common.status.UserAddressStatus;
import com.wannengyongyao.drug.dao.*;
import com.wannengyongyao.drug.model.*;
import com.wannengyongyao.drug.service.user.DrugUserService;
import com.wannengyongyao.drug.vo.UserAddressVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service("drugUserService")
public class DrugUserServiceImpl implements DrugUserService {
    @Autowired
    private DrugUserMapper userMapper;

    @Autowired
    private DrugUserWeixinMapper weixinMapper;

    @Autowired
    private DrugUserCartMapper userCartMapper;

    @Autowired
    private DrugMapper drugMapper;

    @Autowired
    private DrugCouponMapper couponMapper;

    @Autowired
    private DrugUserAddressMapper addressMapper;

    @Override
    public int insertUserPharmacist(DrugUserPharmacist pharmacist) {
        int cnt = userMapper.getUserPharmacist(pharmacist.getUserId(), pharmacist.getPharmacistId());
        if (cnt > 0){
            return -1;
        }
        return userMapper.insertUserPharmacist(pharmacist);
    }

    @Override
    public int insertUserStore(DrugUserStore store) {
        int cnt = userMapper.getUserStoreCount(store.getUserId(), store.getStoreId());
        if (cnt > 0){
            return -1;
        }
        return userMapper.insertUserStore(store);
    }

    @Override
    public List<DrugStore> getUserStores(Long userId) {
        return userMapper.getUserStores(userId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int insertWeixinUser(DrugWeixinUser wxUser) {
        DrugWeixinUser u = weixinMapper.getByOpenId(wxUser.getOpenId());
        if (u != null){
            return 1;
        }
        return weixinMapper.insert(wxUser);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Long insertWeixinAndUser(DrugWeixinUser wxUser) {
        DrugWeixinUser u = weixinMapper.getByOpenId(wxUser.getOpenId());
        if (u != null){
            DrugUser du = userMapper.getUserByOpenid(wxUser.getOpenId());
            if (du == null){
                return -1L;
            }
            return du.getId();
        }

        DrugUser user = new DrugUser();
        user.setName(u.getNickName());
        user.setCreateIp("localhost");
        user.setOpenId(wxUser.getOpenId());
        user.setMobile("-");
        user.setCreateTime(LocalDateTime.now());
        user.setLastUpdatedTime(LocalDateTime.now());
        user.setStatus(0);
        user.setAvatar(wxUser.getAvatarUrl());
        user.setCreateIp("localhost");

        userMapper.insert(user);
        weixinMapper.insert(wxUser);
        return user.getId();
    }

    @Override
    public int insertUser(DrugUser user) {
        DrugUser u = userMapper.getByMobile(user.getMobile());
        if (u != null){
            user.setId(u.getId());
            return 1;
        }

        DrugWeixinUser w = weixinMapper.getByOpenId(user.getOpenId());
        if (w == null){
            return -2;
        }
        user.setName(w.getNickName());
        // user.setGender("男".equalsIgnoreCase(w.getGender()) ? 0 : 1);

        return userMapper.insert(user);
    }

    @Override
    public DrugWeixinUser getByOpenId(String openId) {
        return weixinMapper.getByOpenId(openId);
    }

    @Override
    public int insert(DrugUserCart cart) {
        Drug drug = drugMapper.get(cart.getDrugId());
        if (drug == null){
            return -1;
        }

        DrugUserCart c = userCartMapper.getUserCart(cart.getUserId(), cart.getDrugId());
        if (c != null){
            return -2;
        }
        cart.setDrugName(drug.getName());
        cart.setManufacturer(drug.getManufacturer());
        cart.setSpecifications(drug.getSpecifications());
        cart.setQuantity(1);
        cart.setUnit("件");
        cart.setUnitPrice(BigDecimal.valueOf(0.0));
        return userCartMapper.insert(cart);
    }

    @Override
    public List<DrugUserCart> listUserCart(Long userId) {
        return userCartMapper.listUserCart(userId);
    }

    @Override
    public int deleteUserCart(List<DrugUserCart> carts) {
        return userCartMapper.deleteUserCart(carts);
    }

    @Override
    public List<DrugUserLongterm> listUserLongTerm(Long userId) {
        return userMapper.listUserLongTerm(userId);
    }

    @Override
    public int insertUserLongTerm(DrugUserLongterm longterm) {
        Drug drug = drugMapper.get(longterm.getDrugId());
        if (drug == null){
            return -1;
        }

        DrugUserLongterm c = userMapper.getUserLongTerm(longterm.getUserId(), longterm.getDrugId());
        if (c != null){
            return -2;
        }
        longterm.setDrugName(drug.getName());
        longterm.setManufacturer(drug.getManufacturer());
        longterm.setSpecifications(drug.getSpecifications());
        longterm.setQuantity(1);
        longterm.setUnit("件");
        longterm.setUnitPrice(BigDecimal.valueOf(0.0));

        return userMapper.insertUserLongTerm(longterm);
    }

    @Override
    public int deleteUserLongTerm(List<DrugUserLongterm> longterms) {
        return userMapper.deleteUserLongTerm(longterms);
    }

    @Override
    public Page<DrugUserCoupon> myCoupons(int page, int pageSize, Long userId) {
        PageHelper.startPage(page, pageSize);
        return couponMapper.myCoupons(userId);
    }

    @Override
    public int insertUserCoupon(DrugUserCoupon userCoupon) {
        DrugCoupon c = couponMapper.get(userCoupon.getCode());
        if (c == null){
            return -1;
        }
        DrugUserCoupon uc = couponMapper.getUserCoupon(userCoupon.getCode());
        if (uc != null){
            return -2;
        }
        return couponMapper.insertUserCoupon(userCoupon);
    }

    /*
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int insertUserAndAddress(DrugUser user, DrugUserAddress address) {
        userMapper.insert(user);
        address.setUserId(user.getId());
        return addressMapper.insert(address);
    }
*/
    @Override
    public int insertUserAddress(DrugUserAddress address) {
        List<DrugUserAddress> l = myAddress(address.getUserId());
        if (l == null || l.isEmpty()){
            address.setStatus(1);
        }
        return addressMapper.insert(address);
    }

    @Override
    public List<DrugUserAddress> myAddress(Long userId) {
        return addressMapper.list(userId);
    }

    @Override
    public int changeUserAddressStatus(Long id, Long userId, Integer status) {
        DrugUserAddress a = addressMapper.get(id);
        if (a == null){
            return -1;
        }
        if (a.getUserId().longValue() != userId.longValue()){
            return -2;
        }
        if (status == UserAddressStatus.DEFAULT.get()){
            addressMapper.undefaultStatus(userId);
        }
        return addressMapper.changeStatus(id, status);
    }

    @Override
    public int changeAddress(DrugUserAddress address) {
        return addressMapper.changeAddress(address);
    }

    @Override
    public DrugUser getUserByOpenid(String openid) {
        return userMapper.getUserByOpenid(openid);
    }
}