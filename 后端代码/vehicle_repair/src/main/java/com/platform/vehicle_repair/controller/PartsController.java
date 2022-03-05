package com.platform.vehicle_repair.controller;

import com.platform.vehicle_repair.VO.ResultVO;
import com.platform.vehicle_repair.converter.Parts2PartsDTOConverter;
import com.platform.vehicle_repair.dto.PartsDTO;
import com.platform.vehicle_repair.entity.Parts;
import com.platform.vehicle_repair.enums.ResultEnum;
import com.platform.vehicle_repair.exception.ServiceException;
import com.platform.vehicle_repair.service.PartService;
import com.platform.vehicle_repair.util.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/parts")
@Slf4j
public class PartsController {

    @Autowired
    private PartService partService;

    /**
     * 查找全部配件列表
     */
    @GetMapping("/all")
    public ResultVO<List<Parts>> findAllPart(@RequestParam(value = "page",defaultValue = "0") Integer page){
        PageRequest request = PageRequest.of(page,6);
        Page<Parts> partsPage = partService.findAll(request);
        return ResultVOUtil.success(partsPage.getContent());

    }

    /**
     * 查找指定店铺的配件
     */
    @RequestMapping("/store")
    public ResultVO<List<Parts>> findPartByStore(@RequestParam(value = "storeId",defaultValue = "-1") Integer storeId,
                                                 @RequestParam(value = "page",defaultValue = "0") Integer page){
        PageRequest request = PageRequest.of(page,6);
        if(storeId==-1){
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(),
                    ResultEnum.PARAM_ERROR.getMessage(),
                    "【查询店铺配件】未选定店铺");
        }
        Page<Parts> partPage = partService.findByStoreId(storeId,request);
        return ResultVOUtil.success(partPage.getContent());
    }

    /**
     * 显示详情
     */
    @GetMapping("/detail")
    public ResultVO<Parts> findParts(@RequestParam("partsId") Integer partsId){
        Parts parts = partService.findOne(partsId);
        PartsDTO result = Parts2PartsDTOConverter.converter(parts);
        if(result==null){
            return ResultVOUtil.error(ResultEnum.NOT_EXIST.getCode(),
                    ResultEnum.NOT_EXIST.getMessage(),
                    "【查询配件】配件信息不存在");
        }
        return ResultVOUtil.success(result);
    }

    /**
     * 模糊查找配件名
     */
    @RequestMapping("/find")
    public ResultVO<List<Parts>> findPartByName(@RequestParam("partsName") String partsName,
                                                @RequestParam(value = "page",defaultValue = "0") Integer page){
        if(partsName == null ||partsName.length()<=0){
            return ResultVOUtil.success();
        }else {
            PageRequest request = PageRequest.of(page, 6);
            Page<Parts> partPage = partService.findByPartsNameLike("%" + partsName + "%", request);
            List<PartsDTO> partsDTOList = Parts2PartsDTOConverter.converter(partPage.getContent());
            return ResultVOUtil.success(partsDTOList);
        }
    }
}
