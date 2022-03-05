package com.platform.vehicle_repair.service;

import com.platform.vehicle_repair.dto.CartDTO;
import com.platform.vehicle_repair.dto.CartDetailDTO;
import com.platform.vehicle_repair.entity.CartDetail;

import java.util.List;

public interface CartService {
    /**
     * 查看用户的购物车
     */
    List<CartDTO> findByUserId(String userId);

    /**
     * 查找购物车项目
     */
    CartDetail findOne(Integer id);
    /**
     * 加购物车项目
     */
    boolean AddCartDetail(CartDetailDTO cartDetailDTO);

    /**
     * 删除购物车项目
     */
    boolean DeleteCartDetail(Integer cartDetailId);

    /**
     * 修改购物车商品数目
     */
    boolean UpdateCartDetailNumber(Integer cartDetailId,Integer number);
}
