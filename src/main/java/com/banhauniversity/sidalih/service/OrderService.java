package com.banhauniversity.sidalih.service;

import com.banhauniversity.sidalih.entity.Inventory;
import com.banhauniversity.sidalih.entity.Order;
import com.banhauniversity.sidalih.entity.OrderMedicine;
import com.banhauniversity.sidalih.exception.CustomException;
import com.banhauniversity.sidalih.exception.ExceptionMessage;
import com.banhauniversity.sidalih.repository.InventoryRepository;
import com.banhauniversity.sidalih.repository.OrderMedicineRepository;
import com.banhauniversity.sidalih.repository.OrderRepository;
import jakarta.transaction.Transactional;
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

        orderRepository.findById(savedOrder.getId()).get().getOrderMedicines().forEach((orderMedicine)->{
            inventoryService.add(Inventory.builder().orderMedicine(orderMedicine).amount(0).build());
        });
        return order;
    }

    public void delete(long id){
        orderRepository.findById(id).orElseThrow(()->new CustomException(ExceptionMessage.ID_Not_Found));
        orderRepository.deleteById(id);
    }

    public Order update(Order neworder){
        Order oldOrder = orderRepository.findById(neworder.getId()).orElseThrow(()-> new CustomException(ExceptionMessage.ID_Not_Found));

        orderRepository.save(neworder);

        //find deleted item from updating
        oldOrder.getOrderMedicines().removeIf(orderMedicine -> {
            for (OrderMedicine newOrderMedicine: neworder.getOrderMedicines()) {
                if(newOrderMedicine.getId()==orderMedicine.getId()){
                    return true;
                }
            }
            return false;
        });

        oldOrder.getOrderMedicines().forEach(orderMedicine -> {
            inventoryService.deleteByOrderMedicineId(orderMedicine.getId());
            orderMedicineRepository.deleteById(orderMedicine.getId());
        });

        neworder.getOrderMedicines().forEach( orderMedicine -> {
                orderMedicine.setOrder(neworder);
                OrderMedicine newOrderMedicine = orderMedicineRepository.save(orderMedicine);

                if(inventoryService.findByOrderMedicineId(orderMedicine.getId()).isEmpty()){
                    inventoryService.add(Inventory.builder().orderMedicine(newOrderMedicine).amount(0).build());
                }

        });

        return orderRepository.findById(neworder.getId()).get();
    }
}
