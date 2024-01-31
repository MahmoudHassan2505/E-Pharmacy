package com.banhauniversity.sidalih.service;

import com.banhauniversity.sidalih.entity.Inventory;
import com.banhauniversity.sidalih.entity.Order;
import com.banhauniversity.sidalih.entity.OrderMedicine;
import com.banhauniversity.sidalih.exception.CustomException;
import com.banhauniversity.sidalih.exception.ExceptionMessage;
import com.banhauniversity.sidalih.repository.InventoryRepository;
import com.banhauniversity.sidalih.repository.OrderMedicineRepository;
import com.banhauniversity.sidalih.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    InventoryRepository inventoryRepository;
    @Autowired
    OrderMedicineRepository orderMedicineRepository;
    @Autowired
    InventoryService inventoryService;

    public List<Order> findAll(){
        return orderRepository.findAll();
    }

    public Order findById(long id){
        orderRepository.findById(id).orElseThrow(()->new CustomException(ExceptionMessage.ID_Not_Found));
        return orderRepository.findById(id).get();
    }

    public List<Order> findBySupplier(Long id){
        return orderRepository.findAllBySupplierId(id);
    }

    public Order add(Order order){
        orderRepository.findById(order.getId()).ifPresent((a)->{
            throw new CustomException(ExceptionMessage.ID_is_Exist);
        });

        Order savedOrder = orderRepository.save(order);

        order.getOrderMedicines().forEach(orderMedicine -> {
            orderMedicine.setOrder(savedOrder);
            orderMedicineRepository.save(orderMedicine);
        });

        order.getOrderMedicines().forEach((orderMedicine)->{
            inventoryService.add(Inventory.builder().medicine(orderMedicine.getMedicine()).amount(orderMedicine.getAmount()).price(orderMedicine.getPrice()).expireDate(orderMedicine.getExpirydate()).build());
        });
        return order;
    }

    public void delete(long id){
        orderRepository.findById(id).orElseThrow(()->new CustomException(ExceptionMessage.ID_Not_Found));
        orderRepository.deleteById(id);
    }

    public Order update(Order order){
        orderRepository.findById(order.getId()).orElseThrow(()->new CustomException(ExceptionMessage.ID_Not_Found));
        return orderRepository.save(order);

    }
}
