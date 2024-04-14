package com.banhauniversity.sidalih.repository;

import com.banhauniversity.sidalih.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitRepository extends JpaRepository<Unit,Long> {

    List<Unit> findAllByType(long type);
}
