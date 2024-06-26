package com.banhauniversity.sidalih.service;

import com.banhauniversity.sidalih.entity.Inventory;
import com.banhauniversity.sidalih.entity.Order;
import com.banhauniversity.sidalih.entity.OrderMedicine;
import com.banhauniversity.sidalih.exception.CustomException;
import com.banhauniversity.sidalih.exception.ExceptionMessage;
import com.banhauniversity.sidalih.repository.InventoryRepository;
import com.banhauniversity.sidalih.repository.NotificationRepository;
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

    @Autowired private NotificationRepository notificationRepository;

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

    public Order add(Order order) {
        // Save the order first to generate an ID
        Order savedOrder = orderRepository.saveAndFlush(order);

        // Set the saved order to each OrderMedicine and save them
        for (OrderMedicine orderMedicine : order.getOrderMedicines()) {
            orderMedicine.setOrder(savedOrder);
            orderMedicineRepository.save(orderMedicine);
        }

        // Create corresponding inventory entries
        for (OrderMedicine orderMedicine : savedOrder.getOrderMedicines()) {
            inventoryService.add(Inventory.builder().orderMedicine(orderMedicine).amount(0).build());
        }

        updateNotification();
        return savedOrder;
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

        updateNotification();

        return orderRepository.findById(neworder.getId()).get();
    }

    public void updateNotification(){
        notificationRepository.findAll().forEach(notification -> {
            if (notification.getMedicine().getAlertamount()<inventoryRepository.medicineStatus(notification.getMedicine().getId()).getAmount()){
                notificationRepository.deleteById(notification.getId());
            }
        });
    }
}
