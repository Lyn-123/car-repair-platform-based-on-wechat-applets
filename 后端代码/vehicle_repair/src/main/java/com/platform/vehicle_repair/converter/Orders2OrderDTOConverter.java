package com.platform.vehicle_repair.converter;

import com.platform.vehicle_repair.dto.OrderDTO;
import com.platform.vehicle_repair.dto.OrderDetailDTO;
import com.platform.vehicle_repair.entity.*;
import com.platform.vehicle_repair.service.PartService;
import com.platform.vehicle_repair.service.ServiceService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Orders2OrderDTOConverter {

    private static ServiceService serviceService;
    private static PartService partService;

    @Autowired
    public void setServiceService(ServiceService serviceService){
        Orders2OrderDTOConverter.serviceService = serviceService;
    }
    @Autowired
    public void setPartService(PartService partService){
        Orders2OrderDTOConverter.partService = partService;
    }

    public static OrderDTO convert(Orders orders){
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orders,orderDTO);
        return orderDTO;
    }
    public static OrderDetailDTO convert(OrderDetail orderDetail){
        OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
        BeanUtils.copyProperties(orderDetail,orderDetailDTO);
        int goodsId = orderDetail.getGoodsId();
        int type = orderDetail.getType();
        if(type==1){
            Service service = serviceService.findOne(goodsId);
            orderDetailDTO.setService(service);
        }else{
            Parts parts = partService.findOne(goodsId);
            orderDetailDTO.setParts(parts);
        }
        return orderDetailDTO;
    }
    public static List<OrderDetailDTO> convert(List<OrderDetail> orderDetailList){
        return orderDetailList.stream().map(e->
                convert(e)
        ).collect(Collectors.toList());
    }
}
