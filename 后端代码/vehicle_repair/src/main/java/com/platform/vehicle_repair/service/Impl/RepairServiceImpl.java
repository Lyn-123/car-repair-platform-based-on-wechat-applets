package com.platform.vehicle_repair.service.Impl;

import com.mysql.cj.util.StringUtils;
import com.platform.vehicle_repair.converter.Repair2RepairDTOConverter;
import com.platform.vehicle_repair.dao.RepairDetailRepository;
import com.platform.vehicle_repair.dao.RepairRepository;
import com.platform.vehicle_repair.dto.RepairDTO;
import com.platform.vehicle_repair.dto.RepairDetailDTO;
import com.platform.vehicle_repair.entity.Parts;
import com.platform.vehicle_repair.entity.Repair;
import com.platform.vehicle_repair.entity.RepairDetail;
import com.platform.vehicle_repair.enums.ResultEnum;
import com.platform.vehicle_repair.enums.StateEnum;
import com.platform.vehicle_repair.exception.ServiceException;
import com.platform.vehicle_repair.service.RepairService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@org.springframework.stereotype.Service
public class RepairServiceImpl implements RepairService {

    @Autowired
    private RepairRepository repairRepository;

    @Autowired
    private RepairDetailRepository repairDetailRepository;

    @Override
    @Transactional
    public RepairDTO  findOne(String repairId) {
        //如果repair为空，这个维修单不存在
        if(!repairRepository.findById(repairId).isPresent()){
            throw new ServiceException(ResultEnum.REPAIR_NOT_EXIT);
        }
        //获取repair
        Repair repair = repairRepository.findById(repairId).get();
        //获取repairDetail
        List<RepairDetail> repairDetailList = repairDetailRepository.findByRepairId(repairId);
        //判断获取的repairDetail列表为空
        if(CollectionUtils.isEmpty(repairDetailList)){
            throw new ServiceException(ResultEnum.DETAIL_NOT_EXIT);
        }
        //转换repairDetail为repairDetailDTO
        List<RepairDetailDTO> repairDetailDTOList = repairDetailList.stream().map(e ->
                Repair2RepairDTOConverter.convert(e)
        ).collect(Collectors.toList());
        //转换为DTO返回
        RepairDTO repairDTO = Repair2RepairDTOConverter.convert(repair);
        repairDTO.setRepairDetailDTOList(repairDetailDTOList);
        return repairDTO;
    }

    @Override
    @Transactional
    public Page<RepairDTO> findList(String userId, Pageable pageable) {
        Page<Repair> repairPage = repairRepository.findByUserId(userId, pageable);
        List<RepairDTO> repairDTOList = Repair2RepairDTOConverter.convert(repairPage.getContent());
        for(RepairDTO repairDTO:repairDTOList){
            String repairId = repairDTO.getRepairId();
            List<RepairDetail> repairDetailList = repairDetailRepository.findByRepairId(repairId);
            if(CollectionUtils.isEmpty(repairDetailList)){
                throw new ServiceException(ResultEnum.DETAIL_NOT_EXIT);
            }
            List<RepairDetailDTO> repairDetailDTOList = new ArrayList<RepairDetailDTO>();
            for(RepairDetail repairDetail:repairDetailList){
                repairDetailDTOList.add(Repair2RepairDTOConverter.convert(repairDetail));
            }
            repairDTO.setRepairDetailDTOList(repairDetailDTOList);
        }
        Page<RepairDTO> repairDTOPage = new PageImpl<RepairDTO>(repairDTOList,pageable,repairPage.getTotalElements());
        return repairDTOPage;
    }

    /*取消订单*/
    @Override
    @Transactional
    public boolean cancelRepair(String repairId){
        boolean result = true;
        Repair repair = repairRepository.findById(repairId).get();
        int state = repair.getState();
        //判断已有状态
        if(state==0){
            repair.setState(StateEnum.CANCEL.getCode());
            Repair updateResult = repairRepository.save(repair);
            if(updateResult==null){
                result = false;
            }
        }else{
            result = false;
        }
        return result;
    }

