package com.co_notice.repo;

import com.co_notice.entities.COEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CORepo extends JpaRepository<COEntity, Long> {

}
