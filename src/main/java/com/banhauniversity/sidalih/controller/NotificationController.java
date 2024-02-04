package com.banhauniversity.sidalih.controller;

import com.banhauniversity.sidalih.entity.Notification;
import com.banhauniversity.sidalih.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pharmacy/notifications")
public class NotificationController {

    @Autowired private NotificationService notificationService;

    @GetMapping
    public List<Notification> findAll(){
        return notificationService.findAll();
    }


}
