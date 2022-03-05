package com.platform.vehicle_repair.service.Impl;

import com.platform.vehicle_repair.VO.ResultVO;
import com.platform.vehicle_repair.converter.Orders2OrderDTOConverter;
import com.platform.vehicle_repair.dao.OrderDetailRepository;
import com.platform.vehicle_repair.dao.OrderRepository;
import com.platform.vehicle_repair.dao.PartsRepository;
import com.platform.vehicle_repair.dao.ServiceRepository;
import com.platform.vehicle_repair.dto.OrderDTO;
import com.platform.vehicle_repair.dto.OrderDetailDTO;
import com.platform.vehicle_repair.entity.OrderDetail;
import com.platform.vehicle_repair.entity.Orders;
import com.platform.vehicle_repair.entity.Parts;
import com.platform.vehicle_repair.enums.ResultEnum;
import com.platform.vehicle_repair.enums.StateEnum;
import com.platform.vehicle_repair.exception.ServiceException;
import com.platform.vehicle_repair.service.OrderService;
import com.platform.vehicle_repair.util.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    /**
     * 查询个人全部预约保养单列表
     */
    @Override
    @Transactional
    public List<OrderDTO> findByUserId(String userId){
        List<Orders> orderList = orderRepository.findByUserId(userId);
        List<OrderDTO> orderDTOList = new ArrayList<OrderDTO>();
        for(Orders orders:orderList){
            OrderDTO orderDTO = Orders2OrderDTOConverter.convert(orders);
            List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orders.getOrderId());
            List<OrderDetailDTO> orderDetailDTOList = Orders2OrderDTOConverter.convert(orderDetailList);
            orderDTO.setOrderDetailDTOList(orderDetailDTOList);
            orderDTOList.add(orderDTO);
        }
        return orderDTOList;
    }

    /**
     * 查询预约保养单详情
     */
    @Override
    @Transactional
    public OrderDTO findOne(String orderId) {
        if(!orderRepository.findById(orderId).isPresent()){
            throw new ServiceException(ResultEnum.ORDER_NOT_EXIT);
        }
        Orders orders = orderRepository.findById(orderId).get();
        OrderDTO orderDTO = Orders2OrderDTOConverter.convert(orders);
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if(CollectionUtils.isEmpty(orderDetailList)){
            throw new ServiceException(ResultEnum.ORDER_DETAIL_NOT_EXIT);
        }
        List<OrderDetailDTO> orderDetailDTOList = Orders2OrderDTOConverter.convert(orderDetailList);
        orderDTO.setOrderDetailDTOList(orderDetailDTOList);
        return orderDTO;
    }

    /**
     * 取消预约保养单
     */
    @Override
    @Transactional
    public boolean cancelOrders(String orderId){
        Orders orders = orderRepository.findById(orderId).get();
        int state = orders.getState();
        if(state!=0){
            return false;
        }else{
            orders.setState(StateEnum.CANCEL.getCode());
            Orders result = orderRepository.save(orders);
            if(result==null){
                return false;
            }
            return true;
        }
    }

    /**
     * 结束预约保养单
     */
    @Override
    @Transactional
    public boolean finishOrders(String orderId){
        Orders orders = orderRepository.findById(orderId).get();
        int state = orders.getState();
        if(state==3||state==4){
            return false;
        }else{
            orders.setState(StateEnum.FINISH.getCode());
            Orders result = orderRepository.save(orders);
            if(result==null){
                return false;
            }
            return true;
        }
    }

    /**
     * 新建预约保养单
     */
    public String addOrders(OrderDTO orderDTO){
        //存orders
        Orders orders = new Orders();
        String orderId = KeyUtil.geUniqueKey();
        BeanUtils.copyProperties(orderDTO,orders);
        orders.setOrderId(orderId);
        orders.setState(0);
        orderRepository.save(orders);
        //存orderDetail
        List<OrderDetailDTO> orderDetailDTOList = orderDTO.getOrderDetailDTOList();
        for(OrderDetailDTO orderDetailDTO:orderDetailDTOList){
            OrderDetail orderDetail = new OrderDetail();
            BeanUtils.copyProperties(orderDetailDTO,orderDetail);
            orderDetail.setOrderId(orderId);
            if(orderDetailDTO.getParts()!=null){
                orderDetail.setType(0);
                orderDetail.setGoodsId(orderDetailDTO.getParts().getPartsId());
            }else{
                orderDetail.setType(1);
                orderDetail.setGoodsId(orderDetailDTO.getService().getServiceId());
            }
            orderDetailRepository.save(orderDetail);
        }
        return orderId;
    }

}
