package com.platform.vehicle_repair.dao;

import com.platform.vehicle_repair.entity.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface CartDetailRepository extends JpaRepository<CartDetail,Integer> {
    List<CartDetail> findByUserId(String userId);

    @Transactional
    void deleteById(Integer id);

    @Transactional
    @Modifying
    @Query(value = "update cart_detail c set c.number=?2 where c.id = ?1",nativeQuery = true)
    Integer updateNumber(Integer id,Integer number);

}
