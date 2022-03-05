package com.platform.vehicle_repair.controller;

import com.platform.vehicle_repair.VO.ResultVO;
import com.platform.vehicle_repair.entity.Customer;
import com.platform.vehicle_repair.entity.WxSession;
import com.platform.vehicle_repair.enums.ResultEnum;
import com.platform.vehicle_repair.service.CustomerService;
import com.platform.vehicle_repair.util.ResultVOUtil;
import com.platform.vehicle_repair.util.WxUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    //用户打开微信小程序，后台自动获取微信openid
    @GetMapping("/wxLogin")
    public ResultVO GetOpenId(@RequestParam("code") String code){
        WxSession wx = WxUtil.getWxSession(code);
        String openid = wx.getOpenid();
        return ResultVOUtil.success(openid);
    }
    //查询openid，判断用户信息是否存在数据库中，存在返回用户信息，否则返回错误“用户不存在”
    @RequestMapping("/login")
    public ResultVO wxLogin(@RequestParam("openid") String openid){
        if(customerService.isPresent(openid)){
            Customer customer = customerService.findOne(openid);
            return ResultVOUtil.success(customer);
        }else{
            return ResultVOUtil.error(ResultEnum.USER_NOT_EXIT.getCode(),
                    ResultEnum.USER_NOT_EXIT.getMessage());
        }
    }

    @RequestMapping("/register")
    public ResultVO register(@RequestBody Customer customer){
        customerService.register(customer);
        return ResultVOUtil.success(true);
    }

    @PostMapping("/update/image")
    public ResultVO updateImage(@RequestParam("id") String id,
                                @RequestParam("image") String image) {
        if(customerService.updateImage(id, image)){
            return ResultVOUtil.success();
        }else{
            return ResultVOUtil.error(ResultEnum.FAIL_TO_MODIFY.getCode(),
                    ResultEnum.FAIL_TO_MODIFY.getMessage());
        }
    }
    @PostMapping("/update/userName")
    public ResultVO updateUserName(@RequestParam("id") String id,
                                   @RequestParam("userName") String userName){
        if(customerService.updateUserName(id, userName)){
            return ResultVOUtil.success();
        }else{
            return ResultVOUtil.error(ResultEnum.FAIL_TO_MODIFY.getCode(),
                    ResultEnum.FAIL_TO_MODIFY.getMessage());
        }
    }
    @PostMapping("/update/sex")
    public ResultVO updateSex(@RequestParam("id") String id,
                              @RequestParam("sex") Integer sex){
        if(customerService.updateSex(id, sex)){
            return ResultVOUtil.success();
        }else{
            return ResultVOUtil.error(ResultEnum.FAIL_TO_MODIFY.getCode(),
                    ResultEnum.FAIL_TO_MODIFY.getMessage());
        }
    }
    @PostMapping("/update/tel")
    public ResultVO updateTel(@RequestParam("id") String id,
                              @RequestParam("tel") String tel){
        if(customerService.updateTel(id, tel)){
            return ResultVOUtil.success();
        }else{
            return ResultVOUtil.error(ResultEnum.FAIL_TO_MODIFY.getCode(),
                    ResultEnum.FAIL_TO_MODIFY.getMessage());
        }
    }
    @PostMapping("/update/address")
    public ResultVO updateAddress(@RequestParam("id") String id,
                                  @RequestParam("address") String address){
        if(customerService.updateAddress(id, address)){
            return ResultVOUtil.success();
        }else{
            return ResultVOUtil.error(ResultEnum.FAIL_TO_MODIFY.getCode(),
                    ResultEnum.FAIL_TO_MODIFY.getMessage());
        }
    }
}
