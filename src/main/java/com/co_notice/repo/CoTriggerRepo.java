package com.co_notice.repo;

import com.co_notice.entities.COEntity;
import com.co_notice.entities.CoTriggerEntity;
import com.co_notice.entities.EDEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CoTriggerRepo extends JpaRepository<CoTriggerEntity,Long> {


    @Query("Select n from CoTriggerEntity n where n.noticeStatus= :noticeStatus")
    public List<CoTriggerEntity> findNoticesByNoticeStatus(@Param("noticeStatus")String noticeStatus);

    @Query("Select c from CoTriggerEntity c where c.caseNum.caseNum= :caseNum")
    public CoTriggerEntity findByCaseNum(@Param("caseNum")Long caseNum);
}
