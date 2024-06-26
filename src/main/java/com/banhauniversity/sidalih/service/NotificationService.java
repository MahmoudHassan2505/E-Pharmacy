package com.banhauniversity.sidalih.service;

import com.banhauniversity.sidalih.entity.Notification;
import com.banhauniversity.sidalih.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired private NotificationRepository notificationRepository;

    public List<Notification> findAll() {

        return notificationRepository.findAll();
    }

    public int getRunningOut() {
        return notificationRepository.getState("أوشك علي النفاد");
    }

    public int getAboutToExpire() {
        return notificationRepository.getState("أوشك علي الانتهاء");
    }
}
