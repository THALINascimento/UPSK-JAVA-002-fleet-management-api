package com.api.fleetmanagement.repositories;

import com.api.fleetmanagement.models.TaxisModel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface TaxisRepository extends JpaRepository<TaxisModel, Integer> {
    Page<TaxisModel> findAll(Pageable pageable);
}
