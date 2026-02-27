package com.co_notice.repo;

import com.co_notice.entities.CoTriggerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoTriggerRepo
        extends JpaRepository<CoTriggerEntity, Long> {

    List<CoTriggerEntity> findByTriggerStatus(String status);

    CoTriggerEntity findByCaseNum(Long caseNum);
}