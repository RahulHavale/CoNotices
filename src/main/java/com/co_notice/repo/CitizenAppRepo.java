package com.co_notice.repo;

import com.co_notice.entities.CitizenAppEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitizenAppRepo
        extends JpaRepository<CitizenAppEntity, Long> {
}
