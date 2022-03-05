package com.platform.vehicle_repair.converter;

import com.platform.vehicle_repair.dto.CartDetailDTO;
import com.platform.vehicle_repair.entity.CartDetail;

public class CartDetailDTO2CartDetailConverter {
    public static CartDetail convert(CartDetailDTO cartDetailDTO){
        CartDetail cartDetail = new CartDetail();
        cartDetail.setNumber(cartDetailDTO.getNumber());
        cartDetail.setStoreId(cartDetailDTO.getStoreId());
        cartDetail.setUserId(cartDetailDTO.getUserId());
        if(cartDetailDTO.getParts()!=null){
            cartDetail.setType(0);
            cartDetail.setGoodsId(cartDetailDTO.getParts().getPartsId());
        }else if(cartDetailDTO.getService()!=null){
            cartDetail.setType(1);
            cartDetail.setGoodsId(cartDetailDTO.getService().getServiceId());
        }
        return cartDetail;
    }
}
