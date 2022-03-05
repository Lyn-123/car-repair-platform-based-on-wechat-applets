package com.platform.vehicle_repair.controller;


import com.platform.vehicle_repair.VO.ResultVO;
import com.platform.vehicle_repair.dto.CartDTO;
import com.platform.vehicle_repair.dto.CartDetailDTO;
import com.platform.vehicle_repair.entity.CartDetail;
import com.platform.vehicle_repair.entity.Store;
import com.platform.vehicle_repair.enums.ResultEnum;
import com.platform.vehicle_repair.service.CartService;
import com.platform.vehicle_repair.service.StoreService;
import com.platform.vehicle_repair.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private StoreService storeService;

    @Autowired
    private CartService cartService;

    @GetMapping("/my")
    public ResultVO<Map<Integer,List<CartDetailDTO>>> findMyCart(@RequestParam("userId")String userId){
        List<CartDTO> cartDTOList =  cartService.findByUserId(userId);
        return ResultVOUtil.success(cartDTOList);
    }

    @PostMapping("/add")
    public ResultVO AddCartDetail(@RequestBody CartDetailDTO cartDetailDTO){
        if(cartService.AddCartDetail(cartDetailDTO)){
            return ResultVOUtil.success();
        }else{
            return ResultVOUtil.error(ResultEnum.FAIL_TO_INSERT.getCode(),
                    ResultEnum.FAIL_TO_INSERT.getMessage());
        }
    }

    @PostMapping("/delete")
    public ResultVO DeleteCartDetail(@RequestParam("userId")String userId,
                                     @RequestParam("cartDetailId")Integer cartDetailId){
        CartDetail cartDetail = cartService.findOne(cartDetailId);
        if(cartDetail==null){
            return ResultVOUtil.error(ResultEnum.CART_DETAIL_NOT_EXIT.getCode(),
                    ResultEnum.CART_DETAIL_NOT_EXIT.getMessage());
        }else{
            if(!cartDetail.getUserId().equals(userId)){
                return ResultVOUtil.error(ResultEnum.PERMISSION_ERROR.getCode(),
                        ResultEnum.PERMISSION_ERROR.getMessage(),
                        "不能删除他人的购物车栏");
            }
        }
        try{
            cartService.DeleteCartDetail(cartDetailId);
        }catch(Exception e){
            return ResultVOUtil.error(ResultEnum.FAIL_TO_DELETE.getCode(),
                    ResultEnum.FAIL_TO_DELETE.getMessage());
        }
        return ResultVOUtil.success();
    }

    @PostMapping("/update")
    public ResultVO UpdateCartDetail(@RequestParam("cartDetailId")Integer cartDetailId,
                                     @RequestParam("number")Integer number){
        if(cartService.UpdateCartDetailNumber(cartDetailId,number)){
            return ResultVOUtil.success();
        }else {
            return ResultVOUtil.error(ResultEnum.FAIL_TO_MODIFY.getCode(),
                    ResultEnum.FAIL_TO_MODIFY.getMessage());
        }
    }
}
