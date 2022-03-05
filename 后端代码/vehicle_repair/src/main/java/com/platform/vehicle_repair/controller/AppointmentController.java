package com.platform.vehicle_repair.controller;

import antlr.StringUtils;
import com.platform.vehicle_repair.VO.ResultVO;
import com.platform.vehicle_repair.converter.AppointmentForm2AppointmentConverter;
import com.platform.vehicle_repair.entity.Appointment;
import com.platform.vehicle_repair.entity.Parts;
import com.platform.vehicle_repair.enums.ResultEnum;
import com.platform.vehicle_repair.exception.ServiceException;
import com.platform.vehicle_repair.form.AppointmentForm;
import com.platform.vehicle_repair.service.AppointmentService;
import com.platform.vehicle_repair.util.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/appoint")
@Slf4j
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    /**
     * 创建预约维修单
     */
    @PostMapping("/create")
    public ResultVO<Map<String, String>> createAppointment(@Valid AppointmentForm appointmentForm,
                                                           BindingResult bindingResult) {
        //判断表单校验是否全部正确,如有错误则返回错误信息
        if (bindingResult.hasErrors()) {
            Map<String, String> error = new HashMap<>();
            error.put("errorMsg", bindingResult.getFieldError().getDefaultMessage());
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(),
                    ResultEnum.PARAM_ERROR.getMessage(), error);
        }
        //将表单转化为实体类
        Appointment appointment = AppointmentForm2AppointmentConverter.convert(appointmentForm);
        //数据库中添加预约维修单
        Appointment createResult = appointmentService.createOrUpdate(appointment);
        //返回预约维修单ID
        Map<String, String> map = new HashMap<>();
        map.put("appointId", createResult.getAppointId());
        return ResultVOUtil.success(map);
    }

    /**
     * 查询单个预约订单
     */
    @GetMapping("/detail")
    public ResultVO findOne(@RequestParam("appointId") String appointId){
        Appointment result = appointmentService.findOne(appointId);
        if(result==null){
            log.error("【查询订单】订单信息不存在 result={}",result.toString());
            throw new ServiceException(ResultEnum.NOT_EXIST);
        }
        return ResultVOUtil.success(result);
    }

    /**
     * 批量查询预约订单
     */
    @GetMapping("/list")
    public ResultVO<List<Appointment>> findAppointByUser(@RequestParam("userId") String userId,
                                                 @RequestParam(value = "page",defaultValue = "0") Integer page){
        PageRequest request = PageRequest.of(page,20);
        if(userId==null){
            return ResultVOUtil.error(ResultEnum.PERMISSION_ERROR.getCode(),
                    ResultEnum.PERMISSION_ERROR.getMessage(),
                    "用户未登录");
        }
        Page<Appointment> appointmentPage = appointmentService.findByUserId(userId,request);
        return ResultVOUtil.success(appointmentPage.getContent());
    }

    /**
     * 删除/取消订单
     */
    @PostMapping("/delete")
    public ResultVO deleteAppoint(@RequestParam("userId") String userId,
                                  @RequestParam("appointId")String appointId){
        if(userId==null||userId.equals("")){
            return ResultVOUtil.error(ResultEnum.PERMISSION_ERROR.getCode(),
                    ResultEnum.PERMISSION_ERROR.getMessage(),
                    "用户未登录");
        }
        Appointment appointment = appointmentService.findOne(appointId);
        if(userId.equals(appointment.getUserId())){
            try{
                appointmentService.deleteByAppointId(appointId);
            }catch (Exception e){
                return ResultVOUtil.error(ResultEnum.FAIL_TO_DELETE.getCode(),
                        ResultEnum.FAIL_TO_DELETE.getMessage(),
                        e.getMessage());
            }
        }else{
            return ResultVOUtil.error(ResultEnum.PERMISSION_ERROR.getCode(),
                    ResultEnum.PERMISSION_ERROR.getMessage(),
                    "用户无权删除他人订单");
        }
        return ResultVOUtil.success();
    }
}
