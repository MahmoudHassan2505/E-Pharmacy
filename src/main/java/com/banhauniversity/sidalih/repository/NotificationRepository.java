package com.banhauniversity.sidalih.repository;

import com.banhauniversity.sidalih.entity.Medicine;
import com.banhauniversity.sidalih.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {

    Optional<Notification> findByMedicine(Medicine medicine);
}
