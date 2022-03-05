package com.platform.vehicle_repair.converter;

import com.platform.vehicle_repair.dto.CartDetailDTO;
import com.platform.vehicle_repair.entity.CartDetail;
import com.platform.vehicle_repair.entity.Parts;
import com.platform.vehicle_repair.entity.Service;
import com.platform.vehicle_repair.service.PartService;
import com.platform.vehicle_repair.service.ServiceService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartDetail2CartDetailDTOConverter {

    private static ServiceService serviceService;
    private static PartService partService;

    @Autowired
    public void setServiceService(ServiceService serviceService){
        CartDetail2CartDetailDTOConverter.serviceService = serviceService;
    }

    @Autowired
    public void setPartService(PartService partService){
        CartDetail2CartDetailDTOConverter.partService = partService;
    }

    public static CartDetailDTO convert(CartDetail cartDetail){
        CartDetailDTO cartDetailDTO = new CartDetailDTO();
        BeanUtils.copyProperties(cartDetail,cartDetailDTO);
        int goodsId = cartDetail.getGoodsId();
        if(cartDetail.getType().equals(0)){
            Parts parts = partService.findOne(goodsId);
            cartDetailDTO.setParts(parts);
        }else{
            Service service = serviceService.findOne(goodsId);
            cartDetailDTO.setService(service);
        }
        return cartDetailDTO;

    }

    public static List<CartDetailDTO> convert(List<CartDetail> cartDetailList){
        return cartDetailList.stream().map(e->convert(e)).collect(Collectors.toList());
    }


}