    /*完成服务*/
    @Override
    @Transactional
    public boolean finishRepair(String repairId){
        boolean result = true;
        Repair repair = repairRepository.findById(repairId).get();
        int state = repair.getState();
        //判断已有状态
        if(state!=3&&state!=4){
            repair.setState(StateEnum.FINISH.getCode());
            Repair updateResult = repairRepository.save(repair);
            if(updateResult==null){
                result = false;
            }
        }else{
            result = false;
        }
        return result;
    }

    @Override
    public List<Repair> findByStateIn(List<Integer> stateList) {
        return repairRepository.findByStateIn(stateList);
    }

//    public RepairDTO create(RepairDTO repairDTO) {
//
//        String repairId = KeyUtil.geUniqueKey();
//        BigDecimal amount = new BigDecimal(BigInteger.ZERO);
//        //1、查询商品
//        for(RepairDetail repairDetail:repairDTO.getRepairDetailList() ){
//            Service service = serviceService.findOne(repairDetail.getServiceId());
//            if(service==null){
//                throw new ServiceException(ResultEnum.NOT_EXIST);
//            }
//            //2、计算总价
//            amount = repairDetail.getPrice()
//                    .multiply(new BigDecimal(repairDetail.getNumber()))
//                    .add(amount);
//            //订单详情入库
//
//            repairDetail.setRepairId(repairId);
//            BeanUtils.copyProperties(service,repairDetail);
//            repairDetailRepository.save(repairDetail);
//
//        }
//
//        //3、写入订单数据库（repairDetail和repair）
//        Repair repair = new Repair();
//        repair.setRepairId(repairId);
//        //？要设置总价吗
//        BeanUtils.copyProperties(repairDTO,repair);
//
//        repairRepository.save(repair);
//
//        //4、扣库存
//        return null;
//    }


//    @Transactional
//    public RepairDTO cancel(RepairDTO repairDTO) {
//        Repair repair = new Repair();
//        //判断已有状态
//        if(repairDTO.getState().equals(RepairStateEnum.REPAIRED)){
//            log.error("【取消订单】订单已完成，无法取消订单,repairId={},repairStatus={}",repairDTO.getRepairId(),repairDTO.getState());
//            throw new ServiceException(ResultEnum.REPAIR_STATUS_ERROR);
//        }
//        //修改订单状态
//        repairDTO.setState(RepairStateEnum.CANCEL.getCode());
//        BeanUtils.copyProperties(repairDTO,repair);
//        Repair updateResult = repairRepository.save(repair);
//        if(updateResult==null){
//            log.error("【取消订单】更新失败，repair={}",repair);
//            throw new ServiceException(ResultEnum.REPAIR_STATUS_ERROR);
//        }
//        //返回库存
//        if(CollectionUtils.isEmpty(repairDTO.getRepairDetailDTOList())){
//            log.error("【取消订单】订单中无详情，repairDTO={}",repairDTO);
//            throw new ServiceException(ResultEnum.REPAIR_STATUS_ERROR);
//        }
//        return repairDTO;
//    }

    //    @Transactional
//    public RepairDTO finish(RepairDTO repairDTO) {
//        //判断订单状态
//        if(!repairDTO.getState().equals(RepairStateEnum.REPAIRED.getCode())){
//            log.error("【完结订单】状态不正确,repairId={},repairStatus={}",repairDTO.getRepairId(),repairDTO.getState());
//            throw new ServiceException(ResultEnum.REPAIR_STATUS_ERROR);
//        }
//        //修改状态
//        repairDTO.setState(RepairStateEnum.FINISH.getCode());
//        Repair repair = new Repair();
//        BeanUtils.copyProperties(repairDTO,repair);
//        Repair updateResult = repairRepository.save(repair);
//        if(updateResult==null){
//            log.error("【完结订单】更新失败，repair={}",repair);
//            throw new ServiceException(ResultEnum.REPAIR_STATUS_ERROR);
//        }
//        return repairDTO;
//    }
}
