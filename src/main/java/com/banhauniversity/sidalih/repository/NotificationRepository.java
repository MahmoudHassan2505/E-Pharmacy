package com.banhauniversity.sidalih.repository;

import com.banhauniversity.sidalih.entity.Medicine;
import com.banhauniversity.sidalih.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {

    Optional<Notification> findByMedicineAndMessage(Medicine medicine,String message);

    Optional<Notification>findByOrderid(long orderid);

    @Query("select count(n.id) from Notification as n where n.message =:message ")
    int getState(@Param("message") String message);

}
