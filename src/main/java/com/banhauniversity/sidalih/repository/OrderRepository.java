package com.banhauniversity.sidalih.repository;

import com.banhauniversity.sidalih.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findAllBySupplierId(long id);
}
