package com.banhauniversity.sidalih.controller;

import com.banhauniversity.sidalih.entity.Order;
import com.banhauniversity.sidalih.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/pharmacy/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<Order> findAll(){
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public Order findById(@PathVariable long id){
        return orderService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order add(@RequestBody Order order){
        return orderService.add(order);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id){
        orderService.delete(id);
    }

    @PutMapping
    public Order update(@RequestBody Order order){
        return orderService.update(order);
    }

    @GetMapping("/supplier/{id}")
    public List<Order> findBySupplier(@PathVariable Long id){
        return orderService.findBySupplier(id);
    }
}
