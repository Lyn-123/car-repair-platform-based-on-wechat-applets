package com.platform.vehicle_repair.controller;

import com.mysql.cj.util.StringUtils;
import com.platform.vehicle_repair.dto.RepairDTO;
import com.platform.vehicle_repair.enums.ResultEnum;
import com.platform.vehicle_repair.service.RepairService;
import com.platform.vehicle_repair.util.ResultVOUtil;
import com.platform.vehicle_repair.VO.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 */
@RestController//返回json格式
@RequestMapping("/repair")//url前缀
@Slf4j
public class RepairController {

    @Autowired
    private RepairService repairService;

    /**
     * 查询用户订单列表
     */
    @GetMapping("/list")
    public ResultVO<List<RepairDTO>> list(@RequestParam("userId") String userId,
                                          @RequestParam(value = "page",defaultValue = "0") Integer page){
        if(StringUtils.isNullOrEmpty(userId)){
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(),
                    ResultEnum.PARAM_ERROR.getMessage(),
                    "【查询订单列表】用户名为空");
        }
        PageRequest request = PageRequest.of(page,6);
        Page<RepairDTO> repairDTOPage = repairService.findList(userId,request);
        return ResultVOUtil.success(repairDTOPage.getContent());
    }

    /**
     * 订单详情
     */
    @GetMapping("/detail")
    public ResultVO<RepairDTO> detail(@RequestParam("userId") String userId,
                                      @RequestParam("repairId") String repairId) {
        //如果所有人指导单号就可以查，那也可以查看别人的
        RepairDTO repairDTO = repairService.findOne(repairId);
        if (!repairDTO.getUserId().equals(userId)) {
            return ResultVOUtil.error(ResultEnum.PERMISSION_ERROR.getCode(),
                    ResultEnum.PERMISSION_ERROR.getMessage(),
                    "【查询维修单】无权限查询他人的维修单");
        }
        return ResultVOUtil.success(repairDTO);

    }
    /**
     * 完成订单
     */
    @GetMapping("/finish")
    public ResultVO finish(@RequestParam("userId") String userId,
                           @RequestParam("repairId") String repairId) {
        //如果所有人指导单号就可以查，那也可以查看别人的
        RepairDTO repairDTO = repairService.findOne(repairId);
        if (!repairDTO.getUserId().equals(userId)) {
            return ResultVOUtil.error(ResultEnum.PERMISSION_ERROR.getCode(),
                    ResultEnum.PERMISSION_ERROR.getMessage(),
                    "【完成维修单】无权限修改他人的维修单");
        }
        if(repairService.finishRepair(repairId)){
            return ResultVOUtil.success();
        }else{
            return ResultVOUtil.error(ResultEnum.FAIL_TO_MODIFY.getCode(),
                    ResultEnum.FAIL_TO_MODIFY.getMessage());
        }
    }

    /**
     * 取消订单
     */
    @GetMapping("/cancel")
    public ResultVO cancel(@RequestParam("userId") String userId,
                           @RequestParam("repairId") String repairId) {
        //如果所有人指导单号就可以查，那也可以查看别人的
        RepairDTO repairDTO = repairService.findOne(repairId);
        if (!repairDTO.getUserId().equals(userId)) {
            return ResultVOUtil.error(ResultEnum.PERMISSION_ERROR.getCode(),
                    ResultEnum.PERMISSION_ERROR.getMessage(),
                    "【取消维修单】无权限取消他人的维修单");
        }
        if(repairService.cancelRepair(repairId)){
            return ResultVOUtil.success();
        }else{
            return ResultVOUtil.error(ResultEnum.FAIL_TO_MODIFY.getCode(),
                    ResultEnum.FAIL_TO_MODIFY.getMessage());
        }
    }


}
