package com.platform.vehicle_repair.controller;

import com.platform.vehicle_repair.VO.ResultVO;
import com.platform.vehicle_repair.dto.OrderDTO;
import com.platform.vehicle_repair.entity.Orders;
import com.platform.vehicle_repair.enums.ResultEnum;
import com.platform.vehicle_repair.service.OrderService;
import com.platform.vehicle_repair.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/my")
    public ResultVO findMyOrders(@RequestParam("userId")String userId){
        if(userId==null){
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(),
                    ResultEnum.PERMISSION_ERROR.getMessage(),
                    "【查询预约保养单】请先登录");
        }
        List<OrderDTO> orderDTOList = orderService.findByUserId(userId);
        return ResultVOUtil.success(orderDTOList);
    }

    @GetMapping("/detail")
    public ResultVO findOrder(@RequestParam("userId")String userId,
                              @RequestParam("orderId")String orderId){
        if(userId==null){
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(),
                    ResultEnum.PERMISSION_ERROR.getMessage(),
                    "【查询预约保养单】请先登录");
        }
        OrderDTO orderDTO = orderService.findOne(orderId);
        if(!orderDTO.getUserId().equals(userId)){
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(),
                    ResultEnum.PERMISSION_ERROR.getMessage(),
                    "【查询预约保养单】无权限查询他人的预约保养单");
        }
        return ResultVOUtil.success(orderDTO);
    }

    @GetMapping("/cancel")
    public ResultVO cancelOrders(@RequestParam("orderId")String orderId,
                                 @RequestParam("userId")String userId){
        if(userId==null){
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(),
                    ResultEnum.PERMISSION_ERROR.getMessage(),
                    "【取消预约保养单】请先登录");
        }
        OrderDTO orderDTO = orderService.findOne(orderId);
        if(!orderDTO.getUserId().equals(userId)){
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(),
                    ResultEnum.PERMISSION_ERROR.getMessage(),
                    "【取消预约保养单】无权限取消他人的预约保养单");
        }
        if(!orderService.cancelOrders(orderId)){
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(),
                    ResultEnum.PERMISSION_ERROR.getMessage(),
                    "【取消预约保养单】取消预约保养单失败");
        }else{
            return ResultVOUtil.success();
        }
    }

    @GetMapping("/finish")
    public ResultVO finishOrders(@RequestParam("orderId")String orderId,
                                 @RequestParam("userId")String userId){
        if(userId==null){
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(),
                    ResultEnum.PERMISSION_ERROR.getMessage(),
                    "【结束预约保养单】请先登录");
        }
        OrderDTO orderDTO = orderService.findOne(orderId);
        if(!orderDTO.getUserId().equals(userId)){
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(),
                    ResultEnum.PERMISSION_ERROR.getMessage(),
                    "【结束预约保养单】无权限结束他人的预约保养单");
        }
        if(!orderService.finishOrders(orderId)){
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(),
                    ResultEnum.PERMISSION_ERROR.getMessage(),
                    "【结束预约保养单】结束预约保养单失败");
        }else{
            return ResultVOUtil.success();
        }
    }

    @PostMapping("/add")
    public ResultVO addOrders(@RequestBody OrderDTO orderDTO){
        String orderId = orderService.addOrders(orderDTO);
        Map result = new HashMap();
        result.put("orderId",orderId);
        return ResultVOUtil.success(result);
    }
}
