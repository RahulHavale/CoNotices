package com.co_notice.repo;

import com.co_notice.entities.EDEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EDRepo extends JpaRepository<EDEntity, Long> {

    @Query("Select e from EDEntity e where e.caseNum.caseNum= :caseNum")
    public EDEntity findByCaseNum(@Param("caseNum")Long caseNum);
}
