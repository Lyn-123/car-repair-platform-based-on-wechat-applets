package com.platform.vehicle_repair.service.Impl;

import com.platform.vehicle_repair.converter.CartDetail2CartDetailDTOConverter;
import com.platform.vehicle_repair.converter.CartDetailDTO2CartDetailConverter;
import com.platform.vehicle_repair.dao.CartDetailRepository;
import com.platform.vehicle_repair.dao.StoreRepository;
import com.platform.vehicle_repair.dto.CartDTO;
import com.platform.vehicle_repair.dto.CartDetailDTO;
import com.platform.vehicle_repair.entity.CartDetail;
import com.platform.vehicle_repair.entity.Store;
import com.platform.vehicle_repair.service.CartService;
import com.platform.vehicle_repair.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartDetailRepository cartDetailRepository;
    @Autowired
    private StoreRepository storeRepository;

    /**
     * 查看用户的购物车
     */
    @Override
    public List<CartDTO> findByUserId(String userId){
        List<CartDetail> cartDetailList = cartDetailRepository.findByUserId(userId);
        List<CartDetailDTO> cartDetailDTOList = CartDetail2CartDetailDTOConverter.convert(cartDetailList);
        Map<Integer,List<CartDetailDTO>> resultMap = new HashMap<Integer,List<CartDetailDTO>>();
        for(CartDetailDTO cartDetailDTO:cartDetailDTOList){
            Integer storeId = cartDetailDTO.getStoreId();
            List<CartDetailDTO> list = new ArrayList<>();
            if(resultMap.containsKey(storeId)){
                list = resultMap.get(storeId);
            }
            list.add(cartDetailDTO);
            resultMap.put(storeId,list);
        }
        List<CartDTO> cartDTOList = new ArrayList<>();
        for(Map.Entry<Integer,List<CartDetailDTO>> entry:resultMap.entrySet()){
            CartDTO cartDTO = new CartDTO();
            cartDTO.setCartDetailDTOList(entry.getValue());
            Store store = storeRepository.findById(entry.getKey()).get();
            cartDTO.setStore(store);
            cartDTOList.add(cartDTO);
        }
        return cartDTOList;
    }

    /**
     * 查找购物车项目
     */
    @Override
    public CartDetail findOne(Integer id){
        if(cartDetailRepository.findById(id).isPresent()){
            return cartDetailRepository.findById(id).get();
        }else{
            return null;
        }
    }

    /**
     * 加购物车
     */
    @Transactional
    public boolean AddCartDetail(CartDetailDTO cartDetailDTO){
        CartDetail cartDetail = CartDetailDTO2CartDetailConverter.convert(cartDetailDTO);
        CartDetail result = cartDetailRepository.save(cartDetail);
        if(result==null){
            return false;
        }else{
            return true;
        }
    }

    /**
     * 删除购物车项目
     */
    @Transactional
    public boolean DeleteCartDetail(Integer cartDetailId){
        try{
            cartDetailRepository.deleteById(cartDetailId);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    /**
     * 修改购物车商品数目
     */
    @Transactional
    public boolean UpdateCartDetailNumber(Integer cartDetailId,Integer number){
        int result = cartDetailRepository.updateNumber(cartDetailId,number);
        if(result==0){
            return false;
        }else{
            return true;
        }
    }
}
