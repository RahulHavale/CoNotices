package com.co_notice.repo;

import com.co_notice.entities.DcCaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DcCaseRepo
        extends JpaRepository<DcCaseEntity, Long> {
}
