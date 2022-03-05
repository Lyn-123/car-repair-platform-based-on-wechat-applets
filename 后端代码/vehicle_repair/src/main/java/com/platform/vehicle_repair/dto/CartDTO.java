package com.platform.vehicle_repair.dto;

import com.platform.vehicle_repair.entity.Store;
import lombok.Data;

import java.util.List;

@Data
public class CartDTO {
    Store store;
    List<CartDetailDTO> cartDetailDTOList;
}
