package com.wannengyongyao.drug.controller.pharmacist;

import com.google.common.cache.Cache;
import com.wannengyongyao.drug.common.ResultCode;
import com.wannengyongyao.drug.common.ResultObject;
import com.wannengyongyao.drug.model.DrugSeller;
import com.wannengyongyao.drug.model.DrugUser;
import com.wannengyongyao.drug.service.pharmacist.PharmacistService;
import com.wannengyongyao.drug.util.RequestUtil;
import com.wannengyongyao.drug.vo.PharmacistVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/pharmacist")
public class PharmacistController {
    @Autowired
    private PharmacistService pharmacistService;

    @Autowired
    private Cache<String, String> smsCache;

    /**
     * 药师注册
     *
     * @param pharmacistVo
     * @return
     */
    @RequestMapping(value="/common/register", method= {RequestMethod.POST})
    public ResultObject register(@RequestBody PharmacistVo pharmacistVo){
        String smscode = smsCache.getIfPresent(pharmacistVo.getMobile());
        if (smscode == null || !smscode.equalsIgnoreCase(pharmacistVo.getCode())){
            return ResultObject.fail(ResultCode.INVALID_SMS_CODE);
        }

        int rows = pharmacistService.insert(pharmacistVo.asPharmacist());
        return ResultObject.cond(rows > 0, ResultCode.FAILED);
    }

    /**
     * 编辑药师信息
     *
     * @param request
     * @param pharmacistVo
     * @return
     */
    @RequestMapping(value="/info", method= {RequestMethod.POST})
    public ResultObject edit(HttpServletRequest request,
                             @RequestBody PharmacistVo pharmacistVo){
        long sellerId = RequestUtil.getUserId(request);
        if (sellerId != pharmacistVo.getId().longValue()){
            return ResultObject.fail(ResultCode.FAILED);
        }

        int rows = pharmacistService.update(pharmacistVo.asEditedPharmacist());
        return ResultObject.cond(rows > 0, ResultCode.FAILED);
    }

    /**
     * 用户个人信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value="/profile", method= {RequestMethod.GET})
    public ResultObject profile(HttpServletRequest request){
        long sellerId = RequestUtil.getUserId(request);

        DrugSeller seller = pharmacistService.getSeller(sellerId);
        if (seller == null){
            return ResultObject.fail(ResultCode.PHARMACIST_NOT_EXIST);
        }

        return ResultObject.ok(seller);
    }

    @RequestMapping(value="/users", method= {RequestMethod.GET})
    public ResultObject myUser(HttpServletRequest request){
        long sellerId = RequestUtil.getUserId(request);
        DrugSeller seller = pharmacistService.getSeller(sellerId);
        if (seller == null){
            return ResultObject.fail(ResultCode.PHARMACIST_NOT_EXIST);
        }

        List<DrugUser> users = pharmacistService.getPharmacistUsers(sellerId);
        return ResultObject.ok(users);
    }
}
