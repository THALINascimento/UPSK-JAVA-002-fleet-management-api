package com.api.fleetmanagement.repositories;

import com.api.fleetmanagement.models.TrajectoriesModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TrajectoriesRepository extends JpaRepository<TrajectoriesModel, Integer> {
    @Query("SELECT t FROM TrajectoriesModel t LEFT JOIN FETCH t.taxi tr WHERE tr.id = :taxiId")
    Page<TrajectoriesModel> findTrajectoriesByTaxiId(@Param("taxiId") Integer taxiId, Pageable pageable);

    @Query("SELECT t FROM TrajectoriesModel t WHERE t.id IN (SELECT MAX(tt.id) " +
            "FROM TrajectoriesModel tt GROUP BY tt.taxi) ORDER BY t.date DESC")
    Page<TrajectoriesModel> findLastLocations(Pageable pageable);
}
