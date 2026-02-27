package com.co_notice.repo;

import com.co_notice.entities.EDEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EDRepo
        extends JpaRepository<EDEntity, Long> {

    EDEntity findByCaseNum(Long caseNum);
}
